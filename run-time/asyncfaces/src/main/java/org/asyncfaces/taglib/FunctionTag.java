/*
 *
 * Created on 14 novembre 2006, 15.11
 */

package org.asyncfaces.taglib;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author agori
 */
public class FunctionTag extends AjaxActionTagBase {
    
    private ValueExpression name;
    
    public FunctionTag() {
        super();
    }

    public String getComponentType() {
        return "org.asyncfaces.Ajax";
    }

    public String getRendererType() {
        return "org.asyncfaces.FunctionRenderer";
    }

    public void release() {
        super.release();
        name  = null;
    }

    protected void setProperties(UIComponent comp) {
        super.setProperties(comp);
        FacesContext context = FacesContext.getCurrentInstance();
        TagUtils.setProperty(context, comp, "name", name);
    }
    


    public ValueExpression getName() {
        return name;
    }

    public void setName(ValueExpression name) {
        this.name = name;
    }
    

    
}
