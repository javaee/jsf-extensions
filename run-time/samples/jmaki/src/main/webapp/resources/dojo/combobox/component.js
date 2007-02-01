dojo.require("dojo.widget.ComboBox");
dojo.require("dojo.io.*");

// define the namespaces
if (!jmaki.widgets.dojo) {
	jmaki.widgets.dojo = {};
}
jmaki.widgets.dojo.combobox = {};

jmaki.widgets.dojo.combobox.Widget = function(wargs) {
    
    var _this = this;
	var container = document.getElementById(wargs.uuid);
	this.wrapper = dojo.widget.createWidget("ComboBox", {autocomplete:true}, container);
    if (wargs.service) {
        dojo.io.bind({
                url: wargs.service,
                method: "get",
                mimetype: "text/json",
                load: function (type,data,evt) {
	                _this.wrapper.dataProvider.setData(data);
            }
        });
       	
    this.saveState = function() {
	        // we need to be able to adjust this
	        var url = wargs.service;
	        dojo.io.bind({
	                url: url + "?cmd=setCountry",
	                method: "post",
	                content: { "value" : this.getValue()}
	                // we need an error handler
	        });
	    }
	}

	var topic = "/dojo/combobox";

	if (wargs.value) {
	    this.wrapper.dataProvider.setData(wargs.value);
    }

	if (wargs.args && wargs.args.topic) {
	    topic = wargs.args.topic;
	}

	if (wargs.selected) {
	    this.wrapper.setValue(wargs.selected);
	}
	
	this.getValue = function() {
        return this.wrapper.getValue();
    }
		
	this.onChange = function(value){
	   jmaki.publish(topic, value);
	}
	
	dojo.event.connect(this.wrapper, "setSelectedValue", _this, "onChange");	
}