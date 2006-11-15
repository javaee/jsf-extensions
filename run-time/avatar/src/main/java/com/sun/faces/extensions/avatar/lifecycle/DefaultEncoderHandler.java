/*
 *
 * Created on 11 novembre 2006, 19.41
 */

package com.sun.faces.extensions.avatar.lifecycle;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author agori
 */
public class DefaultEncoderHandler implements EncoderHandler {
    
    public ComponentEncoder getEncoder(UIComponent component) {
        return new DefaultEncoderHandler.DefaultComponentEncoder(component);
    }
 
    
    public static class DefaultComponentEncoder implements ComponentEncoder {
        
        protected UIComponent comp;
        
        public DefaultComponentEncoder(UIComponent comp) {
            this.comp = comp;
        }
        
        public String getClientHandler() {
            return null;
        }
        
        public void encodeMarkup() throws IOException {
            comp.encodeAll(FacesContext.getCurrentInstance());
        }

        public String getClientId() {
            return comp.getClientId(FacesContext.getCurrentInstance());
        }

        public void encodeExtra() throws IOException {
        }
        
    }
    
    
}
