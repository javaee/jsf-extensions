/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2006-2018 Oracle and/or its affiliates. All rights reserved.
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
