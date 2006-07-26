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
     * <p>Parse the {@link com.sun.faces.extensions.avatar.lifecycle.AsyncResponse#FACES_EVENT_CONTEXT_PARAM}
     * context param and populate the application scoped Map under the same key
     * with the <code>eventType</code>:<code>event Constructor</code> pairings.</p>
     *
     * <p>Pairings for the jsf api are pre-populated in this map</p>
     */
    
    public void contextInitialized(ServletContextEvent evt) {
        String eventsParam = 
                evt.getServletContext().getInitParameter(AsyncResponse.FACES_EVENT_CONTEXT_PARAM);
        if (null == eventsParam) {
            eventsParam = "";
        }
        // Populate for the jsf-api events.
        eventsParam = eventsParam + ",ValueChangeEvent:javax.faces.event.ValueChangeEvent" +
                ",ActionEvent:javax.faces.event.ActionEvent";
        String [] facesEvents = eventsParam.split(",");
        String [] keyValue;
        Map<String,Constructor> eventMap = null;
        int i = 0;
        if (0 < facesEvents.length) {
            eventMap = new HashMap<String,Constructor>();
            evt.getServletContext().setAttribute(AsyncResponse.FACES_EVENT_CONTEXT_PARAM,
                    eventMap);
        }
        for (i = 0; i < facesEvents.length; i++) {
            keyValue = facesEvents[i].split(":");
            assert(null != eventMap);
            if (2 == keyValue.length) {
                // PENDING() I18N
                String message = "Can't get class for event type " +
                            keyValue[0] + ".  Given class name is " + keyValue[1] +
                            ".";
                try {
                        eventMap.put(keyValue[0], 
                                Class.forName(keyValue[1]).getConstructor(UIComponent.class));
                } catch (SecurityException ex) {
                    throw new FacesException(message);
                } catch (NoSuchMethodException ex) {
                    throw new FacesException(message);
                } catch (ClassNotFoundException ex) {
                    throw new FacesException(message);
                }
            }
            else {
                // PENDING(edburns): log message.
                throw new FacesException("Can't parse value of " + 
                        AsyncResponse.FACES_EVENT_CONTEXT_PARAM + " parameter");
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
