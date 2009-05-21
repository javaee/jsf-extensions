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

package com.sun.faces.mirror.generator;

import com.sun.faces.mirror.PropertyBundleMap;

/**
 * Base generator for Java source files. A source generator must provide the
 * class name and package name of the class for which a source file will be generated.
 *
 * @author gjmurphy
 */
abstract public class SourceGenerator extends Generator {
    
    /**
     * Read-only property by which this source generator reports the name of
     * class to which it should write.
     */
    abstract public String getClassName();
    
    /**
     * Read-only property by which this source generator reports the name of the
     * package in which its class should be located.
     */
    abstract public String getPackageName();
    
    /**
     * Read-only property that returns the fully qualified name of the generated
     * class.
     */
    public String getQualifiedName() {
        return this.getPackageName() + "." + this.getClassName();
    }

    private PropertyBundleMap propertyBundleMap;

    /**
     * Protected getter for property propertyBundleWriter.
     */
    protected PropertyBundleMap getPropertyBundleMap() {
        return this.propertyBundleMap;
    }

    /**
     * Setter for property propertyBundleWriter. The annotation processor will set the 
     * property bundle print writer before each call to {@link #generate}, if annotation
     * processing was invoked with the localization option.
     */
    public void setPropertyBundleMap(PropertyBundleMap propertyBundleMap) {
        this.propertyBundleMap = propertyBundleMap;
    }
    
}
