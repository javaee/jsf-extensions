
/*
 * $Id: SetTag.java,v 1.1 2005/12/14 19:22:25 edburns Exp $
 */

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or
 * legal/CDDLv1.0.txt.
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * [Name of File] [ver.__] [Date]
 *
 * Copyright 2005 Sun Microsystems Inc. All Rights Reserved
 */


package com.sun.faces.taglib.jsf_extensions;

import javax.el.ELContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * <p>A very simple tag that allows storing values into
 * <code>ValueExpression</code> from JSP.</p>
 *
 * <p>Usage</p>
 *
 * <p><code>&lt;jsfExt:set var="\#{flash.foo}" value="fooValue" /&gt;</code></p>
 * <p>But of course value can also be an expression.</p>
 * 
 *
 * @author edburns
 */
public class SetTag extends TagSupport {
    
    /** Creates a new instance of SetTag */
    public SetTag() {
    }

    /**
     * Holds value of property var.
     */
    private javax.el.ValueExpression var;

    /**
     * Setter for property var.
     * @param var New value of property var.
     */
    public void setVar(javax.el.ValueExpression var) {

        this.var = var;
    }

    /**
     * Holds value of property value.
     */
    private javax.el.ValueExpression value;

    /**
     * Setter for property value.
     * @param value New value of property value.
     */
    public void setValue(javax.el.ValueExpression value) {

        this.value = value;
    }
    
    public int doEndTag() throws JspException {
        Object result = null;
        ELContext elContext = pageContext.getELContext();
        
        result = value.getValue(elContext);
        var.setValue(elContext, result);
        
        return (EVAL_PAGE);
    }

    
}
