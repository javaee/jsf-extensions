/*
 * AjaxLifecycle.java
 *
 * Created on May 15, 2006, 4:31 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.faces.extensions.avatar.lifecycle;

import com.sun.faces.extensions.common.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

import com.sun.faces.lifecycle.RestoreViewPhase;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.ValueHolder;
import javax.faces.convert.ConverterException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author edburns
 */
public class AjaxLifecycle extends Lifecycle {
    
    private Lifecycle parent = null;
    private RestoreViewPhase restoreView = null;

    /** Creates a new instance of AjaxLifecycle */
    public AjaxLifecycle(Lifecycle parent) {
        this.parent = parent;
        restoreView = new RestoreViewPhase();
    }
    
    private boolean writeMessages(FacesContext context, UIComponent comp, 
            ConverterException converterException,
            ResponseWriter writer) throws IOException {
        Iterator<FacesMessage> messages;
        boolean 
                wroteStart = false,
                hasMessages = false;
        messages = context.getMessages(comp.getClientId(context));
        while (messages.hasNext()) {
            hasMessages = true;
            if (!wroteStart) {
                writer.startElement("messages", comp);
                wroteStart = true;
            }
            writer.startElement("message", comp);
            // PENDING(edburns): this is a rendering decision.
            // We should do something with the MessageRenderer.
            writer.write(messages.next().getSummary() + " ");
            writer.endElement("message");
        }
        if (null != converterException) {
            if (!wroteStart) {
                writer.startElement("messages", comp);
                wroteStart = true;
            }
            writer.write(converterException.getFacesMessage().getSummary());
        }
        if (wroteStart) {
            writer.endElement("messages");
        }
        return hasMessages;
    }
    
    private List<String> getSubtreesForEvent(PhaseEvent e) {
        AsyncResponse async = AsyncResponse.getInstance();
        
        List<String> result = null, 
                renderSubtrees = async.getRenderSubtrees(),
                executeSubtrees = async.getExecuteSubtrees();
        if (executeSubtrees.isEmpty()) {
            result = renderSubtrees;
        }
        else {
            result = e.getPhaseId() == PhaseId.RENDER_RESPONSE ? renderSubtrees :
                executeSubtrees;
        }
        return result;
    }
    
    private PhaseId runLifecycleOnProcessingContexts(PhaseEvent e) {
        FacesContext context = e.getFacesContext();
        UIViewRoot root = context.getViewRoot();
        List<String> subtrees = getSubtreesForEvent(e);
        final PhaseId curPhase = e.getPhaseId();
                    
        // Manually invoke the lifecycle method for this phase on the ProcessingContext
        ContextCallback cb = new ContextCallback() {
            public void invokeContextCallback(FacesContext facesContext, UIComponent comp) {
                ConverterException converterException = null;
                try {
                    ResponseWriter writer = AsyncResponse.getInstance().getResponseWriter();

                    if (curPhase == PhaseId.APPLY_REQUEST_VALUES) {
                        comp.processDecodes(facesContext);
                    }
                    else if (curPhase == PhaseId.PROCESS_VALIDATIONS) {
                        comp.processValidators(facesContext);
                    }
                    else if (curPhase == PhaseId.UPDATE_MODEL_VALUES) {
                        comp.processUpdates(facesContext);
                    }
                    else if (curPhase == PhaseId.INVOKE_APPLICATION) {
                        facesContext.getViewRoot().processApplication(facesContext);
                    } 
                    else if (curPhase == PhaseId.RENDER_RESPONSE) {
                        writer.startElement("render", comp);
                        writer.writeAttribute("id", 
                                comp.getClientId(facesContext), "id");
                        try {
                            writer.startElement("markup", comp);
                            writer.write("<![CDATA[");
                            comp.encodeAll(facesContext);
                            writer.write("]]>");
                            writer.endElement("markup");
                        }
                        catch (ConverterException ce) {
                            converterException = ce;
                        }
                        writeMessages(facesContext, comp,
                                converterException, writer);
                        writer.endElement("render");
                    } 
                    
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        for (String cur : subtrees) {
            root.invokeOnComponent(context, cur, cb);
        }

        return curPhase;
    }
    
    public void execute(FacesContext context) throws FacesException {
        Map<String,String> headerMap = context.getExternalContext().getRequestHeaderMap();

        if (!headerMap.containsKey(PARTIAL_HEADER)) {
            parent.execute(context);
            return;
        }
        List<PhaseEvent> phaseEvents = null;
        AsyncResponse async = AsyncResponse.getInstance();
        
        restoreView.execute(context);

        phaseEvents = new ArrayList<PhaseEvent>(4);
        for (int i = PhaseId.APPLY_REQUEST_VALUES.getOrdinal(); 
             i <= PhaseId.INVOKE_APPLICATION.getOrdinal(); i++) {
            phaseEvents.add(new PhaseEvent(context, 
                    (PhaseId) PhaseId.VALUES.get(i), this));
        }

        try {
            for (PhaseEvent cur : phaseEvents) {
                runLifecycleOnProcessingContexts(cur);
            }
        }
        finally {
            if (PhaseId.INVOKE_APPLICATION == async.getLastPhaseId(context)) {
                AsyncResponse.clearInstance();
            }
        }
    }
    
    public void render(FacesContext context) throws FacesException {
        Map<String,String> headerMap = context.getExternalContext().getRequestHeaderMap();

        if (!headerMap.containsKey(PARTIAL_HEADER)) {
            parent.render(context);
            return;
        }
        AsyncResponse async = AsyncResponse.getInstance();

        if (PhaseId.INVOKE_APPLICATION == async.getLastPhaseId(context)) {
            return;
        }
        
        UIViewRoot root = context.getViewRoot();
        ResponseWriter writer = null;
        
        try {
            writer = async.getResponseWriter();
            ExternalContext extContext = context.getExternalContext();
            if (extContext.getResponse() instanceof HttpServletResponse) {
                HttpServletResponse servletResponse = (HttpServletResponse)
                     extContext.getResponse();
                servletResponse.setContentType("text/xml");
                servletResponse.setHeader("Cache-Control", "no-cache");
            }
            
            writer.startElement("partial-response", root);

            writer.startElement("components", root);
            runLifecycleOnProcessingContexts(new PhaseEvent(context, PhaseId.RENDER_RESPONSE, this));
            writer.endElement("components");
            
            writer.startElement("state", root);
            writer.write("<![CDATA[" + async.getViewState() + "]]>");
            writer.endElement("state");

            writer.endElement("partial-response");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        finally {
            AsyncResponse.clearInstance();
        }
        
    }
    

    public void removePhaseListener(PhaseListener phaseListener) {
    }

    public void addPhaseListener(PhaseListener phaseListener) {
    }

    public PhaseListener[] getPhaseListeners() {
        PhaseListener [] result = new PhaseListener[0];
        return result;
    }
    
 
    public static final String FACES_PREFIX = "com.sun.faces.avatar.";
    public static final String PARTIAL_HEADER= FACES_PREFIX + "partial";
    public static final String EXECUTE_HEADER = FACES_PREFIX + "execute";
    public static final String RENDER_HEADER= FACES_PREFIX + "render";
    
}
