/*
 * DynaFacesContextListener.java
 *
 * Created on July 25, 2006, 9:01 PM
 */

package com.sun.faces.extensions.avatar.lifecycle;

import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.component.UIComponent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletContextEvent;

/**
 *
 * @author  edburns
 * @version
 *
 * Web application lifecycle listener.
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
        Map<String,Constructor> eventMap = null;
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
        eventMap = new HashMap<String,Constructor>();
        evt.getServletContext().setAttribute(AsyncResponse.FACES_EVENT_CONTEXT_PARAM,
                eventMap);
        for (i = 0; i < facesEvents.length; i++) {
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
                    Class.forName(eventClass).getConstructor(eventCtorClasses));
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
