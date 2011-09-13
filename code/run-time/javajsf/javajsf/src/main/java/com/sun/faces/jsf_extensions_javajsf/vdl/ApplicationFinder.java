
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

import com.sun.faces.jsf_extensions_javajsf.Application;
import com.sun.faces.jsf_extensions_javajsf.JavaJSFLogger;
import com.sun.faces.spi.AnnotationScanner;
import com.sun.faces.spi.AnnotationScanner.ScannedAnnotation;
import com.sun.faces.spi.InjectionProvider;
import com.sun.faces.spi.InjectionProviderException;
import com.sun.faces.spi.InjectionProviderFactory;
import java.lang.reflect.Method;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FactoryFinder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ListenerFor;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;


@ListenerFor(systemEventClass=PostConstructApplicationEvent.class)
public class ApplicationFinder implements SystemEventListener, PhaseListener {
    
    private static final Logger LOGGER = JavaJSFLogger.VDL.getLogger();
    
    private static final Set<String> JAVAJSF_ANNOTATIONS;
    
    static {
        HashSet<String> annotations = new HashSet<String>(8, 1.0f);
        // JAVASERVERFACES-1835 this collection has the same information twice.
        // Once in javap -s format, and once as fully qualified Java class names.
        Collections.addAll(annotations,
                           "Lcom/sun/faces/jsf_extensions_javajsf/JavaJSFApplication;",
                           "com.sun.faces.jsf_extensions_javajsf.JavaJSFApplication");
        JAVAJSF_ANNOTATIONS = Collections.unmodifiableSet(annotations);
    }
    
    private enum PhaseListenerActionType {
        ADD,
        REMOVE
    };
    
    private List<String> classesAnnotatedWithJavaJSFApplication;

    public ApplicationFinder() {
        classesAnnotatedWithJavaJSFApplication = new ArrayList<String>();
    }
    
    // <editor-fold defaultstate="collapsed" desc="SystemEventListener implementation">

    @Override
    public boolean isListenerForSource(Object source) {
        return source instanceof javax.faces.application.Application;
    }

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {
        
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext extContext = context.getExternalContext();
        Object contextObject = extContext.getContext();
        if (!(contextObject instanceof ServletContext)) {
            if (LOGGER.isLoggable(Level.SEVERE)) {
                LOGGER.log(Level.SEVERE, "javajsf.vdl.invalid_runtime_portlet");
            }
            throw new AbortProcessingException();
        }
        
        if (event instanceof PostConstructApplicationEvent) {

            ServletContext sc = (ServletContext) contextObject;
            
            InjectionProvider containerConnector = InjectionProviderFactory.createInstance(extContext);
            if (containerConnector instanceof AnnotationScanner) {
                Set<String> classList = new HashSet<String>();
                
                processAnnotations(containerConnector, extContext, sc, classList);
                
                if (!classList.isEmpty()) {
                    classesAnnotatedWithJavaJSFApplication.addAll(classList);
                    context.getApplication().subscribeToEvent(PreDestroyApplicationEvent.class, this);
                    addOrRemovePhaseListener(PhaseListenerActionType.ADD);
                }
                
            } else {
                if (LOGGER.isLoggable(Level.SEVERE)) {
                    LOGGER.log(Level.SEVERE, "javajsf.vdl.invalid_runtime");
                }
                throw new AbortProcessingException();
            }
        } else {
            assert(event instanceof PreDestroyApplicationEvent);
            
            if (!classesAnnotatedWithJavaJSFApplication.isEmpty()) {
                addOrRemovePhaseListener(PhaseListenerActionType.REMOVE);
                classesAnnotatedWithJavaJSFApplication.clear();
            }
        } 
    } 
    
    // </editor-fold>
       
    // <editor-fold defaultstate="collapsed" desc="PhaseListener implementation">
    
    private static final String JAVAJSF_APPLICATIONS_DATA_STRUCTURE = "com.sun.faces.jsf_extensions_javajsf.APPLICATIONS";

