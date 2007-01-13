dojo.require("dojo.lfx.*");
dojo.require("dojo.lfx.extras");

// define the namespaces
jmaki.namespace("jmaki.widgets.dojo.drawer");

jmaki.widgets.dojo.drawer.Widget = function(wargs) {
    
    var self = this;
    var topic = "/dojo/drawer/";
    var open = false;
    var icon;
    var lazyLoad = false;
    var contentPane;
    var tooltip_open = "Open";
    var tooltip_close = "Close";
    var rate = 250;
    var contentLoad = false;
    var color = "gray";
    
    this.loadURL = function(url) {      
       jmaki.injector.inject({injectionPoint: contentPane, url : url});
       contentLoad = true;  
    }
        
    // pull in the arguments
    if (wargs.args) {
        if (wargs.args.open) {
            open = wargs.args.open;
        }
        if (wargs.args.lazyLoad) {
            lazyLoad = wargs.args.lazyLoad;
        }
        if (wargs.args.color) {
            color = wargs.args.color;
        }
        if (wargs.args.rate) {
            rate = wargs.args.rate;
        }
    }
    
    var container = document.getElementById(wargs.uuid);
    
    var label = document.createElement("div");
    label.style.height = "15px";
    
    function setIcon(src) {
        if (/MSIE/i.test(navigator.userAgent)) {
           icon.style.filter = "progid:DXImageTransform.Microsoft.AlphaImageLoader(enabled=true,sizingMethod=image,src=" + src + ")";
        } else {
            icon.src = src;
        }
    }
    
    if (/MSIE/i.test(navigator.userAgent)) {
        icon = document.createElement("div");
        icon.style.width = "15px";
        icon.style.height = "15px";
        setIcon(wargs.widgetDir + "/images/arrow_closed_" + color +".png");
    } else {
        icon = document.createElement("img");
    }
    
    label.appendChild(icon);
    label.appendChild(icon);
    icon.title = tooltip_open;
    
    icon.onclick = function() {
        if (open) {
            self.setOpen(false);
        } else {
            self.setOpen(true);
        }
    }
    
    contentPane = document.createElement("div");
    if (!open) {
        contentPane.style.display = "none";
        setIcon(wargs.widgetDir + "/images/arrow_closed_" + color +".png");        
        if (!lazyLoad && wargs.args.url) {
            self.loadURL(wargs.args.url);
        }        
    } else {
        setIcon(wargs.widgetDir + "/images/arrow_opened_" + color +".png");     
        if (wargs.args.url) {
            self.loadURL(wargs.args.url);
        }
    }
    
    
    // if body content was provided in the template use that content.
    if (container.firstChild) {
        contentPane.innerHTML = container.innerHTML;
        container.innerHTML = "";
        container.appendChild(label);
        container.appendChild(contentPane);
    } else {
        if (wargs.args && wargs.args.content) {
            container.appendChild(label);
            contentPane.innerHTML = wargs.args.content;
            container.appendChild(contentPane);
        } else if (wargs.args && wargs.args.url) {
            container.appendChild(label);
            container.appendChild(contentPane);
        } else {
           container.appendChild(label);     
        }
    }

    this.setContent = function(content) {
        contentPane.innerHTML = content;
    }
    
    this.getContent = function() {
        return contentPane.innerHTML;
    }
    
    function show() {
        if (lazyLoad && !contentLoad && wargs.args.url) {
            self.loadURL(wargs.args.url);
        }
        setIcon(wargs.widgetDir + "/images/arrow_opened_" + color +".png");     
        icon.title = tooltip_close;
        dojo.lfx.html.wipeIn(contentPane, rate).play();
        open = true;
        jmaki.publish(topic + "open", {wargs:wargs});
    }
    
    function hide() {
        dojo.lfx.html.wipeOut(contentPane, rate).play();
        setIcon(wargs.widgetDir + "/images/arrow_closed_" + color +".png");  
        icon.title = tooltip_open;
        open = false;
    }
    
    this.setOpen = function(_open) {
        if (_open) {
           show();
        } else {
           hide();
        }
    }
    
    jmaki.subscribe(topic + "setContent", this.setContent);
    jmaki.subscribe(topic + "setOpen", this.setOpen);
}