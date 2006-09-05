new Ajax.InPlaceEditor(widget.uuid, widget.service, 
  { ajaxOptions: 
        {method: 'post'}, 
        widget: widget,
        callback: function(form, value) {
	  var result = null;
	  if (typeof _globalScope.DynaFaces == 'undefined') {
	      result = Form.serialize(form);
	  } else {
	      this.ajaxOptions.requestHeaders = 
		  this.ajaxOptions.requestHeaders || [];
	      this.ajaxOptions.requestHeaders.push(DynaFaces.gPartial);
	      this.ajaxOptions.requestHeaders.push(true);
	      this.ajaxOptions.requestHeaders.push(DynaFaces.gRender);
	      this.ajaxOptions.requestHeaders.push(this.widget.uuid);
	      this.ajaxOptions.requestHeaders.push(DynaFaces.gExecute);
	      this.ajaxOptions.requestHeaders.push(this.widget.uuid);
	      
	      var stateElements = window.document.getElementsByName(DynaFaces.gViewState);
	      var stateValue = encodeURIComponent(stateElements[0].value);
	      var formName = encodeURIComponent(form.id);
	      var uuid = encodeURIComponent(this.widget.uuid);
		  
	      result = uuid + "=" + value + "&" + 
		  formName + "=" + formName + "&" + 
		  DynaFaces.gViewState + "=" + stateValue;
	  }
	  return result;
      }, 
      onComplete: function(transport, element) {
	    if (null == transport || null == element) {
		return;
	    }
	    if (typeof _globalScope.DynaFaces == 'undefined') {
		return;
	    }

	    var xml = transport.responseXML;
	    var components = xml.getElementsByTagName('components')[0];
	    var render = components.getElementsByTagName('render');
	    var str = render[0].firstChild.firstChild;
	    str = str.text || str.data;
	    element.removeChild(element.firstChild);
	    element.innerHTML = str;

	    var state = state || xml.getElementsByTagName('state')[0].firstChild;
	    if (state) {
		var stateFields = 
		    Document.getElementsByName(DynaFaces.gViewState);
		var i;
		for (i = 0; i < stateFields.length; i++) {
		    stateFields[i].value = state.text || state.data;
		}
	    }

      }

  });
