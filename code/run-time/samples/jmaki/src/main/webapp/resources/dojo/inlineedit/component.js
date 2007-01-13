dojo.require("dojo.widget.InlineEditBox");
dojo.require("dojo.event.*");

jmaki.namespace("jmaki.widgets.dojo.inlineedit");

jmaki.widgets.dojo.inlineedit.Widget = function(wargs) {

    var self = this;
    var topic = "/dojo/inlineedit/";
    var container = document.getElementById(wargs.uuid);
    self.wrapper = dojo.widget.createWidget("InlineEditBox",null, container);

    if (wargs.args) {
        if (wargs.args.topic) {
            topic = wargs.args.topic;
        }
    }

    this.getValue = function() {
        return self.wrapper.value;
    }

    // add a saveState function
    if (wargs.service) {
        self.wrapper.onSave = function(newValue, oldValue) {   
            // we need to be able to adjust this
            var url = wargs.service;
            dojo.io.bind({
                    url: url + "?cmd=update",
                    method: "post",
                content: { "value" : newValue  },
                load: function (type,data,evt) {
                    // do something if there is an error
                }
            });
        }
    } else {
        self.wrapper.onSave = function(newValue, oldValue) {
            jmaki.publish(topic + "onSave", {id: wargs.uuid, wargs: wargs, value: self.getValue()});
        }
    }
    this.saveState = this.wrapper.onSave;
}