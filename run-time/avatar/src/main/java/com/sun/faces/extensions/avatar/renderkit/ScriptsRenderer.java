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

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.io.IOException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.render.Renderer;
import org.apache.shale.remoting.Mechanism;
import org.apache.shale.remoting.XhtmlHelper;
import com.sun.faces.extensions.avatar.components.ScriptsComponent;
import java.util.Map;

/**
 * This class renderers TextField components.
 */
public class ScriptsRenderer extends Renderer {
    

    // PENDING(craigmcc): I've filed SHALE-183 on this.  I'd like to get out of 
    // the business of maintaining all this JavaScript myself and let Shale 
    // take care of it.
    
    private static final String scriptIds[] = {
        "/META-INF/libs/scriptaculous/version1.6.4/prototype",
        "/META-INF/${pom.version}-${jar.version.extension}/com_sun_faces_ajax"
    };    
    
    private static final String scriptLinkKeys[] = {
        ScriptsComponent.PROTOTYPE_JS_LINKED,
        ScriptsComponent.AJAX_JS_LINKED,  
    };
    
    private boolean didInit = false;

    public void init() {
        if (!didInit) {
            String maxValue = FacesContext.getCurrentInstance().getExternalContext().
   
                getInitParameter("com.sun.faces.extensions.MAXIMIZE_RESOURCES");
            boolean doMax = (null != maxValue) && 0 < maxValue.length() ? true : false;
            for (int i = 0; i < scriptIds.length; i++) {
                if (doMax) {
                    scriptIds[i] = scriptIds[i] + "-max.js";
                } else {
                    scriptIds[i] = scriptIds[i] + ".js";
                }
            }
            didInit = true;
        }
    }
    
    
    
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    // Renderer methods
    // ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

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
        init();
        
        if (!AsyncResponse.isAjaxRequest() && !java.beans.Beans.isDesignTime()) {
            for (int i = 0; i < scriptIds.length; i++) {
                Map requestMap = context.getExternalContext().getRequestMap();
                if (!requestMap.containsKey(scriptLinkKeys[i])) {
                    getXhtmlHelper().linkJavascript(context, component, context.getResponseWriter(),
                        Mechanism.CLASS_RESOURCE, scriptIds[i]);
                    requestMap.put(scriptLinkKeys[i], Boolean.TRUE);
                }                
            }
        }
    }
    
    private transient XhtmlHelper xHtmlHelper = null;
    
    private XhtmlHelper getXhtmlHelper() {
        if (null == xHtmlHelper) {
            xHtmlHelper = new XhtmlHelper();
        }
        return xHtmlHelper;
    }
}
