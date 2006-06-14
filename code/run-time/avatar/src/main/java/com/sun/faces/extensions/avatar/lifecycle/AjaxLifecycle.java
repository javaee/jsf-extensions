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
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.component.ContextCallback;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

import com.sun.faces.lifecycle.RestoreViewPhase;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author edburns
 */
public class AjaxLifecycle extends Lifecycle {
    
    private Lifecycle parent = null;
    private RestoreViewPhase restoreView = null;
    private List<PhaseEvent> phaseEvents = null;
    
    /** Creates a new instance of AjaxLifecycle */
    public AjaxLifecycle(Lifecycle parent) {
        this.parent = parent;
        restoreView = new RestoreViewPhase();
    }
    
    public void render(FacesContext context) throws FacesException {
        Map<String,String> headerMap = context.getExternalContext().getRequestHeaderMap();

        if (!headerMap.containsKey(ASYNC_HEADER)) {
            parent.render(context);
            return;
        }
        
        PhaseEvent event = new PhaseEvent(context, PhaseId.RENDER_RESPONSE, this);
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
            writer.writeAttribute("state", async.getViewState(), "state");
        }
        catch (IOException ioe) {
            
        }

        runLifecycleOnProcessingContexts(event);
        
        try {
            writer.endElement("async-response");
        }
        catch (IOException ioe) {
            
        }
        
        
        AsyncResponse.clearInstance();
    }

    public void execute(FacesContext context) throws FacesException {
        Map<String,String> headerMap = context.getExternalContext().getRequestHeaderMap();

        if (!headerMap.containsKey(ASYNC_HEADER)) {
            parent.execute(context);
            return;
        }
        
        restoreView.execute(context);
        
        // Look for the runthru header.  
        
        // If found, only populate the phaseEvents list with phases up to that
        // phase.  
        
        phaseEvents = new ArrayList<PhaseEvent>(5);
        phaseEvents.add(new PhaseEvent(context, PhaseId.APPLY_REQUEST_VALUES, this));
        phaseEvents.add(new PhaseEvent(context, PhaseId.PROCESS_VALIDATIONS, this));
        phaseEvents.add(new PhaseEvent(context, PhaseId.UPDATE_MODEL_VALUES, this));
        phaseEvents.add(new PhaseEvent(context, PhaseId.INVOKE_APPLICATION, this));

        for (PhaseEvent cur : phaseEvents) {
            runLifecycleOnProcessingContexts(cur);
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
    
    private void runLifecycleOnProcessingContexts(PhaseEvent e) {
        final PhaseId curPhase = e.getPhaseId();
        // Manually invoke the lifecycle method for this phase on the ProcessingContext
        ContextCallback cb = new ContextCallback() {
            public void invokeContextCallback(FacesContext facesContext, UIComponent comp) {
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
                    try {
                        ResponseWriter writer = AsyncResponse.getInstance().getResponseWriter();
                        writer.startElement("encode", comp);
                        writer.writeAttribute("id", 
                                comp.getClientId(facesContext), "id");
                        writer.write("<![CDATA[");
                        comp.encodeAll(facesContext);
                        writer.write("]]>");
                        writer.endElement("encode");
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        FacesContext context = e.getFacesContext();
        UIViewRoot root = context.getViewRoot();
        AsyncResponse async = AsyncResponse.getInstance();
        List<ProcessingContext> subtrees = async.getProcessingContexts();
        for (ProcessingContext cur : subtrees) {
            root.invokeOnComponent(context, cur.getClientId(), cb);
        }
    }
    
    public static final String FACES_PREFIX = "com.sun.faces.";
    public static final String LIFECYCLE_PREFIX = "com.sun.faces.lifecycle.";
    public static final String SUBTREES_HEADER= FACES_PREFIX + "subtrees";
    public static final String ASYNC_HEADER= FACES_PREFIX + "async";
    public static final String RUNTHRU_HEADER = FACES_PREFIX + "runthru";
    
    

    
}
