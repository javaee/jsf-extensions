/*
 * SpecialOutputTextRenderer.java
 *
 * Created on August 4, 2006, 11:10 AM
 *
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
 * Copyright 2006 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.faces.simple_event;

import com.sun.faces.extensions.common.util.Util;
import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.render.Renderer;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.RenderKitFactory;
 
/**
 *
 * @author edburns
 */
public class SpecialOutputTextRenderer extends Renderer {
 
    /** Creates a new instance of RedOutputTextRenderer */
    public SpecialOutputTextRenderer() {
    }
 
    private Renderer textRenderer = null;
 
    private Renderer getStandardTextRenderer(FacesContext context) {
       if (null != textRenderer) {
           return textRenderer;
       }
 
       textRenderer = Util.getRenderKit(context, 
               RenderKitFactory.HTML_BASIC_RENDER_KIT).
                getRenderer("javax.faces.Output", "javax.faces.Text");
       assert(null != textRenderer);
 
       return textRenderer;
    }
 
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("i", component);
        writer.startElement("blink", component);
        writer.startElement("font", component);
        writer.writeAttribute("color", "red", "color");
        getStandardTextRenderer(context).encodeBegin(context,component);
    }
 
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        getStandardTextRenderer(context).encodeChildren(context,component);
    }
 
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        getStandardTextRenderer(context).encodeEnd(context,component);
        writer.endElement("font");
        writer.endElement("blink");
        writer.endElement("i");
    }
    
    public void addToRequestScope(FacesContext context, UIComponent component) {
        context.getExternalContext().getRequestMap().put("addedFromRenderer", 
                "This message added from renderer at " + System.currentTimeMillis());
    }
} 
