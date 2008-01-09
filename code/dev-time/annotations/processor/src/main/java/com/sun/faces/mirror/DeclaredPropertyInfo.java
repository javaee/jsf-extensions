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

import com.sun.faces.annotation.Property;
import com.sun.mirror.declaration.Declaration;
import java.util.Map;

/**
 * Represents a property declared on a component class or non-component base
 * class, in the current compilation unit.
 *
 * @author gjmurphy
 */
public class DeclaredPropertyInfo extends PropertyInfo {
    
    static final String NAME = "name";
    static final String DISPLAY_NAME = "displayName";
    static final String SHORT_DESCRIPTION = "shortDescription";
    static final String EDITOR_CLASS_NAME = "editorClassName";
    static final String HELP_KEY = "helpKey";
    static final String CATEGORY = "category";
    static final String IS_DEFAULT = "isDefault";
    static final String IS_ATTRIBUTE = "isAttribute";
    static final String IS_HIDDEN = "isHidden";
    static final String ATTRIBUTE = "attribute";
    static final String READ_METHOD_NAME = "readMethodName";
    static final String WRITE_METHOD_NAME = "writeMethodName";
    
    Declaration decl;
    Map<String,Object> annotationValueMap;
    
    DeclaredPropertyInfo(Map<String,Object> annotationValueMap, Declaration decl) {
        this.annotationValueMap = annotationValueMap;
        this.decl = decl;
    }
    
    public Declaration getDeclaration() {
        return this.decl;
    }
    
    private String name;
    
    public String getName() {
        return name;
    }
    
    void setName(String name) {
        this.name = name;
    }
    
    public String getInstanceName() {
        String name = this.getName();
        if (PropertyInfo.JAVA_KEYWORD_PATTERN.matcher(name).matches())
            return "_" + name;
        return name;
    }
    
    public String getDisplayName() {
        String displayName = (String) this.annotationValueMap.get(DISPLAY_NAME);
        if (displayName == null)
            displayName = this.getName();
        return displayName;
    }
    
    public String getShortDescription() {
        String shortDescription = (String) this.annotationValueMap.get(SHORT_DESCRIPTION);
        if (shortDescription == null) {
            String comment = this.getDocComment();
            if (comment == null || comment.trim().length() == 0) {
                shortDescription = this.getDisplayName();
            } else {
                char[] chars = comment.toCharArray();
                StringBuffer buffer = new StringBuffer();
                int index = 0;
                while (index < chars.length && Character.isSpaceChar(chars[index]))
                    index++;
                while (index < chars.length) {
                    if (chars[index] == '<') {
                        index++;
                        while (index < chars.length && chars[index] != '>')
                            index++;
                    } else if (chars[index] == '\n') {
                        buffer.append(" ");
                    } else if (chars[index] == '"') {
                        buffer.append("&quot;");
                    } else if (chars[index] == '.') {
                        if (index == chars.length - 1 || Character.isSpaceChar(chars[index+1]))
                            break;
                        buffer.append('.');
                    } else {
                        buffer.append(chars[index]);
                    }
                    index++;
                }
                shortDescription = buffer.toString();
            }
        }
        return shortDescription;
    }
    
    private String type;
    
    public String getType() {
        return this.type;
    }
    
    void setType(String type) {
        this.type = type;
    }
    
    private String writeMethodName;
    
    public String getWriteMethodName() {
        String methodName = this.writeMethodName;
        if (methodName == null)
            methodName = (String) this.annotationValueMap.get(WRITE_METHOD_NAME);
        return methodName;
    }
    
    void setWriteMethodName(String writeMethodName) {
        this.writeMethodName = writeMethodName;
    }
    
    private String readMethodName;
    
    public String getReadMethodName() {
        String methodName = this.readMethodName;
        if (methodName == null)
            methodName = (String) this.annotationValueMap.get(READ_METHOD_NAME);
        return methodName;
    }
    
    void setReadMethodName(String readMethodName) {
        this.readMethodName = readMethodName;
    }
    
