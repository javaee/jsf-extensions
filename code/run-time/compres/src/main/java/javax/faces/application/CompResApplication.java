/*
 * CompResApplication.java
 *
 * Created on October 16, 2007, 12:26 PM
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

/**
 *
 * @author edburns
 */
public abstract class CompResApplication extends Application {
    
    /** Creates a new instance of CompResApplication */
    public CompResApplication() {
    }

    /**
     * Holds value of property resourceHandler.
     */
    private ResourceHandler resourceHandler;

    /**
     * Return the singleton, stateless, thread-safe ResourceHandler for
     * this Application.  An alternate implementation for this class can be
     * declared in the application configuration resources by giving the
     * fully qualified class name as the value of the resource-manager
     * element within the application element.  It can also be declared via
     * an annotation as specified in [287-ConfigAnnotations].
     * In both cases, the decorator pattern will be followed as for
     * every other pluggable artifact in JSF.
     *
     * In this way, it is possible to completely customize the
     * implementation of resource handling to satisfy [CompRes.R02.D]
     *
     */
    public ResourceHandler getResourceHandler() {
        return this.resourceHandler;
    }

    /**
     * Setter for property resourceHandler.
     * @param resourceHandler New value of property resourceHandler.
     */
    public void setResourceHandler(ResourceHandler resourceHandler) {
        this.resourceHandler = resourceHandler;
    }
    
    
    
}
