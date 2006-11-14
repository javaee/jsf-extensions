dojo.require("dojo.widget.*");
dojo.require("dojo.widget.DatePicker");

var container = document.getElementById(widget.uuid);
var dw = dojo.widget.createWidget(container);
jmaki.attributes.put(widget.uuid, dw);

if (typeof widget.value != 'undefined') {
    var date = new Date(widget.value);
    dw.setDate(date);
}

// add a saveState function
if (typeof widget.valueCallback != 'undefined') {
    dw.saveState = function() {
        // we need to be able to adjust this
        var url = widget.valueCallback;
        dojo.io.bind({
                url: url + "?cmd=update",
                method: "post",
            content: { "value" : dw.getValue().replace(/-/g, '/') },
            load: function (type,data,evt) {
                // do something if there is an error
            }
        });
    }
}