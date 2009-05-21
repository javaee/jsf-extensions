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

import java.util.HashMap;
import java.util.Map;

/** 
 * Represents an attribute for a property declared on a class in the current 
 * compilation unit.
 *
 * @author gjmurphy
 */
public class DeclaredAttributeInfo implements AttributeInfo {
        
    static final String NAME = "name";
    static final String IS_REQUIRED = "isRequired";
    static final String IS_BINDABLE = "isBindable";
    
    Map<String,Object> annotationValueMap;
    PropertyInfo parentPropertyInfo;
    
    DeclaredAttributeInfo(PropertyInfo parentPropertyInfo) {
        this(null, parentPropertyInfo);
    }
    
    DeclaredAttributeInfo(AttributeInfo attributeInfo) {
        this.annotationValueMap = new HashMap<String,Object>();
        this.annotationValueMap.put(NAME, attributeInfo.getName());
        this.annotationValueMap.put(IS_REQUIRED, attributeInfo.isRequired());
        this.annotationValueMap.put(IS_BINDABLE, attributeInfo.isBindable());
        this.setDescription(attributeInfo.getDescription());
        this.setMethodSignature(attributeInfo.getMethodSignature());
        if (attributeInfo instanceof DeclaredAttributeInfo)
            this.parentPropertyInfo = ((DeclaredAttributeInfo) attributeInfo).parentPropertyInfo;
    }
    
    DeclaredAttributeInfo(Map<String,Object> annotationValueMap, PropertyInfo parentPropertyInfo) {
        this.annotationValueMap = annotationValueMap;
        this.parentPropertyInfo = parentPropertyInfo;
    }

    public String getName() {
        if (this.annotationValueMap == null)
            return this.parentPropertyInfo.getName();
        String name = (String) this.annotationValueMap.get(NAME);
        return name == null ? this.parentPropertyInfo.getName() : name;
    }

    public boolean isRequired() {
        if (this.annotationValueMap == null)
            return false;
        return Boolean.TRUE.equals(this.annotationValueMap.get(IS_REQUIRED)) ? true : false;
    }

    public boolean isBindable() {
        if (this.annotationValueMap == null)
            return true;
        return Boolean.FALSE.equals(this.annotationValueMap.get(IS_BINDABLE)) ? false : true;
    }

    private String methodSignature;
    
    public String getMethodSignature() {
        return this.methodSignature;
    }
    
    void setMethodSignature(String methodSignature) {
        this.methodSignature = methodSignature;
    }
    
    private String description;
    
    public String getDescription() {
        if (description == null && this.parentPropertyInfo != null) {
            return ((DeclaredPropertyInfo) this.parentPropertyInfo).getDocComment();
        }
        return this.description;
    }
    
    void setDescription(String description) {
        this.description = description;
    }

    public String getWriteMethodName() {
        String name = this.getName();
        return "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
    }
    
}
