/*
 *
 * Created on 14 novembre 2006, 0.40
 */

package org.asyncfaces;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.ContextCallback;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *
 * @author agori
 */
public class Util {
    
    static private boolean inspectForValidity(UIComponent comp) {
        if (comp instanceof EditableValueHolder &&
                !((EditableValueHolder) comp).isValid()) {
            return false;
        }
        
        for (UIComponent c : comp.getChildren()) {
            if (!inspectForValidity(c)) {
                return false;
            }
        }
        return true;
    }
    
    static public boolean isValid() {
        FacesContext context = FacesContext.getCurrentInstance();
        List<String> execute = AsyncResponse.getInstance().getExecuteSubtrees();
        UIViewRoot root = context.getViewRoot();
        AsyncFacesContextCallback callback = new AsyncFacesContextCallback();
        for (String id : execute) {
            root.invokeOnComponent(context, id, callback);
            if(!inspectForValidity(callback.getComponent())) {
                return false;
            }
        }
        return true;
    }
    
     static public String getPropertyString(UIComponent component,
            String propertyValue, String valueExpressionName) {
        
        ValueExpression ve = component.getValueExpression(valueExpressionName);
        if (ve != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            return (String) ve.getValue(context.getELContext());
        } else {
            return propertyValue;
        }
    }
    
    static public Boolean getPropertyBoolean(UIComponent component,
            Boolean propertyValue, String valueExpressionName) {
   
        ValueExpression ve = component.getValueExpression(valueExpressionName);
        if (ve != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            return (Boolean.TRUE.equals(ve.getValue(
                    context.getELContext())));
        } else {
            return propertyValue;
        }
    }
    
    static public Object getPropertyObject(UIComponent component,
            Object propertyValue, String valueExpressionName) {
        
        ValueExpression ve = component.getValueExpression(valueExpressionName);
        if (ve != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            return ve.getValue(context.getELContext());
        } else {
            return propertyValue;
        }
    }
    
    static public Integer getPropertyInteger(UIComponent component,
            Integer propertyValue, String valueExpressionName) {
        
        ValueExpression ve = component.getValueExpression(valueExpressionName);
        if (ve != null) {
            FacesContext context = FacesContext.getCurrentInstance();
            return (Integer) ve.getValue(context.getELContext());
        } else {
            return propertyValue;
        }
    }
    
    
    static public class AsyncFacesContextCallback implements ContextCallback {
        
        private UIComponent comp;
        
        public void invokeContextCallback(FacesContext context, UIComponent target) {
            comp = target;
        }
        
        public UIComponent getComponent() {
            return comp;
        }
        
    }
}
