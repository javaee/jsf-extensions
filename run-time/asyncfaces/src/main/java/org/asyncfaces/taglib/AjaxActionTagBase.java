/*
 *
 * Created on 14 novembre 2006, 15.11
 */

package org.asyncfaces.taglib;

import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.MethodExpressionActionListener;
import javax.faces.webapp.UIComponentELTag;
import org.asyncfaces.component.UIAjaxAction;

/**
 *
 * @author agori
 */
abstract public class AjaxActionTagBase extends UIComponentELTag {
    
    private MethodExpression actionListener;
    private MethodExpression action;
    private ValueExpression immediate;
    private ValueExpression render;
    private ValueExpression execute;
    private ValueExpression inputs;
    private ValueExpression skipUpdate;
    private ValueExpression form;
    private ValueExpression source;
    private ValueExpression parameters;
    
    public AjaxActionTagBase() {
        super();
    }
    
    public void release() {
        super.release();
        action = null;
        actionListener = null;
        immediate = null;
        render = null;
        execute = null;
        inputs = null;
        skipUpdate = null;
        form = null;
        source = null;
        parameters = null;
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();
        UIAjaxAction ajax = (UIAjaxAction) component;
        
        if (null != actionListener) {
            ajax.addActionListener(
                    new MethodExpressionActionListener(actionListener));
        }
        
        if (null != action) {
            ajax.setActionExpression(action);
        }
        
        if (immediate != null) {
            if (!immediate.isLiteralText()) {
                ajax.setValueExpression("immediate", immediate);
            } else {
                Boolean value = Boolean.valueOf(
                        immediate.getExpressionString());
                ajax.setImmediate(value);
            }
        }
        
        TagUtils.setProperty(context, component, "render", render);
        TagUtils.setProperty(context, component, "execute", execute);
        TagUtils.setProperty(context, component, "inputs", inputs);
        TagUtils.setProperty(context, component, "form", form);
        TagUtils.setProperty(context, component, "source", source);
        TagUtils.setProperty(context, component, "parameters", parameters);
        TagUtils.setProperty(context, component, "skipUpdate", skipUpdate);
        
    }
    
    
    
    public ValueExpression getRender() {
        return render;
    }
    
    public void setRender(ValueExpression render) {
        this.render = render;
    }
    
    public ValueExpression getExecute() {
        return execute;
    }
    
    public void setExecute(ValueExpression execute) {
        this.execute = execute;
    }
    
    public ValueExpression getInputs() {
        return inputs;
    }
    
    public void setInputs(ValueExpression inputs) {
        this.inputs = inputs;
    }
    
    public ValueExpression getSkipUpdate() {
        return skipUpdate;
    }
    
    public void setSkipUpdate(ValueExpression skipUpdate) {
        this.skipUpdate = skipUpdate;
    }
    
    public ValueExpression getForm() {
        return form;
    }
    
    public void setForm(ValueExpression form) {
        this.form = form;
    }
    
    public MethodExpression getActionListener() {
        return actionListener;
    }
    
    public void setActionListener(MethodExpression actionListener) {
        this.actionListener = actionListener;
    }
    
    public MethodExpression getAction() {
        return action;
    }
    
    public void setAction(MethodExpression action) {
        this.action = action;
    }
    
    public ValueExpression getImmediate() {
        return immediate;
    }
    
    public void setImmediate(ValueExpression immediate) {
        this.immediate = immediate;
    }

    public ValueExpression getSource() {
        return source;
    }

    public void setSource(ValueExpression source) {
        this.source = source;
    }

    public ValueExpression getParameters() {
        return parameters;
    }

    public void setParameters(ValueExpression parameters) {
        this.parameters = parameters;
    }
    
    
    
    
}
