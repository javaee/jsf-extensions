/*
 * ComponentEncoder.java
 *
 * Created on 11 novembre 2006, 18.19
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.faces.extensions.avatar.lifecycle;

import java.io.IOException;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

/**
 *
 * @author agori
 */
public interface ComponentEncoder {
    
    public void init(FacesContext context, UIComponent comp);
    
    public void encodeMarkup() throws IOException;
    
    public void encodeExtra() throws IOException;
    
    public String getClientHandler();
    
    public String getClientId();

}
