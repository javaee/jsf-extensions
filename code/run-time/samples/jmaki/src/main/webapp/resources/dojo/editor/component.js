
var container = document.getElementById(widget.uuid);

dojo.widget.createWidget("Editor2", 
    { 	shareToolbar: false, 
        toolbarAlwaysVisible: true,
        focusOnLoad: false 
    }, container);