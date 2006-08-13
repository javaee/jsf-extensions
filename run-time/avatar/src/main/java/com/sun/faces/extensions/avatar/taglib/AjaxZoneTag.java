package com.sun.faces.extensions.avatar.taglib;

import com.sun.faces.extensions.avatar.components.AjaxZone;
import java.util.List;
import java.util.Map;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.component.UIOutput;
import javax.faces.webapp.UIComponentELTag;
import javax.servlet.jsp.JspException;

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
        if (null != collectPostData) {
            if (collectPostData.isLiteralText()) {
                component.getAttributes().put("collectPostData", collectPostData.getValue(getFacesContext().getELContext()));
            }
            else {
                component.setValueExpression("collectPostData", collectPostData);
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
        if (null != inspectElement) {
            if (inspectElement.isLiteralText()) {
                component.getAttributes().put("inspectElement", inspectElement.getValue(getFacesContext().getELContext()));
            }
            else {
                component.setValueExpression("inspectElement", inspectElement);
            }
        }
        if (null != replaceElement) {
            if (replaceElement.isLiteralText()) {
                component.getAttributes().put("replaceElement", replaceElement.getValue(getFacesContext().getELContext()));
            }
            else {
                component.setValueExpression("replaceElement", replaceElement);
            }
        }
        if (null != postReplace) {
            if (postReplace.isLiteralText()) {
                component.getAttributes().put("postReplace", postReplace.getValue(getFacesContext().getELContext()));
            }
            else {
                component.setValueExpression("postReplace", postReplace);
            }
        }
        if (null != getCallbackData) {
            if (getCallbackData.isLiteralText()) {
                component.getAttributes().put("getCallbackData", getCallbackData.getValue(getFacesContext().getELContext()));
            }
            else {
                component.setValueExpression("getCallbackData", getCallbackData);
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
     * Holds value of property collectPostData.
     */
    private ValueExpression collectPostData;

    /**
     * Setter for property collectPostData.
     * @param collectPostData New value of property collectPostData.
     */
    public void setCollectPostData(ValueExpression collectPostData) {
        this.collectPostData = collectPostData;
    }

    /**
     * Holds value of property inspectElement.
     */
    private ValueExpression inspectElement;

    /**
     * Setter for property inspectElement.
     * @param inspectElement New value of property inspectElement.
     */
    public void setInspectElement(ValueExpression inspectElement) {
        this.inspectElement = inspectElement;
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
     * Holds value of property postReplace.
     */
    private ValueExpression postReplace;

    /**
     * Setter for property postReplace.
     * @param postReplace New value of property postReplace.
     */
    public void setPostReplace(ValueExpression postReplace) {
        this.postReplace = postReplace;
    }

    /**
     * Holds value of property replaceElement.
     */
    private ValueExpression replaceElement;

    /**
     * Setter for property replaceElement.
     * @param replaceElement New value of property replaceElement.
     */
    public void setReplaceElementHook(ValueExpression replaceElement) {
        this.replaceElement = replaceElement;
    }

    /**
     * Holds value of property getCallbackData.
     */
    private ValueExpression getCallbackData;

    /**
     * Setter for property getCallbackData.
     * @param getCallbackData New value of property getCallbackData.
     */
    public void setGetCallbackData(ValueExpression getCallbackData) {
        this.getCallbackData = getCallbackData;
    }
    
    
}
