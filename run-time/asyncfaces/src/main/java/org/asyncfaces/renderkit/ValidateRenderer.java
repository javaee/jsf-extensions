/*
 *
 * Created on 16 novembre 2006, 20.01
 */

package org.asyncfaces.renderkit;

import java.io.IOException;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import org.asyncfaces.Parser;
import org.asyncfaces.Target;
import org.asyncfaces.component.UITooltipMessage;
import org.asyncfaces.encoder.AddClassName;

/**
 *
 * @author agori
 */
public class ValidateRenderer extends Renderer {
    
    public void decode(FacesContext context, UIComponent component) {
        super.decode(context, component);
        
        if (RenderUtils.decodeCommand(context, component)) {
        
        }
    }
    
    
    public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
        
        if (!component.isRendered()) {
            return;
        }
        
        UIComponent parent = component.getParent();
        String parentId = parent.getClientId(context);
        String event = RenderUtils.getAttr(context, component, "event");
        String addClassOnError = RenderUtils.getAttr(context, component, "addClassOnError");
        
        component.getAttributes().put("source", "this");
        component.getAttributes().put("execute", parentId);
        component.getAttributes().put("inputs", parentId);
        component.getAttributes().put("skipUpdate", true);
        
        Target target = new Target();
        target.setClientId(parentId);
        target.setServerHandler(AddClassName.class.getName());
        target.getParameters().put("className", addClassOnError);
        String element = Parser.compile(target);
        
        String render = RenderUtils.getAttr(context, component, "render");
        render = RenderUtils.addElementToArray(render, element);
        
        Map tooltipAssociations = 
                (Map) context.getExternalContext().
                getRequestMap().get(
                UITooltipMessage.ASSOCIATIONS_KEY);
        
        if (null != tooltipAssociations || !tooltipAssociations.isEmpty()) {
            String forId = parent.getId();
            UITooltipMessage msg = (UITooltipMessage) tooltipAssociations.get(forId);
            String msgId = msg.getClientId(context);
            render = RenderUtils.addElementToArray(render, msgId);
            tooltipAssociations.remove(forId);
        }
        
        component.getAttributes().put("render", render);
        
        StringBuffer sb = RenderUtils.renderAsyncFaces(context, component);
        parent.getAttributes().put(event, sb.toString());
    }
    
    
}
