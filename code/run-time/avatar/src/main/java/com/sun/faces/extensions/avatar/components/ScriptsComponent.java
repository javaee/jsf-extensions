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

    
}
