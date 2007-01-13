dojo.require("dojo.widget.Clock");

// define the namespaces
if (!jmaki.widgets.dojo) {
	jmaki.widgets.dojo = {};
}
jmaki.widgets.dojo.clock = {};

jmaki.widgets.dojo.clock.Widget = function(wargs) {
    var self = this;
    var wLabel = "";
    var wLabelColor = "#fff";
    var wTopLabelColor = "#efefef";
    var wHandColor="#788598";
    var wHandStroke="#6f7b8c";
    var wSecondHandColor=[201, 4, 5, 0.8];
    var clockImage = dojo.uri.dojoUri("src/widget/templates/images/clock.png");

    var timeZoneOffset=0;

    if (typeof wargs.args != 'undefined') {
        if (typeof wargs.args.clockType != 'undefined') {
           if (wargs.args.clockType == 'black') {
               clockImage = wargs.widgetDir + "/images/clock-black.png";

           } else if (wargs.args.clockType == 'blackTexture') {
                clockImage = wargs.widgetDir + "/images/clock-black-texture.png";
           } else if (wargs.args.clockType == 'gray') {
                clockImage = wargs.widgetDir + "/images/clock-gray-gradient.png";
           } else if (wargs.args.clockType == 'grayPlastic') {
                clockImage = wargs.widgetDir + "/images/clock-gray-plastic.png";
           } else if (wargs.args.clockType == 'plain') {
                clockImage = wargs.widgetDir + "/images/clock-plain.png";
           }
        }
        if (typeof wargs.args.label != 'undefined') {
            wLabel = wargs.args.label;
        }
        if (typeof wargs.args.labelColor != 'undefined') {
            wLabelColor = wargs.args.labelColor;
        }
        if (typeof wargs.args.topLabelColor != 'undefined') {
            wTopLabelColor = wargs.args.topLabelColor;
        }
        if (typeof wargs.args.handColor != 'undefined') {
            wHandColor = wargs.args.handColor;
        }
        if (typeof wargs.args.handBorderColor != 'undefined') {
            wHandStroke = wargs.args.handBorderColor;
        }
        if (typeof wargs.args.secondHandColor != 'undefined') {
            wSecondHandColor = wargs.args.secondHandColor;
        }
	if (typeof wargs.args.timeZoneOffset != 'undefined') {
	    timeZoneOffset = wargs.args.timeZoneOffset;
	}
    }
    var mixins = {
        image: clockImage,
        timeZoneOffset:timeZoneOffset,
        label:wLabel,
        labelColor:wLabelColor,
        topLabelColor: wTopLabelColor,
        handColor:wHandColor,
        handStroke: wHandStroke,
        secondHandColor: wSecondHandColor

       }
       var _container = document.getElementById(wargs.uuid);
       if (/WebKit/i.test(navigator.userAgent)) {
           _container.innerHTML = "<div style='color:red'>This widget is currently not supported on Safari.</div>";
           return;
       }
       if (!_container) {
            var _t = setInterval(function() {
                if (document.getElementById(wargs.uuid)) {
                    clearInterval(_t);
                    _container = document.getElementById(wargs.uuid);
                    self.wrapper = dojo.widget.createWidget("Clock",mixins, _container);                
                }
            }, 25);
        } else {
            self.wrapper = dojo.widget.createWidget("Clock",mixins, _container); 
        }
}