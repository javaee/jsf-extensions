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
        if (null != eventHook) {
            if (eventHook.isLiteralText()) {
                component.getAttributes().put("eventHook", eventHook.getValue(getFacesContext().getELContext()));
            }
            else {
                component.setValueExpression("eventHook", eventHook);
            }
        }
        if (null != eventType) {
            if (eventType.isLiteralText()) {
                component.getAttributes().put("eventType", eventType.getValue(getFacesContext().getELContext()));
            }
            else {
                component.setValueExpression("eventType", eventType);
            }
        }
        if (null != interactionType) {
            if (interactionType.isLiteralText()) {
                component.getAttributes().put("interactionType", interactionType.getValue(getFacesContext().getELContext()));
            }
            else {
                component.setValueExpression("interactionType", interactionType);
            }
        }
        
        
        
    }

    /**
     * Holds value of property interactionType.
     */
    private ValueExpression interactionType;

    /**
     * Setter for property interactionType.
     * @param interactionType New value of property interactionType.
     */
    public void setInteractionType(ValueExpression interactionType) {
        this.interactionType = interactionType;
    }

    /**
     * Holds value of property eventType.
     */
    private ValueExpression eventType;

    /**
     * Setter for property eventType.
     * @param eventType New value of property eventType.
     */
    public void setEventType(ValueExpression eventType) {
        this.eventType = eventType;
    }

    /**
     * Holds value of property eventHook.
     */
    private ValueExpression eventHook;

    /**
     * Setter for property eventHook.
     * @param eventHook New value of property eventHook.
     */
    public void setEventHook(ValueExpression eventHook) {
        this.eventHook = eventHook;
    }
    
    
}
