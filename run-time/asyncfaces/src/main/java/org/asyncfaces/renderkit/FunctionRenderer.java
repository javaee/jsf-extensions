/*
 *
 * Created on 14 novembre 2006, 14.39
 */

package org.asyncfaces.renderkit;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import org.asyncfaces.component.UIAjaxAction;

/**
 *
 * @author agori
 */
public class FunctionRenderer extends Renderer {
    
    public void decode(FacesContext context, UIComponent component) {
        RenderUtils.decodeCommand(context, component);
    }
    
    public void encodeBegin(FacesContext context, UIComponent comp) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        UIAjaxAction ajax = (UIAjaxAction) comp;
        
        
        String name = (String) RenderUtils.getAttr(context, comp, "name");
       
        StringBuffer sb = new StringBuffer();
        sb.append("function ");
        sb.append(name);
        sb.append(" () {");
        
        sb.append("\n");
        sb.append("return ");
        
        sb.append(RenderUtils.renderAsyncFaces(context, comp).toString());
        
        sb.append("\n");
        sb.append("}");
        
        writer.startElement("span", ajax);
        writer.writeAttribute("id", ajax.getClientId(context), "id");
        
        writer.startElement("script", ajax);
        writer.writeAttribute("type", "text/javascript", "type");
        writer.writeText(sb.toString(), null);
        writer.endElement("script");
        
        writer.endElement("span");
    }
    
}
