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
import java.beans.BeanInfo;
import java.beans.EventSetDescriptor;
import java.beans.MethodDescriptor;
import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a component class or a non-component base class from a dependant library,
 * discovered using introspection.
 *
 * @author gjmurphy
 */
public class IntrospectedClassInfo extends ClassInfo {
    
    BeanInfo beanInfo;
    Map<String, PropertyInfo> propertyInfoMap;
    Map<String, EventInfo> eventInfoMap;
    
    IntrospectedClassInfo(BeanInfo beanInfo) {
        this.beanInfo = beanInfo;
    }
    
    public String getName() {
        return this.beanInfo.getBeanDescriptor().getName();
    }
    
    public String getClassName() {
        return this.beanInfo.getBeanDescriptor().getBeanClass().getSimpleName();
    }
    
    public String getPackageName() {
        return this.beanInfo.getBeanDescriptor().getBeanClass().getPackage().getName();
    }
    
    public BeanInfo getBeanInfo() {
        return beanInfo;
    }
    public ClassInfo getSuperClassInfo() {
        return null;
    }
    
    public boolean isAssignableTo(String qualifiedClassName) {
        try {
            Class superClass = Class.forName(qualifiedClassName);
            if (superClass.isAssignableFrom(this.getBeanInfo().getBeanDescriptor().getBeanClass()))
                return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public Map<String, PropertyInfo> getPropertyInfoMap() {
        return this.propertyInfoMap;
    }
    
    void setPropertyInfoMap(Map<String, PropertyInfo> propertyInfoMap) {
        this.propertyInfoMap = propertyInfoMap;
    }
    
    public Map<String, EventInfo> getEventInfoMap() {
        return this.eventInfoMap;
    }
    
    void setEventInfoMap(Map<String, EventInfo> eventInfoMap) {
        this.eventInfoMap = eventInfoMap;
    }
    
    public PropertyInfo getDefaultPropertyInfo() {
        String defaultPropertyName = null;
        int index = this.beanInfo.getDefaultPropertyIndex();
        if (index >= 0) {
            defaultPropertyName = this.beanInfo.getPropertyDescriptors()[index].getName();
        } else {
            try {
                if (Class.forName("javax.faces.component.ValueHolder").isAssignableFrom(
                        this.getBeanInfo().getBeanDescriptor().getBeanClass()))
                    defaultPropertyName = "value";
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (defaultPropertyName == null)
            return null;
        return this.getPropertyInfoMap().get(defaultPropertyName);
    }
    
    public EventInfo getDefaultEventInfo() {
        String defaultEventName = null;
        int index = this.beanInfo.getDefaultEventIndex();
        if (index >= 0) {
            defaultEventName = this.beanInfo.getEventSetDescriptors()[index].getName();
        } else {
            try {
                Class beanClass = this.getBeanInfo().getBeanDescriptor().getBeanClass();
                if (Class.forName("javax.faces.component.ActionSource").isAssignableFrom(beanClass))
                    defaultEventName = "action";
                else if (Class.forName("javax.faces.component.EditableValueHolder").isAssignableFrom(beanClass))
                    defaultEventName = "valueChange";
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        if (defaultEventName == null)
            return null;
        return this.getEventInfoMap().get(defaultEventName);
    }
    
    Set<String> methodNameSet;
    
    Set<String> getMethodNameSet() {
        if (this.methodNameSet == null) {
            this.methodNameSet = new HashSet<String>();
            for (MethodDescriptor method : this.getBeanInfo().getMethodDescriptors())
                this.methodNameSet.add(method.getName());
        }
        return this.methodNameSet;
    }
    
    Set<CategoryDescriptor> categoryDescriptors;
    
    
    
}
