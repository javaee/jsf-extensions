/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2005-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
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

package com.sun.faces.extensions.common.util;

import java.util.Iterator;
import java.util.List;
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
    
}
