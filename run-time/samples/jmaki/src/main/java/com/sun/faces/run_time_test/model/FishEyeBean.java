/*
 * FishEyeBean.java
 *
 * Created on October 26, 2006, 11:33 PM
 *
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
 * Copyright 2006 Sun Microsystems Inc. All Rights Reserved
 */

package com.sun.faces.run_time_test.model;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.util.List;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

/**
 *
 * @author edburns
 */
public class FishEyeBean {
    
    /** Creates a new instance of FishEyeBean */
    public FishEyeBean() {
    }
    
    public void valueChanged(ValueChangeEvent e) {
        FacesContext context = FacesContext.getCurrentInstance();
        AsyncResponse async = AsyncResponse.getInstance();
        String message = " oldValue: " + 
                e.getOldValue() + " newValue: " + e.getNewValue();
        context.getExternalContext().getRequestMap().put("fishEyeMessage", message);
        // Request DynaFaces to re-render only the fishEyeMessage.
        List<String> renderSubtrees = async.getRenderSubtrees();
        renderSubtrees.clear();
        renderSubtrees.add("fishEyeMessage");
        renderSubtrees.add("personMessage");
        renderSubtrees.add(e.getComponent().getClientId(context));
    }

    /**
     * Holds value of property selectedIndex.
     */
    private int selectedIndex;

    /**
     * Getter for property selectedIndex.
     * @return Value of property selectedIndex.
     */
    public int getSelectedIndex() {
        return this.selectedIndex;
    }

    /**
     * Setter for property selectedIndex.
     * @param selectedIndex New value of property selectedIndex.
     */
    public void setSelectedIndex(int selectedIndex) {
        this.selectedIndex = selectedIndex;
    }

    /**
     * Holds value of property personMessages.
     */
    private String [] personMessages;

    /**
     * Getter for property personMessages.
     * @return Value of property personMessages.
     */
    public String [] getPersonMessages() {
        return this.personMessages;
    }

    /**
     * Setter for property personMessages.
     * @param personMessages New value of property personMessages.
     */
    public void setPersonMessages(String [] personMessages) {
        this.personMessages = personMessages;
    }

    /**
     * Getter for property personMessage.
     * @return Value of property personMessage.
     */
    public String getPersonMessage() {
        return personMessages[getSelectedIndex()];
    }
    
}
