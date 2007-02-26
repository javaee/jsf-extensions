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

import com.sun.faces.extensions.avatar.event.EventParser;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import com.sun.faces.extensions.common.util.Util;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.ActionSource;
import javax.faces.component.ContextCallback;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;
import javax.faces.convert.ConverterException;
import javax.faces.event.PhaseId;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * <p>Request Processing Lifecycle Operation</p>
 *
 *  <p>	This class contains the implementation details needed for a
 *	{@link PartialTraversalViewRoot} implementation.  On initial request,
 *	it provides behavior that is the same as a standard
 *	<code>UIViewRoot</code>, delegating all behavior to the UIViewRoot.
 *	However, since it is unable to call viewRoot.super.method(), it instead
 *	returns true from methods that should delegate this.  So it is up to
 *	the caller to correctly invoke super.method() in these cases.</p>
 *
 *  <p>	On postback, the {@link #processDecodes}, {@link #processValidators},
 *	and {@link #processUpdates} methods have special behaviour.  If {@link
 *	com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#isAjaxRequest}
 *	returns <code>false</code>, the UIViewRoot superclass implementation of
 *	the respective method is called.  Otherwise,
 *	{@link com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#getExecuteSubtrees}
 *	is called.  This returns a list of client ids suitable for the
 *	<code>execute</code> portion of the request processing lifecycle.  If
 *	the list is empty, call through to the UIViewRoot superclass
 *	implementation of the respective method.  Otherwise, for each client id
 *	in the list, using <code>invokeOnComponent</code>, call the respective
 *	<code>process*</code> method on the component with that clientId.
 *	After all the client ids in the list have been handled for the current
 *	phase, reflectively call <code>broadcastEvents()</code> on the
 *	superclass passing the current <code>PhaseId</code>.</p>
 *
 *  <p>	On postback, the {@link #encodeBegin}, {@link #encodeChildren} and
 *	{@link #encodeEnd} methods have special behavior.  If {@link
 *	com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#isAjaxRequest}
 *	returns <code>false</code>, the UIViewRoot superclass
 *	<code>encode*</code> methods are called.  Otherwise, {@link
 *	com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#getRenderSubtrees}
 *	is called.  This returns a list of client ids suitable for the
 *	{@link javax.faces.lifecycle.Lifecycle#render} portion of the request
 *	processing lifecycle.  If the list is empty, call through to the
 *	UIViewRoot superclass implementation of <code>encode*</code>.
 *	Otherwise, for each client id in the list, using {@link
 *	javax.faces.component.UIComponent#invokeOnComponent}, call the
 *	<code>encode*</code> methods on the component with that clientId.  If
 *	the list of subtrees to render for this request is non-empty, set the
 *	response content-type and headers approriately for XML, and wrap the
 *	rendered output from the components in the list as in the following
 *	example.</p>
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
 *  If the <code>isRenderXML</code> value is <code>false</code>, assume
 *  the renderers are handling the content-type, header, and component
 *  encapsulation details and write nothing to the response.</p>
 *
 *  <p>See {@link
 *  com.sun.faces.extensions.avatar.lifecycle.PartialTraversalLifecycle} for
 *  additional information about how this class helps
 *  <code>PartialTraversalViewRoot</code> get its job done.</p>
 *
 *  <p>	This class uses a hack to make the
 *	<code>UIViewRoot.broadcastEvents</code> method public.  This is
 *	necessary to allow easy broadcasting events during the AJAX
 *	lifecycle.</p>
 *
 *  @author edburns
 *  @author Ken Paulsen (ken.paulsen@sun.com)
 */
public class PartialTraversalViewRootHelper implements Serializable {

    /**
     *	Constructor.
     */
    public PartialTraversalViewRootHelper(PartialTraversalViewRoot viewRoot) {
	if (viewRoot == null) {
	    throw new IllegalArgumentException("PartialTraversalViewRoot cannot be null!");
	}
	setUIViewRoot(viewRoot);

        modifiedComponents = new ArrayList<UIComponent>();

        markImmediate = new Util.TreeTraversalCallback() {
            public boolean takeActionOnNode(FacesContext context, UIComponent comp) throws FacesException {
                if (comp instanceof ActionSource) {
                    ActionSource as = (ActionSource)comp;
                    if (!as.isImmediate()) {
                        as.setImmediate(true);
                        modifiedComponents.add(comp);
                    }
                } else if (comp instanceof EditableValueHolder) {
                    EditableValueHolder ev = (EditableValueHolder)comp;
                    if (!ev.isImmediate()) {
                        ev.setImmediate(true);
                        modifiedComponents.add(comp);
                    }
                }

                return true;
            }

        };
    }

    /**
     *	<p> Get the cooresponding PartialTraversalViewRoot.</p>
     */
    protected PartialTraversalViewRoot getUIViewRoot() {
	return _viewRoot;
    }

    /**
     *	<p> Set the cooresponding PartialTraversalViewRoot.</p>
     */
    protected void setUIViewRoot(PartialTraversalViewRoot viewRoot) {
	_viewRoot = viewRoot;
    }

    /**
     *
     */
    public void postExecuteCleanup(FacesContext context) {
        for (UIComponent comp : modifiedComponents) {
            if (comp instanceof ActionSource) {
                ActionSource as = (ActionSource)comp;
                assert(as.isImmediate());
                as.setImmediate(false);
            } else if (comp instanceof EditableValueHolder) {
                EditableValueHolder ev = (EditableValueHolder)comp;
                assert(ev.isImmediate());
                ev.setImmediate(false);
            }
        }
        modifiedComponents.clear();
    }

    /**
     *
     *	@return	true If super.processDecodes() should be called.
     */
    public boolean processDecodes(FacesContext context) {
        boolean invokedCallback = false;
	UIComponent comp = (UIComponent) getUIViewRoot();
        if (!AsyncResponse.isAjaxRequest()) {
	    // Invoke the "super"
            return true;
        }
        AsyncResponse async = AsyncResponse.getInstance();
        modifiedComponents.clear();

        // If this is an immediate "render all" request and there are
        // no explicit execute subtrees and the user didn't request
        // execute: none...
        if (async.isImmediateAjaxRequest() && async.isRenderAll() &&
            async.getExecuteSubtrees().isEmpty() &&
            !async.isExecuteNone()) {
            // Traverse the entire view and mark every ActionSource or
            // EditableValueHolder as immediate.
            Util.prefixViewTraversal(context, comp, markImmediate);
        }

        invokedCallback = invokeContextCallbackOnSubtrees(context,
                new PhaseAwareContextCallback(PhaseId.APPLY_REQUEST_VALUES));

        // Queue any events for this request in a context aware manner
        ResponseWriter writer = null;
        try {
            writer = async.getResponseWriter();
        } catch (IOException ex) {
            throw new FacesException(ex);
        }
        // Install the AjaxResponseWriter
        context.setResponseWriter(writer);

        EventParser.queueFacesEvents(context);

        if (!invokedCallback) {
	    // Invoke comp's superclass processDecodes() method
	    comp.processDecodes(context);
        }

        this.broadcastEvents(context, PhaseId.APPLY_REQUEST_VALUES);
	return false;
    }

    /**
     *
     *	@return	true If super.processValidators() should be called.
     */
    public boolean processValidators(FacesContext context) {
        if (!AsyncResponse.isAjaxRequest() ||
            !invokeContextCallbackOnSubtrees(context,
                new PhaseAwareContextCallback(PhaseId.PROCESS_VALIDATIONS))) {
            return true;
        }
        this.broadcastEvents(context, PhaseId.PROCESS_VALIDATIONS);
	return false;
    }

    /**
     *
     *	@return	true If super.processUpdates() should be called.
     */
    public boolean processUpdates(FacesContext context) {
        if (!AsyncResponse.isAjaxRequest() ||
            !invokeContextCallbackOnSubtrees(context,
                new PhaseAwareContextCallback(PhaseId.UPDATE_MODEL_VALUES))) {
            return true;
        }
        this.broadcastEvents(context, PhaseId.UPDATE_MODEL_VALUES);
	return false;
    }

    /**
     *
     * @param	Value  The value if this is not an AjaxRequest.
     */
    public boolean getRendersChildren(boolean value) {
        // If this is not an ajax request...
        AsyncResponse async = AsyncResponse.getInstance();
        if (async.isAjaxRequest() && !async.isRenderAll()) {
            // do Dynamic Faces behavior
	    value = true;
        }
        return value;
    }

    /**
     *	<p> Request scoped key to hold the original ResponseWriter between
     *	    encodeBegin and encodeEnd during Ajax requests.</p>
     */
    private static final String ORIGINAL_WRITER = AsyncResponse.FACES_PREFIX +
            "origWriter";

    /**
     *
     *	@return	true If super.encodeBegin() should be called.
     */
    public boolean encodeBegin(FacesContext context) throws IOException {
        AsyncResponse async = AsyncResponse.getInstance();
	PartialTraversalViewRoot root = getUIViewRoot();

        // If this is not an ajax request...
        if (!async.isAjaxRequest()) {
            // do default behavior
            return true;
        }
        boolean renderAll = async.isRenderAll(),
                renderNone = async.isRenderNone();
        ResponseWriter orig = null, writer = null;

        try {
            // Turn on the response that has been embedded in the ViewHandler.
            async.setOnOffResponseEnabled(true);
            // If this is an ajax request, and it is a partial render request...

            if (!renderAll) {
                // replace the context's responseWriter with the AjaxResponseWriter
                // Get (and maybe create) the AjaxResponseWriter
                writer = async.getResponseWriter();
                orig = context.getResponseWriter();

		// Save the new writer for encodeEnd
		context.getExternalContext().getRequestMap().
                    put(ORIGINAL_WRITER, orig);

                // Install the AjaxResponseWriter
                context.setResponseWriter(writer);
            }

            root.encodePartialResponseBegin(context);

            if (renderAll) {
                writer = context.getResponseWriter();
                // If this is a "render all via ajax" request,
                // make sure to wrap the entire page in a <render> elemnt
                // with the special id of VIEW_ROOT_ID.  This is how the client
                // JavaScript knows how to replace the entire document with
                // this response.
                writer.startElement("render", (UIComponent) root);
                writer.writeAttribute("id", AsyncResponse.VIEW_ROOT_ID, "id");
                writer.startElement("markup", (UIComponent) root);
                writer.write("<![CDATA[");

                // setup up a writer which will escape any CDATA sections
                context.setResponseWriter(new EscapeCDATAWriter(writer));
            }
        } catch (IOException ex) {
	    this.cleanupAfterView(context);
        } catch (RuntimeException ex) {
	    this.cleanupAfterView(context);

	    // Throw the exception
	    throw ex;
        }
	return false;
    }

    /**
     *
     *	@return	true If super.encodeChildren() should be called.
     */
    public boolean encodeChildren(FacesContext context) throws IOException {
        AsyncResponse async = AsyncResponse.getInstance(false);
        // If this is not an ajax request...
        if ((async == null) || !async.isAjaxRequest() || async.isRenderAll()) {
            // Full request or ajax encode All request, operate normally
	    return true;
        }

	// If the context callback was not invoked on any subtrees
	// and the client did not explicitly request that no
	// subtrees be rendered...
	if (!invokeContextCallbackOnSubtrees(context,
		new PhaseAwareContextCallback(PhaseId.RENDER_RESPONSE)) &&
	    !async.isRenderNone()) {
	    assert(false);
	}

	return false;
    }

    /**
     *
     *	@return	true If super.encodeEnd() should be called.
     */
    public boolean encodeEnd(FacesContext context) throws IOException {
        AsyncResponse async = AsyncResponse.getInstance(false);
	PartialTraversalViewRoot root = getUIViewRoot();

        // If this is not an ajax request...
        if ((async == null) || !async.isAjaxRequest()) {
            // do default behavior
            return true;
        }


        try {
            if (async.isRenderAll()) {
		EscapeCDATAWriter cdataWriter = (EscapeCDATAWriter)
		    context.getResponseWriter();
		ResponseWriter writer = cdataWriter.getWrapped();

                // revert the writer and finish up
                context.setResponseWriter(writer);
                writer.write("]]>");
                writer.endElement("markup");
                writer.endElement("render");
                // then bail out.
                return false;
            }


            root.encodePartialResponseEnd(context);

        } catch (IOException ioe) {

        } finally {
            this.cleanupAfterView(context);
        }

	return false;
    }

    /**
     *
     */
    public void encodePartialResponseBegin(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        AsyncResponse async = AsyncResponse.getInstance();

        ExternalContext extContext = context.getExternalContext();
        // If the client did not explicitly request that no subtrees be rendered...
        if (!async.isRenderNone()) {
            // prepare to render the partial response.
            if (extContext.getResponse() instanceof HttpServletResponse) {
                HttpServletResponse servletResponse = (HttpServletResponse)
                extContext.getResponse();
                servletResponse.setContentType("text/xml");
                servletResponse.setHeader("Cache-Control", "no-cache");
                String xjson =
                        extContext.getRequestHeaderMap().get(AsyncResponse.XJSON_HEADER);
                if (null != xjson) {
                    servletResponse.setHeader(AsyncResponse.XJSON_HEADER,
                            xjson);
                }

		UIComponent root = (UIComponent) getUIViewRoot();
                writer.startElement("partial-response", root);
                writer.startElement("components", root);
            }
        }
    }

    /**
     *
     */
    public void encodePartialResponseEnd(FacesContext context) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        AsyncResponse async = AsyncResponse.getInstance();

        // PENDING(edburns): The core JSF spec does not dispatch events for
        // Render Response.  We need to do it here so that events that require
        // template text can rely on the tree including template text.
        this.broadcastEvents(context, PhaseId.RENDER_RESPONSE);

        // If the client did not explicitly request that no subtrees be rendered...
        if (!async.isRenderNone()) {
            writer.endElement("components");
        }

    }

    /**
     *
     */
    protected void broadcastEvents(FacesContext ctx, PhaseId phaseId) {
        String preViewId = ctx.getViewRoot().getViewId();

        // Broadcast the regular FacesEvents
	try {
	    getBroadcastEventsMethod().invoke(getUIViewRoot(), ctx, phaseId);
	} catch (IllegalAccessException ex) {
	    ex.printStackTrace();
	} catch (InvocationTargetException ex) {
	    ex.printStackTrace();
	}

        // Do our extra special MethodExpression invocation
        EventParser.invokeComponentMethodCallbackForPhase(ctx, phaseId);

	// Get the viewId again...
        String postViewId = ctx.getViewRoot().getViewId();

        // If the view id changed as result of the broadcastEvents...
        if (!postViewId.equals(preViewId)) {
            // Advise the browser to re-render the entire view.
            AsyncResponse.getInstance().setRenderAll(true);
        }
    }

    /**
     *
     */
    private void cleanupAfterView(FacesContext context) {
	ResponseWriter orig = (ResponseWriter) context.getExternalContext().
	    getRequestMap().get(ORIGINAL_WRITER);
	assert(null != orig);

        // PENDING(edburns): this is a big hack to get around the
        // way the JSP based faces implementation handles the
        // after view content.
        // We will have to do something different for other implementations.
        // This is not a problem for Facelets.
        context.getExternalContext().getRequestMap().remove("com.sun.faces.AFTER_VIEW_CONTENT");

        // move aside the AjaxResponseWriter
        if (null != orig) {
            context.setResponseWriter(orig);
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
            if (((UIComponent) getUIViewRoot()).
		    invokeOnComponent(context, cur, cb)) {
                result = true;
            }
        }
        return result;
    }

    /**
     *	<p> This method returns a publicly usable broadcastEvents method.</p>
     */
    private Method getBroadcastEventsMethod() {
	if (broadcastEventsMethod == null) {
	    try {
		broadcastEventsMethod = UIViewRoot.class.getDeclaredMethod(
		    "broadcastEvents", FacesContext.class, PhaseId.class);
		broadcastEventsMethod.setAccessible(true);
	    } catch (NoSuchMethodException ex) {
		throw new RuntimeException(
		    "broadcastEvents method not found on UIViewRoot!", ex);
	    }
	}
	return broadcastEventsMethod;
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

                if (curPhase == PhaseId.APPLY_REQUEST_VALUES) {
                    // If the user requested an immediate request
                    // Make sure to set the immediate flag.
                    if (AsyncResponse.isImmediateAjaxRequest()) {
                        PartialTraversalViewRootHelper.this.markImmediate.takeActionOnNode(facesContext, comp);
                    }

                    comp.processDecodes(facesContext);
                } else if (curPhase == PhaseId.PROCESS_VALIDATIONS) {
                    comp.processValidators(facesContext);
                } else if (curPhase == PhaseId.UPDATE_MODEL_VALUES) {
                    comp.processUpdates(facesContext);
                } else if (curPhase == PhaseId.RENDER_RESPONSE) {

                    if (comp.isRendered()) {
                        ResponseWriter writer = AsyncResponse.getInstance().getResponseWriter();

                        writer.startElement("render", comp);
                        writer.writeAttribute("id", comp.getClientId(facesContext), "id");
                        try {
                            writer.startElement("markup", comp);
                            writer.write("<![CDATA[");

                            // setup up a writer which will escape any CDATA sections
                            facesContext.setResponseWriter(new EscapeCDATAWriter(writer));

                            // do the default behavior...
                            comp.encodeAll(facesContext);

                            // revert the write and finish up
                            facesContext.setResponseWriter(writer);
                            writer.write("]]>");
                            writer.endElement("markup");
                        }
                        catch (ConverterException ce) {
                            converterException = ce;
                        }
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

    }

    private class EscapeCDATAWriter extends ResponseWriterWrapper {

        private ResponseWriter toWrap = null;
        public EscapeCDATAWriter(ResponseWriter toWrap) {
            this.toWrap = toWrap;
        }
        protected ResponseWriter getWrapped() {
            return toWrap;
        }

        public void write(String string) throws IOException {
            super.write(string.replace("]]>", "]]]]><![CDATA[>"));
        }
    }

    private PartialTraversalViewRoot _viewRoot	= null;

// FIXME:   The following 2 are initialized in the constructor... this class is
//	    serializable, these are transient, they should NOT be initialized
//	    in the constructor:
    private transient List<UIComponent> modifiedComponents;
    private transient Util.TreeTraversalCallback markImmediate;
    private transient Method broadcastEventsMethod = null;

}
