package com.sun.faces.avatar.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

import com.sun.faces.avatar.AvatarConstants;

public class EventCallback implements ContextCallback {

    private final static Class[] RENDER_PARAM = new Class[] {
            FacesContext.class, UIComponent.class };

    private final static Class[] COMPONENT_PARAM = new Class[] { FacesContext.class };

    private final String method;

    private final String clientId;

    private final boolean immediate;

    private Object result;

    public static EventCallback getInstace(FacesContext faces) {
        Map<String, String> p = faces.getExternalContext()
                .getRequestHeaderMap();

        String de = p.get(AvatarConstants.HeaderEvent);
        if (de != null) {
            String[] ep = de.split(",");
            String clientId = ep[0];
            String method = (ep.length > 1) ? ep[1] : null;
            boolean immediate = (ep.length > 2) ? "immediate".equals(ep[2])
                    : false;

            return new EventCallback(clientId, method, immediate);
        }
        return null;
    }

    public EventCallback(String clientId, String event) {
        this(clientId, event, false);
    }

    public EventCallback(String clientId, String event, boolean immediate) {
        this.clientId = clientId;
        this.method = toMethodName(event);
        this.immediate = immediate;
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
                        m = r.getClass().getMethod(this.method, RENDER_PARAM);
                    } catch (Exception e) {
                    }

                    if (m != null) {
                        this.result = m.invoke(r, new Object[] { faces, c });
                    }
                }
            }

            // else delegate to component itself
            if (m == null) {
                m = c.getClass().getMethod(this.method, COMPONENT_PARAM);
            }

        } catch (NoSuchMethodException e) {
            throw new FacesException(e);
        } catch (IllegalAccessException e) {
            throw new FacesException(e);
        } catch (InvocationTargetException e) {
            throw new FacesException(e.getCause());
        }
    }

    private final static String toMethodName(String event) {
        if (event == null || "".equals(event))
            return "onEvent";
        StringBuffer sb = new StringBuffer(16);
        sb.append("on").append(event);
        sb.setCharAt(2, Character.toUpperCase(event.charAt(0)));
        return sb.toString();
    }

    public Object getResult() {
        return this.result;
    }

    public String getClientId() {
        return clientId;
    }

    public boolean isImmediate() {
        return immediate;
    }

    public String getMethod() {
        return method;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(32);
        return sb.append("Event[").append(clientId).append(',').append(
                this.immediate).append("].").append(this.method).append("()")
                .toString();
    }
}
