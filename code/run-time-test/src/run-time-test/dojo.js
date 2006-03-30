/*
	Copyright (c) 2004-2005, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/

/*
	This is a compiled version of Dojo, built for deployment and not for
	development. To get an editable version, please visit:

		http://dojotoolkit.org

	for documentation and information on getting the source.
*/

var dj_global=this;
function dj_undef(_1,_2){
if(!_2){
_2=dj_global;
}
return (typeof _2[_1]=="undefined");
}
if(dj_undef("djConfig")){
var djConfig={};
}
var dojo;
if(dj_undef("dojo")){
dojo={};
}
dojo.version={major:0,minor:2,patch:1,flag:"",revision:Number("$Rev: 2555 $".match(/[0-9]+/)[0]),toString:function(){
with(dojo.version){
return major+"."+minor+"."+patch+flag+" ("+revision+")";
}
}};
dojo.evalObjPath=function(_3,_4){
if(typeof _3!="string"){
return dj_global;
}
if(_3.indexOf(".")==-1){
if((dj_undef(_3,dj_global))&&(_4)){
dj_global[_3]={};
}
return dj_global[_3];
}
var _5=_3.split(/\./);
var _6=dj_global;
for(var i=0;i<_5.length;++i){
if(!_4){
_6=_6[_5[i]];
if((typeof _6=="undefined")||(!_6)){
return _6;
}
}else{
if(dj_undef(_5[i],_6)){
_6[_5[i]]={};
}
_6=_6[_5[i]];
}
}
return _6;
};
dojo.errorToString=function(_8){
return ((!dj_undef("message",_8))?_8.message:(dj_undef("description",_8)?_8:_8.description));
};
dojo.raise=function(_9,_a){
if(_a){
_9=_9+": "+dojo.errorToString(_a);
}
var he=dojo.hostenv;
if((!dj_undef("hostenv",dojo))&&(!dj_undef("println",dojo.hostenv))){
dojo.hostenv.println("FATAL: "+_9);
}
throw Error(_9);
};
dj_throw=dj_rethrow=function(m,e){
dojo.deprecated("dj_throw and dj_rethrow deprecated, use dojo.raise instead");
dojo.raise(m,e);
};
dojo.debug=function(){
if(!djConfig.isDebug){
return;
}
var _e=arguments;
if(dj_undef("println",dojo.hostenv)){
dojo.raise("dojo.debug not available (yet?)");
}
var _f=dj_global["jum"]&&!dj_global["jum"].isBrowser;
var s=[(_f?"":"DEBUG: ")];
for(var i=0;i<_e.length;++i){
if(!false&&_e[i] instanceof Error){
var msg="["+_e[i].name+": "+dojo.errorToString(_e[i])+(_e[i].fileName?", file: "+_e[i].fileName:"")+(_e[i].lineNumber?", line: "+_e[i].lineNumber:"")+"]";
}else{
try{
var msg=String(_e[i]);
}
catch(e){
if(dojo.render.html.ie){
var msg="[ActiveXObject]";
}else{
var msg="[unknown]";
}
}
}
s.push(msg);
}
if(_f){
jum.debug(s.join(" "));
}else{
dojo.hostenv.println(s.join(" "));
}
};
dojo.debugShallow=function(obj){
if(!djConfig.isDebug){
return;
}
dojo.debug("------------------------------------------------------------");
dojo.debug("Object: "+obj);
for(i in obj){
dojo.debug(i+": "+obj[i]);
}
dojo.debug("------------------------------------------------------------");
};
var dj_debug=dojo.debug;
function dj_eval(s){
return dj_global.eval?dj_global.eval(s):eval(s);
}
dj_unimplemented=dojo.unimplemented=function(_15,_16){
var _17="'"+_15+"' not implemented";
if((!dj_undef(_16))&&(_16)){
_17+=" "+_16;
}
dojo.raise(_17);
};
dj_deprecated=dojo.deprecated=function(_18,_19,_1a){
var _1b="DEPRECATED: "+_18;
if(_19){
_1b+=" "+_19;
}
if(_1a){
_1b+=" -- will be removed in version: "+_1a;
}
dojo.debug(_1b);
};
dojo.inherits=function(_1c,_1d){
if(typeof _1d!="function"){
dojo.raise("superclass: "+_1d+" borken");
}
_1c.prototype=new _1d();
_1c.prototype.constructor=_1c;
_1c.superclass=_1d.prototype;
_1c["super"]=_1d.prototype;
};
dj_inherits=function(_1e,_1f){
dojo.deprecated("dj_inherits deprecated, use dojo.inherits instead");
dojo.inherits(_1e,_1f);
};
dojo.render=(function(){
function vscaffold(_20,_21){
var tmp={capable:false,support:{builtin:false,plugin:false},prefixes:_20};
for(var x in _21){
tmp[x]=false;
}
return tmp;
}
return {name:"",ver:dojo.version,os:{win:false,linux:false,osx:false},html:vscaffold(["html"],["ie","opera","khtml","safari","moz"]),svg:vscaffold(["svg"],["corel","adobe","batik"]),vml:vscaffold(["vml"],["ie"]),swf:vscaffold(["Swf","Flash","Mm"],["mm"]),swt:vscaffold(["Swt"],["ibm"])};
})();
dojo.hostenv=(function(){
var _24={isDebug:false,allowQueryConfig:false,baseScriptUri:"",baseRelativePath:"",libraryScriptUri:"",iePreventClobber:false,ieClobberMinimal:true,preventBackButtonFix:true,searchIds:[],parseWidgets:true};
if(typeof djConfig=="undefined"){
djConfig=_24;
}else{
for(var _25 in _24){
if(typeof djConfig[_25]=="undefined"){
djConfig[_25]=_24[_25];
}
}
}
var djc=djConfig;
function _def(obj,_28,def){
return (dj_undef(_28,obj)?def:obj[_28]);
}
return {name_:"(unset)",version_:"(unset)",pkgFileName:"__package__",loading_modules_:{},loaded_modules_:{},addedToLoadingCount:[],removedFromLoadingCount:[],inFlightCount:0,modulePrefixes_:{dojo:{name:"dojo",value:"src"}},setModulePrefix:function(_2a,_2b){
this.modulePrefixes_[_2a]={name:_2a,value:_2b};
},getModulePrefix:function(_2c){
var mp=this.modulePrefixes_;
if((mp[_2c])&&(mp[_2c]["name"])){
return mp[_2c].value;
}
return _2c;
},getTextStack:[],loadUriStack:[],loadedUris:[],post_load_:false,modulesLoadedListeners:[],getName:function(){
return this.name_;
},getVersion:function(){
return this.version_;
},getText:function(uri){
dojo.unimplemented("getText","uri="+uri);
},getLibraryScriptUri:function(){
dojo.unimplemented("getLibraryScriptUri","");
}};
})();
dojo.hostenv.getBaseScriptUri=function(){
if(djConfig.baseScriptUri.length){
return djConfig.baseScriptUri;
}
var uri=new String(djConfig.libraryScriptUri||djConfig.baseRelativePath);
if(!uri){
dojo.raise("Nothing returned by getLibraryScriptUri(): "+uri);
}
var _30=uri.lastIndexOf("/");
djConfig.baseScriptUri=djConfig.baseRelativePath;
return djConfig.baseScriptUri;
};
dojo.hostenv.setBaseScriptUri=function(uri){
djConfig.baseScriptUri=uri;
};
dojo.hostenv.loadPath=function(_32,_33,cb){
if((_32.charAt(0)=="/")||(_32.match(/^\w+:/))){
dojo.raise("relpath '"+_32+"'; must be relative");
}
var uri=this.getBaseScriptUri()+_32;
if(djConfig.cacheBust&&dojo.render.html.capable){
uri+="?"+djConfig.cacheBust.replace(/\W+/g,"");
}
try{
return ((!_33)?this.loadUri(uri,cb):this.loadUriAndCheck(uri,_33,cb));
}
catch(e){
dojo.debug(e);
return false;
}
};
dojo.hostenv.loadUri=function(uri,cb){
if(dojo.hostenv.loadedUris[uri]){
return;
}
var _38=this.getText(uri,null,true);
if(_38==null){
return 0;
}
var _39=dj_eval(_38);
return 1;
};
dojo.hostenv.loadUriAndCheck=function(uri,_3b,cb){
var ok=true;
try{
ok=this.loadUri(uri,cb);
}
catch(e){
dojo.debug("failed loading ",uri," with error: ",e);
}
return ((ok)&&(this.findModule(_3b,false)))?true:false;
};
dojo.loaded=function(){
};
dojo.hostenv.loaded=function(){
this.post_load_=true;
var mll=this.modulesLoadedListeners;
for(var x=0;x<mll.length;x++){
mll[x]();
}
dojo.loaded();
};
dojo.addOnLoad=function(obj,_41){
if(arguments.length==1){
dojo.hostenv.modulesLoadedListeners.push(obj);
}else{
if(arguments.length>1){
dojo.hostenv.modulesLoadedListeners.push(function(){
obj[_41]();
});
}
}
};
dojo.hostenv.modulesLoaded=function(){
if(this.post_load_){
return;
}
if((this.loadUriStack.length==0)&&(this.getTextStack.length==0)){
if(this.inFlightCount>0){
dojo.debug("files still in flight!");
return;
}
if(typeof setTimeout=="object"){
setTimeout("dojo.hostenv.loaded();",0);
}else{
dojo.hostenv.loaded();
}
}
};
dojo.hostenv.moduleLoaded=function(_42){
var _43=dojo.evalObjPath((_42.split(".").slice(0,-1)).join("."));
this.loaded_modules_[(new String(_42)).toLowerCase()]=_43;
};
dojo.hostenv._global_omit_module_check=false;
dojo.hostenv.loadModule=function(_44,_45,_46){
_46=this._global_omit_module_check||_46;
var _47=this.findModule(_44,false);
if(_47){
return _47;
}
if(dj_undef(_44,this.loading_modules_)){
this.addedToLoadingCount.push(_44);
}
this.loading_modules_[_44]=1;
var _48=_44.replace(/\./g,"/")+".js";
var _49=_44.split(".");
var _4a=_44.split(".");
for(var i=_49.length-1;i>0;i--){
var _4c=_49.slice(0,i).join(".");
var _4d=this.getModulePrefix(_4c);
if(_4d!=_4c){
_49.splice(0,i,_4d);
break;
}
}
var _4e=_49[_49.length-1];
if(_4e=="*"){
_44=(_4a.slice(0,-1)).join(".");
while(_49.length){
_49.pop();
_49.push(this.pkgFileName);
_48=_49.join("/")+".js";
if(_48.charAt(0)=="/"){
_48=_48.slice(1);
}
ok=this.loadPath(_48,((!_46)?_44:null));
if(ok){
break;
}
_49.pop();
}
}else{
_48=_49.join("/")+".js";
_44=_4a.join(".");
var ok=this.loadPath(_48,((!_46)?_44:null));
if((!ok)&&(!_45)){
_49.pop();
while(_49.length){
_48=_49.join("/")+".js";
ok=this.loadPath(_48,((!_46)?_44:null));
if(ok){
break;
}
_49.pop();
_48=_49.join("/")+"/"+this.pkgFileName+".js";
if(_48.charAt(0)=="/"){
_48=_48.slice(1);
}
ok=this.loadPath(_48,((!_46)?_44:null));
if(ok){
break;
}
}
}
if((!ok)&&(!_46)){
dojo.raise("Could not load '"+_44+"'; last tried '"+_48+"'");
}
}
if(!_46){
_47=this.findModule(_44,false);
if(!_47){
dojo.raise("symbol '"+_44+"' is not defined after loading '"+_48+"'");
}
}
return _47;
};
dojo.hostenv.startPackage=function(_50){
var _51=_50.split(/\./);
if(_51[_51.length-1]=="*"){
_51.pop();
}
return dojo.evalObjPath(_51.join("."),true);
};
dojo.hostenv.findModule=function(_52,_53){
if(this.loaded_modules_[(new String(_52)).toLowerCase()]){
return this.loaded_modules_[_52];
}
var _54=dojo.evalObjPath(_52);
if((typeof _54!=="undefined")&&(_54)){
return _54;
}
if(_53){
dojo.raise("no loaded module named '"+_52+"'");
}
return null;
};
if(typeof window=="undefined"){
dojo.raise("no window object");
}
(function(){
if(djConfig.allowQueryConfig){
var _55=document.location.toString();
var _56=_55.split("?",2);
if(_56.length>1){
var _57=_56[1];
var _58=_57.split("&");
for(var x in _58){
var sp=_58[x].split("=");
if((sp[0].length>9)&&(sp[0].substr(0,9)=="djConfig.")){
var opt=sp[0].substr(9);
try{
djConfig[opt]=eval(sp[1]);
}
catch(e){
djConfig[opt]=sp[1];
}
}
}
}
}
if(((djConfig["baseScriptUri"]=="")||(djConfig["baseRelativePath"]==""))&&(document&&document.getElementsByTagName)){
var _5c=document.getElementsByTagName("script");
var _5d=/(__package__|dojo)\.js(\?|$)/i;
for(var i=0;i<_5c.length;i++){
var src=_5c[i].getAttribute("src");
if(!src){
continue;
}
var m=src.match(_5d);
if(m){
root=src.substring(0,m.index);
if(!this["djConfig"]){
djConfig={};
}
if(djConfig["baseScriptUri"]==""){
djConfig["baseScriptUri"]=root;
}
if(djConfig["baseRelativePath"]==""){
djConfig["baseRelativePath"]=root;
}
break;
}
}
}
var dr=dojo.render;
var drh=dojo.render.html;
var dua=drh.UA=navigator.userAgent;
var dav=drh.AV=navigator.appVersion;
var t=true;
var f=false;
drh.capable=t;
drh.support.builtin=t;
dr.ver=parseFloat(drh.AV);
dr.os.mac=dav.indexOf("Macintosh")>=0;
dr.os.win=dav.indexOf("Windows")>=0;
dr.os.linux=dav.indexOf("X11")>=0;
drh.opera=dua.indexOf("Opera")>=0;
drh.khtml=(dav.indexOf("Konqueror")>=0)||(dav.indexOf("Safari")>=0);
drh.safari=dav.indexOf("Safari")>=0;
var _67=dua.indexOf("Gecko");
drh.mozilla=drh.moz=(_67>=0)&&(!drh.khtml);
if(drh.mozilla){
drh.geckoVersion=dua.substring(_67+6,_67+14);
}
drh.ie=(document.all)&&(!drh.opera);
drh.ie50=drh.ie&&dav.indexOf("MSIE 5.0")>=0;
drh.ie55=drh.ie&&dav.indexOf("MSIE 5.5")>=0;
drh.ie60=drh.ie&&dav.indexOf("MSIE 6.0")>=0;
dr.vml.capable=drh.ie;
dr.svg.capable=f;
dr.svg.support.plugin=f;
dr.svg.support.builtin=f;
dr.svg.adobe=f;
if(document.implementation&&document.implementation.hasFeature&&document.implementation.hasFeature("org.w3c.dom.svg","1.0")){
dr.svg.capable=t;
dr.svg.support.builtin=t;
dr.svg.support.plugin=f;
dr.svg.adobe=f;
}else{
if(navigator.mimeTypes&&navigator.mimeTypes.length>0){
var _68=navigator.mimeTypes["image/svg+xml"]||navigator.mimeTypes["image/svg"]||navigator.mimeTypes["image/svg-xml"];
if(_68){
dr.svg.adobe=_68&&_68.enabledPlugin&&_68.enabledPlugin.description&&(_68.enabledPlugin.description.indexOf("Adobe")>-1);
if(dr.svg.adobe){
dr.svg.capable=t;
dr.svg.support.plugin=t;
}
}
}else{
if(drh.ie&&dr.os.win){
var _68=f;
try{
var _69=new ActiveXObject("Adobe.SVGCtl");
_68=t;
}
catch(e){
}
if(_68){
dr.svg.capable=t;
dr.svg.support.plugin=t;
dr.svg.adobe=t;
}
}else{
dr.svg.capable=f;
dr.svg.support.plugin=f;
dr.svg.adobe=f;
}
}
}
})();
dojo.hostenv.startPackage("dojo.hostenv");
dojo.hostenv.name_="browser";
dojo.hostenv.searchIds=[];
var DJ_XMLHTTP_PROGIDS=["Msxml2.XMLHTTP","Microsoft.XMLHTTP","Msxml2.XMLHTTP.4.0"];
dojo.hostenv.getXmlhttpObject=function(){
var _6a=null;
var _6b=null;
try{
_6a=new XMLHttpRequest();
}
catch(e){
}
if(!_6a){
for(var i=0;i<3;++i){
var _6d=DJ_XMLHTTP_PROGIDS[i];
try{
_6a=new ActiveXObject(_6d);
}
catch(e){
_6b=e;
}
if(_6a){
DJ_XMLHTTP_PROGIDS=[_6d];
break;
}
}
}
if(!_6a){
return dojo.raise("XMLHTTP not available",_6b);
}
return _6a;
};
dojo.hostenv.getText=function(uri,_6f,_70){
var _71=this.getXmlhttpObject();
if(_6f){
_71.onreadystatechange=function(){
if((4==_71.readyState)&&(_71["status"])){
if(_71.status==200){
dojo.debug("LOADED URI: "+uri);
_6f(_71.responseText);
}
}
};
}
_71.open("GET",uri,_6f?true:false);
_71.send(null);
if(_6f){
return null;
}
return _71.responseText;
};
dojo.hostenv.defaultDebugContainerId="dojoDebug";
dojo.hostenv._println_buffer=[];
dojo.hostenv._println_safe=false;
dojo.hostenv.println=function(_72){
if(!dojo.hostenv._println_safe){
dojo.hostenv._println_buffer.push(_72);
}else{
try{
var _73=document.getElementById(djConfig.debugContainerId?djConfig.debugContainerId:dojo.hostenv.defaultDebugContainerId);
if(!_73){
_73=document.getElementsByTagName("body")[0]||document.body;
}
var div=document.createElement("div");
div.appendChild(document.createTextNode(_72));
_73.appendChild(div);
}
catch(e){
try{
document.write("<div>"+_72+"</div>");
}
catch(e2){
window.status=_72;
}
}
}
};
dojo.addOnLoad(function(){
dojo.hostenv._println_safe=true;
while(dojo.hostenv._println_buffer.length>0){
dojo.hostenv.println(dojo.hostenv._println_buffer.shift());
}
});
function dj_addNodeEvtHdlr(_75,_76,fp,_78){
var _79=_75["on"+_76]||function(){
};
_75["on"+_76]=function(){
fp.apply(_75,arguments);
_79.apply(_75,arguments);
};
return true;
}
dj_addNodeEvtHdlr(window,"load",function(){
if(dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
dojo.hostenv.modulesLoaded();
});
dojo.hostenv.makeWidgets=function(){
var _7a=[];
if(djConfig.searchIds&&djConfig.searchIds.length>0){
_7a=_7a.concat(djConfig.searchIds);
}
if(dojo.hostenv.searchIds&&dojo.hostenv.searchIds.length>0){
_7a=_7a.concat(dojo.hostenv.searchIds);
}
if((djConfig.parseWidgets)||(_7a.length>0)){
if(dojo.evalObjPath("dojo.widget.Parse")){
try{
var _7b=new dojo.xml.Parse();
if(_7a.length>0){
for(var x=0;x<_7a.length;x++){
var _7d=document.getElementById(_7a[x]);
if(!_7d){
continue;
}
var _7e=_7b.parseElement(_7d,null,true);
dojo.widget.getParser().createComponents(_7e);
}
}else{
if(djConfig.parseWidgets){
var _7e=_7b.parseElement(document.getElementsByTagName("body")[0]||document.body,null,true);
dojo.widget.getParser().createComponents(_7e);
}
}
}
catch(e){
dojo.debug("auto-build-widgets error:",e);
}
}
}
};
dojo.hostenv.modulesLoadedListeners.push(function(){
if(!dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
});
try{
if(!window["djConfig"]||!window.djConfig["preventBackButtonFix"]){
document.write("<iframe style='border: 0px; width: 1px; height: 1px; position: absolute; bottom: 0px; right: 0px; visibility: visible;' name='djhistory' id='djhistory' src='"+(dojo.hostenv.getBaseScriptUri()+"iframe_history.html")+"'></iframe>");
}
if(dojo.render.html.ie){
document.write("<style>v:*{ behavior:url(#default#VML); }</style>");
document.write("<xml:namespace ns=\"urn:schemas-microsoft-com:vml\" prefix=\"v\"/>");
}
}
catch(e){
}
dojo.hostenv.writeIncludes=function(){
};
dojo.hostenv.byId=dojo.byId=function(id,doc){
if(typeof id=="string"||id instanceof String){
if(!doc){
doc=document;
}
return doc.getElementById(id);
}
return id;
};
dojo.hostenv.byIdArray=dojo.byIdArray=function(){
var ids=[];
for(var i=0;i<arguments.length;i++){
if((arguments[i] instanceof Array)||(typeof arguments[i]=="array")){
for(var j=0;j<arguments[i].length;j++){
ids=ids.concat(dojo.hostenv.byIdArray(arguments[i][j]));
}
}else{
ids.push(dojo.hostenv.byId(arguments[i]));
}
}
return ids;
};
dojo.hostenv.conditionalLoadModule=function(_84){
var _85=_84["common"]||[];
var _86=(_84[dojo.hostenv.name_])?_85.concat(_84[dojo.hostenv.name_]||[]):_85.concat(_84["default"]||[]);
for(var x=0;x<_86.length;x++){
var _88=_86[x];
if(_88.constructor==Array){
dojo.hostenv.loadModule.apply(dojo.hostenv,_88);
}else{
dojo.hostenv.loadModule(_88);
}
}
};
dojo.hostenv.require=dojo.hostenv.loadModule;
dojo.require=function(){
dojo.hostenv.loadModule.apply(dojo.hostenv,arguments);
};
dojo.requireAfter=dojo.require;
dojo.requireIf=function(){
if((arguments[0]===true)||(arguments[0]=="common")||(dojo.render[arguments[0]].capable)){
var _89=[];
for(var i=1;i<arguments.length;i++){
_89.push(arguments[i]);
}
dojo.require.apply(dojo,_89);
}
};
dojo.requireAfterIf=dojo.requireIf;
dojo.conditionalRequire=dojo.requireIf;
dojo.kwCompoundRequire=function(){
dojo.hostenv.conditionalLoadModule.apply(dojo.hostenv,arguments);
};
dojo.hostenv.provide=dojo.hostenv.startPackage;
dojo.provide=function(){
return dojo.hostenv.startPackage.apply(dojo.hostenv,arguments);
};
dojo.setModulePrefix=function(_8b,_8c){
return dojo.hostenv.setModulePrefix(_8b,_8c);
};
dojo.profile={start:function(){
},end:function(){
},dump:function(){
}};
dojo.exists=function(obj,_8e){
var p=_8e.split(".");
for(var i=0;i<p.length;i++){
if(!(obj[p[i]])){
return false;
}
obj=obj[p[i]];
}
return true;
};
dojo.provide("dojo.lang");
dojo.provide("dojo.AdapterRegistry");
dojo.provide("dojo.lang.Lang");
dojo.lang.mixin=function(obj,_92,_93){
if(typeof _93!="object"){
_93={};
}
for(var x in _92){
if(typeof _93[x]=="undefined"||_93[x]!=_92[x]){
obj[x]=_92[x];
}
}
return obj;
};
dojo.lang.extend=function(_95,_96){
this.mixin(_95.prototype,_96);
};
dojo.lang.extendPrototype=function(obj,_98){
this.extend(obj.constructor,_98);
};
dojo.lang.anonCtr=0;
dojo.lang.anon={};
dojo.lang.nameAnonFunc=function(_99,_9a){
var nso=(_9a||dojo.lang.anon);
if((dj_global["djConfig"])&&(djConfig["slowAnonFuncLookups"]==true)){
for(var x in nso){
if(nso[x]===_99){
return x;
}
}
}
var ret="__"+dojo.lang.anonCtr++;
while(typeof nso[ret]!="undefined"){
ret="__"+dojo.lang.anonCtr++;
}
nso[ret]=_99;
return ret;
};
dojo.lang.hitch=function(_9e,_9f){
if(dojo.lang.isString(_9f)){
var fcn=_9e[_9f];
}else{
var fcn=_9f;
}
return function(){
return fcn.apply(_9e,arguments);
};
};
dojo.lang.setTimeout=function(_a1,_a2){
var _a3=window,argsStart=2;
if(!dojo.lang.isFunction(_a1)){
_a3=_a1;
_a1=_a2;
_a2=arguments[2];
argsStart++;
}
if(dojo.lang.isString(_a1)){
_a1=_a3[_a1];
}
var _a4=[];
for(var i=argsStart;i<arguments.length;i++){
_a4.push(arguments[i]);
}
return setTimeout(function(){
_a1.apply(_a3,_a4);
},_a2);
};
dojo.lang.isObject=function(wh){
return typeof wh=="object"||dojo.lang.isArray(wh)||dojo.lang.isFunction(wh);
};
dojo.lang.isArray=function(wh){
return (wh instanceof Array||typeof wh=="array");
};
dojo.lang.isArrayLike=function(wh){
if(dojo.lang.isString(wh)){
return false;
}
if(dojo.lang.isArray(wh)){
return true;
}
if(dojo.lang.isNumber(wh.length)&&isFinite(wh)){
return true;
}
return false;
};
dojo.lang.isFunction=function(wh){
return (wh instanceof Function||typeof wh=="function");
};
dojo.lang.isString=function(wh){
return (wh instanceof String||typeof wh=="string");
};
dojo.lang.isAlien=function(wh){
return !dojo.lang.isFunction()&&/\{\s*\[native code\]\s*\}/.test(String(wh));
};
dojo.lang.isBoolean=function(wh){
return (wh instanceof Boolean||typeof wh=="boolean");
};
dojo.lang.isNumber=function(wh){
return (wh instanceof Number||typeof wh=="number");
};
dojo.lang.isUndefined=function(wh){
return ((wh==undefined)&&(typeof wh=="undefined"));
};
dojo.lang.whatAmI=function(wh){
try{
if(dojo.lang.isArray(wh)){
return "array";
}
if(dojo.lang.isFunction(wh)){
return "function";
}
if(dojo.lang.isString(wh)){
return "string";
}
if(dojo.lang.isNumber(wh)){
return "number";
}
if(dojo.lang.isBoolean(wh)){
return "boolean";
}
if(dojo.lang.isAlien(wh)){
return "alien";
}
if(dojo.lang.isUndefined(wh)){
return "undefined";
}
for(var _b0 in dojo.lang.whatAmI.custom){
if(dojo.lang.whatAmI.custom[_b0](wh)){
return _b0;
}
}
if(dojo.lang.isObject(wh)){
return "object";
}
}
catch(E){
}
return "unknown";
};
dojo.lang.whatAmI.custom={};
dojo.lang.find=function(arr,val,_b3){
if(!dojo.lang.isArray(arr)&&dojo.lang.isArray(val)){
var a=arr;
arr=val;
val=a;
}
var _b5=dojo.lang.isString(arr);
if(_b5){
arr=arr.split("");
}
if(_b3){
for(var i=0;i<arr.length;++i){
if(arr[i]===val){
return i;
}
}
}else{
for(var i=0;i<arr.length;++i){
if(arr[i]==val){
return i;
}
}
}
return -1;
};
dojo.lang.indexOf=dojo.lang.find;
dojo.lang.findLast=function(arr,val,_b9){
if(!dojo.lang.isArray(arr)&&dojo.lang.isArray(val)){
var a=arr;
arr=val;
val=a;
}
var _bb=dojo.lang.isString(arr);
if(_bb){
arr=arr.split("");
}
if(_b9){
for(var i=arr.length-1;i>=0;i--){
if(arr[i]===val){
return i;
}
}
}else{
for(var i=arr.length-1;i>=0;i--){
if(arr[i]==val){
return i;
}
}
}
return -1;
};
dojo.lang.lastIndexOf=dojo.lang.findLast;
dojo.lang.inArray=function(arr,val){
return dojo.lang.find(arr,val)>-1;
};
dojo.lang.getNameInObj=function(ns,_c0){
if(!ns){
ns=dj_global;
}
for(var x in ns){
if(ns[x]===_c0){
return new String(x);
}
}
return null;
};
dojo.lang.has=function(obj,_c3){
return (typeof obj[_c3]!=="undefined");
};
dojo.lang.isEmpty=function(obj){
if(dojo.lang.isObject(obj)){
var tmp={};
var _c6=0;
for(var x in obj){
if(obj[x]&&(!tmp[x])){
_c6++;
break;
}
}
return (_c6==0);
}else{
if(dojo.lang.isArray(obj)||dojo.lang.isString(obj)){
return obj.length==0;
}
}
};
dojo.lang.forEach=function(arr,_c9,_ca){
var _cb=dojo.lang.isString(arr);
if(_cb){
arr=arr.split("");
}
var il=arr.length;
for(var i=0;i<((_ca)?il:arr.length);i++){
if(_c9(arr[i],i,arr)=="break"){
break;
}
}
};
dojo.lang.map=function(arr,obj,_d0){
var _d1=dojo.lang.isString(arr);
if(_d1){
arr=arr.split("");
}
if(dojo.lang.isFunction(obj)&&(!_d0)){
_d0=obj;
obj=dj_global;
}else{
if(dojo.lang.isFunction(obj)&&_d0){
var _d2=obj;
obj=_d0;
_d0=_d2;
}
}
if(Array.map){
var _d3=Array.map(arr,_d0,obj);
}else{
var _d3=[];
for(var i=0;i<arr.length;++i){
_d3.push(_d0.call(obj,arr[i]));
}
}
if(_d1){
return _d3.join("");
}else{
return _d3;
}
};
dojo.lang.tryThese=function(){
for(var x=0;x<arguments.length;x++){
try{
if(typeof arguments[x]=="function"){
var ret=(arguments[x]());
if(ret){
return ret;
}
}
}
catch(e){
dojo.debug(e);
}
}
};
dojo.lang.delayThese=function(_d7,cb,_d9,_da){
if(!_d7.length){
if(typeof _da=="function"){
_da();
}
return;
}
if((typeof _d9=="undefined")&&(typeof cb=="number")){
_d9=cb;
cb=function(){
};
}else{
if(!cb){
cb=function(){
};
if(!_d9){
_d9=0;
}
}
}
setTimeout(function(){
(_d7.shift())();
cb();
dojo.lang.delayThese(_d7,cb,_d9,_da);
},_d9);
};
dojo.lang.shallowCopy=function(obj){
var ret={},key;
for(key in obj){
if(dojo.lang.isUndefined(ret[key])){
ret[key]=obj[key];
}
}
return ret;
};
dojo.lang.every=function(arr,_de,_df){
var _e0=dojo.lang.isString(arr);
if(_e0){
arr=arr.split("");
}
if(Array.every){
return Array.every(arr,_de,_df);
}else{
if(!_df){
if(arguments.length>=3){
dojo.raise("thisObject doesn't exist!");
}
_df=dj_global;
}
for(var i=0;i<arr.length;i++){
if(!_de.call(_df,arr[i],i,arr)){
return false;
}
}
return true;
}
};
dojo.lang.some=function(arr,_e3,_e4){
var _e5=dojo.lang.isString(arr);
if(_e5){
arr=arr.split("");
}
if(Array.some){
return Array.some(arr,_e3,_e4);
}else{
if(!_e4){
if(arguments.length>=3){
dojo.raise("thisObject doesn't exist!");
}
_e4=dj_global;
}
for(var i=0;i<arr.length;i++){
if(_e3.call(_e4,arr[i],i,arr)){
return true;
}
}
return false;
}
};
dojo.lang.filter=function(arr,_e8,_e9){
var _ea=dojo.lang.isString(arr);
if(_ea){
arr=arr.split("");
}
if(Array.filter){
var _eb=Array.filter(arr,_e8,_e9);
}else{
if(!_e9){
if(arguments.length>=3){
dojo.raise("thisObject doesn't exist!");
}
_e9=dj_global;
}
var _eb=[];
for(var i=0;i<arr.length;i++){
if(_e8.call(_e9,arr[i],i,arr)){
_eb.push(arr[i]);
}
}
}
if(_ea){
return _eb.join("");
}else{
return _eb;
}
};
dojo.AdapterRegistry=function(){
this.pairs=[];
};
dojo.lang.extend(dojo.AdapterRegistry,{register:function(_ed,_ee,_ef,_f0){
if(_f0){
this.pairs.unshift([_ed,_ee,_ef]);
}else{
this.pairs.push([_ed,_ee,_ef]);
}
},match:function(){
for(var i=0;i<this.pairs.length;i++){
var _f2=this.pairs[i];
if(_f2[1].apply(this,arguments)){
return _f2[2].apply(this,arguments);
}
}
dojo.raise("No match found");
},unregister:function(_f3){
for(var i=0;i<this.pairs.length;i++){
var _f5=this.pairs[i];
if(_f5[0]==_f3){
this.pairs.splice(i,1);
return true;
}
}
return false;
}});
dojo.lang.reprRegistry=new dojo.AdapterRegistry();
dojo.lang.registerRepr=function(_f6,_f7,_f8,_f9){
dojo.lang.reprRegistry.register(_f6,_f7,_f8,_f9);
};
dojo.lang.repr=function(obj){
if(typeof (obj)=="undefined"){
return "undefined";
}else{
if(obj===null){
return "null";
}
}
try{
if(typeof (obj["__repr__"])=="function"){
return obj["__repr__"]();
}else{
if((typeof (obj["repr"])=="function")&&(obj.repr!=arguments.callee)){
return obj["repr"]();
}
}
return dojo.lang.reprRegistry.match(obj);
}
catch(e){
if(typeof (obj.NAME)=="string"&&(obj.toString==Function.prototype.toString||obj.toString==Object.prototype.toString)){
return o.NAME;
}
}
if(typeof (obj)=="function"){
obj=(obj+"").replace(/^\s+/,"");
var idx=obj.indexOf("{");
if(idx!=-1){
obj=obj.substr(0,idx)+"{...}";
}
}
return obj+"";
};
dojo.lang.reprArrayLike=function(arr){
try{
var na=dojo.lang.map(arr,dojo.lang.repr);
return "["+na.join(", ")+"]";
}
catch(e){
}
};
dojo.lang.reprString=function(str){
return ("\""+str.replace(/(["\\])/g,"\\$1")+"\"").replace(/[\f]/g,"\\f").replace(/[\b]/g,"\\b").replace(/[\n]/g,"\\n").replace(/[\t]/g,"\\t").replace(/[\r]/g,"\\r");
};
dojo.lang.reprNumber=function(num){
return num+"";
};
(function(){
var m=dojo.lang;
m.registerRepr("arrayLike",m.isArrayLike,m.reprArrayLike);
m.registerRepr("string",m.isString,m.reprString);
m.registerRepr("numbers",m.isNumber,m.reprNumber);
m.registerRepr("boolean",m.isBoolean,m.reprNumber);
})();
dojo.lang.unnest=function(){
var out=[];
for(var i=0;i<arguments.length;i++){
if(dojo.lang.isArrayLike(arguments[i])){
var add=dojo.lang.unnest.apply(this,arguments[i]);
out=out.concat(add);
}else{
out.push(arguments[i]);
}
}
return out;
};
dojo.provide("dojo.string");
dojo.require("dojo.lang");
dojo.string.trim=function(str,wh){
if(!dojo.lang.isString(str)){
return str;
}
if(!str.length){
return str;
}
if(wh>0){
return str.replace(/^\s+/,"");
}else{
if(wh<0){
return str.replace(/\s+$/,"");
}else{
return str.replace(/^\s+|\s+$/g,"");
}
}
};
dojo.string.trimStart=function(str){
return dojo.string.trim(str,1);
};
dojo.string.trimEnd=function(str){
return dojo.string.trim(str,-1);
};
dojo.string.paramString=function(str,_109,_10a){
for(var name in _109){
var re=new RegExp("\\%\\{"+name+"\\}","g");
str=str.replace(re,_109[name]);
}
if(_10a){
str=str.replace(/%\{([^\}\s]+)\}/g,"");
}
return str;
};
dojo.string.capitalize=function(str){
if(!dojo.lang.isString(str)){
return "";
}
if(arguments.length==0){
str=this;
}
var _10e=str.split(" ");
var _10f="";
var len=_10e.length;
for(var i=0;i<len;i++){
var word=_10e[i];
word=word.charAt(0).toUpperCase()+word.substring(1,word.length);
_10f+=word;
if(i<len-1){
_10f+=" ";
}
}
return new String(_10f);
};
dojo.string.isBlank=function(str){
if(!dojo.lang.isString(str)){
return true;
}
return (dojo.string.trim(str).length==0);
};
dojo.string.encodeAscii=function(str){
if(!dojo.lang.isString(str)){
return str;
}
var ret="";
var _116=escape(str);
var _117,re=/%u([0-9A-F]{4})/i;
while((_117=_116.match(re))){
var num=Number("0x"+_117[1]);
var _119=escape("&#"+num+";");
ret+=_116.substring(0,_117.index)+_119;
_116=_116.substring(_117.index+_117[0].length);
}
ret+=_116.replace(/\+/g,"%2B");
return ret;
};
dojo.string.summary=function(str,len){
if(!len||str.length<=len){
return str;
}else{
return str.substring(0,len).replace(/\.+$/,"")+"...";
}
};
dojo.string.escape=function(type,str){
switch(type.toLowerCase()){
case "xml":
case "html":
case "xhtml":
return dojo.string.escapeXml(str);
case "sql":
return dojo.string.escapeSql(str);
case "regexp":
case "regex":
return dojo.string.escapeRegExp(str);
case "javascript":
case "jscript":
case "js":
return dojo.string.escapeJavaScript(str);
case "ascii":
return dojo.string.encodeAscii(str);
default:
return str;
}
};
dojo.string.escapeXml=function(str){
return str.replace(/&/gm,"&amp;").replace(/</gm,"&lt;").replace(/>/gm,"&gt;").replace(/"/gm,"&quot;").replace(/'/gm,"&#39;");
};
dojo.string.escapeSql=function(str){
return str.replace(/'/gm,"''");
};
dojo.string.escapeRegExp=function(str){
return str.replace(/\\/gm,"\\\\").replace(/([\f\b\n\t\r])/gm,"\\$1");
};
dojo.string.escapeJavaScript=function(str){
return str.replace(/(["'\f\b\n\t\r])/gm,"\\$1");
};
dojo.string.repeat=function(str,_123,_124){
var out="";
for(var i=0;i<_123;i++){
out+=str;
if(_124&&i<_123-1){
out+=_124;
}
}
return out;
};
dojo.string.endsWith=function(str,end,_129){
if(_129){
str=str.toLowerCase();
end=end.toLowerCase();
}
return str.lastIndexOf(end)==str.length-end.length;
};
dojo.string.endsWithAny=function(str){
for(var i=1;i<arguments.length;i++){
if(dojo.string.endsWith(str,arguments[i])){
return true;
}
}
return false;
};
dojo.string.startsWith=function(str,_12d,_12e){
if(_12e){
str=str.toLowerCase();
_12d=_12d.toLowerCase();
}
return str.indexOf(_12d)==0;
};
dojo.string.startsWithAny=function(str){
for(var i=1;i<arguments.length;i++){
if(dojo.string.startsWith(str,arguments[i])){
return true;
}
}
return false;
};
dojo.string.has=function(str){
for(var i=1;i<arguments.length;i++){
if(str.indexOf(arguments[i]>-1)){
return true;
}
}
return false;
};
dojo.string.pad=function(str,len,c,dir){
var out=String(str);
if(!c){
c="0";
}
if(!dir){
dir=1;
}
while(out.length<len){
if(dir>0){
out=c+out;
}else{
out+=c;
}
}
return out;
};
dojo.string.padLeft=function(str,len,c){
return dojo.string.pad(str,len,c,1);
};
dojo.string.padRight=function(str,len,c){
return dojo.string.pad(str,len,c,-1);
};
dojo.string.addToPrototype=function(){
for(var _13e in dojo.string){
if(dojo.lang.isFunction(dojo.string[_13e])){
var func=(function(){
var meth=_13e;
switch(meth){
case "addToPrototype":
return null;
break;
case "escape":
return function(type){
return dojo.string.escape(type,this);
};
break;
default:
return function(){
var args=[this];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
dojo.debug(args);
return dojo.string[meth].apply(dojo.string,args);
};
}
})();
if(func){
String.prototype[_13e]=func;
}
}
}
};
dojo.provide("dojo.io.IO");
dojo.require("dojo.string");
dojo.io.transports=[];
dojo.io.hdlrFuncNames=["load","error"];
dojo.io.Request=function(url,_145,_146,_147){
if((arguments.length==1)&&(arguments[0].constructor==Object)){
this.fromKwArgs(arguments[0]);
}else{
this.url=url;
if(_145){
this.mimetype=_145;
}
if(_146){
this.transport=_146;
}
if(arguments.length>=4){
this.changeUrl=_147;
}
}
};
dojo.lang.extend(dojo.io.Request,{url:"",mimetype:"text/plain",method:"GET",content:undefined,transport:undefined,changeUrl:undefined,formNode:undefined,sync:false,bindSuccess:false,useCache:false,preventCache:false,load:function(type,data,evt){
},error:function(type,_14c){
},handle:function(){
},abort:function(){
},fromKwArgs:function(_14d){
if(_14d["url"]){
_14d.url=_14d.url.toString();
}
if(!_14d["method"]&&_14d["formNode"]&&_14d["formNode"].method){
_14d.method=_14d["formNode"].method;
}
if(!_14d["handle"]&&_14d["handler"]){
_14d.handle=_14d.handler;
}
if(!_14d["load"]&&_14d["loaded"]){
_14d.load=_14d.loaded;
}
if(!_14d["changeUrl"]&&_14d["changeURL"]){
_14d.changeUrl=_14d.changeURL;
}
if(!_14d["encoding"]){
if(!dojo.lang.isUndefined(djConfig["bindEncoding"])){
_14d.encoding=djConfig.bindEncoding;
}else{
_14d.encoding="";
}
}
var _14e=dojo.lang.isFunction;
for(var x=0;x<dojo.io.hdlrFuncNames.length;x++){
var fn=dojo.io.hdlrFuncNames[x];
if(_14e(_14d[fn])){
continue;
}
if(_14e(_14d["handle"])){
_14d[fn]=_14d.handle;
}
}
dojo.lang.mixin(this,_14d);
}});
dojo.io.Error=function(msg,type,num){
this.message=msg;
this.type=type||"unknown";
this.number=num||0;
};
dojo.io.transports.addTransport=function(name){
this.push(name);
this[name]=dojo.io[name];
};
dojo.io.bind=function(_155){
if(!(_155 instanceof dojo.io.Request)){
try{
_155=new dojo.io.Request(_155);
}
catch(e){
dojo.debug(e);
}
}
var _156="";
if(_155["transport"]){
_156=_155["transport"];
if(!this[_156]){
return _155;
}
}else{
for(var x=0;x<dojo.io.transports.length;x++){
var tmp=dojo.io.transports[x];
if((this[tmp])&&(this[tmp].canHandle(_155))){
_156=tmp;
}
}
if(_156==""){
return _155;
}
}
this[_156].bind(_155);
_155.bindSuccess=true;
return _155;
};
dojo.io.queueBind=function(_159){
if(!(_159 instanceof dojo.io.Request)){
try{
_159=new dojo.io.Request(_159);
}
catch(e){
dojo.debug(e);
}
}
var _15a=_159.load;
_159.load=function(){
dojo.io._queueBindInFlight=false;
var ret=_15a.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
var _15c=_159.error;
_159.error=function(){
dojo.io._queueBindInFlight=false;
var ret=_15c.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
dojo.io._bindQueue.push(_159);
dojo.io._dispatchNextQueueBind();
return _159;
};
dojo.io._dispatchNextQueueBind=function(){
if(!dojo.io._queueBindInFlight){
dojo.io._queueBindInFlight=true;
dojo.io.bind(dojo.io._bindQueue.shift());
}
};
dojo.io._bindQueue=[];
dojo.io._queueBindInFlight=false;
dojo.io.argsFromMap=function(map,_15f){
var _160=new Object();
var _161="";
var enc=/utf/i.test(_15f||"")?encodeURIComponent:dojo.string.encodeAscii;
for(var x in map){
if(!_160[x]){
_161+=enc(x)+"="+enc(map[x])+"&";
}
}
return _161;
};
dojo.provide("dojo.dom");
dojo.require("dojo.lang");
dojo.dom.ELEMENT_NODE=1;
dojo.dom.ATTRIBUTE_NODE=2;
dojo.dom.TEXT_NODE=3;
dojo.dom.CDATA_SECTION_NODE=4;
dojo.dom.ENTITY_REFERENCE_NODE=5;
dojo.dom.ENTITY_NODE=6;
dojo.dom.PROCESSING_INSTRUCTION_NODE=7;
dojo.dom.COMMENT_NODE=8;
dojo.dom.DOCUMENT_NODE=9;
dojo.dom.DOCUMENT_TYPE_NODE=10;
dojo.dom.DOCUMENT_FRAGMENT_NODE=11;
dojo.dom.NOTATION_NODE=12;
dojo.dom.dojoml="http://www.dojotoolkit.org/2004/dojoml";
dojo.dom.xmlns={svg:"http://www.w3.org/2000/svg",smil:"http://www.w3.org/2001/SMIL20/",mml:"http://www.w3.org/1998/Math/MathML",cml:"http://www.xml-cml.org",xlink:"http://www.w3.org/1999/xlink",xhtml:"http://www.w3.org/1999/xhtml",xul:"http://www.mozilla.org/keymaster/gatekeeper/there.is.only.xul",xbl:"http://www.mozilla.org/xbl",fo:"http://www.w3.org/1999/XSL/Format",xsl:"http://www.w3.org/1999/XSL/Transform",xslt:"http://www.w3.org/1999/XSL/Transform",xi:"http://www.w3.org/2001/XInclude",xforms:"http://www.w3.org/2002/01/xforms",saxon:"http://icl.com/saxon",xalan:"http://xml.apache.org/xslt",xsd:"http://www.w3.org/2001/XMLSchema",dt:"http://www.w3.org/2001/XMLSchema-datatypes",xsi:"http://www.w3.org/2001/XMLSchema-instance",rdf:"http://www.w3.org/1999/02/22-rdf-syntax-ns#",rdfs:"http://www.w3.org/2000/01/rdf-schema#",dc:"http://purl.org/dc/elements/1.1/",dcq:"http://purl.org/dc/qualifiers/1.0","soap-env":"http://schemas.xmlsoap.org/soap/envelope/",wsdl:"http://schemas.xmlsoap.org/wsdl/",AdobeExtensions:"http://ns.adobe.com/AdobeSVGViewerExtensions/3.0/"};
dojo.dom.isNode=dojo.lang.isDomNode=function(wh){
if(typeof Element=="object"){
try{
return wh instanceof Element;
}
catch(E){
}
}else{
return wh&&!isNaN(wh.nodeType);
}
};
dojo.lang.whatAmI.custom["node"]=dojo.dom.isNode;
dojo.dom.getTagName=function(node){
var _166=node.tagName;
if(_166.substr(0,5).toLowerCase()!="dojo:"){
if(_166.substr(0,4).toLowerCase()=="dojo"){
return "dojo:"+_166.substring(4).toLowerCase();
}
var djt=node.getAttribute("dojoType")||node.getAttribute("dojotype");
if(djt){
return "dojo:"+djt.toLowerCase();
}
if((node.getAttributeNS)&&(node.getAttributeNS(this.dojoml,"type"))){
return "dojo:"+node.getAttributeNS(this.dojoml,"type").toLowerCase();
}
try{
djt=node.getAttribute("dojo:type");
}
catch(e){
}
if(djt){
return "dojo:"+djt.toLowerCase();
}
if((!dj_global["djConfig"])||(!djConfig["ignoreClassNames"])){
var _168=node.className||node.getAttribute("class");
if((_168)&&(_168.indexOf("dojo-")!=-1)){
var _169=_168.split(" ");
for(var x=0;x<_169.length;x++){
if((_169[x].length>5)&&(_169[x].indexOf("dojo-")>=0)){
return "dojo:"+_169[x].substr(5).toLowerCase();
}
}
}
}
}
return _166.toLowerCase();
};
dojo.dom.getUniqueId=function(){
do{
var id="dj_unique_"+(++arguments.callee._idIncrement);
}while(document.getElementById(id));
return id;
};
dojo.dom.getUniqueId._idIncrement=0;
dojo.dom.firstElement=dojo.dom.getFirstChildElement=function(_16c,_16d){
var node=_16c.firstChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.nextSibling;
}
if(_16d&&node&&node.tagName&&node.tagName.toLowerCase()!=_16d.toLowerCase()){
node=dojo.dom.nextElement(node,_16d);
}
return node;
};
dojo.dom.lastElement=dojo.dom.getLastChildElement=function(_16f,_170){
var node=_16f.lastChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.previousSibling;
}
if(_170&&node&&node.tagName&&node.tagName.toLowerCase()!=_170.toLowerCase()){
node=dojo.dom.prevElement(node,_170);
}
return node;
};
dojo.dom.nextElement=dojo.dom.getNextSiblingElement=function(node,_173){
if(!node){
return null;
}
do{
node=node.nextSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_173&&_173.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.nextElement(node,_173);
}
return node;
};
dojo.dom.prevElement=dojo.dom.getPreviousSiblingElement=function(node,_175){
if(!node){
return null;
}
if(_175){
_175=_175.toLowerCase();
}
do{
node=node.previousSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_175&&_175.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.prevElement(node,_175);
}
return node;
};
dojo.dom.moveChildren=function(_176,_177,trim){
var _179=0;
if(trim){
while(_176.hasChildNodes()&&_176.firstChild.nodeType==dojo.dom.TEXT_NODE){
_176.removeChild(_176.firstChild);
}
while(_176.hasChildNodes()&&_176.lastChild.nodeType==dojo.dom.TEXT_NODE){
_176.removeChild(_176.lastChild);
}
}
while(_176.hasChildNodes()){
_177.appendChild(_176.firstChild);
_179++;
}
return _179;
};
dojo.dom.copyChildren=function(_17a,_17b,trim){
var _17d=_17a.cloneNode(true);
return this.moveChildren(_17d,_17b,trim);
};
dojo.dom.removeChildren=function(node){
var _17f=node.childNodes.length;
while(node.hasChildNodes()){
node.removeChild(node.firstChild);
}
return _17f;
};
dojo.dom.replaceChildren=function(node,_181){
dojo.dom.removeChildren(node);
node.appendChild(_181);
};
dojo.dom.removeNode=function(node){
if(node&&node.parentNode){
return node.parentNode.removeChild(node);
}
};
dojo.dom.getAncestors=function(node,_184,_185){
var _186=[];
var _187=dojo.lang.isFunction(_184);
while(node){
if(!_187||_184(node)){
_186.push(node);
}
if(_185&&_186.length>0){
return _186[0];
}
node=node.parentNode;
}
if(_185){
return null;
}
return _186;
};
dojo.dom.getAncestorsByTag=function(node,tag,_18a){
tag=tag.toLowerCase();
return dojo.dom.getAncestors(node,function(el){
return ((el.tagName)&&(el.tagName.toLowerCase()==tag));
},_18a);
};
dojo.dom.getFirstAncestorByTag=function(node,tag){
return dojo.dom.getAncestorsByTag(node,tag,true);
};
dojo.dom.isDescendantOf=function(node,_18f,_190){
if(_190&&node){
node=node.parentNode;
}
while(node){
if(node==_18f){
return true;
}
node=node.parentNode;
}
return false;
};
dojo.dom.innerXML=function(node){
if(node.innerXML){
return node.innerXML;
}else{
if(typeof XMLSerializer!="undefined"){
return (new XMLSerializer()).serializeToString(node);
}
}
};
dojo.dom.createDocumentFromText=function(str,_193){
if(!_193){
_193="text/xml";
}
if(typeof DOMParser!="undefined"){
var _194=new DOMParser();
return _194.parseFromString(str,_193);
}else{
if(typeof ActiveXObject!="undefined"){
var _195=new ActiveXObject("Microsoft.XMLDOM");
if(_195){
_195.async=false;
_195.loadXML(str);
return _195;
}else{
dojo.debug("toXml didn't work?");
}
}else{
if(document.createElement){
var tmp=document.createElement("xml");
tmp.innerHTML=str;
if(document.implementation&&document.implementation.createDocument){
var _197=document.implementation.createDocument("foo","",null);
for(var i=0;i<tmp.childNodes.length;i++){
_197.importNode(tmp.childNodes.item(i),true);
}
return _197;
}
return tmp.document&&tmp.document.firstChild?tmp.document.firstChild:tmp;
}
}
}
return null;
};
dojo.dom.insertBefore=function(node,ref,_19b){
if(_19b!=true&&(node===ref||node.nextSibling===ref)){
return false;
}
var _19c=ref.parentNode;
_19c.insertBefore(node,ref);
return true;
};
dojo.dom.insertAfter=function(node,ref,_19f){
var pn=ref.parentNode;
if(ref==pn.lastChild){
if((_19f!=true)&&(node===ref)){
return false;
}
pn.appendChild(node);
}else{
return this.insertBefore(node,ref.nextSibling,_19f);
}
return true;
};
dojo.dom.insertAtPosition=function(node,ref,_1a3){
if((!node)||(!ref)||(!_1a3)){
return false;
}
switch(_1a3.toLowerCase()){
case "before":
return dojo.dom.insertBefore(node,ref);
case "after":
return dojo.dom.insertAfter(node,ref);
case "first":
if(ref.firstChild){
return dojo.dom.insertBefore(node,ref.firstChild);
}else{
ref.appendChild(node);
return true;
}
break;
default:
ref.appendChild(node);
return true;
}
};
dojo.dom.insertAtIndex=function(node,_1a5,_1a6){
var _1a7=_1a5.childNodes;
if(!_1a7.length){
_1a5.appendChild(node);
return true;
}
var _1a8=null;
for(var i=0;i<_1a7.length;i++){
var _1aa=_1a7.item(i)["getAttribute"]?parseInt(_1a7.item(i).getAttribute("dojoinsertionindex")):-1;
if(_1aa<_1a6){
_1a8=_1a7.item(i);
}
}
if(_1a8){
return dojo.dom.insertAfter(node,_1a8);
}else{
return dojo.dom.insertBefore(node,_1a7.item(0));
}
};
dojo.dom.textContent=function(node,text){
if(text){
dojo.dom.replaceChildren(node,document.createTextNode(text));
return text;
}else{
var _1ad="";
if(node==null){
return _1ad;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
_1ad+=dojo.dom.textContent(node.childNodes[i]);
break;
case 3:
case 2:
case 4:
_1ad+=node.childNodes[i].nodeValue;
break;
default:
break;
}
}
return _1ad;
}
};
dojo.dom.collectionToArray=function(_1af){
var _1b0=new Array(_1af.length);
for(var i=0;i<_1af.length;i++){
_1b0[i]=_1af[i];
}
return _1b0;
};
dojo.provide("dojo.io.BrowserIO");
dojo.require("dojo.io");
dojo.require("dojo.lang");
dojo.require("dojo.dom");
try{
if((!djConfig.preventBackButtonFix)&&(!dojo.hostenv.post_load_)){
document.write("<iframe style='border: 0px; width: 1px; height: 1px; position: absolute; bottom: 0px; right: 0px; visibility: visible;' name='djhistory' id='djhistory' src='"+(dojo.hostenv.getBaseScriptUri()+"iframe_history.html")+"'></iframe>");
}
}
catch(e){
}
dojo.io.checkChildrenForFile=function(node){
var _1b3=false;
var _1b4=node.getElementsByTagName("input");
dojo.lang.forEach(_1b4,function(_1b5){
if(_1b3){
return;
}
if(_1b5.getAttribute("type")=="file"){
_1b3=true;
}
});
return _1b3;
};
dojo.io.formHasFile=function(_1b6){
return dojo.io.checkChildrenForFile(_1b6);
};
dojo.io.encodeForm=function(_1b7,_1b8){
if((!_1b7)||(!_1b7.tagName)||(!_1b7.tagName.toLowerCase()=="form")){
dojo.raise("Attempted to encode a non-form element.");
}
var enc=/utf/i.test(_1b8||"")?encodeURIComponent:dojo.string.encodeAscii;
var _1ba=[];
for(var i=0;i<_1b7.elements.length;i++){
var elm=_1b7.elements[i];
if(elm.disabled||elm.tagName.toLowerCase()=="fieldset"||!elm.name){
continue;
}
var name=enc(elm.name);
var type=elm.type.toLowerCase();
if(type=="select-multiple"){
for(var j=0;j<elm.options.length;j++){
if(elm.options[j].selected){
_1ba.push(name+"="+enc(elm.options[j].value));
}
}
}else{
if(dojo.lang.inArray(type,["radio","checkbox"])){
if(elm.checked){
_1ba.push(name+"="+enc(elm.value));
}
}else{
if(!dojo.lang.inArray(type,["file","submit","reset","button"])){
_1ba.push(name+"="+enc(elm.value));
}
}
}
}
var _1c0=_1b7.getElementsByTagName("input");
for(var i=0;i<_1c0.length;i++){
var _1c1=_1c0[i];
if(_1c1.type.toLowerCase()=="image"&&_1c1.form==_1b7){
var name=enc(_1c1.name);
_1ba.push(name+"="+enc(_1c1.value));
_1ba.push(name+".x=0");
_1ba.push(name+".y=0");
}
}
return _1ba.join("&")+"&";
};
dojo.io.setIFrameSrc=function(_1c2,src,_1c4){
try{
var r=dojo.render.html;
if(!_1c4){
if(r.safari){
_1c2.location=src;
}else{
frames[_1c2.name].location=src;
}
}else{
var idoc;
if(r.ie){
idoc=_1c2.contentWindow.document;
}else{
if(r.moz){
idoc=_1c2.contentWindow;
}
}
idoc.location.replace(src);
}
}
catch(e){
dojo.debug(e);
dojo.debug("setIFrameSrc: "+e);
}
};
dojo.io.XMLHTTPTransport=new function(){
var _1c7=this;
this.initialHref=window.location.href;
this.initialHash=window.location.hash;
this.moveForward=false;
var _1c8={};
this.useCache=false;
this.preventCache=false;
this.historyStack=[];
this.forwardStack=[];
this.historyIframe=null;
this.bookmarkAnchor=null;
this.locationTimer=null;
function getCacheKey(url,_1ca,_1cb){
return url+"|"+_1ca+"|"+_1cb.toLowerCase();
}
function addToCache(url,_1cd,_1ce,http){
_1c8[getCacheKey(url,_1cd,_1ce)]=http;
}
function getFromCache(url,_1d1,_1d2){
return _1c8[getCacheKey(url,_1d1,_1d2)];
}
this.clearCache=function(){
_1c8={};
};
function doLoad(_1d3,http,url,_1d6,_1d7){
if((http.status==200)||(location.protocol=="file:"&&http.status==0)){
var ret;
if(_1d3.method.toLowerCase()=="head"){
var _1d9=http.getAllResponseHeaders();
ret={};
ret.toString=function(){
return _1d9;
};
var _1da=_1d9.split(/[\r\n]+/g);
for(var i=0;i<_1da.length;i++){
var pair=_1da[i].match(/^([^:]+)\s*:\s*(.+)$/i);
if(pair){
ret[pair[1]]=pair[2];
}
}
}else{
if(_1d3.mimetype=="text/javascript"){
try{
ret=dj_eval(http.responseText);
}
catch(e){
dojo.debug(e);
dojo.debug(http.responseText);
ret=null;
}
}else{
if(_1d3.mimetype=="text/json"){
try{
ret=dj_eval("("+http.responseText+")");
}
catch(e){
dojo.debug(e);
dojo.debug(http.responseText);
ret=false;
}
}else{
if((_1d3.mimetype=="application/xml")||(_1d3.mimetype=="text/xml")){
ret=http.responseXML;
if(!ret||typeof ret=="string"){
ret=dojo.dom.createDocumentFromText(http.responseText);
}
}else{
ret=http.responseText;
}
}
}
}
if(_1d7){
addToCache(url,_1d6,_1d3.method,http);
}
_1d3[(typeof _1d3.load=="function")?"load":"handle"]("load",ret,http);
}else{
var _1dd=new dojo.io.Error("XMLHttpTransport Error: "+http.status+" "+http.statusText);
_1d3[(typeof _1d3.error=="function")?"error":"handle"]("error",_1dd,http);
}
}
function setHeaders(http,_1df){
if(_1df["headers"]){
for(var _1e0 in _1df["headers"]){
if(_1e0.toLowerCase()=="content-type"&&!_1df["contentType"]){
_1df["contentType"]=_1df["headers"][_1e0];
}else{
http.setRequestHeader(_1e0,_1df["headers"][_1e0]);
}
}
}
}
this.addToHistory=function(args){
var _1e2=args["back"]||args["backButton"]||args["handle"];
var hash=null;
if(!this.historyIframe){
this.historyIframe=window.frames["djhistory"];
}
if(!this.bookmarkAnchor){
this.bookmarkAnchor=document.createElement("a");
(document.body||document.getElementsByTagName("body")[0]).appendChild(this.bookmarkAnchor);
this.bookmarkAnchor.style.display="none";
}
if((!args["changeUrl"])||(dojo.render.html.ie)){
var url=dojo.hostenv.getBaseScriptUri()+"iframe_history.html?"+(new Date()).getTime();
this.moveForward=true;
dojo.io.setIFrameSrc(this.historyIframe,url,false);
}
if(args["changeUrl"]){
hash="#"+((args["changeUrl"]!==true)?args["changeUrl"]:(new Date()).getTime());
setTimeout("window.location.href = '"+hash+"';",1);
this.bookmarkAnchor.href=hash;
if(dojo.render.html.ie){
var _1e5=_1e2;
var lh=null;
var hsl=this.historyStack.length-1;
if(hsl>=0){
while(!this.historyStack[hsl]["urlHash"]){
hsl--;
}
lh=this.historyStack[hsl]["urlHash"];
}
if(lh){
_1e2=function(){
if(window.location.hash!=""){
setTimeout("window.location.href = '"+lh+"';",1);
}
_1e5();
};
}
this.forwardStack=[];
var _1e8=args["forward"]||args["forwardButton"];
var tfw=function(){
if(window.location.hash!=""){
window.location.href=hash;
}
if(_1e8){
_1e8();
}
};
if(args["forward"]){
args.forward=tfw;
}else{
if(args["forwardButton"]){
args.forwardButton=tfw;
}
}
}else{
if(dojo.render.html.moz){
if(!this.locationTimer){
this.locationTimer=setInterval("dojo.io.XMLHTTPTransport.checkLocation();",200);
}
}
}
}
this.historyStack.push({"url":url,"callback":_1e2,"kwArgs":args,"urlHash":hash});
};
this.checkLocation=function(){
var hsl=this.historyStack.length;
if((window.location.hash==this.initialHash)||(window.location.href==this.initialHref)&&(hsl==1)){
this.handleBackButton();
return;
}
if(this.forwardStack.length>0){
if(this.forwardStack[this.forwardStack.length-1].urlHash==window.location.hash){
this.handleForwardButton();
return;
}
}
if((hsl>=2)&&(this.historyStack[hsl-2])){
if(this.historyStack[hsl-2].urlHash==window.location.hash){
this.handleBackButton();
return;
}
}
};
this.iframeLoaded=function(evt,_1ec){
var isp=_1ec.href.split("?");
if(isp.length<2){
if(this.historyStack.length==1){
this.handleBackButton();
}
return;
}
var _1ee=isp[1];
if(this.moveForward){
this.moveForward=false;
return;
}
var last=this.historyStack.pop();
if(!last){
if(this.forwardStack.length>0){
var next=this.forwardStack[this.forwardStack.length-1];
if(_1ee==next.url.split("?")[1]){
this.handleForwardButton();
}
}
return;
}
this.historyStack.push(last);
if(this.historyStack.length>=2){
if(isp[1]==this.historyStack[this.historyStack.length-2].url.split("?")[1]){
this.handleBackButton();
}
}else{
this.handleBackButton();
}
};
this.handleBackButton=function(){
var last=this.historyStack.pop();
if(!last){
return;
}
if(last["callback"]){
last.callback();
}else{
if(last.kwArgs["backButton"]){
last.kwArgs["backButton"]();
}else{
if(last.kwArgs["back"]){
last.kwArgs["back"]();
}else{
if(last.kwArgs["handle"]){
last.kwArgs.handle("back");
}
}
}
}
this.forwardStack.push(last);
};
this.handleForwardButton=function(){
var last=this.forwardStack.pop();
if(!last){
return;
}
if(last.kwArgs["forward"]){
last.kwArgs.forward();
}else{
if(last.kwArgs["forwardButton"]){
last.kwArgs.forwardButton();
}else{
if(last.kwArgs["handle"]){
last.kwArgs.handle("forward");
}
}
}
this.historyStack.push(last);
};
this.inFlight=[];
this.inFlightTimer=null;
this.startWatchingInFlight=function(){
if(!this.inFlightTimer){
this.inFlightTimer=setInterval("dojo.io.XMLHTTPTransport.watchInFlight();",10);
}
};
this.watchInFlight=function(){
for(var x=this.inFlight.length-1;x>=0;x--){
var tif=this.inFlight[x];
if(!tif){
this.inFlight.splice(x,1);
continue;
}
if(4==tif.http.readyState){
this.inFlight.splice(x,1);
doLoad(tif.req,tif.http,tif.url,tif.query,tif.useCache);
if(this.inFlight.length==0){
clearInterval(this.inFlightTimer);
this.inFlightTimer=null;
}
}
}
};
var _1f5=dojo.hostenv.getXmlhttpObject()?true:false;
this.canHandle=function(_1f6){
return _1f5&&dojo.lang.inArray((_1f6["mimetype"]||"".toLowerCase()),["text/plain","text/html","application/xml","text/xml","text/javascript","text/json"])&&dojo.lang.inArray(_1f6["method"].toLowerCase(),["post","get","head"])&&!(_1f6["formNode"]&&dojo.io.formHasFile(_1f6["formNode"]));
};
this.multipartBoundary="45309FFF-BD65-4d50-99C9-36986896A96F";
this.bind=function(_1f7){
if(!_1f7["url"]){
if(!_1f7["formNode"]&&(_1f7["backButton"]||_1f7["back"]||_1f7["changeUrl"]||_1f7["watchForURL"])&&(!djConfig.preventBackButtonFix)){
this.addToHistory(_1f7);
return true;
}
}
var url=_1f7.url;
var _1f9="";
if(_1f7["formNode"]){
var ta=_1f7.formNode.getAttribute("action");
if((ta)&&(!_1f7["url"])){
url=ta;
}
var tp=_1f7.formNode.getAttribute("method");
if((tp)&&(!_1f7["method"])){
_1f7.method=tp;
}
_1f9+=dojo.io.encodeForm(_1f7.formNode,_1f7.encoding);
}
if(url.indexOf("#")>-1){
dojo.debug("Warning: dojo.io.bind: stripping hash values from url:",url);
url=url.split("#")[0];
}
if(_1f7["file"]){
_1f7.method="post";
}
if(!_1f7["method"]){
_1f7.method="get";
}
if(_1f7.method.toLowerCase()=="get"){
_1f7.multipart=false;
}else{
if(_1f7["file"]){
_1f7.multipart=true;
}else{
if(!_1f7["multipart"]){
_1f7.multipart=false;
}
}
}
if(_1f7["backButton"]||_1f7["back"]||_1f7["changeUrl"]){
this.addToHistory(_1f7);
}
do{
if(_1f7.postContent){
_1f9=_1f7.postContent;
break;
}
if(_1f7["content"]){
_1f9+=dojo.io.argsFromMap(_1f7.content,_1f7.encoding);
}
if(_1f7.method.toLowerCase()=="get"||!_1f7.multipart){
break;
}
var t=[];
if(_1f9.length){
var q=_1f9.split("&");
for(var i=0;i<q.length;++i){
if(q[i].length){
var p=q[i].split("=");
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+p[0]+"\"","",p[1]);
}
}
}
if(_1f7.file){
if(dojo.lang.isArray(_1f7.file)){
for(var i=0;i<_1f7.file.length;++i){
var o=_1f7.file[i];
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+o.name+"\"; filename=\""+("fileName" in o?o.fileName:o.name)+"\"","Content-Type: "+("contentType" in o?o.contentType:"application/octet-stream"),"",o.content);
}
}else{
var o=_1f7.file;
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+o.name+"\"; filename=\""+("fileName" in o?o.fileName:o.name)+"\"","Content-Type: "+("contentType" in o?o.contentType:"application/octet-stream"),"",o.content);
}
}
if(t.length){
t.push("--"+this.multipartBoundary+"--","");
_1f9=t.join("\r\n");
}
}while(false);
var _201=_1f7["sync"]?false:true;
var _202=_1f7["preventCache"]||(this.preventCache==true&&_1f7["preventCache"]!=false);
var _203=_1f7["useCache"]==true||(this.useCache==true&&_1f7["useCache"]!=false);
if(!_202&&_203){
var _204=getFromCache(url,_1f9,_1f7.method);
if(_204){
doLoad(_1f7,_204,url,_1f9,false);
return;
}
}
var http=dojo.hostenv.getXmlhttpObject();
var _206=false;
if(_201){
this.inFlight.push({"req":_1f7,"http":http,"url":url,"query":_1f9,"useCache":_203});
this.startWatchingInFlight();
}
if(_1f7.method.toLowerCase()=="post"){
http.open("POST",url,_201);
setHeaders(http,_1f7);
http.setRequestHeader("Content-Type",_1f7.multipart?("multipart/form-data; boundary="+this.multipartBoundary):(_1f7.contentType||"application/x-www-form-urlencoded"));
http.send(_1f9);
}else{
var _207=url;
if(_1f9!=""){
_207+=(_207.indexOf("?")>-1?"&":"?")+_1f9;
}
if(_202){
_207+=(dojo.string.endsWithAny(_207,"?","&")?"":(_207.indexOf("?")>-1?"&":"?"))+"dojo.preventCache="+new Date().valueOf();
}
http.open(_1f7.method.toUpperCase(),_207,_201);
setHeaders(http,_1f7);
http.send(null);
}
if(!_201){
doLoad(_1f7,http,url,_1f9,_203);
}
_1f7.abort=function(){
return http.abort();
};
return;
};
dojo.io.transports.addTransport("XMLHTTPTransport");
};
dojo.require("dojo.lang");
dojo.provide("dojo.event");
dojo.event=new function(){
this.canTimeout=dojo.lang.isFunction(dj_global["setTimeout"])||dojo.lang.isAlien(dj_global["setTimeout"]);
this.createFunctionPair=function(obj,cb){
var ret=[];
if(typeof obj=="function"){
ret[1]=dojo.lang.nameAnonFunc(obj,dj_global);
ret[0]=dj_global;
return ret;
}else{
if((typeof obj=="object")&&(typeof cb=="string")){
return [obj,cb];
}else{
if((typeof obj=="object")&&(typeof cb=="function")){
ret[1]=dojo.lang.nameAnonFunc(cb,obj);
ret[0]=obj;
return ret;
}
}
}
return null;
};
function interpolateArgs(args){
var ao={srcObj:dj_global,srcFunc:null,adviceObj:dj_global,adviceFunc:null,aroundObj:null,aroundFunc:null,adviceType:(args.length>2)?args[0]:"after",precedence:"last",once:false,delay:null,rate:0,adviceMsg:false};
switch(args.length){
case 0:
return;
case 1:
return;
case 2:
ao.srcFunc=args[0];
ao.adviceFunc=args[1];
break;
case 3:
if((typeof args[0]=="object")&&(typeof args[1]=="string")&&(typeof args[2]=="string")){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((typeof args[1]=="string")&&(typeof args[2]=="string")){
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((typeof args[0]=="object")&&(typeof args[1]=="string")&&(typeof args[2]=="function")){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
var _20d=dojo.lang.nameAnonFunc(args[2],ao.adviceObj);
ao.adviceObj[_20d]=args[2];
ao.adviceFunc=_20d;
}else{
if((typeof args[0]=="function")&&(typeof args[1]=="object")&&(typeof args[2]=="string")){
ao.adviceType="after";
ao.srcObj=dj_global;
var _20d=dojo.lang.nameAnonFunc(args[0],ao.srcObj);
ao.srcObj[_20d]=args[0];
ao.srcFunc=_20d;
ao.adviceObj=args[1];
ao.adviceFunc=args[2];
}
}
}
}
break;
case 4:
if((typeof args[0]=="object")&&(typeof args[2]=="object")){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((typeof args[1]).toLowerCase()=="object"){
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=dj_global;
ao.adviceFunc=args[3];
}else{
if((typeof args[2]).toLowerCase()=="object"){
ao.srcObj=dj_global;
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
ao.srcObj=ao.adviceObj=ao.aroundObj=dj_global;
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
ao.aroundFunc=args[3];
}
}
}
break;
case 6:
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=args[3];
ao.adviceFunc=args[4];
ao.aroundFunc=args[5];
ao.aroundObj=dj_global;
break;
default:
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=args[3];
ao.adviceFunc=args[4];
ao.aroundObj=args[5];
ao.aroundFunc=args[6];
ao.once=args[7];
ao.delay=args[8];
ao.rate=args[9];
ao.adviceMsg=args[10];
break;
}
if((typeof ao.srcFunc).toLowerCase()!="string"){
ao.srcFunc=dojo.lang.getNameInObj(ao.srcObj,ao.srcFunc);
}
if((typeof ao.adviceFunc).toLowerCase()!="string"){
ao.adviceFunc=dojo.lang.getNameInObj(ao.adviceObj,ao.adviceFunc);
}
if((ao.aroundObj)&&((typeof ao.aroundFunc).toLowerCase()!="string")){
ao.aroundFunc=dojo.lang.getNameInObj(ao.aroundObj,ao.aroundFunc);
}
if(!ao.srcObj){
dojo.raise("bad srcObj for srcFunc: "+ao.srcFunc);
}
if(!ao.adviceObj){
dojo.raise("bad adviceObj for adviceFunc: "+ao.adviceFunc);
}
return ao;
}
this.connect=function(){
var ao=interpolateArgs(arguments);
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
if(ao.adviceFunc){
var mjp2=dojo.event.MethodJoinPoint.getForMethod(ao.adviceObj,ao.adviceFunc);
}
mjp.kwAddAdvice(ao);
return mjp;
};
this.connectBefore=function(){
var args=["before"];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
return this.connect.apply(this,args);
};
this.connectAround=function(){
var args=["around"];
for(var i=0;i<arguments.length;i++){
args.push(arguments[i]);
}
return this.connect.apply(this,args);
};
this._kwConnectImpl=function(_215,_216){
var fn=(_216)?"disconnect":"connect";
if(typeof _215["srcFunc"]=="function"){
_215.srcObj=_215["srcObj"]||dj_global;
var _218=dojo.lang.nameAnonFunc(_215.srcFunc,_215.srcObj);
_215.srcFunc=_218;
}
if(typeof _215["adviceFunc"]=="function"){
_215.adviceObj=_215["adviceObj"]||dj_global;
var _218=dojo.lang.nameAnonFunc(_215.adviceFunc,_215.adviceObj);
_215.adviceFunc=_218;
}
return dojo.event[fn]((_215["type"]||_215["adviceType"]||"after"),_215["srcObj"]||dj_global,_215["srcFunc"],_215["adviceObj"]||_215["targetObj"]||dj_global,_215["adviceFunc"]||_215["targetFunc"],_215["aroundObj"],_215["aroundFunc"],_215["once"],_215["delay"],_215["rate"],_215["adviceMsg"]||false);
};
this.kwConnect=function(_219){
return this._kwConnectImpl(_219,false);
};
this.disconnect=function(){
var ao=interpolateArgs(arguments);
if(!ao.adviceFunc){
return;
}
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
return mjp.removeAdvice(ao.adviceObj,ao.adviceFunc,ao.adviceType,ao.once);
};
this.kwDisconnect=function(_21c){
return this._kwConnectImpl(_21c,true);
};
};
dojo.event.MethodInvocation=function(_21d,obj,args){
this.jp_=_21d;
this.object=obj;
this.args=[];
for(var x=0;x<args.length;x++){
this.args[x]=args[x];
}
this.around_index=-1;
};
dojo.event.MethodInvocation.prototype.proceed=function(){
this.around_index++;
if(this.around_index>=this.jp_.around.length){
return this.jp_.object[this.jp_.methodname].apply(this.jp_.object,this.args);
}else{
var ti=this.jp_.around[this.around_index];
var mobj=ti[0]||dj_global;
var meth=ti[1];
return mobj[meth].call(mobj,this);
}
};
dojo.event.MethodJoinPoint=function(obj,_225){
this.object=obj||dj_global;
this.methodname=_225;
this.methodfunc=this.object[_225];
this.before=[];
this.after=[];
this.around=[];
};
dojo.event.MethodJoinPoint.getForMethod=function(obj,_227){
if(!obj){
obj=dj_global;
}
if(!obj[_227]){
obj[_227]=function(){
};
}else{
if((!dojo.lang.isFunction(obj[_227]))&&(!dojo.lang.isAlien(obj[_227]))){
return null;
}
}
var _228=_227+"$joinpoint";
var _229=_227+"$joinpoint$method";
var _22a=obj[_228];
if(!_22a){
var _22b=false;
if(dojo.event["browser"]){
if((obj["attachEvent"])||(obj["nodeType"])||(obj["addEventListener"])){
_22b=true;
dojo.event.browser.addClobberNodeAttrs(obj,[_228,_229,_227]);
}
}
obj[_229]=obj[_227];
_22a=obj[_228]=new dojo.event.MethodJoinPoint(obj,_229);
obj[_227]=function(){
var args=[];
if((_22b)&&(!arguments.length)&&(window.event)){
args.push(dojo.event.browser.fixEvent(window.event));
}else{
for(var x=0;x<arguments.length;x++){
if((x==0)&&(_22b)&&(dojo.event.browser.isEvent(arguments[x]))){
args.push(dojo.event.browser.fixEvent(arguments[x]));
}else{
args.push(arguments[x]);
}
}
}
return _22a.run.apply(_22a,args);
};
}
return _22a;
};
dojo.lang.extend(dojo.event.MethodJoinPoint,{unintercept:function(){
this.object[this.methodname]=this.methodfunc;
},run:function(){
var obj=this.object||dj_global;
var args=arguments;
var _230=[];
for(var x=0;x<args.length;x++){
_230[x]=args[x];
}
var _232=function(marr){
if(!marr){
dojo.debug("Null argument to unrollAdvice()");
return;
}
var _234=marr[0]||dj_global;
var _235=marr[1];
if(!_234[_235]){
dojo.raise("function \""+_235+"\" does not exist on \""+_234+"\"");
}
var _236=marr[2]||dj_global;
var _237=marr[3];
var msg=marr[6];
var _239;
var to={args:[],jp_:this,object:obj,proceed:function(){
return _234[_235].apply(_234,to.args);
}};
to.args=_230;
var _23b=parseInt(marr[4]);
var _23c=((!isNaN(_23b))&&(marr[4]!==null)&&(typeof marr[4]!="undefined"));
if(marr[5]){
var rate=parseInt(marr[5]);
var cur=new Date();
var _23f=false;
if((marr["last"])&&((cur-marr.last)<=rate)){
if(dojo.event.canTimeout){
if(marr["delayTimer"]){
clearTimeout(marr.delayTimer);
}
var tod=parseInt(rate*2);
var mcpy=dojo.lang.shallowCopy(marr);
marr.delayTimer=setTimeout(function(){
mcpy[5]=0;
_232(mcpy);
},tod);
}
return;
}else{
marr.last=cur;
}
}
if(_237){
_236[_237].call(_236,to);
}else{
if((_23c)&&((dojo.render.html)||(dojo.render.svg))){
dj_global["setTimeout"](function(){
if(msg){
_234[_235].call(_234,to);
}else{
_234[_235].apply(_234,args);
}
},_23b);
}else{
if(msg){
_234[_235].call(_234,to);
}else{
_234[_235].apply(_234,args);
}
}
}
};
if(this.before.length>0){
dojo.lang.forEach(this.before,_232,true);
}
var _242;
if(this.around.length>0){
var mi=new dojo.event.MethodInvocation(this,obj,args);
_242=mi.proceed();
}else{
if(this.methodfunc){
_242=this.object[this.methodname].apply(this.object,args);
}
}
if(this.after.length>0){
dojo.lang.forEach(this.after,_232,true);
}
return (this.methodfunc)?_242:null;
},getArr:function(kind){
var arr=this.after;
if((typeof kind=="string")&&(kind.indexOf("before")!=-1)){
arr=this.before;
}else{
if(kind=="around"){
arr=this.around;
}
}
return arr;
},kwAddAdvice:function(args){
this.addAdvice(args["adviceObj"],args["adviceFunc"],args["aroundObj"],args["aroundFunc"],args["adviceType"],args["precedence"],args["once"],args["delay"],args["rate"],args["adviceMsg"]);
},addAdvice:function(_247,_248,_249,_24a,_24b,_24c,once,_24e,rate,_250){
var arr=this.getArr(_24b);
if(!arr){
dojo.raise("bad this: "+this);
}
var ao=[_247,_248,_249,_24a,_24e,rate,_250];
if(once){
if(this.hasAdvice(_247,_248,_24b,arr)>=0){
return;
}
}
if(_24c=="first"){
arr.unshift(ao);
}else{
arr.push(ao);
}
},hasAdvice:function(_253,_254,_255,arr){
if(!arr){
arr=this.getArr(_255);
}
var ind=-1;
for(var x=0;x<arr.length;x++){
if((arr[x][0]==_253)&&(arr[x][1]==_254)){
ind=x;
}
}
return ind;
},removeAdvice:function(_259,_25a,_25b,once){
var arr=this.getArr(_25b);
var ind=this.hasAdvice(_259,_25a,_25b,arr);
if(ind==-1){
return false;
}
while(ind!=-1){
arr.splice(ind,1);
if(once){
break;
}
ind=this.hasAdvice(_259,_25a,_25b,arr);
}
return true;
}});
dojo.require("dojo.event");
dojo.provide("dojo.event.topic");
dojo.event.topic=new function(){
this.topics={};
this.getTopic=function(_25f){
if(!this.topics[_25f]){
this.topics[_25f]=new this.TopicImpl(_25f);
}
return this.topics[_25f];
};
this.registerPublisher=function(_260,obj,_262){
var _260=this.getTopic(_260);
_260.registerPublisher(obj,_262);
};
this.subscribe=function(_263,obj,_265){
var _263=this.getTopic(_263);
_263.subscribe(obj,_265);
};
this.unsubscribe=function(_266,obj,_268){
var _266=this.getTopic(_266);
_266.unsubscribe(obj,_268);
};
this.publish=function(_269,_26a){
var _269=this.getTopic(_269);
var args=[];
if((arguments.length==2)&&(_26a.length)&&(typeof _26a!="string")){
args=_26a;
}else{
var args=[];
for(var x=1;x<arguments.length;x++){
args.push(arguments[x]);
}
}
_269.sendMessage.apply(_269,args);
};
};
dojo.event.topic.TopicImpl=function(_26d){
this.topicName=_26d;
var self=this;
self.subscribe=function(_26f,_270){
dojo.event.connect("before",self,"sendMessage",_26f,_270);
};
self.unsubscribe=function(_271,_272){
dojo.event.disconnect("before",self,"sendMessage",_271,_272);
};
self.registerPublisher=function(_273,_274){
dojo.event.connect(_273,_274,self,"sendMessage");
};
self.sendMessage=function(_275){
};
};
dojo.provide("dojo.event.browser");
dojo.require("dojo.event");
dojo_ie_clobber=new function(){
this.clobberNodes=[];
function nukeProp(node,prop){
try{
node[prop]=null;
}
catch(e){
}
try{
delete node[prop];
}
catch(e){
}
try{
node.removeAttribute(prop);
}
catch(e){
}
}
this.clobber=function(_278){
var na;
var tna;
if(_278){
tna=_278.getElementsByTagName("*");
na=[_278];
for(var x=0;x<tna.length;x++){
if(tna[x]["__doClobber__"]){
na.push(tna[x]);
}
}
}else{
try{
window.onload=null;
}
catch(e){
}
na=(this.clobberNodes.length)?this.clobberNodes:document.all;
}
tna=null;
var _27c={};
for(var i=na.length-1;i>=0;i=i-1){
var el=na[i];
if(el["__clobberAttrs__"]){
for(var j=0;j<el.__clobberAttrs__.length;j++){
nukeProp(el,el.__clobberAttrs__[j]);
}
nukeProp(el,"__clobberAttrs__");
nukeProp(el,"__doClobber__");
}
}
na=null;
};
};
if(dojo.render.html.ie){
window.onunload=function(){
dojo_ie_clobber.clobber();
try{
if((dojo["widget"])&&(dojo.widget["manager"])){
dojo.widget.manager.destroyAll();
}
}
catch(e){
}
try{
window.onload=null;
}
catch(e){
}
try{
window.onunload=null;
}
catch(e){
}
dojo_ie_clobber.clobberNodes=[];
};
}
dojo.event.browser=new function(){
var _280=0;
this.clean=function(node){
if(dojo.render.html.ie){
dojo_ie_clobber.clobber(node);
}
};
this.addClobberNode=function(node){
if(!node["__doClobber__"]){
node.__doClobber__=true;
dojo_ie_clobber.clobberNodes.push(node);
node.__clobberAttrs__=[];
}
};
this.addClobberNodeAttrs=function(node,_284){
this.addClobberNode(node);
for(var x=0;x<_284.length;x++){
node.__clobberAttrs__.push(_284[x]);
}
};
this.removeListener=function(node,_287,fp,_289){
if(!_289){
var _289=false;
}
_287=_287.toLowerCase();
if(_287.substr(0,2)=="on"){
_287=_287.substr(2);
}
if(node.removeEventListener){
node.removeEventListener(_287,fp,_289);
}
};
this.addListener=function(node,_28b,fp,_28d,_28e){
if(!node){
return;
}
if(!_28d){
var _28d=false;
}
_28b=_28b.toLowerCase();
if(_28b.substr(0,2)!="on"){
_28b="on"+_28b;
}
if(!_28e){
var _28f=function(evt){
if(!evt){
evt=window.event;
}
var ret=fp(dojo.event.browser.fixEvent(evt));
if(_28d){
dojo.event.browser.stopEvent(evt);
}
return ret;
};
}else{
_28f=fp;
}
if(node.addEventListener){
node.addEventListener(_28b.substr(2),_28f,_28d);
return _28f;
}else{
if(typeof node[_28b]=="function"){
var _292=node[_28b];
node[_28b]=function(e){
_292(e);
return _28f(e);
};
}else{
node[_28b]=_28f;
}
if(dojo.render.html.ie){
this.addClobberNodeAttrs(node,[_28b]);
}
return _28f;
}
};
this.isEvent=function(obj){
return (typeof Event!="undefined")&&(obj.eventPhase);
};
this.currentEvent=null;
this.callListener=function(_295,_296){
if(typeof _295!="function"){
dojo.raise("listener not a function: "+_295);
}
dojo.event.browser.currentEvent.currentTarget=_296;
return _295.call(_296,dojo.event.browser.currentEvent);
};
this.stopPropagation=function(){
dojo.event.browser.currentEvent.cancelBubble=true;
};
this.preventDefault=function(){
dojo.event.browser.currentEvent.returnValue=false;
};
this.keys={KEY_BACKSPACE:8,KEY_TAB:9,KEY_ENTER:13,KEY_SHIFT:16,KEY_CTRL:17,KEY_ALT:18,KEY_PAUSE:19,KEY_CAPS_LOCK:20,KEY_ESCAPE:27,KEY_SPACE:32,KEY_PAGE_UP:33,KEY_PAGE_DOWN:34,KEY_END:35,KEY_HOME:36,KEY_LEFT_ARROW:37,KEY_UP_ARROW:38,KEY_RIGHT_ARROW:39,KEY_DOWN_ARROW:40,KEY_INSERT:45,KEY_DELETE:46,KEY_LEFT_WINDOW:91,KEY_RIGHT_WINDOW:92,KEY_SELECT:93,KEY_F1:112,KEY_F2:113,KEY_F3:114,KEY_F4:115,KEY_F5:116,KEY_F6:117,KEY_F7:118,KEY_F8:119,KEY_F9:120,KEY_F10:121,KEY_F11:122,KEY_F12:123,KEY_NUM_LOCK:144,KEY_SCROLL_LOCK:145};
this.revKeys=[];
for(var key in this.keys){
this.revKeys[this.keys[key]]=key;
}
this.fixEvent=function(evt){
if((!evt)&&(window["event"])){
var evt=window.event;
}
if((evt["type"])&&(evt["type"].indexOf("key")==0)){
evt.keys=this.revKeys;
for(var key in this.keys){
evt[key]=this.keys[key];
}
if((dojo.render.html.ie)&&(evt["type"]=="keypress")){
evt.charCode=evt.keyCode;
}
}
if(dojo.render.html.ie){
if(!evt.target){
evt.target=evt.srcElement;
}
if(!evt.currentTarget){
evt.currentTarget=evt.srcElement;
}
if(!evt.layerX){
evt.layerX=evt.offsetX;
}
if(!evt.layerY){
evt.layerY=evt.offsetY;
}
if(evt.fromElement){
evt.relatedTarget=evt.fromElement;
}
if(evt.toElement){
evt.relatedTarget=evt.toElement;
}
this.currentEvent=evt;
evt.callListener=this.callListener;
evt.stopPropagation=this.stopPropagation;
evt.preventDefault=this.preventDefault;
}
return evt;
};
this.stopEvent=function(ev){
if(window.event){
ev.returnValue=false;
ev.cancelBubble=true;
}else{
ev.preventDefault();
ev.stopPropagation();
}
};
};
dojo.hostenv.conditionalLoadModule({common:["dojo.event","dojo.event.topic"],browser:["dojo.event.browser"]});
dojo.hostenv.moduleLoaded("dojo.event.*");
dojo.provide("dojo.alg.Alg");
dojo.require("dojo.lang");
dj_deprecated("dojo.alg.Alg is deprecated, use dojo.lang instead");
dojo.alg.find=function(arr,val){
return dojo.lang.find(arr,val);
};
dojo.alg.inArray=function(arr,val){
return dojo.lang.inArray(arr,val);
};
dojo.alg.inArr=dojo.alg.inArray;
dojo.alg.getNameInObj=function(ns,item){
return dojo.lang.getNameInObj(ns,item);
};
dojo.alg.has=function(obj,name){
return dojo.lang.has(obj,name);
};
dojo.alg.forEach=function(arr,_2a4,_2a5){
return dojo.lang.forEach(arr,_2a4,_2a5);
};
dojo.alg.for_each=dojo.alg.forEach;
dojo.alg.map=function(arr,obj,_2a8){
return dojo.lang.map(arr,obj,_2a8);
};
dojo.alg.tryThese=function(){
return dojo.lang.tryThese.apply(dojo.lang,arguments);
};
dojo.alg.delayThese=function(farr,cb,_2ab,_2ac){
return dojo.lang.delayThese.apply(dojo.lang,arguments);
};
dojo.alg.for_each_call=dojo.alg.map;
dojo.require("dojo.alg.Alg",false,true);
dojo.hostenv.moduleLoaded("dojo.alg.*");
dojo.provide("dojo.uri.Uri");
dojo.uri=new function(){
this.joinPath=function(){
var arr=[];
for(var i=0;i<arguments.length;i++){
arr.push(arguments[i]);
}
return arr.join("/").replace(/\/{2,}/g,"/").replace(/((https*|ftps*):)/i,"$1/");
};
this.dojoUri=function(uri){
return new dojo.uri.Uri(dojo.hostenv.getBaseScriptUri(),uri);
};
this.Uri=function(){
var uri=arguments[0];
for(var i=1;i<arguments.length;i++){
if(!arguments[i]){
continue;
}
var _2b2=new dojo.uri.Uri(arguments[i].toString());
var _2b3=new dojo.uri.Uri(uri.toString());
if(_2b2.path==""&&_2b2.scheme==null&&_2b2.authority==null&&_2b2.query==null){
if(_2b2.fragment!=null){
_2b3.fragment=_2b2.fragment;
}
_2b2=_2b3;
}else{
if(_2b2.scheme==null){
_2b2.scheme=_2b3.scheme;
if(_2b2.authority==null){
_2b2.authority=_2b3.authority;
if(_2b2.path.charAt(0)!="/"){
var path=_2b3.path.substring(0,_2b3.path.lastIndexOf("/")+1)+_2b2.path;
var segs=path.split("/");
for(var j=0;j<segs.length;j++){
if(segs[j]=="."){
if(j==segs.length-1){
segs[j]="";
}else{
segs.splice(j,1);
j--;
}
}else{
if(j>0&&!(j==1&&segs[0]=="")&&segs[j]==".."&&segs[j-1]!=".."){
if(j==segs.length-1){
segs.splice(j,1);
segs[j-1]="";
}else{
segs.splice(j-1,2);
j-=2;
}
}
}
}
_2b2.path=segs.join("/");
}
}
}
}
uri="";
if(_2b2.scheme!=null){
uri+=_2b2.scheme+":";
}
if(_2b2.authority!=null){
uri+="//"+_2b2.authority;
}
uri+=_2b2.path;
if(_2b2.query!=null){
uri+="?"+_2b2.query;
}
if(_2b2.fragment!=null){
uri+="#"+_2b2.fragment;
}
}
this.uri=uri.toString();
var _2b7="^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?$";
var r=this.uri.match(new RegExp(_2b7));
this.scheme=r[2]||(r[1]?"":null);
this.authority=r[4]||(r[3]?"":null);
this.path=r[5];
this.query=r[7]||(r[6]?"":null);
this.fragment=r[9]||(r[8]?"":null);
if(this.authority!=null){
_2b7="^((([^:]+:)?([^@]+))@)?([^:]*)(:([0-9]+))?$";
r=this.authority.match(new RegExp(_2b7));
this.user=r[3]||null;
this.password=r[4]||null;
this.host=r[5];
this.port=r[7]||null;
}
this.toString=function(){
return this.uri;
};
};
};
dojo.provide("dojo.math");
dojo.math.degToRad=function(x){
return (x*Math.PI)/180;
};
dojo.math.radToDeg=function(x){
return (x*180)/Math.PI;
};
dojo.math.factorial=function(n){
if(n<1){
return 0;
}
var _2bc=1;
for(var i=1;i<=n;i++){
_2bc*=i;
}
return _2bc;
};
dojo.math.permutations=function(n,k){
if(n==0||k==0){
return 1;
}
return (dojo.math.factorial(n)/dojo.math.factorial(n-k));
};
dojo.math.combinations=function(n,r){
if(n==0||r==0){
return 1;
}
return (dojo.math.factorial(n)/(dojo.math.factorial(n-r)*dojo.math.factorial(r)));
};
dojo.math.bernstein=function(t,n,i){
return (dojo.math.combinations(n,i)*Math.pow(t,i)*Math.pow(1-t,n-i));
};
dojo.math.gaussianRandom=function(){
var k=2;
do{
var i=2*Math.random()-1;
var j=2*Math.random()-1;
k=i*i+j*j;
}while(k>=1);
k=Math.sqrt((-2*Math.log(k))/k);
return i*k;
};
dojo.math.mean=function(){
var _2c8=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
var mean=0;
for(var i=0;i<_2c8.length;i++){
mean+=_2c8[i];
}
return mean/_2c8.length;
};
dojo.math.round=function(_2cb,_2cc){
if(!_2cc){
var _2cd=1;
}else{
var _2cd=Math.pow(10,_2cc);
}
return Math.round(_2cb*_2cd)/_2cd;
};
dojo.math.sd=function(){
var _2ce=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
return Math.sqrt(dojo.math.variance(_2ce));
};
dojo.math.variance=function(){
var _2cf=dojo.lang.isArray(arguments[0])?arguments[0]:arguments;
var mean=0,squares=0;
for(var i=0;i<_2cf.length;i++){
mean+=_2cf[i];
squares+=Math.pow(_2cf[i],2);
}
return (squares/_2cf.length)-Math.pow(mean/_2cf.length,2);
};
dojo.provide("dojo.graphics.color");
dojo.require("dojo.lang");
dojo.require("dojo.string");
dojo.require("dojo.math");
dojo.graphics.color.Color=function(r,g,b,a){
if(dojo.lang.isArray(r)){
this.r=r[0];
this.g=r[1];
this.b=r[2];
this.a=r[3]||1;
}else{
if(dojo.lang.isString(r)){
var rgb=dojo.graphics.color.extractRGB(r);
this.r=rgb[0];
this.g=rgb[1];
this.b=rgb[2];
this.a=g||1;
}else{
if(r instanceof dojo.graphics.color.Color){
this.r=r.r;
this.b=r.b;
this.g=r.g;
this.a=r.a;
}else{
this.r=r;
this.g=g;
this.b=b;
this.a=a;
}
}
}
};
dojo.lang.extend(dojo.graphics.color.Color,{toRgb:function(_2d7){
if(_2d7){
return this.toRgba();
}else{
return [this.r,this.g,this.b];
}
},toRgba:function(){
return [this.r,this.g,this.b,this.a];
},toHex:function(){
return dojo.graphics.color.rgb2hex(this.toRgb());
},toCss:function(){
return "rgb("+this.toRgb().join()+")";
},toString:function(){
return this.toHex();
},toHsv:function(){
return dojo.graphics.color.rgb2hsv(this.toRgb());
},toHsl:function(){
return dojo.graphics.color.rgb2hsl(this.toRgb());
},blend:function(_2d8,_2d9){
return dojo.graphics.color.blend(this.toRgb(),new Color(_2d8).toRgb(),_2d9);
}});
dojo.graphics.color.named={white:[255,255,255],black:[0,0,0],red:[255,0,0],green:[0,255,0],blue:[0,0,255],navy:[0,0,128],gray:[128,128,128],silver:[192,192,192]};
dojo.graphics.color.blend=function(a,b,_2dc){
if(typeof a=="string"){
return dojo.graphics.color.blendHex(a,b,_2dc);
}
if(!_2dc){
_2dc=0;
}else{
if(_2dc>1){
_2dc=1;
}else{
if(_2dc<-1){
_2dc=-1;
}
}
}
var c=new Array(3);
for(var i=0;i<3;i++){
var half=Math.abs(a[i]-b[i])/2;
c[i]=Math.floor(Math.min(a[i],b[i])+half+(half*_2dc));
}
return c;
};
dojo.graphics.color.blendHex=function(a,b,_2e2){
return dojo.graphics.color.rgb2hex(dojo.graphics.color.blend(dojo.graphics.color.hex2rgb(a),dojo.graphics.color.hex2rgb(b),_2e2));
};
dojo.graphics.color.extractRGB=function(_2e3){
var hex="0123456789abcdef";
_2e3=_2e3.toLowerCase();
if(_2e3.indexOf("rgb")==0){
var _2e5=_2e3.match(/rgba*\((\d+), *(\d+), *(\d+)/i);
var ret=_2e5.splice(1,3);
return ret;
}else{
var _2e7=dojo.graphics.color.hex2rgb(_2e3);
if(_2e7){
return _2e7;
}else{
return dojo.graphics.color.named[_2e3]||[255,255,255];
}
}
};
dojo.graphics.color.hex2rgb=function(hex){
var _2e9="0123456789ABCDEF";
var rgb=new Array(3);
if(hex.indexOf("#")==0){
hex=hex.substring(1);
}
hex=hex.toUpperCase();
if(hex.replace(new RegExp("["+_2e9+"]","g"),"")!=""){
return null;
}
if(hex.length==3){
rgb[0]=hex.charAt(0)+hex.charAt(0);
rgb[1]=hex.charAt(1)+hex.charAt(1);
rgb[2]=hex.charAt(2)+hex.charAt(2);
}else{
rgb[0]=hex.substring(0,2);
rgb[1]=hex.substring(2,4);
rgb[2]=hex.substring(4);
}
for(var i=0;i<rgb.length;i++){
rgb[i]=_2e9.indexOf(rgb[i].charAt(0))*16+_2e9.indexOf(rgb[i].charAt(1));
}
return rgb;
};
dojo.graphics.color.rgb2hex=function(r,g,b){
if(dojo.lang.isArray(r)){
g=r[1]||0;
b=r[2]||0;
r=r[0]||0;
}
return ["#",dojo.string.pad(r.toString(16),2),dojo.string.pad(g.toString(16),2),dojo.string.pad(b.toString(16),2)].join("");
};
dojo.graphics.color.rgb2hsv=function(r,g,b){
if(dojo.lang.isArray(r)){
b=r[2]||0;
g=r[1]||0;
r=r[0]||0;
}
var h=null;
var s=null;
var v=null;
var min=Math.min(r,g,b);
v=Math.max(r,g,b);
var _2f6=v-min;
s=(v==0)?0:_2f6/v;
if(s==0){
h=0;
}else{
if(r==v){
h=60*(g-b)/_2f6;
}else{
if(g==v){
h=120+60*(b-r)/_2f6;
}else{
if(b==v){
h=240+60*(r-g)/_2f6;
}
}
}
if(h<0){
h+=360;
}
}
h=(h==0)?360:Math.ceil((h/360)*255);
s=Math.ceil(s*255);
return [h,s,v];
};
dojo.graphics.color.hsv2rgb=function(h,s,v){
if(dojo.lang.isArray(h)){
v=h[2]||0;
s=h[1]||0;
h=h[0]||0;
}
h=(h/255)*360;
if(h==360){
h=0;
}
s=s/255;
v=v/255;
var r=null;
var g=null;
var b=null;
if(s==0){
r=v;
g=v;
b=v;
}else{
var _2fd=h/60;
var i=Math.floor(_2fd);
var f=_2fd-i;
var p=v*(1-s);
var q=v*(1-(s*f));
var t=v*(1-(s*(1-f)));
switch(i){
case 0:
r=v;
g=t;
b=p;
break;
case 1:
r=q;
g=v;
b=p;
break;
case 2:
r=p;
g=v;
b=t;
break;
case 3:
r=p;
g=q;
b=v;
break;
case 4:
r=t;
g=p;
b=v;
break;
case 5:
r=v;
g=p;
b=q;
break;
}
}
r=Math.ceil(r*255);
g=Math.ceil(g*255);
b=Math.ceil(b*255);
return [r,g,b];
};
dojo.graphics.color.rgb2hsl=function(r,g,b){
if(dojo.lang.isArray(r)){
b=r[2]||0;
g=r[1]||0;
r=r[0]||0;
}
r/=255;
g/=255;
b/=255;
var h=null;
var s=null;
var l=null;
var min=Math.min(r,g,b);
var max=Math.max(r,g,b);
var _30b=max-min;
l=(min+max)/2;
s=0;
if((l>0)&&(l<1)){
s=_30b/((l<0.5)?(2*l):(2-2*l));
}
h=0;
if(_30b>0){
if((max==r)&&(max!=g)){
h+=(g-b)/_30b;
}
if((max==g)&&(max!=b)){
h+=(2+(b-r)/_30b);
}
if((max==b)&&(max!=r)){
h+=(4+(r-g)/_30b);
}
h*=60;
}
h=(h==0)?360:Math.ceil((h/360)*255);
s=Math.ceil(s*255);
l=Math.ceil(l*255);
return [h,s,l];
};
dojo.graphics.color.hsl2rgb=function(h,s,l){
if(dojo.lang.isArray(h)){
l=h[2]||0;
s=h[1]||0;
h=h[0]||0;
}
h=(h/255)*360;
if(h==360){
h=0;
}
s=s/255;
l=l/255;
while(h<0){
h+=360;
}
while(h>360){
h-=360;
}
if(h<120){
r=(120-h)/60;
g=h/60;
b=0;
}else{
if(h<240){
r=0;
g=(240-h)/60;
b=(h-120)/60;
}else{
r=(h-240)/60;
g=0;
b=(360-h)/60;
}
}
r=Math.min(r,1);
g=Math.min(g,1);
b=Math.min(b,1);
r=2*s*r+(1-s);
g=2*s*g+(1-s);
b=2*s*b+(1-s);
if(l<0.5){
r=l*r;
g=l*g;
b=l*b;
}else{
r=(1-l)*r+2*l-1;
g=(1-l)*g+2*l-1;
b=(1-l)*b+2*l-1;
}
r=Math.ceil(r*255);
g=Math.ceil(g*255);
b=Math.ceil(b*255);
return [r,g,b];
};
dojo.provide("dojo.style");
dojo.require("dojo.dom");
dojo.require("dojo.uri.Uri");
dojo.require("dojo.graphics.color");
dojo.style.boxSizing={marginBox:"margin-box",borderBox:"border-box",paddingBox:"padding-box",contentBox:"content-box"};
dojo.style.getBoxSizing=function(node){
if(dojo.render.html.ie||dojo.render.html.opera){
var cm=document["compatMode"];
if(cm=="BackCompat"||cm=="QuirksMode"){
return dojo.style.boxSizing.borderBox;
}else{
return dojo.style.boxSizing.contentBox;
}
}else{
if(arguments.length==0){
node=document.documentElement;
}
var _311=dojo.style.getStyle(node,"-moz-box-sizing");
if(!_311){
_311=dojo.style.getStyle(node,"box-sizing");
}
return (_311?_311:dojo.style.boxSizing.contentBox);
}
};
dojo.style.isBorderBox=function(node){
return (dojo.style.getBoxSizing(node)==dojo.style.boxSizing.borderBox);
};
dojo.style.getUnitValue=function(_313,_314,_315){
var _316={value:0,units:"px"};
var s=dojo.style.getComputedStyle(_313,_314);
if(s==""||(s=="auto"&&_315)){
return _316;
}
if(dojo.lang.isUndefined(s)){
_316.value=NaN;
}else{
var _318=s.match(/([\d.]+)([a-z%]*)/i);
if(!_318){
_316.value=NaN;
}else{
_316.value=Number(_318[1]);
_316.units=_318[2].toLowerCase();
}
}
return _316;
};
dojo.style.getPixelValue=function(_319,_31a,_31b){
var _31c=dojo.style.getUnitValue(_319,_31a,_31b);
if(isNaN(_31c.value)||(_31c.value&&_31c.units!="px")){
return NaN;
}
return _31c.value;
};
dojo.style.getNumericStyle=dojo.style.getPixelValue;
dojo.style.isPositionAbsolute=function(node){
return (dojo.style.getComputedStyle(node,"position")=="absolute");
};
dojo.style.getMarginWidth=function(node){
var _31f=dojo.style.isPositionAbsolute(node);
var left=dojo.style.getPixelValue(node,"margin-left",_31f);
var _321=dojo.style.getPixelValue(node,"margin-right",_31f);
return left+_321;
};
dojo.style.getBorderWidth=function(node){
var left=(dojo.style.getStyle(node,"border-left-style")=="none"?0:dojo.style.getPixelValue(node,"border-left-width"));
var _324=(dojo.style.getStyle(node,"border-right-style")=="none"?0:dojo.style.getPixelValue(node,"border-right-width"));
return left+_324;
};
dojo.style.getPaddingWidth=function(node){
var left=dojo.style.getPixelValue(node,"padding-left",true);
var _327=dojo.style.getPixelValue(node,"padding-right",true);
return left+_327;
};
dojo.style.getContentWidth=function(node){
return node.offsetWidth-dojo.style.getPaddingWidth(node)-dojo.style.getBorderWidth(node);
};
dojo.style.getInnerWidth=function(node){
return node.offsetWidth;
};
dojo.style.getOuterWidth=function(node){
return dojo.style.getInnerWidth(node)+dojo.style.getMarginWidth(node);
};
dojo.style.setOuterWidth=function(node,_32c){
if(!dojo.style.isBorderBox(node)){
_32c-=dojo.style.getPaddingWidth(node)+dojo.style.getBorderWidth(node);
}
_32c-=dojo.style.getMarginWidth(node);
if(!isNaN(_32c)&&_32c>0){
node.style.width=_32c+"px";
return true;
}else{
return false;
}
};
dojo.style.getContentBoxWidth=dojo.style.getContentWidth;
dojo.style.getBorderBoxWidth=dojo.style.getInnerWidth;
dojo.style.getMarginBoxWidth=dojo.style.getOuterWidth;
dojo.style.setMarginBoxWidth=dojo.style.setOuterWidth;
dojo.style.getMarginHeight=function(node){
var _32e=dojo.style.isPositionAbsolute(node);
var top=dojo.style.getPixelValue(node,"margin-top",_32e);
var _330=dojo.style.getPixelValue(node,"margin-bottom",_32e);
return top+_330;
};
dojo.style.getBorderHeight=function(node){
var top=(dojo.style.getStyle(node,"border-top-style")=="none"?0:dojo.style.getPixelValue(node,"border-top-width"));
var _333=(dojo.style.getStyle(node,"border-bottom-style")=="none"?0:dojo.style.getPixelValue(node,"border-bottom-width"));
return top+_333;
};
dojo.style.getPaddingHeight=function(node){
var top=dojo.style.getPixelValue(node,"padding-top",true);
var _336=dojo.style.getPixelValue(node,"padding-bottom",true);
return top+_336;
};
dojo.style.getContentHeight=function(node){
return node.offsetHeight-dojo.style.getPaddingHeight(node)-dojo.style.getBorderHeight(node);
};
dojo.style.getInnerHeight=function(node){
return node.offsetHeight;
};
dojo.style.getOuterHeight=function(node){
return dojo.style.getInnerHeight(node)+dojo.style.getMarginHeight(node);
};
dojo.style.setOuterHeight=function(node,_33b){
if(!dojo.style.isBorderBox(node)){
_33b-=dojo.style.getPaddingHeight(node)+dojo.style.getBorderHeight(node);
}
_33b-=dojo.style.getMarginHeight(node);
if(!isNaN(_33b)&&_33b>0){
node.style.height=_33b+"px";
return true;
}else{
return false;
}
};
dojo.style.setContentWidth=function(node,_33d){
if(dojo.style.isBorderBox(node)){
_33d+=dojo.style.getPaddingWidth(node)+dojo.style.getBorderWidth(node);
}
if(!isNaN(_33d)&&_33d>0){
node.style.width=_33d+"px";
return true;
}else{
return false;
}
};
dojo.style.setContentHeight=function(node,_33f){
if(dojo.style.isBorderBox(node)){
_33f+=dojo.style.getPaddingHeight(node)+dojo.style.getBorderHeight(node);
}
if(!isNaN(_33f)&&_33f>0){
node.style.height=_33f+"px";
return true;
}else{
return false;
}
};
dojo.style.getContentBoxHeight=dojo.style.getContentHeight;
dojo.style.getBorderBoxHeight=dojo.style.getInnerHeight;
dojo.style.getMarginBoxHeight=dojo.style.getOuterHeight;
dojo.style.setMarginBoxHeight=dojo.style.setOuterHeight;
dojo.style.getTotalOffset=function(node,type,_342){
var _343=(type=="top")?"offsetTop":"offsetLeft";
var _344=(type=="top")?"scrollTop":"scrollLeft";
var alt=(type=="top")?"y":"x";
var ret=0;
if(node["offsetParent"]){
if(_342&&node.parentNode!=document.body){
ret-=dojo.style.sumAncestorProperties(node,_344);
}
do{
ret+=node[_343];
node=node.offsetParent;
}while(node!=document.getElementsByTagName("body")[0].parentNode&&node!=null);
}else{
if(node[alt]){
ret+=node[alt];
}
}
return ret;
};
dojo.style.sumAncestorProperties=function(node,prop){
if(!node){
return 0;
}
var _349=0;
while(node){
var val=node[prop];
if(val){
_349+=val-0;
}
node=node.parentNode;
}
return _349;
};
dojo.style.totalOffsetLeft=function(node,_34c){
return dojo.style.getTotalOffset(node,"left",_34c);
};
dojo.style.getAbsoluteX=dojo.style.totalOffsetLeft;
dojo.style.totalOffsetTop=function(node,_34e){
return dojo.style.getTotalOffset(node,"top",_34e);
};
dojo.style.getAbsoluteY=dojo.style.totalOffsetTop;
dojo.style.getAbsolutePosition=function(node,_350){
var _351=[dojo.style.getAbsoluteX(node,_350),dojo.style.getAbsoluteY(node,_350)];
_351.x=_351[0];
_351.y=_351[1];
return _351;
};
dojo.style.styleSheet=null;
dojo.style.insertCssRule=function(_352,_353,_354){
if(!dojo.style.styleSheet){
if(document.createStyleSheet){
dojo.style.styleSheet=document.createStyleSheet();
}else{
if(document.styleSheets[0]){
dojo.style.styleSheet=document.styleSheets[0];
}else{
return null;
}
}
}
if(arguments.length<3){
if(dojo.style.styleSheet.cssRules){
_354=dojo.style.styleSheet.cssRules.length;
}else{
if(dojo.style.styleSheet.rules){
_354=dojo.style.styleSheet.rules.length;
}else{
return null;
}
}
}
if(dojo.style.styleSheet.insertRule){
var rule=_352+" { "+_353+" }";
return dojo.style.styleSheet.insertRule(rule,_354);
}else{
if(dojo.style.styleSheet.addRule){
return dojo.style.styleSheet.addRule(_352,_353,_354);
}else{
return null;
}
}
};
dojo.style.removeCssRule=function(_356){
if(!dojo.style.styleSheet){
dojo.debug("no stylesheet defined for removing rules");
return false;
}
if(dojo.render.html.ie){
if(!_356){
_356=dojo.style.styleSheet.rules.length;
dojo.style.styleSheet.removeRule(_356);
}
}else{
if(document.styleSheets[0]){
if(!_356){
_356=dojo.style.styleSheet.cssRules.length;
}
dojo.style.styleSheet.deleteRule(_356);
}
}
return true;
};
dojo.style.insertCssFile=function(URI,doc,_359){
if(!URI){
return;
}
if(!doc){
doc=document;
}
if(doc.baseURI){
URI=new dojo.uri.Uri(doc.baseURI,URI);
}
if(_359&&doc.styleSheets){
var loc=location.href.split("#")[0].substring(0,location.href.indexOf(location.pathname));
for(var i=0;i<doc.styleSheets.length;i++){
if(doc.styleSheets[i].href&&URI.toString()==new dojo.uri.Uri(doc.styleSheets[i].href.toString())){
return;
}
}
}
var file=doc.createElement("link");
file.setAttribute("type","text/css");
file.setAttribute("rel","stylesheet");
file.setAttribute("href",URI);
var head=doc.getElementsByTagName("head")[0];
if(head){
head.appendChild(file);
}
};
dojo.style.getBackgroundColor=function(node){
var _35f;
do{
_35f=dojo.style.getStyle(node,"background-color");
if(_35f.toLowerCase()=="rgba(0, 0, 0, 0)"){
_35f="transparent";
}
if(node==document.getElementsByTagName("body")[0]){
node=null;
break;
}
node=node.parentNode;
}while(node&&dojo.lang.inArray(_35f,["transparent",""]));
if(_35f=="transparent"){
_35f=[255,255,255,0];
}else{
_35f=dojo.graphics.color.extractRGB(_35f);
}
return _35f;
};
dojo.style.getComputedStyle=function(_360,_361,_362){
var _363=_362;
if(_360.style.getPropertyValue){
_363=_360.style.getPropertyValue(_361);
}
if(!_363){
if(document.defaultView){
_363=document.defaultView.getComputedStyle(_360,"").getPropertyValue(_361);
}else{
if(_360.currentStyle){
_363=_360.currentStyle[dojo.style.toCamelCase(_361)];
}
}
}
return _363;
};
dojo.style.getStyle=function(_364,_365){
var _366=dojo.style.toCamelCase(_365);
var _367=_364.style[_366];
return (_367?_367:dojo.style.getComputedStyle(_364,_365,_367));
};
dojo.style.toCamelCase=function(_368){
var arr=_368.split("-"),cc=arr[0];
for(var i=1;i<arr.length;i++){
cc+=arr[i].charAt(0).toUpperCase()+arr[i].substring(1);
}
return cc;
};
dojo.style.toSelectorCase=function(_36b){
return _36b.replace(/([A-Z])/g,"-$1").toLowerCase();
};
dojo.style.setOpacity=function setOpacity(node,_36d,_36e){
node=dojo.byId(node);
var h=dojo.render.html;
if(!_36e){
if(_36d>=1){
if(h.ie){
dojo.style.clearOpacity(node);
return;
}else{
_36d=0.999999;
}
}else{
if(_36d<0){
_36d=0;
}
}
}
if(h.ie){
if(node.nodeName.toLowerCase()=="tr"){
var tds=node.getElementsByTagName("td");
for(var x=0;x<tds.length;x++){
tds[x].style.filter="Alpha(Opacity="+_36d*100+")";
}
}
node.style.filter="Alpha(Opacity="+_36d*100+")";
}else{
if(h.moz){
node.style.opacity=_36d;
node.style.MozOpacity=_36d;
}else{
if(h.safari){
node.style.opacity=_36d;
node.style.KhtmlOpacity=_36d;
}else{
node.style.opacity=_36d;
}
}
}
};
dojo.style.getOpacity=function getOpacity(node){
if(dojo.render.html.ie){
var opac=(node.filters&&node.filters.alpha&&typeof node.filters.alpha.opacity=="number"?node.filters.alpha.opacity:100)/100;
}else{
var opac=node.style.opacity||node.style.MozOpacity||node.style.KhtmlOpacity||1;
}
return opac>=0.999999?1:Number(opac);
};
dojo.style.clearOpacity=function clearOpacity(node){
var h=dojo.render.html;
if(h.ie){
if(node.filters&&node.filters.alpha){
node.style.filter="";
}
}else{
if(h.moz){
node.style.opacity=1;
node.style.MozOpacity=1;
}else{
if(h.safari){
node.style.opacity=1;
node.style.KhtmlOpacity=1;
}else{
node.style.opacity=1;
}
}
}
};
dojo.provide("dojo.html");
dojo.require("dojo.dom");
dojo.require("dojo.style");
dojo.require("dojo.string");
dojo.lang.mixin(dojo.html,dojo.dom);
dojo.lang.mixin(dojo.html,dojo.style);
dojo.html.clearSelection=function(){
try{
if(window["getSelection"]){
if(dojo.render.html.safari){
window.getSelection().collapse();
}else{
window.getSelection().removeAllRanges();
}
}else{
if((document.selection)&&(document.selection.clear)){
document.selection.clear();
}
}
return true;
}
catch(e){
dojo.debug(e);
return false;
}
};
dojo.html.disableSelection=function(_376){
_376=_376||dojo.html.body();
var h=dojo.render.html;
if(h.mozilla){
_376.style.MozUserSelect="none";
}else{
if(h.safari){
_376.style.KhtmlUserSelect="none";
}else{
if(h.ie){
_376.unselectable="on";
}else{
return false;
}
}
}
return true;
};
dojo.html.enableSelection=function(_378){
_378=_378||dojo.html.body();
var h=dojo.render.html;
if(h.mozilla){
_378.style.MozUserSelect="";
}else{
if(h.safari){
_378.style.KhtmlUserSelect="";
}else{
if(h.ie){
_378.unselectable="off";
}else{
return false;
}
}
}
return true;
};
dojo.html.selectElement=function(_37a){
if(document.selection&&dojo.html.body().createTextRange){
var _37b=dojo.html.body().createTextRange();
_37b.moveToElementText(_37a);
_37b.select();
}else{
if(window["getSelection"]){
var _37c=window.getSelection();
if(_37c["selectAllChildren"]){
_37c.selectAllChildren(_37a);
}
}
}
};
dojo.html.isSelectionCollapsed=function(){
if(document["selection"]){
return document.selection.createRange().text=="";
}else{
if(window["getSelection"]){
var _37d=window.getSelection();
if(dojo.lang.isString(_37d)){
return _37d=="";
}else{
return _37d.isCollapsed;
}
}
}
};
dojo.html.getEventTarget=function(evt){
if(!evt){
evt=window.event||{};
}
if(evt.srcElement){
return evt.srcElement;
}else{
if(evt.target){
return evt.target;
}
}
return null;
};
dojo.html.getScrollTop=function(){
return document.documentElement.scrollTop||dojo.html.body().scrollTop||0;
};
dojo.html.getScrollLeft=function(){
return document.documentElement.scrollLeft||dojo.html.body().scrollLeft||0;
};
dojo.html.getDocumentWidth=function(){
dojo.deprecated("dojo.html.getDocument* has been deprecated in favor of dojo.html.getViewport*");
return dojo.html.getViewportWidth();
};
dojo.html.getDocumentHeight=function(){
dojo.deprecated("dojo.html.getDocument* has been deprecated in favor of dojo.html.getViewport*");
return dojo.html.getViewportHeight();
};
dojo.html.getDocumentSize=function(){
dojo.deprecated("dojo.html.getDocument* has been deprecated in favor of dojo.html.getViewport*");
return dojo.html.getViewportSize();
};
dojo.html.getViewportWidth=function(){
var w=0;
if(window.innerWidth){
w=window.innerWidth;
}
if(dojo.exists(document,"documentElement.clientWidth")){
var w2=document.documentElement.clientWidth;
if(!w||w2&&w2<w){
w=w2;
}
return w;
}
if(document.body){
return document.body.clientWidth;
}
return 0;
};
dojo.html.getViewportHeight=function(){
if(window.innerHeight){
return window.innerHeight;
}
if(dojo.exists(document,"documentElement.clientHeight")){
return document.documentElement.clientHeight;
}
if(document.body){
return document.body.clientHeight;
}
return 0;
};
dojo.html.getViewportSize=function(){
var ret=[dojo.html.getViewportWidth(),dojo.html.getViewportHeight()];
ret.w=ret[0];
ret.h=ret[1];
return ret;
};
dojo.html.getScrollOffset=function(){
var ret=[0,0];
if(window.pageYOffset){
ret=[window.pageXOffset,window.pageYOffset];
}else{
if(dojo.exists(document,"documentElement.scrollTop")){
ret=[document.documentElement.scrollLeft,document.documentElement.scrollTop];
}else{
if(document.body){
ret=[document.body.scrollLeft,document.body.scrollTop];
}
}
}
ret.x=ret[0];
ret.y=ret[1];
return ret;
};
dojo.html.getParentOfType=function(node,type){
dojo.deprecated("dojo.html.getParentOfType has been deprecated in favor of dojo.html.getParentByType*");
return dojo.html.getParentByType(node,type);
};
dojo.html.getParentByType=function(node,type){
var _387=node;
type=type.toLowerCase();
while((_387)&&(_387.nodeName.toLowerCase()!=type)){
if(_387==(document["body"]||document["documentElement"])){
return null;
}
_387=_387.parentNode;
}
return _387;
};
dojo.html.getAttribute=function(node,attr){
if((!node)||(!node.getAttribute)){
return null;
}
var ta=typeof attr=="string"?attr:new String(attr);
var v=node.getAttribute(ta.toUpperCase());
if((v)&&(typeof v=="string")&&(v!="")){
return v;
}
if(v&&v.value){
return v.value;
}
if((node.getAttributeNode)&&(node.getAttributeNode(ta))){
return (node.getAttributeNode(ta)).value;
}else{
if(node.getAttribute(ta)){
return node.getAttribute(ta);
}else{
if(node.getAttribute(ta.toLowerCase())){
return node.getAttribute(ta.toLowerCase());
}
}
}
return null;
};
dojo.html.hasAttribute=function(node,attr){
return dojo.html.getAttribute(node,attr)?true:false;
};
dojo.html.getClass=function(node){
if(!node){
return "";
}
var cs="";
if(node.className){
cs=node.className;
}else{
if(dojo.html.hasAttribute(node,"class")){
cs=dojo.html.getAttribute(node,"class");
}
}
return dojo.string.trim(cs);
};
dojo.html.getClasses=function(node){
var c=dojo.html.getClass(node);
return (c=="")?[]:c.split(/\s+/g);
};
dojo.html.hasClass=function(node,_393){
return dojo.lang.inArray(dojo.html.getClasses(node),_393);
};
dojo.html.prependClass=function(node,_395){
if(!node){
return false;
}
_395+=" "+dojo.html.getClass(node);
return dojo.html.setClass(node,_395);
};
dojo.html.addClass=function(node,_397){
if(!node){
return false;
}
if(dojo.html.hasClass(node,_397)){
return false;
}
_397=dojo.string.trim(dojo.html.getClass(node)+" "+_397);
return dojo.html.setClass(node,_397);
};
dojo.html.setClass=function(node,_399){
if(!node){
return false;
}
var cs=new String(_399);
try{
if(typeof node.className=="string"){
node.className=cs;
}else{
if(node.setAttribute){
node.setAttribute("class",_399);
node.className=cs;
}else{
return false;
}
}
}
catch(e){
dojo.debug("dojo.html.setClass() failed",e);
}
return true;
};
dojo.html.removeClass=function(node,_39c,_39d){
if(!node){
return false;
}
var _39c=dojo.string.trim(new String(_39c));
try{
var cs=dojo.html.getClasses(node);
var nca=[];
if(_39d){
for(var i=0;i<cs.length;i++){
if(cs[i].indexOf(_39c)==-1){
nca.push(cs[i]);
}
}
}else{
for(var i=0;i<cs.length;i++){
if(cs[i]!=_39c){
nca.push(cs[i]);
}
}
}
dojo.html.setClass(node,nca.join(" "));
}
catch(e){
dojo.debug("dojo.html.removeClass() failed",e);
}
return true;
};
dojo.html.replaceClass=function(node,_3a2,_3a3){
dojo.html.removeClass(node,_3a3);
dojo.html.addClass(node,_3a2);
};
dojo.html.classMatchType={ContainsAll:0,ContainsAny:1,IsOnly:2};
dojo.html.getElementsByClass=function(_3a4,_3a5,_3a6,_3a7){
if(!_3a5){
_3a5=document;
}
var _3a8=_3a4.split(/\s+/g);
var _3a9=[];
if(_3a7!=1&&_3a7!=2){
_3a7=0;
}
var _3aa=new RegExp("(\\s|^)(("+_3a8.join(")|(")+"))(\\s|$)");
if(!_3a6){
_3a6="*";
}
var _3ab=_3a5.getElementsByTagName(_3a6);
outer:
for(var i=0;i<_3ab.length;i++){
var node=_3ab[i];
var _3ae=dojo.html.getClasses(node);
if(_3ae.length==0){
continue outer;
}
var _3af=0;
for(var j=0;j<_3ae.length;j++){
if(_3aa.test(_3ae[j])){
if(_3a7==dojo.html.classMatchType.ContainsAny){
_3a9.push(node);
continue outer;
}else{
_3af++;
}
}else{
if(_3a7==dojo.html.classMatchType.IsOnly){
continue outer;
}
}
}
if(_3af==_3a8.length){
if(_3a7==dojo.html.classMatchType.IsOnly&&_3af==_3ae.length){
_3a9.push(node);
}else{
if(_3a7==dojo.html.classMatchType.ContainsAll){
_3a9.push(node);
}
}
}
}
return _3a9;
};
dojo.html.getElementsByClassName=dojo.html.getElementsByClass;
dojo.html.gravity=function(node,e){
var _3b3=e.pageX||e.clientX+dojo.html.body().scrollLeft;
var _3b4=e.pageY||e.clientY+dojo.html.body().scrollTop;
with(dojo.html){
var _3b5=getAbsoluteX(node)+(getInnerWidth(node)/2);
var _3b6=getAbsoluteY(node)+(getInnerHeight(node)/2);
}
with(dojo.html.gravity){
return ((_3b3<_3b5?WEST:EAST)|(_3b4<_3b6?NORTH:SOUTH));
}
};
dojo.html.gravity.NORTH=1;
dojo.html.gravity.SOUTH=1<<1;
dojo.html.gravity.EAST=1<<2;
dojo.html.gravity.WEST=1<<3;
dojo.html.overElement=function(_3b7,e){
var _3b9=e.pageX||e.clientX+dojo.html.body().scrollLeft;
var _3ba=e.pageY||e.clientY+dojo.html.body().scrollTop;
with(dojo.html){
var top=getAbsoluteY(_3b7);
var _3bc=top+getInnerHeight(_3b7);
var left=getAbsoluteX(_3b7);
var _3be=left+getInnerWidth(_3b7);
}
return (_3b9>=left&&_3b9<=_3be&&_3ba>=top&&_3ba<=_3bc);
};
dojo.html.renderedTextContent=function(node){
var _3c0="";
if(node==null){
return _3c0;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
var _3c2="unknown";
try{
_3c2=dojo.style.getStyle(node.childNodes[i],"display");
}
catch(E){
}
switch(_3c2){
case "block":
case "list-item":
case "run-in":
case "table":
case "table-row-group":
case "table-header-group":
case "table-footer-group":
case "table-row":
case "table-column-group":
case "table-column":
case "table-cell":
case "table-caption":
_3c0+="\n";
_3c0+=dojo.html.renderedTextContent(node.childNodes[i]);
_3c0+="\n";
break;
case "none":
break;
default:
if(node.childNodes[i].tagName&&node.childNodes[i].tagName.toLowerCase()=="br"){
_3c0+="\n";
}else{
_3c0+=dojo.html.renderedTextContent(node.childNodes[i]);
}
break;
}
break;
case 3:
case 2:
case 4:
var text=node.childNodes[i].nodeValue;
var _3c4="unknown";
try{
_3c4=dojo.style.getStyle(node,"text-transform");
}
catch(E){
}
switch(_3c4){
case "capitalize":
text=dojo.string.capitalize(text);
break;
case "uppercase":
text=text.toUpperCase();
break;
case "lowercase":
text=text.toLowerCase();
break;
default:
break;
}
switch(_3c4){
case "nowrap":
break;
case "pre-wrap":
break;
case "pre-line":
break;
case "pre":
break;
default:
text=text.replace(/\s+/," ");
if(/\s$/.test(_3c0)){
text.replace(/^\s/,"");
}
break;
}
_3c0+=text;
break;
default:
break;
}
}
return _3c0;
};
dojo.html.setActiveStyleSheet=function(_3c5){
var i,a,main;
for(i=0;(a=document.getElementsByTagName("link")[i]);i++){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")){
a.disabled=true;
if(a.getAttribute("title")==_3c5){
a.disabled=false;
}
}
}
};
dojo.html.getActiveStyleSheet=function(){
var i,a;
for(i=0;(a=document.getElementsByTagName("link")[i]);i++){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")&&!a.disabled){
return a.getAttribute("title");
}
}
return null;
};
dojo.html.getPreferredStyleSheet=function(){
var i,a;
for(i=0;(a=document.getElementsByTagName("link")[i]);i++){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("rel").indexOf("alt")==-1&&a.getAttribute("title")){
return a.getAttribute("title");
}
}
return null;
};
dojo.html.body=function(){
return document.body||document.getElementsByTagName("body")[0];
};
dojo.html.createNodesFromText=function(txt,wrap){
var tn=document.createElement("div");
tn.style.visibility="hidden";
document.body.appendChild(tn);
tn.innerHTML=txt;
tn.normalize();
if(wrap){
var ret=[];
var fc=tn.firstChild;
ret[0]=((fc.nodeValue==" ")||(fc.nodeValue=="\t"))?fc.nextSibling:fc;
document.body.removeChild(tn);
return ret;
}
var _3ce=[];
for(var x=0;x<tn.childNodes.length;x++){
_3ce.push(tn.childNodes[x].cloneNode(true));
}
tn.style.display="none";
document.body.removeChild(tn);
return _3ce;
};
if(!dojo.evalObjPath("dojo.dom.createNodesFromText")){
dojo.dom.createNodesFromText=function(){
dojo.deprecated("dojo.dom.createNodesFromText","use dojo.html.createNodesFromText instead");
return dojo.html.createNodesFromText.apply(dojo.html,arguments);
};
}
dojo.html.isVisible=function(node){
return dojo.style.getComputedStyle(node||this.domNode,"display")!="none";
};
dojo.html.show=function(node){
if(node.style){
node.style.display=dojo.lang.inArray(["tr","td","th"],node.tagName.toLowerCase())?"":"block";
}
};
dojo.html.hide=function(node){
if(node.style){
node.style.display="none";
}
};
dojo.html.toCoordinateArray=function(_3d3,_3d4){
if(dojo.lang.isArray(_3d3)){
while(_3d3.length<4){
_3d3.push(0);
}
while(_3d3.length>4){
_3d3.pop();
}
var ret=_3d3;
}else{
var node=dojo.byId(_3d3);
var ret=[dojo.html.getAbsoluteX(node,_3d4),dojo.html.getAbsoluteY(node,_3d4),dojo.html.getInnerWidth(node),dojo.html.getInnerHeight(node)];
}
ret.x=ret[0];
ret.y=ret[1];
ret.w=ret[2];
ret.h=ret[3];
return ret;
};
dojo.html.placeOnScreen=function(node,_3d8,_3d9,_3da,_3db){
if(dojo.lang.isArray(_3d8)){
_3db=_3da;
_3da=_3d9;
_3d9=_3d8[1];
_3d8=_3d8[0];
}
if(!isNaN(_3da)){
_3da=[Number(_3da),Number(_3da)];
}else{
if(!dojo.lang.isArray(_3da)){
_3da=[0,0];
}
}
var _3dc=dojo.html.getScrollOffset();
var view=dojo.html.getViewportSize();
node=dojo.byId(node);
var w=node.offsetWidth+_3da[0];
var h=node.offsetHeight+_3da[1];
if(_3db){
_3d8-=_3dc.x;
_3d9-=_3dc.y;
}
var x=_3d8+w;
if(x>view.w){
x=view.w-w;
}else{
x=_3d8;
}
x=Math.max(_3da[0],x)+_3dc.x;
var y=_3d9+h;
if(y>view.h){
y=view.h-h;
}else{
y=_3d9;
}
y=Math.max(_3da[1],y)+_3dc.y;
node.style.left=x+"px";
node.style.top=y+"px";
var ret=[x,y];
ret.x=x;
ret.y=y;
return ret;
};
dojo.html.placeOnScreenPoint=function(node,_3e4,_3e5,_3e6,_3e7){
if(dojo.lang.isArray(_3e4)){
_3e7=_3e6;
_3e6=_3e5;
_3e5=_3e4[1];
_3e4=_3e4[0];
}
var _3e8=dojo.html.getScrollOffset();
var view=dojo.html.getViewportSize();
node=dojo.byId(node);
var w=node.offsetWidth;
var h=node.offsetHeight;
if(_3e7){
_3e4-=_3e8.x;
_3e5-=_3e8.y;
}
var x=-1,y=-1;
if(_3e4+w<=view.w&&_3e5+h<=view.h){
x=_3e4;
y=_3e5;
}
if((x<0||y<0)&&_3e4<=view.w&&_3e5+h<=view.h){
x=_3e4-w;
y=_3e5;
}
if((x<0||y<0)&&_3e4+w<=view.w&&_3e5<=view.h){
x=_3e4;
y=_3e5-h;
}
if((x<0||y<0)&&_3e4<=view.w&&_3e5<=view.h){
x=_3e4-w;
y=_3e5-h;
}
if(x<0||y<0||(x+w>view.w)||(y+h>view.h)){
return dojo.html.placeOnScreen(node,_3e4,_3e5,_3e6,_3e7);
}
x+=_3e8.x;
y+=_3e8.y;
node.style.left=x+"px";
node.style.top=y+"px";
var ret=[x,y];
ret.x=x;
ret.y=y;
return ret;
};
dojo.html.BackgroundIframe=function(){
if(this.ie){
this.iframe=document.createElement("<iframe frameborder='0' src='about:blank'>");
var s=this.iframe.style;
s.position="absolute";
s.left=s.top="0px";
s.zIndex=2;
s.display="none";
dojo.style.setOpacity(this.iframe,0);
dojo.html.body().appendChild(this.iframe);
}else{
this.enabled=false;
}
};
dojo.lang.extend(dojo.html.BackgroundIframe,{ie:dojo.render.html.ie,enabled:true,visibile:false,iframe:null,sizeNode:null,sizeCoords:null,size:function(node){
if(!this.ie||!this.enabled){
return;
}
if(dojo.dom.isNode(node)){
this.sizeNode=node;
}else{
if(arguments.length>0){
this.sizeNode=null;
this.sizeCoords=node;
}
}
this.update();
},update:function(){
if(!this.ie||!this.enabled){
return;
}
if(this.sizeNode){
this.sizeCoords=dojo.html.toCoordinateArray(this.sizeNode,true);
}else{
if(this.sizeCoords){
this.sizeCoords=dojo.html.toCoordinateArray(this.sizeCoords,true);
}else{
return;
}
}
var s=this.iframe.style;
var dims=this.sizeCoords;
s.width=dims.w+"px";
s.height=dims.h+"px";
s.left=dims.x+"px";
s.top=dims.y+"px";
},setZIndex:function(node){
if(!this.ie||!this.enabled){
return;
}
if(dojo.dom.isNode(node)){
this.iframe.zIndex=dojo.html.getStyle(node,"z-index")-1;
}else{
if(!isNaN(node)){
this.iframe.zIndex=node;
}
}
},show:function(node){
if(!this.ie||!this.enabled){
return;
}
this.size(node);
this.iframe.style.display="block";
},hide:function(){
if(!this.ie){
return;
}
var s=this.iframe.style;
s.display="none";
s.width=s.height="1px";
},remove:function(){
dojo.dom.removeNode(this.iframe);
}});
dojo.provide("dojo.math.curves");
dojo.require("dojo.math");
dojo.math.curves={Line:function(_3f5,end){
this.start=_3f5;
this.end=end;
this.dimensions=_3f5.length;
for(var i=0;i<_3f5.length;i++){
_3f5[i]=Number(_3f5[i]);
}
for(var i=0;i<end.length;i++){
end[i]=Number(end[i]);
}
this.getValue=function(n){
var _3f9=new Array(this.dimensions);
for(var i=0;i<this.dimensions;i++){
_3f9[i]=((this.end[i]-this.start[i])*n)+this.start[i];
}
return _3f9;
};
return this;
},Bezier:function(pnts){
this.getValue=function(step){
if(step>=1){
return this.p[this.p.length-1];
}
if(step<=0){
return this.p[0];
}
var _3fd=new Array(this.p[0].length);
for(var k=0;j<this.p[0].length;k++){
_3fd[k]=0;
}
for(var j=0;j<this.p[0].length;j++){
var C=0;
var D=0;
for(var i=0;i<this.p.length;i++){
C+=this.p[i][j]*this.p[this.p.length-1][0]*dojo.math.bernstein(step,this.p.length,i);
}
for(var l=0;l<this.p.length;l++){
D+=this.p[this.p.length-1][0]*dojo.math.bernstein(step,this.p.length,l);
}
_3fd[j]=C/D;
}
return _3fd;
};
this.p=pnts;
return this;
},CatmullRom:function(pnts,c){
this.getValue=function(step){
var _407=step*(this.p.length-1);
var node=Math.floor(_407);
var _409=_407-node;
var i0=node-1;
if(i0<0){
i0=0;
}
var i=node;
var i1=node+1;
if(i1>=this.p.length){
i1=this.p.length-1;
}
var i2=node+2;
if(i2>=this.p.length){
i2=this.p.length-1;
}
var u=_409;
var u2=_409*_409;
var u3=_409*_409*_409;
var _411=new Array(this.p[0].length);
for(var k=0;k<this.p[0].length;k++){
var x1=(-this.c*this.p[i0][k])+((2-this.c)*this.p[i][k])+((this.c-2)*this.p[i1][k])+(this.c*this.p[i2][k]);
var x2=(2*this.c*this.p[i0][k])+((this.c-3)*this.p[i][k])+((3-2*this.c)*this.p[i1][k])+(-this.c*this.p[i2][k]);
var x3=(-this.c*this.p[i0][k])+(this.c*this.p[i1][k]);
var x4=this.p[i][k];
_411[k]=x1*u3+x2*u2+x3*u+x4;
}
return _411;
};
if(!c){
this.c=0.7;
}else{
this.c=c;
}
this.p=pnts;
return this;
},Arc:function(_417,end,ccw){
var _41a=dojo.math.points.midpoint(_417,end);
var _41b=dojo.math.points.translate(dojo.math.points.invert(_41a),_417);
var rad=Math.sqrt(Math.pow(_41b[0],2)+Math.pow(_41b[1],2));
var _41d=dojo.math.radToDeg(Math.atan(_41b[1]/_41b[0]));
if(_41b[0]<0){
_41d-=90;
}else{
_41d+=90;
}
dojo.math.curves.CenteredArc.call(this,_41a,rad,_41d,_41d+(ccw?-180:180));
},CenteredArc:function(_41e,_41f,_420,end){
this.center=_41e;
this.radius=_41f;
this.start=_420||0;
this.end=end;
this.getValue=function(n){
var _423=new Array(2);
var _424=dojo.math.degToRad(this.start+((this.end-this.start)*n));
_423[0]=this.center[0]+this.radius*Math.sin(_424);
_423[1]=this.center[1]-this.radius*Math.cos(_424);
return _423;
};
return this;
},Circle:function(_425,_426){
dojo.math.curves.CenteredArc.call(this,_425,_426,0,360);
return this;
},Path:function(){
var _427=[];
var _428=[];
var _429=[];
var _42a=0;
this.add=function(_42b,_42c){
if(_42c<0){
dojo.raise("dojo.math.curves.Path.add: weight cannot be less than 0");
}
_427.push(_42b);
_428.push(_42c);
_42a+=_42c;
computeRanges();
};
this.remove=function(_42d){
for(var i=0;i<_427.length;i++){
if(_427[i]==_42d){
_427.splice(i,1);
_42a-=_428.splice(i,1)[0];
break;
}
}
computeRanges();
};
this.removeAll=function(){
_427=[];
_428=[];
_42a=0;
};
this.getValue=function(n){
var _430=false,value=0;
for(var i=0;i<_429.length;i++){
var r=_429[i];
if(n>=r[0]&&n<r[1]){
var subN=(n-r[0])/r[2];
value=_427[i].getValue(subN);
_430=true;
break;
}
}
if(!_430){
value=_427[_427.length-1].getValue(1);
}
for(j=0;j<i;j++){
value=dojo.math.points.translate(value,_427[j].getValue(1));
}
return value;
};
function computeRanges(){
var _434=0;
for(var i=0;i<_428.length;i++){
var end=_434+_428[i]/_42a;
var len=end-_434;
_429[i]=[_434,end,len];
_434=end;
}
}
return this;
}};
dojo.provide("dojo.animation");
dojo.provide("dojo.animation.Animation");
dojo.require("dojo.lang");
dojo.require("dojo.math");
dojo.require("dojo.math.curves");
dojo.animation.Animation=function(_438,_439,_43a,_43b,rate){
this.curve=_438;
this.duration=_439;
this.repeatCount=_43b||0;
this.rate=rate||10;
if(_43a){
if(dojo.lang.isFunction(_43a.getValue)){
this.accel=_43a;
}else{
var i=0.35*_43a+0.5;
this.accel=new dojo.math.curves.CatmullRom([[0],[i],[1]],0.45);
}
}
};
dojo.lang.extend(dojo.animation.Animation,{curve:null,duration:0,repeatCount:0,accel:null,onBegin:null,onAnimate:null,onEnd:null,onPlay:null,onPause:null,onStop:null,handler:null,_animSequence:null,_startTime:null,_endTime:null,_lastFrame:null,_timer:null,_percent:0,_active:false,_paused:false,_startRepeatCount:0,play:function(_43e){
if(_43e){
clearTimeout(this._timer);
this._active=false;
this._paused=false;
this._percent=0;
}else{
if(this._active&&!this._paused){
return;
}
}
this._startTime=new Date().valueOf();
if(this._paused){
this._startTime-=(this.duration*this._percent/100);
}
this._endTime=this._startTime+this.duration;
this._lastFrame=this._startTime;
var e=new dojo.animation.AnimationEvent(this,null,this.curve.getValue(this._percent),this._startTime,this._startTime,this._endTime,this.duration,this._percent,0);
this._active=true;
this._paused=false;
if(this._percent==0){
if(!this._startRepeatCount){
this._startRepeatCount=this.repeatCount;
}
e.type="begin";
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onBegin=="function"){
this.onBegin(e);
}
}
e.type="play";
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onPlay=="function"){
this.onPlay(e);
}
if(this._animSequence){
this._animSequence._setCurrent(this);
}
this._cycle();
},pause:function(){
clearTimeout(this._timer);
if(!this._active){
return;
}
this._paused=true;
var e=new dojo.animation.AnimationEvent(this,"pause",this.curve.getValue(this._percent),this._startTime,new Date().valueOf(),this._endTime,this.duration,this._percent,0);
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onPause=="function"){
this.onPause(e);
}
},playPause:function(){
if(!this._active||this._paused){
this.play();
}else{
this.pause();
}
},gotoPercent:function(pct,_442){
clearTimeout(this._timer);
this._active=true;
this._paused=true;
this._percent=pct;
if(_442){
this.play();
}
},stop:function(_443){
clearTimeout(this._timer);
var step=this._percent/100;
if(_443){
step=1;
}
var e=new dojo.animation.AnimationEvent(this,"stop",this.curve.getValue(step),this._startTime,new Date().valueOf(),this._endTime,this.duration,this._percent,Math.round(fps));
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onStop=="function"){
this.onStop(e);
}
this._active=false;
this._paused=false;
},status:function(){
if(this._active){
return this._paused?"paused":"playing";
}else{
return "stopped";
}
},_cycle:function(){
clearTimeout(this._timer);
if(this._active){
var curr=new Date().valueOf();
var step=(curr-this._startTime)/(this._endTime-this._startTime);
fps=1000/(curr-this._lastFrame);
this._lastFrame=curr;
if(step>=1){
step=1;
this._percent=100;
}else{
this._percent=step*100;
}
if(this.accel&&this.accel.getValue){
step=this.accel.getValue(step);
}
var e=new dojo.animation.AnimationEvent(this,"animate",this.curve.getValue(step),this._startTime,curr,this._endTime,this.duration,this._percent,Math.round(fps));
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onAnimate=="function"){
this.onAnimate(e);
}
if(step<1){
this._timer=setTimeout(dojo.lang.hitch(this,"_cycle"),this.rate);
}else{
e.type="end";
this._active=false;
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onEnd=="function"){
this.onEnd(e);
}
if(this.repeatCount>0){
this.repeatCount--;
this.play(true);
}else{
if(this.repeatCount==-1){
this.play(true);
}else{
if(this._startRepeatCount){
this.repeatCount=this._startRepeatCount;
this._startRepeatCount=0;
}
if(this._animSequence){
this._animSequence._playNext();
}
}
}
}
}
}});
dojo.animation.AnimationEvent=function(anim,type,_44b,_44c,_44d,_44e,dur,pct,fps){
this.type=type;
this.animation=anim;
this.coords=_44b;
this.x=_44b[0];
this.y=_44b[1];
this.z=_44b[2];
this.startTime=_44c;
this.currentTime=_44d;
this.endTime=_44e;
this.duration=dur;
this.percent=pct;
this.fps=fps;
};
dojo.lang.extend(dojo.animation.AnimationEvent,{coordsAsInts:function(){
var _452=new Array(this.coords.length);
for(var i=0;i<this.coords.length;i++){
_452[i]=Math.round(this.coords[i]);
}
return _452;
}});
dojo.animation.AnimationSequence=function(_454){
this.repeatCount=_454||0;
};
dojo.lang.extend(dojo.animation.AnimationSequence,{repeateCount:0,_anims:[],_currAnim:-1,onBegin:null,onEnd:null,onNext:null,handler:null,add:function(){
for(var i=0;i<arguments.length;i++){
this._anims.push(arguments[i]);
arguments[i]._animSequence=this;
}
},remove:function(anim){
for(var i=0;i<this._anims.length;i++){
if(this._anims[i]==anim){
this._anims[i]._animSequence=null;
this._anims.splice(i,1);
break;
}
}
},removeAll:function(){
for(var i=0;i<this._anims.length;i++){
this._anims[i]._animSequence=null;
}
this._anims=[];
this._currAnim=-1;
},clear:function(){
this.removeAll();
},play:function(_459){
if(this._anims.length==0){
return;
}
if(_459||!this._anims[this._currAnim]){
this._currAnim=0;
}
if(this._anims[this._currAnim]){
if(this._currAnim==0){
var e={type:"begin",animation:this._anims[this._currAnim]};
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onBegin=="function"){
this.onBegin(e);
}
}
this._anims[this._currAnim].play(_459);
}
},pause:function(){
if(this._anims[this._currAnim]){
this._anims[this._currAnim].pause();
}
},playPause:function(){
if(this._anims.length==0){
return;
}
if(this._currAnim==-1){
this._currAnim=0;
}
if(this._anims[this._currAnim]){
this._anims[this._currAnim].playPause();
}
},stop:function(){
if(this._anims[this._currAnim]){
this._anims[this._currAnim].stop();
}
},status:function(){
if(this._anims[this._currAnim]){
return this._anims[this._currAnim].status();
}else{
return "stopped";
}
},_setCurrent:function(anim){
for(var i=0;i<this._anims.length;i++){
if(this._anims[i]==anim){
this._currAnim=i;
break;
}
}
},_playNext:function(){
if(this._currAnim==-1||this._anims.length==0){
return;
}
this._currAnim++;
if(this._anims[this._currAnim]){
var e={type:"next",animation:this._anims[this._currAnim]};
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onNext=="function"){
this.onNext(e);
}
this._anims[this._currAnim].play(true);
}else{
var e={type:"end",animation:this._anims[this._anims.length-1]};
if(typeof this.handler=="function"){
this.handler(e);
}
if(typeof this.onEnd=="function"){
this.onEnd(e);
}
if(this.repeatCount>0){
this._currAnim=0;
this.repeatCount--;
this._anims[this._currAnim].play(true);
}else{
if(this.repeatCount==-1){
this._currAnim=0;
this._anims[this._currAnim].play(true);
}else{
this._currAnim=-1;
}
}
}
}});
dojo.hostenv.conditionalLoadModule({common:["dojo.animation.Animation",false,false]});
dojo.hostenv.moduleLoaded("dojo.animation.*");
dojo.provide("dojo.fx.html");
dojo.require("dojo.html");
dojo.require("dojo.style");
dojo.require("dojo.lang");
dojo.require("dojo.animation.*");
dojo.require("dojo.event.*");
dojo.require("dojo.graphics.color");
dojo.fx.html._makeFadeable=function(node){
if(dojo.render.html.ie){
if((node.style.zoom.length==0)&&(dojo.style.getStyle(node,"zoom")=="normal")){
node.style.zoom="1";
}
if((node.style.width.length==0)&&(dojo.style.getStyle(node,"width")=="auto")){
node.style.width="auto";
}
}
};
dojo.fx.html.fadeOut=function(node,_460,_461,_462){
return dojo.fx.html.fade(node,_460,dojo.style.getOpacity(node),0,_461,_462);
};
dojo.fx.html.fadeIn=function(node,_464,_465,_466){
return dojo.fx.html.fade(node,_464,dojo.style.getOpacity(node),1,_465,_466);
};
dojo.fx.html.fadeHide=function(node,_468,_469,_46a){
node=dojo.byId(node);
if(!_468){
_468=150;
}
return dojo.fx.html.fadeOut(node,_468,function(node){
node.style.display="none";
if(typeof _469=="function"){
_469(node);
}
});
};
dojo.fx.html.fadeShow=function(node,_46d,_46e,_46f){
node=dojo.byId(node);
if(!_46d){
_46d=150;
}
node.style.display="block";
return dojo.fx.html.fade(node,_46d,0,1,_46e,_46f);
};
dojo.fx.html.fade=function(node,_471,_472,_473,_474,_475){
node=dojo.byId(node);
dojo.fx.html._makeFadeable(node);
var anim=new dojo.animation.Animation(new dojo.math.curves.Line([_472],[_473]),_471,0);
dojo.event.connect(anim,"onAnimate",function(e){
dojo.style.setOpacity(node,e.x);
});
if(_474){
dojo.event.connect(anim,"onEnd",function(e){
_474(node,anim);
});
}
if(!_475){
anim.play(true);
}
return anim;
};
dojo.fx.html.slideTo=function(node,_47a,_47b,_47c,_47d){
if(!dojo.lang.isNumber(_47a)){
var tmp=_47a;
_47a=_47b;
_47b=tmp;
}
node=dojo.byId(node);
var top=node.offsetTop;
var left=node.offsetLeft;
var pos=dojo.style.getComputedStyle(node,"position");
if(pos=="relative"||pos=="static"){
top=parseInt(dojo.style.getComputedStyle(node,"top"))||0;
left=parseInt(dojo.style.getComputedStyle(node,"left"))||0;
}
return dojo.fx.html.slide(node,_47a,[left,top],_47b,_47c,_47d);
};
dojo.fx.html.slideBy=function(node,_483,_484,_485,_486){
if(!dojo.lang.isNumber(_483)){
var tmp=_483;
_483=_484;
_484=tmp;
}
node=dojo.byId(node);
var top=node.offsetTop;
var left=node.offsetLeft;
var pos=dojo.style.getComputedStyle(node,"position");
if(pos=="relative"||pos=="static"){
top=parseInt(dojo.style.getComputedStyle(node,"top"))||0;
left=parseInt(dojo.style.getComputedStyle(node,"left"))||0;
}
return dojo.fx.html.slideTo(node,_483,[left+_484[0],top+_484[1]],_485,_486);
};
dojo.fx.html.slide=function(node,_48c,_48d,_48e,_48f,_490){
if(!dojo.lang.isNumber(_48c)){
var tmp=_48c;
_48c=_48e;
_48e=_48d;
_48d=tmp;
}
node=dojo.byId(node);
if(dojo.style.getComputedStyle(node,"position")=="static"){
node.style.position="relative";
}
var anim=new dojo.animation.Animation(new dojo.math.curves.Line(_48d,_48e),_48c,0);
dojo.event.connect(anim,"onAnimate",function(e){
with(node.style){
left=e.x+"px";
top=e.y+"px";
}
});
if(_48f){
dojo.event.connect(anim,"onEnd",function(e){
_48f(node,anim);
});
}
if(!_490){
anim.play(true);
}
return anim;
};
dojo.fx.html.colorFadeIn=function(node,_496,_497,_498,_499,_49a){
if(!dojo.lang.isNumber(_496)){
var tmp=_496;
_496=_497;
_497=tmp;
}
node=dojo.byId(node);
var _49c=dojo.html.getBackgroundColor(node);
var bg=dojo.style.getStyle(node,"background-color").toLowerCase();
var _49e=bg=="transparent"||bg=="rgba(0, 0, 0, 0)";
while(_49c.length>3){
_49c.pop();
}
var rgb=new dojo.graphics.color.Color(_497).toRgb();
var anim=dojo.fx.html.colorFade(node,_496,_497,_49c,_499,true);
dojo.event.connect(anim,"onEnd",function(e){
if(_49e){
node.style.backgroundColor="transparent";
}
});
if(_498>0){
node.style.backgroundColor="rgb("+rgb.join(",")+")";
if(!_49a){
setTimeout(function(){
anim.play(true);
},_498);
}
}else{
if(!_49a){
anim.play(true);
}
}
return anim;
};
dojo.fx.html.highlight=dojo.fx.html.colorFadeIn;
dojo.fx.html.colorFadeFrom=dojo.fx.html.colorFadeIn;
dojo.fx.html.colorFadeOut=function(node,_4a3,_4a4,_4a5,_4a6,_4a7){
if(!dojo.lang.isNumber(_4a3)){
var tmp=_4a3;
_4a3=_4a4;
_4a4=tmp;
}
node=dojo.byId(node);
var _4a9=new dojo.graphics.color.Color(dojo.html.getBackgroundColor(node)).toRgb();
var rgb=new dojo.graphics.color.Color(_4a4).toRgb();
var anim=dojo.fx.html.colorFade(node,_4a3,_4a9,rgb,_4a6,_4a5>0||_4a7);
if(_4a5>0){
node.style.backgroundColor="rgb("+_4a9.join(",")+")";
if(!_4a7){
setTimeout(function(){
anim.play(true);
},_4a5);
}
}
return anim;
};
dojo.fx.html.unhighlight=dojo.fx.html.colorFadeOut;
dojo.fx.html.colorFadeTo=dojo.fx.html.colorFadeOut;
dojo.fx.html.colorFade=function(node,_4ad,_4ae,_4af,_4b0,_4b1){
if(!dojo.lang.isNumber(_4ad)){
var tmp=_4ad;
_4ad=_4af;
_4af=_4ae;
_4ae=tmp;
}
node=dojo.byId(node);
var _4b3=new dojo.graphics.color.Color(_4ae).toRgb();
var _4b4=new dojo.graphics.color.Color(_4af).toRgb();
var anim=new dojo.animation.Animation(new dojo.math.curves.Line(_4b3,_4b4),_4ad,0);
dojo.event.connect(anim,"onAnimate",function(e){
node.style.backgroundColor="rgb("+e.coordsAsInts().join(",")+")";
});
if(_4b0){
dojo.event.connect(anim,"onEnd",function(e){
_4b0(node,anim);
});
}
if(!_4b1){
anim.play(true);
}
return anim;
};
dojo.fx.html.wipeIn=function(node,_4b9,_4ba,_4bb){
node=dojo.byId(node);
var _4bc=dojo.html.getStyle(node,"height");
var _4bd=dojo.lang.inArray(node.tagName.toLowerCase(),["tr","td","th"])?"":"block";
node.style.display=_4bd;
var _4be=node.offsetHeight;
var anim=dojo.fx.html.wipeInToHeight(node,_4b9,_4be,function(e){
node.style.height=_4bc||"auto";
if(_4ba){
_4ba(node,anim);
}
},_4bb);
};
dojo.fx.html.wipeInToHeight=function(node,_4c2,_4c3,_4c4,_4c5){
node=dojo.byId(node);
var _4c6=dojo.html.getStyle(node,"overflow");
node.style.height="0px";
node.style.display="none";
if(_4c6=="visible"){
node.style.overflow="hidden";
}
var _4c7=dojo.lang.inArray(node.tagName.toLowerCase(),["tr","td","th"])?"":"block";
node.style.display=_4c7;
var anim=new dojo.animation.Animation(new dojo.math.curves.Line([0],[_4c3]),_4c2,0);
dojo.event.connect(anim,"onAnimate",function(e){
node.style.height=Math.round(e.x)+"px";
});
dojo.event.connect(anim,"onEnd",function(e){
if(_4c6!="visible"){
node.style.overflow=_4c6;
}
if(_4c4){
_4c4(node,anim);
}
});
if(!_4c5){
anim.play(true);
}
return anim;
};
dojo.fx.html.wipeOut=function(node,_4cc,_4cd,_4ce){
node=dojo.byId(node);
var _4cf=dojo.html.getStyle(node,"overflow");
var _4d0=dojo.html.getStyle(node,"height");
var _4d1=node.offsetHeight;
node.style.overflow="hidden";
var anim=new dojo.animation.Animation(new dojo.math.curves.Line([_4d1],[0]),_4cc,0);
dojo.event.connect(anim,"onAnimate",function(e){
node.style.height=Math.round(e.x)+"px";
});
dojo.event.connect(anim,"onEnd",function(e){
node.style.display="none";
node.style.overflow=_4cf;
node.style.height=_4d0||"auto";
if(_4cd){
_4cd(node,anim);
}
});
if(!_4ce){
anim.play(true);
}
return anim;
};
dojo.fx.html.explode=function(_4d5,_4d6,_4d7,_4d8,_4d9){
var _4da=dojo.html.toCoordinateArray(_4d5);
var _4db=document.createElement("div");
with(_4db.style){
position="absolute";
border="1px solid black";
display="none";
}
dojo.html.body().appendChild(_4db);
_4d6=dojo.byId(_4d6);
with(_4d6.style){
visibility="hidden";
display="block";
}
var _4dc=dojo.html.toCoordinateArray(_4d6);
with(_4d6.style){
display="none";
visibility="visible";
}
var anim=new dojo.animation.Animation(new dojo.math.curves.Line(_4da,_4dc),_4d7,0);
dojo.event.connect(anim,"onBegin",function(e){
_4db.style.display="block";
});
dojo.event.connect(anim,"onAnimate",function(e){
with(_4db.style){
left=e.x+"px";
top=e.y+"px";
width=e.coords[2]+"px";
height=e.coords[3]+"px";
}
});
dojo.event.connect(anim,"onEnd",function(){
_4d6.style.display="block";
_4db.parentNode.removeChild(_4db);
if(_4d8){
_4d8(_4d6,anim);
}
});
if(!_4d9){
anim.play();
}
return anim;
};
dojo.fx.html.implode=function(_4e0,end,_4e2,_4e3,_4e4){
var _4e5=dojo.html.toCoordinateArray(_4e0);
var _4e6=dojo.html.toCoordinateArray(end);
_4e0=dojo.byId(_4e0);
var _4e7=document.createElement("div");
with(_4e7.style){
position="absolute";
border="1px solid black";
display="none";
}
dojo.html.body().appendChild(_4e7);
var anim=new dojo.animation.Animation(new dojo.math.curves.Line(_4e5,_4e6),_4e2,0);
dojo.event.connect(anim,"onBegin",function(e){
_4e0.style.display="none";
_4e7.style.display="block";
});
dojo.event.connect(anim,"onAnimate",function(e){
with(_4e7.style){
left=e.x+"px";
top=e.y+"px";
width=e.coords[2]+"px";
height=e.coords[3]+"px";
}
});
dojo.event.connect(anim,"onEnd",function(){
_4e7.parentNode.removeChild(_4e7);
if(_4e3){
_4e3(_4e0,anim);
}
});
if(!_4e4){
anim.play();
}
return anim;
};
dojo.fx.html.Exploder=function(_4eb,_4ec){
_4eb=dojo.byId(_4eb);
_4ec=dojo.byId(_4ec);
var _4ed=this;
this.waitToHide=500;
this.timeToShow=100;
this.waitToShow=200;
this.timeToHide=70;
this.autoShow=false;
this.autoHide=false;
var _4ee=null;
var _4ef=null;
var _4f0=null;
var _4f1=null;
var _4f2=null;
var _4f3=null;
this.showing=false;
this.onBeforeExplode=null;
this.onAfterExplode=null;
this.onBeforeImplode=null;
this.onAfterImplode=null;
this.onExploding=null;
this.onImploding=null;
this.timeShow=function(){
clearTimeout(_4f0);
_4f0=setTimeout(_4ed.show,_4ed.waitToShow);
};
this.show=function(){
clearTimeout(_4f0);
clearTimeout(_4f1);
if((_4ef&&_4ef.status()=="playing")||(_4ee&&_4ee.status()=="playing")||_4ed.showing){
return;
}
if(typeof _4ed.onBeforeExplode=="function"){
_4ed.onBeforeExplode(_4eb,_4ec);
}
_4ee=dojo.fx.html.explode(_4eb,_4ec,_4ed.timeToShow,function(e){
_4ed.showing=true;
if(typeof _4ed.onAfterExplode=="function"){
_4ed.onAfterExplode(_4eb,_4ec);
}
});
if(typeof _4ed.onExploding=="function"){
dojo.event.connect(_4ee,"onAnimate",this,"onExploding");
}
};
this.timeHide=function(){
clearTimeout(_4f0);
clearTimeout(_4f1);
if(_4ed.showing){
_4f1=setTimeout(_4ed.hide,_4ed.waitToHide);
}
};
this.hide=function(){
clearTimeout(_4f0);
clearTimeout(_4f1);
if(_4ee&&_4ee.status()=="playing"){
return;
}
_4ed.showing=false;
if(typeof _4ed.onBeforeImplode=="function"){
_4ed.onBeforeImplode(_4eb,_4ec);
}
_4ef=dojo.fx.html.implode(_4ec,_4eb,_4ed.timeToHide,function(e){
if(typeof _4ed.onAfterImplode=="function"){
_4ed.onAfterImplode(_4eb,_4ec);
}
});
if(typeof _4ed.onImploding=="function"){
dojo.event.connect(_4ef,"onAnimate",this,"onImploding");
}
};
dojo.event.connect(_4eb,"onclick",function(e){
if(_4ed.showing){
_4ed.hide();
}else{
_4ed.show();
}
});
dojo.event.connect(_4eb,"onmouseover",function(e){
if(_4ed.autoShow){
_4ed.timeShow();
}
});
dojo.event.connect(_4eb,"onmouseout",function(e){
if(_4ed.autoHide){
_4ed.timeHide();
}
});
dojo.event.connect(_4ec,"onmouseover",function(e){
clearTimeout(_4f1);
});
dojo.event.connect(_4ec,"onmouseout",function(e){
if(_4ed.autoHide){
_4ed.timeHide();
}
});
dojo.event.connect(document.documentElement||dojo.html.body(),"onclick",function(e){
if(_4ed.autoHide&&_4ed.showing&&!dojo.dom.isDescendantOf(e.target,_4ec)&&!dojo.dom.isDescendantOf(e.target,_4eb)){
_4ed.hide();
}
});
return this;
};
dojo.lang.mixin(dojo.fx,dojo.fx.html);
dojo.hostenv.conditionalLoadModule({browser:["dojo.fx.html"]});
dojo.hostenv.moduleLoaded("dojo.fx.*");
dojo.provide("dojo.graphics.htmlEffects");
dojo.require("dojo.fx.*");
dj_deprecated("dojo.graphics.htmlEffects is deprecated, use dojo.fx.html instead");
dojo.graphics.htmlEffects=dojo.fx.html;
dojo.hostenv.conditionalLoadModule({browser:["dojo.graphics.htmlEffects"]});
dojo.hostenv.moduleLoaded("dojo.graphics.*");

