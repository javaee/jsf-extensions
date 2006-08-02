/*
 * SuggestEvent.java
 *
 * Created on July 25, 2006, 10:26 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.enverio.jsf;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

/**
 *
 * @author edburns
 */
public class SuggestEvent extends FacesEvent {
    
    /** Creates a new instance of SuggestEvent */
    public SuggestEvent(UISuggest source) {
        super(source);
    }
    
    public UISuggest getSuggestSource() {
        return (UISuggest) getComponent();
    }

    public void processListener(FacesListener facesListener) {
    }

    public boolean isAppropriateListener(FacesListener facesListener) {
        return false;
    }
    
}
