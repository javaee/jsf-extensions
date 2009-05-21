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

import com.sun.mirror.declaration.MethodDeclaration;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.type.InterfaceType;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Represents a class or interface declared in the current compilation unit.
 *
 * @author gjmurphy
 */
public abstract class DeclaredTypeInfo extends ClassInfo {
    
    TypeDeclaration decl;
    
    DeclaredTypeInfo(TypeDeclaration decl) {
        this.decl = decl;
        String qualifiedName = this.decl.getQualifiedName();
        int index = qualifiedName.lastIndexOf('.');
        if (index >= 0) {
            this.className = qualifiedName.substring(index + 1);
            this.packageName = qualifiedName.substring(0, index);
        } else {
            this.className = qualifiedName;
            this.packageName = null;
        }
    }
    
    public TypeDeclaration getDeclaration() {
        return this.decl;
    }
    
    private String className;
    
    public String getClassName() {
        return this.className;
    }
    
    private String packageName;
    
    public String getPackageName() {
        return this.packageName;
    }
    
    private ClassInfo superClassInfo;
    
    public ClassInfo getSuperClassInfo() {
        return this.superClassInfo;
    }
    
    void setSuperClassInfo(ClassInfo classInfo) {
        this.superClassInfo = classInfo;
    }

    public boolean isAssignableTo(String qualifiedName) {
        TypeDeclaration decl = this.getDeclaration();
        if (decl.getQualifiedName().equals(qualifiedName))
            return true;
        for (InterfaceType interfaceType : decl.getSuperinterfaces()) {
            if (interfaceType.getDeclaration().getQualifiedName().equals(qualifiedName))
                return true;
        }
        return false;
    }
    
    Map<String, PropertyInfo> propertyInfoMap;
    
    /**
     * Returns a map of all properties declared explicitly in this class or interface.
     */
    public Map<String, PropertyInfo> getPropertyInfoMap() {
        return this.propertyInfoMap;
    }
    
    void setPropertyInfoMap(Map<String, PropertyInfo> propertyInfoMap) {
        this.propertyInfoMap = propertyInfoMap;
        for (PropertyInfo propertyInfo : propertyInfoMap.values()) {
            DeclaredPropertyInfo declaredPropertyInfo = (DeclaredPropertyInfo) propertyInfo;
            declaredPropertyInfo.setDeclaringClassInfo(this);
            if (Boolean.TRUE.equals(declaredPropertyInfo.annotationValueMap.get("isDefault")))
                this.setDefaultPropertyInfo(declaredPropertyInfo);
        }
    }
    
    Map<String, EventInfo> eventInfoMap;
    
    /**
     * Returns a map of all events declared explicitly in this class or interface.
     */
    public Map<String, EventInfo> getEventInfoMap() {
        return this.eventInfoMap;
    }
    
    void setEventInfoMap(Map<String, EventInfo> eventInfoMap) {
        this.eventInfoMap = eventInfoMap;
        for (EventInfo eventInfo : eventInfoMap.values()) {
            DeclaredEventInfo declaredEventInfo = (DeclaredEventInfo) eventInfo;
            declaredEventInfo.setDeclaringClassInfo(this);
            if (Boolean.TRUE.equals(declaredEventInfo.annotationValueMap.get("isDefault")))
                this.setDefaultEventInfo(declaredEventInfo);
        }
    }
    
    private PropertyInfo defaultPropertyInfo;
    
    public PropertyInfo getDefaultPropertyInfo() {
        return this.defaultPropertyInfo;
    }
    
    void setDefaultPropertyInfo(PropertyInfo defaultPropertyInfo) {
        this.defaultPropertyInfo = defaultPropertyInfo;
    }
    
    private EventInfo defaultEventInfo;
    
    public EventInfo getDefaultEventInfo() {
        return this.defaultEventInfo;
    }
    
    void setDefaultEventInfo(EventInfo defaultEventInfo) {
        this.defaultEventInfo = defaultEventInfo;
    }
    
    Set<String> methodNameSet;
    
    Set<String> getMethodNameSet() {
        if (this.methodNameSet == null) {
            this.methodNameSet = new HashSet<String>();
            for (MethodDeclaration decl : this.decl.getMethods())
                this.methodNameSet.add(decl.getSimpleName());
        }
        return this.methodNameSet;
    }
    
    /**
     * Returns the JavaDoc comments associated with the type declaration.
     */
    public String getDocComment() {
        return this.getDeclaration().getDocComment();
    }
    
}
