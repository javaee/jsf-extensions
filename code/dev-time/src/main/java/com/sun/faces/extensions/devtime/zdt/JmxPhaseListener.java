/*
 * JmxPhaseListener.java
 *
 * Created on November 29, 2006, 4:22 PM
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

package com.sun.faces.extensions.devtime.zdt;

import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author edburns
 */
public class JmxPhaseListener implements PhaseListener {
    
    /** Creates a new instance of JmxPhaseListener */
    public JmxPhaseListener() {
    }

    public String toString() {
        String retValue;
        
        retValue = super.toString();
        return retValue;
    }

    public PhaseId getPhaseId() {
        return PhaseId.RESTORE_VIEW;
    }

    public void beforePhase(PhaseEvent phaseEvent) {
    }

    public void afterPhase(PhaseEvent phaseEvent) {
        FacesContext context = phaseEvent.getFacesContext();
        UIViewRoot root = context.getViewRoot();
        String contextRoot = null,
               viewId = root.getViewId();
        
        if (!viewId.equals("/restart")) {
            return;
        }
        
        HttpServletRequest request = (HttpServletRequest) 
          context.getExternalContext().getRequest();
        
        contextRoot = request.getContextPath();
        if (null == contextRoot) {
            return;
        }
        if (contextRoot.startsWith("/")) {
            contextRoot = contextRoot.substring(1);
        }
        final String [] args = { "--restart", "localhost", contextRoot, "admin", "adminadmin", "4848" };
        Runnable doRestart = new Runnable() {
            public void run() {
                try {
                    JMXDeploy.main(args);

                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            
        };
        Thread restartThread = new Thread(doRestart);
        restartThread.start();
        context.responseComplete();
    }
    
}
