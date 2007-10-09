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
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
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
    
    
}
