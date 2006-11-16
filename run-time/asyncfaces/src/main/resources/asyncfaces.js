var AsyncFaces = {};


AsyncFaces.getParameters = function(node) {
    var prms = node.getElementsByTagName("param");
    var parameters = {};
    for (var i = 0; i < prms.length; ++i) {
        var p = prms[i];
        var content = p.firstChild;
        var paramValue = content.text || content.data;
        
        parameters[p.getAttribute("name")] = paramValue;
    }  
    return parameters;
};

AsyncFaces.SetClassName = {
    
    handle: function(id, markup, node) {
        var parameters = AsyncFaces.getParameters(node);
        var node = document.getElementById(id);
        node.className = parameters.className;
    }
    
};