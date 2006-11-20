dojo.require("dojo.widget.*");
dojo.require("dojo.widget.TimePicker");

var container = document.getElementById(widget.uuid);
var w = dojo.widget.createWidget(container);
jmaki.attributes.put(widget.uuid, w);

if (typeof widget.value != 'undefined') {
    var splitme = widget.value.split(':');
    var hour = Number(splitme[0]);
    var minute = Number(splitme[1]);
    var time = new Date()
    time.setHours(hour);
    time.setMinutes(minute);
    w.setTime(time);
    w.initUI();
}

w.getValue = function() {
    var date = new Date();
    var time = w.selectedTime;
    date.setHours(time.hour);
    date.setMinutes(time.minute);
    return date;
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