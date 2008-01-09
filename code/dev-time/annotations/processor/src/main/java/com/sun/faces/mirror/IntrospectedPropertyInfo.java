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

import com.sun.rave.designtime.CategoryDescriptor;
import com.sun.rave.designtime.Constants;
import com.sun.rave.designtime.markup.AttributeDescriptor;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;

/**
 * Represents a property of a class from a dependant library, discovered using
 * introspection.
 *
 * @author gjmurphy
 */
public class IntrospectedPropertyInfo extends PropertyInfo {
    
    PropertyDescriptor propertyDescriptor;
    
    IntrospectedPropertyInfo(PropertyDescriptor propertyDescriptor) {
        this.propertyDescriptor = propertyDescriptor;
    }
    
    public PropertyDescriptor getPropertyDescriptor() {
        return this.propertyDescriptor;
    }

    public String getName() {
        return this.propertyDescriptor.getName();
    }
    
    public String getInstanceName() {
        String name = this.getName();
        if (PropertyInfo.JAVA_KEYWORD_PATTERN.matcher(name).matches())
            return "_" + name;
        return name;
    }

    public String getType() {
        return this.propertyDescriptor.getPropertyType().getName();
    }

    public String getWriteMethodName() {
        Method method = this.propertyDescriptor.getWriteMethod();
        if (method == null)
            return null;
        return method.getName();
    }

    public String getReadMethodName() {
        Method method = this.propertyDescriptor.getReadMethod();
        if (method == null)
            return null;
        return method.getName();
    }

    public String getShortDescription() {
        return this.propertyDescriptor.getShortDescription();
    }

    public String getDisplayName() {
        return this.propertyDescriptor.getDisplayName();
    }
    
    public boolean isHidden() {
        return this.propertyDescriptor.isHidden();
    }

    public String getEditorClassName() {
        Class editorClass = this.propertyDescriptor.getPropertyEditorClass();
        if (editorClass == null)
            return null;
        return editorClass.getName();
    }
    
    private CategoryInfo categoryInfo;
    
    public CategoryInfo getCategoryInfo() {
        return this.categoryInfo;
    }
    
    void setCategoryInfo(CategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }
    
    String getCategoryReferenceName() {
        if (this.propertyDescriptor.getValue(Constants.PropertyDescriptor.CATEGORY) != null)
            return ((CategoryDescriptor) this.propertyDescriptor.getValue(Constants.PropertyDescriptor.CATEGORY)).getName();
        return null;
    }
    
    public boolean equals(Object obj) {
        if (!(obj instanceof PropertyInfo))
            return false;
        PropertyInfo that = (PropertyInfo) obj;
        if (!this.getName().equals(that.getName()))
            return false;
        String thisReadName = this.getReadMethodName();
        String thatReadName = that.getReadMethodName();
        if (thisReadName != null && (thatReadName == null || !thatReadName.equals(thisReadName)))
            return false;
        if (thatReadName == null && thatReadName != null)
            return false;
        String thisWriteName = this.getReadMethodName();
        String thatWriteName = that.getReadMethodName();
        if (thisWriteName != null && (thatWriteName == null || !thatWriteName.equals(thisWriteName)))
            return false;
        if (thatWriteName == null && thatWriteName != null)
            return false;
        return true;
    }

    public AttributeInfo getAttributeInfo() {
        AttributeDescriptor attributeDescriptor =
                (AttributeDescriptor) this.propertyDescriptor.getValue(Constants.PropertyDescriptor.ATTRIBUTE_DESCRIPTOR);
        if (attributeDescriptor == null)
            return null;
        return new IntrospectedAttributeInfo(attributeDescriptor);
    }
    
}
