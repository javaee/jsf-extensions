package com.enverio.client;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

public class ClientWriter extends ResponseWriterWrapper {
    
    
    private final static ThreadLocal<ClientWriter> Instance = new ThreadLocal<ClientWriter>();
    
    public static boolean exists() {
        return Instance.get() != null;
    }
    
    public static ClientWriter getInstance(boolean create) {
        ClientWriter cw = Instance.get();
        if (cw == null && create) {
            try {
                cw = new ClientWriter(AsyncResponse.getInstance().getResponseWriter());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            
            Instance.set(cw);
        }
        return cw;
    }
    
    public static ClientWriter getInstance() {
        return getInstance(true);
    }
    
    public static void clearInstance() {
        ClientWriter instance = getInstance(false);
        Instance.remove();
    }
    

    private final ResponseWriter writer;

    private final StringBuffer sb = new StringBuffer(16);

    private ClientWriter(ResponseWriter writer) {
        this.writer = writer;
    }

    public ResponseWriter getWrapped() {
        return this.writer;
    }

    private StringBuffer getBuffer() {
        this.sb.setLength(0);
        return sb;
    }
    
    public boolean scriptResource(FacesContext faces, String resource) throws IOException {
        if (resource.charAt(0) != '/') {
            throw new IOException("Resource must begin with '/'");
        }
        Set<String> rsc = getResourcesWritten(faces);
        if (rsc.contains(resource)) return false;
        StringBuffer sb = new StringBuffer(resource.length() + 11);
        sb.append("resource://");
        sb.append(resource.substring(1));
        this.writer.startElement("script", null);
        this.writer.writeAttribute("type","text/javascript", null);
        this.writer.writeURIAttribute("src", faces.getApplication().getViewHandler().getResourceURL(faces, sb.toString()), null);
        this.writer.endElement("script");
        rsc.add(resource);
        return true;
    }
    
    protected Set<String> getResourcesWritten(FacesContext faces) {
        Map<String, Object> attr = faces.getExternalContext().getRequestMap();
        Set<String> s = (Set<String>) attr.get("facelets.ResourcesWritten");
        if (s == null) {
            s = new HashSet<String>();
            attr.put("facelets.ResourcesWritten", s);
        }
        return s;
    }
    
    public ClientWriter startScript() throws IOException {
        FacesContext context = FacesContext.getCurrentInstance();
        this.startScript(context.getViewRoot());
        return this;
    }
    
    public ClientWriter startScript(UIComponent c) throws IOException {
        this.writer.startElement("script", c);
        this.writer.writeAttribute("type","text/javascript", null);
        this.writer.writeText("", null); // close start element
        return this;
    }
    
    public ClientWriter endScript() throws IOException {
        this.writer.endElement("script");
        this.writer.flush();
        return this;
    }

    public ClientWriter id(String id, Script... s) throws IOException {
        if (s == null)
            return this;
        for (Script i : s) {
            i.write(this.getBuffer().append("$('").append(id).append("')")
                    .toString(), this);
        }
        return this;
    }

    public ClientWriter select(UIComponent c, Script... s) throws IOException {
        if (c == null || s == null) return this;
        FacesContext faces = FacesContext.getCurrentInstance();
        return this.id(c.getClientId(faces), s);
    }

    public ClientWriter select(String selector, Script... s) throws IOException {
        if (s == null || selector == null)
            return this;
        this.writer.write(this.getBuffer().append("$$('").append(selector)
                .append("').each(function(e){").toString());
        for (Script i : s) {
            i.write("e", this);
        }
        this.writer.write("});");
        return this;
    }

    public ResponseWriter cloneWithWriter(Writer writer) {
        return new ClientWriter(super.cloneWithWriter(writer));
    }
}
