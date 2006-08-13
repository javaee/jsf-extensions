package com.enverio.jsf.render;

import com.enverio.client.ClientWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;

import javax.el.ELContext;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;

import com.enverio.jsf.UISuggest;
import org.apache.shale.remoting.Mechanism;
import org.apache.shale.remoting.XhtmlHelper;

public class ServerSuggestRenderer extends SuggestRenderer {

    public ServerSuggestRenderer() {
        super();
    }

    public void encodeBegin(FacesContext faces, UIComponent c)
            throws IOException {
        UISuggest s = (UISuggest) c;

        String id = c.getClientId(faces);

        ResponseWriter rw = faces.getResponseWriter();
        Map<String, Object> attr = c.getAttributes();

        rw.startElement("input", c);
        this.encodeAttributes(faces, s);
        rw.endElement("input");

        rw.startElement("div", s);
        rw.writeAttribute("class", "auto_complete", null);
        rw.writeAttribute("id", id + "_auto_complete", null);
        rw.endElement("div");
        
        XhtmlHelper xh = getXhtmlHelper();
        xh.linkJavascript(faces, c, rw,
                        Mechanism.CLASS_RESOURCE, "/META-INF/com.enverio.js");
        
        rw.startElement("script", c);
        rw.writeAttribute("type", "text/javascript", "type");
        StringBuffer sb = new StringBuffer(64);
        sb.append("new Enverio.Autocompleter('");
        sb.append(id);
        sb.append("','");
        sb.append(id);
        sb.append("_auto_complete');");
        rw.write(sb.toString());
        rw.endElement("script");
    }

    public void onSuggest(FacesContext faces, UIComponent o) throws IOException {
        UISuggest c = (UISuggest) o;
        String clientId = c.getClientId(faces);

        String value = this.getSubmittedValue(faces, c);
        if (value == null) {
            value = "";
        }
        if (log.isLoggable(Level.FINE)) {
            log.fine("Suggest["+clientId+"] <- "+value);
        }

        ELContext ctx = faces.getELContext();
        Object obj = ((UISuggest) c).getFrom().invoke(ctx,
                new Object[] { value });

        if (obj instanceof Iterable) {
            Iterable itr = (Iterable) obj;

            // collect settings
            boolean hasChildren = c.getChildCount() != 0;
            String var = (String) c.getAttributes().get("var");
            if (var == null)
                var = "item";

            // converter?
            Converter converter = c.getConverter();

            // just in case
            Map<String, Object> req = faces.getExternalContext()
                    .getRequestMap();
            Object orig = req.get(var);

            ResponseWriter rw = faces.getResponseWriter();
            rw.startElement("ul", c);
            for (Object i : itr) {
                rw.startElement("li", c);

                // determine how to render body of li
                if (hasChildren) {
                    req.put(var, i);
                    this.encodeItem(faces, c);
                } else if (converter != null) {
                    rw.writeText(converter.getAsString(faces, c, i), null);
                } else {
                    rw.writeText(i, null);
                }

                rw.endElement("li");
            }
            rw.endElement("ul");

            // reset old value
            if (hasChildren) {
                if (orig != null) {
                    req.put(var, orig);
                } else {
                    req.remove(var);
                }
            }
        }
    }

    public boolean getRendersChildren() {
        return true;
    }
    
    protected void encodeItem(FacesContext context, UIComponent component) throws IOException {
        if (component.getChildCount() == 0) return;
        Iterator kids = component.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next();
            kid.encodeAll(context);
        }
    }

    public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
        // do nothing
    }

    public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
        // do nothing
    }
    
    
    private transient XhtmlHelper xHtmlHelper = null;
    
    private XhtmlHelper getXhtmlHelper() {
        if (null == xHtmlHelper) {
            xHtmlHelper = new XhtmlHelper();
        }
        return xHtmlHelper;
    }
    

}
