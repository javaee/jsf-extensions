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

import com.sun.faces.extensions.avatar.components.AjaxZone;
import com.sun.faces.extensions.avatar.components.ScriptsComponent;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import com.sun.faces.extensions.common.util.Util;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.el.MethodExpression;
import javax.el.ValueExpression;
import javax.faces.FacesException;
import javax.faces.component.ActionSource;
import javax.faces.component.EditableValueHolder;

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
    

    // PENDING(craigmcc): I've filed SHALE-183 on this.  I'd like to get
    // out of the business of maintaining all this JavaScript myself and
    // let Shale take care of it.
    
    private static final String scriptIds[] = {
        "/META-INF/libs/scriptaculous/version1.6.4/prototype.js",
        "/META-INF/com_sun_faces_ajax.js",
        "/META-INF/com_sun_faces_ajax_zone.js"
    };
    
    private static final String scriptLinkKeys[] = {
        ScriptsComponent.PROTOTYPE_JS_LINKED,
        ScriptsComponent.AJAX_JS_LINKED,
        AjaxZone.ZONE_JS_LINKED        
    };
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    public void decode(FacesContext context, UIComponent component) {
        if (context == null || component == null) {
            throw new NullPointerException();
        }

        // Was our command the one that caused this submission?  we don'
        // have to worry about getting the value from request parameter
        // because we just need to know if this command caused the
        // submission. We can get the command name by calling
        // currentValue. This way we can get around the IE bug.
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
     * Render the beginning of the specified UIComponent to the output
     * stream or writer associated with the response we are creating.
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
        writeStyle(context, writer, component, false);
        writeStyle(context, writer, component, true);
    }
    
    private void writeStyle(FacesContext context, ResponseWriter writer, 
			    UIComponent comp, boolean isStyleClass) 
	throws IOException {
        String styleValue = null;
        ValueExpression styleExp = null;
        String styleProperty = isStyleClass ? "styleClass" : "style";
        
        if (null == (styleValue = (String) comp.getAttributes().
		     get(styleProperty))) {
            if (null != (styleExp = comp.getValueExpression(styleProperty))) {
                styleValue = (String) styleExp.getValue(context.
							getELContext());
            }
        }
        
        if (null != styleValue) {
            writer.writeAttribute(styleProperty, styleValue, styleProperty);
        }
    }
    
    /**
     * <p>Take different action depending on the value of isAjaxResuest 
     * component attribute and the result of calling 
     * <code>getInteractionType</code>.</p>
     *
     * <p>If isAjaxRequest, and interactionType is null or "output",
     * take no action.</p>
     *
     * <p>If !isAjaxRequest, and interactionType is null or "output",
     * write only the zone accruer.</p>
     *
     * <p>If !isAjaxRequest, and interactionType is non-null and
     * "input", write the zone accrual and ajaxifyChildren script.</p>
     *
     * <p>If isAjaxRequest, and interactionType is "input", write only
     * the ajaxifyChildren script.</P>
     *
     * <p>
     */
    
    private void writeAjaxifyScripts(FacesContext context, 
				     ResponseWriter writer, AjaxZone comp,
            boolean isAjaxRequest) throws IOException {
        String
                clientId = null,
                getCallbackData = null,
                collectPostData = null,
                eventType = null,
                inspectElement = null,
                replaceElement = null,
                postReplace = null,
                execute = null,
                render = null;
        StringBuffer ajaxifyChildren = null;
        MethodExpression action = null;
        boolean typeIsOutput, writeZoneAccruer, writeAjaxifyChildren;
        InteractionType interactionType;
        try {
	    writer.startElement("script", comp);
	    writer.writeAttribute("language", "javascript", "language");
	    writer.writeAttribute("type", "text/javascript", "language");
            List<AjaxZone> zoneList = comp.getAllZoneList();
            

            writer.write("\nvar curZone = null;\n");
            // Write the zone accruer
            for (AjaxZone currentZone : zoneList) {

                interactionType = getInteractionType(context, currentZone);
                typeIsOutput = (interactionType == InteractionType.output);
                writeZoneAccruer = ((!isAjaxRequest && typeIsOutput) ||
                        (!isAjaxRequest && !typeIsOutput));

                if (!currentZone.isRendered()) {
                    continue;
                }
                
                if (isAjaxRequest && typeIsOutput) {
                    continue;
                }
	    
                clientId = currentZone.getClientId(context);
                if (writeZoneAccruer) {
                    writer.write("\nDynaFacesZones.g_zones.push(\"" +
				 clientId + "\");");
                }
                execute = getAttr(context, currentZone, "execute");
                render = getAttr(context, currentZone, "render");
                if (null != execute || null != render) {
                    writer.write("\ncurZone = document.getElementById(\"" +
                            clientId + "\");");
                }
                if (null != execute) {
                    writer.write("\ncurZone[\"execute\"] = \"" + execute + 
				 "\";");
                }
                if (null != render) {
                    writer.write("\ncurZone[\"render\"] = \"" + render + 
				 "\";");
                }
            }
            
            // Write the ajaxifyChildren
            for (AjaxZone currentZone : zoneList) {

                interactionType = getInteractionType(context, currentZone);
                typeIsOutput = (interactionType == InteractionType.output);
                writeAjaxifyChildren = ((!isAjaxRequest && !typeIsOutput) ||
                        (isAjaxRequest && !typeIsOutput));

                if (!currentZone.isRendered()) {
                    continue;
                }
                
                if (isAjaxRequest && typeIsOutput) {
                    continue;
                }
	    
                clientId = currentZone.getClientId(context);

                if (writeAjaxifyChildren) {
                    boolean wroteAttribute = false;
                    collectPostData = getAttr(context, currentZone, 
					      "collectPostData");
                    eventType = getAttr(context, currentZone, "eventType");
                    getCallbackData = getAttr(context, currentZone, 
					      "getCallbackData");
                    inspectElement = getAttr(context, currentZone, 
					     "inspectElement");
                    replaceElement = getAttr(context, currentZone, 
					     "replaceElement");
                    postReplace = getAttr(context, currentZone, 
					  "postReplace");

                    ajaxifyChildren = new StringBuffer();
                    ajaxifyChildren.append("\nDynaFacesZones." +
					   "ajaxifyChildren($(\'" + 
					   clientId + "\'), ");
                    ajaxifyChildren.append("{ ");
                    if (null != collectPostData) {
                        wroteAttribute = true;
                        ajaxifyChildren.append(" collectPostData: \'" + 
					       collectPostData + "\'");
                    }
                    if (null != eventType) {
                        if (wroteAttribute) {
                            ajaxifyChildren.append(", ");
                        }
                        wroteAttribute = true;
                        ajaxifyChildren.append("eventType: \'" + eventType +
					       "\'");
                    }
                    if (null != inspectElement) {
                        if (wroteAttribute) {
                            ajaxifyChildren.append(", ");
                        }
                        wroteAttribute = true;
                        ajaxifyChildren.append("inspectElement: \'" + 
					       inspectElement + "\'");
                    }
                    if (null != replaceElement) {
                        if (wroteAttribute) {
                            ajaxifyChildren.append(", ");
                        }
                        wroteAttribute = true;
                        ajaxifyChildren.append("replaceElement: \'" + 
					       replaceElement + "\'");
                    }
                    if (null != postReplace) {
                        if (wroteAttribute) {
                            ajaxifyChildren.append(", ");
                        }
                        wroteAttribute = true;
                        ajaxifyChildren.append("postReplace: \'" + 
					       postReplace + "\'");
                    }
                    if (null != (action = currentZone.getActionExpression())) {
                        if (wroteAttribute) {
                            ajaxifyChildren.append(", ");
                        }
                        wroteAttribute = true;
                        ajaxifyChildren.append("action: \'" + 
					       action.getExpressionString() + 
					       "\'");
                    }
                    ajaxifyChildren.append(" }");
                    if (null != getCallbackData) {
                        ajaxifyChildren.append(", \'" + getCallbackData + 
					       "\'");
                    }
                    ajaxifyChildren.append(");");

                    writer.write(ajaxifyChildren.toString());
                }

            }
	}
	finally {
	    writer.endElement("script");
	}

    }
    
    private enum InteractionType { input, output }
    
    /**
     * <p>Return input if this component has one or more children that
     * are <code>EditableValueHolder</code> instances.  Return output
     * otherwise.</p>
     */ 
    
    private InteractionType getInteractionType(FacesContext context, 
					       final AjaxZone component) {
        Util.TreeTraversalCallback findEditableValueHolder = 
                new Util.TreeTraversalCallback() {
            public boolean takeActionOnNode(FacesContext context,
					    UIComponent curNode)
		throws FacesException {
                boolean keepGoing = true;
                // Skipping the zone itself, return false if the curNode
                // is an EditableValueHolder or ActionSource
                if ((curNode != component) && 
                    (curNode instanceof EditableValueHolder || 
                    curNode instanceof ActionSource)) {
                    keepGoing = false;
                }
                return keepGoing;
            }
        };
        // the view traversal will return false iff there is one or more 
        // EditableValueHolder in this zone.
        InteractionType result = 
         Util.prefixViewTraversal(context, component, 
				  findEditableValueHolder) ?
         InteractionType.output : InteractionType.input;
        return result;
    }
    
    private String getAttr(FacesContext context, UIComponent comp, 
			   String name) {
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
        AjaxZone zone = (AjaxZone) component;
        boolean isAjaxRequest = AsyncResponse.isAjaxRequest();
        if (this.isRenderScriptsForAllZonesRightNow(context, zone, 
						    isAjaxRequest)) {
            
            if (!isAjaxRequest && !java.beans.Beans.isDesignTime()) {
                for (int i = 0; i < scriptIds.length; i++) {
                    Map requestMap = context.getExternalContext().
			getRequestMap();
                    if (!requestMap.containsKey(scriptLinkKeys[i])) {
                        getXhtmlHelper().linkJavascript(context, component,
							writer,
                            Mechanism.CLASS_RESOURCE, scriptIds[i]);
                        requestMap.put(scriptLinkKeys[i], Boolean.TRUE);
                    }
                }
            }
            writeAjaxifyScripts(context, writer, zone, isAjaxRequest);
        }
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

    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Private methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

    private transient XhtmlHelper xHtmlHelper = null;
    
    private XhtmlHelper getXhtmlHelper() {
        if (null == xHtmlHelper) {
            xHtmlHelper = new XhtmlHelper();
        }
        return xHtmlHelper;
    }
    
    
    /**
     * @return true if the scripts for all zones in this view should
     * be rendered right now.
     *
     * <p>Returns true if this AjaxZone instance is the last rendered zone 
     * in the view.
     */
    
    private boolean isRenderScriptsForAllZonesRightNow(FacesContext context,
            AjaxZone currentZone, boolean isAjaxRequest) {
        boolean result = false;
        List<AjaxZone> zoneList = isAjaxRequest ?
	    currentZone.getRenderedZoneList()
         : currentZone.getAllZoneList();
        if (null != zoneList && !zoneList.isEmpty()) {
            AjaxZone lastRenderedZone = null;
            // Find the last entry in the zoneList
            // that has its rendered property set to true
            for (int i = zoneList.size() - 1; i >= 0; i--) {
                lastRenderedZone = zoneList.get(i);
                if (lastRenderedZone.isRendered()) {
                    break;
                }
                lastRenderedZone = null;
            }
            if (null != lastRenderedZone) {
                result = (currentZone == lastRenderedZone);
            }
        }

        return result;
    }

}
