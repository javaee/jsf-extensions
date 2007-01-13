dojo.require("dojo.widget.Editor2Plugin.SimpleSignalCommands");
dojo.require("dojo.widget.Editor2");

//create namespace
jmaki.namespace("jmaki.widgets.dojo.editor");

jmaki.widgets.dojo.editor.Widget = function(wargs) {
    
    var self = this;
    var topic = "/dojo/editor/";
    var container = document.getElementById(wargs.uuid);

    var toolbarSmall = wargs.widgetDir + "/toolbars/toolbar-small.html";
    var toolbarMedium = wargs.widgetDir + "/toolbars/toolbar-medium.html";
    var toolbarFull = wargs.widgetDir + "/toolbars/toolbar-full.html";
    var toolbar = toolbarSmall;

    if (wargs.args) {
        if (wargs.args.topic) {
            topic = wargs.args.topic;
        }
        if (wargs.args.toolbar) {
            if (wargs.args.toolbar == "full") {
                toolbar = toolbarFull;
            }
        }
        if (wargs.args.toolbar) {
            if (wargs.args.toolbar == "medium") {
                toolbar = toolbarMedium;
            }
        }
        if (wargs.args.toolbar) {
            if (wargs.args.toolbar == "small") {
                toolbar = toolbarMedium;
            }
        }
    }
    var eargs = {toolbarTemplatePath: toolbar, shareToolbar: false};

    this.wrapper = dojo.widget.createWidget("Editor2", eargs, container);
    
    this.getValue = function() {
        return this.wrapper.getEditorContent();
    }
    
    this.saveState = function() {
        jmaki.publish(topic + "onSave", {id: wargs.uuid, wargs: wargs, value: self.getValue()});
    }
    
    // add a saveState function
    if (wargs.service) {
        self.saveState = function() {
            // we need to be able to adjust this
            var url = wargs.service;
            dojo.io.bind({
                url: url + "?cmd=update",
                method: "post",
                content: { "value" : self.getValue() },
                load: function (type,data,evt) {
                    // do something if there is an error
                }
            });
        }
    }
    dojo.event.connect(self.wrapper, "save", dojo.lang.hitch(this, function() {
				self.saveState();
    }));
}