    public String getEditorClassName() {
        return (String) this.annotationValueMap.get(EDITOR_CLASS_NAME);
    }
    
    public boolean isHidden() {
        if (this.annotationValueMap.containsKey(IS_HIDDEN))
            return (Boolean) this.annotationValueMap.get(IS_HIDDEN);
        return false;
    }
    
    private CategoryInfo categoryInfo;
    
    public CategoryInfo getCategoryInfo() {
        return this.categoryInfo;
    }
    
    void setCategoryInfo(CategoryInfo categoryInfo) {
        this.categoryInfo = categoryInfo;
    }
    
    String getCategoryReferenceName() {
        return (String) this.annotationValueMap.get(CATEGORY);
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
    
    private AttributeInfo attributeInfo;
    
    public AttributeInfo getAttributeInfo() {
        if (this.attributeInfo == null) {
            if (Boolean.FALSE.equals(this.annotationValueMap.get(IS_ATTRIBUTE)))
                return null;
            if (this.annotationValueMap.containsKey(ATTRIBUTE))
                this.attributeInfo = new DeclaredAttributeInfo(
                        (Map<String,Object>) this.annotationValueMap.get(ATTRIBUTE), this);
            else
                this.attributeInfo = new DeclaredAttributeInfo(this);
        }
        return this.attributeInfo;
    }
    
    void setAttributeInfo(AttributeInfo attributeInfo) {
        this.attributeInfo = attributeInfo;
    }
    
    /**
     * Update any missing values in this property info with the values in the property
     * info specified. Values will be copies only if they correspond to annotation
     * elements, and only if the elements where not explicitly declared for this
     * property. Also, if a read or write method is inherited but not overriden, the
     * method names will be copied.
     */
    void updateInheritedValues(PropertyInfo propertyInfo) {
        if (!this.annotationValueMap.containsKey(DISPLAY_NAME))
            this.annotationValueMap.put(DISPLAY_NAME, propertyInfo.getDisplayName());
        if (!this.annotationValueMap.containsKey(SHORT_DESCRIPTION) && this.getDocComment() == null)
            this.annotationValueMap.put(SHORT_DESCRIPTION, propertyInfo.getShortDescription());
        if (!this.annotationValueMap.containsKey(EDITOR_CLASS_NAME))
            this.annotationValueMap.put(EDITOR_CLASS_NAME, propertyInfo.getEditorClassName());
        if (!this.annotationValueMap.containsKey(IS_HIDDEN))
            this.annotationValueMap.put(IS_HIDDEN, propertyInfo.isHidden());
        if (!this.annotationValueMap.containsKey(CATEGORY))
            this.annotationValueMap.put(CATEGORY, propertyInfo.getCategoryReferenceName());
        if (!this.annotationValueMap.containsKey(IS_ATTRIBUTE)) {
            AttributeInfo attributeInfo = propertyInfo.getAttributeInfo();
            if (attributeInfo == null) {
                this.annotationValueMap.put(IS_ATTRIBUTE, Boolean.FALSE);
                this.attributeInfo = null;
            } else {
                this.annotationValueMap.put(IS_ATTRIBUTE, Boolean.TRUE);
                this.attributeInfo = new DeclaredAttributeInfo(attributeInfo);
                if (this.getDocComment() != null)
                    ((DeclaredAttributeInfo) this.attributeInfo).setDescription(this.getDocComment());
            }
        }
        if (getReadMethodName() == null)
            setReadMethodName(propertyInfo.getReadMethodName());
        if (getWriteMethodName() == null)
            setWriteMethodName(propertyInfo.getWriteMethodName());
        if (getCategoryInfo() == null && getCategoryReferenceName() != null)
            this.setCategoryInfo(propertyInfo.getCategoryInfo());
    }
    
    /**
     * Returns the JavaDoc comments associated with the member field or method
     * where this property was defined.
     */
    public String getDocComment() {
        return this.getDeclaration().getDocComment();
    }
    
}
