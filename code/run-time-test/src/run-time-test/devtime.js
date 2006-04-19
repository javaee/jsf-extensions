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

dojo.require("dojo.fx.*");
dojo.require("dojo.io.*");

var g_jsfJspContexts = new Array();

var g_request = null;

var g_eventPrefix = "jsfex";

dojo.fx.html.crossfadeSwitch = function(nodeOut, nodeIn, duration, callback, dontPlay){
	nodeOut = dojo.byId(nodeOut);
	nodeIn = dojo.byId(nodeIn);
 
	dojo.style.setOpacity(nodeIn, 0);
	nodeIn.style.display = "block";
 
	dojo.fx.html.crossfade(nodeOut, nodeIn, duration, 0, 1, function(){
		// note: This is a swap
		var innerHTML = nodeOut.innerHTML;
		nodeOut.innerHTML = nodeIn.innerHTML;
		nodeOut.style.display = "block";
		dojo.style.setOpacity(nodeOut, 1);
		nodeIn.style.display = "none";
		dojo.style.setOpacity(nodeIn, 0);
		dojo.lang.setTimeout(null, function(){ nodeIn.innerHTML = innerHTML; }, 0); // This prevents flicker
	});
}
// http://10is2.com/dojo/test.html
dojo.fx.html.crossfade = function(nodeOut, nodeIn, duration, startOpac, endOpac, callback, dontPlay){
	if(arguments.length < 4){
		startOpac = 0;
	}
	if(arguments.length < 5){
		endOpac = 1;
	}
	nodeOut = dojo.byId(nodeOut);
	nodeIn = dojo.byId(nodeIn);
	dojo.fx.html._makeFadeable(nodeOut);
	dojo.fx.html._makeFadeable(nodeIn);
	var anim = new dojo.animation.Animation(new dojo.math.curves.Line([startOpac], [endOpac]), duration, 0);
      dojo.event.connect(anim, "onAnimate", function(e){
		dojo.style.setOpacity(nodeIn, e.x);
		dojo.style.setOpacity(nodeOut, endOpac - e.x);
	});
	if(callback) {
		dojo.event.connect(anim, "onEnd", function(e){
			callback(nodeOut, nodeIn, anim);
		});
	}
	if(!dontPlay){ anim.play(true); }
	return anim;
}

function handleOnclick(event) {
    submitViaAJAX(event, "onmousedown");
    return false;
}

function ajaxifyChildren(target) {
    if (null == target.isAjaxified && 
        target.hasChildNodes()) {
	for (var i = 0; i < target.childNodes.length; i++) {
	    takeActionAndTraverseTree(target.childNodes[i], 
				      moveAsideOnMouseDown);
	}
	target.onclick = handleOnclick;
    }
    target.isAjaxified = true;
    return false;
}



function processAjaxResponse() {
    var request = getXMLHttpRequest();
    
    if (request.readyState == 4) {
	if (request.status == 200) {
	    var subview1 = window.document.getElementById("form:subview1");
	    var subview2 = window.document.getElementById("form:subview2");
	    var fadeIn = window.document.getElementById("fadeIn");
	    var fadeOut = window.document.getElementById("fadeOut");
	    var pCtxts = 
		request.responseXML.getElementsByTagName("processing-context");
	    var newSubview1 = pCtxts[0];
	    var newSubview2 = pCtxts[1];

	    fadeIn.style.display = "none";
	    fadeIn.innerHTML = newSubview1.childNodes[0].data;
	    dojo.fx.html.crossfadeSwitch(fadeOut, fadeIn, 500);
	    subview2.innerHTML = newSubview2.childNodes[0].data;
	    var controlSpan = window.document.getElementById("controlSpan");
	    controlSpan.isAjaxified = null;
	}
    }
}

function takeActionAndTraverseTree(element, action) {
    action(element);
    if (element.hasChildNodes()) {
	for (var i = 0; i < element.childNodes.length; i++) {
	    takeActionAndTraverseTree(element.childNodes[i], action);
	}
    }
    return false;
}

function moveAsideOnMouseDown(element) {
    var handler = null;
    var src = "onmousedown";
    var dest = g_eventPrefix + src;
    if (null != element.onmousedown && 
	null != (handler = element.getAttribute(src))) {

	element.onmousedown = null;
	element.setAttribute(src, null);
	element.jsfexonmousedown = handler;
	element.setAttribute(dest, handler);
    }

}


function submitViaAJAX(event, handlerName) {
    var props = new Object();
    var pruned = pruneSubmitFromEventHandler(event, handlerName);
    var paramStruct = extractFormParamsFromPrunedHandlerStatements(pruned, props);
    props = paramStruct.params;
    props['com.sun.faces.PCtxt'] = ":form:subview1,:form:subview2";
    props['sjwuic_update', false];
    var requestStruct = prepareRequest(props);

    dojo.io.bind({
        method: "POST",
        url: window.location,
        content: content,
        formNode: window.document.forms[0],
        load: function(type, data, evt) {
            var ajaxZones = data.documentElement;
            for (var i = 0; i < ajaxZones.childNodes.length; i++) {
                var ajaxZone = ajaxZones.childNodes[i];
                var id = ajaxZone.attributes.getNamedItem("id").nodeValue;

                var zone = document.getElementById(id);
                if (zone != null) {
                    zone.innerHTML = ajaxZone.childNodes[0].nodeValue;
                }
            }
            // To do: Apply focus to replaced HTML?
            //document.getElementById(focusId).focus();
        },
        mimetype: "application/x-www-form-urlencoded",
    });


    return false;
}

