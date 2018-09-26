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
 * $Id: devtime.js,v 1.5 2006/01/13 16:05:28 edburns Exp $
 */

if (typeof DynaFacesZones != 'undefined') {
    alert("DynaFacesZones already defined!"); 
}

var DynaFacesZones = new Object();

DynaFacesZones.g_zones = [];

DynaFacesZones.ajaxifyChildren = 
function ajaxifyChildren(target, options, getCallbackData) {
    if (!options.collectPostData) {
	options.collectPostData = DynaFacesZones.collectPostData;
    }
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
	    options.eventType = options.eventType.substring(2);
	}
    }
    else {
	options.eventType = 'click';
    }
    // If the user specified an execute attribute...
    if (null != ajaxZone["execute"]) {
      // copy it to the DynaFaces options.
      options.execute = ajaxZone["execute"];
    }
    else {
      // Otherwise, just execute this zone.
      options.execute = ajaxZone.id;
    }
    // If the user specified a render attribute...
    if (null != ajaxZone["render"]) {
      // copy it to the DynaFaces options.
      options.render = ajaxZone["render"];
    }
    else {
      // Otherwise, just render this zone.
      options.render = ajaxZone.id;
    }
    options.ajaxZone = ajaxZone;
    if (getCallbackData) {
	if (typeof getCallbackData == 'function') {
	    options.closure = getCallbackData(ajaxZone, element);
	}
	else if (typeof DynaFaces.gGlobalScope[getCallbackData] == 'function') {
	    options.closure = DynaFaces.gGlobalScope[getCallbackData](ajaxZone, element);
	}
    }
    if (DynaFaces.isJsfCommandLink(element) && 'click' === options.eventType) {
	element["onclick"] = null;
    }
    DynaFaces.installDeferredAjaxTransaction(element, options.eventType, 
					     options);
}

DynaFacesZones.takeActionAndTraverseTree = 
function takeActionAndTraverseTree(target, element, action, options, 
				   getCallbackData) {
    var takeAction = false;
    var inspectElementFunction = DynaFacesZones.inspectElement;

    // If the user defined an "inspectElement" function, call it.
    if (!(typeof options.inspectElement == 'function')) {
	if (typeof DynaFaces.gGlobalScope[options.inspectElement] == 'function') {
	    options.inspectElement = DynaFaces.gGlobalScope[options.inspectElement];
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
	    if (0 == nodeName.indexOf("input")) {
		result = true;
	    }
	    else if (0 == nodeName.indexOf("option")) {
		result = true;
	    }
	    else if (0 == nodeName.indexOf("button")) {
		result = true;
	    }
	    else if (DynaFaces.isJsfCommandLink(element)) {
		result = true;
	    }
	}
    }
    return result;
}

DynaFaces.isJsfCommandLink = 
    function isJsfCommandLink(element) {
    var result = false;

    if (null == element) {
	return result;
    }

    var nodeName = element.nodeName;
    if (null != nodeName) {
	nodeName = nodeName.toLowerCase();
	// if this an a href...
	if (0 == nodeName.indexOf("a") && 1 == nodeName.length
	    && element.hasAttribute('href')) {
	    // Use a heuristic to test if this is a JSF generated commandlink.
	    
	    // If it has an "onclick" attribute, the tostring of which contains
	    // "jsfcljs", return true.
	    var onclick = element["onclick"];
	    if (null != onclick) {
		onclick = onclick.toString();
		result = (-1 != onclick.indexOf("jsfcljs"));
	    }
	}
    }
    return result;
}

DynaFacesZones.collectPostData = 
    function collectPostData(ajaxZone, element, outArray) {

    props = new Object();
    // Start at the top of the zone, collect all the params, except for
    // command (anchor and button) components.
    if (ajaxZone.hasChildNodes()) {
	for (var i = 0; i < ajaxZone.childNodes.length; i++) {
	    DynaFacesZones.takeActionAndTraverseTree(ajaxZone, 
                                      ajaxZone.childNodes[i], 
				      DynaFacesZones.extractParamFromElement, 
				      props, null);
	}
    }
    // Get the name and value of this selected component.  If this is a
    // command component, this step is necessary otherwise no value will
    // be submitted for the button.
    var name = null;
    var value = null;
    var elementNodeName = element.nodeName.toLowerCase();
    var elementType = element.type;

    name = DynaFacesZones.getParamNameFromElement(element, 
						  elementNodeName, 
						  elementType);
    value = DynaFacesZones.getParamValueFromElement(element, 
						    elementNodeName, 
						    elementType);
    props[name] = value;
    for (prop in props) {
	outArray.push(prop+'='+props[prop]);
    }

    
}

