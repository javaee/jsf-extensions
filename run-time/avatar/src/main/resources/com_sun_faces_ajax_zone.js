/*
 * $Id: devtime.js,v 1.5 2006/01/13 16:05:28 edburns Exp $
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

var g_zones = [];

function ajaxifyChildren(target, options, closureHook) {
    if (null == target.isAjaxified && 
        target.hasChildNodes()) {
	for (var i = 0; i < target.childNodes.length; i++) {
	    takeActionAndTraverseTree(target, target.childNodes[i], 
				      moveAsideEventType, options, 
				      closureHook);
	}
    }
    target.isAjaxified = true;
    return false;
}

function moveAsideEventType(ajaxZone, element, options, closureHook) {
    if (null != options.eventType &&
	'on' == options.eventType.substring(0,2)) {
	options.eventType = eventType.substring(2);
    }
    options.render = g_zones.join(',');
    options.ajaxZone = ajaxZone;
    if (closureHook) {
	if (typeof closureHook == 'function') {
	    options.closure = closureHook(ajaxZone, element);
	}
	else if (typeof gGlobalScope[closureHook] == 'function') {
	    options.closure = gGlobalScope[closureHook](ajaxZone, element);
	}
    }
    var c = new Faces.Command(element, options.eventType, options);
}

function takeActionAndTraverseTree(target, element, action, options, 
				   closureHook) {
    var takeAction = false;

    // If the user defined an "inspectElement" function, call it.
    if (!(typeof options.inspectElementHook == 'function')) {
	if (typeof gGlobalScope[options.inspectElementHook] == 'function') {
	    options.inspectElementHook = gGlobalScope[options.inspectElementHook];
	}
    }
    if (null != options.inspectElementHook) {
	takeAction = options.inspectElementHook(element);
    }
    // If the function returned false or null, or was not defined...
    if (null == takeAction || !takeAction) {
      // if this element has a handler for the eventType
      if (null != element[options.eventType] &&
  	  null != element.getAttribute(options.eventType)) {
        takeAction = true;
      }
    }
    if (takeAction) {
	// take the action on this element.
	action(target, element, options, closureHook);
    }
    if (element.hasChildNodes()) {
	for (var i = 0; i < element.childNodes.length; i++) {
	    takeActionAndTraverseTree(target, element.childNodes[i], action, 
				      options, closureHook);
	}
    }
    return false;
}
