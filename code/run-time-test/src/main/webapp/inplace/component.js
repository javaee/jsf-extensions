new Ajax.InPlaceEditor(widget.uuid, widget.service, {ajaxOptions: {method: 'post'}, widget: widget, callback: function(form, value) {
    var result = null;
    if (typeof _globalScope.userInplaceCallback == 'undefined') {
        result = Form.serialize(form);
    } else {
	result = _globalScope.userInplaceCallback(form,value, $(this.widget.uuid));
	var stateFieldName = "javax.faces.ViewState";
	var stateElements = window.document.getElementsByName(stateFieldName);
	var stateValue = stateElements[0].value;
	var uriEncodedState = encodeURI(stateValue);
	var rexp = new RegExp("\\+", "g");
	var encodedState = uriEncodedState.replace(rexp, "\%2B");
	var formName = form.id;
	var pctxts = "";
	result = result + "&" + formName + "=" + formName + "&" + stateFieldName + "=" +
            encodedState + "&" + this.widget.uuid + "=" + this.widget.uuid +
            "&com.sun.faces.PCtxt=:" + this.widget.uuid;
    }
    return result;
  }
});