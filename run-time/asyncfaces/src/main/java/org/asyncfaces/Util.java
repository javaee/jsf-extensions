/*
 *
 * Created on 14 novembre 2006, 0.40
 */

package org.asyncfaces;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.util.List;
import javax.faces.component.ContextCallback;
import javax.faces.component.EditableValueHolder;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

/**
 *
 * @author agori
 */
public class Util {
    
    static private boolean inspectForValidity(UIComponent comp) {
        if (comp instanceof EditableValueHolder &&
                !((EditableValueHolder) comp).isValid()) {
            return false;
        }
        
        for (UIComponent c : comp.getChildren()) {
            if (!inspectForValidity(c)) {
                return false;
            }
        }
        return true;
    }
    
    static public boolean isValid() {
        FacesContext context = FacesContext.getCurrentInstance();
        List<String> execute = AsyncResponse.getInstance().getExecuteSubtrees();
        UIViewRoot root = context.getViewRoot();
        AsyncFacesContextCallback callback = new AsyncFacesContextCallback();
        for (String id : execute) {
            root.invokeOnComponent(context, id, callback);
            if(!inspectForValidity(callback.getComponent())) {
                return false;
            }
        }
        return true;
    }
    
    static public class AsyncFacesContextCallback implements ContextCallback {
        
        private UIComponent comp;
        
        public void invokeContextCallback(FacesContext context, UIComponent target) {
            comp = target;
        }
        
        public UIComponent getComponent() {
            return comp;
        }
        
    }
}
