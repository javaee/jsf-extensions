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

//import com.sun.rave.designtime.markup.AttributeDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Represents an attribute for a property of a class from a dependant library, 
 * discovered using introspection.
 *
 * @author gjmurphy
 */
public class IntrospectedAttributeInfo implements AttributeInfo {

    Object attributeDescriptor;

    IntrospectedAttributeInfo(Object attributeDescriptor) {
        this.attributeDescriptor = attributeDescriptor;
    }

    public String getName() {
        String ret = null;
        try {
            Method getName = attributeDescriptor.getClass().getDeclaredMethod("getName", (Class[])null);
            ret = (String) getName.invoke(attributeDescriptor, (Object[])null);
        } catch (Exception ex) {
            Logger.getLogger(IntrospectedAttributeInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public boolean isBindable() {
        Boolean ret = null;
        try {
            Method isBindable = attributeDescriptor.getClass().getDeclaredMethod("isBindable", (Class[])null);
            ret = (Boolean) isBindable.invoke(attributeDescriptor, (Object[])null);
        } catch (Exception ex) {
            Logger.getLogger(IntrospectedAttributeInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
    }

    public boolean isRequired() {
        Boolean ret = null;
        try {
            Method isRequired = attributeDescriptor.getClass().getDeclaredMethod("isRequired", (Class[])null);
            ret = (Boolean) isRequired.invoke(attributeDescriptor, (Object[])null);
        } catch (Exception ex) {
            Logger.getLogger(IntrospectedAttributeInfo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return ret;
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
