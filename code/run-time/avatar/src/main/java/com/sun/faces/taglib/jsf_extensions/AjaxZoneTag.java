package com.sun.faces.taglib.jsf_extensions;

import com.sun.faces.extensions.avatar.components.AjaxZone;
import javax.el.MethodExpression;
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

    protected void setProperties(UIComponent comp) {
        super.setProperties(comp);
        
        AjaxZone component = (AjaxZone) comp;
        
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
        if (null != inspectElementHook) {
            if (inspectElementHook.isLiteralText()) {
                component.getAttributes().put("inspectElementHook", inspectElementHook.getValue(getFacesContext().getELContext()));
            }
            else {
                component.setValueExpression("inspectElementHook", inspectElementHook);
            }
        }
        if (null != postInstallHook) {
            if (postInstallHook.isLiteralText()) {
                component.getAttributes().put("postInstallHook", postInstallHook.getValue(getFacesContext().getELContext()));
            }
            else {
                component.setValueExpression("postInstallHook", postInstallHook);
            }
        }
        if (action != null) {
            component.setActionExpression(action);
        }
        if (immediate != null) {
            if (!immediate.isLiteralText()) {
                component.setValueExpression("immediate", immediate);
            } else {
                component.setImmediate(java.lang.Boolean.valueOf(immediate.getExpressionString()).booleanValue());
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

    /**
     * Holds value of property inspectElementHook.
     */
    private ValueExpression inspectElementHook;

    /**
     * Setter for property inspectElementHook.
     * @param inspectElementHook New value of property inspectElementHook.
     */
    public void setInspectElementHook(ValueExpression inspectElementHook) {
        this.inspectElementHook = inspectElementHook;
    }

    /**
     * Holds value of property action.
     */
    private MethodExpression action;

    /**
     * Setter for property action.
     * @param action New value of property action.
     */
    public void setAction(MethodExpression action) {
        this.action = action;
    }

    /**
     * Holds value of property immediate.
     */
    private ValueExpression immediate;

    /**
     * Setter for property immediate.
     * @param immediate New value of property immediate.
     */
    public void setImmediate(ValueExpression immediate) {
        this.immediate = immediate;
    }

    /**
     * Holds value of property postInstallHook.
     */
    private ValueExpression postInstallHook;

    /**
     * Setter for property postInstallHook.
     * @param postInstallHook New value of property postInstallHook.
     */
    public void setPostInstallHook(ValueExpression postInstallHook) {
        this.postInstallHook = postInstallHook;
    }
    
    
}
