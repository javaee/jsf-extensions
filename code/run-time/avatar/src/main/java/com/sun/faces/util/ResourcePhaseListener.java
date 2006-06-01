
/*
 * $Id: ResourcePhaseListener.java,v 1.3 2005/12/19 22:23:42 edburns Exp $
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


package com.sun.faces.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.portlet.RenderResponse;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Leverage the Faces Lifecycle to load non-Java class resources out
 * of jars in the web-app classloader.  This class enables packing
 * non-Java class resources associated with a component into the same
 * jar as the classes of the component itself.  For example, a component
 * may require extra JavaScript and style sheet files.  It is a burden
 * to distribute these artifacts separately from the classes themselves.</p>

 * <p><strong>Security Caution:</strong> The default operation of this
 * class allows anyone to access the contents of the web app classloader
 * using a simple HTTP get.  Users are advised to restrict access to the
 * "/resource" viewId such that such accesses are subject to standard
 * Servlet security constraints.</p>
 *
 * <p>Build-Time Usage</p>
 *
 * <p>This listener is self-configured by placing the jar file in which
 * it is distributed into <code>WEB-INF/lib</code>.</p>
 *
 * <p>Referencing Resources Using this PhaseListener</p>
 *
 * <p>Let's assume we have a JavaScript file in our component.jar under
 * the jar entry named <code>/META-INF/autocomplete/script.js</code>.
 * To access this jar from a web page in a prefix mapped
 * <code>FacesServlet</code>, the following &lt;script&gt; tag must be
 * used.</p>
 *
<pre><code>
&lt;script type="text/javascript" 
           src="faces/resource?r=/META-INF/autocomplete/script.js" /&gt;
</code></pre>
 * 
 * <p>To do so in an extension mapped <code>FacesServlet</code>, do this:</p>
 *
<pre><code>
&lt;script type="text/javascript" 
           src="resource.faces?r=/META-INF/autocomplete/script.js" /&gt;
</code></pre>
 * 
 * 
 * <p>How it works</p>
 *
 * <p>This class is very simple.  Inspection of the above HTML code
 * reveals that we simply use a well known JSF view id of "resource"
 * combined with the query parameter named <code>r</code> whose value is
 * the path to the resource in the web app classloader.  An additional
 * query parameter of <code>ct</code> is supported to pass the
 * content-type.  If no <code>ct</code> parameter is specified, this
 * class uses the extension to identify the content-type.</p>
 *
 * <p>The following extension to content-type mappings are understood.</p>
 *
 * <table border="1">
 *
 * <tr><th>extension</th> <th>content-type</th></tr>
 *
 * <tr><td>js</td> <td>text/javascript</td></tr>
 * <tr><td>gif</td> <td>image/gif</td></tr>
 * <tr><td>jpeg</td> <td>image/jpeg</td></tr>
 * <tr><td>jpg</td> <td>image/jpeg</td></tr>
 * <tr><td>png</td> <td>image/png</td></tr>
 * <tr><td>ico</td> <td>image/vnd.microsoft.icon</td></tr>
 *
 * </table>
 *
 * @author edburns
 */
public class ResourcePhaseListener implements PhaseListener {
    
    public static final String RESOURCE_PREFIX = "/resource";
    
    public static final String DEFAULT_CONTENT_TYPE = "application/octet-stream";    
    
    private Map<String,String> extensionToContentType = null;
    
    /** Creates a new instance of ResourcePhaseListener */
    public ResourcePhaseListener() {
        init();
    }
    
    private void init() {
        extensionToContentType = new HashMap<String,String>();
        extensionToContentType.put(".js", "text/javascript");
        extensionToContentType.put(".gif", "image/gif");
        extensionToContentType.put(".jpg", "image/jpeg");
        extensionToContentType.put(".jpeg", "image/jpeg");
        extensionToContentType.put(".png", "image/png");
        extensionToContentType.put(".ico", "image/vnd.microsoft.icon");
    }
    
    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }
    
    public void beforePhase(PhaseEvent phaseEvent) {
    }
    
    public void afterPhase(PhaseEvent event) {
        if (-1 != event.getFacesContext().getViewRoot().getViewId().
                indexOf(RESOURCE_PREFIX)) {
            // render the script
            renderResource(event);
        }
    }
    
    private String getResourceFromRequest(ExternalContext context) {
        String result = null;
        
        result = (String) context.getRequestParameterMap().get("r");
        
        return result;
    }
    
    private String getContentTypeFromRequest(ExternalContext context, 
            String resourceName) {
        String result = null;
        int i;
        if (null == (result = (String) context.getRequestMap().get("ct"))) {
            if (null != resourceName) {
                if (-1 != (i = resourceName.lastIndexOf("."))) {
                    result = resourceName.substring(i);
                    result = extensionToContentType.get(result);
                }
            }
        }
        if (null == result) {
            result = DEFAULT_CONTENT_TYPE;
        }
        
        return result;
    }
    
    private void renderResource(PhaseEvent event) {
        FacesContext context = event.getFacesContext();
        ExternalContext extContext = context.getExternalContext();
        String resourceName = getResourceFromRequest(extContext);
        
        if (null == resourceName) {
            return;
        }
        
        // PENDING(edburns): need to optimize this method for performance.
        // Clearly this won't scale too well compared to just serving up actual
        // bytes from disk.  Of course, using NIO would probably help things.
        // In addition to NIO, it might be necessary to do some memory and -
        // disk caching.
        URL url = ResourcePhaseListener.class.getResource(resourceName);
        URLConnection conn = null;
        InputStream stream = null;
        BufferedReader bufReader = null;
        Object response = extContext.getResponse();
        RenderResponse renderResponse = null;
        HttpServletResponse servletResponse = null;
        OutputStreamWriter outWriter = null;
        String contentType = getContentTypeFromRequest(extContext, resourceName),
                curLine = null;
        
        try {
            if (response instanceof HttpServletResponse) {
                servletResponse = (HttpServletResponse) response;
                outWriter = new OutputStreamWriter(servletResponse.
                        getOutputStream(),
                        servletResponse.getCharacterEncoding());
                conn = url.openConnection();
                conn.setUseCaches(false);
                stream = conn.getInputStream();
                bufReader = new BufferedReader(new InputStreamReader(stream));
                servletResponse.setContentType(contentType);
                servletResponse.setStatus(200);
            }
            else if (response instanceof RenderResponse) {
                renderResponse = (RenderResponse) response;
                outWriter = new OutputStreamWriter(renderResponse.
                        getPortletOutputStream(),
                        renderResponse.getCharacterEncoding());
                conn = url.openConnection();
                conn.setUseCaches(false);
                stream = conn.getInputStream();
                bufReader = new BufferedReader(new InputStreamReader(stream));
                renderResponse.setContentType(contentType);
            }
            
            while (null != (curLine = bufReader.readLine())) {
                outWriter.write(curLine+"\n");
            }
            outWriter.flush();
            outWriter.close();
            event.getFacesContext().responseComplete();
            
        } catch (Exception e) {
            String message = null;
            message = "Can't load script file:" +
                    url.toExternalForm();
        }
        
    }
    
}
