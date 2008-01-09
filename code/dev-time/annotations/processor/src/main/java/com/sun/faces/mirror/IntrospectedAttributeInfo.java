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

import com.sun.rave.designtime.markup.AttributeDescriptor;

/**
 * Represents an attribute for a property of a class from a dependant library, 
 * discovered using introspection.
 *
 * @author gjmurphy
 */
public class IntrospectedAttributeInfo implements AttributeInfo {
    
    AttributeDescriptor attributeDescriptor;
    
    IntrospectedAttributeInfo(AttributeDescriptor attributeDescriptor) {
        this.attributeDescriptor = attributeDescriptor;
    }

    public String getName() {
        return this.attributeDescriptor.getName();
    }

    public boolean isBindable() {
        return this.attributeDescriptor.isBindable();
    }

    public boolean isRequired() {
        return this.attributeDescriptor.isRequired();
    }

    public String getMethodSignature() {
        return null;
    }
    
    private String description;
    
    public String getDescription() {
        if (description == null) {
            return "";
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