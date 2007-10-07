/*
 * CompResViewHandlerImpl.java
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
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.application.CompResApplication;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletResponse;

/**
 *
 * @author edburns
 */
public class CompResViewHandlerImpl extends ViewHandlerWrapper {
    // Log instance for this class
    private static final Logger logger = com.sun.faces.util.Util.getLogger(com.sun.faces.util.Util.FACES_LOGGER 
            + com.sun.faces.util.Util.APPLICATION_LOGGER);
    

    private ViewHandler oldViewHandler = null;

    public CompResViewHandlerImpl(ViewHandler oldViewHandler) {
	this.oldViewHandler = oldViewHandler;
    }

    public ViewHandler getWrapped() {
	return oldViewHandler;
    }

    public void renderView(FacesContext facesContext, UIViewRoot uIViewRoot) throws IOException, FacesException {

        CompResApplication app = (CompResApplication) facesContext.getApplication();
        ResourceHandler resourceHandler = app.getResourceHandler();
        if (!resourceHandler.isResourceRequest(facesContext)) {
            getWrapped().renderView(facesContext, uIViewRoot);
        }
        else {
            ExternalContext extContext = facesContext.getExternalContext();
            ServletResponse response = (ServletResponse) extContext.getResponse();
            Resource resource = resourceHandler.restoreResource(facesContext);
            ReadableByteChannel resourceChannel;
            WritableByteChannel out;
            ByteBuffer buf = ByteBuffer.allocate(8192);
            resourceChannel = Channels.newChannel(resource.getInputStream());
            out = Channels.newChannel(response.getOutputStream());
            try {
                while (-1 != resourceChannel.read(buf)) {
                    buf.rewind();
                    out.write(buf);
                    buf.clear();
                }
                resourceChannel.close();
                out.close();
                facesContext.responseComplete();
                
            } catch (IOException ioe) {
                String message = null;
                if (null != resource.getLibraryName()) {
                    message = "Unable to serve resource " + resource.getResourceName() + 
                            " in library " + resource.getLibraryName();
                }
                else {
                    message = "Unable to serve resource " + resource.getResourceName();
                }
                logger.log(Level.WARNING, message, ioe);
                throw ioe;
            }
        }
    }
    
    
    
    
}
