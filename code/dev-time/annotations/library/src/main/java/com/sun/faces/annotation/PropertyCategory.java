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
 * Annotation that identifies a public field as being a property category instance.
 * The field must be assignable from {@link com.sun.rave.designtime.CategoryDescriptor}.
 * Property annotations may reference annotated property categories. For example,
 * the property annotation
 *
 * <pre>
 *    &#64;Property(name="myProperty",category="myCategory")
 *    private String myProperty;
 * </pre>
 *
 * contains a reference to the property category whose name is {@code myCategory},
 * which might be defined elsewhere as
 *
 * <pre>
 *    &#64;PropertyCategory("myCategory")
 *    public static final CategoryDescriptor MYCATEGORY = 
 *        new CategoryDescriptor("myCategory", "My Category", false);
 * </pre>
 * 
 * {@code PropertyCategory} must be used to annotate only publicly accessible fields
 * or static methods of type {@link com.sun.rave.designtime.CategoryDescriptor}.
 *
 * @author gjmurphy
 */
@Retention(RetentionPolicy.SOURCE)
@Target(ElementType.FIELD)
public @interface PropertyCategory {
    
    /**
     * This category's unique name. The name must be unique within the scope of
     * the current compilation unit. This name should generally be exactly
     * equal to the value of {@code CategoryDescriptor.getName()}. If it is not,
     * category descriptors inherited from external libraries will not be comparable
     * to category descriptors defined in the current compilation unit.
     */
    public String name();
    
    /**
     * This category's sort key, used to determine the order in which this 
     * category should appear in a list of categories. If not specified, the sort
     * key defaults to the value of {@link #name}.
     *
     * <p>The collation sequence used for ordering is not specified.
     */
    public String sortKey() default "";
    
}
