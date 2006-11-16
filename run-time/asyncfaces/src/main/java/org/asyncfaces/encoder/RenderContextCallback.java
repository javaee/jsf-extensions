/*
 *
 * Created on 16 novembre 2006, 11.27
 */

package org.asyncfaces.encoder;

import java.io.IOException;
import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author agori
 */
public class RenderContextCallback implements ContextCallback {

    public void invokeContextCallback(FacesContext context, UIComponent target) {
        try {
            target.encodeAll(context);
        } catch (IOException ex) {
            throw new FacesException(ex);
        }
    }
    
}
