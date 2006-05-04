package com.sun.faces.components;

import javax.faces.component.UICommand;

/**
 * 
 */
public class AjaxZone extends UICommand {
    public AjaxZone() {
        super();
        setRendererType("com.sun.faces.AjaxZone");
    }

    /** 
     * <p>Return the family for this component.</p> 
     */ 
    public String getFamily() { 
        return "com.sun.faces.AjaxZone"; 
    }
    
}
