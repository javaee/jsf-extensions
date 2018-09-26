/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2006-2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
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
