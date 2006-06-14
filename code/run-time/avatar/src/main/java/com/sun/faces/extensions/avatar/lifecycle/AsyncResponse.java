package com.sun.faces.extensions.avatar.lifecycle;

import com.sun.faces.extensions.avatar.components.ProcessingContext;
import com.sun.faces.extensions.common.util.FastWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.StateManager;
import javax.faces.application.ViewHandler;
import javax.faces.component.ContextCallback;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.FactoryFinder;


public class AsyncResponse {
    
    private final static ThreadLocal<AsyncResponse> Instance = new ThreadLocal<AsyncResponse>();
    
    private final Map<String,String> encoded = new HashMap<String, String>();
    private String viewState;

    public AsyncResponse() {
        super();
    }
    
    public static boolean exists() {
        return Instance.get() != null;
    }
    
    public static AsyncResponse getInstance(boolean create) {
        AsyncResponse ar = Instance.get();
        if (ar == null && create) {
            ar = new AsyncResponse();
            Instance.set(ar);
        }
        return ar;
    }
    
    public static AsyncResponse getInstance() {
        return getInstance(true);
    }
    
    public static void clearInstance() {
        AsyncResponse instance = getInstance(false);
        if (null != instance) {
            instance.clearProcessingContexts();
        }
        Instance.remove();
    }
    
    public void setViewState(String output) {
        this.viewState = output;
    }
    
    public String getViewState() {
        if (null == this.viewState) {
            FacesContext faces = FacesContext.getCurrentInstance();
            ResponseWriter oldWriter = faces.getResponseWriter();
            try {
                FastWriter fw = new FastWriter(256);
                
                // write state
                StateCapture sc = new StateCapture(oldWriter.cloneWithWriter(fw));
                faces.setResponseWriter(sc);
                StateManager sm = faces.getApplication().getStateManager();
                Object stateObj = sm.saveSerializedView(faces);
                sm.writeState(faces,
                        (StateManager.SerializedView) stateObj);
                this.viewState = sc.getState();
                
            } 
            catch (IOException ioe) {
                
            }
            catch (FacesException e) {
                if (e.getCause() instanceof IOException) {
                    
                } else {
                    throw e;
                }
            } finally {
                faces.setResponseWriter(oldWriter);
            }
            
        }
        
        
        return this.viewState;
    }
    
    public static ResponseWriter getResponseWriter() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        ResponseWriter rw = context.getResponseWriter();
        if (rw == null) {
            rw = setupResponseWriter(context);
        }
        return rw;
    }

    /**
     * Holds value of property processingContexts.
     */
    private transient List<ProcessingContext> processingContexts;
    
    public void clearProcessingContexts() {
        if (null != processingContexts) {
            processingContexts.clear();
        }
        processingContexts = null;
    }

    public List<ProcessingContext> getProcessingContexts() {
        String param = null;
        String [] pcs = null;
        Map requestMap = FacesContext.getCurrentInstance().
                getExternalContext().getRequestHeaderMap();
        
        if (null != processingContexts) {
            return processingContexts;
        }

        // If there is no subtrees request header
        if (!requestMap.containsKey(AjaxLifecycle.SUBTREES_HEADER)) {
            return null;
        }
        
        processingContexts = new ArrayList<ProcessingContext>();
        
        // If we have a processingContext Request Parameter
        param = 
            requestMap.get(AjaxLifecycle.SUBTREES_HEADER).toString();
        if (null != (pcs = param.split(",[ \t]*"))) {
            for (String cur : pcs) {
                cur = cur.trim();
                processingContexts.add(new ProcessingContext(cur.trim()));
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

    private static ResponseWriter setupResponseWriter(FacesContext context) {
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
        return responseWriter;
    }
    
    
    private static class StateCapture extends ResponseWriterWrapper {
        
        protected final ResponseWriter orig;
        private Object state;
        
        public StateCapture(ResponseWriter orig) {
            this.orig = orig;
        }

        protected ResponseWriter getWrapped() {
            return this.orig;
        }

        public void writeAttribute(String name, Object value, String property) throws IOException {
            if ("value".equals(name)) {
                this.state = value;
            }
        }
        
        public String getState() {
            return this.state != null ? this.state.toString() : "";
        }
        
    }
    

}
