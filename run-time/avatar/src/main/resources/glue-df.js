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
