/*
 *
 * Created on 14 novembre 2006, 17.02
 */

package org.asyncfaces.renderkit;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;

/**
 *
 * @author agori
 */
public class SupportRenderer extends Renderer {
    
    public void decode(FacesContext context, UIComponent component) {
        RenderUtils.decodeCommand(context, component);
    }
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        UIComponent parent = component.getParent();
        String parentId = parent.getClientId(context);
        
        String event = RenderUtils.getAttr(context, component, "event");
        String execute = RenderUtils.getAttr(context, component, "execute");
        String inputs = RenderUtils.getAttr(context, component, "inputs");
        boolean submitForm = RenderUtils.getAttrBoolean(context, component, "submitForm");
        
        
        
        execute = RenderUtils.addElementToArray(execute, parentId);
        if (!submitForm) {
            inputs = RenderUtils.addElementToArray(inputs, parentId);
        } else {
            inputs = null;
        }
        
        component.getAttributes().put("source", "this");
        component.getAttributes().put("execute", execute);
        component.getAttributes().put("inputs", inputs);
        
        StringBuffer sb = RenderUtils.renderAsyncFaces(context, component);
        String script = sb.toString();
        
        
        
        String e = RenderUtils.getAttr(context, parent, event);
        if (e != null) {
            script = e + ";" + script;
        }
        
        parent.getAttributes().put(event, "return " + script);
        
    }
    
    
    
}
