/*
 * AjaxLifecycle.java
 *
 * Created on May 15, 2006, 4:31 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.faces.extensions.avatar.lifecycle;

import com.sun.faces.extensions.avatar.components.ProcessingContext;
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
    
    private PhaseId getRunThruPhaseId(FacesContext context, PhaseId lastPhase) {
        Map<String,String> headerMap = context.getExternalContext().getRequestHeaderMap();
        String runThru = null;
        PhaseId runThruPhaseId = null;

        // If found, only populate the phaseEvents list with phases up to that
        // phase.  
        runThru = headerMap.get(RUNTHRU_HEADER);
        if (null != runThru) {
            runThruPhaseId = null;
            // Issue 182
            for (Object idObj : PhaseId.VALUES) {
                runThruPhaseId = (PhaseId) idObj;
                if (-1 != runThruPhaseId.toString().indexOf(runThru)) {
                    break;
                }
                runThruPhaseId = null;
            }
        }
        runThruPhaseId = (null != runThruPhaseId) ? runThruPhaseId :
            lastPhase;
        
        return runThruPhaseId;
    }
    
    private List<PhaseEvent> getPhaseEventsFromRequest(FacesContext context,
            PhaseId lastPhase) {
        Map<String,String> headerMap = context.getExternalContext().getRequestHeaderMap();
        PhaseId lastPhaseId = getRunThruPhaseId(context, lastPhase);
        List<PhaseEvent> phaseEvents = new ArrayList<PhaseEvent>(lastPhaseId.getOrdinal());
            
        for (int i = PhaseId.APPLY_REQUEST_VALUES.getOrdinal(); 
             i <= lastPhaseId.getOrdinal(); i++) {
            phaseEvents.add(new PhaseEvent(context, 
                    (PhaseId) PhaseId.VALUES.get(i), this));
        }
        return phaseEvents;
    }
    
    private boolean writeMessages(FacesContext context, UIComponent comp, ResponseWriter writer) throws IOException {
        Iterator<FacesMessage> messages;
        boolean hasMessages = false;
        messages = context.getMessages(comp.getClientId(context));
        while (messages.hasNext()) {
            hasMessages = true;
            writer.startElement("message", comp);
            // PENDING(edburns): this is a rendering decision.
            // We should do something with the MessageRenderer.
            writer.write(messages.next().getSummary() + " ");
            writer.endElement("message");
        }
        return hasMessages;
    }
    
    private void writeEndOfResponse(FacesContext context) {
        UIViewRoot root = context.getViewRoot();
        AsyncResponse async = AsyncResponse.getInstance();
        try {
            ResponseWriter writer = async.getResponseWriter();
            writer.startElement("state", root);
            writer.write("<![CDATA[" + async.getViewState() + "]]>");
            writer.endElement("state");
            writer.endElement("async-response");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    private PhaseId runLifecycleOnProcessingContexts(PhaseEvent e) {
        FacesContext context = e.getFacesContext();
        UIViewRoot root = context.getViewRoot();
        AsyncResponse async = AsyncResponse.getInstance();
        List<ProcessingContext> subtrees = async.getProcessingContexts();
        final PhaseId curPhase = e.getPhaseId();
        final PhaseId lastPhaseRequested = getRunThruPhaseId(context,
                PhaseId.RENDER_RESPONSE);
                    
        // Manually invoke the lifecycle method for this phase on the ProcessingContext
        ContextCallback cb = new ContextCallback() {
            public void invokeContextCallback(FacesContext facesContext, UIComponent comp) {
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
                        // If we're only running through update.
                        if (lastPhaseRequested == PhaseId.UPDATE_MODEL_VALUES) {
                            // Then we need to render the result of the update
                            ValueHolder valueHolder = null;
                            String value = null;
                            writer.startElement("update", comp);
                            writer.writeAttribute("id", 
                                    comp.getClientId(facesContext), "id");
                            if (!writeMessages(facesContext, comp, writer)){
                                if (comp instanceof ValueHolder) {
                                    valueHolder = (ValueHolder) comp;
                                    try {
                                        // Get the converted value
                                        value = Util.getFormattedValue(facesContext,
                                                comp, valueHolder.getValue());
                                        writer.write("<![CDATA[");
                                        writer.write(value);
                                        writer.write("]]>");
                                    }
                                    catch (ConverterException ce) {
                                        writer.startElement("message", comp);
                                        writer.write(ce.getFacesMessage().getSummary());
                                    }
                                }
                            }
                            writer.endElement("update");
                        }
                    }
                    else if (curPhase == PhaseId.INVOKE_APPLICATION) {
                        facesContext.getViewRoot().processApplication(facesContext);
                    } 
                    else if (curPhase == PhaseId.RENDER_RESPONSE) {
                        writer.startElement("render", comp);
                        writer.writeAttribute("id", 
                                comp.getClientId(facesContext), "id");
                        writer.write("<![CDATA[");
                        comp.encodeAll(facesContext);
                        writer.write("]]>");
                        writer.endElement("render");
                    } 
                    
                }
                catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        };
        for (ProcessingContext cur : subtrees) {
            root.invokeOnComponent(context, cur.getClientId(), cb);
        }

        if (lastPhaseRequested == curPhase) {
            writeEndOfResponse(context);
        }
        
        return curPhase;
    }
    
    public void execute(FacesContext context) throws FacesException {
        Map<String,String> headerMap = context.getExternalContext().getRequestHeaderMap();
        List<PhaseEvent> phaseEvents = null;
        PhaseId 
                lastPhaseRequested = getRunThruPhaseId(context, PhaseId.RENDER_RESPONSE),
                lastPhaseRun = PhaseId.INVOKE_APPLICATION;

        if (!headerMap.containsKey(ASYNC_HEADER)) {
            parent.execute(context);
            return;
        }
        
        restoreView.execute(context);
        AsyncResponse async = AsyncResponse.getInstance();
        ResponseWriter writer = null;
        UIViewRoot root = context.getViewRoot(); 
        
        try {
            writer = async.getResponseWriter();
            ExternalContext extContext = context.getExternalContext();
            if (extContext.getResponse() instanceof HttpServletResponse) {
                HttpServletResponse servletResponse = (HttpServletResponse)
                     extContext.getResponse();
                servletResponse.setContentType("text/xml");
                servletResponse.setHeader("Cache-Control", "no-cache");
            }
            
            writer.startElement("async-response", root);

            phaseEvents = getPhaseEventsFromRequest(context, 
                    PhaseId.INVOKE_APPLICATION);
        
            for (PhaseEvent cur : phaseEvents) {
                lastPhaseRun = runLifecycleOnProcessingContexts(cur);
            }
        }
        catch (IOException ioe) {
            
        }
        finally {
            if (lastPhaseRequested == lastPhaseRun) {
                AsyncResponse.clearInstance();
            }
        }
    }
    
    public void render(FacesContext context) throws FacesException {
        Map<String,String> headerMap = context.getExternalContext().getRequestHeaderMap();
        PhaseId lastPhaseRequested = null;
        if (!headerMap.containsKey(ASYNC_HEADER)) {
            parent.render(context);
            return;
        }
        
        lastPhaseRequested = getRunThruPhaseId(context, PhaseId.RENDER_RESPONSE);
        // If the request did not direct us to include RENDER_RESPONSE
        if (PhaseId.RENDER_RESPONSE != lastPhaseRequested) {
            // take no action.
            return;
        }
        AsyncResponse async = AsyncResponse.getInstance();
        ResponseWriter writer = null;

        runLifecycleOnProcessingContexts(new PhaseEvent(context, PhaseId.RENDER_RESPONSE, this));
        
        AsyncResponse.clearInstance();
        
    }
    

    public void removePhaseListener(PhaseListener phaseListener) {
    }

    public void addPhaseListener(PhaseListener phaseListener) {
    }

    public PhaseListener[] getPhaseListeners() {
        PhaseListener [] result = new PhaseListener[0];
        return result;
    }
    
 
    public static final String FACES_PREFIX = "com.sun.faces.";
    public static final String LIFECYCLE_PREFIX = "com.sun.faces.lifecycle.";
    public static final String SUBTREES_HEADER= FACES_PREFIX + "subtrees";
    public static final String ASYNC_HEADER= FACES_PREFIX + "async";
    public static final String RUNTHRU_HEADER = LIFECYCLE_PREFIX + "runthru";
    
    

    
}
