package com.sun.faces.avatar;

import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

public class PartialContext {

    private final static String KEY = "com.sun.faces.avatar.PartialContext";
    private final boolean partial;
    
    public PartialContext(FacesContext faces) {
        Map<String,String> m = FacesContext.getCurrentInstance().getExternalContext().getRequestHeaderMap();
        this.partial = m.containsKey(AvatarConstants.HeaderPartial);
    }

    /**
     * Always returns a PartialContext instance, either return the one from
     * the request or create and set a new one.
     * 
     * @return PartialContext instance, never null
     */
    public static PartialContext getInstance() {
        FacesContext faces = FacesContext.getCurrentInstance();
        Map<String, Object> m = faces
                .getExternalContext().getRequestMap();
        PartialContext ctx = (PartialContext) m.get(KEY);
        if (ctx == null) {
            ctx = new PartialContext(faces);
            m.put(KEY, ctx);
        }
        return ctx;
    }
    
    public boolean isPartial() {
        return this.partial;
    }

    public static void setInstance(PartialContext ctx) {
        Map<String, Object> m = FacesContext.getCurrentInstance()
                .getExternalContext().getRequestMap();
        m.put(KEY, ctx);
    }

}
