/*
 *
 * Created on 14 novembre 2006, 15.33
 */

package org.asyncfaces.taglib;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentELTag;

/**
 *
 * @author agori
 */
public class TargetTag extends UIComponentELTag {
    
    private ValueExpression method;
    private ValueExpression _for;
    
    public TargetTag() {
        super();
    }

    public String getComponentType() {
        return "org.asyncfaces.Target";
    }

    public String getRendererType() {
        return null;
    }

    public void release() {
        super.release();
        method = null;
        _for = null;
    }

    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        FacesContext context = FacesContext.getCurrentInstance();
        
        TagUtils.setProperty(context, component, "for", _for);
        TagUtils.setProperty(context, component, "method", method);
    }
    


    


    public ValueExpression getMethod() {
        return method;
    }

    public void setMethod(ValueExpression method) {
        this.method = method;
    }

    public ValueExpression getFor() {
        return _for;
    }

    public void setFor(ValueExpression _for) {
        this._for = _for;
    }
    
}
