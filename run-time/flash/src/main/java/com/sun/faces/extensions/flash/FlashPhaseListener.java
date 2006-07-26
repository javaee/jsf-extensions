
/*
 * $Id: FlashPhaseListener.java,v 1.5 2005/12/16 21:32:37 edburns Exp $
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

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.ResponseStateManager;
import javax.portlet.PortletRequest;
import javax.portlet.PortletSession;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Cookie;

/**
 * <p>By leveraging <code>PhaseListener</code> we allow the flash
 * implementation to correctly manage clearing out the {@link ELFlash}
 * stored in the session at the proper time in the lifecycle.</p>
 *
 * @author edburns
 */
public class FlashPhaseListener extends Object implements PhaseListener {
    
    public FlashPhaseListener() {
    }

    private double sequenceNumber = 0;
    private synchronized Double getSequenceNumber() {
        if (Double.MAX_VALUE == ++sequenceNumber) {
            sequenceNumber = 0;
        }
        return new Double(sequenceNumber);
    }

    /**
     * <p>Perform actions that need to happen on the
     * <code>afterPhase</code> event.</p>
     *
     * <p>For after restore-view, if this is a postback, we extract the
     * sequenceId from the request and store it in the request
     * scope.</p>
     *
     * <p>For after render-response, we clear out the flash for the
     * postback, while leaving the current one intact. </p>
     */
    
    public void afterPhase(PhaseEvent e) {
        FacesContext context = e.getFacesContext();
        ExternalContext extContext = context.getExternalContext();
        Object request = extContext.getRequest(), response = extContext.getResponse();
        String postbackSequenceString = null;
        
        // If we're on after-restore-view...
        if (e.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
            // It is ok to read from the request parameters because the locale
            // and character encoding will have already been set.
            
            // If this is a postback...
            if (extContext.getRequestParameterMap().containsKey(ResponseStateManager.VIEW_STATE_PARAM)) {
                // to a servlet JSF app...
                if (response instanceof HttpServletResponse) {
                    // extract the sequence number from the cookie or portletSession
                    // for the request/response pair for which this request is a postback.
                    Cookie cookie = (Cookie)
                    extContext.getRequestCookieMap().
                            get(Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME);
                    if (null != cookie) {
                        postbackSequenceString = cookie.getValue();
                    }
                } else {
                    PortletRequest portletRequest = null;
                    portletRequest = (PortletRequest) request;
                    // You can't retrieve a cookie in portlet.
                    // http://wiki.java.net/bin/view/Portlet/JSR168FAQ#How_can_I_set_retrieve_a_cookie
                    postbackSequenceString = (String)
                    portletRequest.getPortletSession().
                            getAttribute(Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME,
                            PortletSession.PORTLET_SCOPE);
                }
                
                if (null != postbackSequenceString) {
                    // Store the sequenceNumber in the request so the
                    // after-render-response event can flush the flash
                    // of entries from that sequence
                    extContext.getRequestMap().put(Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME,
                            postbackSequenceString);
                }
            }
        } else if (e.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
            // Clear out the flash for the postback.
            if (null != (postbackSequenceString = (String)
            extContext.getRequestMap().
                    get(Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME))) {
                ELFlash flash = (ELFlash)ELFlash.getFlash(context, false);
                if (null != flash) {
                    flash.expireEntriesForSequence(postbackSequenceString);
                }
            }
        }
    }

    /**
     * <p>Perform actions that need to happen on the
     * <code>beforePhase</code> event.</p>
     *
     * <p>For all phases, store the current phaseId in request scope.</p>
     *
     * <p>For before restore-view, create a sequenceId for this request
     * and store it in request scope.</p>
     *
     * <p>For before render-response, store the sequenceId for this
     * request in the response.</p>
     *
     */
    
    public void beforePhase(PhaseEvent e) {
        FacesContext context = e.getFacesContext();
        ExternalContext extContext = context.getExternalContext();
        Double sequenceNumber = null;
        String thisRequestSequenceString = null;
        HttpServletResponse servletResponse = null;
        PortletRequest portletRequest = null;
        Object request = extContext.getRequest(), response = extContext.getResponse();
        
	extContext.getRequestMap().put(Constants.CURRENT_PHASE_ID_ATTRIBUTE_NAME,
                e.getPhaseId());
	
        
        // If we're on before-restore-view...
        if (e.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
            sequenceNumber = getSequenceNumber();
            thisRequestSequenceString = sequenceNumber.toString();
            // Put the sequence number for the request/response pair 
            // that is starting with *this particular request* in the request scope 
            // so the ELFlash can access it.
            extContext.getRequestMap().put(Constants.FLASH_THIS_REQUEST_ATTRIBUTE_NAME, 
                    thisRequestSequenceString);            
        }
        // If we're on before-render-response
        else if (e.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
            
            thisRequestSequenceString = 
                    extContext.getRequestMap().
                        get(Constants.FLASH_THIS_REQUEST_ATTRIBUTE_NAME).toString();            
            // Set the REQUEST_ID cookie to be the sequence number
            if (response instanceof HttpServletResponse) {
                servletResponse = (HttpServletResponse) response;
                Cookie cookie = new Cookie(Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME,
                        thisRequestSequenceString);
                cookie.setMaxAge(-1);
                servletResponse.addCookie(cookie);
            } else {
                portletRequest = (PortletRequest) request;
                // You can't add a cookie in portlet.
                // http://wiki.java.net/bin/view/Portlet/JSR168FAQ#How_can_I_set_retrieve_a_cookie
                portletRequest.getPortletSession().setAttribute(Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME,
                        thisRequestSequenceString, PortletSession.PORTLET_SCOPE);
            }
        }
        
    }

    /**
     * <p>We need to be notified of all phases.</p>
     */
    
    public PhaseId getPhaseId() {
	return PhaseId.ANY_PHASE;
    }
    
    
}
