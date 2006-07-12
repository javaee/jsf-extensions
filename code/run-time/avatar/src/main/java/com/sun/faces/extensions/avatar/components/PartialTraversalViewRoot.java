/*
 * $Id: PartialTraversalViewRoot.java,v 1.4 2005/12/21 22:38:09 edburns Exp $
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

package com.sun.faces.extensions.avatar.components;

import com.sun.faces.extensions.avatar.event.EventCallback;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.ConverterException;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * <p>Request Processing Lifecycle Operation</p>
 *
 * <p>On initial request, this instance behaves effectively like the
 * <code>UIViewRoot</code> from which it extends, delegating all
 * behavior to the superclass.</p>
 *
 * <p>On postback, the {@link #processDecodes}, {@link
 * #processValidators}, and {@link #processUpdates} methods have special
 * behaviour.  If {@link
 * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#isAjaxRequest}
 * returns <code>false</code>, the superclass implementation of the
 * respective method is called.  Otherwise, {@link
 * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#getExecuteSubtrees}
 * is called.  This returns a list of client ids suitable for the
 * <code>execute</code> portion of the request processing lifecycle.  If
 * the list is empty, call through to the superclass implementation of
 * the respective method.  Otherwise, for each client id in the list,
 * using <code>invokeOnComponent</code>, call the respective
 * <code>process*</code> method on the component with that clientId.
 * After all the client ids in the list have been handled for the
 * current phase, reflectively call <code>broadcastEvents()</code> on
 * the superclass passing the current <code>PhaseId</code>.</p>
 *
 * <p>On postback, the {@link #encodeAll} method has special behavior.
 * If {@link
 * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#isAjaxRequest}
 * returns <code>false</code>, the superclass <code>encodeAll</code> is
 * called.  Otherwise, {@link
 * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#getRenderSubtrees}
 * is called.  This returns a list of client ids suitable for the {@link
 * javax.faces.lifecycle.Lifecycle#render} portion of the request
 * processing lifecycle.  If the list is empty, call through to the
 * superclass implementation of <code>encodeAll</code>.  Otherwise, for
 * each client id in the list, using {@link
 * javax.faces.component.UIComponent#invokeOnComponent}, call the
 * <code>encodeAll</code> method on the component with that clientId.
 * If {@link
 * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#isRenderXML}
 * returns <code>true</code> set the response content-type and headers
 * approriately for XML, and wrap the rendered output from the 
 * components in the list as in the following example.</p>
 *
<pre><code>
&lt;partial-response&gt;
  &lt;components&gt;
    &lt;render id="form:table"/&gt;
      &lt;markup&gt;&lt;![CDATA[
        Rendered content from component
      ]]&gt;&lt;/markup&gt;
      &lt;messages&gt;
        &lt;message&gt;The messages element is optional.  If present,
                 it is a list of FacesMessage.getSummary() output
        &lt;/message&gt;
      &lt;/messages&gt;
    &lt;/render&gt;
    &lt;!-- repeat for the appropriate number of components --&gt;
  &lt;/components&gt;
  &lt;state&gt;&lt;![CDATA[state information for this view ]]&gt;&lt;/state&gt;
&lt;/partial-response&gt;
</code></pre>
  *
  * if the <code>isRenderXML</code> value is <code>false</code>, assume
  * the renderers are handling the content-type, header, and component
  * encapsulation details and write nothing to the response.</p>
  *
  * <p>See {@link
  * com.sun.faces.extensions.avatar.lifecycle.PartialTraversalLifecycle} for
  * additional information about how this class helps
  * <code>PartialTraversalViewRoot</code> get its job done.</p>
  *
  * @author edburns
  */
public class PartialTraversalViewRoot extends UIViewRoot implements Serializable {
    
    public PartialTraversalViewRoot() {
    }
    
    public void processDecodes(FacesContext context) {
        EventCallback cb = null;
        boolean invokedCallback = false;
        if (!AsyncResponse.isAjaxRequest()) {
            super.processDecodes(context);
            return;
        }
        invokedCallback = invokeContextCallbackOnSubtrees(context,
                new PhaseAwareContextCallback(PhaseId.APPLY_REQUEST_VALUES));
        if (null != (cb =
                AsyncResponse.getEventCallbackForPhase(PhaseId.APPLY_REQUEST_VALUES))) {
            cb.invoke(context);
        }
        if (!invokedCallback) {
            super.processDecodes(context);
        }

    }

    public void processValidators(FacesContext context) {
        if (!AsyncResponse.isAjaxRequest() ||
            !invokeContextCallbackOnSubtrees(context, 
                new PhaseAwareContextCallback(PhaseId.PROCESS_VALIDATIONS))) {
            super.processValidators(context);
            return;
        }
    }

    public void processUpdates(FacesContext context) {
        if (!AsyncResponse.isAjaxRequest() ||
            !invokeContextCallbackOnSubtrees(context, 
                new PhaseAwareContextCallback(PhaseId.UPDATE_MODEL_VALUES))) {
            super.processUpdates(context);
            return;
        }
    }

