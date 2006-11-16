/*
 *
 * Created on 12 novembre 2006, 19.48
 */

package org.asyncfaces.encoder;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import com.sun.faces.extensions.avatar.lifecycle.ComponentEncoder;
import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import org.asyncfaces.AsyncFacesEncoderHandler;
import org.asyncfaces.Target;

/**
 *
 * @author agori
 */
abstract public class BaseEncoder implements ComponentEncoder {
    
    protected UIComponent comp;
    protected FacesContext context;
    
    abstract public Map<String, String> getParameters();
    
    protected Target getTarget(String clientId) {
        AsyncFacesEncoderHandler handler =
                (AsyncFacesEncoderHandler) AsyncResponse.
                getInstance().
                getEncodeHandlerInstance();
        return handler.getTarget(clientId);
    }
    
    public void setComponent(UIComponent comp) {
        this.context = FacesContext.getCurrentInstance();
        this.comp = comp;
    }
    
    public void encodeExtra() throws IOException {
        AsyncResponse async = AsyncResponse.getInstance();
        ResponseWriter writer = async.getResponseWriter();
        
        Map<String, String> parameters = getParameters();
        if (null != parameters) {
            writer.startElement("parameters", comp);
            for (Map.Entry entry : parameters.entrySet()) {
                writer.startElement("param", null);
                writer.writeAttribute("name", entry.getKey(), "name");
                writer.write("<![CDATA[");
                Object value = entry.getValue();
                if (value != null) {
                    writer.write(entry.getValue().toString());
                }
                writer.write("]]>");
                writer.endElement("param");
            }
            writer.endElement("parameters");
        }
    }
    
    
}
