dojo.require("dojo.widget.FilteringTable");

var topic = "/dojo/table";
var autoCreate = true;

function FilterTableWrapper (uuid, rows, auto, columns) {

  var container = document.getElementById(uuid);
  var table;
  var count = rows.length;
  var columnNames = [];
  var lColumns = [];

  if (!auto) {
    table = dojo.widget.createWidget(container);
  } else {
    table = dojo.widget.createWidget("dojo:FilteringTable",{valueField: "Id"},container);
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
      for (var i=0; i < rows.length; i++) {
          var nRow = {};
          if (typeof rows[i].Id == "undefined") {
                nRow.Id = i;
          } else {
            nRow.Id = rows.Id;
          }
          for (var cl = 0; cl < columnNames.length; cl++) {         
                nRow[columnNames[cl]] = rows[i][cl];
          }
          data.push(nRow);
    
        }
  table.store.setData(data);

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

var columns;

if (typeof widget.args != "undefined") {
    if (typeof widget.args.topic != "undefined") {
        topic = widget.args.topic;
    }
    if (typeof widget.args.auto != "undefined") {
        autoCreate = (widget.args.auto == 'true');
    }
}

// pull in the arguments
if (typeof widget.value != "undefined") {
  var rows = [];
  // convert the rows object into an array
  if (typeof widget.value.rows == "object") {
      for (var i in widget.value.rows) {
        var row = [];
        for (var ir in widget.value.rows[i] ) {  
            row[ir] = widget.value.rows[i][ir];
        }
        rows.push(row);
      }
  } else {
        rows = widget.value.rows;
  }
  var columns = widget.value.columns;
  tw = new FilterTableWrapper(widget.uuid, rows, autoCreate, columns);
  jmaki.attributes.put(widget.uuid, tw);
} else if (typeof widget.service != "undefined") {
        var _w = widget;
        dojo.io.bind({
                url: widget.service,
                method: "get",
                mimetype: "text/json",
                load: function (type,data,evt) {
                    wdata = data.rows;
                    columns = data.columns;
                    tw = new FilterTableWrapper(_w.uuid, wdata, autoCreate, columns);
                    jmaki.attributes.put(_w.uuid, tw);
            }
        });
} else {
 columns = { "title" : "Title", "author":"Author", "isbn": "ISBN #", "description":"Description"}
 // default data
 var rows = [
   ['Book Title 1', 'Author 1', '4412', 'A Some long description'],
   ['Book Title 2', 'Author 2', '4413','B Some long description'],
   ['Book Title 3', 'Author 3',  '4414','C Some long description'],
   ['Book Title 4', 'Author 4','4415','D Some long description']
   ]

   tw = new FilterTableWrapper(widget.uuid, rows, autoCreate, columns);
   jmaki.attributes.put(widget.uuid, tw);
}