package com.sun.faces.extensions.avatar.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.render.Renderer;

public class ComponentMethodCallback implements ContextCallback {

    private final static Class[] RENDER_PARAM = new Class[] {
            FacesContext.class, UIComponent.class };

    private final static Class[] COMPONENT_PARAM = new Class[] { FacesContext.class };

    private final String methodName;

    private final String clientId;
    
    private final PhaseId phaseId;

    private Object result;

    public ComponentMethodCallback(String clientId, String methodName, 
            PhaseId phaseId) {
        this.clientId = clientId;
        this.methodName = methodName;
        this.phaseId = phaseId;
    }

    public Object invoke(FacesContext faces) {
        UIViewRoot root = faces.getViewRoot();
        root.invokeOnComponent(faces, this.clientId, this);
        return this.result;
    }

    public void invokeContextCallback(FacesContext faces, UIComponent c) {
        Method m = null;
        try {

            // first attempt to pull from renderer
            String t = c.getRendererType();
            if (t != null) {
                Renderer r = faces.getRenderKit().getRenderer(c.getFamily(), t);
                if (r != null) {
                    try {
                        m = r.getClass().getMethod(this.methodName, RENDER_PARAM);
                    } catch (Exception e) {
                    }

                    if (m != null) {
                        this.result = m.invoke(r, new Object[] { faces, c });
                    }
                }
            }

            // else delegate to component itself
            if (m == null) {
                m = c.getClass().getMethod(this.methodName, COMPONENT_PARAM);
                this.result = m.invoke(c, new Object[] { faces });
            }

        } catch (NoSuchMethodException e) {
            throw new FacesException(e);
        } catch (IllegalAccessException e) {
            throw new FacesException(e);
        } catch (InvocationTargetException e) {
            throw new FacesException(e.getCause());
        }
    }

    public Object getResult() {
        return this.result;
    }

    public String getClientId() {
        return clientId;
    }

    public String getMethod() {
        return methodName;
    }
    
    public PhaseId getPhaseId() {
        return phaseId;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(32);
        return sb.append("Event[").append(clientId).append(',').append("].").append(this.methodName).append("()")
                .toString();
    }
}
