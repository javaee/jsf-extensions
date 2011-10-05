
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

package com.sun.faces.jsf_extensions_javajsf.vdl;

import com.sun.faces.jsf_extensions_javajsf.ApplicationFinder;
import com.sun.faces.jsf_extensions_javajsf.JavaJsfApplication;
import com.sun.faces.jsf_extensions_javajsf.JavaJSFLogger;
import java.beans.BeanInfo;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PostAddToViewEvent;
import javax.faces.event.PreRemoveFromViewEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.view.AttachedObjectHandler;
import javax.faces.view.StateManagementStrategy;
import javax.faces.view.ViewDeclarationLanguage;
import javax.faces.view.ViewDeclarationLanguageWrapper;
import javax.faces.view.ViewMetadata;



public class JavaJsfViewDeclarationLanguage extends ViewDeclarationLanguageWrapper {

    private ViewDeclarationLanguage parent;
    private ViewDeclarationLanguage faceletViewDeclarationLanguage;
    private ApplicationFinder appFinder;
    
    private static final Logger LOGGER = JavaJSFLogger.JAVAJSF.getLogger();

    public JavaJsfViewDeclarationLanguage(ViewDeclarationLanguage faceletViewDeclarationLanguage,
            ViewDeclarationLanguage parent) {
        this.faceletViewDeclarationLanguage = faceletViewDeclarationLanguage;
        this.parent = parent;
    }
    
    public ApplicationFinder getAppFinder() {
        return appFinder;
    }

    public void setAppFinder(ApplicationFinder appFinder) {
        this.appFinder = appFinder;
    }
    
    @Override
    public ViewDeclarationLanguage getWrapped() {
        return parent;
    }

    // <editor-fold desc="ViewDeclarationLanguage implementation">

    @Override
    public void buildView(FacesContext context, UIViewRoot root) throws IOException {
        if (null == appFinder) {
            getWrapped().buildView(context, root);
            return;
        }
        instantiateAndInjectApplications(context);
        faceletViewDeclarationLanguage.buildView(context, root);
        linkMainWindowToViewRoot(context, root);
        
    }

