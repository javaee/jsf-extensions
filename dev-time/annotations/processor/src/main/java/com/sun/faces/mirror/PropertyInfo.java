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

import java.util.regex.Pattern;

/**
 * A base class that defines the basic metadata available for a property, whether
 * it belongs to a class declared in the current compilation unit, or to a class
 * in a dependant library.
 *
 * @author gjmurphy
 */
public abstract class PropertyInfo extends FeatureInfo {
    
    /**
     * Returns the name of this property as a Java instance name. This will usually
     * be the value of {@link #getName), unless that value equals a Java keyword
     * or reserverd word, in which case a "_" character will be prepended to the name.
     */
    public abstract String getInstanceName();

    /**
     * Returns the fully qualified type name of this property.
     */
    public abstract String getType();

    /**
     * Returns the simple name of this property's read method. May be null if 
     * property is write-only or if the property is inherited and the read method 
     * is defined in the super class.
     */
    public abstract String getReadMethodName();

    /**
     * Returns the simple name of this property's write method. May be null if 
     * property is read-only or if the property is inherited and the write method 
     * is defined in the super class.
     */
    public abstract String getWriteMethodName();
    
    /**
     * Returns the fully qualified type name of a property editor class to be used
     * for editing this property. If no editor was assigned to this property, 
     * returns null.
     */
    public abstract String getEditorClassName();
    
    /**
     * Returns the category info for this property, or null if this property is
     * uncategorized.
     */
    public abstract CategoryInfo getCategoryInfo();
    
    
    abstract String getCategoryReferenceName();
    
    /**
     * Returns the attribute info for this property, or null if this property does
     * not correspond to an attribute.
     */
    public abstract AttributeInfo getAttributeInfo();

}
