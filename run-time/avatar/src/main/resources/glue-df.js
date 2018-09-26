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

/* Programatically Register the glue */
jmaki.addGlueListener({topic : "/dojo/fisheye",action: "call",  
target: { object: "jmaki.dynamicfaces",functionName:  
"fishEyeValueUpdate"}});

jmaki.addGlueListener({topic : "/scriptaculous/inplace/valueUpdate",
                       action: "call",  
                       target: { object: "jmaki.dynamicfaces",functionName:  
                       "inplaceValueUpdate"}});

jmaki.addGlueListener({topic : "/scriptaculous/inplace/response",
                       action: "call",  
                       target: { object: "jmaki.dynamicfaces",functionName:  
                       "inplaceResponse"}});
 
/*
* Define an object to hang the glue listeners off of
*/
jmaki.dynamicfaces = {
    fishEyeValueUpdate : function fishEyeValueUpdate(args) {
	// args come in as {target: dojoIcon, wargs: widgetArguments}
	var _wargs = args.wargs;
	var _item = args.target;
	// If dojo removed the div declared in component.htm
	// from the DOM.
	var hidden = null;
	if (_wargs.form) {
	    // create an inputHidden with the proper value.
	    hidden = document.createElement("input");
	    hidden.type = "hidden";
	    hidden.id = _wargs.uuid;
	    hidden.value = _item.index;
	    _wargs.form.appendChild(hidden);
	}
	DynaFaces.fireAjaxTransaction(hidden,
				      {
				      render: _wargs.uuid,
					      inputs: _wargs.uuid		
					      });
    },
    inplaceValueUpdate: function inplaceValueUpdate(args) { 
	var _wargs = args.wargs;
	var _inplace = args.target.wrapper;
        var _value = _inplace.value;
	_inplace.options.ajaxOptions.requestHeaders = 
	  _inplace.options.ajaxOptions.requestHeaders || [];
	_inplace.options.ajaxOptions.requestHeaders.push(DynaFaces.gPartial);
	_inplace.options.ajaxOptions.requestHeaders.push(true);
	_inplace.options.ajaxOptions.requestHeaders.push(DynaFaces.gRender);
	_inplace.options.ajaxOptions.requestHeaders.push(_wargs.uuid);
	_inplace.options.ajaxOptions.requestHeaders.push(DynaFaces.gExecute);
	_inplace.options.ajaxOptions.requestHeaders.push(_wargs.uuid);
	
	var stateElements = window.document.getElementsByName(DynaFaces.gViewState);
	var stateValue = encodeURIComponent(stateElements[0].value);
	var formName = encodeURIComponent(form.id);
	var uuid = encodeURIComponent(_wargs.uuid);
	
	_wargs.outResult = uuid + "=" + _value + "&" + formName + "=" + formName + "&" + DynaFaces.gViewState + "=" + stateValue;
    },
    inplaceResponse: function inplaceResponse(args) {
	if (null == args.transport || null == args.element) {
	    return;
	}
	var xml = args.transport.responseXML;
	var components = xml.getElementsByTagName('components')[0];
	var render = components.getElementsByTagName('render');
	var str = render[0].firstChild.firstChild;
	var element = args.element;
	str = str.text || str.data;
	element.removeChild(element.firstChild);
	element.innerHTML = str;
	
	var state = state || xml.getElementsByTagName('state')[0].firstChild;
	if (state) {
	    var stateFields = 
		document.getElementsByName(DynaFaces.gViewState);
	    var i;
	    for (i = 0; i < stateFields.length; i++) {
		stateFields[i].value = state.text || state.data;
	    }
	}
	
    }
}
