/*
 * DeferredStateManager.java
 *
 * Created on June 26, 2006, 8:36 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package admingui.avatar.application;

import admingui.avatar.lifecycle.*;
import java.io.IOException;
import javax.faces.application.StateManager;
import javax.faces.application.StateManagerWrapper;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 * <p>Decorate the existing <code>StateManager</code> and disable
 * writing state in the case of an AJAX request, as indicated by a
 * <code>true</code> return from {@link
 * admingui.avatar.lifecycle.AsyncResponse#isAjaxRequest}.
 * In this case, state is initiated manually by the {@link
 * admingui.avatar.lifecycle.PartialTraversalLifecycle#render}
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
     * admingui.avatar.lifecycle.AsyncResponse#isAjaxRequest}
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
     * admingui.avatar.lifecycle.AsyncResponse#isAjaxRequest}
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
     * admingui.avatar.lifecycle.AsyncResponse#isAjaxRequest}
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
     * admingui.avatar.lifecycle.AsyncResponse#isAjaxRequest}
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

