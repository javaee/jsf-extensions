/*
 * $Id: AjaxZoneRenderer.java,v 1.4 2005/12/21 22:38:09 edburns Exp $
 */

/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License). You may not use this file except in
 * compliance with the License.
 *
 * You can obtain a copy of the License at
 * https://javaserverfaces.dev.java.net/CDDL.html or
 * legal/CDDLv1.0.txt.
 * See the License for the specific language governing
 * permission and limitations under the License.
 *
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at legal/CDDLv1.0.txt.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * [Name of File] [ver.__] [Date]
 *
 * Copyright 2005 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.faces.extensions.avatar.renderkit;

import com.sun.faces.extensions.avatar.lifecycle.AjaxLifecycle;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.render.Renderer;
import org.apache.shale.remoting.Mechanism;
import org.apache.shale.remoting.XhtmlHelper;

/**
 * This class renderers TextField components.
 */
public class AjaxZoneRenderer extends Renderer {
    

    // PENDING(craigmcc): I've filed SHALE-183 on this.  I'd like to get out of 
    // the business of maintaining all this JavaScript myself and let Shale 
    // take care of it.
    
    private static final String scriptIds[] = {
        "/META-INF/dojo/dojo.js",
        "/META-INF/prototype.js",
        "/META-INF/effects.js",
        "/META-INF/dragdrop.js",
        "/META-INF/controls.js",
        "/META-INF/com_sun_faces_ajax.js"
    };    

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        // Was our command the one that caused this submission?
        // we don' have to worry about getting the value from request parameter
        // because we just need to know if this command caused the submission. We
        // can get the command name by calling currentValue. This way we can 
        // get around the IE bug.
        String clientId = component.getClientId(context);
        Map<String,String> requestParameterMap = context.getExternalContext()
            .getRequestParameterMap();
        String value = requestParameterMap.get(clientId);
        if (value == null) {
            if (requestParameterMap.get(clientId + ".x") == null &&
                requestParameterMap.get(clientId + ".y") == null) {
                return;
            }
        }

        String type = (String) component.getAttributes().get("type");
        if ((type != null) && (type.toLowerCase().equals("reset"))) {
            return;
        }
        ActionEvent actionEvent = new ActionEvent(component);
        component.queueEvent(actionEvent);
    }

    /**
     * Render the beginning of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeBegin(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {

            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            return;
        }

        ResponseWriter writer = context.getResponseWriter();
        String id = component.getClientId(FacesContext.getCurrentInstance());
        writer.startElement("div", component);
        writer.writeAttribute("id", id, null);
        writeStyle(context, writer, component);
        if (!isAjaxRequest(context, component)) {
            writeAjaxifyScript(context, writer, component);
            for (int i = 0; i < scriptIds.length; i++) {
                getXhtmlHelper().linkJavascript(context, component, writer,
                        Mechanism.CLASS_RESOURCE, scriptIds[i]);
            }
            writeZoneAccruer(context, writer, component);
        }
    }
    
    private void writeStyle(FacesContext context, ResponseWriter writer, UIComponent comp) throws IOException {
        String style = null;
        ValueExpression styleExp = null;
        
        if (null == (style = (String) comp.getAttributes().get("style"))) {
            if (null != (styleExp = comp.getValueExpression("style"))) {
                style = (String) styleExp.getValue(context.getELContext());
            }
        }
        
        if (null != style) {
            writer.writeAttribute("style", style, "style");
        }
    }
    
    private void writeZoneAccruer(FacesContext context, ResponseWriter writer, UIComponent comp) throws IOException {
        writer.startElement("script", comp);
        writer.writeAttribute("language", "javascript", "language");
        writer.writeAttribute("type", "text/javascript", "language");
        boolean isXhtml = false;
        if (isXhtml = context.getExternalContext().getRequestMap().containsKey("com.sun.faces.ContentTypeIsXHTML")) {
            writer.write("\n<![CDATA[\n");
        }
        else {
            writer.write("\n<!--\n");
        }
        writer.write("\ng_zones.push(\"" + NamingContainer.SEPARATOR_CHAR +
                comp.getClientId(context) + "\");");
        if (isXhtml) {
            writer.write("\n]]>\n");
        }
        else {
            writer.write("\n//-->\n");
        }

        
        writer.endElement("script");
    }
    
    private void writeAjaxifyScript(FacesContext context, ResponseWriter writer, UIComponent comp) throws IOException {

        String 
                interactionType = null,
                eventHook = null,
                eventType = null,
                inspectElementHook = null,
                ajaxifyChildren = null;


        if (null == (interactionType = getAttr(context, comp, "interactionType"))) {
            return;
        }
        
        // If the interactionType is "output", take no ajaxification action.
        if (interactionType.equals("output")) {
            return;
        }
        
        if (!interactionType.equals("input")) {
            // PENDING(edburns): I18N
            throw new IOException("Valid values for optional attribute \"interactionType\" are \"input\" or \"output\".");
        }
        
        assert(interactionType.equals("input"));
        
        eventHook = getAttr(context, comp, "eventHook");
        eventType = getAttr(context, comp, "eventType");
        
        if (null == eventHook || null == eventType) {
            // PENDING: I18N
            throw new IOException("If \"interactionType\" is specified, both \"eventHook\" and \"eventType\" must be specified");
        }
        
        ajaxifyChildren = "ajaxifyChildren(this, \'" + eventType + "\', \'" + eventHook + "\')";
        writer.writeAttribute("onmouseover", ajaxifyChildren, "onmouseover");
        
        inspectElementHook = getAttr(context, comp, "inspectElementHook");
        if (null != inspectElementHook) {
            writer.writeAttribute("inspectElementHook", inspectElementHook, "inspectElementHook");
        }
    }
    
    private String getAttr(FacesContext context, UIComponent comp, String name) {
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

    /**
     * Render the children of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeChildren(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            return;
        }

        Iterator kids = component.getChildren().iterator();
        while (kids.hasNext()) {
            UIComponent kid = (UIComponent) kids.next(); 
            if (!kid.isRendered()) {
                continue;
            }
            kid.encodeAll(context);
        }
    }

    /**
     * Render the ending of the specified UIComponent to the output stream or
     * writer associated with the response we are creating.
     *
     * @param context FacesContext for the current request.
     * @param component UIComponent to be rendered.
     *
     * @exception IOException if an input/output error occurs.
     * @exception NullPointerException if context or component is null.
     */
    public void encodeEnd(FacesContext context, UIComponent component)
            throws IOException {
        if (context == null || component == null) {
            throw new NullPointerException();
        }
        if (!component.isRendered()) {
            return;
        }

        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div"); //NOI18N
    }

    /**
     * Return a flag indicating whether this Renderer is responsible
     * for rendering the children the component it is asked to render.
     * The default implementation returns false.
     */
    public boolean getRendersChildren() {
        return true; 
    }

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    // Helper method to test if this is an Ajax request
    private boolean isAjaxRequest(FacesContext context, UIComponent component) {
        Map<String, String> requestMap = 
                context.getExternalContext().getRequestHeaderMap();
        return requestMap.containsKey(AjaxLifecycle.ASYNC_HEADER);
    }
    
    private transient XhtmlHelper xHtmlHelper = null;
    
    private XhtmlHelper getXhtmlHelper() {
        if (null == xHtmlHelper) {
            xHtmlHelper = new XhtmlHelper();
        }
        return xHtmlHelper;
    }
}
