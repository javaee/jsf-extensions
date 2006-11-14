dojo.require("dojo.widget.*");
dojo.require("dojo.widget.DropdownDatePicker");

var container = document.getElementById(widget.uuid);
var w = dojo.widget.createWidget(container);

var date;
if (typeof widget.value != 'undefined') {
    date = new Date(widget.value);
} else {
    date = new Date();
}

w.datePicker.setDate(date);

w.getValue = function() {
    return w.datePicker.getValue().replace(/-/g, '/');
}

// add a saveState function
if (typeof widget.valueCallback != 'undefined') {
    w.saveState = function() {
        // we need to be able to adjust this
        var url = widget.valueCallback;
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
jmaki.attributes.put(widget.uuid, w);