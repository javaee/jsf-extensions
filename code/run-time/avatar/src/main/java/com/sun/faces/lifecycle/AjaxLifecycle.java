/*
 * AjaxLifecycle.java
 *
 * Created on May 15, 2006, 4:31 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.faces.lifecycle;

import com.sun.faces.components.ProcessingContext;
import com.sun.faces.components.ProcessingContextViewRoot;
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
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

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
    
    private void setupResponseWriter(FacesContext context) {
        // set up the ResponseWriter
        ResponseWriter responseWriter = context.getResponseWriter();
        if (null == responseWriter) {
            RenderKitFactory renderFactory = (RenderKitFactory)
            FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
            RenderKit renderKit =
                    renderFactory.getRenderKit(context,
                    context.getViewRoot().getRenderKitId());
            Writer out = null;
            Object response = context.getExternalContext().getResponse();
            try {
                Method getWriter =
                        response.getClass().getMethod("getWriter",
                        (Class []) null);
                if (null != getWriter) {
                    out = (Writer) getWriter.invoke(response);
                }
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (SecurityException ex) {
                ex.printStackTrace();
            } catch (InvocationTargetException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            } catch (NoSuchMethodException ex) {
                ex.printStackTrace();
            }
            if (null != out) {
                responseWriter =
                        renderKit.createResponseWriter(out,
                        "text/xml",
                        context.getExternalContext().getRequestCharacterEncoding());
            }
            if (null != responseWriter) {
                context.setResponseWriter(responseWriter);
            }

        }
    }

    public void render(FacesContext context) throws FacesException {
        PhaseEvent event = new PhaseEvent(context, PhaseId.RENDER_RESPONSE, this);
        ProcessingContextViewRoot pcRoot =
                (ProcessingContextViewRoot) context.getViewRoot();
        List<UIComponent> roots = pcRoot.getDelegateRoots();

        runLifecycleOnProcessingContexts(event, pcRoot, roots);
    }

    public void execute(FacesContext context) throws FacesException {
        restoreView.execute(context);
        setupResponseWriter(context);
        phaseEvents = new ArrayList<PhaseEvent>(5);
        phaseEvents.add(new PhaseEvent(context, PhaseId.APPLY_REQUEST_VALUES, this));
        phaseEvents.add(new PhaseEvent(context, PhaseId.PROCESS_VALIDATIONS, this));
        phaseEvents.add(new PhaseEvent(context, PhaseId.UPDATE_MODEL_VALUES, this));
        phaseEvents.add(new PhaseEvent(context, PhaseId.INVOKE_APPLICATION, this));
        ProcessingContextViewRoot pcRoot =
                (ProcessingContextViewRoot) context.getViewRoot();
        List<UIComponent> roots = pcRoot.getDelegateRoots();

        for (PhaseEvent cur : phaseEvents) {
            runLifecycleOnProcessingContexts(cur, pcRoot, roots);
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
    
    private void runLifecycleOnProcessingContexts(PhaseEvent e, ProcessingContextViewRoot pcRoot,
            List<UIComponent> roots) {
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
                    // Take no action on invokeApplication
                }
                else if (curPhase == PhaseId.RENDER_RESPONSE) {
                    try {
                        comp.encodeAll(facesContext);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        };
        List<ProcessingContext> pcs = pcRoot.getProcessingContexts();
        Iterator<ProcessingContext> pcIter = pcs.iterator();
        String pcClientId = null;
        // Build up a list of UIComponents, one for each processing context.
        while (pcIter.hasNext()) {
            pcClientId = pcIter.next().getClientId();
            assert(pcClientId.startsWith("" + NamingContainer.SEPARATOR_CHAR));
            pcClientId = pcClientId.substring(1);
            pcRoot.setCalledDuringInvokeOnComponent(true);
            try {
                pcRoot.invokeOnComponent(FacesContext.getCurrentInstance(),
                        pcClientId, cb);
            }
            finally {
                pcRoot.setCalledDuringInvokeOnComponent(false);
            }
        }
    }
    
    
}
