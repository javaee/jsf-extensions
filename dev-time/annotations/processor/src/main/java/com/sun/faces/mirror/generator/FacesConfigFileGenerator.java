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

import com.sun.faces.mirror.DeclaredComponentInfo;
import com.sun.faces.mirror.DeclaredRendererInfo;
import java.util.Set;

/**
 * Base generator for the faces configuration file.
 *
 * @author gjmurphy
 */
abstract public class FacesConfigFileGenerator extends FileGenerator {

    private Set<DeclaredComponentInfo> declaredComponentInfoSet;

    /**
     * Protected getter for property declaredComponentInfoSet.
     */
    protected Set<DeclaredComponentInfo> getDeclaredComponentInfoSet() {
        return this.declaredComponentInfoSet;
    }

    /**
     * Setter for property declaredComponentInfoSet.
     */
    public void setDeclaredComponentInfoSet(Set<DeclaredComponentInfo> declaredComponentInfoSet) {
        this.declaredComponentInfoSet = declaredComponentInfoSet;
    }

    private Set<DeclaredRendererInfo> declaredRendererInfoSet;

    /**
     * Protected getter for property declaredRendererInfoSet.
     */
    protected Set<DeclaredRendererInfo> getDeclaredRendererInfoSet() {
        return this.declaredRendererInfoSet;
    }

    /**
     * Setter for property declaredRendererInfoSet.
     */
    public void setDeclaredRendererInfoSet(Set<DeclaredRendererInfo> declaredRendererInfoSet) {
        this.declaredRendererInfoSet = declaredRendererInfoSet;
    }

    private Set<String> declaredPropertyResolverNameSet;

    /**
     * Protected getter for property declaredPropertyResolverNameSet.
     * @return Value of property declaredPropertyResolverNameSet.
     */
    protected Set<String> getDeclaredPropertyResolverNameSet() {
        return this.declaredPropertyResolverNameSet;
    }

    /**
     * Setter for property declaredPropertyResolverNameSet.
     * @param declaredPropertyResolverNameSet New value of property declaredPropertyResolverNameSet.
     */
    public void setDeclaredPropertyResolverNameSet(Set<String> declaredPropertyResolverNameSet) {
        this.declaredPropertyResolverNameSet = declaredPropertyResolverNameSet;
    }
    

    private Set<String> declaredVariableResolverNameSet;

    /**
     * Protected getter for Variable declaredVariableResolverNameSet.
     * @return Value of Variable declaredVariableResolverNameSet.
     */
    protected Set<String> getDeclaredVariableResolverNameSet() {
        return this.declaredVariableResolverNameSet;
    }

    /**
     * Setter for Variable declaredVariableResolverNameSet.
     * @param declaredVariableResolverNameSet New value of Variable declaredVariableResolverNameSet.
     */
    public void setDeclaredVariableResolverNameSet(Set<String> declaredVariableResolverNameSet) {
        this.declaredVariableResolverNameSet = declaredVariableResolverNameSet;
    }

    
    private Set<String> declaredJavaEEResolverNameSet;

    /**
     * Protected getter for JavaEE declaredJavaEEResolverNameSet.
     * @return Value of JavaEE declaredJavaEEResolverNameSet.
     */
    protected Set<String> getDeclaredJavaEEResolverNameSet() {
        return this.declaredJavaEEResolverNameSet;
    }

    /**
     * Setter for JavaEE declaredJavaEEResolverNameSet.
     * @param declaredJavaEEResolverNameSet New value of JavaEE declaredJavaEEResolverNameSet.
     */
    public void setDeclaredJavaEEResolverNameSet(Set<String> declaredJavaEEResolverNameSet) {
        this.declaredJavaEEResolverNameSet = declaredJavaEEResolverNameSet;
    }
    
}
