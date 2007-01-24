dojo.require("dojo.widget.Tree");

// define the namespaces
if (!jmaki.widgets.dojo ) {
	jmaki.widgets.dojo = {};
}
jmaki.widgets.dojo.tree = {};

jmaki.widgets.dojo.tree.Widget = function(widget) {
    var _widget = widget;
    var topic = "/dojo/tree";

    var container = document.getElementById(_widget.uuid);
    var dTree = dojo.widget.createWidget(container);

    // use the default tree found in the widget.json if none is provided
    if (typeof _widget.value == 'undefined') {
        var callback;
        // default to the service in the widget.json if a value has not been st
        // and if there is no service
        if (typeof _widget.service == 'undefined') {
            _widget.service = _widget.widgetDir + "/widget.json";
            callback = function(req) {
                if (req.readyState == 4) {
                    var obj = eval("(" + req.responseText + ")");
                    var jTree = obj.value.defaultValue;
                    var root = jTree.root;
                    buildTree(root);
                }
            }
           
        } else {
           callback = function(req) {
                if (req.readyState == 4) {
                alert("eavl");
                    var jTree = eval("(" + req.responseText + ")");
                    var root = jTree.root;
                    buildTree(root);
                }
            }        
        }
          var ajax = jmaki.doAjax({url : _widget.service, callback : callback});
    } else if (typeof _widget.value == 'object') {
      buildTree(_widget.value.root);
    }
    
    // now build the tree programtically
    function buildTree(root, parent) {
        var rChildren = false;
        var rExpanded = false;
        if (typeof root.children != 'undefined') rChildren = true;
        if (typeof root.expanded != 'undefined' && (root.expanded == true || root.expanded == "true")) rExpanded = true;
        var rNode = dojo.widget.createWidget("TreeNode", {title:root.title, isFolder:rChildren, isExpanded:rExpanded});
        // wire in onclick handler
        if (typeof root.onclick != 'undefined') {
                var _m = root.onclick;
                _m.title = root.title;
                dojo.event.connect(rNode, 'onTitleClick', function(e){_m.event = e;jmaki.publish(topic,_m);});
            } else if (!rChildren) {
                var _m = {};
                _m.title = root.title;
                dojo.event.connect(rNode, 'onTitleClick', function(e){_m.event = e;jmaki.publish(topic,_m);});
            }
        if (typeof parent == 'undefined') {
            dTree.addChild(rNode,0);
        } else {
            parent.addChild(rNode);
        }
        for (t in root.children) {
            var n = root.children[t];
            var hasChildren = false;
            if (typeof n.children != 'undefined') hasChildren = true;
            var isExpanded = false;
            if (typeof n.expanded  != 'undefined' && n.expanded == "true") isExpanded = true;
            var lNode = dojo.widget.createWidget("TreeNode", {title:n.title, isFolder:hasChildren, isExpanded:isExpanded});
            if (typeof n.onclick != 'undefined') {
                var _m = n.onclick;
                _m.title = n.title;
                dojo.event.connect(lNode, 'onTitleClick', function(e){_m.event = e;jmaki.publish(topic,_m);});
            } else if (!hasChildren) {
                var _m = {};
                _m.title = n.title;
                dojo.event.connect(lNode, 'onTitleClick', function(e){_m.event = e;jmaki.publish(topic,_m);});
            }
            rNode.addChild(lNode,t);
            //  recursively call this function to add children
            if (typeof n.children != 'undefined') {
                for (ts in n.children) {
                    buildTree(n.children[ts], lNode);
                }
            }
            
        }

   }
}