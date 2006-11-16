/*
 *
 * Created on 14 novembre 2006, 15.37
 */

package org.asyncfaces.taglib;

import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author agori
 */
public class TagUtils {
    
   static public void setProperty(FacesContext context, UIComponent component,
            String name, ValueExpression ve) {
        
        if (null != ve) {
            if (ve.isLiteralText()) {
                component.getAttributes().put(name, ve.getValue(context.getELContext()));
            }
            else {
                component.setValueExpression(name, ve);
            }
        }
    }
    
}
