new Ajax.InPlaceEditor(widget.uuid, widget.service, 
  { ajaxOptions: 
        {method: 'post'}, 
        widget: widget,
        callback: function(form, value) {
	  var result = null;
	  if (typeof _globalScope.gAsync == 'undefined') {
	      result = Form.serialize(form);
	  } else {
	      result = _globalScope.userInplaceCallback(form,value, 
							$(this.widget.uuid));
	      this.ajaxOptions.requestHeaders = 
		  this.ajaxOptions.requestHeaders || [];
	      this.ajaxOptions.requestHeaders.push(gAsync);
	      this.ajaxOptions.requestHeaders.push("true");
	      this.ajaxOptions.requestHeaders.push(gSubtrees);
	      this.ajaxOptions.requestHeaders.push(this.widget.uuid);
	      this.ajaxOptions.requestHeaders.push(gRunthru);
	      this.ajaxOptions.requestHeaders.push("UPDATE_MODEL_VALUES");
	      
	      var stateElements = window.document.getElementsByName(gViewState);
	      var stateValue = stateElements[0].value;
	      var uriEncodedState = encodeURI(stateValue);
	      var rexp = new RegExp("\\+", "g");
	      var encodedState = uriEncodedState.replace(rexp, "\%2B");
	      var formName = form.id;
	      result = result + "&" + formName + "=" + formName + "&" + gViewState + "=" +
		  encodedState + "&" + this.widget.uuid + "=" + this.widget.uuid;
	  }
	  return result;
      }, 
      onComplete: function(transport, element) {
	    if (null == transport || null == element) {
		return;
	    }

	    var xml = transport.responseXML;
	    var state = state || xml.getElementsByTagName('async-response')[0].getAttribute('state');
	    var encode = xml.getElementsByTagName('encode');
	    var id, content, markup, str;
	    id = encode[0].getAttribute('id');
	    content = encode[0].firstChild;
	    markup = content.text || content.data;
	    str = markup.stripScripts();
	    element.removeChild(element.firstChild);
	    element.innerHTML = str;
	    markup.evalScripts();
      }

  });
