
/*
 * $Id: ELFlash.java,v 1.6 2005/12/16 21:32:36 edburns Exp $
 */

/*
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
 * Copyright 2005 Sun Microsystems Inc. All Rights Reserved
 */


package com.sun.faces.extensions.flash;

import java.util.HashMap;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseId;

/**
 *
 * <p>A Map implementation that provides semantics identical to the <a
 * target="_"
 * href="http://api.rubyonrails.com/classes/ActionController/Flash.html">
 * "flash" concept in Ruby on Rails</a>.</p>
 * 
 * <p>Usage</p>
 *
 * <p>See {@link FlashELResolver} for usage instructions.</p>
 * 
 * <p>Methods on this Map have different effects depending on the
 * current lifecycle phase when the method is invoked.  During all
 * lifecycle phases earlier than invoke-application, methods on this Map
 * are directed to an internal Map that is cleared at the end of the
 * current lifecycle run.  For invoke-application and beyond, all
 * operations are directed to an internal Map that will be accessible
 * for all lifecycle phases on the <b>next</b> request on this session,
 * up to, but not including, invoke-application.</p>
 *
 * @author edburns
 */
public class ELFlash implements Map<String,Object> {
    
    private Map<String,Map<String, Object>> innerMap = null;
    
    /** Creates a new instance of ELFlash */
    private ELFlash() {
        // We only need exactly two entries.
        innerMap = new HashMap<String,Map<String, Object>>(2);
    }

    /**
     * <p>Returns the flash <code>Map</code> for this session.  This is
     * a convenience method that calls
     * <code>FacesContext.getCurrentInstance()</code> and then calls the
     * overloaded <code>getFlash()</code> that takes a
     * <code>FacesContext</code> with it.</p>
     * 
     * @return The flash <code>Map</code> for this session.
     */
    
    public static Map<String,Object> getFlash() {
        FacesContext context = FacesContext.getCurrentInstance();
        return getFlash(context, true);
    }

    /**
     *
     * @param context the <code>FacesContext</code> for this request.
     *
     * @param create <code>true</code> to create a new instance for this request if 
     * necessary; <code>false</code> to return <code>null</code> if there's no 
     * instance in the current <code>session</code>.
     * 
     * @return The flash <code>Map</code> for this session.
     */
    
    public static Map<String,Object> getFlash(FacesContext context, boolean create) {
        ELFlash flash = (ELFlash) 
            context.getExternalContext().
                getSessionMap().get(Constants.FLASH_ATTRIBUTE_NAME);
        if (null == flash && create) {
            flash = new ELFlash();
        }
        return flash;
    }
    
    /**
     * <p>Returns the correct Map considering the current lifecycle phase.</p>
     *
     * <p>If the current lifecycle phase is invoke-application or
     * render-response, call {@link #getNextRequestMap} and return the result.
     * Otherwise, call {@link #getThisRequestMap} and return the result.</p>
     *
     * @return the "correct" map for the current lifecycle phase.
     */
    
    protected Map<String,Object> getPhaseMap() {
        Map<String,Object> result = null;
        FacesContext context = FacesContext.getCurrentInstance();
        PhaseId currentPhase = (PhaseId)
                context.getExternalContext().getRequestMap().
                    get(Constants.CURRENT_PHASE_ID_ATTRIBUTE_NAME);
        // If we're in invoke-application or render-response phase...
        if (currentPhase == PhaseId.RENDER_RESPONSE || 
            currentPhase == PhaseId.INVOKE_APPLICATION) {
            // make operations go to the next request Map.
            result = getNextRequestMap(context);
        }
        else {
            // Otherwise, make operations go this request Map.
            result = getThisRequestMap(context);
        }
        return result;
    }
    
    /**
     * @return the Map flash for the next postback.  This Map is used by the
     * flash for all operations during the render-response lifecycle phase.  
     * During all other lifecycle phases, operations go instead to the Map
     * returned by {@link #getThisRequestMap}.
     */
    private Map<String,Object> getNextRequestMap(FacesContext context) {
        return getMapForSequenceId(context, 
                Constants.FLASH_THIS_REQUEST_ATTRIBUTE_NAME);
    }
    
