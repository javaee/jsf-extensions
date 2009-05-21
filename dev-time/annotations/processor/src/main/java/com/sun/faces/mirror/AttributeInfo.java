/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.faces.mirror;

/**
 * Defines the basic metadata available for the JSP tag attribute that corresponds 
 * to a property. See {@link PropertyInfo#getAttribute}.
 *
 * @author gjmurphy
 */
public interface AttributeInfo {
    
    /**
     * This attribute's name, which is guaranteed to be unique among all attribute
     * names within the scope of the containing component. See 
     * {@link com.sun.faces.annotation.Attribute#name}.
     */
    public String getName();

    /**
     * Returns true if this attribute is required in the JSP. See 
     * {@link com.sun.faces.annotation.Attribute#isRequired}.
     */
    public boolean isRequired();
    
    /**
     * Returns true if this attribute is bindable. See 
     * {@link com.sun.faces.annotation.Attribute#isBindable}.
     */
    public boolean isBindable();
    
    /**
     * If this attribute corresponds to a property of type {@link javax.el.MethodExpression},
     * then this method will return the signature of the method to which the expression
     * should be bound.
     */
    public abstract String getMethodSignature();
    
    /**
     * Returns the description of this attribute, appropriate for use as a description
     * element in the taglib configuration. If no tag description was set, returns the
     * doc comment of this attribute's property.
     */
    public String getDescription();
    
    /**
     * Returns the name of the method used to set the attribute value in the tag
     * handler class.
     */
    public String getWriteMethodName();
    
}
