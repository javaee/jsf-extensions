dojo.require("dojo.widget.DropdownDatePicker");

// define the namespaces
if (!jmaki.widgets.dojo) {
	jmaki.widgets.dojo = {};
}
jmaki.widgets.dojo.dropdowndatepicker = {};

jmaki.widgets.dojo.dropdowndatepicker.Widget = function(wargs) {

    var _this = this;
	var container = document.getElementById(wargs.uuid);
	this.wrapper = dojo.widget.createWidget("DropdownDatePicker", null, container);
	
	var date;
	if (typeof wargs.value != 'undefined') {
	    date = new Date(wargs.value);
	} else {
	    date = new Date();
	}
	
	this.wrapper.datePicker.setDate(date);
	
	this.getValue = function() {
	    return this.wrapper.datePicker.getValue().replace(/-/g, '/');
	}
	
	// add a saveState function
	if (wargs.service != 'undefined') {
	    _this.saveState = function() {
	        // we need to be able to adjust this
	        var url = wargs.service;
	        dojo.io.bind({
	                url: url + "?cmd=update",
	                method: "post",
	                content: { "value" : this.getValue() },
	                load: function (type,data,evt) {
	                // do something if there is an error
	            }
	        });
	    }
	}
	
}