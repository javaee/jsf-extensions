dojo.require("dojo.widget.FilteringTable");
// define the namespaces
if (!jmaki.widgets.dojo) {
	jmaki.widgets.dojo = {};
}
jmaki.widgets.dojo.table = {};

jmaki.widgets.dojo.table.Widget = function(wargs) {
	var _this = this;
	var columns;
	this.rows = [];
	var uuid = wargs.uuid;
	var topic = "/dojo/table";
	var auto = true;
	
	if (typeof wargs.args != "undefined") {
	    if (typeof wargs.args.topic != "undefined") {
	        topic = wargs.args.topic;
	    }
	    if (typeof wargs.args.auto != "undefined") {
	        auto = (wargs.args.auto == 'true');
	    }
	}

  var container = document.getElementById(uuid);
  var table;
  var count = _this.rows.length;
  var columnNames = [];
  var lColumns = [];
  this.init = function() {
	  if (!auto) {
	    table = dojo.widget.createWidget(container);
	  } else {
	    table = dojo.widget.createWidget("FilteringTable",{valueField: "Id"},container);
	    // provide generic column names if they were not provided.
	    if (typeof columns == "undefined") {
	        for (var l = 0; l < count; l++) {
	            columnNames.push({ field: "col_" + l, label: "Column " + l,  dataType:"String" });
	        }
	    }
	    // now build up the list of titles
	     for (var l in columns) {
	          var ct = columns[l];
	          var cType = "string";
	          // we want to only allow for specifc number and string types
	          // this could be an array or object and we want to prevent
	          if (typeof ct == "string") {
	          } else if (typeof ct == "number") {
	            cType = "number";
	          }
	          lColumns.push({ field: l, label: ct,  dataType:cType });
	          columnNames.push(l);
	      }
	  }
	  
	  for (var x = 0; x < lColumns.length; x++) {
	      table.columns.push(table.createMetaData(lColumns[x]));
	  }
	
	  var data = [];
	  // add an Id for everything as it is needed for sorting
	  for (var i=0; i < _this.rows.length; i++) {
	      var nRow = {};
	      if (typeof _this.rows[i].Id == "undefined") {
	          nRow.Id = i;
	      } else {
	          nRow.Id = _this.rows.Id;

	      }
	      for (var cl = 0; cl < columnNames.length; cl++) {     
	          nRow[columnNames[cl]] = _this.rows[i][cl];
	      }
	      data.push(nRow);   
	  }
	  table.store.setData(data);
  }
 	// pull in the arguments
	if (typeof wargs.value != "undefined") {
	  _this.rows = [];
	  // convert the rows object into an array
	  if (typeof wargs.value.rows == "object") {
	      for (var i in wargs.value.rows) {
	        var row = [];
	        for (var ir in wargs.value.rows[i] ) {  
	            row[ir] = wargs.value.rows[i][ir];
	        }
	        _this.rows.push(row);
	      }
	  } else {
	        _this.rows = wargs.value.rows;
	  }
	  columns = wargs.value.columns;
	  _this.init();

	} else if (typeof wargs.service != "undefined") {

	        var _w = wargs;
	        dojo.io.bind({
	                url: wargs.service,
	                method: "get",
	                mimetype: "text/json",
	                load: function (type,data,evt) {
	                    _this.rows = data.rows;
	                    columns = data.columns;
	                    _this.init();
	            }
	        });
	} else {
	 columns = { "title" : "Title", "author":"Author", "isbn": "ISBN #", "description":"Description"}
	 // default data
	 _this.rows = [
	   ['Book Title 1', 'Author 1', '4412', 'A Some long description'],
	   ['Book Title 2', 'Author 2', '4413','B Some long description'],
	   ['Book Title 3', 'Author 3',  '4414','C Some long description'],
	   ['Book Title 4', 'Author 4','4415','D Some long description']
	   ]
		_this.init();
	}
  
  this.clearFilters = function(){
      table.clearFilters();
  }
  
  this.addRow = function(b){
    // add an id for sorting if not defined
    if (typeof b.Id == "undefined") {      
        b.Id = count++;
    }
    table.store.addData(b);
  }
}