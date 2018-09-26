/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.enverio.jsf.render;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.render.Renderer;

import com.enverio.jsf.UISuggest;

public class SuggestRenderer extends Renderer {
    
    protected final Logger log = Logger.getLogger(this.getClass().getName());

    public SuggestRenderer() {
        super();
    }

    public void decode(FacesContext faces, UIComponent component) {
        String value = this.getSubmittedValue(faces, component);
        ((UISuggest) component).setSubmittedValue(value);
    }
    
    protected String getSubmittedValue(FacesContext faces, UIComponent c) {
        String clientId = c.getClientId(faces);
        Map<String, String> p = faces.getExternalContext()
                .getRequestParameterMap();
        return p.get(clientId);
    }

    public boolean getRendersChildren() {
        return true;
    }

    protected void encodeAttributes(FacesContext faces, UISuggest s)
            throws IOException {
        String id = s.getClientId(faces);
        ResponseWriter rw = faces.getResponseWriter();

        rw.writeAttribute("id", id, "clientId");
        rw.writeAttribute("type", "text", null);
        rw.writeAttribute("autocomplete", "off", null);
        rw.writeAttribute("name", id, "clientId");
        
        rw.writeAttribute("value", this.getRenderedValue(faces, s), "value");
    }

    protected Object getRenderedValue(FacesContext faces, UISuggest s) {
        Object v = s.getSubmittedValue();
        if (v != null) {
            return v;
        } else {
            v = s.getValue();
            if (v != null) {
                Converter c = s.getConverter();
                if (c == null && !(v instanceof String)) {
                    try {
                        c = faces.getApplication()
                                .createConverter(v.getClass());
                    } catch (Exception e) {
                    }
                    if (c != null) {
                        v = c.getAsString(faces, s, v);
                    }
                }
            }
        }
        return (v != null) ? v : "";
    }

}
