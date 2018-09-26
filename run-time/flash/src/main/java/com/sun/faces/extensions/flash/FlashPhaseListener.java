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

/*
 * $Id: FlashPhaseListener.java,v 1.5 2005/12/16 21:32:37 edburns Exp $
 */

package com.sun.faces.extensions.flash;

import java.util.Map;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.render.ResponseStateManager;
//import javax.portlet.PortletRequest;
//import javax.portlet.PortletSession;
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

    private long sequenceNumber = 0;
    private synchronized long getSequenceNumber() {
        if (Long.MAX_VALUE == ++sequenceNumber) {
            sequenceNumber = 0;
        }
        return sequenceNumber;
    }
    
    static String getCookieValue(ExternalContext extContext) {
        String result = null;
        Cookie cookie = (Cookie)
        extContext.getRequestCookieMap().
                get(Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME);
        if (null != cookie) {
            result = cookie.getValue();
        }
        
        return result;
    }
    
    private void addCookie(ExternalContext extContext) {
        String thisRequestSequenceString = null;
        HttpServletResponse servletResponse = null;
        //PortletRequest portletRequest = null;
        Object request = extContext.getRequest(), response = extContext.getResponse();

        thisRequestSequenceString =
                extContext.getRequestMap().
                get(Constants.FLASH_THIS_REQUEST_ATTRIBUTE_NAME).toString();

        if (response instanceof HttpServletResponse) {
            servletResponse = (HttpServletResponse) response;
            Cookie cookie = new Cookie(Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME,
                    thisRequestSequenceString);
            cookie.setMaxAge(-1);
            servletResponse.addCookie(cookie);
        } else {
            /*****
             * portletRequest = (PortletRequest) request;
             * // You can't add a cookie in portlet.
             * // http://wiki.java.net/bin/view/Portlet/JSR168FAQ#How_can_I_set_retrieve_a_cookie
             * portletRequest.getPortletSession().setAttribute(Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME,
             * thisRequestSequenceString, PortletSession.PORTLET_SCOPE);
             *********/
        }
    }
    
    private void expireEntries(FacesContext context) {
        ExternalContext extContext = context.getExternalContext();
        String postbackSequenceString = null;
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
        Map<String, Object> requestMap = extContext.getRequestMap();
        Object request = extContext.getRequest(), response = extContext.getResponse();
        String postbackSequenceString = null;
        ELFlash elFlash = ELFlash.getELFlash();
        
        // If we're on after-restore-view...
        if (e.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
            // It is ok to read from the request parameters because the locale
            // and character encoding will have already been set.
            
            if (extContext.getRequestParameterMap().
                        containsKey(ResponseStateManager.VIEW_STATE_PARAM)) {
                    // to a servlet JSF app...
                if (response instanceof HttpServletResponse) {
                    // extract the sequence number from the cookie or portletSession
                    // for the request/response pair for which this request is a postback.
                    postbackSequenceString = getCookieValue(extContext);
                } else {
                    /******
                     * PortletRequest portletRequest = null;
                     * portletRequest = (PortletRequest) request;
                     * // You can't retrieve a cookie in portlet.
                     * // http://wiki.java.net/bin/view/Portlet/JSR168FAQ#How_can_I_set_retrieve_a_cookie
                     * postbackSequenceString = (String)
                     * portletRequest.getPortletSession().
                     * getAttribute(Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME,
                     * PortletSession.PORTLET_SCOPE);
                     *******/
                }
            }

            if (null != postbackSequenceString) {
                // Store the sequenceNumber in the request so the
                // after-render-response event can flush the flash
                // of entries from that sequence
                requestMap.put(Constants.FLASH_POSTBACK_REQUEST_ATTRIBUTE_NAME,
                        postbackSequenceString);
            }

        } else if (e.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
            expireEntries(context);
        }
        
/*******        
        
        // If this requset is ending normally...
        if (e.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
            // and the user requested we save all request scoped data...
            if (null != elFlash && elFlash.isKeepAllRequestScopedData()) {
                // save it all.
                elFlash.saveAllRequestScopedData(context);
            }
        }
        // Otherwise, if this request is ending early...
        else 
 * 
 */ 
        if ((context.getResponseComplete() || context.getRenderResponse()) &&
                elFlash.isRedirectAfterPost()) {
            // and the user requested we save all request scoped data...
            if (null != elFlash && elFlash.isKeepAllRequestScopedData()) {
                // save it all.
                addCookie(extContext);
                elFlash.saveAllRequestScopedData(context);
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
        Map<String, Object> requestMap = extContext.getRequestMap();
        String thisRequestSequenceString = null;
        
	extContext.getRequestMap().put(Constants.CURRENT_PHASE_ID_ATTRIBUTE_NAME,
                e.getPhaseId());
	
        
        // If we're on before-restore-view...
        if (e.getPhaseId().equals(PhaseId.RESTORE_VIEW)) {
            thisRequestSequenceString = Long.toString(getSequenceNumber());
            // Put the sequence number for the request/response pair 
            // that is starting with *this particular request* in the request scope 
            // so the ELFlash can access it.
            requestMap.put(Constants.FLASH_THIS_REQUEST_ATTRIBUTE_NAME, 
                    thisRequestSequenceString);   

            // Make sure to restore all request scoped data
            ELFlash elFlash = ELFlash.getELFlash();
            if (null != elFlash && elFlash.isKeepAllRequestScopedData()) {
                elFlash.restoreAllRequestScopedData(context);
            }
        }
        // If we're on before-render-response
        else if (e.getPhaseId().equals(PhaseId.RENDER_RESPONSE)) {
            
            // Set the REQUEST_ID cookie to be the sequence number
            addCookie(extContext);
        }
        
    }
    
    /**
     * <p>We need to be notified of all phases.</p>
     */
    
    public PhaseId getPhaseId() {
	return PhaseId.ANY_PHASE;
    }
    
    
}
