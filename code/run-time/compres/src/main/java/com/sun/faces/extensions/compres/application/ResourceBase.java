/*
 * ResourceBase.java
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import javax.servlet.ServletContext;

import com.sun.faces.extensions.common.util.Util;

/**
 *
 * @author edburns
 */
public class ResourceBase extends Resource {
    // Log instance for this class
    private static final Logger logger = Util.getLogger(Util.FACES_LOGGER 
            + Util.APPLICATION_LOGGER);
    
    ResourceHandlerImpl owner;
    int maxAge = 86400;
    String resourceId;
    
    private enum ResourceOrigin {
        FileSystem,
        Classpath
    };
    
    private ResourceOrigin resourceOrigin = null;
    
    /** Creates a new instance of ResourceBase */
    ResourceBase(ResourceHandlerImpl owner, String resourceName, String libraryName, String contentType) {
        this.owner = owner;
        super.setResourceName(resourceName);
        super.setLibraryName(libraryName);
        super.setContentType(contentType);
    }

    public Renderer getRenderer(UIComponent component) {
        Renderer result = null;
        return result;
    }

    public String getURI() {
        String uri = null;
        return uri;
    }

    public InputStream getInputStream() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        URL url = null;
        InputStream resourceStream = null;
        
        if (null == (resourceStream = searchForInputStream(context))) {
            resourceId = null;
            throw new IOException("Unable to find resource for " + this.toString());
        }
        return resourceStream;
    }
    
    private InputStream searchForInputStream(FacesContext context) throws IOException {
        InputStream result = null;
        
        if (null == (result = getInputStreamFromWebapp(context))) {
            result = getInputStreamFromClasspath(context);
        }
        return result;
    }
    
    public int getMaxAge(FacesContext context) {
        return maxAge;
    }

    private InputStream getInputStreamFromWebapp(FacesContext context) throws IOException {
        resourceId = getResourceId(context, "/resources");
        InputStream result = 
                context.getExternalContext().getResourceAsStream(resourceId);
        if (null != result) {
            resourceOrigin = ResourceOrigin.FileSystem;
        }
        return result;        
        
    }
    
    private InputStream getInputStreamFromClasspath(FacesContext context) throws IOException {
        resourceId = getResourceId(context, "/META-INF/resources");
        InputStream result = 
                Thread.currentThread().getContextClassLoader().getResourceAsStream(resourceId.substring(1));
        if (null != result) {
            resourceOrigin = ResourceOrigin.Classpath;
        }
        return result;        
    }
    
    
    private ResourceOrigin getResourceOrigin(FacesContext context) {
        ResourceOrigin result = null;
        if (null != resourceOrigin) {
            result = resourceOrigin;
        }
        else {
            InputStream stream = null;
            try {
                stream = searchForInputStream(context);
                stream.close();
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Unable to get Resource Origin for " +
                        this.toString(), ex);
            }
            if (null != stream) {
                result = resourceOrigin;
            }
        }
        return result;
    }
        
    private String getResourceId(FacesContext context, String prefix) throws IOException {
        
        ExternalContext extContext = context.getExternalContext();
        String localePrefix = owner.getLocalePrefix(context);
        Set<String> resourcePaths;
        String resId, libraryVersion = null, resourceVersion = null;
        if (null != localePrefix && 0 < localePrefix.length()) {
            prefix = prefix + '/' + localePrefix;
        }
        
        if (null != getLibraryName()) {
            resourcePaths = extContext.getResourcePaths(prefix + '/' + 
                    getLibraryName());
            if (null != resourcePaths && !resourcePaths.isEmpty()) {
                // Look for versioned libraries.  If one or more versioned libraries
                // are found, take the higest one as the value of "libraryVersion"

                // If no versioned libraries are found, let libraryVersion remain null

                // Apply the same process to resourceVersion.
            }
            if (null != libraryVersion) {
                resId = prefix + '/' + getLibraryName() + '/' + 
                        libraryVersion + '/';
            }
            else {
                resId = prefix + '/' + getLibraryName() + '/';
            }
            if (null != resourceVersion) {
                resId = prefix + '/' + resId + '/' + 
                        getResourceName() + '/' + resourceVersion;
            }
            else {
                resId = prefix + '/' + resId + '/' + getResourceName();
            }
        }
        else {
            resourcePaths = extContext.getResourcePaths(prefix + '/' + getResourceName());
            if (null != resourcePaths && !resourcePaths.isEmpty()) {
                // Look for versioned resources, not in a library.
                // If one or more versioned resource
                // is found, take the higest one as the value of "resourceVersion"

                // If no versioned resources are found, let resourceVersion remain null

            }
            if (null != resourceVersion) {
                resId = prefix + '/' + getResourceName() + '/' + resourceVersion;
            }
            else {
                resId = prefix + '/' + getResourceName();
            }
        }
        return resId;
    }

    /**
     * Return true if the user agent needs an update of this resource.
     * Look for a "Cache-Control" request header with a "max-age=0" value.
     * If not found, assume the browser does not have a copy of the
     * resource and return true.
     *
     * If found, assume the user agent has
     * an entry in its local cache for this resource and is just trying to 
     * determine if it needs to be refreshed.  Determine the ResourceOrigin.
     *
     * If FileSystem:
     * If the file has been modified since the ResourceHandler instance
     * was instantiated, return true.  Otherwise, return false.
     * 
     *
     * If Classpath, the age of the resource is the value of the creationTime
     * property of our ResourceLoaderImpl, in seconds.
     *
     * If the file is older than getMaxAge() return true;
     *
     */ 
    public boolean userAgentNeedsUpdate(FacesContext context) {
        boolean result = true;
        long current_age, ageInMillis = 0, freshness_lifetime = getMaxAge(context);
        Map<String, String> headers = 
                context.getExternalContext().getRequestHeaderMap();
                
        String cacheControlHeader = headers.get("Cache-Control");
        if (null == cacheControlHeader ||
                (!cacheControlHeader.equals("max-age=0"))) {
            return true;
        }
        
        if (ResourceOrigin.FileSystem == getResourceOrigin(context)) {
            InputStream stream = null;
            try {
                stream = searchForInputStream(context);
                stream.close();
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Unable to determine age of " + 
                        this.toString(), ex);
            }
            if (null != stream) {
                ServletContext servletContext =
                        (ServletContext) context.getExternalContext().getContext();
                File resource = new File(servletContext.getRealPath(resourceId));
                result = resource.lastModified() > owner.getCreationTime();
            }

        }
        else {
            ageInMillis = System.currentTimeMillis() - owner.getCreationTime();
            current_age = ageInMillis / 1000;
            result =  (freshness_lifetime > current_age);
        }
        return result;
    }
       
}