    @Override
    public void beforePhase(PhaseEvent event) {
        FacesContext context = FacesContext.getCurrentInstance();
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
        if (!appManager.isApplicationsInitialized()) {
            boolean allAppsSuccessfullyInitialized = true;
            for (String cur : classesAnnotatedWithJavaJSFApplication) {
                ClassLoader cl = getCurrentLoader(this);
                try {
                    Class appClass = cl.loadClass(cur);
                    Application app = (Application) appClass.newInstance();
                    app.init();
                    appManager.addApplication(cur, app);
                } catch (Exception ex) {
                    allAppsSuccessfullyInitialized = false;
                    if (LOGGER.isLoggable(Level.SEVERE)) {
                        Object [] params = new Object[]{ cur, ex };
                        LOGGER.log(Level.SEVERE, "javajsf.vdl.cannot_create_application", params);
                    }
                } 
            }
            appManager.setApplicationsInitialized(allAppsSuccessfullyInitialized);
            
        }
        
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    
    @Override
    public void afterPhase(PhaseEvent event) {
        
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Helper methods">
    
    private void addOrRemovePhaseListener(PhaseListenerActionType actionToTake) {
        LifecycleFactory lifecycleFactory = (LifecycleFactory)
                FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
        Iterator<String> lifecycleIds = lifecycleFactory.getLifecycleIds();
        while (lifecycleIds.hasNext()) {
            Lifecycle lifecycle = lifecycleFactory.getLifecycle(lifecycleIds.next());
            if (actionToTake.equals(PhaseListenerActionType.ADD)) {
                lifecycle.addPhaseListener(this);
            } else {
                lifecycle.removePhaseListener(this);
            }
        }
        
    }
    
    private void processAnnotations(InjectionProvider containerConnector, 
            ExternalContext extContext,
            ServletContext sc, Set<String> classList) {
            AnnotationScanner annotationScanner = (AnnotationScanner) containerConnector;
            String archiveName = getCurrentWebModulePrefix(extContext);
            
            try {
                Map<String, List<ScannedAnnotation>> classesByAnnotation =
                        annotationScanner.getAnnotatedClassesInCurrentModule(sc);
                for (String curAnnotationName : classesByAnnotation.keySet()) {
                    if (JAVAJSF_ANNOTATIONS.contains(curAnnotationName)) {
                        for (ScannedAnnotation curAnnotation : classesByAnnotation.get(curAnnotationName)) {
                            Collection<URI> definingUris = curAnnotation.getDefiningURIs();
                            Iterator<URI> iter = definingUris.iterator();
                            URI uri, jarUri = null;
                            String uriString;
                            boolean doAdd = false;
                            while (!doAdd && iter.hasNext()) {
                                uri = iter.next();
                                uriString = uri.toASCIIString();
                                
                                // If the class is in the current web module
                                boolean currentClassIsInCurrentWebModule =
                                        (uriString.endsWith("WEB-INF/classes") || uriString.endsWith("WEB-INF/classes/"))
                                        && uriString.contains(archiveName);
                                // or it is from a jar that is *not* within a web module...
                                boolean currentClassIsInJarNotInAnyWebModule =
                                        uriString.endsWith(".jar")
                                        && !uriString.contains(archiveName)
                                        && !uriString.contains("WEB-INF/classes");
                                if (currentClassIsInCurrentWebModule
                                        || currentClassIsInJarNotInAnyWebModule) {
                                    doAdd = true;
                                } 
                            }
                            if (doAdd) {
                                String fqcn = curAnnotation.getFullyQualifiedClassName();
                                classList.add(fqcn);
                            }
                            
                        }
                        
                    }
                }
                
            } catch (InjectionProviderException ex) {
                if (LOGGER.isLoggable(Level.SEVERE)) {
                    LOGGER.log(Level.SEVERE, "javajsf.vdl.annotation_scanning_failed", ex);
                }
                
            }
    }
    
    private String getCurrentWebModulePrefix(ExternalContext extContext) {
        String result = null;
        Object deploymentContext = getDeploymentContext(extContext);
        if (null != deploymentContext) {
            try {
                // If this module is a war or an exploded war, then this will give the 
                // prefix.
                Method getSource = deploymentContext.getClass().getDeclaredMethod("getSource");
                Object source = getSource.invoke(deploymentContext, (Object[]) null);
                if (null != source) {
                    Method getName = source.getClass().getDeclaredMethod("getName");
                    if (null != getName) {
                        result = (String) getName.invoke(source, (Object[]) null);
                    }

                }

            } catch (Exception e) {
            }
        }
        if (null == result && null != deploymentContext) {
            try {
                // If this module is an ear, then this will give the prefix.
                Method getModuleUri = deploymentContext.getClass().getMethod("getModuleUri");
                if (null != getModuleUri) {
                    result = (String) getModuleUri.invoke(deploymentContext, (Object[]) null);
                    if (null != result && result.endsWith(".war")) {
                        result = result.substring(0, result.length() - 4);
                    }
                }

            } catch (Exception e) {
            }
        }

        if (null == result) {
            result = extContext.getApplicationContextPath();
        }

        return result;
    }
    
    private Object getDeploymentContext(ExternalContext extContext) {
        Object result = null;
        Map<String, Object> appMap = extContext.getApplicationMap();
        
        // This will work in GlassFish 3.1.1.
        result = appMap.get("com.sun.enterprise.web.WebModule.DeploymentContext");
        return result;
    }
    
    public ClassLoader getCurrentLoader(Object fallbackClass) {
        ClassLoader loader =
            Thread.currentThread().getContextClassLoader();
        if (loader == null) {
            loader = fallbackClass.getClass().getClassLoader();
        }
        return loader;
    }
    

    // </editor-fold>
    
    private class PerSessionJavaJSFApplicationManager {
        
        private boolean applicationsInitialized = false;
        private Map<String, Application> apps;

        public PerSessionJavaJSFApplicationManager() {
            apps = new HashMap<String, Application>();
        }
        
        public boolean isApplicationsInitialized() {
            return applicationsInitialized;
        }

        public void setApplicationsInitialized(boolean applicationsInitialized) {
            this.applicationsInitialized = applicationsInitialized;
        }
        
        public void addApplication(String className, Application app) {
            apps.put(className, app);
        }
        
        public Application getApplication(String className) {
            Application result = apps.get(className);
            
            return result;
        }
    }

}
