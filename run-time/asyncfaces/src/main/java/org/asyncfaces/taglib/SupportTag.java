/*
 *
 * Created on 14 novembre 2006, 17.58
 */

package org.asyncfaces.taglib;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author agori
 */
public class SupportTag extends AjaxActionTagBase {
    
    private ValueExpression event;
    private ValueExpression submitForm;
    
    public SupportTag() {
        super();
    }
    
    public String getComponentType() {
        return "org.asyncfaces.Ajax";
    }
    
    public String getRendererType() {
        return "org.asyncfaces.SupportRenderer";
    }
    
    public void release() {
        super.release();
        event = null;
        submitForm = null;
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();
        TagUtils.setProperty(context, component, "event", event);
        TagUtils.setProperty(context, component, "submitForm", submitForm);
    }
    
    public ValueExpression getEvent() {
        return event;
    }
    
    public void setEvent(ValueExpression event) {
        this.event = event;
    }

    public ValueExpression getSubmitForm() {
        return submitForm;
    }

    public void setSubmitForm(ValueExpression submitForm) {
        this.submitForm = submitForm;
    }
    
    
    
}
