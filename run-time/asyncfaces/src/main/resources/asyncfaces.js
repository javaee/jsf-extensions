var AsyncFaces = {};

AsyncFaces.collectElements = function(list) {
    var result = new Array();
    for(var i = 0; i < list.length; ++i) {
        var n = list[i];
        if (n.nodeType == 1) {
            result.push(n);
        }
    }
    return result;
};

AsyncFaces.getElement = function(list, index) {
    var k = 0;
    for (var i = 0; i < list.length; ++i) {
        var node = list[i];
        if (node.nodeType == 1) {
            if (k++ == index) {
                return list[i];
            }
        }
    }
};

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

AsyncFaces.HtmlDataTableRowRender = {
    
    handle: function(id, markup, node) {
        var parameters = AsyncFaces.getParameters(node);
        var index = parseInt(parameters["index"]);
        
        var table = $(id);
        var tbody = table.getElementsByTagName("tbody")[0];
        var row = AsyncFaces.getElement(tbody.childNodes, index);
        
        var cols = AsyncFaces.collectElements(row.childNodes);
        
        var container = document.createElement("div");    
        container.innerHTML = markup;
        var divList = AsyncFaces.collectElements(container.childNodes);
        for (var i = 0; i < divList.length; ++i) {
            var div = divList[i];
            cols[i].innerHTML = div.innerHTML;
        }
    }
    
};