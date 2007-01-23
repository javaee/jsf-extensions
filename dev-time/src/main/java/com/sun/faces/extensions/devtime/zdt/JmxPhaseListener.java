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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

/**
 *
 * @author edburns
 */
public class JmxPhaseListener implements PhaseListener {
    
    public static final String RESTART_PASSWORD_PARAM_NAME = 
            "com.sun.faces.RESTART_PASSWORD";
    
    public static final String ADMIN_PASSWORD_PARAM_NAME = 
            "com.sun.faces.ADMIN_PASSWORD";
    
    public static final String ADMIN_PORT_PARAM_NAME = 
            "com.sun.faces.ADMIN_PORT";
    
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
        String viewId = root.getViewId();
        
        if (!viewId.contains("/restart")) {
            return;
        }
        
        doRestart(context);
        
    }
    
    private void doRestart(FacesContext context) {
        ExternalContext extContext = context.getExternalContext();
        String  restartPasswordParam = extContext.getRequestParameterMap().get("password"),
                restartPassword = getRestartPassword(extContext);
        assert(null != restartPassword);
        
        // If there is no password param, take no action
        if (null == restartPasswordParam) {
            return;
        }
        
        // If the restart password param is not equal to the restart password, 
        // take no action
        if (!restartPasswordParam.equals(restartPassword)) {
            return;
        }
        
        String contextRoot = extContext.getRequestContextPath();
        if (null == contextRoot) {
            return;
        }
        if (contextRoot.startsWith("/")) {
            contextRoot = contextRoot.substring(1);
        }
        final String [] args = { "--restart", "localhost", contextRoot, "admin", 
          getAdminPassword(extContext), getAdminPort(extContext) };
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
    
    private String getRestartPassword(ExternalContext extContext) {
        String restartPassword = "adminadmin";
        
        String contextRestartPassword = 
                extContext.getInitParameter(RESTART_PASSWORD_PARAM_NAME);
        if (null != contextRestartPassword) {
            restartPassword = contextRestartPassword;
        }
        
        return restartPassword;
    }
    
    private String getAdminPassword(ExternalContext extContext) {
        String restartPassword = "adminadmin";
        
        String contextRestartPassword = 
                extContext.getInitParameter(ADMIN_PASSWORD_PARAM_NAME);
        if (null != contextRestartPassword) {
            restartPassword = contextRestartPassword;
        }
        
        return restartPassword;
    }
    
    private String getAdminPort(ExternalContext extContext) {
        String restartPassword = "4848";
        
        String contextRestartPassword = 
                extContext.getInitParameter(ADMIN_PORT_PARAM_NAME);
        if (null != contextRestartPassword) {
            restartPassword = contextRestartPassword;
        }
        
        return restartPassword;
    }
    
    
}
