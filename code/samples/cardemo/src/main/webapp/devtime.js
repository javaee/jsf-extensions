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

function extractParams(ajaxZone, element, originalScript, outProps, 
		       invocation) {
    var name = null;
    var value = null;
    var elementNodeName = element.nodeName.toLowerCase();
    var elementType = element.type;

    if (null != elementType) {
	elementType = elementType.toLowerCase();
    }

    if (isCommand(elementNodeName, elementType)) {
	invocation.args[0].preventDefault();
	invocation.args[0].stopPropagation();
    }
    else {
	invocation.proceed();
    }
    
    // Start at the top of the zone, collect all the params, except for
    // command components.
    collectParams(ajaxZone, outProps);

    // Get the name and value of this selected component.  If this is a
    // command component, this step is necessary otherwise no value will
    // be submitted for the button.
    name = getParamNameFromElement(element, elementNodeName, elementType);
    value = getParamValueFromElement(element, elementNodeName, elementType);
    
    outProps[name] = value;
}

function refreshElement(name) {
    var element = null;
    if (null != name) {
	element = document.getElementById(name);
    }
    if (null == element) {
	var list = document.getElementsByName(name);
	if (null != list && 0 < list.length) {
	    element = list[0];
	}
    }
    return element;
}

/**
 * Operate recursively on the dom element and its children.
 * 
 * If the element is <input type="button">, <input type="submit">, or
 * <button>, take no action.
 *
 * If the element is any other kind of input or <option> element,
 * collect the params from it.
 */

function collectParams(element, props) {
    var name = null;
    var value = null;
    var elementNodeName = element.nodeName.toLowerCase();
    var elementType = element.type;
    var i = 0;
    var doCollect = isCollectParams(element);

    if (null != elementType) {
	elementType = elementType.toLowerCase();
    }

    if (doCollect) {
	name = getParamNameFromElement(element, elementNodeName, elementType);
	value = getParamValueFromElement(element, elementNodeName, 
					 elementType);
	props[name] = value;
    }
    
    if (element.hasChildNodes()) {
	for (i = 0; i < element.childNodes.length; i++) {
	    collectParams(element.childNodes[i], props);
	}
    }
    return;
}

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


/**
 * return false if the element is <input type="submit">, <input
 * type="button">, or <button>.  
 *
 * otherwise, if the element is any other kind of <input> element, or an
 * <option> element, return true.
 */

function isCollectParams(element) {
    var elementNodeName = element.nodeName.toLowerCase();
    var elementType = element.type;
    var doCollect = false;

    if (null != elementType) {
	elementType = elementType.toLowerCase();
    }

    if (!isCommand(elementNodeName, elementType) &&
	-1 != elementNodeName.indexOf("input")) {
	doCollect = true;
    }
    else if (-1 != elementNodeName.indexOf("option")) {
	doCollect = true
    }

    return doCollect;
}

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

function getParamValueFromElement(element, elementNodeName, elementType) {
    var result = null;

    if (isCheckbox(elementNodeName, elementType)) {
	result = element.checked;
    }
    else {
	result = element.value;
    }
    return result;
}

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
    