    public void encodeAll(FacesContext context) throws IOException {
        if (!AsyncResponse.isAjaxRequest()) {
            super.encodeAll(context);
            return;
        }
        UIViewRoot root = context.getViewRoot();
        ResponseWriter orig = null, writer = null;
        AsyncResponse async = AsyncResponse.getInstance();
        boolean writeXML = AsyncResponse.isRenderXML();
        EventCallback cb = null;
        
        try {
            // Remove the no-op response so our content can be written.
            async.removeNoOpResponse(context);
            // Get (and maybe create) the AjaxResponseWriter
            writer = async.getResponseWriter();
            orig = context.getResponseWriter();
            // Install the AjaxResponseWriter
            context.setResponseWriter(writer);
            if (writeXML) {
                ExternalContext extContext = context.getExternalContext();
                if (extContext.getResponse() instanceof HttpServletResponse) {
                    HttpServletResponse servletResponse = (HttpServletResponse)
                    extContext.getResponse();
                    servletResponse.setContentType("text/xml");
                    servletResponse.setHeader("Cache-Control", "no-cache");
                }
                
                writer.startElement("partial-response", root);
                writer.startElement("components", root);
            }
        
            invokeContextCallbackOnSubtrees(context, 
                new PhaseAwareContextCallback(PhaseId.RENDER_RESPONSE));
            
            if (null != (cb = async.getEventCallbackForPhase(PhaseId.RENDER_RESPONSE))) {
                cb.invoke(context);
            }
            
            if (writeXML) {
                writer.endElement("components");
            }
        }
        catch (IOException ioe) {
            
        }
        finally {
            // re-install the no-op classes so any post f:view content is not written
            async.installNoOpResponse(context);
            // move aside the AjaxResponseWriter
            if (null != orig) {
                context.setResponseWriter(orig);
            }
        }
    }
    
    private boolean invokeContextCallbackOnSubtrees(FacesContext context, 
            PhaseAwareContextCallback cb) {
        AsyncResponse async = AsyncResponse.getInstance();
        List<String> subtrees = null;
        
        // If this callback is intended for RENDER_RESPONSE, use
        // async.getRenderSubtrees().  Otherwise, use async.getExecuteSubtrees().
        // If async.getExecuteSubtrees() is empty, use async.getRenderSubtrees().
        
        if (cb.getPhaseId() == PhaseId.RENDER_RESPONSE) {
            subtrees = async.getRenderSubtrees();
        }
        else {
            subtrees = async.getExecuteSubtrees();
            if (subtrees.isEmpty()) {
                subtrees = async.getRenderSubtrees();
            }
        }
        
        boolean result = false;
        
        for (String cur : subtrees) {
            if (this.invokeOnComponent(context, cur, cb)) {
                result = true;
            }
        }
        return result;
    }

    private class PhaseAwareContextCallback implements ContextCallback {
        
        private PhaseId curPhase = null;
        private PhaseAwareContextCallback(PhaseId curPhase) {
            this.curPhase = curPhase;
        }
        
        private PhaseId getPhaseId() {
            return curPhase;
        }
        
        public void invokeContextCallback(FacesContext facesContext, UIComponent comp) {
            try {
                ConverterException converterException = null;
                boolean writeXML = AsyncResponse.isRenderXML();
                
                if (curPhase == PhaseId.APPLY_REQUEST_VALUES) {
                    comp.processDecodes(facesContext);
                }
                else if (curPhase == PhaseId.PROCESS_VALIDATIONS) {
                    comp.processValidators(facesContext);
                }
                else if (curPhase == PhaseId.UPDATE_MODEL_VALUES) {
                    comp.processUpdates(facesContext);
                }
                else if (curPhase == PhaseId.RENDER_RESPONSE) {
                    ResponseWriter writer = AsyncResponse.getInstance().getResponseWriter();

                    if (writeXML) {
                        writer.startElement("render", comp);
                        writer.writeAttribute("id",
                                comp.getClientId(facesContext), "id");
                    }
                    try {
                        if (writeXML) {
                            writer.startElement("markup", comp);
                            writer.write("<![CDATA[");
                        }
                        comp.encodeAll(facesContext);
                        if (writeXML) {
                            writer.write("]]>");
                            writer.endElement("markup");
                        }
                    }
                    catch (ConverterException ce) {
                        converterException = ce;
                    }
                    if (writeXML) {
                        writeMessages(facesContext, comp,
                                converterException, writer);
                        writer.endElement("render");
                    }
                    
                }
                else {
                    throw new IllegalStateException("I18N: Unexpected PhaseId passed to PhaseAwareContextCallback: " + curPhase.toString());
                }
                    
            }
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        
        private boolean writeMessages(FacesContext context, UIComponent comp,
                ConverterException converterException,
                ResponseWriter writer) throws IOException {
            Iterator<FacesMessage> messages;
            boolean
                    wroteStart = false,
                    hasMessages = false;
            messages = context.getMessages(comp.getClientId(context));
            while (messages.hasNext()) {
                hasMessages = true;
                if (!wroteStart) {
                    writer.startElement("messages", comp);
                    wroteStart = true;
                }
                writer.startElement("message", comp);
                // PENDING(edburns): this is a rendering decision.
                // We should do something with the MessageRenderer.
                writer.write(messages.next().getSummary() + " ");
                writer.endElement("message");
            }
            if (null != converterException) {
                if (!wroteStart) {
                    writer.startElement("messages", comp);
                    wroteStart = true;
                }
                writer.write(converterException.getFacesMessage().getSummary());
            }
            if (wroteStart) {
                writer.endElement("messages");
            }
            return hasMessages;
        }
        
    }
    
}
