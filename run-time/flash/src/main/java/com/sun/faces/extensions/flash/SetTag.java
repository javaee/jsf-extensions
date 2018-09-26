/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

/*
 * $Id: SetTag.java,v 1.1 2005/12/14 19:22:25 edburns Exp $
 */

package com.sun.faces.extensions.flash;

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
