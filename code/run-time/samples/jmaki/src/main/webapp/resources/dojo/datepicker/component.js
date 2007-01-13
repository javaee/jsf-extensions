dojo.require("dojo.widget.DatePicker");

// define the namespaces
if (!jmaki.widgets.dojo) {
	jmaki.widgets.dojo = {};
}
jmaki.widgets.dojo.datepicker = {};

jmaki.widgets.dojo.datepicker.Widget = function(wargs) {
    var _this = this;
	var container = document.getElementById(wargs.uuid);
	this.wrapper = dojo.widget.createWidget("DatePicker", null, container);
	
	if (wargs.value) {
	    var date = new Date(wargs.value);
	    this.wrapper.setDate(date);
	}
    
    this.getValue = function() {
        return  _this.wrapper.getValue().replace(/-/g, '/');
    }
	
	// add a saveState function
	if (wargs.service) {
	    this.saveState = function() {
	        // we need to be able to adjust this
	        var url = wargs.service;
	        dojo.io.bind({
	                url: url + "?cmd=update",
	                method: "post",
	                content: { "value" : _this.wrapper.getValue().replace(/-/g, '/') },
	                load: function (type,data,evt) {
	                    // do something if there is an error
	                }
	        });
        }
    }
}