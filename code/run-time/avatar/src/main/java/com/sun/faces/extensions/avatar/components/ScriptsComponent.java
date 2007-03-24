package com.sun.faces.extensions.avatar.components;

import javax.faces.component.UIOutput;

/**
 * 
 */
public class ScriptsComponent extends UIOutput {
    public ScriptsComponent() {
        super();
        setRendererType("com.sun.faces.extensions.avatar.Scripts");
    }

    /** 
     * <p>Return the family for this component.</p> 
     */ 
    public String getFamily() { 
        return "com.sun.faces.extensions.avatar.Scripts"; 
    }

    /**
     * <p>Request attribute that indicates a <code>script</code> tag pointing to com_sun_faces_ajax.js has been rendered already.</p>
     */
    public static final String AJAX_JS_LINKED = "com.sun.faces.extensions.avatar.LINKED/com_sun_faces_ajax.js";
    
    /**
     * <p>Request attribute that indicates a <code>script</code> tag pointing to prototype.js has been rendered already.</p>
     */
    public static final String PROTOTYPE_JS_LINKED = "com.sun.faces.extensions.avatar.LINKED/prototype.js";
}
