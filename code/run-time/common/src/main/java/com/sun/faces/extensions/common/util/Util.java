/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or
 * legal/CDDLv1.0.txt. 
 * See the License for the specific language governing
 * permission and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at legal/CDDLv1.0.txt.    
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * [Name of File] [ver.__] [Date]
 * 
 * Copyright 2005 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.faces.extensions.common.util;

import java.util.Iterator;
import java.util.List;
import java.util.MissingResourceException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.event.PhaseId;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

/**
 *
 * @author edburns
 */
public class Util {
    
    private Util() {
    }
    
    public static final String FACES_LOGGER = "javax.enterprise.resource.webcontainer.jsf";
    
    
    
    public static final String FACES_LOG_STRINGS = 
            "com.sun.faces.LogStrings";        
    
    // Loggers
    public static final String RENDERKIT_LOGGER = ".renderkit";
    public static final String TAGLIB_LOGGER = ".taglib";
    public static final String APPLICATION_LOGGER = ".application";
    public static final String CONTEXT_LOGGER = ".context";
    public static final String CONFIG_LOGGER = ".config";
    public static final String LIFECYCLE_LOGGER = ".lifecycle";
    public static final String TIMING_LOGGER = ".timing";
    
     /**
     * <p>The <code>request</code> scoped attribute to store the
     * {@link javax.faces.webapp.FacesServlet} path of the original
     * request.</p>
     */
    private static final String INVOCATION_PATH =
       "com.sun.faces.INVOCATION_PATH";
    
    
    // Log instance for this class
    private static final Logger LOGGER = getLogger(FACES_LOGGER);
    
    
    public static String getFormattedValue(FacesContext context, UIComponent component,
                                       Object currentValue)
        throws ConverterException {

        String result = null;
        // formatting is supported only for components that support
        // converting value attributes.
        if (!(component instanceof ValueHolder)) {
            if (currentValue != null) {
                result = currentValue.toString();
            }
            return result;
        }

        Converter converter = null;

        // If there is a converter attribute, use it to to ask application
        // instance for a converter with this identifer.
        converter = ((ValueHolder) component).getConverter();


        // if value is null and no converter attribute is specified, then
        // return a zero length String.
        if (converter == null && currentValue == null) {
            return "";
        }

        if (converter == null) {
            // Do not look for "by-type" converters for Strings
            if (currentValue instanceof String) {
                return (String) currentValue;
            }

            // if converter attribute set, try to acquire a converter
            // using its class type.

            Class converterType = currentValue.getClass();
            converter = Util.getConverterForClass(converterType, context);

            // if there is no default converter available for this identifier,
            // assume the model type to be String.
            if (converter == null) {
                result = currentValue.toString();
                return result;
            }
        }

        return converter.getAsString(context, component, currentValue);
    }    
    
    
    public static Converter getConverterForClass(Class converterClass,
                                                 FacesContext context) {
        if (converterClass == null) {
            return null;
        }
        try {            
            Application application = context.getApplication();
            return (application.createConverter(converterClass));
        } catch (Exception e) {
            return (null);
        }
    }
    
    public static PhaseId getPhaseIdFromString(String phaseIdString) {
        PhaseId result = PhaseId.ANY_PHASE;
        List<PhaseId> phaseIds = PhaseId.VALUES;
        for (PhaseId curPhase : phaseIds) {
            if (-1 != curPhase.toString().toLowerCase().
                    indexOf(phaseIdString.toLowerCase())) {
                return curPhase;
            }
        }
        return result;
    }
      
    public static RenderKit getRenderKit(FacesContext context) {
       String renderKitId = context.getViewRoot().getRenderKitId();
       renderKitId = (null != renderKitId) ? renderKitId : RenderKitFactory.HTML_BASIC_RENDER_KIT;
       return getRenderKit(context, renderKitId);
    }
 
    public static RenderKit getRenderKit(FacesContext context,
            String renderKitId) {
       RenderKitFactory fact = (RenderKitFactory)
            FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
       assert(null != fact);
 
       RenderKit curRenderKit = fact.getRenderKit(context, renderKitId);
       return curRenderKit;
    }
    
    public static boolean prefixViewTraversal(FacesContext context,
					      UIComponent root,
					      TreeTraversalCallback action) throws FacesException {
	boolean keepGoing = false;
	if (keepGoing = action.takeActionOnNode(context, root)) {
	    Iterator<UIComponent> kids = root.getFacetsAndChildren();
	    while (kids.hasNext() && keepGoing) {
		keepGoing = prefixViewTraversal(context, 
						kids.next(), 
						action);
	    }
	}
	return keepGoing;
    }

