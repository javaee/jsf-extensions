/*
 * EventParser.java
 *
 * Created on August 3, 2006, 10:14 AM
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

package com.sun.faces.extensions.avatar.event;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import com.sun.faces.extensions.common.util.Util;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.FacesException;
import javax.faces.application.Application;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.event.FacesEvent;
import javax.faces.event.PhaseId;

/**
 *
 * @author edburns
 */
public class EventParser {
    
    /** Creates a new instance of EventParser */
    public EventParser() {
    }


    /**
     * <p>Look in the {@link #FACES_EVENT_HEADER} and parse it according to 
     * the following syntax</p>
     *
     * <code><pre>EVENT_TYPE,clientId,PhaseId[,eventCtorArgs...],...[;EVENT_TYPE,clientId,PhaseId,...]*</pre></code>
     *
     * <p>Where <code>EVENT_TYPE</code> is the String <code>ValueChangeEvent</code>,
     * <code>ActionEvent</code>, or one of the event types defined to the system
     * in the 
     * that must be a subclass of <code>FacesEvent</code>.
     *
     */
    
    public static void queueFacesEvents(FacesContext context) {
        Map<String, String> p = 
                FacesContext.getCurrentInstance().getExternalContext()
                .getRequestHeaderMap();
        List<FacesEvent> result = new ArrayList<FacesEvent>();
        String header = p.get(AsyncResponse.FACES_EVENT_HEADER);
        String [] events = null;
        String [] params = null;
        String [] ctorArgs = null;
        PhaseId phaseId = null;
        int i = 0, j = 0;
        if (header != null) {
            events = header.split(";");
            for (i = 0; i < events.length; i++) {
                params = events[i].split(",");
                phaseId = Util.getPhaseIdFromString(params[2]);
                // If we only have the EVENT_TYPE, clientId, and PhaseId...
                if (3 == params.length) {
                    // pass no ctor args.
                    queueFacesEvent(context, params[0], params[1], phaseId, 
                            new String[0]);
                }
                // Otherwise, if we have more than just those three pieces of data...
                else if (3 < params.length) {
                    // assume they are argument values
                    ctorArgs = new String[params.length - 3];
                    System.arraycopy(params, 3, ctorArgs, 0, ctorArgs.length);
                    queueFacesEvent(context, params[0], params[1], phaseId, ctorArgs);
                }
                else {
                    // Log Message.  Params must be >= 3
                }
            }
        }
    }

    private static void queueFacesEvent(FacesContext context,
            String eventId, String clientId, final PhaseId phaseId, 
            final String [] params) {
        FacesEvent result = null;
        Map<String,ConstructorWrapper> eventsMap = (Map<String,ConstructorWrapper>)
                context.getExternalContext().getApplicationMap().get(AsyncResponse.FACES_EVENT_CONTEXT_PARAM);
        
        assert(null != eventsMap);
        final ConstructorWrapper eventCtor = eventsMap.get(eventId);
        if (null != eventCtor) {
            
            context.getViewRoot().invokeOnComponent(context, clientId, new ContextCallback() {
                public void invokeContextCallback(FacesContext facesContext, 
                        UIComponent comp) {
                    FacesEvent event = null;
                    Class [] ctorArgClasses = eventCtor.getArgClasses();
                    int len = 0;
                    Object [] ctorArgs = getEventCtorArgs(facesContext, comp, 
                            ctorArgClasses, params);
                    try {
                        event = (FacesEvent) 
                                eventCtor.getConstructor().newInstance(ctorArgs);
                    } catch (InvocationTargetException ex) {
                        throw new FacesException(ex);
                    } catch (InstantiationException ex) {
                        throw new FacesException(ex);
                    } catch (IllegalAccessException ex) {
                        throw new FacesException(ex);
                    }
                    event.setPhaseId(phaseId);
                    comp.queueEvent(event);
                }
            });
        }
    }
    
    private static Object [] getEventCtorArgs(FacesContext context, UIComponent comp, 
            Class [] ctorArgClasses,
            String [] ctorArgValues) {
        Object [] result = null;
        Application app = context.getApplication();
        Converter converter = null;
        int argClassesLen = ctorArgClasses.length, 
            argValuesLen = ctorArgValues.length, 
            i = 0;
        

        // Special case heuristic.  Not sure if this is a good idea.
        
        // If ctorArgClasses.length == 1, ctorArgValues.length == 0
        // and ctorArgClasses[0] is a UIComponent, assume the only
        // argument is our comp argument.
        if (1 == argClassesLen && 0 == argValuesLen &&
            UIComponent.class.isAssignableFrom(ctorArgClasses[0])) {
            result = new Object[1];
            result[0] = comp;
        }
        // Otherwise, if the argument class list is a different length
        // than the argument values list...
        else if (argClassesLen != argValuesLen) {
            // return a result that indicates we cannot derive the 
            // constructor arguments.
            result = new Object[0];
        }
        else {
            // Otherwise, we know that the length of the argument class array
            // and the argument value array is the same.  Therefore, we assume
            // the ordering is the same and try to convert the values.
            assert(argClassesLen == argValuesLen);
            
            result = new Object[ctorArgClasses.length];

            // Iterate over both arrays and convert the elements using converters
            for (i = 0; i < ctorArgClasses.length; i++) {
                // First, check if this argument is an instanceof UIComponent...
                if (UIComponent.class.isAssignableFrom(ctorArgClasses[i])) {
                    // if so, assume it is intended to be the "source" argument.
                    result[i] = comp;
                }
                // Otherwise, try to use a converter to convert the type.
                else if (null != (converter = app.createConverter(ctorArgClasses[i]))) {
                    result[i] = converter.getAsObject(context, comp, ctorArgValues[i]);
                }
                // Otherwise, just use the unconverted value.
                else {
                    result[i] = ctorArgValues[i];
                }
            }
        }
        
        return result;
    }
    
}
