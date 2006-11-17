/*
 *
 * Created on 17 novembre 2006, 12.20
 */

package org.asyncfaces.renderkit;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIMessage;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
import org.apache.shale.remoting.Mechanism;
import org.apache.shale.remoting.XhtmlHelper;
import org.asyncfaces.component.UITooltipMessage;

/**
 *
 * @author agori
 */
public class TooltipMessageRenderer extends Renderer {
    
    private final static String TOOLTIP_RESOURCE = "/META-INF/tooltip-v0.1.js";
    
    /*
     *Copied from RI
     */
    private UIComponent findUIComponentBelow(UIComponent startPoint, String forComponent) {
        UIComponent retComp = null;
        List<UIComponent> children = startPoint.getChildren();
        for (int i = 0, size = children.size(); i < size; i++) {
            UIComponent comp = children.get(i);
            
            if (comp instanceof NamingContainer) {
                try {
                    retComp = comp.findComponent(forComponent);
                } catch (IllegalArgumentException iae) {
                    continue;
                }
            }
            
            if (retComp == null) {
                if (comp.getChildCount() > 0) {
                    retComp = findUIComponentBelow(comp, forComponent);
                }
            }
            
            if (retComp != null)
                break;
        }
        return retComp;
    }
    
    
    /*
     *copied from RI
     */
    protected UIComponent getForComponent(FacesContext context,
            String forComponent, UIComponent component) {
        if (null == forComponent || forComponent.length() == 0) {
            return null;
        }
        
        UIComponent result = null;
        UIComponent currentParent = component;
        try {
            // Check the naming container of the current
            // component for component identified by
            // 'forComponent'
            while (currentParent != null) {
                // If the current component is a NamingContainer,
                // see if it contains what we're looking for.
                result = currentParent.findComponent(forComponent);
                if (result != null)
                    break;
                // if not, start checking further up in the view
                currentParent = currentParent.getParent();
            }
            
            // no hit from above, scan for a NamingContainer
            // that contains the component we're looking for from the root.
            if (result == null) {
                result =
                        findUIComponentBelow(context.getViewRoot(), forComponent);
            }
        } catch (Exception e) {
            // ignore - log the warning
        }
        return result;
    }
    
    /*
     *Copied from RI
     */
    protected void encodeMessage(FacesContext context, UIComponent component,
            UIComponent forComponent,
            FacesMessage curMessage,
            ResponseWriter writer) throws IOException {
        
        String
                summary = null,
                detail = null,
                severityStyle = null,
                severityStyleClass = null;
        boolean
                showSummary = ((UIMessage) component).isShowSummary(),
                showDetail = ((UIMessage) component).isShowDetail();
        
        // make sure we have a non-null value for summary and
        // detail.
        summary = (null != (summary = curMessage.getSummary())) ?
            summary : "";
        // Default to summary if we have no detail
        detail = (null != (detail = curMessage.getDetail())) ?
            detail : summary;
        
        if (curMessage.getSeverity() == FacesMessage.SEVERITY_INFO) {
            severityStyle =
                    (String) component.getAttributes().get("infoStyle");
            severityStyleClass = (String)
            component.getAttributes().get("infoClass");
        } else if (curMessage.getSeverity() == FacesMessage.SEVERITY_WARN) {
            severityStyle =
                    (String) component.getAttributes().get("warnStyle");
            severityStyleClass = (String)
            component.getAttributes().get("warnClass");
        } else if (curMessage.getSeverity() == FacesMessage.SEVERITY_ERROR) {
            severityStyle =
                    (String) component.getAttributes().get("errorStyle");
            severityStyleClass = (String)
            component.getAttributes().get("errorClass");
        } else if (curMessage.getSeverity() == FacesMessage.SEVERITY_FATAL) {
            severityStyle =
                    (String) component.getAttributes().get("fatalStyle");
            severityStyleClass = (String)
            component.getAttributes().get("fatalClass");
        }
        
        String
                style = (String) component.getAttributes().get("style"),
                styleClass = (String) component.getAttributes().get("styleClass");
        
        // if we have style and severityStyle
        if ((style != null) && (severityStyle != null)) {
            // severityStyle wins
            style = severityStyle;
        }
        // if we have no style, but do have severityStyle
        else if ((style == null) && (severityStyle != null)) {
            // severityStyle wins
            style = severityStyle;
        }
        
        // if we have styleClass and severityStyleClass
        if ((styleClass != null) && (severityStyleClass != null)) {
            // severityStyleClass wins
            styleClass = severityStyleClass;
        }
        // if we have no styleClass, but do have severityStyleClass
        else if ((styleClass == null) && (severityStyleClass != null)) {
            // severityStyleClass wins
            styleClass = severityStyleClass;
        }
        
        //Done intializing local variables. Move on to rendering.
        if (styleClass != null) {
            writer.writeAttribute("class", styleClass, "styleClass");
        }
        
        if (style != null) {
            writer.writeAttribute("style", style, "style");
        }
        
        writer.flush();
        
        if (showSummary) {
            writer.writeText("\t", component, null);
            writer.writeText(summary, component, null);
            writer.writeText(" ", component, null);
        }
        
        if (showDetail) {
            writer.writeText(detail, component, null);
        }
    }
    
    public void encodeBegin(FacesContext context,
            UIComponent component) throws IOException {
        
        if (!component.isRendered()) {
            return;
        }
        
        UITooltipMessage message = (UITooltipMessage) component;
        Map attributes = message.getAttributes();
        
        ResponseWriter writer = context.getResponseWriter();
        
        writer.startElement("span", component);
        writer.writeAttribute("id", component.getClientId(context), "id");
        
        String dir = (String) attributes.get("dir");
        if (dir != null) {
            writer.writeAttribute("dir", dir, null);
        }
        
        String lang = (String) attributes.get("lang");
        if (lang != null) {
            writer.writeAttribute("lang", lang, null);
        }
        
        if (message.getRenderOnlyIfAjax() && !AsyncResponse.isAjaxRequest()) {
            
            new XhtmlHelper().linkJavascript(context, component, context.getResponseWriter(),
                    Mechanism.CLASS_RESOURCE, TOOLTIP_RESOURCE);
            return;
        }
        
        String _for = message.getFor();
        UIComponent forComponent = getForComponent(context, _for, component);
        
        Iterator<FacesMessage> msgIt = context.getMessages(
                forComponent.getClientId(context));
        
        if (!msgIt.hasNext()) {
            new XhtmlHelper().linkJavascript(context, component, context.getResponseWriter(),
                    Mechanism.CLASS_RESOURCE, TOOLTIP_RESOURCE);
            return;
        }
        
        FacesMessage msg = msgIt.next();
        
        encodeMessage(context, component, forComponent, msg, writer);
        
        new XhtmlHelper().linkJavascript(context, component, context.getResponseWriter(),
                Mechanism.CLASS_RESOURCE, TOOLTIP_RESOURCE);
        
        StringBuffer sb = new StringBuffer();
        sb.append("new Tooltip('");
        sb.append(forComponent.getClientId(context));
        sb.append("', '");
        sb.append(component.getClientId(context));
        sb.append("');");
        
        RenderUtils.writeScriptInline(context, sb.toString());
    }
    
    public void encodeEnd(FacesContext context,
            UIComponent component) throws IOException {
        
        if (!component.isRendered()) {
            return;
        }
        ResponseWriter writer = context.getResponseWriter();
        
        writer.endElement("span");
    }
    
    
}