DynaFacesZones.extractParamFromElement = 
    function extractParamFromElement(ajaxZone, element, outProps, 
				     getCallbackData) {
    var name = null;
    var value = null;
    var elementNodeName = element.nodeName.toLowerCase();
    var elementType = element.type;
    var i = 0;
    var doCollect = DynaFacesZones.isCollectParams(element);

    if (null != elementType) {
	elementType = elementType.toLowerCase();
    }

    if (doCollect) {
	name = DynaFacesZones.getParamNameFromElement(element, elementNodeName,
						      elementType);
	value = DynaFacesZones.getParamValueFromElement(element, 
							elementNodeName, 
							elementType);
	outProps[name]=value;
    }
   
}

DynaFacesZones.getParamValueFromElement = 
    function getParamValueFromElement(element, elementNodeName, elementType) {
    var result = null;

    if (DynaFacesZones.isCheckbox(elementNodeName, elementType)) {
	result = element.checked;
    }
    else {
	result = element.value;
    }
    return result;
}

DynaFacesZones.getParamNameFromElement =
    function getParamNameFromElement(element, elementNodeName, elementType) {
    var name = null;
    
    if (-1 != elementNodeName.indexOf("option")) {
	// the name comes from the parent <select> element
	while (null != (element = element.parentNode) &&
	       -1 == element.nodeName.toLowerCase().indexOf("select"));
    }	
    if (null != element) {
	if (null == (name = element.name)) {
	    name = element.id;
	}
    }
    return name;
}

DynaFacesZones.isCheckbox =
    function isCheckbox(elementNodeName, elementType) {
    var result = false;

    // If the element is an input element...
    if (-1 != elementNodeName.indexOf("input")) {
	if (null != elementType) {
	    if (-1 != elementType.indexOf("checkbox")) {
		result = true;
	    }
	}
    }
    return result;
}

/**
 * return false if the element is <input type="radio">,
 * <input type="submit">, <input type="button">, <option> or <button>.  
 *
 * otherwise, if the element is any other kind of <input> element return true.
 */

DynaFacesZones.isCollectParams =
    function isCollectParams(element) {
    var elementNodeName = element.nodeName.toLowerCase();
    var elementType = element.type;
    var doCollect = false;

    if (null != elementType) {
	elementType = elementType.toLowerCase();
    }

    if (!DynaFacesZones.isCommand(elementNodeName, elementType)) {
	// What sort of element is it?
	if (-1 != elementNodeName.indexOf("input")) {
	    // It is an input element, but is it a radio?
	    if (-1 != elementType.indexOf("radio")) {
		// Yes.  Only return true if it is selected.
		if (element.checked) {
		    doCollect = true;
		}

	    }
	    else {
		// Other kinds of input elements are submitted
		doCollect = true;
	    }
	}
	else if (-1 != elementNodeName.indexOf("option")) {
	    // It is an option element, but is it selected?
	    if (element.selected) {
		doCollect = true;
	    }
	}
    }

    return doCollect;
}

DynaFacesZones.isCommand =
    function isCommand(elementNodeName, elementType) {
    var result = false;

    // If the element is an input element...
    if (-1 != elementNodeName.indexOf("input")) {
	if (null != elementType) {
	    //  whose type is "button" or "submit" 
	    if (-1 != elementType.indexOf("submit") ||
		-1 != elementType.indexOf("button")) {
		// collect its params.
		result = true;
	    }
	}
    }
    else if (-1 != elementNodeName.indexOf("button")) {
	result = true;
    }
    else if (elementNodeName === "a") {
	result = true;
    }

    return result;
}
