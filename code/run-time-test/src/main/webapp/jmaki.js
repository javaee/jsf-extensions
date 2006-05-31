
var _globalScope = this;

if (typeof jmaki == 'undefined') {
    var jmaki = new Jmaki();
}

function Jmaki() {
    var _this = this;
    var libraries = [];
    var widgets = [];
    this.attributes = new Map();
           
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

    this.addWidget = function(widget) {
        widgets.push(widget);
    }

    this.clearWidgets = function() {
	widgets = [];
    }
    
    this.bootstrapWidgets = function() { 
        for (var l=0; l < widgets.length; l++) {
            this.loadWidget(widgets[l]);
        }
    }
    
    this.serialize = function(obj) {
        var props = [];
        for (var l in obj) {
            var prop;
            if (typeof obj[l] == "object") { 
                prop = this.serialize(obj[l]);
            } else {
                prop = "'" + obj[l] + "'";
            }
            props.push(l + ": " + prop);
        }
        return "{" + props.concat() + "}";
    }
    
    function getXHR () {
        if (window.XMLHttpRequest) {
            return new XMLHttpRequest();
        } else if (window.ActiveXObject) {
            return new ActiveXObject("Microsoft.XMLHTTP");
        }
    }
    this.doAjax= function(args) {
        if (typeof args == 'undefined') return;
       var _req = getXHR();
       var method = "GET";
       if (typeof args.method != 'undefined') {
            method=args.method;
            if (method.toLowerCase() == 'post') {
                _req.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
            }
       }
       var async = true;
       var callback;
       if  (typeof args.asynchronous != 'undefined') {
            async=args.asynchronous;
       }
       if  (typeof args.callback != 'undefined' &&
            typeof args.callback == 'function') {
           callback = args.callback;
       }
       var body = null;
       if (typeof args.body != 'undefined') {
            async=args.body;
       }
       if (async == true) _req.onreadystatechange = function() {callback(_req);};
       _req.open(method, args.url, async); 
       
       _req.send(body);
       if (!async && callback) callback(_req);
    }
    
    // to do keep tabs for multiple declarations
    this.loadScript = function(target) {
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
    
    this.loadWidget = function(widget) {
        var req = getXHR();
        req.onreadystatechange = processRequest;
        req.open("GET", widget.script, true);
        req.send(null);

        function processRequest () {
            if (req.readyState == 4) {
                // status of 200 signifies sucessful HTTP call
                if (req.status == 200) {
                    var script = "var widget=" + _this.serialize(widget) + ";";
                    if (typeof widget.onload != 'undefined') {
                        script +=  widget.onload + "()"; 
                    }
                    script += req.responseText;
                    _globalScope.setTimeout(script,0);
                }
            }
        }
    }
    
    function Map() {
        var size = 0;
        var keys = [];
        var values = [];
        
        this.put = function(key,value) {
            if (this.get(key) == null) {
                keys[size] = key; values[size] = value;
                size++;
            } else {
                for (i=0; i < size; i++) {
                    if (keys[i] == key) {
                        values[i] = value;
                    }
                }
            }
        }
        
        this.get = function(key) {
            for (i=0; i < size; i++) {
                if (keys[i] == key) {
                    return values[i];
                }
            }
            return null;
        }
        
        this.clear = function() {
            size = 0;
            keys = [];
            values = [];
        }
        
        // TODO: Need a remove
    }
}
   
var oldLoad = window.onload;

window.onload = function() {
    jmaki.bootstrapWidgets();
    if (typeof oldLoad  == 'function') {
        oldLoad();
    }
}