    /**
     * @return the Map flash for this postback.  This Map is used by the flash
     * for all operations during all lifecycle phases except render-response.
     * During render-response, any flash operations go instead to the Map returned
     * by {@link #getNextRequestMap}.
     */
    private Map<String,Object> getThisRequestMap(FacesContext context) {
        return getMapForSequenceId(context,
                Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME);
    }    
    
    /**
     * <p>This is a private helper method for {@link #getNextRequestMap} and 
     * {@link #getThisRequestMap}.
     *
     * @return the sequence Map given the sequence identifier.
     */
    
    private Map<String,Object> getMapForSequenceId(FacesContext context, String attrName) {
        Object sequenceId = context.getExternalContext().
                getRequestMap().get(attrName);
        if (null == sequenceId) {
            return null;
        }
        
        Map<String,Object> result = innerMap.get(sequenceId.toString());
        if (null == result) {
            result = new HashMap<String,Object>();
            innerMap.put(sequenceId.toString(), result);
        }
        
        assert(null != result);
        return result;
    }
    
    /**
     * <p>Called by the {@link FlashPhaseListener#afterPhase} for the 
     * render-response phase to clear out the flash for the appropriate
     * sequence.
     */
        
    void expireEntriesForSequence(String sequence) {
        Map<String, Object> toExpire = innerMap.get(sequence);
        if (null != toExpire) {
            toExpire.clear();
            innerMap.remove(sequence);
        }
    }
    
    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public Object remove(Object key) {
        return getPhaseMap().remove(key);
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public boolean containsKey(Object key) {
        return getPhaseMap().containsKey(key);
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public boolean containsValue(Object value) {
        return getPhaseMap().containsValue(value);
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public boolean equals(Object obj) {
        return getPhaseMap().equals(obj);
    }
    
    /**
     * <p>This operation takes special care to handle the "keep" operation on 
     * the flash.  Consider the expression #{flash.keep.foo}.  A set operation
     * on this expression will simply end up calling the {@link #put} method.
     * During a get operation on this expression, we first look in the Map for  
     * the currently posting back request.  If no value is found, we look in
     * the request scope for the current request.  If a value is found there, we
     * place it in the map for the next request.  We return the value.</p>
     *
     * <p>In the case of a non "keep" operation, this method simply returns
     * the value from the Map for the currently posting back request.</p>
     */

    public Object get(Object key) {
        FacesContext context = FacesContext.getCurrentInstance();        
        Map<String,Object> map = getThisRequestMap(context);
        Object requestValue = null, result = null;
        // For gets, look in the postbackSequenceMap.
        if (null != map) {
            result = map.get(key);
        }
        // If not found in the postbackSequenceMap and we're doing a "keep" promotion...
        if (null == result && FlashELResolver.isDoKeep()) {
            // See if we have a value in the request scope.
            if (null != (requestValue = 
                    FacesContext.getCurrentInstance().getExternalContext().
                        getRequestMap().get(key))) {
                // get the value from the request scope
                result = requestValue;
            }
        }
        // If this resolution is for a keep promotion...
        if (FlashELResolver.isDoKeep()) {
            FlashELResolver.setDoKeep(false);
            // Do the promotion.
            getNextRequestMap(context).put((String) key, result);
        }
        return result;
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public void putAll(Map<? extends String, ? extends Object> t) {
        getPhaseMap().putAll(t);
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public Object put(String key, Object value) {
        return getPhaseMap().put(key, value);
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public java.util.Collection<Object> values() {
        return getPhaseMap().values();
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public String toString() {
        return getPhaseMap().toString();
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public int size() {
        return getPhaseMap().size();
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public void clear() {
        getPhaseMap().clear();
    }

    /**
     * @throws CloneNotSupportedException
     */

    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public java.util.Set<java.util.Map.Entry<String, Object>> entrySet() {
        return getPhaseMap().entrySet();
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public int hashCode() {
        return getPhaseMap().hashCode();
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public boolean isEmpty() {
        return getPhaseMap().isEmpty();
    }

    /**
     * <p>Get the correct map as descibed above and perform this operation on
     * it.</p>
     */

    public java.util.Set<String> keySet() {
        return getPhaseMap().keySet();
    }


}
