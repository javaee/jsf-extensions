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

package com.sun.faces.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation that groups together information about the JSP attribute for a
 * component property. Instances of this annotation may be used in two ways: as a
 * value for {@link Property#attribute}, to specify non-default attribute settings
 * as part of a property annotation within a JSF component class; or, to annotate
 * directly the attribute setter methods in a JSP tag class.
 *
 * @author gjmurphy
 */
@Retention(RetentionPolicy.SOURCE)
@Target({ElementType.ANNOTATION_TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface Attribute {
    
    /**
     * This attribute's unique name. The name must be unique within the scope of
     * the attribute's component class. If no value is specified, then the name
     * of the containing property will be used.
     */
    public String name() default "";
    
    /**
     * Indicates whether the value of this attribute may be a value-binding expression.
     * If the value of this element is false, the type of the attribute will be
     * set to the property type. If the value is true, it will be set to 
     * {@link javax.el.ValueExpression}.
     */
    public boolean isBindable() default true;
    
    /**
     * Indicates whether a value is required for this attribute.
     */
    public boolean isRequired() default false;
}
