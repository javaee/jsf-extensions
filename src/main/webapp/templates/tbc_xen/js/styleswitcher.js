var prefsLoaded = false;
var skins=["blue.css","green.css","orange.css","pink.css"];

function setActiveStyleSheet(title) {
	
  var i, a, main;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    if(a.getAttribute("rel").indexOf("style") != -1 ) {
      for(var j=0;j<skins.length;j++) {
    	  if(a.getAttribute("href").indexOf(skins[j]) != -1)
    		  a.disabled = true;
   	  }
      if(a.getAttribute("href").indexOf(title) != -1)
    	a.disabled = false;    
   }
  }
  createCookie("style", title, 365);
  
}

function getActiveStyleSheet() {
  var i, a;
  for(i=0; (a = document.getElementsByTagName("link")[i]); i++) {
    if(a.getAttribute("rel").indexOf("style") != -1  && !a.disabled) {
    	 for(var j=0;j<skins.length;j++) {
       	  if(a.getAttribute("href").indexOf(skins[j]) != -1)
       		var href=a.getAttribute("href");
       	  	var value=href.substring(href.lastIndexOf("/")+1,href.indexOf(".css")+3);
       	  	alert(value);
      	  }
    	return null;
    }
  }
  return null;
}

function getPreferredStyleSheet() {
  return skins[3];
}

function createCookie(name,value,days) {
  if (days) {
    var date = new Date();
    date.setTime(date.getTime()+(days*24*60*60*1000));
    var expires = "; expires="+date.toGMTString();
  }
  else expires = "";
  document.cookie = name+"="+value+expires+"; path=/";
}

function readCookie(name) {
  var nameEQ = name + "=";
  var ca = document.cookie.split(';');
  for(var i=0;i < ca.length;i++) {
    var c = ca[i];
    while (c.charAt(0)==' ') c = c.substring(1,c.length);
    if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
  }
  return null;
}

window.onload = function(e) {

  var style=readCookie("style");
  if(!style)
	  style=getPreferredStyleSheet();
   setActiveStyleSheet(style);
}



var style=readCookie("style");
if(!style)
	  style=getPreferredStyleSheet();
 setActiveStyleSheet(style);