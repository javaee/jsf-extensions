/*
 * Resource.java
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


package javax.faces.application;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

/**
 *
 * @author edburns
 */
public abstract class Resource {

/**
  * Return the relative URI for this resource following the algorithm
  * specified in ResourceHandler.  This URI, when resolved against the
  * URI of the view request will yield an absolute URI which will return
  * the actual bytes of the resource.
  */
  public abstract String getURI();

/**
  * Call through to getURI() and return the result.
  */
  public String toString() { return getURI(); }

/**
  * Return the resourceName for this resource.  Will never be null.
  */
  public String getResourceName() {
      return resourceName;
  }
  
  private String resourceName;
  public void setResourceName(String resourceName) {
      this.resourceName = resourceName;
  }
  
/**
  * Return the libraryName for this resource.  May be null.
  */
  public String getLibraryName() {      
      return libraryName;
  }
  
  private String libraryName;
  public void setLibraryName(String libraryName) {
      this.libraryName = libraryName;
  }
  
/**
  * Return the content-type for this resource.
  */
  public String getContentType() {
      return contentType;
  }
  
  private String contentType;
  public void setContentType(String contentType) {
      this.contentType = contentType;
  }


/**
  * If the current request is a resource request, return an InputStream 
  * containing the bytes of the resource.  Otherwise, throw an 
  * IOException.
  */
  public abstract InputStream getInputStream() throws IOException;
    
  /**
   * Return true if the user agent requesting this resource needs an update.
   * Returns false otherwise.  If false, the caller should send a 
   * 304 not modified to the client.
   */
    
  public abstract boolean userAgentNeedsUpdate(FacesContext context);
  
  public abstract int getMaxAge(FacesContext context);
  
  /**
   * Returns a Map of response headers to be sent with this resource, or
   * an empty map if no response headers are to be sent.
   */
  
  public abstract Map<String,String> getResponseHeaders();
}
