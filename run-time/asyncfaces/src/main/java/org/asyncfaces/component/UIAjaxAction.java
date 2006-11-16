/*
 *
 * Created on 14 novembre 2006, 13.33
 */

package org.asyncfaces.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.faces.component.UICommand;
import javax.faces.component.UIComponent;
import org.asyncfaces.Target;

/**
 *
 * @author agori
 */
public class UIAjaxAction extends UICommand implements Serializable {
    
    public static final String COMPONENT_TYPE = "org.asyncfaces.Ajax";

    public String getFamily() {
        return "org.asyncfaces.Ajax";
    }
    

    
    public void setActionExpression(final MethodExpression actionExpression) {
        MethodExpression me = new MethodExpression() {
            public boolean equals(Object other) {
                return actionExpression.equals(other);
            }
            
            public String getExpressionString() {
                return actionExpression.getExpressionString();
            }
            
            public int hashCode() {
                return actionExpression.hashCode();
            }
            
            public boolean isLiteralText() {
                return actionExpression.isLiteralText();
            }
            
            public MethodInfo getMethodInfo(ELContext context) {
                return actionExpression.getMethodInfo(context);
            }
            
            public Object invoke(ELContext context,
                    Object[] params) {
                actionExpression.invoke(context, params);
                return null;
            }
        };
        
        super.setActionExpression(me);
    }
    
    public List<Target> buildTargets() {
        List<Target> targets = new ArrayList<Target>();
        
        for (UIComponent comp : getChildren()) { 
            if (comp instanceof UITarget) {
                UITarget t = (UITarget) comp;
                targets.add(t.buildTarget());
            }
        }
        
        return targets;
    }
  
}
