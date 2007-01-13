 // define the namespaces
if (!jmaki.widgets.scriptaculous) {
    jmaki.widgets.scriptaculous = {};
}
jmaki.widgets.scriptaculous = {};

jmaki.widgets.scriptaculous.autocompleter = {};

jmaki.widgets.scriptaculous.autocompleter.Widget = function(wargs) {
	this.wrapper = new Ajax.Autocompleter(wargs.uuid,
									  wargs.uuid + '_target',
									  wargs.service,
									  {method:'get', select:'selectme'}
									  );
}