
/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 1997-2010 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://glassfish.dev.java.net/public/CDDL+GPL_1_1.html
 * or packager/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at packager/legal/LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

package com.sun.faces.jsf_extensions_javajsf;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.facelets.Facelet;
import javax.faces.view.facelets.FaceletFactory;


public abstract class Application  {
    
    private static final Logger LOGGER = JavaJSFLogger.VDL.getLogger();
    
    private javax.faces.application.Application jsfApplication;
    private ViewDeclarationLanguage jsfVDL;
    private ResourceHandler resourceHandler;

    public void setJsfApplication(javax.faces.application.Application jsfApplication) {
        this.jsfApplication = jsfApplication;
        this.resourceHandler = jsfApplication.getResourceHandler();
    }
    
    public javax.faces.application.Application getJsfApplication() {
        return this.jsfApplication;
    }

    public ViewDeclarationLanguage getJsfViewDeclarationLanguage() {
        return jsfVDL;
    }

    public void setJsfViewDeclarationLanguage(ViewDeclarationLanguage jsfVDL) {
        this.jsfVDL = jsfVDL;
    }
    
    

    private UIComponent mainWindow;

    public UIComponent getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(UIComponent mainWindow) {
        this.mainWindow = mainWindow;
    }

    private Map<UIComponent, String> windows;

    public abstract void init();
    
    public abstract void destroy();
    
    public UIComponent createComponent(String componentType) {
        UIComponent result = createComponent(componentType, null);
        
        return result;
    }
    
    public UIComponent createComponent(String componentType, String rendererType) {
        UIComponent result = null;
        FacesContext context = FacesContext.getCurrentInstance();
        
        try{
            result = useFaceletsToCreateComponent(context, componentType, null);
            result.setId(context.getViewRoot().createUniqueId());
        } catch (FacesException fe) {
        }
                
        return result;
    }
    
    public UIComponent createFaceletsComponent(String taglibUri, String tagName) {
        UIComponent result = createFaceletsComponent(taglibUri, tagName, Collections.EMPTY_MAP);
        
        return result;
    }
    
    public UIComponent createFaceletsComponent(String taglibUri, String tagName,
            Map<String,Object> attrs) {
        UIComponent result = null;
        FacesContext context = FacesContext.getCurrentInstance();
        
        FaceletFactory faceletFactory = (FaceletFactory)
                FactoryFinder.getFactory(FactoryFinder.FACELET_FACTORY);
        
        result = faceletFactory.createComponent(taglibUri,
                tagName, attrs);
        
        result.setId(context.getViewRoot().createUniqueId());
        return result;
        
    }


    
    // <editor-fold defaultstate="collapsed" desc="Helper methods">
    
    private UIComponent useFaceletsToCreateComponent(FacesContext context, String componentType, String rendererType) {
        FaceletFactory faceletFactory = (FaceletFactory)
                FactoryFinder.getFactory(FactoryFinder.FACELET_FACTORY);
        Facelet usingPageFacelet = null;
        Map contextAttrs = context.getAttributes();
        final String componentTypeAttrName = "com.sun.faces.jsf_extensions_javajsf.componentTypeAttrName";
        final String rendererTypeAttrName = "com.sun.faces.jsf_extensions_javajsf.rendererTypeAttrName";
        contextAttrs.put(componentTypeAttrName, componentType);
        contextAttrs.put(rendererTypeAttrName, rendererType);
        UIComponent result = null;
        
        try {
            usingPageFacelet = faceletFactory.getFacelet("/javajsf_using.xhtml");
            UIComponent tmp = (UIComponent)
                    jsfApplication.createComponent("javax.faces.NamingContainer");
            tmp.setId(context.getViewRoot().createUniqueId());
            usingPageFacelet.apply(context, tmp);
                result = tmp.findComponent("javajsf_c");
            tmp.getChildren().clear();
        } catch (Exception ioe) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                Object [] params = {
                    componentType + " " + rendererType, ioe
                };
                LOGGER.log(Level.SEVERE, "javajsf.cannot_create_composite_component", params);
                LOGGER.log(Level.SEVERE, "", ioe);
            }
        } finally {
            contextAttrs.remove(componentTypeAttrName);
            contextAttrs.remove(rendererTypeAttrName);
        }
        return result;
    }
    
    
    // </editor-fold>
    

}
