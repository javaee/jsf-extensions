dojo.require("dojo.widget.TimePicker");

jmaki.namespace("jmaki.widgets.dojo.timepicker");

jmaki.widgets.dojo.timepicker.Widget = function(wargs) {
    var _this = this;
    var container = document.getElementById(wargs.uuid);
    this.wrapper = dojo.widget.createWidget("TimePicker", null,container);

    if (typeof wargs.value != 'undefined') {
        var splitme = wargs.value.split(':');
        var hour = Number(splitme[0]);
        var minute = Number(splitme[1]);
        var time = new Date()
        time.setHours(hour);
        time.setMinutes(minute);
        w.setTime(time);
        w.initUI();
    }

    this.getValue = function() {
        var date = new Date();
        var time = w.selectedTime;
        date.setHours(time.hour);
        date.setMinutes(time.minute);
        return date;
    }

    // add a saveState function
    if (wargs.service) {
        this.saveState = function() {
            // we need to be able to adjust this
            var url = wargs.service;
            dojo.io.bind({
                    url: url + "?cmd=update",
                    method: "post",
                    content: { "value" : _this.getValue() },
                    load: function (type,data,evt) {
                    // do something if there is an error
                }
            });
        }
    }
}