    public static interface TreeTraversalCallback {
	public boolean takeActionOnNode(FacesContext context, 
					UIComponent curNode) throws FacesException;
    }
    
    public static Logger getLogger( String loggerName ) {
        Logger logger = null;
        try {
            logger = Logger.getLogger(loggerName, FACES_LOG_STRINGS );
        } catch (MissingResourceException mre) {
            logger = Logger.getLogger(loggerName);
        }
        
        return logger;
    }
    
    


    /**
     * <p>Returns the URL pattern of the
     * {@link javax.faces.webapp.FacesServlet} that
     * is executing the current request.  If there are multiple
     * URL patterns, the value returned by
     * <code>HttpServletRequest.getServletPath()</code> and
     * <code>HttpServletRequest.getPathInfo()</code> is
     * used to determine which mapping to return.</p>
     * If no mapping can be determined, it most likely means
     * that this particular request wasn't dispatched through
     * the {@link javax.faces.webapp.FacesServlet}.
     *
     * @param context the {@link FacesContext} of the current request
     *
     * @return the URL pattern of the {@link javax.faces.webapp.FacesServlet}
     *         or <code>null</code> if no mapping can be determined
     *
     * @throws NullPointerException if <code>context</code> is null
     */
    public static String getFacesMapping(FacesContext context) {

        if (context == null) {
            String message = "Unable to get FacesMapping, null paramaters";
            throw new NullPointerException(message);
        }

        // Check for a previously stored mapping   
        ExternalContext extContext = context.getExternalContext();
        String mapping =
              (String) extContext.getRequestMap().get(INVOCATION_PATH);

        if (mapping == null) {

            Object request = extContext.getRequest();
            String servletPath = null;
            String pathInfo = null;

            // first check for javax.servlet.forward.servlet_path
            // and javax.servlet.forward.path_info for non-null
            // values.  if either is non-null, use this
            // information to generate determine the mapping.

            servletPath = extContext.getRequestServletPath();
            pathInfo = extContext.getRequestPathInfo();


            mapping = getMappingForRequest(servletPath, pathInfo);
            if (mapping == null) {
                if (LOGGER.isLoggable(Level.FINE)) {
                    LOGGER.log(Level.FINE,
                               "jsf.faces_servlet_mapping_cannot_be_determined_error",
                               new Object[]{servletPath});
                }
            }
        }
        
        // if the FacesServlet is mapped to /* throw an 
        // Exception in order to prevent an endless 
        // RequestDispatcher loop
        if ("/*".equals(mapping)) {
            throw new FacesException("FacesServlet mapped to *.");
        }

        if (mapping != null) {
            extContext.getRequestMap().put(INVOCATION_PATH, mapping);
        }
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE,
                       "URL pattern of the FacesServlet executing the current request "
                       + mapping);
        }
        return mapping;
    }

    /**
     * <p>Return the appropriate {@link javax.faces.webapp.FacesServlet} mapping
     * based on the servlet path of the current request.</p>
     *
     * @param servletPath the servlet path of the request
     * @param pathInfo    the path info of the request
     *
     * @return the appropriate mapping based on the current request
     *
     * @see HttpServletRequest#getServletPath()
     */
    private static String getMappingForRequest(String servletPath, String pathInfo) {

        if (servletPath == null) {
            return null;
        }
        if (LOGGER.isLoggable(Level.FINE)) {
            LOGGER.log(Level.FINE, "servletPath " + servletPath);
            LOGGER.log(Level.FINE, "pathInfo " + pathInfo);
        }
        // If the path returned by HttpServletRequest.getServletPath()
        // returns a zero-length String, then the FacesServlet has
        // been mapped to '/*'.
        if (servletPath.length() == 0) {
            return "/*";
        }

        // presence of path info means we were invoked
        // using a prefix path mapping
        if (pathInfo != null) {
            return servletPath;
        } else if (servletPath.indexOf('.') < 0) {
            // if pathInfo is null and no '.' is present, assume the
            // FacesServlet was invoked using prefix path but without
            // any pathInfo - i.e. GET /contextroot/faces or
            // GET /contextroot/faces/
            return servletPath;
        } else {
            // Servlet invoked using extension mapping
            return servletPath.substring(servletPath.lastIndexOf('.'));
        }
    }
    
    
    /**
     * <p>Returns true if the provided <code>url-mapping</code> is
     * a prefix path mapping (starts with <code>/</code>).</p>
     *
     * @param mapping a <code>url-pattern</code>
     * @return true if the mapping starts with <code>/</code>
     */
    public static boolean isPrefixMapped(String mapping) {
        return (mapping.charAt(0) == '/');
    }
    
    
}
