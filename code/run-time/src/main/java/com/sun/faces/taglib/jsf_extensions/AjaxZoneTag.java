package com.sun.faces.taglib.jsf_extensions;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentELTag;

public class AjaxZoneTag extends UIComponentELTag {
    /** 
     * <p>Return the requested component type.</p> 
     */ 
    public String getComponentType() { 
        return "com.sun.faces.AjaxZone"; 
    } 
 
    /** 
     * <p>Return the requested renderer type.</p> 
     */ 
    public String getRendererType() { 
        return "com.sun.faces.AjaxZone"; 
    }
    
    private ValueExpression style = null;
    
    public void setStyle(ValueExpression ve) {
        style = ve;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        if (null != style) {
            if (style.isLiteralText()) {
                component.getAttributes().put("style", style.getValue(getFacesContext().getELContext()));
            }
            else {
                component.setValueExpression("style", style);
            }
        }
    }
    
    
}
