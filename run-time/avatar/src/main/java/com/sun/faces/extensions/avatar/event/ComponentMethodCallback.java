/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
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

package com.sun.faces.extensions.avatar.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;
import javax.faces.render.Renderer;

public class ComponentMethodCallback implements ContextCallback {

    private final static Class[] RENDER_PARAM = new Class[] {
            FacesContext.class, UIComponent.class };

    private final static Class[] COMPONENT_PARAM = new Class[] { FacesContext.class };

    private final String methodName;

    private final String clientId;
    
    private final PhaseId phaseId;

    private Object result;

    public ComponentMethodCallback(String clientId, String methodName, 
            PhaseId phaseId) {
        this.clientId = clientId;
        this.methodName = methodName;
        this.phaseId = phaseId;
    }

    public Object invoke(FacesContext faces) {
        UIViewRoot root = faces.getViewRoot();
        root.invokeOnComponent(faces, this.clientId, this);
        return this.result;
    }

    public void invokeContextCallback(FacesContext faces, UIComponent c) {
        Method m = null;
        try {

            // first attempt to pull from renderer
            String t = c.getRendererType();
            if (t != null) {
                Renderer r = faces.getRenderKit().getRenderer(c.getFamily(), t);
                if (r != null) {
                    try {
                        m = r.getClass().getMethod(this.methodName, RENDER_PARAM);
                    } catch (Exception e) {
                    }

                    if (m != null) {
                        this.result = m.invoke(r, new Object[] { faces, c });
                    }
                }
            }

            // else delegate to component itself
            if (m == null) {
                m = c.getClass().getMethod(this.methodName, COMPONENT_PARAM);
                this.result = m.invoke(c, new Object[] { faces });
            }

        } catch (NoSuchMethodException e) {
            throw new FacesException(e);
        } catch (IllegalAccessException e) {
            throw new FacesException(e);
        } catch (InvocationTargetException e) {
            throw new FacesException(e.getCause());
        }
    }

    public Object getResult() {
        return this.result;
    }

    public String getClientId() {
        return clientId;
    }

    public String getMethod() {
        return methodName;
    }
    
    public PhaseId getPhaseId() {
        return phaseId;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer(32);
        return sb.append("Event[").append(clientId).append(',').append("].").append(this.methodName).append("()")
                .toString();
    }
}
