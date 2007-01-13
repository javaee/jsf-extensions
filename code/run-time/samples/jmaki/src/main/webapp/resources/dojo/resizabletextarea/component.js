dojo.require("dojo.widget.ResizableTextarea");
dojo.require("dojo.event.*");

// create the namespaces
jmaki.namespace("jmaki.widgets.dojo.resizabletextarea");

jmaki.widgets.dojo.resizabletextarea.Widget = function(wargs) {
    var self = this;
    var topic = "/dojo/resizabletextarea/";
    var container = document.getElementById(wargs.uuid);
    this.wrapper = dojo.widget.createWidget("ResizableTextarea",null, container);

    if (wargs.args) {
        if (wargs.args.topic) {
            topic = wargs.args.topic;
        }
    }

    this.getValue = function() {
        return this.wrapper.textAreaNode.value;
    }

    // add a saveState function
    if (wargs.service) {
        this.saveState = function(newValue, oldValue) {   
            // we need to be able to adjust this
            var url = wargs.service;
            dojo.io.bind({
                    url: url + "?cmd=update",
                    method: "post",
                    content: { "value" : self.getValue()  },
                    load: function (type,data,evt) {
                    // do something if there is an error
                }
            });
        }
    } else {
        self.saveState = function() {
           jmaki.publish(topic + "onSave", {id: wargs.uuid, wargs: wargs, value: self.getValue()});
        }
    }
    
}