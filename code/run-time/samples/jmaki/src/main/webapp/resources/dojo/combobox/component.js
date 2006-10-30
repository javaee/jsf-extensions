dojo.require("dojo.widget.ComboBox");

var container = document.getElementById(widget.uuid);
var cb = dojo.widget.createWidget(container);

var data = cb.baseDir + "/assets/data.js";
var topic = "/dojo/combobox";


if (typeof widget.value != 'undefined') {
    // populate the dataProvider for the combobox
    // with an array of arrays (as dojo likes it)
    if (typeof widget.value == 'object') {
        var props = [];
        for (var l in widget.value ) {
            var items = [];
            items.push(widget.value[l][0]);
            items.push(widget.value[l][1]);
            props.push(items);
        }
        cb.dataProvider.addData(props);
    } else {
        cb.setValue(widget.value);
    }
}

if (typeof widget.args != "undefined") {
    if (typeof widget.args.topic != "undefined") {
        topic = widget.args.topic;
    }
}

cb.onChange = function(value){
   jmaki.publish(topic, value);
} 
dojo.event.connect(cb, "setSelectedValue", cb, "onChange"); 
// add a saveState function
if (typeof widget.valueCallback != 'undefined') {
    cb.saveState = function() {
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
jmaki.attributes.put(widget.uuid, cb);