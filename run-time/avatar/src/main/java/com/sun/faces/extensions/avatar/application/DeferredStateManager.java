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
 * DeferredStateManager.java
 *
 * Created on June 26, 2006, 8:36 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.faces.extensions.avatar.application;

import com.sun.faces.extensions.avatar.lifecycle.*;
import java.io.IOException;
import javax.faces.application.StateManager;
import javax.faces.application.StateManagerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * <p>Decorate the existing <code>StateManager</code> and disable
 * writing state in the case of an AJAX request, as indicated by a
 * <code>true</code> return from {@link
 * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#isAjaxRequest}.
 * In this case, state is initiated manually by the {@link
 * com.sun.faces.extensions.avatar.lifecycle.PartialTraversalLifecycle#render}
 * method.</p>
 */

public class DeferredStateManager extends StateManagerWrapper {
        
    private StateManager parent = null;
    public DeferredStateManager(StateManager parent) {
        this.parent = parent;
    }

    /**
     * <p>Return the parent <code>StateManager</code></p>
     */ 

    public StateManager getWrapped() { return parent; }

    /**
     * <p>If {@link
     * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#isAjaxRequest}
     * returns <code>true</code>, take no action, otherwise call the
     * method on the wrapped object.</p>
     */

    public void writeState(FacesContext context, Object state) throws IOException {
        AsyncResponse async = AsyncResponse.getInstance();

        if (!async.isAjaxRequest() || async.isRenderAll()) {
            getWrapped().writeState(context, state);
        }
    }

    /**
     * <p>If {@link
     * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#isAjaxRequest}
     * returns <code>true</code>, take no action, otherwise call the
     * method on the wrapped object.</p>
     */

    public Object saveView(FacesContext context) {
        Object result = null;
        AsyncResponse async = AsyncResponse.getInstance();

        if (!async.isAjaxRequest() || async.isRenderAll()) {
            result = getWrapped().saveView(context);
        }
        return result;
    }

    /**
     * <p>If {@link 
     * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#isAjaxRequest}
     * returns <code>true</code>, take no action, otherwise call the
     * method on the wrapped object.</p>
     */

    public StateManager.SerializedView saveSerializedView(FacesContext context) {
        StateManager.SerializedView result = null;
        AsyncResponse async = AsyncResponse.getInstance();

        if (!async.isAjaxRequest() || async.isRenderAll()) {
            result = getWrapped().saveSerializedView(context);
        }

        return result;
    }

    /**
     * <p>If {@link 
     * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#isAjaxRequest}
     * returns <code>true</code>, take no action, otherwise call the
     * method on the wrapped object.</p>
     */

    public void writeState(FacesContext context, StateManager.SerializedView state) throws IOException {
        AsyncResponse async = AsyncResponse.getInstance();

        if (!async.isAjaxRequest() || async.isRenderAll()) {
            getWrapped().writeState(context, state);
        }
    }

}

