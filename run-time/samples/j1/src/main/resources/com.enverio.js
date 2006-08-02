
var Enverio = {}
Enverio.Autocompleter = Class.create();
Object.extend(Object.extend(Enverio.Autocompleter.prototype, Autocompleter.Base.prototype), {
  initialize: function(element, update, options) {
		      this.baseInitialize(element, update, options);
		      this.options.asynchronous  = true;
		      this.options.frequency = 0.2;
		      this.options.onComplete    = this.onComplete.bind(this);
		      this.options.defaultParams = this.options.parameters;
		      this.options.render = 'none';

		      
  },

  getUpdatedChoices: function() {
    entry = encodeURIComponent(this.options.paramName) + '=' + 
      encodeURIComponent(this.getToken());

    this.options.parameters = this.options.callback ?
	this.options.callback(this.element, entry) : entry;
    this.options.requestHeaders = this.options.requestHeaders || [];
    this.options.requestHeaders.push(gFacesEvent);
    var elementId = this.element.id || this.element.name;
    this.options.requestHeaders.push("SuggestEvent," + elementId +
				     ",INVOKE_APPLICATION");

    DynaFaces.fireEvent(this.element, this.options);
  },

  onComplete: function(request) {
    this.updateChoices(request.responseText);
  }

});
