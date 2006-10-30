
var _globalScope = this;

if (typeof jmaki == 'undefined') {
    var jmaki = new Jmaki();
}

function Jmaki() {
    var _this = this;
    var libraries = [];
    var widgets = [];
    this.loaded = false;
    
    this.resourceRoot = "/resources";
    this.attributes = new Map();
	
	var topics = new Map();

	/**
	*  Subscribe to a new topic
        * @param name Name of the topic to subscribe to
        * @param listener Callback listener
	*/
	this.subscribe = function(name, listener) {
		var topic = topics.get(name);
		// create the topic if it has not been created yet
		if (!topic) {
			topic = [];
			topics.put(name, topic);
		}
		// make sure that a listener is only added once
		for (var i in topic) {
			if (i == listener) {
				return;
			}
		}
		topic.push(listener);
	}
	
  	/**
	*  Unsubscribe a listener from a topic
        *  @param name Name of the topic 
        *  @param listener 
	*/
	this.unsubscribe = function(name, listener) {
		var topic = topics.get(name);
		// create the topic if it has not been created yet
		if (topic) {
			for (var i = 0; i < topic.length; i++) {
				if (topic[i] == listener) {
					topic.splice(i,1);
					break;
				}
			}
		}
	}  
	
       /**
	*  Publish an event to a topic
        *  @param name Name of the topic
        *  @param args ??
	*/
	this.publish = function(name, args) {
		if (typeof name == 'undefined' || typeof args == 'undefined') return;
		var topic = topics.get(name);
		// create the topic if it does not exist
		if (!topic) {
			topic = [];
			topics.put(name, topic);
		}
		// notify the listeners
		for (var index in topic) {
			topic[index](args);
		}
	}
       /**
	*  Library name is added as a script element which will be loaded when the page is rendered
        *  @param lib library to add 
	*/
				         
    this.addLibrary = function(lib) {
        var added = false;
        for (var l=0; l < libraries.length; l++) {
            if (libraries[l] == lib) {
                added = true;
            }
        }
        if (!added) {
            libraries.push(lib);
            var head = document.getElementsByTagName("head")[0];
            var scriptElement = document.createElement("script");
            scriptElement.type="text/javascript";
            _globalScope.setTimeout(function() {scriptElement.src = lib;},0);
            head.appendChild(scriptElement);
        }
    }
    /**
     * Register widget with jMaki 
     * @param widget Object respresenting the widget
     */
    this.addWidget = function(widget) {
        widgets.push(widget);
    }
    
    /**
     * Bootstrap or load all registered widgets
     */
    this.bootstrapWidgets = function() { 
        for (var l=0; l < widgets.length; l++) {
            this.loadWidget(widgets[l]);
        }
        this.loaded = true;
    }
    
    /**
     * Serialize a JavaScript object to a string. Functions are not serialized
     * @param obj is the object to serialize.
     */
    this.serialize = function(obj) {
        var props = [];
        for (var l in obj) {
            var prop;
            if (typeof obj[l] == "object") { 
                prop = this.serialize(obj[l]);
            } else if (typeof obj[l] == "function") {
                // do nothing for now
            } else {
                prop = "'" + obj[l] + "'";
            }
            props.push(l + ": " + prop);
        }
        return "{" + props.concat() + "}";
    }
    
    /**
     *  Get the XMLHttpRequest object
     *
     */
    function getXHR () {
        if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            return new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    
    /**
    * Generalized XMLHttpRequest which can be used from evaluated code. Evaluated code is not allowed to make calls.
    * @param args is an object literal containing configuration parameters including method[get| post, get is default], body[bodycontent for a post], asynchronous[true is default]
    */
    
    this.doAjax= function(args) {
        if (typeof args == 'undefined') return;
       var _req = getXHR();
       var method = "GET";

       var async = true;
       var callback;
       if  (typeof args.asynchronous != 'undefined') {
            async=args.asynchronous;
       }
       if  (typeof args.method != 'undefined') {
            method=args.method;
       }
       
       if  (typeof args.callback != 'undefined' &&
            typeof args.callback == 'function') {
           callback = args.callback;
       }
       var body = null;
       if (typeof args.content != 'undefined') {
           body = "";
           for (var l in args.content) {
               if (typeof args.content[l] == "string") { 
                   body = body +  l + "=" + encodeURIComponent(args.content[l]) + "&";
               }
           }
        }
       if (async == true) _req.onreadystatechange = function() {callback(_req);};
       _req.open(method, args.url, async);
       if (typeof args.method != 'undefined') {
            method=args.method;
            if (method.toLowerCase() == 'post') {
                _req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            }
       }
       
       _req.send(body);
       if (!async && callback) callback(_req);
    }
    
    // to do keep tabs for multiple declarations
    /**
     * Load the script name provided 
     * @param target name of script from the web root to load 
     */
    this.loadScript = function(target) {
        if (!/http:/.test(target)) target = this.webRoot + target;
        var req = getXHR();
        req.open("GET", target, false);
        try {
            req.send(null);
        } catch (e){
            // log error
        }
        if (req.status == 200) {
             return window.eval(req.responseText);
        }
    }
    
    /**
     * Loads the style sheet by adding a link element to the DOM 
     * @param target name of style sheet to load 
     */
    this.loadStyle = function(target) {
        var styleElement = document.createElement("link");
        styleElement.type = "text/css";
        styleElement.rel="stylesheet"
        styleElement.href = this.webRoot + target;
        if (document.getElementsByTagName('head').length == 0) {
            var headN = document.createElement("head");
            document.documentElement.insertBefore(headN, document.documentElement.firstChild);
        }
        document.getElementsByTagName('head')[0].appendChild(styleElement);
    }
    /**
     * Replace style class
     * @param root root of the oldStyle classes
     * @param oldStyle name of class or classes to replace
     * @param targetStyle name of new class or classes to use 
     */
    
    this.replaceStyleClass = function (root, oldStyle, targetStyle) {
        var elements = this.getElementsByStyle(oldStyle,root);
        for (var i=0; i < elements.length; i++) {
			// Handle cases where there are multiple classnames
            if (elements[i].className.indexOf(' ') != -1) {
                var classNames = elements[i].className.split(' ');
                for (var ci in classNames) {
                    if (classNames[ci] == oldStyle) {
                        classNames[ci] = targetStyle;
                    }
                }
                // now reset the styles with the replaced values
                elements[i].className = classNames.join(' ');
            } else  if (elements[i].className == oldStyle) {
                elements[i].className = targetStyle;
            }
        }
    }
    
    /**
    * Find a set of child nodes that contain the className specified
    * @param className is the targetClassName you are looking for
    * @param root  An optional root node to start searching from. The entire document will be searched if not specfied.
    *
    */
	this.getElementsByStyle = function(className, root){
        var elements = [];
        if (typeof root != 'undefined') {
            var rootNode = root;
            if (typeof root == 'string') {
                rootNode = document.getElementById(root);
            }    
            elements = this.getAllChildren(rootNode, []);
        } else {
            elements = (document.all) ? document.all : document.getElementsByTagName("*");
        }
		var found = [];
		for (var i=0; i < elements.length; i++) {
			// Handle cases where there are multiple classnames
            if (elements[i].className.indexOf(' ') != -1) {
                var classNames = elements[i].className.split(' ');
                for (var ci in classNames) {
                    if (classNames[ci] == className) {
                        found.push(elements[i]);
                    }
                }
            } else  if (elements[i].className == className) {
                found.push(elements[i]);
            }
        }
		return found;
	}
    
    this.getAllChildren = function(target, children) {
        var nChildren = target.childNodes;
        for (var l=0; l <  nChildren.length; l++) {
            if (nChildren[l].nodeType == 1) {
                children.push(nChildren[l]);
                if (nChildren[l].childNodes.length > 0) {
                    this.getAllChildren(nChildren[l], children);
                }
            }
        }
        return children;
    }
    /**
     * Load a widget
     * @param widget Object representing widget to load
     */
    this.loadWidget = function(_widget) {
        var req = getXHR();
        req.onreadystatechange = _processRequest;
        req.open("GET", _widget.script, true);
        req.send(null);

        function _processRequest () {
            if (req.readyState == 4) {
                // status of 200 signifies sucessful HTTP call
                if (req.status == 200) {
                    var script = "var widget=" + _this.serialize(_widget) + ";";
                    if (typeof _widget.onload != 'undefined') {
                        script +=  _widget.onload + "()"; 
                    }
                    script += req.responseText;
                    _globalScope.setTimeout(script,0);
                }
            }
        }
    }
    
    /**
     * Clear all registered widgets
     */
    this.clearWidgets = function() {
	jmaki.loaded = false;
        widgets = [];
    }
    
    function Map() {
    /**
     * This class provides a hash table like utility
     */
		var map = {};
        
    /**
     * Put stores the value in the table
     * @param key the index in the table where the value will be stored
     * @param value the value to be stored 
     */
        this.put = function(key,value) {
			map[key] = value;
		}
        
    /**
     * Return the value stored in the table
     * @param key the index of the value to retrieve
     */
        this.get = function(key) {
            return map[key];
        }
		
    /**
     * Remove the value from the table
     * @param key the index of the value to be removed
     */
	this.remove =  function(key) {
		delete map[key] ;
	}
    /**
     *  Clear the table
     */
        
        this.clear = function() {
			map = {};
        }
    }
}

var oldLoad  = window.onload;

/**
 * onload calls bootstrap function to initialize and load all registered widgets
 */
window.onload = function() {
    if (!jmaki.loaded) {
        jmaki.bootstrapWidgets();
    } else {
       return;
    }
    if (typeof oldLoad  == 'function') {
        oldLoad();
    }
}
