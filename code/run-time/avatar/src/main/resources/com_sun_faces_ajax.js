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

var gFacesPrefix = "com.sun.faces.avatar.";
var gPartial = gFacesPrefix + "Partial";
var gExecute = gFacesPrefix + "Execute";
var gRender = gFacesPrefix + "Render";
var gViewRoot = gFacesPrefix + "ViewRoot";
var gFacesEvent = gFacesPrefix + "FacesEvent";
var gMethodName = gFacesPrefix + "MethodName";
var gViewState = "javax.faces.ViewState";
var gGlobalScope = this;

var gSpecialChars = {
            '\b': '\\b',
            '\t': '\\t',
            '\n': '\\n',
            '\f': '\\f',
            '\r': '\\r',
            '"' : '\\"',
            '\\': '\\\\'
};

var gJSON = {
    array: function (x) {
	var a = ['['], b, f, i, l = x.length, v;
	for (i = 0; i < l; i += 1) {
	    v = x[i];
	    f = gJSON[typeof v];
	    if (f) {
		v = f(v);
		if (typeof v == 'string') {
		    if (b) {
			a[a.length] = ',';
		    }
		    a[a.length] = v;
		    b = true;
		}
	    }
	}
	a[a.length] = ']';
	return a.join('');
    },
    'boolean': function (x) {
	return String(x);
    },
    'null': function (x) {
	return "null";
    },
    number: function (x) {
	return isFinite(x) ? String(x) : 'null';
    },
    object: function (x) {
	if (x) {
	    if (x instanceof Array) {
		return gJSON.array(x);
	    }
	    var a = ['{'], b, f, i, v;
	    for (i in x) {
		v = x[i];
		f = gJSON[typeof v];
		if (f) {
		    v = f(v);
		    if (typeof v == 'string') {
			if (b) {
			    a[a.length] = ',';
			}
			a.push(gJSON.string(i), ':', v);
			b = true;
                            }
                        }
	    }
	    a[a.length] = '}';
	    return a.join('');
	}
	return 'null';
    },
    string: function (x) {
	if (/[\"\\\x00-\x1f]/.test(x)) {
	    x = x.replace(/([\x00-\x1f\\\"])/g, function(a, b) {
			      var c = gSpecialChars[b];
			      if (c) {
				  return c;
			      }
			      c = b.charCodeAt();
			      return '\\u00' +
				  Math.floor(c / 16).toString(16) +
				  (c % 16).toString(16);
			  });
	}
	return '"' + x + '"';
    }
};



/* Object Extensions
 ***********************************************************/
Object.extend(String.prototype, {
	trim: function() {
		var s = this.replace( /^\s+/g, "" );
  		return s.replace( /\s+$/g, "" );
	}
});
Object.extend(Element, {
    elementReplace: function elementReplace(d, tempTagName, src) {
    var parent = d.parentNode;
    var temp = document.createElement(tempTagName);
    var result = null;
    temp.id = d.id;

    // If we are creating a head element...
    if (-1 != d.tagName.indexOf("head") && d.tagName.length == 4) {
	// Strip link elements from src.
	if (-1 != src.indexOf("link")) {
	    var 
		linkStartEx = new RegExp("< *link.*>", "gi");
	    var linkStart;
	    while (null != (linkStart = linkStartEx.exec(src))) {
		src = src.substring(0, linkStart.index) +
		    src.substring(linkStartEx.lastIndex);
		linkStartEx.lastIndex = 0;
	    }
	}

	// Strip style elements from src
	if (-1 != src.indexOf("style")) {
	    var 
		styleStartEx = new RegExp("< *style.*>", "gi"),
		styleEndEx = new RegExp("< */ *style.*>", "gi");
	    var styleStart, styleEnd;
	    while (null != (styleStart = styleStartEx.exec(src))) {
		styleEnd = styleEndEx.exec(src);
		src = src.substring(0, styleStart.index) +
		    src.substring(styleStartEx.lastIndex);
		styleStartEx.lastIndex = 0;
	    }
	}

	temp.innerHTML = src;

	// clone all the link elements...
	var i, links, styles;
	links = d.getElementsByTagName("link");
	if (links) {
	    for (i = 0; i < links.length; i++) {
		temp.appendChild(links[i].cloneNode(true));
	    }
	}
	// then clone all the style elements.
	styles = d.getElementsByTagName("style");
	if (styles) {
	    for (i = 0; i < styles.length; i++) {
		temp.appendChild(styles[i].cloneNode(true));
	    }
	}
    }
    else {
	temp.innerHTML = src;
    }
	    
    
    result = temp
    parent.replaceChild(temp, d);
    return result;
  },
  replace: function(dest, src)  {

    // If this partial response is the entire view...
    if (-1 != dest.indexOf(gViewRoot)) {
	// if src contains <html>, trim the <html> and </html>, if present.
	//   if src contains <head>
	//      extract the contents of <head> and replace current document's
	//      <head> with the contents.
	//   if src contains <body> 
	//      extract the contents of <body> and replace the current
	//      document's <body> with the contents.
	//   if src does not contain <body>
	//      replace the current document's <body> with the contents.

        var 
	    htmlStartEx = new RegExp("< *html.*>", "gi"),
	    htmlEndEx = new RegExp("< */ *html.*>", "gi"),
	    headStartEx = new RegExp("< *head.*>", "gi"),
	    headEndEx = new RegExp("< */ *head.*>", "gi"),
	    bodyStartEx = new RegExp("< *body.*>", "gi"),
	    bodyEndEx = new RegExp("< */ *body.*>", "gi"),
	    htmlStart, htmlEnd, headStart, headEnd, bodyStart, bodyEnd;
	var srcHead = null, srcBody = null;
	// find the current document's "body" element
	var docBody = document.getElementsByTagName("body")[0];
	// if src contains <html>
	if (null != (htmlStart = htmlStartEx.exec(src))) {
	    // if src contains </html>
	    if (null != (htmlEnd = htmlEndEx.exec(src))) {
		src = src.substring(htmlStartEx.lastIndex,
				     htmlEnd.index);
	    }
	    else {
		src = src.substring(htmlStartEx.lastIndex);
	    }
	}

	// if src contains <head>
	if (null != (headStart = headStartEx.exec(src))) {
	    // if src contains </head>
	    if (null != (headEnd = headEndEx.exec(src))) {
		srcHead = src.substring(headStartEx.lastIndex,
					headEnd.index);
	    }
	    else {
		srcHead = src.substring(headStartEx.lastIndex);
	    }
	    // find the "head" element
	    var docHead = document.getElementsByTagName("head")[0];
	    if (docHead) {
		this.elementReplace(docHead, "head", srcHead);
	    }
	}
	// if src contains <body>
	if (null != (bodyStart = bodyStartEx.exec(src))) {
	    // if src contains </body>
	    if (null != (bodyEnd = bodyEndEx.exec(src))) {
		srcBody = src.substring(bodyStartEx.lastIndex,
				     bodyEnd.index);
	    }
	    else {
		srcBody = src.substring(bodyStartEx.lastIndex);
	    }
	    result = this.elementReplace(docBody, "body", srcBody);
	}
	if (!srcBody) {
	    result = this.elementReplace(docBody, "body", src);
	}
	
    }
    else {
	var d = $(dest);
	if (!d) { 
	    alert(dest + " not found");
	}
	var parent = d.parentNode;
	var temp = document.createElement('div');
	var result = null;
	temp.id = d.id;
	temp.innerHTML = src;

	result = temp.firstChild;
	parent.replaceChild(temp.firstChild,d);
    }
    return result;
    
  },
  serialize: function(e) {
	  var str = (e.xml) ? this.serializeIE(e) : this.serializeMozilla(e);
	  return str;
  },
  serializeIE: function(e) {
	  return e.xml;
  },
  serializeMozilla: function(e) {
	  return new XMLSerializer().serializeToString(e);
  },
  firstElement: function(e) {
	  var first = e.firstChild;
	  while (first && first.nodeType != 1) {
		  first = first.nextSibling;
	  }
	  return first;
  }
});
function $Error(e) {
	if (e.message) {
		return '['+e.lineNumber+'] ' + e.message;
	} else {
		return e.toString();
	}
};
function $Form(src) {
	if (src) {
		var form = $(src);
		while (form && form.tagName && form.tagName.toLowerCase() != 'form') {
			if (form.form) return form.form;
			if (form.parentNode) {
				form = form.parentNode;
			} else {
				form = null;
			}
		}
		if (form) return form;
	}
	return document.forms[0];
};

/* Facelet Utility Class
 ***********************************************************/
var Faces = {
	initialized: false,
	create: function() {
		return function() {
	  		if (Faces.initialized) {
      			this.initialize.apply(this, arguments);
	  		} else {
				var args = arguments;
				var me = this;
	  			Event.observe(window,'load',function() {
					Faces.initialized = true;
					me.initialize.apply(me, args);
				},false);
			}
		}
	},
	toArray: function(s,e) {
		if (typeof s == 'string') {
			return s.split((e)?e:' ').map(function(p) { return p.trim(); });
		}
		return s;
	},
	toString: function() {
		return "Faces Agent v. 1.0";
	}
};

/* ViewState Hash over a given Form
 ***********************************************************/
Faces.ViewState = Class.create();
Faces.ViewState.CommandType = ['button','submit','reset'];
// concat?
Faces.ViewState.Ignore = ['button','submit','reset','image'];
Faces.ViewState.prototype = {
    setOptions: function(options) {
	this.options= {};
	Object.extend(this.options, options || {});
    },
    initialize: function(form, options) {
	this.setOptions(options);

	// Skip the traversal if the user elected to have more control over
	// the post data.
	var collectPostDataType = typeof this.options.collectPostData;
	var inputsType = typeof this.options.inputs;
	if (('void' != collectPostDataType && 'undefined' != collectPostDataType) ||
	    ('void' != inputsType && 'undefined' != collectPostDataType)) {
	    // Just get the state data.
	    var viewState = $(gViewState);
	    t = viewState.tagName.toLowerCase();
	    p = Form.Element.Serializers[t](viewState);
	    if (p && p[0].length != 0) {
		if (p[1].constructor != Array) { 
		    p[1] = [p[1]];
		}
		if (this[p[0]]) { 
		    this[p[0]] = this[p[0]].concat(p[1]); 
		}
		else { 
		    this[p[0]] = p[1];
		}
	    }
	    
	    return;
	}
	var e = Form.getElements($(form));
	var t,p;
	// Traverse the elements of the form and slam all of them into this 
	// element's property set.
	for (var i = 0; i < e.length; i++) {
	    if (Faces.ViewState.Ignore.indexOf(e[i].type) == -1) {
		t = e[i].tagName.toLowerCase();
		p = Form.Element.Serializers[t](e[i]);
		if (p && p[0].length != 0) {
		    if (p[1].constructor != Array) {
			p[1] = [p[1]];
		    }
		    // Don't concatenate the viewState.
		    if (this[p[0]] && -1 == gViewState.indexOf(p[0])) { 
			this[p[0]] = this[p[0]].concat(p[1]); 
		    }
		    else {
			this[p[0]] = p[1];
		    }
		}
	    }
	};
	// For good measure,
	var source = this.options.source;
	// add source
	var action = $(source);
	if (action && action.form) {
	    this[action.name] = action.value || 'x';
	}
	else {
	    this[source] = source;
	}
	
    },
    toQueryString: function() {
	var q = new Array();
	var i,j,p,v;

	if (this.options.inputs) {
	    if (this[gViewState]) {
		p = encodeURIComponent(gViewState);
		v = encodeURIComponent(this[gViewState]);
		q.push(p+'='+v);
	    }
	    var inputs = this.options.inputs.split(",");
	    if (inputs) {
		for (i = 0; i < inputs.length; i++) {
		    if (this[inputs[i]]) {
			p = encodeURIComponent(inputs[i]);
			if (this[inputs[i]].constructor == Array) {
			    for (j = 0; j < this[inputs[i]].length; j++){
				v = this[inputs[i]][j];
				if (null != v) {
				    v = encodeURIComponent(v);
				    q.push(p+'='+v);
				}
			    }
			} else {
			    v = encodeURIComponent(this[inputs[i]]);
			    q.push(p+'='+v);
			}
		    }
		}
	    }
	}
	else {
	    for (property in this) {
		if (this[property]) {
		    if (typeof this[property] == 'function') continue;
		    p = encodeURIComponent(property);
		    if (this[property].constructor == Array) {
			for (j = 0; j < this[property].length; j++) {
			    v = this[property][j];
			    if (null != v) {
				v = encodeURIComponent(v);
				q.push(p+'='+v);
			    }
			}
		    } else {
			v = encodeURIComponent(this[property]);
			q.push(p+'='+v);
		    }
		}
	    }
	}
	if (this.options.parameters) {
	    q.push(this.options.parameters);
	}
	if (typeof this.options.collectPostData == 'function') {
	    this.options.collectPostData(this.options.ajaxZone, this.options.source,
				   q);
	}
	else if (typeof gGlobalScope[this.options.collectPostData] == 'function') {
	    gGlobalScope[this.options.collectPostData](this.options.ajaxZone, 
						 this.options.source, q);
	}

	if (this.options.action) {
	    p = (this.options.ajaxZone) ? this.options.ajaxZone.id :
	      this.options.source.id || this.options.source.name;
	    q.push(encodeURIComponent(p)+'='+
		   encodeURIComponent(this.options.action));
	}
	    
	return q.join('&');
    }
};

/* Handles Sending Events to the Server
 ***********************************************************/
Faces.Event = Class.create();
Object.extend(Object.extend(Faces.Event.prototype, Ajax.Request.prototype), {
  initialize: function(source, options) {
    this.transport = Ajax.getTransport();
    this.setOptions(options);
	
	// make sure we are posting
	this.options.method = 'post';
	
	// get form
	this.form = $Form(source);
	this.url = this.form.action;
	
	// create viewState
	var viewState = null;
	var stateOptions = new Object();

	// copy over the options that should get passed through
	Object.extend(stateOptions, this.options);
	stateOptions.source = source;
	// remove the ones that should not
	stateOptions.render = null;
	stateOptions.execute = null;
	stateOptions.asynchronous = null;
	stateOptions.contentType = null;
	stateOptions.method = null;

	viewState = new Faces.ViewState(this.form, stateOptions);
	
	// add passed parameters
	viewState.options.parameters = this.options.parameters;
	
	// initialize headers
	this.options.requestHeaders = this.options.requestHeaders || [];
	
	// guarantee our header
	this.options.requestHeaders.push(gPartial);
	if (this.options.immediate) {
	    this.options.requestHeaders.push('immediate');
	}
	else {
	    this.options.requestHeaders.push('true');
	}
	
	// add methodName
	if (this.options.methodName) {
	    var sourceId = $(source).id || $(source).name;
		sourceId += "," + this.options.methodName;
		if (this.options.phaseId) {
			sourceId += "," + this.options.phaseId;
		}
		this.options.requestHeaders.push(gMethodName);
		this.options.requestHeaders.push(sourceId);
	}

	if (this.options.execute) {
		this.options.requestHeaders.push(gExecute);
		this.options.requestHeaders.push(Faces.toArray(this.options.execute,',').join(','));
	}
	
	if (this.options.render) {
		this.options.requestHeaders.push(gRender);
		this.options.requestHeaders.push(Faces.toArray(this.options.render,',').join(','));
	}

	if (this.options.xjson) {
	    var xjson = gJSON.object(this.options.xjson);
	    this.options.requestHeaders.push("X-JSON");
	    this.options.requestHeaders.push(xjson);
	}
	
	if (typeof DynaFaces != 'undefined') {
	    if (0 < DynaFaces._eventQueue.length) {
		this.options.requestHeaders.push(gFacesEvent);
		var arr = new Array();
		for (i = 0; i < DynaFaces._eventQueue.length; i++) {
		    arr.push(DynaFaces._eventQueue[i].toString());
		}
		var events = arr.join(';');
		this.options.requestHeaders.push(events);

		// Clear out the queue
		DynaFaces._eventQueue = new Array();
	    }
	}
	
	this.options.postBody = viewState.toQueryString();

    var onComplete = this.options.onComplete;
    this.options.onComplete = (function(transport, xjson) {
      this.renderView(xjson);
      if (onComplete) {
	  onComplete(transport, options);
      } else if (this.doEvalResponse) {
	  this.evalResponse();
      }
    }).bind(this);

	if (this.options.onException == null) {
		this.options.onException = this.onException;
	}
	
	// send request
    this.request(this.url);
  },
  renderView: function(xjson) {
     var xml = this.transport.responseXML;
     var id, content, markup, str;
     if (null == xml || typeof xml == 'undefined') {
	 // If the content contains javaScript, just execute it
	 markup = this.transport.responseText;
	 if (null != markup && typeof markup != 'undefined') {
	     markup.evalScripts();
	 }
	 return;
     }    
     var components = xml.getElementsByTagName('components')[0];
     var render = components.getElementsByTagName('render');
     for (var i = 0; i < render.length; i++) {
	 id = render[i].getAttribute('id');
	 content = render[i].firstChild.firstChild;
	 markup = content.text || content.data;
	 str = markup.stripScripts();
	 this.doEvalResponse = false;
	 // If the user passed a replaceElement...
	 if (this.options.replaceElement) {
	     var optionType = null;
	     // look at its type.
	     if ((optionType = typeof this.options.replaceElement) != 
		 'undefined') {
		 // If its type is already a function...
		 if (optionType == 'function') {
		     // invoke it.
		     this.options.replaceElement(id, markup, 
						     this.options.closure, 
						     xjson);
		 }
		 // Otherwise, if there is a globally scoped function
		 // named as the value of the replaceElement...
		 else if (typeof gGlobalScope[this.options.replaceElement] ==
			  'function') {
		     // invoke it.
		     gGlobalScope[this.options.replaceElement](id, markup,
								   this.options.closure, 
								   xjson);
		 }
	     }
	 }
	 else {
	     // Otherwise, just do our element replacement.
	     Element.replace(id, str);
	 }
	 
	 // If the user specified a postReplace...
	 if (this.options.postReplace) {
	     // look at its type.
	     if ((optionType == typeof this.options.postReplace) !=
		 'undefined') {
		 // If its type is already a function...
		 if (optionType == 'function') {
		     // invoke it.
		     this.options.postReplace($(id), markup, 
						  this.options.closure,
						  xjson);
		 }
		 // Otherwise, if there is a globally scoped function
		 // named as the value of the postReplace...
		 else if (typeof gGlobalScope[this.options.postReplace] ==
			  'function') {
		     // invoke it.
		     gGlobalScope[this.options.postReplace]($(id), markup,
								this.options.closure,
								xjson);
		 }
	     }
	 }
	 else {
	     // Otherwise, just evaluate the scripts.
	     markup.evalScripts();
	 }
     }

     // This should happen for each of the elements in the collection,
     // not just the zeroth.
     var state = state || xml.getElementsByTagName('state')[0].firstChild;
     if (state) {
	 var hf = $(gViewState);
	 if (hf) {
	     hf.value = state.text || state.data;
	 }
     }
  },
  evalResponse: function() {
	  if (this.responseIsSuccess()) {
	      var text = this.transport.responseText;
	      //alert(text);
	      if (text) {
		  try {
		      text.evalScripts();
		  } catch (e) {}
	      }
	  }
  },
  onException: function(o,e) {
	  throw e;
  }
});

/* Turn any Element into a Faces.DeferredEvent
 ***********************************************************/
Faces.DeferredEvent = Faces.create();
Faces.DeferredEvent.prototype = {
	initialize: function(action, event, options) {
		var event = (event) || 'click';
		var options = options;
		var facesObserver = function(e) {
			new Faces.Event(action,options);
			// if this element can cause a form submit...
			if (Faces.ViewState.CommandType.indexOf(action.type) != -1){
			    Event.stop(e);
			}
			return false;
		};
		// If the element had no existing facesObserver
		if (typeof action.facesObserver == 'undefined') {
		    // store one here so we can remove the observer later.
		    action.facesObserver = facesObserver;
		}
		else {
		    Event.stopObserving(action,event,action.facesObserver,true);
		}
		Event.observe(action,event,facesObserver,true);
	}
};

/* Take any Event and delegate it to an Observer
 ***********************************************************/
Faces.Observer = Faces.create();
Faces.Observer.prototype = {
	initialize: function(trigger,events,options) {
		this.options = {};
		Object.extend(this.options, options || {});
		var source = this.options.source;
		var fn = function(e) {
			new Faces.Event((source || Event.element(e)), options);
			Event.stop(e);
			return false;
		};
		var event = events || 'click';
		var ta = Faces.toArray(trigger);
		var ea = Faces.toArray(events);
		for (var i = 0; i < ta.length; i++) {
			for (var j = 0; j < ea.length; j++) {
				Event.observe($(ta[i]),ea[j],fn,true);
			}
		}
	}
};



if (typeof DynaFaces != 'undefined') {
    alert("DynaFaces already defined!"); 
}

var DynaFaces = new Object();

DynaFaces._eventQueue = new Array();

DynaFaces.fireAjaxTransaction = function(element, options) {
    new Faces.Event(element, options);
    return false;
}
    
DynaFaces.installDeferredAjaxTransaction = function(action, event, options) {
    new Faces.DeferredEvent(action, event, options);
    return false;
}

DynaFaces.PhaseId = {
    ANY_PHASE: "ANY_PHASE",
    RESTORE_VIEW: "RESTORE_VIEW",
    APPLY_REQUEST_VALUES: "APPLY_REQUEST_VALUES",
    PROCESS_VALIDATIONS: "PROCESS_VALIDATIONS",
    UPDATE_MODEL_VALUES: "UPDATE_MODEL_VALUES",
    INVOKE_APPLICATION: "INVOKE_APPLICATION",
    RENDER_RESPONSE: "RENDER_RESPONSE"
};

DynaFaces.PhaseId.values = new Array();
DynaFaces.PhaseId.values[0] = DynaFaces.PhaseId.ANY_PHASE;
DynaFaces.PhaseId.values[1] = DynaFaces.PhaseId.RESTORE_VIEW;
DynaFaces.PhaseId.values[2] = DynaFaces.PhaseId.APPLY_REQUEST_VALUES;
DynaFaces.PhaseId.values[3] = DynaFaces.PhaseId.PROCESS_VALIDATIONS;
DynaFaces.PhaseId.values[4] = DynaFaces.PhaseId.UPDATE_MODEL_VALUES;
DynaFaces.PhaseId.values[5] = DynaFaces.PhaseId.INVOKE_APPLICATION;
DynaFaces.PhaseId.values[6] = DynaFaces.PhaseId.RENDER_RESPONSE

DynaFaces.FacesEvent = function(eventId, clientId, phaseId) {
    this.eventId = eventId;
    this.clientId = clientId;
    this.phaseId = phaseId;
};

DynaFaces.FacesEvent.prototype.toString = function () {
    return this.eventId + ',' + this.clientId + ',' + this.phaseId + ",source";
};

DynaFaces.ValueChangeEvent = function(clientId, 
				      phaseId, oldValue, newValue) {
    this.base = DynaFaces.FacesEvent;
    this.base("ValueChangeEvent", clientId, phaseId);
    this.oldValue = oldValue;
    this.newValue = newValue;
};
DynaFaces.ValueChangeEvent.prototype = new DynaFaces.FacesEvent;
DynaFaces.ValueChangeEvent.prototype.toString = function() {
    return (this.eventId + ',' + this.clientId + ',' + this.phaseId +  
	    ",source," + this.oldValue + ',' + this.newValue);
};

DynaFaces.ActionEvent = function(clientId, phaseId) {
    this.base = DynaFaces.FacesEvent;
    this.base("ActionEvent", clientId, phaseId);
};
DynaFaces.ActionEvent.prototype = new DynaFaces.FacesEvent;


DynaFaces.queueFacesEvent = function (facesEvent) {
    DynaFaces._eventQueue.push(facesEvent);
}
