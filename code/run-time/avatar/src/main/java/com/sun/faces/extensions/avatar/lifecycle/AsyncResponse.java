package com.sun.faces.extensions.avatar.lifecycle;

import com.sun.faces.extensions.common.util.FastWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.application.StateManager;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.faces.FactoryFinder;
import javax.faces.event.PhaseId;
import javax.faces.render.ResponseStateManager;


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
            instance.clearSubtrees();
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
                StateCapture sc = new StateCapture(oldWriter.cloneWithWriter(fw), fw);
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
    
    private List<String> populateListFromHeader(String headerName) {
        String param = null;
        String [] pcs = null;
        Map requestMap = FacesContext.getCurrentInstance().
                getExternalContext().getRequestHeaderMap();
        List<String> result = null;
        // If there is no subtrees request header
        result = new ArrayList<String>();

        if (!requestMap.containsKey(headerName)) {
            return result;
        }
        // If we have a processingContext Request Parameter
        param = 
            requestMap.get(headerName).toString();
        if (null != (pcs = param.split(",[ \t]*"))) {
            for (String cur : pcs) {
                cur = cur.trim();
                result.add(cur);
            }
        }

        return result;
    }

    private transient List<String> executeSubtrees;
    private transient List<String> renderSubtrees;
    
    public void clearSubtrees() {
        if (null != executeSubtrees) {
            executeSubtrees.clear();
        }
        executeSubtrees = null;
        if (null != renderSubtrees) {
            renderSubtrees.clear();
        }
        renderSubtrees = null;
    }

    public List<String> getExecuteSubtrees() {
        
        if (null != executeSubtrees) {
            return executeSubtrees;
        }
        executeSubtrees = populateListFromHeader(AjaxLifecycle.EXECUTE_HEADER);
        return this.executeSubtrees;
    }

    public void setExecuteSubtrees(List<String> executeSubtrees) {

        this.executeSubtrees = executeSubtrees;
    }    

    public List<String> getRenderSubtrees() {
        
        if (null != renderSubtrees) {
            return renderSubtrees;
        }
        renderSubtrees = populateListFromHeader(AjaxLifecycle.RENDER_HEADER);
        return this.renderSubtrees;
    }

    public void setRenderSubtrees(List<String> renderSubtrees) {

        this.renderSubtrees = renderSubtrees;
    }
    
    public PhaseId getLastPhaseId(FacesContext context) {
        PhaseId result = PhaseId.INVOKE_APPLICATION;
        Map<String,String> headerMap = context.getExternalContext().getRequestHeaderMap();
        if (headerMap.containsKey(AjaxLifecycle.RENDER_HEADER)) {
            result = PhaseId.RENDER_RESPONSE;
        }
        
        return result;
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
        private FastWriter fw;
        
        public StateCapture(ResponseWriter orig, FastWriter fw) {
            this.orig = orig;
            this.fw = fw;
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
            String buf = null;
            int i,j;
            if (null == this.state && null != (buf = fw.toString())) {
                buf = buf.toLowerCase(Locale.US);
                if (-1 != (i = buf.indexOf(ResponseStateManager.VIEW_STATE_PARAM.toLowerCase()))) {
                    if (-1 != (i = buf.lastIndexOf("<", i))) {
                        if (-1 != (i = buf.indexOf("value", i))) {
                            if (-1 != (i = buf.indexOf("\"",i))) {
                                if (-1 != (j = buf.indexOf("\"", ++i))) {
                                    state = buf.substring(i, j);
                                }
                            }
                        }
                    }
                }
            }
            return this.state != null ? this.state.toString() : "";
        }
        
    }
    

}
