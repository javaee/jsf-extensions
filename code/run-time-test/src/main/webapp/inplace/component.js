new Ajax.InPlaceEditor(widget.uuid, widget.service, 
  { ajaxOptions: 
        {method: 'post'}, 
        widget: widget,
        callback: function(form, value) {
	  var result = null;
	  if (typeof _globalScope.gPartial == 'undefined') {
	      result = Form.serialize(form);
	  } else {
	      this.ajaxOptions.requestHeaders = 
		  this.ajaxOptions.requestHeaders || [];
	      this.ajaxOptions.requestHeaders.push(gPartial);
	      this.ajaxOptions.requestHeaders.push("true");
	      this.ajaxOptions.requestHeaders.push(gRender);
	      this.ajaxOptions.requestHeaders.push(this.widget.uuid);
	      this.ajaxOptions.requestHeaders.push(gRunthru);
	      this.ajaxOptions.requestHeaders.push("UPDATE_MODEL_VALUES");
	      
	      var stateElements = window.document.getElementsByName(gViewState);
	      var stateValue = encodeURIComponent(stateElements[0].value);
	      var formName = encodeURIComponent(form.id);
	      var uuid = encodeURIComponent(this.widget.uuid);
		  
	      result = uuid + "=" + value + "&" + 
		  formName + "=" + formName + "&" + 
		  gViewState + "=" + stateValue;
	  }
	  return result;
      }, 
      onComplete: function(transport, element) {
	    if (null == transport || null == element) {
		return;
	    }

	    var xml = transport.responseXML;
	    var state = state || xml.getElementsByTagName('async-response')[0].getAttribute('state');
	    var update = xml.getElementsByTagName('update');
	    var str;
	    str = update[0].firstChild;
	    str = str.text || str.data;
	    element.removeChild(element.firstChild);
	    element.innerHTML = str;
	    if (state) {
		var hf = $(gViewState);
		if (hf) {
		    hf.value = state.text || state.data;
		}
	    }

      }

  });
