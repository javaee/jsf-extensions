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

package admingui.avatar.renderkit;

import admingui.avatar.lifecycle.AsyncResponse;
import java.io.IOException;
import java.util.Map;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.Renderer;
//import org.apache.shale.remoting.Mechanism;
//import org.apache.shale.remoting.XhtmlHelper;

/**
 * This class renderers TextField components.
 */
public class ScriptsRenderer extends Renderer {
    

    // PENDING(craigmcc): I've filed SHALE-183 on this.  I'd like to get out of 
    // the business of maintaining all this JavaScript myself and let Shale 
    // take care of it.
    
    private static final String scriptIds[] = {
        "/META-INF/libs/scriptaculous/version1.6.4/prototype.js",
        "/META-INF/com_sun_faces_ajax.js"
    };    

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
        if (!AsyncResponse.isAjaxRequest()) {
	    ResponseWriter writer = context.getResponseWriter();
            for (int i = 0; i < scriptIds.length; i++) {
                linkJavascript(context, component, writer, scriptIds[i]);
            }
        }
    }
    
    /**
     *	<p> Directory scoped to share w/ the ScriptsRenderer.  This is a
     *	    work-a-round method to do what I need to get done to get rid of
     *	    shale.</p>
     */
    static void linkJavascript(FacesContext ctx, UIComponent comp, ResponseWriter writer, String script) throws IOException {
	// Find the UIViewRoot (don't rely on it being set in the FacesContext)
	UIComponent root = comp;
	while (root.getParent() != null) {
	    root = root.getParent();
	}

	// Generate a view specific id to mark this script as rendered
	String resId = "" + comp.getAttributes().get("viewId") + script;
	Map requestMap = ctx.getExternalContext().getRequestMap();
	if (requestMap.get(resId) != null) {
	    // Script already written, don't do it again
	    return;
	}

	// Write the script
	writer.startElement("script", comp);
	writer.writeAttribute("type", "text/javascript", null);
// FIXME: I am hard-coding "/resource" for now... b/c shale is behaving flakey
//	  and has too many dependencies, perhaps if shale behaves I can use
//	  it in the future, or write a more mappable solution.  Ideally Dynamic
//	  Faces makes resource resolving plugable so that I can do this in a
//	  clean way.
	if (!script.startsWith("/")) {
	    script = "/" + script;
	}
	if (script.startsWith("/META-INF/")) {
	    script = script.substring(9);
	}
	writer.writeURIAttribute("src", ctx.getApplication().getViewHandler().
		getResourceURL(ctx, "/resource" + script), null);
	writer.endElement("script");
	writer.write("\n");

	// Mark
	requestMap.put(resId, Boolean.TRUE);
    }

    /*
    private transient XhtmlHelper xHtmlHelper = null;
    
    private XhtmlHelper getXhtmlHelper() {
        if (null == xHtmlHelper) {
            xHtmlHelper = new XhtmlHelper();
        }
        return xHtmlHelper;
    }
    */
}
