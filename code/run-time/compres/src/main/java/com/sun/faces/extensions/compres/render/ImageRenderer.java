/*
 * ScriptRenderer.java
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


package com.sun.faces.extensions.compres.render;

import java.io.IOException;
import javax.faces.application.Resource;
import javax.faces.component.UIComponent;
import javax.faces.component.UIGraphic;
import javax.faces.context.FacesContext;
import javax.faces.render.RenderKit;
import javax.faces.render.Renderer;

/**
 *
 * @author edburns
 */
public class ImageRenderer extends Renderer {
    
    private Renderer graphicImageRenderer = null;
    
    public Renderer getGraphicImageRenderer() {
        if (null == graphicImageRenderer) {
            RenderKit kit = FacesContext.getCurrentInstance().getRenderKit();
            graphicImageRenderer = kit.getRenderer("javax.faces.Graphic", 
                    "javax.faces.Image");
        }
        return graphicImageRenderer;
    }
    
    
    public void encodeBegin(FacesContext context, UIComponent comp) throws IOException {
        if (!(comp instanceof UIGraphic)) {
            return;
        }
        UIGraphic graphic = (UIGraphic)comp;
        Object oldValue = graphic.getValue();
        Resource resource = ResourceRendererHelper.getResource(context, comp);
        String uri = resource.getURI();
        graphic.setValue(uri);
        getGraphicImageRenderer().encodeBegin(context, comp);
    }

    public void encodeEnd(FacesContext context, UIComponent comp) throws IOException {
        UIGraphic graphic = (UIGraphic)comp;
        Object oldValue = graphic.getValue();
        Resource resource = ResourceRendererHelper.getResource(context, comp);
        String uri = resource.getURI();
        // Special Case, have to trim off the contextRoot from the front of
        // URI because the graphicImageRenderer will duplicate it.
        String contextRoot = context.getExternalContext().getRequestContextPath();
        uri = uri.substring(contextRoot.length());
        graphic.setValue(uri);
        getGraphicImageRenderer().encodeEnd(context, comp);
    }

    public void encodeChildren(FacesContext context, UIComponent comp) throws IOException {
        UIGraphic graphic = (UIGraphic)comp;
        Object oldValue = graphic.getValue();
        Resource resource = ResourceRendererHelper.getResource(context, comp);
        String uri = resource.getURI();
        graphic.setValue(uri);
        getGraphicImageRenderer().encodeChildren(context, comp);
    }

    public boolean getRendersChildren() {
        boolean retValue;
        
        retValue = true;
        return retValue;
    }
    
    
    

    
}
