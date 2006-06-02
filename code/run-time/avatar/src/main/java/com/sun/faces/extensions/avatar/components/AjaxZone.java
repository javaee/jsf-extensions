package com.sun.faces.extensions.avatar.components;

import javax.faces.component.UICommand;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodInfo;

/**
 * 
 */
public class AjaxZone extends UICommand {
    public AjaxZone() {
        super();
        setRendererType("com.sun.faces.AjaxZone");
    }

    /** 
     * <p>Return the family for this component.</p> 
     */ 
    public String getFamily() { 
        return "com.sun.faces.AjaxZone"; 
    }

    /**
     * <p>Override the <code>UICommand</code> method to wrap the
     * argument <code>MethodExpression</code> so that any return value
     * it may yield from invocation is ignored and <code>null</code> is
     * returned instead.</p>
     */

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

    
}
