/*
 * SuggestEvent.java
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
 *
 */

package com.enverio.jsf;

import javax.faces.component.UIComponent;
import javax.faces.event.FacesEvent;
import javax.faces.event.FacesListener;

/**
 * <p>Instances of this event indicate that the UISuggest component
 * listed as the <code>source</code> of this event should perform a
 * component specific "suggestion" action, possibly rendering some
 * content to the response.</p>
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
