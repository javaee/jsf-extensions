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

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.component.ValueHolder;
import javax.faces.render.Renderer;

import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

 
/**
 *
 * @author edburns
 */
public class SpecialOutputTextRenderer extends Renderer {
 
    /** Creates a new instance of RedOutputTextRenderer */
    public SpecialOutputTextRenderer() {
    }
 
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
    }
 
    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
    }
 
    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("span", component);
        writer.writeAttribute("id", component.getClientId(context), "id");
        writer.write("<![CDATA[ Character data in the span, before the <i> ( ]]>");
        writer.startElement("i", component);
        writer.startElement("blink", component);
        writer.startElement("font", component);
        writer.writeAttribute("color", "red", "color");
        writer.writeText(" ", "blank");
        writer.write("<![CDATA[ Character data before the message ( ]]>");
        writer.writeText(getValue(context, component), component, "value");
        writer.write("<![CDATA[ ) character data after the message. ]]>");        
        writer.endElement("font");
        writer.endElement("blink");
        writer.endElement("i");
        writer.write("<![CDATA[ ) Character data in the span, after the </i> ]]>");
        writer.endElement("span");
    }
    
    public void addToRequestScope(FacesContext context, UIComponent component) {
        context.getExternalContext().getRequestMap().put("addedFromRenderer", 
                "This message added from renderer at " + System.currentTimeMillis());
    }
    
    protected String getValue(FacesContext context, UIComponent component) {
        String result = "";
        
        if (component instanceof ValueHolder) {
            Object value = ((ValueHolder) component).getValue();
            if (null != value) {
                result = value.toString();
            }
        }

        return result;
    }
} 
