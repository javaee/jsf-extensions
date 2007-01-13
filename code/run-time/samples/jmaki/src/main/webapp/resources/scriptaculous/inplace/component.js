// define the namespaces
if (!jmaki.widgets.scriptaculous) {
	jmaki.widgets.scriptaculous = {};
}
jmaki.widgets.scriptaculous.inplace = {};

jmaki.widgets.scriptaculous.inplace.Widget = function(wargs) {
    var wInstance = this;
	
  this.wrapper = new Ajax.InPlaceEditor(wargs.uuid, wargs.service, 
  { ajaxOptions: 
        {method: 'post'}, 
        widget: wargs,
        callback: function(form, value) {
	    var result = null;
	  wInstance.wrapper.value = value;
	  jmaki.publish("/scriptaculous/inplace/valueUpdate", 
			{target:wInstance, wargs:wargs});
	  if (null != wargs.outResult) {
	      result = wargs.outResult;
	  }
	  else {
	      result = Form.serialize(form);
	  }
	  return result;
      }, 
      onComplete: function(transport, element) {
	    if (null == transport || null == element) {
		return;
	    }
	    jmaki.publish("/scriptaculous/inplace/response", 
			  {target:wInstance, wargs:wargs, transport:transport, 
			   element:element});
      }

  });
}
