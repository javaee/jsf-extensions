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

if (typeof DynaFacesZones != 'undefined') {
    alert("DynaFacesZones already defined!"); 
}

var DynaFacesZones = new Object();

DynaFacesZones.g_zones = [];

DynaFacesZones.ajaxifyChildren = 
function ajaxifyChildren(target, options, getCallbackData) {
    if (null == target.isAjaxified && 
        target.hasChildNodes()) {
	for (var i = 0; i < target.childNodes.length; i++) {
	    DynaFacesZones.takeActionAndTraverseTree(target, 
                                      target.childNodes[i], 
				      DynaFacesZones.moveAsideEventType, 
                                      options, 
				      getCallbackData);
	}
    }
    target.isAjaxified = true;
    return false;
}

DynaFacesZones.moveAsideEventType = 
function moveAsideEventType(ajaxZone, element, options, getCallbackData) {
    if (null != options.eventType) {
	if('on' == options.eventType.substring(0,2)) {
	    options.eventType = eventType.substring(2);
	}
    }
    else {
	options.eventType = 'click';
    }
    options.render = DynaFacesZones.g_zones.join(',');
    options.ajaxZone = ajaxZone;
    if (getCallbackData) {
	if (typeof getCallbackData == 'function') {
	    options.closure = getCallbackData(ajaxZone, element);
	}
	else if (typeof gGlobalScope[getCallbackData] == 'function') {
	    options.closure = gGlobalScope[getCallbackData](ajaxZone, element);
	}
    }
    var c = new Faces.DeferredEvent(element, options.eventType, options);
}

DynaFacesZones.takeActionAndTraverseTree = 
function takeActionAndTraverseTree(target, element, action, options, 
				   getCallbackData) {
    var takeAction = false;
    var inspectElementFunction = DynaFacesZones.inspectElement;

    // If the user defined an "inspectElement" function, call it.
    if (!(typeof options.inspectElement == 'function')) {
	if (typeof gGlobalScope[options.inspectElement] == 'function') {
	    options.inspectElement = gGlobalScope[options.inspectElement];
	}
    }
    if (null != options.inspectElement) {
        inspectElementFunction = options.inspectElement;
    }
    takeAction = inspectElementFunction(element);

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
	action(target, element, options, getCallbackData);
    }
    if (element.hasChildNodes()) {
	for (var i = 0; i < element.childNodes.length; i++) {
	    DynaFacesZones.takeActionAndTraverseTree(target, 
                                      element.childNodes[i], action, 
				      options, getCallbackData);
	}
    }
    return false;
}

DynaFacesZones.inspectElement = 
function inspectElement(element) {
    var result = false;
    if (null != element) {
	var nodeName = element.nodeName;
	if (null != nodeName) {
	    nodeName = nodeName.toLowerCase();
	    if (-1 != nodeName.indexOf("input")) {
		result = true;
	    }
	    else if (-1 != nodeName.indexOf("option")) {
		result = true;
	    }
	    else if (-1 != nodeName.indexOf("button")) {
		result = true;
	    }
	}
    }
    return result;
}

