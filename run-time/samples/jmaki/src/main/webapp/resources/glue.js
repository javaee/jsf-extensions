jmaki.listeners = function() {

    this.handleFisheye = function(args) {
        alert("glue.js : fisheye event");
    }

    this.onSave = function(args) {
        alert("glue.js : onSave request from: " + args.id + " value=" + args.value);
    }
    
    this.debug = function(args) {
        alert("debug: " + args);
    }
    
    this.geocoderListener = function(coordinates) {
        var keys = jmaki.attributes.keys();       
        for (var l = 0; l < keys.length; l++) {
            if (jmaki.widgets.yahoo && jmaki.widgets.yahoo.map && jmaki.widgets.yahoo.map.Widget &&
                 jmaki.attributes.get(keys[l]) instanceof jmaki.widgets.yahoo.map.Widget ) {
                var _map = jmaki.attributes.get(keys[l]).map;
                var centerPoint = new YGeoPoint(coordinates[0].latitude,coordinates[0].longitude);
                var marker = new YMarker(centerPoint);
                var txt = '<div style="width:160px;height:50px;"><b>' + coordinates[0].address + ' ' + coordinates[0].city + ' ' +  coordinates[0].state + '</b></div>';
                marker.addAutoExpand(txt);
                _map.addOverlay(marker);
                _map.drawZoomAndCenter(centerPoint);
            } else if (jmaki.widgets.google && jmaki.widgets.google.map &&
                jmaki.widgets.google.map.Widget &&
                 jmaki.attributes.get(keys[l]) instanceof jmaki.widgets.google.map.Widget ) {
                var _map = jmaki.attributes.get(keys[l]).map;
                var centerPoint = new GLatLng(coordinates[0].latitude,coordinates[0].longitude);
                _map.setCenter(centerPoint);
                var marker = new GMarker(centerPoint);
                _map.addOverlay(marker);
                var txt = '<div style="width:160px;height:50px;"><b>' + coordinates[0].address + ' ' + coordinates[0].city + ' ' +  coordinates[0].state + '</b></div>';
                marker.openInfoWindowHtml(txt);               
            }  
        }
    }
}