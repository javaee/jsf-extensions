/*
 *
 * Created on 14 novembre 2006, 14.30
 */

package org.asyncfaces.renderkit;

import java.util.List;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.asyncfaces.Parser;
import org.asyncfaces.Target;
import org.asyncfaces.component.UIAjaxAction;

/**
 *
 * @author agori
 */
public class RenderUtils {
    
    static public String getAttr(FacesContext context, UIComponent comp, String name) {
        String result = null;
        ValueExpression ve;
        
        if (null != (ve = comp.getValueExpression(name))) {
            result = (String) ve.getValue(context.getELContext());
        }
        if (null == result) {
            if (null == (result = (String) comp.getAttributes().get(name))) {
                return null;
            }
        }
        
        return result;
    }
    
    static public Boolean getAttrBoolean(FacesContext context, UIComponent comp, String name) {
        Boolean result = false;
        ValueExpression ve;
        
        if (null != (ve = comp.getValueExpression(name))) {
            result = (Boolean) ve.getValue(context.getELContext());
        }
        if (null == result) {
            String temp = (String) comp.getAttributes().get(name);
            
            if (null == temp) {
                return false;
            } else {
                result = Boolean.TRUE.toString().equals(temp);
            }
        }
        
        return result;
    }
    
    static public String addElementToArray(String array, String element) {
        if (array != null) {
            return array + "," + element;
        } else {
            return element;
        }
    }
    
    public static boolean decodeCommand(FacesContext context, UIComponent comp) {
        if (context.getExternalContext().
                getRequestParameterMap().containsKey(
                comp.getClientId(context))) {
            
            UIAjaxAction ajax = (UIAjaxAction) comp;
            
            ActionEvent actionEvent = new ActionEvent(comp);
            comp.queueEvent(actionEvent);
            return true;
        }
        return false;
    }
    
    public static StringBuffer renderAsyncFaces(FacesContext context, UIComponent comp) {
        UIAjaxAction ajax = (UIAjaxAction) comp;
        
        String render = (String) RenderUtils.getAttr(context, comp, "render");
        String execute = (String) RenderUtils.getAttr(context, comp, "execute");
        boolean skipUpdate = (Boolean) RenderUtils.getAttrBoolean(context, comp, "skipUpdate");
        String form = (String) RenderUtils.getAttr(context, comp, "form");
        String inputs = (String) RenderUtils.getAttr(context, comp, "inputs");
        String source = (String) RenderUtils.getAttr(context, comp, "source");
        String parameters = (String) RenderUtils.getAttr(context, comp, "parameters");
        
        List<Target> targets = ajax.buildTargets();
        
        String clientId = comp.getClientId(context);
        
        for (Target target : targets) {
            String compiledRenderText = Parser.compile(target);
            render = RenderUtils.addElementToArray(render, compiledRenderText);
        }
        
        inputs = RenderUtils.addElementToArray(inputs, clientId);
        execute = RenderUtils.addElementToArray(execute, clientId);
        
        if (parameters != null) {
            parameters += "&" + clientId + "=";
        } else {
            parameters = clientId + "=";
        }
        
        StringBuffer sb = new StringBuffer();
        
        sb.append("DynaFaces.fireAjaxTransaction(");
        sb.append(source);
        sb.append(", {");
        
        boolean somePropertyWritten = false;
        
        
        
        if (render != null) {
            sb.append("render: '" + render + "'");
            somePropertyWritten = true;
        }
        
        if (execute != null) {
            if (somePropertyWritten) {
                sb.append(",");
            }
            sb.append("execute: '" + execute + "'");
        }
        
        if (inputs != null) {
            if (somePropertyWritten) {
                sb.append(",");
            }
            sb.append("inputs: '" + inputs + "'");
        }
        
        if (skipUpdate) {
            if (somePropertyWritten) {
                sb.append(",");
            }
            sb.append("skipUpdate: " + skipUpdate);
        }
        
        if (form != null) {
            if (somePropertyWritten) {
                sb.append(",");
            }
            sb.append("form: '" + form + "'");
        }
        
        if (parameters != null) {
            if (somePropertyWritten) {
                sb.append(",");
            }
            sb.append("parameters: '" + parameters + "'");
        }
        
        sb.append("});");
        
        return sb;
    }
    
    
    
}
