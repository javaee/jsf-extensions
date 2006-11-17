/*
 *
 * Created on 17 novembre 2006, 11.53
 */

package org.asyncfaces.taglib;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author agori
 */
public class ValidateTag extends AjaxActionTagBase { 
    
    private ValueExpression event;
    private ValueExpression addClassOnError;
    
    public ValidateTag() {
        super();
    }
    
    public String getComponentType() {
        return "org.asyncfaces.Ajax";
    }
    
    public String getRendererType() {
        return "org.asyncfaces.ValidateRenderer";
    }
    
    public void release() {
        super.release();
        event = null;
        addClassOnError = null;
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();
        TagUtils.setProperty(context, component, "event", event);
        TagUtils.setProperty(context, component, "addClassOnError", addClassOnError);
    }

    public ValueExpression getEvent() {
        return event;
    }

    public void setEvent(ValueExpression event) {
        this.event = event;
    }

    public ValueExpression getAddClassOnError() {
        return addClassOnError;
    }

    public void setAddClassOnError(ValueExpression addClassOnError) {
        this.addClassOnError = addClassOnError;
    }
    
 
    
}
