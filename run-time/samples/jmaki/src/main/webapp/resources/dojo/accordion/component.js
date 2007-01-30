dojo.require("dojo.widget.AccordionContainer");

// define the namespaces
if (!jmaki.widgets.dojo) {
	jmaki.widgets.dojo = {};
}
jmaki.widgets.dojo.accordion = {};

jmaki.widgets.dojo.accordion.Widget = function(wargs) {
	var container = document.getElementById(wargs.uuid);
	var accordion = dojo.widget.createWidget("AccordionContainer", null, container);
	var rows = [];
	// pull in the arguments
	if (typeof wargs.value != "undefined") {
  
  	// convert the rows object into an array
  	if (typeof wargs.value.rows == "object") {
        for (var i in wargs.value.rows) {
          var row = [];
          for (var ir in wargs.value.rows[i] ) {  
            row[ir] = wargs.value.rows[i][ir];
          }
          rows.push(row);
        }
  	  }
	} else {
      rows = [
                {label: 'Books', content: 'Book content'},
                {label: 'Magazines', content: 'Magazines here'},
                {label: 'Newspaper', content: 'Newspaper content'}
            ];
    }

  var selectedIndex = 0;

  if (typeof wargs.args != 'undefined' && 
    typeof wargs.args.selectedIndex != 'undefined') {
        selectedIndex = Number(wargs.args.selectedIndex);
  }

  for(i=0; i<rows.length; ++i) {
    var _row = rows[i];
    if (typeof _row.url == 'undefined') {
        var content = dojo.widget.createWidget("ContentPane", {label: rows[i].label, selected: i==selectedIndex});
        content.setContent(_row.content);
        accordion.addChild(content);
    } else {
        var _c = dojo.widget.createWidget("ContentPane", {label: _row.label, selected: i==1});
        var _d = document.createElement("div");
        _c.setContent(_d);
        accordion.addChild(_c);
        var _in = new jmaki.Injector();
        _in.inject({url:_row.url, injectionPoint: _d});
    }  
  }
  accordion.onResized();
}
