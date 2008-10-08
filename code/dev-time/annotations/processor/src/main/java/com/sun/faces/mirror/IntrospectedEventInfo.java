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

import java.beans.EventSetDescriptor;
import java.lang.reflect.Method;

/**
 *
 * @author gjmurphy
 */
public class IntrospectedEventInfo extends EventInfo {
    
    EventSetDescriptor eventDescriptor;
    
    IntrospectedEventInfo(EventSetDescriptor eventDescriptor) {
        this.eventDescriptor = eventDescriptor;
    }

    public String getName() {
        return this.eventDescriptor.getName();
    }

    public String getDisplayName() {
        return this.eventDescriptor.getDisplayName();
    }

    public String getShortDescription() {
        return this.eventDescriptor.getShortDescription();
    }

    public String getAddListenerMethodName() {
        if (this.eventDescriptor.getAddListenerMethod() != null)
            return this.eventDescriptor.getAddListenerMethod().getName();
        return null;
    }

    public String getRemoveListenerMethodName() {
        if (this.eventDescriptor.getRemoveListenerMethod() != null)
            return this.eventDescriptor.getRemoveListenerMethod().getName();
        return null;
    }

    public String getGetListenersMethodName() {
        if (this.eventDescriptor.getGetListenerMethod() != null)
            return this.eventDescriptor.getGetListenerMethod().getName();
        return null;
    }
    
    String listenerMethodSignature;

    public String getListenerMethodSignature() {
        if (this.listenerMethodSignature == null) {
            StringBuffer buffer = new StringBuffer();
            Method listenerMethod = this.eventDescriptor.getListenerMethods()[0];
            buffer.append(listenerMethod.getReturnType().toString());
            buffer.append(" ");
            buffer.append(listenerMethod.getName());
            buffer.append("(");
            for (Class paramClass : listenerMethod.getParameterTypes()) {
                buffer.append(paramClass.getName());
                buffer.append(",");
            }
            buffer.setLength(buffer.length() - 1);
            buffer.append(")");
            this.listenerMethodSignature = buffer.toString();
        }
        return this.listenerMethodSignature;
    }

    public String getListenerClassName() {
        return this.eventDescriptor.getListenerType().getName();
    }
    
    String[] listenerMethodParameterClassNames;
    
    public String[] getListenerMethodParameterClassNames() {
        if (listenerMethodParameterClassNames == null) {
            Class[] paramClasses = 
                    this.eventDescriptor.getListenerMethods()[0].getParameterTypes();
            listenerMethodParameterClassNames = new String[paramClasses.length];
            for (int i = 0; i < paramClasses.length; i++)
                listenerMethodParameterClassNames[i] = paramClasses[i].getName();
        }
        return listenerMethodParameterClassNames;
    }

    public String getListenerMethodName() {
        return this.eventDescriptor.getListenerMethods()[0].getName();
    }

    public boolean isHidden() {
        return this.eventDescriptor.isHidden();
    }

}
