/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

/*
 * $Id: MockFacesContext.java,v 1.1 2005/10/18 17:47:54 edburns Exp $
 */

package com.sun.faces.mock;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseStream;
import javax.faces.context.ResponseWriter;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;

import javax.el.ELContext;


// Mock Object for FacesContext
public class MockFacesContext extends FacesContext {


    // ------------------------------------------------------------ Constructors


    public MockFacesContext() {
        super();
        setCurrentInstance(this);
    }


    public MockFacesContext(ExternalContext externalContext) {
        setExternalContext(externalContext);
        setCurrentInstance(this);
	elContext = new MockELContext(new MockELResolver());
	elContext.putContext(FacesContext.class, this);
    }


    public MockFacesContext(ExternalContext externalContext, Lifecycle lifecycle) {
        this(externalContext);
    }


    // -------------------------------------------------------------- Properties


    // application
    private Application application = null;
    public Application getApplication() {
        return (this.application);
    }
    public void setApplication(Application application) {
        this.application = application;
    }


    // clientIdsWithMessages
    public Iterator getClientIdsWithMessages() {
        return (messages.keySet().iterator());
    }

    private ELContext elContext = null;
    public ELContext getELContext() {
	return (this.elContext);
    }

    // externalContext
    private ExternalContext externalContext = null;
    public ExternalContext getExternalContext() {
        return (this.externalContext);
    }
    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }


    // locale
    private Locale locale = null;
    public Locale getLocale() {
        return (this.locale);
    }
    public void setLocale(Locale locale) {
        this.locale = locale;
    }


    // maximumSeverity
    public Severity getMaximumSeverity() {
        throw new UnsupportedOperationException();
    }


    // messages
    private Map messages = new HashMap();
    public Iterator getMessages() {
        ArrayList results = new ArrayList();
        Iterator clientIds = messages.keySet().iterator();
        while (clientIds.hasNext()) {
            String clientId = (String) clientIds.next();
            results.addAll((List) messages.get(clientId));
        }
        return (results.iterator());
    }
    public Iterator getMessages(String clientId) {
        List list = (List) messages.get(clientId);
        if (list == null) {
            list = new ArrayList();
        }
        return (list.iterator());
    }


    // renderKit
    public RenderKit getRenderKit() {
        UIViewRoot vr = getViewRoot();
        if (vr == null) {
            return (null);
        }
        String renderKitId = vr.getRenderKitId();
        if (renderKitId == null) {
            return (null);
        }
        RenderKitFactory rkFactory = (RenderKitFactory)
            FactoryFinder.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
        return (rkFactory.getRenderKit(this, renderKitId));
    }


    // renderResponse
    private boolean renderResponse = false;
    public boolean getRenderResponse() {
        return (this.renderResponse);
    }
    public void setRenderResponse(boolean renderResponse) {
        this.renderResponse = renderResponse;
    }

    // responseComplete
    private boolean responseComplete = false;
    public boolean getResponseComplete() {
        return (this.responseComplete);
    }
    public void setResponseComplete(boolean responseComplete) {
        this.responseComplete = responseComplete;
    }


    // responseStream
    private ResponseStream responseStream = null;
    public ResponseStream getResponseStream() {
        return (this.responseStream);
    }
    public void setResponseStream(ResponseStream responseStream) {
        this.responseStream = responseStream;
    }


    // responseWriter
    private ResponseWriter responseWriter = null;
    public ResponseWriter getResponseWriter() {
        return (this.responseWriter);
    }
    public void setResponseWriter(ResponseWriter responseWriter) {
        this.responseWriter = responseWriter;
    }


    // viewRoot
    private UIViewRoot root = null;
    public UIViewRoot getViewRoot() {
        return (this.root);
    }
    public void setViewRoot(UIViewRoot root) {
        this.root = root;
    }


    // ---------------------------------------------------------- Public Methods


    public void addMessage(String clientId, FacesMessage message){ 
        if (message == null) {
            throw new NullPointerException();
        }
        List list = (List) messages.get(clientId);
        if (list == null) {
            list = new ArrayList();
            messages.put(clientId, list);
        }
        list.add(message);
    }


    public void release() {
        application = null;
        externalContext = null;
        locale = null;
        messages.clear();
        renderResponse = false;
        responseComplete = false;
        responseStream = null;
        responseWriter = null;
        root = null;
    }


    public void renderResponse() {
        this.renderResponse = true;
    }


    public void responseComplete() {
        this.responseComplete = true;
    }


}
