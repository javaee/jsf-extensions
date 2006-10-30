dojo.require("dojo.widget.*");
dojo.require("dojo.widget.Clock");
var wLabel = "";
var wLabelColor = "#fff";
var wTopLabelColor = "#efefef";
var wHandColor="#788598";
var wHandStroke="#6f7b8c";
var wSecondHandColor=[201, 4, 5, 0.8];


var clockImage = dojo.uri.dojoUri("src/widget/templates/images/clock.png");

if (typeof widget.args != 'undefined') {
    if (typeof widget.args.clockType != 'undefined') {
       if (widget.args.clockType == 'black') {
           clockImage = widget.widgetDir + "/images/clock-black.png";

       } else if (widget.args.clockType == 'blackTexture') {
            clockImage = widget.widgetDir + "/images/clock-black-texture.png";
       } else if (widget.args.clockType == 'gray') {
            clockImage = widget.widgetDir + "/images/clock-gray-gradient.png";
       } else if (widget.args.clockType == 'grayPlastic') {
            clockImage = widget.widgetDir + "/images/clock-gray-plastic.png";
       } else if (widget.args.clockType == 'plain') {
            clockImage = widget.widgetDir + "/images/clock-plain.png";
       }
    }
    if (typeof widget.args.label != 'undefined') {
        wLabel = widget.args.label;
    }
    if (typeof widget.args.labelColor != 'undefined') {
        wLabelColor = widget.args.labelColor;
    }
    if (typeof widget.args.topLabelColor != 'undefined') {
        wTopLabelColor = widget.args.topLabelColor;
    }
    if (typeof widget.args.handColor != 'undefined') {
        wHandColor = widget.args.handColor;
    }
    if (typeof widget.args.handBorderColor != 'undefined') {
        wHandStroke = widget.args.handBorderColor;
    }
    if (typeof widget.args.secondHandColor != 'undefined') {
        wSecondHandColor = widget.args.secondHandColor;
    }
}
var timeZoneOffset=0;
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
var clock = dojo.widget.createWidget("dojo:clock",mixins, dojo.byId(widget.uuid));
jmaki.attributes.put(widget.uuid, clock);