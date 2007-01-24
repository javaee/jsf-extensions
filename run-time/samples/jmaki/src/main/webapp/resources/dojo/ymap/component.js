dojo.require("dojo.widget.YahooMap");

// define the namespaces
if (!jmaki.widgets.dojo) {
	jmaki.widgets.dojo = {};
}
jmaki.widgets.dojo.ymap = {};

jmaki.widgets.dojo.ymap.Widget = function(wargs) {
    var container = document.getElementById(wargs.uuid);
    var w = dojo.widget.createWidget(container);
}