    @Override
    public UIViewRoot createView(FacesContext context, String viewId) {
        if (null == appFinder) {
            return getWrapped().createView(context, viewId);
        }

        UIViewRoot result = getWrapped().createView(context, "/javajsf.xhtml");

        return result;
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="ViewDeclarationLanguage implementation delegated to Facelets">
    
    @Override
    public BeanInfo getComponentMetadata(FacesContext context, Resource componentResource) {
        return faceletViewDeclarationLanguage.getComponentMetadata(context, componentResource);
    }

    @Override
    public String getId() {
        return faceletViewDeclarationLanguage.getId();
    }

    @Override
    public Resource getScriptComponentResource(FacesContext context, Resource componentResource) {
        return faceletViewDeclarationLanguage.getScriptComponentResource(context, componentResource);
    }

    @Override
    public StateManagementStrategy getStateManagementStrategy(FacesContext context, String viewId) {
        return faceletViewDeclarationLanguage.getStateManagementStrategy(context, viewId);
    }

    @Override
    public ViewMetadata getViewMetadata(FacesContext context, String viewId) {
        return faceletViewDeclarationLanguage.getViewMetadata(context, viewId);
    }

    @Override
    public void renderView(FacesContext context, UIViewRoot view) throws IOException {
        faceletViewDeclarationLanguage.renderView(context, view);
    }

    @Override
    public UIViewRoot restoreView(FacesContext context, String viewId) {
        return faceletViewDeclarationLanguage.restoreView(context, viewId);
    }

    @Override
    public void retargetAttachedObjects(FacesContext context, UIComponent topLevelComponent, List<AttachedObjectHandler> handlers) {
        faceletViewDeclarationLanguage.retargetAttachedObjects(context, topLevelComponent, handlers);
    }

    @Override
    public void retargetMethodExpressions(FacesContext context, UIComponent topLevelComponent) {
        faceletViewDeclarationLanguage.retargetMethodExpressions(context, topLevelComponent);
    }

    @Override
    public boolean viewExists(FacesContext context, String viewId) {
        return super.viewExists(context, viewId);
    }
    
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Helper classes and methods">
    
    private static final String JAVAJSF_APPLICATIONS_DATA_STRUCTURE = "com.sun.faces.jsf_extensions_javajsf.APPLICATIONS";
        
    
    public void linkMainWindowToViewRoot(FacesContext context, UIViewRoot root) {
        
        List<SystemEventListener> postAddListeners = root.getViewListenersForEventClass(PostAddToViewEvent.class);
        List<SystemEventListener> postAddListenersCopy = new ArrayList(postAddListeners);
        postAddListeners.clear();
        
        List<SystemEventListener> preRemoveListeners = root.getViewListenersForEventClass(PreRemoveFromViewEvent.class);
        List<SystemEventListener> preRemoveListenersCopy = new ArrayList(preRemoveListeners);
        preRemoveListeners.clear();
        
        try {
            
            com.sun.faces.jsf_extensions_javajsf.Application app = findCurrentApplication(context);
            if (null != app) {
                UIComponent form = root.findComponent("javajsf_form");
                List<UIComponent> formChildren = form.getChildren();
                formChildren.clear();
                
                formChildren.add(app.getMainWindow());
                
            }
        }
        finally {
            postAddListeners.addAll(postAddListenersCopy);
            postAddListenersCopy.clear();

            preRemoveListeners.addAll(preRemoveListenersCopy);
            preRemoveListenersCopy.clear();
        }
    }
    
    private boolean currentRequestMatchesApplication(FacesContext context, JavaJsfApplication appAnnotation) {
        boolean result = true;
        
        // PENDING(edburns): implement
        return result;
    }
    
    private com.sun.faces.jsf_extensions_javajsf.Application findCurrentApplication(FacesContext context) {
        com.sun.faces.jsf_extensions_javajsf.Application result = null;
        ExternalContext extContext = context.getExternalContext();
        Map<String, Object> sessionMap = extContext.getSessionMap();
        PerSessionJavaJSFApplicationManager appManager = 
                (PerSessionJavaJSFApplicationManager) sessionMap.get(JAVAJSF_APPLICATIONS_DATA_STRUCTURE);
        assert(null != appManager);
        for (String cur : appFinder.getClassesAnnotatedWithJavaJSFApplication()) {
            com.sun.faces.jsf_extensions_javajsf.Application app = 
                    (com.sun.faces.jsf_extensions_javajsf.Application) appManager.getApplication(cur);
            JavaJsfApplication appAnnotation = (JavaJsfApplication) 
                    app.getClass().getAnnotation(JavaJsfApplication.class);
            if (currentRequestMatchesApplication(context, appAnnotation)) {
                result = app;
                break;
            }
        }
        
        return result;
    }

    private void instantiateAndInjectApplications(FacesContext context) {
        ExternalContext extContext = context.getExternalContext();
        // No-one complains when Vaadin insists that a session be created.
        Map<String, Object> sessionMap = extContext.getSessionMap();
        PerSessionJavaJSFApplicationManager appManager = 
                (PerSessionJavaJSFApplicationManager) sessionMap.get(JAVAJSF_APPLICATIONS_DATA_STRUCTURE);
        if (null == appManager) {
            appManager = new PerSessionJavaJSFApplicationManager();
            sessionMap.put(JAVAJSF_APPLICATIONS_DATA_STRUCTURE, appManager);
        }
        
        // Have the JavaJSF applications been initialized for this session?
        if (!appManager.isApplicationsInstantiatedAndInjected()) {
            boolean allAppsSuccessfullyInstantiatedAndInjected = true;
            for (String cur : appFinder.getClassesAnnotatedWithJavaJSFApplication()) {
                ClassLoader cl = getCurrentLoader(this);
                try {
                    Class appClass = cl.loadClass(cur);
                    
                    JavaJsfApplication appAnnotation = (JavaJsfApplication) 
                            appClass.getAnnotation(JavaJsfApplication.class);
                    if (currentRequestMatchesApplication(context, appAnnotation)) {
                        com.sun.faces.jsf_extensions_javajsf.Application app = 
                                (com.sun.faces.jsf_extensions_javajsf.Application) appClass.newInstance();
                        app.setJsfApplication(context.getApplication());
                        app.setJsfViewDeclarationLanguage(this);
                        appManager.addApplication(cur, app);
                        appFinder.invokePostConstruct(extContext, app);
                        appFinder.inject(extContext, app);
                    }
                } catch (Exception ex) {
                    allAppsSuccessfullyInstantiatedAndInjected = false;
                    if (LOGGER.isLoggable(Level.SEVERE)) {
                        Object [] params = new Object[]{ cur, ex };
                        LOGGER.log(Level.SEVERE, "javajsf.cannot_create_application", params);
                        LOGGER.log(Level.SEVERE, "", ex);
                    }
                } 
            }
            appManager.setApplicationsInstantiatedAndInjected(allAppsSuccessfullyInstantiatedAndInjected);
            
        }
        
    }

    public void initApplications(FacesContext context) {
        ExternalContext extContext = context.getExternalContext();
        Map<String, Object> sessionMap = extContext.getSessionMap();
        PerSessionJavaJSFApplicationManager appManager = 
                (PerSessionJavaJSFApplicationManager) sessionMap.get(JAVAJSF_APPLICATIONS_DATA_STRUCTURE);
        assert(null != appManager);
        
        // Have the JavaJSF applications been initialized for this session?
        if (!appManager.isApplicationsInitialized()) {
            boolean allAppsSuccessfullyInitialized = true;
            for (String cur : appFinder.getClassesAnnotatedWithJavaJSFApplication()) {
                com.sun.faces.jsf_extensions_javajsf.Application app = 
                        (com.sun.faces.jsf_extensions_javajsf.Application) appManager.getApplication(cur);
                JavaJsfApplication appAnnotation = (JavaJsfApplication) 
                        app.getClass().getAnnotation(JavaJsfApplication.class);
                if (currentRequestMatchesApplication(context, appAnnotation)) {
                    app.init();
                }
            }
            appManager.setApplicationsInitialized(allAppsSuccessfullyInitialized);
        }
        
    }

    
    private class PerSessionJavaJSFApplicationManager implements Serializable {
        
        private boolean applicationsInitialized = false;
        private boolean applicationsInstantiatedAndInjected = false;
        private Map<String, com.sun.faces.jsf_extensions_javajsf.Application> apps;

        public PerSessionJavaJSFApplicationManager() {
            apps = new HashMap<String, com.sun.faces.jsf_extensions_javajsf.Application>();
        }
        
        public boolean isApplicationsInitialized() {
            return applicationsInitialized;
        }

        public void setApplicationsInitialized(boolean applicationsInitialized) {
            this.applicationsInitialized = applicationsInitialized;
        }

        public boolean isApplicationsInstantiatedAndInjected() {
            return applicationsInstantiatedAndInjected;
        }

        public void setApplicationsInstantiatedAndInjected(boolean applicationsInstantiatedAndInjected) {
            this.applicationsInstantiatedAndInjected = applicationsInstantiatedAndInjected;
        }
        
        public void addApplication(String className, 
                com.sun.faces.jsf_extensions_javajsf.Application app) {
            apps.put(className, app);
        }
        
        public com.sun.faces.jsf_extensions_javajsf.Application getApplication(String className) {
            com.sun.faces.jsf_extensions_javajsf.Application result = apps.get(className);
            
            return result;
        }
    }
    
    private ClassLoader getCurrentLoader(Object fallbackClass) {
        ClassLoader loader =
            Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = fallbackClass.getClass().getClassLoader();
        }
        return loader;
    }
    

    // </editor-fold>

    

}
