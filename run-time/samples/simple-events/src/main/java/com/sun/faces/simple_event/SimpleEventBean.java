/*
 * SimpleEventPojo.java
 *
 * Created on August 2, 2006, 11:37 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.faces.simple_event;

import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author edburns
 */
public class SimpleEventBean {
    
    /** Creates a new instance of SimpleEventPojo */
    public SimpleEventBean() {
    }
    
    public void valueChange(ValueChangeEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        Map<String,Object> requestMap = 
                FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        String curValue = (String) requestMap.get("valueChangeEvents");
        if (null == curValue) {
            curValue = "";
        }
        curValue = curValue + " [new: " + e.getNewValue() + 
                ", old: " + e.getOldValue() + ", clientId: " + 
                e.getComponent().getClientId(FacesContext.getCurrentInstance())+
                "]";
        requestMap.put("valueChangeEvents", curValue);
    }
    
}