/**
 * @return an Array of statements guaranteed not to contain a form submit.
 */ 

function pruneSubmitFromEventHandler(event, handlerName) {
    var originalHandler;
    var target = event.originalTarget;
    var allHandlerStatements = null;
    var prunedHandlerStatements = null;
    var i = 0, j = 0;
    var copyHandlerName = g_eventPrefix + handlerName;
    
    if (null != target) {
	if (target.hasAttributes() &&
	    null != (originalHandler = target.getAttribute(copyHandlerName))) {
	    // remove the .submit() statement, if present;
	    allHandlerStatements = originalHandler.split(";");
	    if (0 < allHandlerStatements.length) {
		prunedHandlerStatements = new Array();
		for (i = 0; i < allHandlerStatements.length; i++) {
		    // If the current statement does not contain the submit...
		    if (-1 == allHandlerStatements[i].search(".*submit[ ]*\([ ]*\)")) {
			// copy it to the prunedHandlerStatements.
			prunedHandlerStatements[j++] = allHandlerStatements[i];
		    }
		}
	    }
	}
	
    }
    return prunedHandlerStatements;
}

/**
 * @return an associative array.  key params is the request params 
 * as an associative array.  key
 * nonAssignmentStatements is an array of statements that are not mere
 * assignment statements.
 */

function extractFormParamsFromPrunedHandlerStatements(prunedHandlerStatements, params){
    var result = new Array();
    var nonAssignmentStatements = null;
    var expI = 0, i = 0, j = 0;
    var pattern = null;
    var curStatement = null;
    var name = null, value = null;
    // Investigate the statements to see if they are simple value assignments
    // or not.
    for (i = 0; i < prunedHandlerStatements.length; i++) {
	// Hack: assume we're using the [''] syntax.  A more general
	// solution would discover this dynamically.
	if (-1 != (expI = prunedHandlerStatements[i].lastIndexOf("[\'"))) {
	    curStatement = prunedHandlerStatements[i].substring(expI + 2);
	    name = null;
	    value = null;
	    // Extract the parameter name.
	    if (-1 != (expI = curStatement.indexOf("\']"))) {
		name = curStatement.substring(0, expI);
		// Extract the parameter value
		if (-1 != (expI = 
			   prunedHandlerStatements[i].lastIndexOf("\.value"))){
		    if (-1 != (expI = 
			       prunedHandlerStatements[i].indexOf("=", 
								  expI))) {
			value = prunedHandlerStatements[i].substring(expI + 1);
			// strip off the leading and trailing ' if necessary
			if (null != value &&
			    "\'" == value.charAt(0)) {
			    if ("\'" == value.charAt(value.length - 1)) {
				value = value.substring(1, value.length - 1);
			    }
			}
			    
		    }
		}
		if (null != name && null != value) {
		    if (null != params) {
                        params[name] = value;
		    }
		}
	    }
	}
	else {
	    if (null == nonAssignmentStatements) {
		nonAssignmentStatements = new Array();
	    }
	    nonAssignmentStatements[j++] = prunedHandlerStatements[i];
	}
    }
    result.params = params;
    result.nonAssignmentStatements = nonAssignmentStatements;
    
    return result;
}

function prepareRequest(extraParams) {
    var stateFieldName = "javax.faces.ViewState";
    var stateElements = window.document.getElementsByName(stateFieldName);
    // In the case of a page with multiple h:form tags, there will be
    // multiple instances of stateFieldName in the page.  Even so, they
    // all have the same value, so it's safe to use the 0th value.
    var stateValue = stateElements[0].value;
    // We must carefully encode the value of the state array to ensure
    // it is accurately transmitted to the server.  The implementation
    // of encodeURI() in mozilla doesn't properly encode the plus
    // character as %2B so we have to do this as an extra step.
    var uriEncodedState = encodeURI(stateValue);
    var rexp = new RegExp("\\+", "g");
    var encodedState = uriEncodedState.replace(rexp, "\%2B");
    // A truly robust implementation would discern the form number in
    // which the element named by "clientId" exists, and use that as the
    // index into the forms[] array.
    var formName = window.document.forms[0].id;
    // build up the post data
    extraParams.stateFieldName = encodedState;
    extraParams.formName = formName;
}

function getXMLHttpRequest() {
  if (!g_request) {
    if (window.XMLHttpRequest) {
      g_request = new XMLHttpRequest();
    } else if (window.ActiveXObject) {
      isIE = true;
      g_request = new ActiveXObject("Microsoft.XMLHTTP");
    }
  }
  return (g_request); 
}
