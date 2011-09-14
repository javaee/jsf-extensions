
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

import java.util.List;
import java.util.Map;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;


public abstract class Application  {

    private javax.faces.application.Application jsfApplication;
    private ResourceHandler resourceHandler;

    public void setJsfApplication(javax.faces.application.Application jsfApplication) {
        this.jsfApplication = jsfApplication;
        this.resourceHandler = jsfApplication.getResourceHandler();
    }
    
    public javax.faces.application.Application getJsfApplication() {
        return this.jsfApplication;
    }

    private UIComponent mainWindow;

    public UIComponent getMainWindow() {
        return mainWindow;
    }

    public void setMainWindow(UIComponent mainWindow) {
        this.mainWindow = mainWindow;
        FacesContext context = FacesContext.getCurrentInstance();
        UIViewRoot root = context.getViewRoot();
        UIComponent body = root.findComponent("javajsf_body");
        List<UIComponent> bodyChildren = body.getChildren();
        bodyChildren.clear();
        bodyChildren.add(mainWindow);
    }

    private Map<UIComponent, String> windows;

    public abstract void init();
    
    public abstract void destroy();
    
    public UIComponent createComponent(String componentType) {
        UIComponent result = null;
        FacesContext context = FacesContext.getCurrentInstance();
        result = createCompositeComponent(context, componentType);
        return result;
    }


    public UIComponent createComponent(String componentType, String rendererType) {
        UIComponent result = null;
        FacesContext context = FacesContext.getCurrentInstance();
        
        // Ask JSF for a traditional component
        result = jsfApplication.createComponent(context, componentType, rendererType);
        if (null == result) {
            // Look for a composite component
            result = createCompositeComponent(context, componentType);
        }
        
        return result;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Helper methods">
    
    private UIComponent createCompositeComponent(FacesContext context, String componentName) {
        UIComponent result = null;
        // PENDING(edburns): inspect all the resource libraries in the application, looking 
        // for a composite component componentName.  For now, just hard code "javajsf"
        String resourceName = componentName.endsWith(".xhtml") ? componentName : 
                componentName + ".xhtml";
        Resource compositeResource = resourceHandler.createResource(resourceName, "javajsf");
        result = jsfApplication.createComponent(context, compositeResource);
        
        return result;
    }
    
    // </editor-fold>
    

}
