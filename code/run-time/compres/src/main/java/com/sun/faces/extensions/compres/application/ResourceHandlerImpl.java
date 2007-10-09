/*
 * ResourceHandlerImpl.java
 */

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
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


package com.sun.faces.extensions.compres.application;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.activation.FileTypeMap;
import javax.activation.MimetypesFileTypeMap;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import com.sun.faces.extensions.common.util.Util;

/**
 *
 * @author edburns
 */
public class ResourceHandlerImpl extends ResourceHandler {
    
    // Log instance for this class
    private static final Logger logger = Util.getLogger(Util.FACES_LOGGER + Util.APPLICATION_LOGGER);

    FileTypeMap mimeTypeMap = null;
    List<Pattern> excludePatterns = null;
    long creationTime = 0;
    
    
    /** Creates a new instance of ResourceHandlerImpl */
    ResourceHandlerImpl() {
        mimeTypeMap = new MimetypesFileTypeMap();
        ExternalContext extContext = 
                FacesContext.getCurrentInstance().getExternalContext();
        String excludesParam = 
                extContext.getInitParameter("javax.faces.resource.EXCLUDES");
        excludePatterns = new ArrayList<Pattern>();
        if (null != excludesParam) {
            
        }
        else {
            excludePatterns.add(Pattern.compile(".*\\.class"));
            excludePatterns.add(Pattern.compile(".*\\.properties"));
            excludePatterns.add(Pattern.compile(".*\\.xhtml"));
            excludePatterns.add(Pattern.compile(".*\\.jsp"));
        }
        
        creationTime = System.currentTimeMillis();
    }
    
    String getContentTypeFromResourceName(String resourceName) {
        String result;
        result = mimeTypeMap.getContentType(resourceName);
        return result;
    }

    public Resource createResource(String resourceName) {
        String contentType = getContentTypeFromResourceName(resourceName);
        Resource result = createResource(resourceName, null, contentType);
        return result;
    }

    public Resource restoreResource(FacesContext context) {
        String viewId = normalizeViewId(context);
        String resourceName, libraryName, localePrefix;
        Resource result = null;
        assert(null != viewId);
        assert(viewId.startsWith("javax.faces.resource."));
        if ("javax.faces.resource.".length() < viewId.length()) {

            resourceName = viewId.substring("javax.faces.resource.".length() + 1);
            libraryName = context.getExternalContext().getRequestParameterMap().get("ln");
            result = createResource(resourceName, libraryName);
        }
        return result;
    }
    
    String getLocalePrefix(FacesContext context) {
        String localePrefix = "";
        String appBundleName = context.getApplication().getMessageBundle();
        if (null != appBundleName) {
            Locale curLocale = context.getViewRoot().getLocale();
            ResourceBundle appBundle = null;

            try {
                appBundle = ResourceBundle.getBundle(appBundleName, curLocale);
                localePrefix = appBundle.getString("javax.faces.resource.localePrefix");
            } catch (MissingResourceException e) {
                logger.log(Level.INFO, "Unable to find key 'javax.faces.resource.localePrefix' in application message bundle.\nAssuming default Locale.\n", e);
            }
        }
        return localePrefix;
    }
    
    
    long getCreationTime() {
        return creationTime;
    }
    
    private String normalizeViewId(FacesContext context) {
        String viewId = context.getViewRoot().getViewId();
        if (viewId.startsWith("/") && 2 < viewId.length()) {
            viewId = viewId.substring(1);
        }
        String facesServletMapping = Util.getFacesMapping(context);
        // If it is extension mapped
        if (!Util.isPrefixMapped(facesServletMapping)) {
            // strip off the extension
            int i = viewId.lastIndexOf(".");
            if (0 < i) {
                viewId = viewId.substring(0, i);
            }
        }
        return viewId;
    }

    public boolean isResourceRequest(FacesContext context) {
        String viewId = normalizeViewId(context);
        boolean 
                result = false,
                matchesExcludeEntry = false;
        // Step 1, see if the viewId matches an exclude
        for (Pattern cur : excludePatterns) {
            if (matchesExcludeEntry = cur.matcher(viewId).matches()) {
                break;
            }
        }
        // Step 2, if the viewId does not match an exclude,
        // see if it starts with javax.faces.resource
        if (!matchesExcludeEntry) {
            result = viewId.startsWith("javax.faces.resource");
        }
        return result;
    }

    public Resource createResource(String resourceName, String libraryName, String contentType) {
        Resource result = new ResourceBase(this, resourceName, libraryName, contentType);
        return result;
    }

    public Resource createResource(String resourceName, String libraryName) {
        String contentType = getContentTypeFromResourceName(resourceName);
        Resource result = createResource(resourceName, libraryName, contentType);
        return result;
    }
    
    
    
}
