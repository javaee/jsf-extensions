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

import java.io.IOException;
import java.io.InputStream;
import java.util.Set;
import java.util.logging.Logger;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

/**
 *
 * @author edburns
 */
public class ResourceBase extends Resource {
    // Log instance for this class
    private static final Logger logger = com.sun.faces.util.Util.getLogger(com.sun.faces.util.Util.FACES_LOGGER 
            + com.sun.faces.util.Util.APPLICATION_LOGGER);
    
    ResourceHandlerImpl owner;
    
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
        InputStream resourceStream = null;
        
        if (null == (resourceStream = resourceSearch(context, "/resources"))) {
            if (null == (resourceStream = resourceSearch(context, "/META-INF/resources"))) {
                throw new IOException("Unable to find resource for " + this.toString());
            }
        }
        return resourceStream;
    }
    
    private InputStream resourceSearch(FacesContext context, String prefix) throws IOException {
        InputStream resourceStream = null;
        ExternalContext extContext = context.getExternalContext();
        String localePrefix = owner.getLocalePrefix(context);
        Set<String> resourcePaths;
        String resourceId, libraryVersion = null, resourceVersion = null;
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
                resourceId = prefix + '/' + getLibraryName() + '/' + 
                        libraryVersion + '/';
            }
            else {
                resourceId = prefix + '/' + getLibraryName() + '/';
            }
            if (null != resourceVersion) {
                resourceId = prefix + '/' + resourceId + '/' + 
                        getResourceName() + '/' + resourceVersion;
            }
            else {
                resourceId = prefix + '/' + resourceId + '/' + getResourceName();
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
                resourceId = prefix + '/' + getResourceName() + '/' + resourceVersion;
            }
            else {
                resourceId = prefix + '/' + getResourceName();
            }
        }
        resourceStream = extContext.getResourceAsStream(resourceId);

        return resourceStream;
    }
    
    
}
