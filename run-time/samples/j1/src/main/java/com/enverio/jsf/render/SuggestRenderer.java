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
