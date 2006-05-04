/*
 * $Id: ProcessingContextViewRoot.java,v 1.4 2005/12/21 22:38:09 edburns Exp $
 */

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or
 * legal/CDDLv1.0.txt.
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * [Name of File] [ver.__] [Date]
 *
 * Copyright 2005 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.faces.components;

import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author edburns
 */
public class ProcessingContextViewRoot extends UIViewRoot implements Serializable {
    
    /** Creates a new instance of ProcessingContextViewRoot */
    public ProcessingContextViewRoot() {
        init();
    }
    
    private void init() {
        doAjaxRenderPhaseListener = new PhaseListener() {
            public PhaseId getPhaseId() {
                return PhaseId.INVOKE_APPLICATION;
            }
            
            public void beforePhase(PhaseEvent e) {
                
            }

            public void afterPhase(PhaseEvent e) {
                FacesContext context = e.getFacesContext();
                ProcessingContextViewRoot pcRoot = 
                        (ProcessingContextViewRoot) context.getViewRoot();
                // If this is an AJAX request
                if (!pcRoot.getDelegateRoots().isEmpty()) {
                    try {
                        
                        // set up the ResponseWriter
                        ResponseWriter responseWriter = context.getResponseWriter();
                        if (null == responseWriter) {
                            RenderKitFactory renderFactory = (RenderKitFactory)
                            FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
                            RenderKit renderKit =
                                    renderFactory.getRenderKit(context, 
                                    pcRoot.getRenderKitId());
                            Writer out = null;
                            Object response = context.getExternalContext().getResponse();
                            Method getWriter = 
                                    response.getClass().getMethod("getWriter", 
                                      (Class []) null);
                            if (null != getWriter) {
                                out = (Writer) getWriter.invoke(response);
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
                        
                        pcRoot.encodeAll(context);
                        pcRoot.clearDelegateRoots();
                    }
                    catch (NoSuchMethodException nsme) {
                        nsme.printStackTrace();
                    }
                    catch (IllegalAccessException iae) {
                        iae.printStackTrace();
                    }
                    catch (InvocationTargetException ite) {
                        ite.printStackTrace();
                    }
                    catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    finally {
                        context.responseComplete();
                    }
                }
            }

        };
        this.addPhaseListener(doAjaxRenderPhaseListener);
    }
    
    private transient PhaseListener doAjaxRenderPhaseListener = null;
    
    
    public void restoreState(FacesContext context, Object state) {
        this.removePhaseListener(doAjaxRenderPhaseListener);
        super.restoreState(context, state);
    }

    
    public String getRendererType() {
        return (COMPONENT_FAMILY);
        
    }
    
    private transient boolean calledDuringInvokeOnComponent = false;

    /**
     * Holds value of property processingContexts.
     */
    private transient List<ProcessingContext> processingContexts;

    public List<ProcessingContext> getProcessingContexts() {
        String param = null;
        String [] pcs = null;
        Map requestMap = FacesContext.getCurrentInstance().
                getExternalContext().getRequestParameterMap();
        
        if (null != processingContexts) {
            return processingContexts;
        }

        // If there is no processingContext request parameter
        if (!requestMap.containsKey(PROCESSING_CONTEXTS_REQUEST_PARAM_NAME)) {
            return null;
        }
        
        processingContexts = new ArrayList<ProcessingContext>();
        
        // If we have a processingContext Request Parameter
        param = 
            requestMap.get(PROCESSING_CONTEXTS_REQUEST_PARAM_NAME).toString();
        if (null != (pcs = param.split(",[ \t]*"))) {
            for (String cur : pcs) {
                cur = cur.trim();
                // Only absolute clientIds are valid as ProcessingContext identifiers
                if (cur.startsWith("" + NamingContainer.SEPARATOR_CHAR)) {
                    processingContexts.add(new ProcessingContext(cur.trim()));
                }
            }
        }
        // Catch a mal-formed param value here
        if (0 == processingContexts.size()) {
            processingContexts = null;
        }
        
        return this.processingContexts;
    }

    public void setProcessingContexts(List<ProcessingContext> processingContexts) {

        this.processingContexts = processingContexts;
    }

    public static final String PROCESSING_CONTEXTS_REQUEST_PARAM_NAME = "com.sun.faces.PCtxt";
    
    private transient List<UIComponent> delegateRoots = null;
    
    private void clearDelegateRoots() {
        getDelegateRoots().clear();
    }
    
    private List<UIComponent> getDelegateRoots() {
        
        if (null != delegateRoots) {
            return delegateRoots;
        }
        
        // Otherwise, we need to generate the list of delegateRoots.
        final UIComponent [] cur = new UIComponent[1];
        cur[0] = null;
        String pcClientId = null;

        delegateRoots = new ArrayList<UIComponent>();
        List<ProcessingContext> pcs = getProcessingContexts();
        if (null == pcs) {
            return delegateRoots;
        }
        Iterator<ProcessingContext> pcIter = pcs.iterator();
        // Build up a list of UIComponents, one for each processing context.
        while (pcIter.hasNext()) {
            pcClientId = pcIter.next().getClientId();
            assert(pcClientId.startsWith("" + NamingContainer.SEPARATOR_CHAR));
            pcClientId = pcClientId.substring(1);
            // PENDING(edburns): pain point.  An incorrectly specified pcId
            // can cause this method to break.  Need to catch this condition
            // and deal with it gracefully.
            ContextCallback cb = new ContextCallback() {
                public void invokeContextCallback(FacesContext facesContext, UIComponent comp) {
                    cur[0] = comp;
                }
                
            };
            this.calledDuringInvokeOnComponent = true;
            try {
                this.invokeOnComponent(FacesContext.getCurrentInstance(), 
                        pcClientId, cb);
            }
            finally {
                calledDuringInvokeOnComponent = false;
            }
            if (null != cur[0]) {
                delegateRoots.add(cur[0]);
            }
        }
        return delegateRoots;
    }
    
    public Iterator<UIComponent> getFacetsAndChildren() {
        List<UIComponent> roots = getDelegateRoots();
        Iterator<UIComponent> result = null;
        if (roots.isEmpty() || calledDuringInvokeOnComponent) {
            result = super.getFacetsAndChildren();
        }
        else {
            result = roots.iterator();
        }
        return result;
    }
    
    public void encodeAll(FacesContext context) throws IOException {
        List<UIComponent> roots = getDelegateRoots();
        if (roots.isEmpty()) {
            super.encodeAll(context);
        }
        else {
            ExternalContext extContext = context.getExternalContext();
            if (extContext.getResponse() instanceof HttpServletResponse) {
                HttpServletResponse servletResponse = (HttpServletResponse)
                    extContext.getResponse();
                servletResponse.setContentType("text/xml");
                servletResponse.setHeader("Cache-Control", "no-cache");
            }
            ResponseWriter responseWriter = context.getResponseWriter();
            responseWriter.startDocument();
            responseWriter.startElement("processing-context-responses", this);
            Map<String, Object> requestMap = 
                    context.getExternalContext().getRequestMap();
            requestMap.put(PROCESSING_CONTEXTS_REQUEST_PARAM_NAME, 
                    PROCESSING_CONTEXTS_REQUEST_PARAM_NAME);
            try {
                for (UIComponent curRoot : roots) {
                    curRoot.encodeAll(context);
                }
            }
            finally {
                requestMap.remove(PROCESSING_CONTEXTS_REQUEST_PARAM_NAME);
            }
            responseWriter.endElement("processing-context-responses");
            responseWriter.endDocument();
        }
    }
    

    
}
