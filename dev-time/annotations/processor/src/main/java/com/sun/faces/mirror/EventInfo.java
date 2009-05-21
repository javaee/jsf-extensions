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
 * A base class that defines the basic metadata available for an event set, whether
 * it belongs to a class declared in the current compilation unit, or to a class
 * in a dependant library.
 *
 * @author gjmurphy
 */
public abstract class EventInfo extends FeatureInfo {

    /**
     * Returns the simple name of the method used to add event listeners.
     */
    public abstract String getAddListenerMethodName();

    /**
     * Returns the simple name of the method used to remove event listeners.
     */
    public abstract String getRemoveListenerMethodName();

    /**
     * Returns the simple name of the method used to get event listeners. May be
     * null if there is no such method.
     */
    public abstract String getGetListenersMethodName();
    
    /**
     * Returns the signature of the singleton method defined by this event's listener
     * class or interface.
     */
    public abstract String getListenerMethodSignature();
    
    /**
     * Returns the simple name of the event listener class's singleton method.
     */
    public abstract String getListenerMethodName();
    
    /**
     * Returns an array of the fully qualified names of the singleton listener 
     * method's parameters.
     */
    public abstract String[] getListenerMethodParameterClassNames();
    
    /**
     * Returns the fully qualified name of the event listener class.
     */
    public abstract String getListenerClassName();

    
    private PropertyInfo propertyInfo;

    /**
     * If a property is used to bind to this event, returns the {@code PropertyInfo}
     * instance corresponding to the binding property. Otherwise returns null.
     */
    public PropertyInfo getPropertyInfo() {
        return this.propertyInfo;
    }

    void setPropertyInfo(PropertyInfo propertyInfo) {
        this.propertyInfo = propertyInfo;
    }

}
