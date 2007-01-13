dojo.require("dojo.widget.TabContainer");

// define the namespaces
if (!jmaki.widgets.dojo) {
	jmaki.widgets.dojo = {};
}
jmaki.widgets.dojo.tabbedview = {};

jmaki.widgets.dojo.tabbedview.Widget = function(wargs) {
        var container = document.getElementById(wargs.uuid);
        var tabbedview = dojo.widget.createWidget("TabContainer", null, container);
        var tabs = [];
        // pull in the arguments
        if (typeof wargs.value != "undefined") {
            
            // convert the tabs object into an array
            if (typeof wargs.value.tabs == "object") {
                for (var i in wargs.value.tabs) {
                    var row = [];
                    for (var ir in wargs.value.tabs[i] ) {  
                        row[ir] = wargs.value.tabs[i][ir];
                    }
                    tabs.push(row);
                }
            }
        } else {
            // default some data;
            tabs = [
            {label: 'Tab 0', content: 'This is the default data provided for a tabbed view in the component.js file.' +
             ' To customize the tabs set the value of the widget to an object containing an object with a tabs property containg' +
             ' an array of tabs. For example: {tabs:[{label:\'My Tab\', content: \'Some Content\'},{label:\'My Tab 2\', content: \'Tab 2 Content\'} ]}' +
             '. You can also include content from any URL within your domain. Example: {tabs:[{label:\'My Tab\', url: \'URL_TO_TAB_CONTENT\'} ]}'},
             {label: 'Tab 1', content: 'Tab 1 content'},
             {label: 'Tab 2', content: 'Tab 2 content'}
             ];    
        }
        
        for(var i=0; i<tabs.length; ++i) {
            var _row = tabs[i];
            if (typeof _row.url == 'undefined') {
                var _c = dojo.widget.createWidget("ContentPane", {label: tabs[i].label, selected: i==1});
                _c.setContent(_row.content);
                tabbedview.addChild(_c);
            } else {
                var divId = wargs.uuid + "_tab" + i;
                var _c = dojo.widget.createWidget("ContentPane", {label: _row.label, selected: i==1});
                _c.setContent("<div style='' id='" + divId +"'>Loading..." + "</div>");
                tabbedview.addChild(_c);
                jmaki.injector.inject({url:_row.url, injectionPoint: divId});
            }
            
        }
        tabbedview.onResized();
}