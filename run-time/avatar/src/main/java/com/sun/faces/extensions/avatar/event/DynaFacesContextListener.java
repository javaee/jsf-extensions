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

package com.sun.faces.extensions.avatar.event;

import com.sun.faces.extensions.avatar.lifecycle.*;
import java.lang.reflect.Constructor;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

/**
 *
 * @author  edburns
 *
 * <p>This listener, declared in the TLD for jsf-extensions-avatar, is
 * responsible for parsing the value of the
 * <code>com.sun.faces.extensions.avatar.FacesEvents</code>
 * <code>&lt;init-param&gt;</code> per the algorithm described in {@link
 * #contextInitialized}.</p>
 *
 */

public class DynaFacesContextListener implements ServletContextListener {

    
    /**
     * <p>Parse the {@link 
     * com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#FACES_EVENT_CONTEXT_PARAM}
     * context param and populate the application scoped Map under the same key
     * with according to the following syntax: </p>
     * 
     * <code><pre>
&lt;facesevent-header-value&gt; ::= &lt;facesevent-info&gt;[,&lt;facesevent-info&gt;...]

  &lt;facesevent-info&gt; :: = &lt;fqcn&gt;[":"&lt;fqcn&gt;[:&lt;fqcn&gt;...]]

  &lt;fqcn&gt; :: Java Language Fully Qualified Class Name
     * </pre></code>.
     * 
     * <p>Pairings for the jsf api are pre-populated in this map</p>
     */
    
    public void contextInitialized(ServletContextEvent evt) {
        String [] eventCtorInfo;
        String [] eventCtorParams;
        Class [] eventCtorClasses;
        String eventId = null;
        String eventClass = null;
        Map<String,ConstructorWrapper> eventMap = null;
        int len, i = 0, j = 0;
        String eventsParam = 
                evt.getServletContext().getInitParameter(AsyncResponse.FACES_EVENT_CONTEXT_PARAM);
        if (null == eventsParam) {
            eventsParam = "";
        }
        eventsParam = eventsParam + ",ValueChangeEvent:javax.faces.event.ValueChangeEvent:" + 
                "javax.faces.component.UIComponent:java.lang.Object:java.lang.Object" +
                ",ActionEvent:javax.faces.event.ActionEvent:javax.faces.component.UIComponent";
        
        String [] facesEvents = eventsParam.split(",");
        eventMap = new HashMap<String,ConstructorWrapper>();
        evt.getServletContext().setAttribute(AsyncResponse.FACES_EVENT_CONTEXT_PARAM,
                Collections.unmodifiableMap(eventMap));
        for (i = 0; i < facesEvents.length; i++) {
            // Skip empty entries
            if (0 == facesEvents[i].length()) {
                continue;
            }
            eventCtorInfo = facesEvents[i].split(":");
            if (eventCtorInfo.length < 2) {
                throw new FacesException("Can't understand " + 
                        AsyncResponse.FACES_EVENT_CONTEXT_PARAM + " " +
                        facesEvents[i]);
            }
            assert(2 <= eventCtorInfo.length);
            // PENDING() I18N
            String message = "Can't get class for event type " +
                    eventCtorInfo[0] + ".";
            eventId = eventCtorInfo[0];
            eventClass = eventCtorInfo[1];
            try {
                if (2 < eventCtorInfo.length) {
                    eventCtorParams = new String[(len = eventCtorInfo.length - 2)];
                    eventCtorClasses = new Class[len];
                    System.arraycopy(eventCtorInfo, 2, eventCtorParams, 0, len);
                    for (j = 0; j < len; j++) {
                        eventCtorClasses[j] = Class.forName(eventCtorParams[j]);
                    }
                }
                else {
                    eventCtorClasses = new Class[0];
                }
                eventMap.put(eventId, 
                        new ConstructorWrapper(Class.forName(eventClass).getConstructor(eventCtorClasses),
                            eventCtorClasses));
            } catch (SecurityException ex) {
                throw new FacesException(message);
            } catch (NoSuchMethodException ex) {
                throw new FacesException(message);
            } catch (ClassNotFoundException ex) {
                throw new FacesException(message);
            }
        }
    }

    /**
     * ### Method from ServletContextListener ###
     * 
     * Called when a Web application is about to be shut down
     * (i.e. on Web server shutdown or when a context is removed or reloaded).
     * Request handling will be stopped before this method is called.
     * 
     * For example, the database connections can be closed here.
     */
    public void contextDestroyed(ServletContextEvent evt) {
        evt.getServletContext().removeAttribute(AsyncResponse.FACES_EVENT_CONTEXT_PARAM);
    }
}
