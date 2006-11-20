dojo.require("dojo.widget.FisheyeList");

// create the top level widget
var container = document.getElementById(widget.uuid);

if (typeof _globalScope.DynaFaces != 'undefined') {
  // Find the form for this widget
  var containerForm  = container;
  while (null != containerForm && 
	 -1 == containerForm.tagName.toLowerCase().indexOf("form")) {
      containerForm = containerForm.parentNode;
  }
  // Save it on the widget so we have access to it in the listener.
  widget.form = containerForm;
}

var fishEye = dojo.widget.createWidget(container);

// default topic
var topic = "/dojo/fisheye";
// default values
var items = ["item1","item2","item3"];

if (typeof widget.args != "undefined") {
    if (typeof widget.args.topic != "undefined") {
        topic = widget.args.topic;
    }
    if (typeof widget.args.items != "undefined") {
        items = widget.args.items;
    }
}

// programtically add FisheyeListItem children to the widget
var counter = 0;
while (true) {
    var i = items[counter++];
    if (i == null) break;
    var icon = dojo.widget.createWidget("FisheyeListItem", i);

    icon.onClick = function () {
        jmaki.publish(topic, this);
    }
    fishEye.addChild(icon);
}

if (typeof _globalScope.DynaFaces != 'undefined') {
    function getDynaFacesListenerClosure(widget) {
	return (function dynaFacesFisheyeListener(item) {
		    // If dojo removed the div declared in component.htm
		    // from the DOM.
		    var hidden = null;
		    if (widget.form) {
			// create an inputHidden with the proper value.
			hidden = document.createElement("input");
			hidden.type = "hidden";
			hidden.id = widget.uuid;
			hidden.value = item.index;
			widget.form.appendChild(hidden);
		    }
		    DynaFaces.fireAjaxTransaction(hidden, 
                    { 

		    render: widget.uuid,
		    inputs: widget.uuid
			    
                    });
		});
    }
    var functRef = getDynaFacesListenerClosure(widget);
    jmaki.subscribe("/dojo/fisheye", functRef);
    
 }

jmaki.attributes.put(widget.uuid, fishEye);
