/*
	Copyright (c) 2004-2006, The Dojo Foundation
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

if(typeof dojo=="undefined"){
var dj_global=this;
var dj_currentContext=this;
function dj_undef(_1,_2){
return (typeof (_2||dj_currentContext)[_1]=="undefined");
}
if(dj_undef("djConfig",this)){
var djConfig={};
}
if(dj_undef("dojo",this)){
var dojo={};
}
dojo.global=function(){
return dj_currentContext;
};
dojo.locale=djConfig.locale;
dojo.version={major:0,minor:0,patch:0,flag:"dev",revision:Number("$Rev: 5779 $".match(/[0-9]+/)[0]),toString:function(){
with(dojo.version){
return major+"."+minor+"."+patch+flag+" ("+revision+")";
}
}};
dojo.evalProp=function(_3,_4,_5){
if((!_4)||(!_3)){
return undefined;
}
if(!dj_undef(_3,_4)){
return _4[_3];
}
return (_5?(_4[_3]={}):undefined);
};
dojo.parseObjPath=function(_6,_7,_8){
var _9=(_7||dojo.global());
var _a=_6.split(".");
var _b=_a.pop();
for(var i=0,l=_a.length;i<l&&_9;i++){
_9=dojo.evalProp(_a[i],_9,_8);
}
return {obj:_9,prop:_b};
};
dojo.evalObjPath=function(_e,_f){
if(typeof _e!="string"){
return dojo.global();
}
if(_e.indexOf(".")==-1){
return dojo.evalProp(_e,dojo.global(),_f);
}
var ref=dojo.parseObjPath(_e,dojo.global(),_f);
if(ref){
return dojo.evalProp(ref.prop,ref.obj,_f);
}
return null;
};
dojo.errorToString=function(_11){
if(!dj_undef("message",_11)){
return _11.message;
}else{
if(!dj_undef("description",_11)){
return _11.description;
}else{
return _11;
}
}
};
dojo.raise=function(_12,_13){
if(_13){
_12=_12+": "+dojo.errorToString(_13);
}
try{
if(djConfig.isDebug){
dojo.hostenv.println("FATAL exception raised: "+_12);
}
}
catch(e){
}
throw _13||Error(_12);
};
dojo.debug=function(){
};
dojo.debugShallow=function(obj){
};
dojo.profile={start:function(){
},end:function(){
},stop:function(){
},dump:function(){
}};
function dj_eval(_15){
return dj_global.eval?dj_global.eval(_15):eval(_15);
}
dojo.unimplemented=function(_16,_17){
var _18="'"+_16+"' not implemented";
if(_17!=null){
_18+=" "+_17;
}
dojo.raise(_18);
};
dojo.deprecated=function(_19,_1a,_1b){
var _1c="DEPRECATED: "+_19;
if(_1a){
_1c+=" "+_1a;
}
if(_1b){
_1c+=" -- will be removed in version: "+_1b;
}
dojo.debug(_1c);
};
dojo.render=(function(){
function vscaffold(_1d,_1e){
var tmp={capable:false,support:{builtin:false,plugin:false},prefixes:_1d};
for(var i=0;i<_1e.length;i++){
tmp[_1e[i]]=false;
}
return tmp;
}
return {name:"",ver:dojo.version,os:{win:false,linux:false,osx:false},html:vscaffold(["html"],["ie","opera","khtml","safari","moz"]),svg:vscaffold(["svg"],["corel","adobe","batik"]),vml:vscaffold(["vml"],["ie"]),swf:vscaffold(["Swf","Flash","Mm"],["mm"]),swt:vscaffold(["Swt"],["ibm"])};
})();
dojo.hostenv=(function(){
var _21={isDebug:false,allowQueryConfig:false,baseScriptUri:"",baseRelativePath:"",libraryScriptUri:"",iePreventClobber:false,ieClobberMinimal:true,preventBackButtonFix:true,searchIds:[],parseWidgets:true};
if(typeof djConfig=="undefined"){
djConfig=_21;
}else{
for(var _22 in _21){
if(typeof djConfig[_22]=="undefined"){
djConfig[_22]=_21[_22];
}
}
}
return {name_:"(unset)",version_:"(unset)",getName:function(){
return this.name_;
},getVersion:function(){
return this.version_;
},getText:function(uri){
dojo.unimplemented("getText","uri="+uri);
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
var _25=uri.lastIndexOf("/");
djConfig.baseScriptUri=djConfig.baseRelativePath;
return djConfig.baseScriptUri;
};
(function(){
var _26={pkgFileName:"__package__",loading_modules_:{},loaded_modules_:{},addedToLoadingCount:[],removedFromLoadingCount:[],inFlightCount:0,modulePrefixes_:{dojo:{name:"dojo",value:"src"}},setModulePrefix:function(_27,_28){
this.modulePrefixes_[_27]={name:_27,value:_28};
},moduleHasPrefix:function(_29){
var mp=this.modulePrefixes_;
return Boolean(mp[_29]&&mp[_29].value);
},getModulePrefix:function(_2b){
if(this.moduleHasPrefix(_2b)){
return this.modulePrefixes_[_2b].value;
}
return _2b;
},getTextStack:[],loadUriStack:[],loadedUris:[],post_load_:false,modulesLoadedListeners:[],unloadListeners:[],loadNotifying:false};
for(var _2c in _26){
dojo.hostenv[_2c]=_26[_2c];
}
})();
dojo.hostenv.loadPath=function(_2d,_2e,cb){
var uri;
if(_2d.charAt(0)=="/"||_2d.match(/^\w+:/)){
uri=_2d;
}else{
uri=this.getBaseScriptUri()+_2d;
}
if(djConfig.cacheBust&&dojo.render.html.capable){
uri+="?"+String(djConfig.cacheBust).replace(/\W+/g,"");
}
try{
return !_2e?this.loadUri(uri,cb):this.loadUriAndCheck(uri,_2e,cb);
}
catch(e){
dojo.debug(e);
return false;
}
};
dojo.hostenv.loadUri=function(uri,cb){
if(this.loadedUris[uri]){
return true;
}
var _33=this.getText(uri,null,true);
if(!_33){
return false;
}
this.loadedUris[uri]=true;
if(cb){
_33="("+_33+")";
}
var _34=dj_eval(_33);
if(cb){
cb(_34);
}
return true;
};
dojo.hostenv.loadUriAndCheck=function(uri,_36,cb){
var ok=true;
try{
ok=this.loadUri(uri,cb);
}
catch(e){
dojo.debug("failed loading ",uri," with error: ",e);
}
return Boolean(ok&&this.findModule(_36,false));
};
dojo.loaded=function(){
};
dojo.unloaded=function(){
};
dojo.hostenv.loaded=function(){
this.loadNotifying=true;
this.post_load_=true;
var mll=this.modulesLoadedListeners;
for(var x=0;x<mll.length;x++){
mll[x]();
}
this.modulesLoadedListeners=[];
this.loadNotifying=false;
dojo.loaded();
};
dojo.hostenv.unloaded=function(){
var mll=this.unloadListeners;
while(mll.length){
(mll.pop())();
}
dojo.unloaded();
};
dojo.addOnLoad=function(obj,_3d){
var dh=dojo.hostenv;
if(arguments.length==1){
dh.modulesLoadedListeners.push(obj);
}else{
if(arguments.length>1){
dh.modulesLoadedListeners.push(function(){
obj[_3d]();
});
}
}
if(dh.post_load_&&dh.inFlightCount==0&&!dh.loadNotifying){
dh.callLoaded();
}
};
dojo.addOnUnload=function(obj,_40){
var dh=dojo.hostenv;
if(arguments.length==1){
dh.unloadListeners.push(obj);
}else{
if(arguments.length>1){
dh.unloadListeners.push(function(){
obj[_40]();
});
}
}
};
dojo.hostenv.modulesLoaded=function(){
if(this.post_load_){
return;
}
if(this.loadUriStack.length==0&&this.getTextStack.length==0){
if(this.inFlightCount>0){
dojo.debug("files still in flight!");
return;
}
dojo.hostenv.callLoaded();
}
};
dojo.hostenv.callLoaded=function(){
if(typeof setTimeout=="object"){
setTimeout("dojo.hostenv.loaded();",0);
}else{
dojo.hostenv.loaded();
}
};
dojo.hostenv.getModuleSymbols=function(_42){
var _43=_42.split(".");
for(var i=_43.length;i>0;i--){
var _45=_43.slice(0,i).join(".");
if((i==1)&&!this.moduleHasPrefix(_45)){
_43[0]="../"+_43[0];
}else{
var _46=this.getModulePrefix(_45);
if(_46!=_45){
_43.splice(0,i,_46);
break;
}
}
}
return _43;
};
dojo.hostenv._global_omit_module_check=false;
dojo.hostenv.loadModule=function(_47,_48,_49){
if(!_47){
return;
}
_49=this._global_omit_module_check||_49;
var _4a=this.findModule(_47,false);
if(_4a){
return _4a;
}
if(dj_undef(_47,this.loading_modules_)){
this.addedToLoadingCount.push(_47);
}
this.loading_modules_[_47]=1;
var _4b=_47.replace(/\./g,"/")+".js";
var _4c=_47.split(".");
var _4d=this.getModuleSymbols(_47);
var _4e=((_4d[0].charAt(0)!="/")&&!_4d[0].match(/^\w+:/));
var _4f=_4d[_4d.length-1];
var ok;
if(_4f=="*"){
_47=_4c.slice(0,-1).join(".");
while(_4d.length){
_4d.pop();
_4d.push(this.pkgFileName);
_4b=_4d.join("/")+".js";
if(_4e&&_4b.charAt(0)=="/"){
_4b=_4b.slice(1);
}
ok=this.loadPath(_4b,!_49?_47:null);
if(ok){
break;
}
_4d.pop();
}
}else{
_4b=_4d.join("/")+".js";
_47=_4c.join(".");
var _51=!_49?_47:null;
ok=this.loadPath(_4b,_51);
if(!ok&&!_48){
_4d.pop();
while(_4d.length){
_4b=_4d.join("/")+".js";
ok=this.loadPath(_4b,_51);
if(ok){
break;
}
_4d.pop();
_4b=_4d.join("/")+"/"+this.pkgFileName+".js";
if(_4e&&_4b.charAt(0)=="/"){
_4b=_4b.slice(1);
}
ok=this.loadPath(_4b,_51);
if(ok){
break;
}
}
}
if(!ok&&!_49){
dojo.raise("Could not load '"+_47+"'; last tried '"+_4b+"'");
}
}
if(!_49&&!this["isXDomain"]){
_4a=this.findModule(_47,false);
if(!_4a){
dojo.raise("symbol '"+_47+"' is not defined after loading '"+_4b+"'");
}
}
return _4a;
};
dojo.hostenv.startPackage=function(_52){
var _53=String(_52);
var _54=_53;
var _55=_52.split(/\./);
if(_55[_55.length-1]=="*"){
_55.pop();
_54=_55.join(".");
}
var _56=dojo.evalObjPath(_54,true);
this.loaded_modules_[_53]=_56;
this.loaded_modules_[_54]=_56;
return _56;
};
dojo.hostenv.findModule=function(_57,_58){
var lmn=String(_57);
if(this.loaded_modules_[lmn]){
return this.loaded_modules_[lmn];
}
if(_58){
dojo.raise("no loaded module named '"+_57+"'");
}
return null;
};
dojo.kwCompoundRequire=function(_5a){
var _5b=_5a["common"]||[];
var _5c=_5a[dojo.hostenv.name_]?_5b.concat(_5a[dojo.hostenv.name_]||[]):_5b.concat(_5a["default"]||[]);
for(var x=0;x<_5c.length;x++){
var _5e=_5c[x];
if(_5e.constructor==Array){
dojo.hostenv.loadModule.apply(dojo.hostenv,_5e);
}else{
dojo.hostenv.loadModule(_5e);
}
}
};
dojo.require=function(){
dojo.hostenv.loadModule.apply(dojo.hostenv,arguments);
};
dojo.requireIf=function(){
var _5f=arguments[0];
if((_5f===true)||(_5f=="common")||(_5f&&dojo.render[_5f].capable)){
var _60=[];
for(var i=1;i<arguments.length;i++){
_60.push(arguments[i]);
}
dojo.require.apply(dojo,_60);
}
};
dojo.requireAfterIf=dojo.requireIf;
dojo.provide=function(){
return dojo.hostenv.startPackage.apply(dojo.hostenv,arguments);
};
dojo.registerModulePath=function(_62,_63){
return dojo.hostenv.setModulePrefix(_62,_63);
};
dojo.setModulePrefix=function(_64,_65){
dojo.deprecated("dojo.setModulePrefix(\""+_64+"\", \""+_65+"\")","replaced by dojo.registerModulePath","0.5");
return dojo.registerModulePath(_64,_65);
};
dojo.exists=function(obj,_67){
var p=_67.split(".");
for(var i=0;i<p.length;i++){
if(!obj[p[i]]){
return false;
}
obj=obj[p[i]];
}
return true;
};
dojo.hostenv.normalizeLocale=function(_6a){
return _6a?_6a.toLowerCase():dojo.locale;
};
dojo.hostenv.searchLocalePath=function(_6b,_6c,_6d){
_6b=dojo.hostenv.normalizeLocale(_6b);
var _6e=_6b.split("-");
var _6f=[];
for(var i=_6e.length;i>0;i--){
_6f.push(_6e.slice(0,i).join("-"));
}
_6f.push(false);
if(_6c){
_6f.reverse();
}
for(var j=_6f.length-1;j>=0;j--){
var loc=_6f[j]||"ROOT";
var _73=_6d(loc);
if(_73){
break;
}
}
};
dojo.hostenv.preloadLocalizations=function(){
var _74;
if(_74){
dojo.registerModulePath("nls","nls");
function preload(_75){
_75=dojo.hostenv.normalizeLocale(_75);
dojo.hostenv.searchLocalePath(_75,true,function(loc){
for(var i=0;i<_74.length;i++){
if(_74[i]==loc){
dojo["require"]("nls.dojo_"+loc);
return true;
}
}
return false;
});
}
preload();
var _78=djConfig.extraLocale||[];
for(var i=0;i<_78.length;i++){
preload(_78[i]);
}
}
dojo.hostenv.preloadLocalizations=function(){
};
};
dojo.requireLocalization=function(_7a,_7b,_7c){
dojo.hostenv.preloadLocalizations();
var _7d=[_7a,"nls",_7b].join(".");
var _7e=dojo.hostenv.findModule(_7d);
if(_7e){
if(djConfig.localizationComplete&&_7e._built){
return;
}
var _7f=dojo.hostenv.normalizeLocale(_7c).replace("-","_");
var _80=_7d+"."+_7f;
if(dojo.hostenv.findModule(_80)){
return;
}
}
_7e=dojo.hostenv.startPackage(_7d);
var _81=dojo.hostenv.getModuleSymbols(_7a);
var _82=_81.concat("nls").join("/");
var _83;
dojo.hostenv.searchLocalePath(_7c,false,function(loc){
var _85=loc.replace("-","_");
var _86=_7d+"."+_85;
var _87=false;
if(!dojo.hostenv.findModule(_86)){
dojo.hostenv.startPackage(_86);
var _88=[_82];
if(loc!="ROOT"){
_88.push(loc);
}
_88.push(_7b);
var _89=_88.join("/")+".js";
_87=dojo.hostenv.loadPath(_89,null,function(_8a){
var _8b=function(){
};
_8b.prototype=_83;
_7e[_85]=new _8b();
for(var j in _8a){
_7e[_85][j]=_8a[j];
}
});
}else{
_87=true;
}
if(_87&&_7e[_85]){
_83=_7e[_85];
}else{
_7e[_85]=_83;
}
});
};
(function(){
var _8d=djConfig.extraLocale;
if(_8d){
if(!_8d instanceof Array){
_8d=[_8d];
}
var req=dojo.requireLocalization;
dojo.requireLocalization=function(m,b,_91){
req(m,b,_91);
if(_91){
return;
}
for(var i=0;i<_8d.length;i++){
req(m,b,_8d[i]);
}
};
}
})();
}
if(typeof window!="undefined"){
(function(){
if(djConfig.allowQueryConfig){
var _93=document.location.toString();
var _94=_93.split("?",2);
if(_94.length>1){
var _95=_94[1];
var _96=_95.split("&");
for(var x in _96){
var sp=_96[x].split("=");
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
var _9a=document.getElementsByTagName("script");
var _9b=/(__package__|dojo|bootstrap1)\.js([\?\.]|$)/i;
for(var i=0;i<_9a.length;i++){
var src=_9a[i].getAttribute("src");
if(!src){
continue;
}
var m=src.match(_9b);
if(m){
var _9f=src.substring(0,m.index);
if(src.indexOf("bootstrap1")>-1){
_9f+="../";
}
if(!this["djConfig"]){
djConfig={};
}
if(djConfig["baseScriptUri"]==""){
djConfig["baseScriptUri"]=_9f;
}
if(djConfig["baseRelativePath"]==""){
djConfig["baseRelativePath"]=_9f;
}
break;
}
}
}
var dr=dojo.render;
var drh=dojo.render.html;
var drs=dojo.render.svg;
var dua=(drh.UA=navigator.userAgent);
var dav=(drh.AV=navigator.appVersion);
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
var _a7=dua.indexOf("Gecko");
drh.mozilla=drh.moz=(_a7>=0)&&(!drh.khtml);
if(drh.mozilla){
drh.geckoVersion=dua.substring(_a7+6,_a7+14);
}
drh.ie=(document.all)&&(!drh.opera);
drh.ie50=drh.ie&&dav.indexOf("MSIE 5.0")>=0;
drh.ie55=drh.ie&&dav.indexOf("MSIE 5.5")>=0;
drh.ie60=drh.ie&&dav.indexOf("MSIE 6.0")>=0;
drh.ie70=drh.ie&&dav.indexOf("MSIE 7.0")>=0;
var cm=document["compatMode"];
drh.quirks=(cm=="BackCompat")||(cm=="QuirksMode")||drh.ie55||drh.ie50;
dojo.locale=dojo.locale||(drh.ie?navigator.userLanguage:navigator.language).toLowerCase();
dr.vml.capable=drh.ie;
drs.capable=f;
drs.support.plugin=f;
drs.support.builtin=f;
var _a9=window["document"];
var tdi=_a9["implementation"];
if((tdi)&&(tdi["hasFeature"])&&(tdi.hasFeature("org.w3c.dom.svg","1.0"))){
drs.capable=t;
drs.support.builtin=t;
drs.support.plugin=f;
}
if(drh.safari){
var tmp=dua.split("AppleWebKit/")[1];
var ver=parseFloat(tmp.split(" ")[0]);
if(ver>=420){
drs.capable=t;
drs.support.builtin=t;
drs.support.plugin=f;
}
}
})();
dojo.hostenv.startPackage("dojo.hostenv");
dojo.render.name=dojo.hostenv.name_="browser";
dojo.hostenv.searchIds=[];
dojo.hostenv._XMLHTTP_PROGIDS=["Msxml2.XMLHTTP","Microsoft.XMLHTTP","Msxml2.XMLHTTP.4.0"];
dojo.hostenv.getXmlhttpObject=function(){
var _ad=null;
var _ae=null;
try{
_ad=new XMLHttpRequest();
}
catch(e){
}
if(!_ad){
for(var i=0;i<3;++i){
var _b0=dojo.hostenv._XMLHTTP_PROGIDS[i];
try{
_ad=new ActiveXObject(_b0);
}
catch(e){
_ae=e;
}
if(_ad){
dojo.hostenv._XMLHTTP_PROGIDS=[_b0];
break;
}
}
}
if(!_ad){
return dojo.raise("XMLHTTP not available",_ae);
}
return _ad;
};
dojo.hostenv._blockAsync=false;
dojo.hostenv.getText=function(uri,_b2,_b3){
if(!_b2){
this._blockAsync=true;
}
var _b4=this.getXmlhttpObject();
function isDocumentOk(_b5){
var _b6=_b5["status"];
return Boolean((!_b6)||((200<=_b6)&&(300>_b6))||(_b6==304));
}
if(_b2){
var _b7=this,_b8=null,gbl=dojo.global();
var xhr=dojo.evalObjPath("dojo.io.XMLHTTPTransport");
_b4.onreadystatechange=function(){
if(_b8){
gbl.clearTimeout(_b8);
_b8=null;
}
if(_b7._blockAsync||(xhr&&xhr._blockAsync)){
_b8=gbl.setTimeout(function(){
_b4.onreadystatechange.apply(this);
},10);
}else{
if(4==_b4.readyState){
if(isDocumentOk(_b4)){
_b2(_b4.responseText);
}
}
}
};
}
_b4.open("GET",uri,_b2?true:false);
try{
_b4.send(null);
if(_b2){
return null;
}
if(!isDocumentOk(_b4)){
var err=Error("Unable to load "+uri+" status:"+_b4.status);
err.status=_b4.status;
err.responseText=_b4.responseText;
throw err;
}
}
catch(e){
this._blockAsync=false;
if((_b3)&&(!_b2)){
return null;
}else{
throw e;
}
}
this._blockAsync=false;
return _b4.responseText;
};
dojo.hostenv.defaultDebugContainerId="dojoDebug";
dojo.hostenv._println_buffer=[];
dojo.hostenv._println_safe=false;
dojo.hostenv.println=function(_bc){
if(!dojo.hostenv._println_safe){
dojo.hostenv._println_buffer.push(_bc);
}else{
try{
var _bd=document.getElementById(djConfig.debugContainerId?djConfig.debugContainerId:dojo.hostenv.defaultDebugContainerId);
if(!_bd){
_bd=dojo.body();
}
var div=document.createElement("div");
div.appendChild(document.createTextNode(_bc));
_bd.appendChild(div);
}
catch(e){
try{
document.write("<div>"+_bc+"</div>");
}
catch(e2){
window.status=_bc;
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
function dj_addNodeEvtHdlr(_bf,_c0,fp,_c2){
var _c3=_bf["on"+_c0]||function(){
};
_bf["on"+_c0]=function(){
fp.apply(_bf,arguments);
_c3.apply(_bf,arguments);
};
return true;
}
function dj_load_init(e){
var _c5=(e&&e.type)?e.type.toLowerCase():"load";
if(arguments.callee.initialized||(_c5!="domcontentloaded"&&_c5!="load")){
return;
}
arguments.callee.initialized=true;
if(typeof (_timer)!="undefined"){
clearInterval(_timer);
delete _timer;
}
var _c6=function(){
if(dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
};
if(dojo.hostenv.inFlightCount==0){
_c6();
dojo.hostenv.modulesLoaded();
}else{
dojo.addOnLoad(_c6);
}
}
if(document.addEventListener){
document.addEventListener("DOMContentLoaded",dj_load_init,null);
document.addEventListener("load",dj_load_init,null);
}
if(dojo.render.html.ie&&dojo.render.os.win){
document.attachEvent("onreadystatechange",function(e){
if(document.readyState=="complete"){
dj_load_init();
}
});
}
if(/(WebKit|khtml)/i.test(navigator.userAgent)){
var _timer=setInterval(function(){
if(/loaded|complete/.test(document.readyState)){
dj_load_init();
}
},10);
}
if(dojo.render.html.ie){
dj_addNodeEvtHdlr(window,"beforeunload",function(){
dojo.hostenv._unloading=true;
window.setTimeout(function(){
dojo.hostenv._unloading=false;
},0);
});
}
dj_addNodeEvtHdlr(window,"unload",function(){
dojo.hostenv.unloaded();
if((!dojo.render.html.ie)||(dojo.render.html.ie&&dojo.hostenv._unloading)){
dojo.hostenv.unloaded();
}
});
dojo.hostenv.makeWidgets=function(){
var _c8=[];
if(djConfig.searchIds&&djConfig.searchIds.length>0){
_c8=_c8.concat(djConfig.searchIds);
}
if(dojo.hostenv.searchIds&&dojo.hostenv.searchIds.length>0){
_c8=_c8.concat(dojo.hostenv.searchIds);
}
if((djConfig.parseWidgets)||(_c8.length>0)){
if(dojo.evalObjPath("dojo.widget.Parse")){
var _c9=new dojo.xml.Parse();
if(_c8.length>0){
for(var x=0;x<_c8.length;x++){
var _cb=document.getElementById(_c8[x]);
if(!_cb){
continue;
}
var _cc=_c9.parseElement(_cb,null,true);
dojo.widget.getParser().createComponents(_cc);
}
}else{
if(djConfig.parseWidgets){
var _cc=_c9.parseElement(dojo.body(),null,true);
dojo.widget.getParser().createComponents(_cc);
}
}
}
}
};
dojo.addOnLoad(function(){
if(!dojo.render.html.ie){
dojo.hostenv.makeWidgets();
}
});
try{
if(dojo.render.html.ie){
document.namespaces.add("v","urn:schemas-microsoft-com:vml");
document.createStyleSheet().addRule("v\\:*","behavior:url(#default#VML)");
}
}
catch(e){
}
dojo.hostenv.writeIncludes=function(){
};
if(!dj_undef("document",this)){
dj_currentDocument=this.document;
}
dojo.doc=function(){
return dj_currentDocument;
};
dojo.body=function(){
return dojo.doc().body||dojo.doc().getElementsByTagName("body")[0];
};
dojo.byId=function(id,doc){
if((id)&&((typeof id=="string")||(id instanceof String))){
if(!doc){
doc=dj_currentDocument;
}
var ele=doc.getElementById(id);
if(ele&&(ele.id!=id)&&doc.all){
ele=null;
eles=doc.all[id];
if(eles){
if(eles.length){
for(var i=0;i<eles.length;i++){
if(eles[i].id==id){
ele=eles[i];
break;
}
}
}else{
ele=eles;
}
}
}
return ele;
}
return id;
};
dojo.setContext=function(_d1,_d2){
dj_currentContext=_d1;
dj_currentDocument=_d2;
};
dojo._fireCallback=function(_d3,_d4,_d5){
if((_d4)&&((typeof _d3=="string")||(_d3 instanceof String))){
_d3=_d4[_d3];
}
return (_d4?_d3.apply(_d4,_d5||[]):_d3());
};
dojo.withGlobal=function(_d6,_d7,_d8,_d9){
var _da;
var _db=dj_currentContext;
var _dc=dj_currentDocument;
try{
dojo.setContext(_d6,_d6.document);
_da=dojo._fireCallback(_d7,_d8,_d9);
}
finally{
dojo.setContext(_db,_dc);
}
return _da;
};
dojo.withDoc=function(_dd,_de,_df,_e0){
var _e1;
var _e2=dj_currentDocument;
try{
dj_currentDocument=_dd;
_e1=dojo._fireCallback(_de,_df,_e0);
}
finally{
dj_currentDocument=_e2;
}
return _e1;
};
}
(function(){
if(typeof dj_usingBootstrap!="undefined"){
return;
}
var _e3=false;
var _e4=false;
var _e5=false;
if((typeof this["load"]=="function")&&((typeof this["Packages"]=="function")||(typeof this["Packages"]=="object"))){
_e3=true;
}else{
if(typeof this["load"]=="function"){
_e4=true;
}else{
if(window.widget){
_e5=true;
}
}
}
var _e6=[];
if((this["djConfig"])&&((djConfig["isDebug"])||(djConfig["debugAtAllCosts"]))){
_e6.push("debug.js");
}
if((this["djConfig"])&&(djConfig["debugAtAllCosts"])&&(!_e3)&&(!_e5)){
_e6.push("browser_debug.js");
}
var _e7=djConfig["baseScriptUri"];
if((this["djConfig"])&&(djConfig["baseLoaderUri"])){
_e7=djConfig["baseLoaderUri"];
}
for(var x=0;x<_e6.length;x++){
var _e9=_e7+"src/"+_e6[x];
if(_e3||_e4){
load(_e9);
}else{
try{
document.write("<scr"+"ipt type='text/javascript' src='"+_e9+"'></scr"+"ipt>");
}
catch(e){
var _ea=document.createElement("script");
_ea.src=_e9;
document.getElementsByTagName("head")[0].appendChild(_ea);
}
}
}
})();
dojo.provide("dojo.string.common");
dojo.string.trim=function(str,wh){
if(!str.replace){
return str;
}
if(!str.length){
return str;
}
var re=(wh>0)?(/^\s+/):(wh<0)?(/\s+$/):(/^\s+|\s+$/g);
return str.replace(re,"");
};
dojo.string.trimStart=function(str){
return dojo.string.trim(str,1);
};
dojo.string.trimEnd=function(str){
return dojo.string.trim(str,-1);
};
dojo.string.repeat=function(str,_f1,_f2){
var out="";
for(var i=0;i<_f1;i++){
out+=str;
if(_f2&&i<_f1-1){
out+=_f2;
}
}
return out;
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
dojo.provide("dojo.string");
dojo.provide("dojo.lang.common");
dojo.lang.inherits=function(_100,_101){
if(typeof _101!="function"){
dojo.raise("dojo.inherits: superclass argument ["+_101+"] must be a function (subclass: ["+_100+"']");
}
_100.prototype=new _101();
_100.prototype.constructor=_100;
_100.superclass=_101.prototype;
_100["super"]=_101.prototype;
};
dojo.lang._mixin=function(obj,_103){
var tobj={};
for(var x in _103){
if((typeof tobj[x]=="undefined")||(tobj[x]!=_103[x])){
obj[x]=_103[x];
}
}
if(dojo.render.html.ie&&(typeof (_103["toString"])=="function")&&(_103["toString"]!=obj["toString"])&&(_103["toString"]!=tobj["toString"])){
obj.toString=_103.toString;
}
return obj;
};
dojo.lang.mixin=function(obj,_107){
for(var i=1,l=arguments.length;i<l;i++){
dojo.lang._mixin(obj,arguments[i]);
}
return obj;
};
dojo.lang.extend=function(_10a,_10b){
for(var i=1,l=arguments.length;i<l;i++){
dojo.lang._mixin(_10a.prototype,arguments[i]);
}
return _10a;
};
dojo.inherits=dojo.lang.inherits;
dojo.mixin=dojo.lang.mixin;
dojo.extend=dojo.lang.extend;
dojo.lang.find=function(_10e,_10f,_110,_111){
if(!dojo.lang.isArrayLike(_10e)&&dojo.lang.isArrayLike(_10f)){
dojo.deprecated("dojo.lang.find(value, array)","use dojo.lang.find(array, value) instead","0.5");
var temp=_10e;
_10e=_10f;
_10f=temp;
}
var _113=dojo.lang.isString(_10e);
if(_113){
_10e=_10e.split("");
}
if(_111){
var step=-1;
var i=_10e.length-1;
var end=-1;
}else{
var step=1;
var i=0;
var end=_10e.length;
}
if(_110){
while(i!=end){
if(_10e[i]===_10f){
return i;
}
i+=step;
}
}else{
while(i!=end){
if(_10e[i]==_10f){
return i;
}
i+=step;
}
}
return -1;
};
dojo.lang.indexOf=dojo.lang.find;
dojo.lang.findLast=function(_117,_118,_119){
return dojo.lang.find(_117,_118,_119,true);
};
dojo.lang.lastIndexOf=dojo.lang.findLast;
dojo.lang.inArray=function(_11a,_11b){
return dojo.lang.find(_11a,_11b)>-1;
};
dojo.lang.isObject=function(it){
if(typeof it=="undefined"){
return false;
}
return (typeof it=="object"||it===null||dojo.lang.isArray(it)||dojo.lang.isFunction(it));
};
dojo.lang.isArray=function(it){
return (it&&it instanceof Array||typeof it=="array");
};
dojo.lang.isArrayLike=function(it){
if((!it)||(dojo.lang.isUndefined(it))){
return false;
}
if(dojo.lang.isString(it)){
return false;
}
if(dojo.lang.isFunction(it)){
return false;
}
if(dojo.lang.isArray(it)){
return true;
}
if((it.tagName)&&(it.tagName.toLowerCase()=="form")){
return false;
}
if(dojo.lang.isNumber(it.length)&&isFinite(it.length)){
return true;
}
return false;
};
dojo.lang.isFunction=function(it){
if(!it){
return false;
}
if((typeof (it)=="function")&&(it=="[object NodeList]")){
return false;
}
return (it instanceof Function||typeof it=="function");
};
dojo.lang.isString=function(it){
return (typeof it=="string"||it instanceof String);
};
dojo.lang.isAlien=function(it){
if(!it){
return false;
}
return !dojo.lang.isFunction()&&/\{\s*\[native code\]\s*\}/.test(String(it));
};
dojo.lang.isBoolean=function(it){
return (it instanceof Boolean||typeof it=="boolean");
};
dojo.lang.isNumber=function(it){
return (it instanceof Number||typeof it=="number");
};
dojo.lang.isUndefined=function(it){
return ((typeof (it)=="undefined")&&(it==undefined));
};
dojo.provide("dojo.lang.extras");
dojo.lang.setTimeout=function(func,_126){
var _127=window,_128=2;
if(!dojo.lang.isFunction(func)){
_127=func;
func=_126;
_126=arguments[2];
_128++;
}
if(dojo.lang.isString(func)){
func=_127[func];
}
var args=[];
for(var i=_128;i<arguments.length;i++){
args.push(arguments[i]);
}
return dojo.global().setTimeout(function(){
func.apply(_127,args);
},_126);
};
dojo.lang.clearTimeout=function(_12b){
dojo.global().clearTimeout(_12b);
};
dojo.lang.getNameInObj=function(ns,item){
if(!ns){
ns=dj_global;
}
for(var x in ns){
if(ns[x]===item){
return new String(x);
}
}
return null;
};
dojo.lang.shallowCopy=function(obj,deep){
var i,ret;
if(obj===null){
return null;
}
if(dojo.lang.isObject(obj)){
ret=new obj.constructor();
for(i in obj){
if(dojo.lang.isUndefined(ret[i])){
ret[i]=deep?dojo.lang.shallowCopy(obj[i],deep):obj[i];
}
}
}else{
if(dojo.lang.isArray(obj)){
ret=[];
for(i=0;i<obj.length;i++){
ret[i]=deep?dojo.lang.shallowCopy(obj[i],deep):obj[i];
}
}else{
ret=obj;
}
}
return ret;
};
dojo.lang.firstValued=function(){
for(var i=0;i<arguments.length;i++){
if(typeof arguments[i]!="undefined"){
return arguments[i];
}
}
return undefined;
};
dojo.lang.getObjPathValue=function(_134,_135,_136){
with(dojo.parseObjPath(_134,_135,_136)){
return dojo.evalProp(prop,obj,_136);
}
};
dojo.lang.setObjPathValue=function(_137,_138,_139,_13a){
if(arguments.length<4){
_13a=true;
}
with(dojo.parseObjPath(_137,_139,_13a)){
if(obj&&(_13a||(prop in obj))){
obj[prop]=_138;
}
}
};
dojo.provide("dojo.io.common");
dojo.io.transports=[];
dojo.io.hdlrFuncNames=["load","error","timeout"];
dojo.io.Request=function(url,_13c,_13d,_13e){
if((arguments.length==1)&&(arguments[0].constructor==Object)){
this.fromKwArgs(arguments[0]);
}else{
this.url=url;
if(_13c){
this.mimetype=_13c;
}
if(_13d){
this.transport=_13d;
}
if(arguments.length>=4){
this.changeUrl=_13e;
}
}
};
dojo.lang.extend(dojo.io.Request,{url:"",mimetype:"text/plain",method:"GET",content:undefined,transport:undefined,changeUrl:undefined,formNode:undefined,sync:false,bindSuccess:false,useCache:false,preventCache:false,load:function(type,data,evt){
},error:function(type,_143){
},timeout:function(type){
},handle:function(){
},timeoutSeconds:0,abort:function(){
},fromKwArgs:function(_145){
if(_145["url"]){
_145.url=_145.url.toString();
}
if(_145["formNode"]){
_145.formNode=dojo.byId(_145.formNode);
}
if(!_145["method"]&&_145["formNode"]&&_145["formNode"].method){
_145.method=_145["formNode"].method;
}
if(!_145["handle"]&&_145["handler"]){
_145.handle=_145.handler;
}
if(!_145["load"]&&_145["loaded"]){
_145.load=_145.loaded;
}
if(!_145["changeUrl"]&&_145["changeURL"]){
_145.changeUrl=_145.changeURL;
}
_145.encoding=dojo.lang.firstValued(_145["encoding"],djConfig["bindEncoding"],"");
_145.sendTransport=dojo.lang.firstValued(_145["sendTransport"],djConfig["ioSendTransport"],false);
var _146=dojo.lang.isFunction;
for(var x=0;x<dojo.io.hdlrFuncNames.length;x++){
var fn=dojo.io.hdlrFuncNames[x];
if(_145[fn]&&_146(_145[fn])){
continue;
}
if(_145["handle"]&&_146(_145["handle"])){
_145[fn]=_145.handle;
}
}
dojo.lang.mixin(this,_145);
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
dojo.io.bind=function(_14d){
if(!(_14d instanceof dojo.io.Request)){
try{
_14d=new dojo.io.Request(_14d);
}
catch(e){
dojo.debug(e);
}
}
var _14e="";
if(_14d["transport"]){
_14e=_14d["transport"];
if(!this[_14e]){
dojo.io.sendBindError(_14d,"No dojo.io.bind() transport with name '"+_14d["transport"]+"'.");
return _14d;
}
if(!this[_14e].canHandle(_14d)){
dojo.io.sendBindError(_14d,"dojo.io.bind() transport with name '"+_14d["transport"]+"' cannot handle this type of request.");
return _14d;
}
}else{
for(var x=0;x<dojo.io.transports.length;x++){
var tmp=dojo.io.transports[x];
if((this[tmp])&&(this[tmp].canHandle(_14d))){
_14e=tmp;
}
}
if(_14e==""){
dojo.io.sendBindError(_14d,"None of the loaded transports for dojo.io.bind()"+" can handle the request.");
return _14d;
}
}
this[_14e].bind(_14d);
_14d.bindSuccess=true;
return _14d;
};
dojo.io.sendBindError=function(_151,_152){
if((typeof _151.error=="function"||typeof _151.handle=="function")&&(typeof setTimeout=="function"||typeof setTimeout=="object")){
var _153=new dojo.io.Error(_152);
setTimeout(function(){
_151[(typeof _151.error=="function")?"error":"handle"]("error",_153,null,_151);
},50);
}else{
dojo.raise(_152);
}
};
dojo.io.queueBind=function(_154){
if(!(_154 instanceof dojo.io.Request)){
try{
_154=new dojo.io.Request(_154);
}
catch(e){
dojo.debug(e);
}
}
var _155=_154.load;
_154.load=function(){
dojo.io._queueBindInFlight=false;
var ret=_155.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
var _157=_154.error;
_154.error=function(){
dojo.io._queueBindInFlight=false;
var ret=_157.apply(this,arguments);
dojo.io._dispatchNextQueueBind();
return ret;
};
dojo.io._bindQueue.push(_154);
dojo.io._dispatchNextQueueBind();
return _154;
};
dojo.io._dispatchNextQueueBind=function(){
if(!dojo.io._queueBindInFlight){
dojo.io._queueBindInFlight=true;
if(dojo.io._bindQueue.length>0){
dojo.io.bind(dojo.io._bindQueue.shift());
}else{
dojo.io._queueBindInFlight=false;
}
}
};
dojo.io._bindQueue=[];
dojo.io._queueBindInFlight=false;
dojo.io.argsFromMap=function(map,_15a,last){
var enc=/utf/i.test(_15a||"")?encodeURIComponent:dojo.string.encodeAscii;
var _15d=[];
var _15e=new Object();
for(var name in map){
var _160=function(elt){
var val=enc(name)+"="+enc(elt);
_15d[(last==name)?"push":"unshift"](val);
};
if(!_15e[name]){
var _163=map[name];
if(dojo.lang.isArray(_163)){
dojo.lang.forEach(_163,_160);
}else{
_160(_163);
}
}
}
return _15d.join("&");
};
dojo.io.setIFrameSrc=function(_164,src,_166){
try{
var r=dojo.render.html;
if(!_166){
if(r.safari){
_164.location=src;
}else{
frames[_164.name].location=src;
}
}else{
var idoc;
if(r.ie){
idoc=_164.contentWindow.document;
}else{
if(r.safari){
idoc=_164.document;
}else{
idoc=_164.contentWindow;
}
}
if(!idoc){
_164.location=src;
return;
}else{
idoc.location.replace(src);
}
}
}
catch(e){
dojo.debug(e);
dojo.debug("setIFrameSrc: "+e);
}
};
dojo.provide("dojo.lang.array");
dojo.lang.has=function(obj,name){
try{
return (typeof obj[name]!="undefined");
}
catch(e){
return false;
}
};
dojo.lang.isEmpty=function(obj){
if(dojo.lang.isObject(obj)){
var tmp={};
var _16d=0;
for(var x in obj){
if(obj[x]&&(!tmp[x])){
_16d++;
break;
}
}
return (_16d==0);
}else{
if(dojo.lang.isArrayLike(obj)||dojo.lang.isString(obj)){
return obj.length==0;
}
}
};
dojo.lang.map=function(arr,obj,_171){
var _172=dojo.lang.isString(arr);
if(_172){
arr=arr.split("");
}
if(dojo.lang.isFunction(obj)&&(!_171)){
_171=obj;
obj=dj_global;
}else{
if(dojo.lang.isFunction(obj)&&_171){
var _173=obj;
obj=_171;
_171=_173;
}
}
if(Array.map){
var _174=Array.map(arr,_171,obj);
}else{
var _174=[];
for(var i=0;i<arr.length;++i){
_174.push(_171.call(obj,arr[i]));
}
}
if(_172){
return _174.join("");
}else{
return _174;
}
};
dojo.lang.reduce=function(arr,_177,obj,_179){
var _17a=_177;
var ob=obj?obj:dj_global;
dojo.lang.map(arr,function(val){
_17a=_179.call(ob,_17a,val);
});
return _17a;
};
dojo.lang.forEach=function(_17d,_17e,_17f){
if(dojo.lang.isString(_17d)){
_17d=_17d.split("");
}
if(Array.forEach){
Array.forEach(_17d,_17e,_17f);
}else{
if(!_17f){
_17f=dj_global;
}
for(var i=0,l=_17d.length;i<l;i++){
_17e.call(_17f,_17d[i],i,_17d);
}
}
};
dojo.lang._everyOrSome=function(_182,arr,_184,_185){
if(dojo.lang.isString(arr)){
arr=arr.split("");
}
if(Array.every){
return Array[(_182)?"every":"some"](arr,_184,_185);
}else{
if(!_185){
_185=dj_global;
}
for(var i=0,l=arr.length;i<l;i++){
var _188=_184.call(_185,arr[i],i,arr);
if((_182)&&(!_188)){
return false;
}else{
if((!_182)&&(_188)){
return true;
}
}
}
return (_182)?true:false;
}
};
dojo.lang.every=function(arr,_18a,_18b){
return this._everyOrSome(true,arr,_18a,_18b);
};
dojo.lang.some=function(arr,_18d,_18e){
return this._everyOrSome(false,arr,_18d,_18e);
};
dojo.lang.filter=function(arr,_190,_191){
var _192=dojo.lang.isString(arr);
if(_192){
arr=arr.split("");
}
if(Array.filter){
var _193=Array.filter(arr,_190,_191);
}else{
if(!_191){
if(arguments.length>=3){
dojo.raise("thisObject doesn't exist!");
}
_191=dj_global;
}
var _193=[];
for(var i=0;i<arr.length;i++){
if(_190.call(_191,arr[i],i,arr)){
_193.push(arr[i]);
}
}
}
if(_192){
return _193.join("");
}else{
return _193;
}
};
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
dojo.lang.toArray=function(_198,_199){
var _19a=[];
for(var i=_199||0;i<_198.length;i++){
_19a.push(_198[i]);
}
return _19a;
};
dojo.provide("dojo.lang.func");
dojo.lang.hitch=function(_19c,_19d){
var fcn=(dojo.lang.isString(_19d)?_19c[_19d]:_19d)||function(){
};
return function(){
return fcn.apply(_19c,arguments);
};
};
dojo.lang.anonCtr=0;
dojo.lang.anon={};
dojo.lang.nameAnonFunc=function(_19f,_1a0,_1a1){
var nso=(_1a0||dojo.lang.anon);
if((_1a1)||((dj_global["djConfig"])&&(djConfig["slowAnonFuncLookups"]==true))){
for(var x in nso){
try{
if(nso[x]===_19f){
return x;
}
}
catch(e){
}
}
}
var ret="__"+dojo.lang.anonCtr++;
while(typeof nso[ret]!="undefined"){
ret="__"+dojo.lang.anonCtr++;
}
nso[ret]=_19f;
return ret;
};
dojo.lang.forward=function(_1a5){
return function(){
return this[_1a5].apply(this,arguments);
};
};
dojo.lang.curry=function(ns,func){
var _1a8=[];
ns=ns||dj_global;
if(dojo.lang.isString(func)){
func=ns[func];
}
for(var x=2;x<arguments.length;x++){
_1a8.push(arguments[x]);
}
var _1aa=(func["__preJoinArity"]||func.length)-_1a8.length;
function gather(_1ab,_1ac,_1ad){
var _1ae=_1ad;
var _1af=_1ac.slice(0);
for(var x=0;x<_1ab.length;x++){
_1af.push(_1ab[x]);
}
_1ad=_1ad-_1ab.length;
if(_1ad<=0){
var res=func.apply(ns,_1af);
_1ad=_1ae;
return res;
}else{
return function(){
return gather(arguments,_1af,_1ad);
};
}
}
return gather([],_1a8,_1aa);
};
dojo.lang.curryArguments=function(ns,func,args,_1b5){
var _1b6=[];
var x=_1b5||0;
for(x=_1b5;x<args.length;x++){
_1b6.push(args[x]);
}
return dojo.lang.curry.apply(dojo.lang,[ns,func].concat(_1b6));
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
dojo.lang.delayThese=function(farr,cb,_1bc,_1bd){
if(!farr.length){
if(typeof _1bd=="function"){
_1bd();
}
return;
}
if((typeof _1bc=="undefined")&&(typeof cb=="number")){
_1bc=cb;
cb=function(){
};
}else{
if(!cb){
cb=function(){
};
if(!_1bc){
_1bc=0;
}
}
}
setTimeout(function(){
(farr.shift())();
cb();
dojo.lang.delayThese(farr,cb,_1bc,_1bd);
},_1bc);
};
dojo.provide("dojo.string.extras");
dojo.string.substituteParams=function(_1be,hash){
var map=(typeof hash=="object")?hash:dojo.lang.toArray(arguments,1);
return _1be.replace(/\%\{(\w+)\}/g,function(_1c1,key){
if(typeof (map[key])!="undefined"&&map[key]!=null){
return map[key];
}
dojo.raise("Substitution not found: "+key);
});
};
dojo.string.capitalize=function(str){
if(!dojo.lang.isString(str)){
return "";
}
if(arguments.length==0){
str=this;
}
var _1c4=str.split(" ");
for(var i=0;i<_1c4.length;i++){
_1c4[i]=_1c4[i].charAt(0).toUpperCase()+_1c4[i].substring(1);
}
return _1c4.join(" ");
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
var _1c9=escape(str);
var _1ca,re=/%u([0-9A-F]{4})/i;
while((_1ca=_1c9.match(re))){
var num=Number("0x"+_1ca[1]);
var _1cd=escape("&#"+num+";");
ret+=_1c9.substring(0,_1ca.index)+_1cd;
_1c9=_1c9.substring(_1ca.index+_1ca[0].length);
}
ret+=_1c9.replace(/\+/g,"%2B");
return ret;
};
dojo.string.escape=function(type,str){
var args=dojo.lang.toArray(arguments,1);
switch(type.toLowerCase()){
case "xml":
case "html":
case "xhtml":
return dojo.string.escapeXml.apply(this,args);
case "sql":
return dojo.string.escapeSql.apply(this,args);
case "regexp":
case "regex":
return dojo.string.escapeRegExp.apply(this,args);
case "javascript":
case "jscript":
case "js":
return dojo.string.escapeJavaScript.apply(this,args);
case "ascii":
return dojo.string.encodeAscii.apply(this,args);
default:
return str;
}
};
dojo.string.escapeXml=function(str,_1d2){
str=str.replace(/&/gm,"&amp;").replace(/</gm,"&lt;").replace(/>/gm,"&gt;").replace(/"/gm,"&quot;");
if(!_1d2){
str=str.replace(/'/gm,"&#39;");
}
return str;
};
dojo.string.escapeSql=function(str){
return str.replace(/'/gm,"''");
};
dojo.string.escapeRegExp=function(str){
return str.replace(/\\/gm,"\\\\").replace(/([\f\b\n\t\r[\^$|?*+(){}])/gm,"\\$1");
};
dojo.string.escapeJavaScript=function(str){
return str.replace(/(["'\f\b\n\t\r])/gm,"\\$1");
};
dojo.string.escapeString=function(str){
return ("\""+str.replace(/(["\\])/g,"\\$1")+"\"").replace(/[\f]/g,"\\f").replace(/[\b]/g,"\\b").replace(/[\n]/g,"\\n").replace(/[\t]/g,"\\t").replace(/[\r]/g,"\\r");
};
dojo.string.summary=function(str,len){
if(!len||str.length<=len){
return str;
}
return str.substring(0,len).replace(/\.+$/,"")+"...";
};
dojo.string.endsWith=function(str,end,_1db){
if(_1db){
str=str.toLowerCase();
end=end.toLowerCase();
}
if((str.length-end.length)<0){
return false;
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
dojo.string.startsWith=function(str,_1df,_1e0){
if(_1e0){
str=str.toLowerCase();
_1df=_1df.toLowerCase();
}
return str.indexOf(_1df)==0;
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
if(str.indexOf(arguments[i])>-1){
return true;
}
}
return false;
};
dojo.string.normalizeNewlines=function(text,_1e6){
if(_1e6=="\n"){
text=text.replace(/\r\n/g,"\n");
text=text.replace(/\r/g,"\n");
}else{
if(_1e6=="\r"){
text=text.replace(/\r\n/g,"\r");
text=text.replace(/\n/g,"\r");
}else{
text=text.replace(/([^\r])\n/g,"$1\r\n").replace(/\r([^\n])/g,"\r\n$1");
}
}
return text;
};
dojo.string.splitEscaped=function(str,_1e8){
var _1e9=[];
for(var i=0,_1eb=0;i<str.length;i++){
if(str.charAt(i)=="\\"){
i++;
continue;
}
if(str.charAt(i)==_1e8){
_1e9.push(str.substring(_1eb,i));
_1eb=i+1;
}
}
_1e9.push(str.substr(_1eb));
return _1e9;
};
dojo.provide("dojo.dom");
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
dojo.dom.isNode=function(wh){
if(typeof Element=="function"){
try{
return wh instanceof Element;
}
catch(E){
}
}else{
return wh&&!isNaN(wh.nodeType);
}
};
dojo.dom.getUniqueId=function(){
var _1ed=dojo.doc();
do{
var id="dj_unique_"+(++arguments.callee._idIncrement);
}while(_1ed.getElementById(id));
return id;
};
dojo.dom.getUniqueId._idIncrement=0;
dojo.dom.firstElement=dojo.dom.getFirstChildElement=function(_1ef,_1f0){
var node=_1ef.firstChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.nextSibling;
}
if(_1f0&&node&&node.tagName&&node.tagName.toLowerCase()!=_1f0.toLowerCase()){
node=dojo.dom.nextElement(node,_1f0);
}
return node;
};
dojo.dom.lastElement=dojo.dom.getLastChildElement=function(_1f2,_1f3){
var node=_1f2.lastChild;
while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE){
node=node.previousSibling;
}
if(_1f3&&node&&node.tagName&&node.tagName.toLowerCase()!=_1f3.toLowerCase()){
node=dojo.dom.prevElement(node,_1f3);
}
return node;
};
dojo.dom.nextElement=dojo.dom.getNextSiblingElement=function(node,_1f6){
if(!node){
return null;
}
do{
node=node.nextSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_1f6&&_1f6.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.nextElement(node,_1f6);
}
return node;
};
dojo.dom.prevElement=dojo.dom.getPreviousSiblingElement=function(node,_1f8){
if(!node){
return null;
}
if(_1f8){
_1f8=_1f8.toLowerCase();
}
do{
node=node.previousSibling;
}while(node&&node.nodeType!=dojo.dom.ELEMENT_NODE);
if(node&&_1f8&&_1f8.toLowerCase()!=node.tagName.toLowerCase()){
return dojo.dom.prevElement(node,_1f8);
}
return node;
};
dojo.dom.moveChildren=function(_1f9,_1fa,trim){
var _1fc=0;
if(trim){
while(_1f9.hasChildNodes()&&_1f9.firstChild.nodeType==dojo.dom.TEXT_NODE){
_1f9.removeChild(_1f9.firstChild);
}
while(_1f9.hasChildNodes()&&_1f9.lastChild.nodeType==dojo.dom.TEXT_NODE){
_1f9.removeChild(_1f9.lastChild);
}
}
while(_1f9.hasChildNodes()){
_1fa.appendChild(_1f9.firstChild);
_1fc++;
}
return _1fc;
};
dojo.dom.copyChildren=function(_1fd,_1fe,trim){
var _200=_1fd.cloneNode(true);
return this.moveChildren(_200,_1fe,trim);
};
dojo.dom.removeChildren=function(node){
var _202=node.childNodes.length;
while(node.hasChildNodes()){
node.removeChild(node.firstChild);
}
return _202;
};
dojo.dom.replaceChildren=function(node,_204){
dojo.dom.removeChildren(node);
node.appendChild(_204);
};
dojo.dom.removeNode=function(node){
if(node&&node.parentNode){
return node.parentNode.removeChild(node);
}
};
dojo.dom.getAncestors=function(node,_207,_208){
var _209=[];
var _20a=(_207&&(_207 instanceof Function||typeof _207=="function"));
while(node){
if(!_20a||_207(node)){
_209.push(node);
}
if(_208&&_209.length>0){
return _209[0];
}
node=node.parentNode;
}
if(_208){
return null;
}
return _209;
};
dojo.dom.getAncestorsByTag=function(node,tag,_20d){
tag=tag.toLowerCase();
return dojo.dom.getAncestors(node,function(el){
return ((el.tagName)&&(el.tagName.toLowerCase()==tag));
},_20d);
};
dojo.dom.getFirstAncestorByTag=function(node,tag){
return dojo.dom.getAncestorsByTag(node,tag,true);
};
dojo.dom.isDescendantOf=function(node,_212,_213){
if(_213&&node){
node=node.parentNode;
}
while(node){
if(node==_212){
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
if(node.xml){
return node.xml;
}else{
if(typeof XMLSerializer!="undefined"){
return (new XMLSerializer()).serializeToString(node);
}
}
}
};
dojo.dom.createDocument=function(){
var doc=null;
var _216=dojo.doc();
if(!dj_undef("ActiveXObject")){
var _217=["MSXML2","Microsoft","MSXML","MSXML3"];
for(var i=0;i<_217.length;i++){
try{
doc=new ActiveXObject(_217[i]+".XMLDOM");
}
catch(e){
}
if(doc){
break;
}
}
}else{
if((_216.implementation)&&(_216.implementation.createDocument)){
doc=_216.implementation.createDocument("","",null);
}
}
return doc;
};
dojo.dom.createDocumentFromText=function(str,_21a){
if(!_21a){
_21a="text/xml";
}
if(!dj_undef("DOMParser")){
var _21b=new DOMParser();
return _21b.parseFromString(str,_21a);
}else{
if(!dj_undef("ActiveXObject")){
var _21c=dojo.dom.createDocument();
if(_21c){
_21c.async=false;
_21c.loadXML(str);
return _21c;
}else{
dojo.debug("toXml didn't work?");
}
}else{
var _21d=dojo.doc();
if(_21d.createElement){
var tmp=_21d.createElement("xml");
tmp.innerHTML=str;
if(_21d.implementation&&_21d.implementation.createDocument){
var _21f=_21d.implementation.createDocument("foo","",null);
for(var i=0;i<tmp.childNodes.length;i++){
_21f.importNode(tmp.childNodes.item(i),true);
}
return _21f;
}
return ((tmp.document)&&(tmp.document.firstChild?tmp.document.firstChild:tmp));
}
}
}
return null;
};
dojo.dom.prependChild=function(node,_222){
if(_222.firstChild){
_222.insertBefore(node,_222.firstChild);
}else{
_222.appendChild(node);
}
return true;
};
dojo.dom.insertBefore=function(node,ref,_225){
if(_225!=true&&(node===ref||node.nextSibling===ref)){
return false;
}
var _226=ref.parentNode;
_226.insertBefore(node,ref);
return true;
};
dojo.dom.insertAfter=function(node,ref,_229){
var pn=ref.parentNode;
if(ref==pn.lastChild){
if((_229!=true)&&(node===ref)){
return false;
}
pn.appendChild(node);
}else{
return this.insertBefore(node,ref.nextSibling,_229);
}
return true;
};
dojo.dom.insertAtPosition=function(node,ref,_22d){
if((!node)||(!ref)||(!_22d)){
return false;
}
switch(_22d.toLowerCase()){
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
dojo.dom.insertAtIndex=function(node,_22f,_230){
var _231=_22f.childNodes;
if(!_231.length){
_22f.appendChild(node);
return true;
}
var _232=null;
for(var i=0;i<_231.length;i++){
var _234=_231.item(i)["getAttribute"]?parseInt(_231.item(i).getAttribute("dojoinsertionindex")):-1;
if(_234<_230){
_232=_231.item(i);
}
}
if(_232){
return dojo.dom.insertAfter(node,_232);
}else{
return dojo.dom.insertBefore(node,_231.item(0));
}
};
dojo.dom.textContent=function(node,text){
if(arguments.length>1){
var _237=dojo.doc();
dojo.dom.replaceChildren(node,_237.createTextNode(text));
return text;
}else{
if(node.textContent!=undefined){
return node.textContent;
}
var _238="";
if(node==null){
return _238;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
_238+=dojo.dom.textContent(node.childNodes[i]);
break;
case 3:
case 2:
case 4:
_238+=node.childNodes[i].nodeValue;
break;
default:
break;
}
}
return _238;
}
};
dojo.dom.hasParent=function(node){
return node&&node.parentNode&&dojo.dom.isNode(node.parentNode);
};
dojo.dom.isTag=function(node){
if(node&&node.tagName){
for(var i=1;i<arguments.length;i++){
if(node.tagName==String(arguments[i])){
return String(arguments[i]);
}
}
}
return "";
};
dojo.dom.setAttributeNS=function(elem,_23e,_23f,_240){
if(elem==null||((elem==undefined)&&(typeof elem=="undefined"))){
dojo.raise("No element given to dojo.dom.setAttributeNS");
}
if(!((elem.setAttributeNS==undefined)&&(typeof elem.setAttributeNS=="undefined"))){
elem.setAttributeNS(_23e,_23f,_240);
}else{
var _241=elem.ownerDocument;
var _242=_241.createNode(2,_23f,_23e);
_242.nodeValue=_240;
elem.setAttributeNode(_242);
}
};
dojo.provide("dojo.undo.browser");
try{
if((!djConfig["preventBackButtonFix"])&&(!dojo.hostenv.post_load_)){
document.write("<iframe style='border: 0px; width: 1px; height: 1px; position: absolute; bottom: 0px; right: 0px; visibility: visible;' name='djhistory' id='djhistory' src='"+(dojo.hostenv.getBaseScriptUri()+"iframe_history.html")+"'></iframe>");
}
}
catch(e){
}
if(dojo.render.html.opera){
dojo.debug("Opera is not supported with dojo.undo.browser, so back/forward detection will not work.");
}
dojo.undo.browser={initialHref:window.location.href,initialHash:window.location.hash,moveForward:false,historyStack:[],forwardStack:[],historyIframe:null,bookmarkAnchor:null,locationTimer:null,setInitialState:function(args){
this.initialState=this._createState(this.initialHref,args,this.initialHash);
},addToHistory:function(args){
this.forwardStack=[];
var hash=null;
var url=null;
if(!this.historyIframe){
this.historyIframe=window.frames["djhistory"];
}
if(!this.bookmarkAnchor){
this.bookmarkAnchor=document.createElement("a");
dojo.body().appendChild(this.bookmarkAnchor);
this.bookmarkAnchor.style.display="none";
}
if(args["changeUrl"]){
hash="#"+((args["changeUrl"]!==true)?args["changeUrl"]:(new Date()).getTime());
if(this.historyStack.length==0&&this.initialState.urlHash==hash){
this.initialState=this._createState(url,args,hash);
return;
}else{
if(this.historyStack.length>0&&this.historyStack[this.historyStack.length-1].urlHash==hash){
this.historyStack[this.historyStack.length-1]=this._createState(url,args,hash);
return;
}
}
this.changingUrl=true;
setTimeout("window.location.href = '"+hash+"'; dojo.undo.browser.changingUrl = false;",1);
this.bookmarkAnchor.href=hash;
if(dojo.render.html.ie){
url=this._loadIframeHistory();
var _247=args["back"]||args["backButton"]||args["handle"];
var tcb=function(_249){
if(window.location.hash!=""){
setTimeout("window.location.href = '"+hash+"';",1);
}
_247.apply(this,[_249]);
};
if(args["back"]){
args.back=tcb;
}else{
if(args["backButton"]){
args.backButton=tcb;
}else{
if(args["handle"]){
args.handle=tcb;
}
}
}
var _24a=args["forward"]||args["forwardButton"]||args["handle"];
var tfw=function(_24c){
if(window.location.hash!=""){
window.location.href=hash;
}
if(_24a){
_24a.apply(this,[_24c]);
}
};
if(args["forward"]){
args.forward=tfw;
}else{
if(args["forwardButton"]){
args.forwardButton=tfw;
}else{
if(args["handle"]){
args.handle=tfw;
}
}
}
}else{
if(dojo.render.html.moz){
if(!this.locationTimer){
this.locationTimer=setInterval("dojo.undo.browser.checkLocation();",200);
}
}
}
}else{
url=this._loadIframeHistory();
}
this.historyStack.push(this._createState(url,args,hash));
},checkLocation:function(){
if(!this.changingUrl){
var hsl=this.historyStack.length;
if((window.location.hash==this.initialHash||window.location.href==this.initialHref)&&(hsl==1)){
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
}
},iframeLoaded:function(evt,_24f){
if(!dojo.render.html.opera){
var _250=this._getUrlQuery(_24f.href);
if(_250==null){
if(this.historyStack.length==1){
this.handleBackButton();
}
return;
}
if(this.moveForward){
this.moveForward=false;
return;
}
if(this.historyStack.length>=2&&_250==this._getUrlQuery(this.historyStack[this.historyStack.length-2].url)){
this.handleBackButton();
}else{
if(this.forwardStack.length>0&&_250==this._getUrlQuery(this.forwardStack[this.forwardStack.length-1].url)){
this.handleForwardButton();
}
}
}
},handleBackButton:function(){
var _251=this.historyStack.pop();
if(!_251){
return;
}
var last=this.historyStack[this.historyStack.length-1];
if(!last&&this.historyStack.length==0){
last=this.initialState;
}
if(last){
if(last.kwArgs["back"]){
last.kwArgs["back"]();
}else{
if(last.kwArgs["backButton"]){
last.kwArgs["backButton"]();
}else{
if(last.kwArgs["handle"]){
last.kwArgs.handle("back");
}
}
}
}
this.forwardStack.push(_251);
},handleForwardButton:function(){
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
},_createState:function(url,args,hash){
return {"url":url,"kwArgs":args,"urlHash":hash};
},_getUrlQuery:function(url){
var _258=url.split("?");
if(_258.length<2){
return null;
}else{
return _258[1];
}
},_loadIframeHistory:function(){
var url=dojo.hostenv.getBaseScriptUri()+"iframe_history.html?"+(new Date()).getTime();
this.moveForward=true;
dojo.io.setIFrameSrc(this.historyIframe,url,false);
return url;
}};
dojo.provide("dojo.io.BrowserIO");
dojo.io.checkChildrenForFile=function(node){
var _25b=false;
var _25c=node.getElementsByTagName("input");
dojo.lang.forEach(_25c,function(_25d){
if(_25b){
return;
}
if(_25d.getAttribute("type")=="file"){
_25b=true;
}
});
return _25b;
};
dojo.io.formHasFile=function(_25e){
return dojo.io.checkChildrenForFile(_25e);
};
dojo.io.updateNode=function(node,_260){
node=dojo.byId(node);
var args=_260;
if(dojo.lang.isString(_260)){
args={url:_260};
}
args.mimetype="text/html";
args.load=function(t,d,e){
while(node.firstChild){
if(dojo["event"]){
try{
dojo.event.browser.clean(node.firstChild);
}
catch(e){
}
}
node.removeChild(node.firstChild);
}
node.innerHTML=d;
};
dojo.io.bind(args);
};
dojo.io.formFilter=function(node){
var type=(node.type||"").toLowerCase();
return !node.disabled&&node.name&&!dojo.lang.inArray(["file","submit","image","reset","button"],type);
};
dojo.io.encodeForm=function(_267,_268,_269){
if((!_267)||(!_267.tagName)||(!_267.tagName.toLowerCase()=="form")){
dojo.raise("Attempted to encode a non-form element.");
}
if(!_269){
_269=dojo.io.formFilter;
}
var enc=/utf/i.test(_268||"")?encodeURIComponent:dojo.string.encodeAscii;
var _26b=[];
for(var i=0;i<_267.elements.length;i++){
var elm=_267.elements[i];
if(!elm||elm.tagName.toLowerCase()=="fieldset"||!_269(elm)){
continue;
}
var name=enc(elm.name);
var type=elm.type.toLowerCase();
if(type=="select-multiple"){
for(var j=0;j<elm.options.length;j++){
if(elm.options[j].selected){
_26b.push(name+"="+enc(elm.options[j].value));
}
}
}else{
if(dojo.lang.inArray(["radio","checkbox"],type)){
if(elm.checked){
_26b.push(name+"="+enc(elm.value));
}
}else{
_26b.push(name+"="+enc(elm.value));
}
}
}
var _271=_267.getElementsByTagName("input");
for(var i=0;i<_271.length;i++){
var _272=_271[i];
if(_272.type.toLowerCase()=="image"&&_272.form==_267&&_269(_272)){
var name=enc(_272.name);
_26b.push(name+"="+enc(_272.value));
_26b.push(name+".x=0");
_26b.push(name+".y=0");
}
}
return _26b.join("&")+"&";
};
dojo.io.FormBind=function(args){
this.bindArgs={};
if(args&&args.formNode){
this.init(args);
}else{
if(args){
this.init({formNode:args});
}
}
};
dojo.lang.extend(dojo.io.FormBind,{form:null,bindArgs:null,clickedButton:null,init:function(args){
var form=dojo.byId(args.formNode);
if(!form||!form.tagName||form.tagName.toLowerCase()!="form"){
throw new Error("FormBind: Couldn't apply, invalid form");
}else{
if(this.form==form){
return;
}else{
if(this.form){
throw new Error("FormBind: Already applied to a form");
}
}
}
dojo.lang.mixin(this.bindArgs,args);
this.form=form;
this.connect(form,"onsubmit","submit");
for(var i=0;i<form.elements.length;i++){
var node=form.elements[i];
if(node&&node.type&&dojo.lang.inArray(["submit","button"],node.type.toLowerCase())){
this.connect(node,"onclick","click");
}
}
var _278=form.getElementsByTagName("input");
for(var i=0;i<_278.length;i++){
var _279=_278[i];
if(_279.type.toLowerCase()=="image"&&_279.form==form){
this.connect(_279,"onclick","click");
}
}
},onSubmit:function(form){
return true;
},submit:function(e){
e.preventDefault();
if(this.onSubmit(this.form)){
dojo.io.bind(dojo.lang.mixin(this.bindArgs,{formFilter:dojo.lang.hitch(this,"formFilter")}));
}
},click:function(e){
var node=e.currentTarget;
if(node.disabled){
return;
}
this.clickedButton=node;
},formFilter:function(node){
var type=(node.type||"").toLowerCase();
var _280=false;
if(node.disabled||!node.name){
_280=false;
}else{
if(dojo.lang.inArray(["submit","button","image"],type)){
if(!this.clickedButton){
this.clickedButton=node;
}
_280=node==this.clickedButton;
}else{
_280=!dojo.lang.inArray(["file","submit","reset","button"],type);
}
}
return _280;
},connect:function(_281,_282,_283){
if(dojo.evalObjPath("dojo.event.connect")){
dojo.event.connect(_281,_282,this,_283);
}else{
var fcn=dojo.lang.hitch(this,_283);
_281[_282]=function(e){
if(!e){
e=window.event;
}
if(!e.currentTarget){
e.currentTarget=e.srcElement;
}
if(!e.preventDefault){
e.preventDefault=function(){
window.event.returnValue=false;
};
}
fcn(e);
};
}
}});
dojo.io.XMLHTTPTransport=new function(){
var _286=this;
var _287={};
this.useCache=false;
this.preventCache=false;
function getCacheKey(url,_289,_28a){
return url+"|"+_289+"|"+_28a.toLowerCase();
}
function addToCache(url,_28c,_28d,http){
_287[getCacheKey(url,_28c,_28d)]=http;
}
function getFromCache(url,_290,_291){
return _287[getCacheKey(url,_290,_291)];
}
this.clearCache=function(){
_287={};
};
function doLoad(_292,http,url,_295,_296){
if(((http.status>=200)&&(http.status<300))||(http.status==304)||(location.protocol=="file:"&&(http.status==0||http.status==undefined))||(location.protocol=="chrome:"&&(http.status==0||http.status==undefined))){
var ret;
if(_292.method.toLowerCase()=="head"){
var _298=http.getAllResponseHeaders();
ret={};
ret.toString=function(){
return _298;
};
var _299=_298.split(/[\r\n]+/g);
for(var i=0;i<_299.length;i++){
var pair=_299[i].match(/^([^:]+)\s*:\s*(.+)$/i);
if(pair){
ret[pair[1]]=pair[2];
}
}
}else{
if(_292.mimetype=="text/javascript"){
try{
ret=dj_eval(http.responseText);
}
catch(e){
dojo.debug(e);
dojo.debug(http.responseText);
ret=null;
}
}else{
if(_292.mimetype=="text/json"){
try{
ret=dj_eval("("+http.responseText+")");
}
catch(e){
dojo.debug(e);
dojo.debug(http.responseText);
ret=false;
}
}else{
if((_292.mimetype=="application/xml")||(_292.mimetype=="text/xml")){
ret=http.responseXML;
if(!ret||typeof ret=="string"||!http.getResponseHeader("Content-Type")){
ret=dojo.dom.createDocumentFromText(http.responseText);
}
}else{
ret=http.responseText;
}
}
}
}
if(_296){
addToCache(url,_295,_292.method,http);
}
_292[(typeof _292.load=="function")?"load":"handle"]("load",ret,http,_292);
}else{
var _29c=new dojo.io.Error("XMLHttpTransport Error: "+http.status+" "+http.statusText);
_292[(typeof _292.error=="function")?"error":"handle"]("error",_29c,http,_292);
}
}
function setHeaders(http,_29e){
if(_29e["headers"]){
for(var _29f in _29e["headers"]){
if(_29f.toLowerCase()=="content-type"&&!_29e["contentType"]){
_29e["contentType"]=_29e["headers"][_29f];
}else{
http.setRequestHeader(_29f,_29e["headers"][_29f]);
}
}
}
}
this.inFlight=[];
this.inFlightTimer=null;
this.startWatchingInFlight=function(){
if(!this.inFlightTimer){
this.inFlightTimer=setTimeout("dojo.io.XMLHTTPTransport.watchInFlight();",10);
}
};
this.watchInFlight=function(){
var now=null;
if(!dojo.hostenv._blockAsync&&!_286._blockAsync){
for(var x=this.inFlight.length-1;x>=0;x--){
try{
var tif=this.inFlight[x];
if(!tif||tif.http._aborted||!tif.http.readyState){
this.inFlight.splice(x,1);
continue;
}
if(4==tif.http.readyState){
this.inFlight.splice(x,1);
doLoad(tif.req,tif.http,tif.url,tif.query,tif.useCache);
}else{
if(tif.startTime){
if(!now){
now=(new Date()).getTime();
}
if(tif.startTime+(tif.req.timeoutSeconds*1000)<now){
if(typeof tif.http.abort=="function"){
tif.http.abort();
}
this.inFlight.splice(x,1);
tif.req[(typeof tif.req.timeout=="function")?"timeout":"handle"]("timeout",null,tif.http,tif.req);
}
}
}
}
catch(e){
try{
var _2a3=new dojo.io.Error("XMLHttpTransport.watchInFlight Error: "+e);
tif.req[(typeof tif.req.error=="function")?"error":"handle"]("error",_2a3,tif.http,tif.req);
}
catch(e2){
dojo.debug("XMLHttpTransport error callback failed: "+e2);
}
}
}
}
clearTimeout(this.inFlightTimer);
if(this.inFlight.length==0){
this.inFlightTimer=null;
return;
}
this.inFlightTimer=setTimeout("dojo.io.XMLHTTPTransport.watchInFlight();",10);
};
var _2a4=dojo.hostenv.getXmlhttpObject()?true:false;
this.canHandle=function(_2a5){
return _2a4&&dojo.lang.inArray(["text/plain","text/html","application/xml","text/xml","text/javascript","text/json"],(_2a5["mimetype"].toLowerCase()||""))&&!(_2a5["formNode"]&&dojo.io.formHasFile(_2a5["formNode"]));
};
this.multipartBoundary="45309FFF-BD65-4d50-99C9-36986896A96F";
this.bind=function(_2a6){
if(!_2a6["url"]){
if(!_2a6["formNode"]&&(_2a6["backButton"]||_2a6["back"]||_2a6["changeUrl"]||_2a6["watchForURL"])&&(!djConfig.preventBackButtonFix)){
dojo.deprecated("Using dojo.io.XMLHTTPTransport.bind() to add to browser history without doing an IO request","Use dojo.undo.browser.addToHistory() instead.","0.4");
dojo.undo.browser.addToHistory(_2a6);
return true;
}
}
var url=_2a6.url;
var _2a8="";
if(_2a6["formNode"]){
var ta=_2a6.formNode.getAttribute("action");
if((ta)&&(!_2a6["url"])){
url=ta;
}
var tp=_2a6.formNode.getAttribute("method");
if((tp)&&(!_2a6["method"])){
_2a6.method=tp;
}
_2a8+=dojo.io.encodeForm(_2a6.formNode,_2a6.encoding,_2a6["formFilter"]);
}
if(url.indexOf("#")>-1){
dojo.debug("Warning: dojo.io.bind: stripping hash values from url:",url);
url=url.split("#")[0];
}
if(_2a6["file"]){
_2a6.method="post";
}
if(!_2a6["method"]){
_2a6.method="get";
}
if(_2a6.method.toLowerCase()=="get"){
_2a6.multipart=false;
}else{
if(_2a6["file"]){
_2a6.multipart=true;
}else{
if(!_2a6["multipart"]){
_2a6.multipart=false;
}
}
}
if(_2a6["backButton"]||_2a6["back"]||_2a6["changeUrl"]){
dojo.undo.browser.addToHistory(_2a6);
}
var _2ab=_2a6["content"]||{};
if(_2a6.sendTransport){
_2ab["dojo.transport"]="xmlhttp";
}
do{
if(_2a6.postContent){
_2a8=_2a6.postContent;
break;
}
if(_2ab){
_2a8+=dojo.io.argsFromMap(_2ab,_2a6.encoding);
}
if(_2a6.method.toLowerCase()=="get"||!_2a6.multipart){
break;
}
var t=[];
if(_2a8.length){
var q=_2a8.split("&");
for(var i=0;i<q.length;++i){
if(q[i].length){
var p=q[i].split("=");
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+p[0]+"\"","",p[1]);
}
}
}
if(_2a6.file){
if(dojo.lang.isArray(_2a6.file)){
for(var i=0;i<_2a6.file.length;++i){
var o=_2a6.file[i];
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+o.name+"\"; filename=\""+("fileName" in o?o.fileName:o.name)+"\"","Content-Type: "+("contentType" in o?o.contentType:"application/octet-stream"),"",o.content);
}
}else{
var o=_2a6.file;
t.push("--"+this.multipartBoundary,"Content-Disposition: form-data; name=\""+o.name+"\"; filename=\""+("fileName" in o?o.fileName:o.name)+"\"","Content-Type: "+("contentType" in o?o.contentType:"application/octet-stream"),"",o.content);
}
}
if(t.length){
t.push("--"+this.multipartBoundary+"--","");
_2a8=t.join("\r\n");
}
}while(false);
var _2b1=_2a6["sync"]?false:true;
var _2b2=_2a6["preventCache"]||(this.preventCache==true&&_2a6["preventCache"]!=false);
var _2b3=_2a6["useCache"]==true||(this.useCache==true&&_2a6["useCache"]!=false);
if(!_2b2&&_2b3){
var _2b4=getFromCache(url,_2a8,_2a6.method);
if(_2b4){
doLoad(_2a6,_2b4,url,_2a8,false);
return;
}
}
var http=dojo.hostenv.getXmlhttpObject(_2a6);
var _2b6=false;
if(_2b1){
var _2b7=this.inFlight.push({"req":_2a6,"http":http,"url":url,"query":_2a8,"useCache":_2b3,"startTime":_2a6.timeoutSeconds?(new Date()).getTime():0});
this.startWatchingInFlight();
}else{
_286._blockAsync=true;
}
if(_2a6.method.toLowerCase()=="post"){
if(!_2a6.user){
http.open("POST",url,_2b1);
}else{
http.open("POST",url,_2b1,_2a6.user,_2a6.password);
}
setHeaders(http,_2a6);
http.setRequestHeader("Content-Type",_2a6.multipart?("multipart/form-data; boundary="+this.multipartBoundary):(_2a6.contentType||"application/x-www-form-urlencoded"));
try{
http.send(_2a8);
}
catch(e){
if(typeof http.abort=="function"){
http.abort();
}
doLoad(_2a6,{status:404},url,_2a8,_2b3);
}
}else{
var _2b8=url;
if(_2a8!=""){
_2b8+=(_2b8.indexOf("?")>-1?"&":"?")+_2a8;
}
if(_2b2){
_2b8+=(dojo.string.endsWithAny(_2b8,"?","&")?"":(_2b8.indexOf("?")>-1?"&":"?"))+"dojo.preventCache="+new Date().valueOf();
}
if(!_2a6.user){
http.open(_2a6.method.toUpperCase(),_2b8,_2b1);
}else{
http.open(_2a6.method.toUpperCase(),_2b8,_2b1,_2a6.user,_2a6.password);
}
setHeaders(http,_2a6);
try{
http.send(null);
}
catch(e){
if(typeof http.abort=="function"){
http.abort();
}
doLoad(_2a6,{status:404},url,_2a8,_2b3);
}
}
if(!_2b1){
doLoad(_2a6,http,url,_2a8,_2b3);
_286._blockAsync=false;
}
_2a6.abort=function(){
try{
http._aborted=true;
}
catch(e){
}
return http.abort();
};
return;
};
dojo.io.transports.addTransport("XMLHTTPTransport");
};
dojo.provide("dojo.io.cookie");
dojo.io.cookie.setCookie=function(name,_2ba,days,path,_2bd,_2be){
var _2bf=-1;
if(typeof days=="number"&&days>=0){
var d=new Date();
d.setTime(d.getTime()+(days*24*60*60*1000));
_2bf=d.toGMTString();
}
_2ba=escape(_2ba);
document.cookie=name+"="+_2ba+";"+(_2bf!=-1?" expires="+_2bf+";":"")+(path?"path="+path:"")+(_2bd?"; domain="+_2bd:"")+(_2be?"; secure":"");
};
dojo.io.cookie.set=dojo.io.cookie.setCookie;
dojo.io.cookie.getCookie=function(name){
var idx=document.cookie.lastIndexOf(name+"=");
if(idx==-1){
return null;
}
var _2c3=document.cookie.substring(idx+name.length+1);
var end=_2c3.indexOf(";");
if(end==-1){
end=_2c3.length;
}
_2c3=_2c3.substring(0,end);
_2c3=unescape(_2c3);
return _2c3;
};
dojo.io.cookie.get=dojo.io.cookie.getCookie;
dojo.io.cookie.deleteCookie=function(name){
dojo.io.cookie.setCookie(name,"-",0);
};
dojo.io.cookie.setObjectCookie=function(name,obj,days,path,_2ca,_2cb,_2cc){
if(arguments.length==5){
_2cc=_2ca;
_2ca=null;
_2cb=null;
}
var _2cd=[],_2ce,_2cf="";
if(!_2cc){
_2ce=dojo.io.cookie.getObjectCookie(name);
}
if(days>=0){
if(!_2ce){
_2ce={};
}
for(var prop in obj){
if(prop==null){
delete _2ce[prop];
}else{
if(typeof obj[prop]=="string"||typeof obj[prop]=="number"){
_2ce[prop]=obj[prop];
}
}
}
prop=null;
for(var prop in _2ce){
_2cd.push(escape(prop)+"="+escape(_2ce[prop]));
}
_2cf=_2cd.join("&");
}
dojo.io.cookie.setCookie(name,_2cf,days,path,_2ca,_2cb);
};
dojo.io.cookie.getObjectCookie=function(name){
var _2d2=null,_2d3=dojo.io.cookie.getCookie(name);
if(_2d3){
_2d2={};
var _2d4=_2d3.split("&");
for(var i=0;i<_2d4.length;i++){
var pair=_2d4[i].split("=");
var _2d7=pair[1];
if(isNaN(_2d7)){
_2d7=unescape(pair[1]);
}
_2d2[unescape(pair[0])]=_2d7;
}
}
return _2d2;
};
dojo.io.cookie.isSupported=function(){
if(typeof navigator.cookieEnabled!="boolean"){
dojo.io.cookie.setCookie("__TestingYourBrowserForCookieSupport__","CookiesAllowed",90,null);
var _2d8=dojo.io.cookie.getCookie("__TestingYourBrowserForCookieSupport__");
navigator.cookieEnabled=(_2d8=="CookiesAllowed");
if(navigator.cookieEnabled){
this.deleteCookie("__TestingYourBrowserForCookieSupport__");
}
}
return navigator.cookieEnabled;
};
if(!dojo.io.cookies){
dojo.io.cookies=dojo.io.cookie;
}
dojo.provide("dojo.io.*");
dojo.provide("dojo.io");
dojo.deprecated("dojo.io","replaced by dojo.io.*","0.5");
dojo.provide("dojo.xml.Parse");
dojo.xml.Parse=function(){
function getTagName(node){
return ((node)&&(node.tagName)?node.tagName.toLowerCase():"");
}
function getDojoTagName(node){
var _2db=getTagName(node);
if(!_2db){
return "";
}
if((dojo.widget)&&(dojo.widget.tags[_2db])){
return _2db;
}
var p=_2db.indexOf(":");
if(p>=0){
return _2db;
}
if(_2db.substr(0,5)=="dojo:"){
return _2db;
}
if(dojo.render.html.capable&&dojo.render.html.ie&&node.scopeName!="HTML"){
return node.scopeName.toLowerCase()+":"+_2db;
}
if(_2db.substr(0,4)=="dojo"){
return "dojo:"+_2db.substring(4);
}
var djt=node.getAttribute("dojoType")||node.getAttribute("dojotype");
if(djt){
if(djt.indexOf(":")<0){
djt="dojo:"+djt;
}
return djt.toLowerCase();
}
djt=node.getAttributeNS&&node.getAttributeNS(dojo.dom.dojoml,"type");
if(djt){
return "dojo:"+djt.toLowerCase();
}
try{
djt=node.getAttribute("dojo:type");
}
catch(e){
}
if(djt){
return "dojo:"+djt.toLowerCase();
}
if(!dj_global["djConfig"]||!djConfig["ignoreClassNames"]){
var _2de=node.className||node.getAttribute("class");
if(_2de&&_2de.indexOf&&_2de.indexOf("dojo-")!=-1){
var _2df=_2de.split(" ");
for(var x=0,c=_2df.length;x<c;x++){
if(_2df[x].slice(0,5)=="dojo-"){
return "dojo:"+_2df[x].substr(5).toLowerCase();
}
}
}
}
return "";
}
this.parseElement=function(node,_2e3,_2e4,_2e5){
var _2e6={};
var _2e7=getTagName(node);
if((_2e7)&&(_2e7.indexOf("/")==0)){
return null;
}
var _2e8=true;
if(_2e4){
var _2e9=getDojoTagName(node);
_2e7=_2e9||_2e7;
_2e8=Boolean(_2e9);
}
_2e6[_2e7]=[];
var pos=_2e7.indexOf(":");
if(pos>0){
var ns=_2e7.substring(0,pos);
_2e6["namespace"]=ns;
if((dojo["namespace"])&&(!dojo["namespace"].allow(ns))){
_2e8=false;
}
}
if(_2e8){
var _2ec=this.parseAttributes(node);
for(var attr in _2ec){
if((!_2e6[_2e7][attr])||(typeof _2e6[_2e7][attr]!="array")){
_2e6[_2e7][attr]=[];
}
_2e6[_2e7][attr].push(_2ec[attr]);
}
_2e6[_2e7].nodeRef=node;
_2e6.tagName=_2e7;
_2e6.index=_2e5||0;
}
var _2ee=0;
for(var i=0;i<node.childNodes.length;i++){
var tcn=node.childNodes.item(i);
switch(tcn.nodeType){
case dojo.dom.ELEMENT_NODE:
_2ee++;
var ctn=getDojoTagName(tcn)||getTagName(tcn);
if(!_2e6[ctn]){
_2e6[ctn]=[];
}
_2e6[ctn].push(this.parseElement(tcn,true,_2e4,_2ee));
if((tcn.childNodes.length==1)&&(tcn.childNodes.item(0).nodeType==dojo.dom.TEXT_NODE)){
_2e6[ctn][_2e6[ctn].length-1].value=tcn.childNodes.item(0).nodeValue;
}
break;
case dojo.dom.TEXT_NODE:
if(node.childNodes.length==1){
_2e6[_2e7].push({value:node.childNodes.item(0).nodeValue});
}
break;
default:
break;
}
}
return _2e6;
};
this.parseAttributes=function(node){
var _2f3={};
var atts=node.attributes;
var _2f5,i=0;
while((_2f5=atts[i++])){
if((dojo.render.html.capable)&&(dojo.render.html.ie)){
if(!_2f5){
continue;
}
if((typeof _2f5=="object")&&(typeof _2f5.nodeValue=="undefined")||(_2f5.nodeValue==null)||(_2f5.nodeValue=="")){
continue;
}
}
var nn=_2f5.nodeName.split(":");
nn=(nn.length==2)?nn[1]:_2f5.nodeName;
_2f3[nn]={value:_2f5.nodeValue};
}
return _2f3;
};
};
dojo.provide("dojo.lang.declare");
dojo.lang.declare=function(_2f8,_2f9,init,_2fb){
if((dojo.lang.isFunction(_2fb))||((!_2fb)&&(!dojo.lang.isFunction(init)))){
var temp=_2fb;
_2fb=init;
init=temp;
}
var _2fd=[];
if(dojo.lang.isArray(_2f9)){
_2fd=_2f9;
_2f9=_2fd.shift();
}
if(!init){
init=dojo.evalObjPath(_2f8,false);
if((init)&&(!dojo.lang.isFunction(init))){
init=null;
}
}
var ctor=dojo.lang.declare._makeConstructor();
var scp=(_2f9?_2f9.prototype:null);
if(scp){
scp.prototyping=true;
ctor.prototype=new _2f9();
scp.prototyping=false;
}
ctor.superclass=scp;
ctor.mixins=_2fd;
for(var i=0,l=_2fd.length;i<l;i++){
dojo.lang.extend(ctor,_2fd[i].prototype);
}
ctor.prototype.initializer=null;
ctor.prototype.declaredClass=_2f8;
if(dojo.lang.isArray(_2fb)){
dojo.lang.extend.apply(dojo.lang,[ctor].concat(_2fb));
}else{
dojo.lang.extend(ctor,(_2fb)||{});
}
dojo.lang.extend(ctor,dojo.lang.declare.base);
ctor.prototype.constructor=ctor;
ctor.prototype.initializer=(ctor.prototype.initializer)||(init)||(function(){
});
dojo.lang.setObjPathValue(_2f8,ctor,null,true);
return ctor;
};
dojo.lang.declare._makeConstructor=function(){
return function(){
var self=this._getPropContext();
var s=self.constructor.superclass;
if((s)&&(s.constructor)){
if(s.constructor==arguments.callee){
this.inherited("constructor",arguments);
}else{
this._inherited(s,"constructor",arguments);
}
}
var m=(self.constructor.mixins)||([]);
for(var i=0,l=m.length;i<l;i++){
(((m[i].prototype)&&(m[i].prototype.initializer))||(m[i])).apply(this,arguments);
}
if((!this.prototyping)&&(self.initializer)){
self.initializer.apply(this,arguments);
}
};
};
dojo.lang.declare.base={_getPropContext:function(){
return (this.___proto||this);
},_inherited:function(_307,_308,args){
var _30a,_30b=this.___proto;
this.___proto=_307;
try{
_30a=_307[_308].apply(this,(args||[]));
}
catch(e){
throw e;
}
finally{
this.___proto=_30b;
}
return _30a;
},inheritedFrom:function(ctor,prop,args){
var p=((ctor)&&(ctor.prototype)&&(ctor.prototype[prop]));
return (dojo.lang.isFunction(p)?p.apply(this,(args||[])):p);
},inherited:function(prop,args){
var p=this._getPropContext();
do{
if((!p.constructor)||(!p.constructor.superclass)){
return;
}
p=p.constructor.superclass;
}while(!(prop in p));
return (dojo.lang.isFunction(p[prop])?this._inherited(p,prop,args):p[prop]);
}};
dojo.declare=dojo.lang.declare;
dojo.provide("dojo.namespace");
dojo["namespace"]={dojo:"dojo",namespaces:{},failed:{},loading:{},loaded:{},register:function(name,_314,_315,_316){
if(!_316||!this.namespaces[name]){
this.namespaces[name]=new dojo["namespace"].Namespace(name,_314,_315);
}
},allow:function(name){
if(this.failed[name]){
return false;
}
var excl=djConfig.excludeNamespace;
if(excl&&dojo.lang.inArray(excl,name)){
return false;
}
var incl=djConfig.includeNamespace;
return ((name==this.dojo)||!incl||dojo.lang.inArray(incl,name));
},get:function(name){
return this.namespaces[name];
},require:function(name){
var ns=this.namespaces[name];
if(ns&&this.loaded[name]){
return ns;
}
if(!this.allow(name)){
return false;
}
if(this.loading[name]){
dojo.debug("dojo.namespace.require: re-entrant request to load namespace \""+name+"\" must fail.");
return false;
}
var req=dojo.require;
this.loading[name]=true;
try{
if(name==this.dojo){
req("dojo.namespaces.dojo");
}else{
if(!dojo.hostenv.moduleHasPrefix(name)){
dojo.registerModulePath(name,"../"+name);
}
req([name,"manifest"].join("."),false,true);
}
if(!this.namespaces[name]){
this.failed[name]=true;
}
}
finally{
this.loading[name]=false;
}
return this.namespaces[name];
}};
dojo.registerNamespace=function(name,_31f,_320){
dojo["namespace"].register.apply(dojo["namespace"],arguments);
};
dojo.registerNamespaceResolver=function(name,_322){
var n=dojo["namespace"].namespaces[name];
if(n){
n.resolver=_322;
}
};
dojo.registerNamespaceManifest=function(_324,path,name,_327,_328){
dojo.registerModulePath(name,path);
dojo.registerNamespace(name,_327,_328);
};
dojo.defineNamespace=function(_329,_32a,_32b,_32c,_32d){
dojo.deprecated("dojo.defineNamespace"," is replaced by other systems. See the Dojo Wiki [http://dojo.jot.com/WikiHome/Modules & Namespaces].","0.5");
dojo.registerNamespaceManifest(_329,_32a,_32b,_32d,_32c);
};
dojo["namespace"].Namespace=function(name,_32f,_330){
this.name=name;
this.module=_32f;
this.resolver=_330;
};
dojo["namespace"].Namespace.prototype._loaded={};
dojo["namespace"].Namespace.prototype._failed={};
dojo["namespace"].Namespace.prototype.resolve=function(name,_332,_333){
if(!this.resolver){
return false;
}
var _334=this.resolver(name,_332);
if(_334&&!this._loaded[_334]&&!this._failed[_334]){
var req=dojo.require;
req(_334,false,true);
if(dojo.hostenv.findModule(_334,false)){
this._loaded[_334]=true;
}else{
if(!omit_module_check){
dojo.raise("dojo.namespace.Namespace.resolve: module '"+_334+"' not found after loading via namespace '"+this.name+"'");
}
this._failed[_334]=true;
}
}
return Boolean(this._loaded[_334]);
};
dojo["namespace"].Namespace.prototype.getModule=function(_336){
if(!this.module){
return null;
}
if(!dojo.lang.isArray(this.module)){
return this.module;
}
if(this.module.length<=0){
return null;
}
if(!this.resolver){
return this.module[0];
}
var _337=this.resolver(_336);
if(!_337){
return this.module[0];
}
var _338=_337.lastIndexOf(".");
return (_338>-1)?_337.substr(0,_338):this.module[0];
};
dojo.registerNamespace("dojo",["dojo.widget","dojo.widget.validate"]);
dojo.provide("dojo.event.common");
dojo.event=new function(){
this._canTimeout=dojo.lang.isFunction(dj_global["setTimeout"])||dojo.lang.isAlien(dj_global["setTimeout"]);
function interpolateArgs(args,_33a){
var dl=dojo.lang;
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
if((dl.isObject(args[0]))&&(dl.isString(args[1]))&&(dl.isString(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((dl.isString(args[1]))&&(dl.isString(args[2]))){
ao.srcFunc=args[1];
ao.adviceFunc=args[2];
}else{
if((dl.isObject(args[0]))&&(dl.isString(args[1]))&&(dl.isFunction(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
var _33d=dl.nameAnonFunc(args[2],ao.adviceObj,_33a);
ao.adviceFunc=_33d;
}else{
if((dl.isFunction(args[0]))&&(dl.isObject(args[1]))&&(dl.isString(args[2]))){
ao.adviceType="after";
ao.srcObj=dj_global;
var _33d=dl.nameAnonFunc(args[0],ao.srcObj,_33a);
ao.srcFunc=_33d;
ao.adviceObj=args[1];
ao.adviceFunc=args[2];
}
}
}
}
break;
case 4:
if((dl.isObject(args[0]))&&(dl.isObject(args[2]))){
ao.adviceType="after";
ao.srcObj=args[0];
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isString(args[1]))&&(dl.isObject(args[2]))){
ao.adviceType=args[0];
ao.srcObj=dj_global;
ao.srcFunc=args[1];
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isFunction(args[1]))&&(dl.isObject(args[2]))){
ao.adviceType=args[0];
ao.srcObj=dj_global;
var _33d=dl.nameAnonFunc(args[1],dj_global,_33a);
ao.srcFunc=_33d;
ao.adviceObj=args[2];
ao.adviceFunc=args[3];
}else{
if((dl.isString(args[0]))&&(dl.isObject(args[1]))&&(dl.isString(args[2]))&&(dl.isFunction(args[3]))){
ao.srcObj=args[1];
ao.srcFunc=args[2];
var _33d=dl.nameAnonFunc(args[3],dj_global,_33a);
ao.adviceObj=dj_global;
ao.adviceFunc=_33d;
}else{
if(dl.isObject(args[1])){
ao.srcObj=args[1];
ao.srcFunc=args[2];
ao.adviceObj=dj_global;
ao.adviceFunc=args[3];
}else{
if(dl.isObject(args[2])){
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
if(dl.isFunction(ao.aroundFunc)){
var _33d=dl.nameAnonFunc(ao.aroundFunc,ao.aroundObj,_33a);
ao.aroundFunc=_33d;
}
if(dl.isFunction(ao.srcFunc)){
ao.srcFunc=dl.getNameInObj(ao.srcObj,ao.srcFunc);
}
if(dl.isFunction(ao.adviceFunc)){
ao.adviceFunc=dl.getNameInObj(ao.adviceObj,ao.adviceFunc);
}
if((ao.aroundObj)&&(dl.isFunction(ao.aroundFunc))){
ao.aroundFunc=dl.getNameInObj(ao.aroundObj,ao.aroundFunc);
}
if(!ao.srcObj){
dojo.raise("bad srcObj for srcFunc: "+ao.srcFunc);
}
if(!ao.adviceObj){
dojo.raise("bad adviceObj for adviceFunc: "+ao.adviceFunc);
}
if(!ao.adviceFunc){
dojo.debug("bad adviceFunc for srcFunc: "+ao.srcFunc);
dojo.debugShallow(ao);
}
return ao;
}
this.connect=function(){
if(arguments.length==1){
var ao=arguments[0];
}else{
var ao=interpolateArgs(arguments,true);
}
if(dojo.lang.isString(ao.srcFunc)&&(ao.srcFunc.toLowerCase()=="onkey")){
if(dojo.render.html.ie){
ao.srcFunc="onkeydown";
this.connect(ao);
}
ao.srcFunc="onkeypress";
}
if(dojo.lang.isArray(ao.srcObj)&&ao.srcObj!=""){
var _33f={};
for(var x in ao){
_33f[x]=ao[x];
}
var mjps=[];
dojo.lang.forEach(ao.srcObj,function(src){
if((dojo.render.html.capable)&&(dojo.lang.isString(src))){
src=dojo.byId(src);
}
_33f.srcObj=src;
mjps.push(dojo.event.connect.call(dojo.event,_33f));
});
return mjps;
}
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
if(ao.adviceFunc){
var mjp2=dojo.event.MethodJoinPoint.getForMethod(ao.adviceObj,ao.adviceFunc);
}
mjp.kwAddAdvice(ao);
return mjp;
};
this.log=function(a1,a2){
var _347;
if((arguments.length==1)&&(typeof a1=="object")){
_347=a1;
}else{
_347={srcObj:a1,srcFunc:a2};
}
_347.adviceFunc=function(){
var _348=[];
for(var x=0;x<arguments.length;x++){
_348.push(arguments[x]);
}
dojo.debug("("+_347.srcObj+")."+_347.srcFunc,":",_348.join(", "));
};
this.kwConnect(_347);
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
this.connectOnce=function(){
var ao=interpolateArgs(arguments,true);
ao.once=true;
return this.connect(ao);
};
this._kwConnectImpl=function(_34f,_350){
var fn=(_350)?"disconnect":"connect";
if(typeof _34f["srcFunc"]=="function"){
_34f.srcObj=_34f["srcObj"]||dj_global;
var _352=dojo.lang.nameAnonFunc(_34f.srcFunc,_34f.srcObj,true);
_34f.srcFunc=_352;
}
if(typeof _34f["adviceFunc"]=="function"){
_34f.adviceObj=_34f["adviceObj"]||dj_global;
var _352=dojo.lang.nameAnonFunc(_34f.adviceFunc,_34f.adviceObj,true);
_34f.adviceFunc=_352;
}
_34f.srcObj=_34f["srcObj"]||dj_global;
_34f.adviceObj=_34f["adviceObj"]||_34f["targetObj"]||dj_global;
_34f.adviceFunc=_34f["adviceFunc"]||_34f["targetFunc"];
return dojo.event[fn](_34f);
};
this.kwConnect=function(_353){
return this._kwConnectImpl(_353,false);
};
this.disconnect=function(){
if(arguments.length==1){
var ao=arguments[0];
}else{
var ao=interpolateArgs(arguments,true);
}
if(!ao.adviceFunc){
return;
}
if(dojo.lang.isString(ao.srcFunc)&&(ao.srcFunc.toLowerCase()=="onkey")){
if(dojo.render.html.ie){
ao.srcFunc="onkeydown";
this.disconnect(ao);
}
ao.srcFunc="onkeypress";
}
var mjp=dojo.event.MethodJoinPoint.getForMethod(ao.srcObj,ao.srcFunc);
return mjp.removeAdvice(ao.adviceObj,ao.adviceFunc,ao.adviceType,ao.once);
};
this.kwDisconnect=function(_356){
return this._kwConnectImpl(_356,true);
};
};
dojo.event.MethodInvocation=function(_357,obj,args){
this.jp_=_357;
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
dojo.event.MethodJoinPoint=function(obj,_35f){
this.object=obj||dj_global;
this.methodname=_35f;
this.methodfunc=this.object[_35f];
this.squelch=false;
};
dojo.event.MethodJoinPoint.getForMethod=function(obj,_361){
if(!obj){
obj=dj_global;
}
if(!obj[_361]){
obj[_361]=function(){
};
if(!obj[_361]){
dojo.raise("Cannot set do-nothing method on that object "+_361);
}
}else{
if((!dojo.lang.isFunction(obj[_361]))&&(!dojo.lang.isAlien(obj[_361]))){
return null;
}
}
var _362=_361+"$joinpoint";
var _363=_361+"$joinpoint$method";
var _364=obj[_362];
if(!_364){
var _365=false;
if(dojo.event["browser"]){
if((obj["attachEvent"])||(obj["nodeType"])||(obj["addEventListener"])){
_365=true;
dojo.event.browser.addClobberNodeAttrs(obj,[_362,_363,_361]);
}
}
var _366=obj[_361].length;
obj[_363]=obj[_361];
_364=obj[_362]=new dojo.event.MethodJoinPoint(obj,_363);
obj[_361]=function(){
var args=[];
if((_365)&&(!arguments.length)){
var evt=null;
try{
if(obj.ownerDocument){
evt=obj.ownerDocument.parentWindow.event;
}else{
if(obj.documentElement){
evt=obj.documentElement.ownerDocument.parentWindow.event;
}else{
if(obj.event){
evt=obj.event;
}else{
evt=window.event;
}
}
}
}
catch(e){
evt=window.event;
}
if(evt){
args.push(dojo.event.browser.fixEvent(evt,this));
}
}else{
for(var x=0;x<arguments.length;x++){
if((x==0)&&(_365)&&(dojo.event.browser.isEvent(arguments[x]))){
args.push(dojo.event.browser.fixEvent(arguments[x],this));
}else{
args.push(arguments[x]);
}
}
}
return _364.run.apply(_364,args);
};
obj[_361].__preJoinArity=_366;
}
return _364;
};
dojo.lang.extend(dojo.event.MethodJoinPoint,{unintercept:function(){
this.object[this.methodname]=this.methodfunc;
this.before=[];
this.after=[];
this.around=[];
},disconnect:dojo.lang.forward("unintercept"),run:function(){
var obj=this.object||dj_global;
var args=arguments;
var _36c=[];
for(var x=0;x<args.length;x++){
_36c[x]=args[x];
}
var _36e=function(marr){
if(!marr){
dojo.debug("Null argument to unrollAdvice()");
return;
}
var _370=marr[0]||dj_global;
var _371=marr[1];
if(!_370[_371]){
dojo.raise("function \""+_371+"\" does not exist on \""+_370+"\"");
}
var _372=marr[2]||dj_global;
var _373=marr[3];
var msg=marr[6];
var _375;
var to={args:[],jp_:this,object:obj,proceed:function(){
return _370[_371].apply(_370,to.args);
}};
to.args=_36c;
var _377=parseInt(marr[4]);
var _378=((!isNaN(_377))&&(marr[4]!==null)&&(typeof marr[4]!="undefined"));
if(marr[5]){
var rate=parseInt(marr[5]);
var cur=new Date();
var _37b=false;
if((marr["last"])&&((cur-marr.last)<=rate)){
if(dojo.event._canTimeout){
if(marr["delayTimer"]){
clearTimeout(marr.delayTimer);
}
var tod=parseInt(rate*2);
var mcpy=dojo.lang.shallowCopy(marr);
marr.delayTimer=setTimeout(function(){
mcpy[5]=0;
_36e(mcpy);
},tod);
}
return;
}else{
marr.last=cur;
}
}
if(_373){
_372[_373].call(_372,to);
}else{
if((_378)&&((dojo.render.html)||(dojo.render.svg))){
dj_global["setTimeout"](function(){
if(msg){
_370[_371].call(_370,to);
}else{
_370[_371].apply(_370,args);
}
},_377);
}else{
if(msg){
_370[_371].call(_370,to);
}else{
_370[_371].apply(_370,args);
}
}
}
};
var _37e=function(){
if(this.squelch){
try{
return _36e.apply(this,arguments);
}
catch(e){
dojo.debug(e);
}
}else{
return _36e.apply(this,arguments);
}
};
if((this["before"])&&(this.before.length>0)){
dojo.lang.forEach(this.before.concat(new Array()),_37e);
}
var _37f;
try{
if((this["around"])&&(this.around.length>0)){
var mi=new dojo.event.MethodInvocation(this,obj,args);
_37f=mi.proceed();
}else{
if(this.methodfunc){
_37f=this.object[this.methodname].apply(this.object,args);
}
}
}
catch(e){
if(!this.squelch){
dojo.raise(e);
}
}
if((this["after"])&&(this.after.length>0)){
dojo.lang.forEach(this.after.concat(new Array()),_37e);
}
return (this.methodfunc)?_37f:null;
},getArr:function(kind){
var type="after";
if((typeof kind=="string")&&(kind.indexOf("before")!=-1)){
type="before";
}else{
if(kind=="around"){
type="around";
}
}
if(!this[type]){
this[type]=[];
}
return this[type];
},kwAddAdvice:function(args){
this.addAdvice(args["adviceObj"],args["adviceFunc"],args["aroundObj"],args["aroundFunc"],args["adviceType"],args["precedence"],args["once"],args["delay"],args["rate"],args["adviceMsg"]);
},addAdvice:function(_384,_385,_386,_387,_388,_389,once,_38b,rate,_38d){
var arr=this.getArr(_388);
if(!arr){
dojo.raise("bad this: "+this);
}
var ao=[_384,_385,_386,_387,_38b,rate,_38d];
if(once){
if(this.hasAdvice(_384,_385,_388,arr)>=0){
return;
}
}
if(_389=="first"){
arr.unshift(ao);
}else{
arr.push(ao);
}
},hasAdvice:function(_390,_391,_392,arr){
if(!arr){
arr=this.getArr(_392);
}
var ind=-1;
for(var x=0;x<arr.length;x++){
var aao=(typeof _391=="object")?(new String(_391)).toString():_391;
var a1o=(typeof arr[x][1]=="object")?(new String(arr[x][1])).toString():arr[x][1];
if((arr[x][0]==_390)&&(a1o==aao)){
ind=x;
}
}
return ind;
},removeAdvice:function(_398,_399,_39a,once){
var arr=this.getArr(_39a);
var ind=this.hasAdvice(_398,_399,_39a,arr);
if(ind==-1){
return false;
}
while(ind!=-1){
arr.splice(ind,1);
if(once){
break;
}
ind=this.hasAdvice(_398,_399,_39a,arr);
}
return true;
}});
dojo.provide("dojo.event.topic");
dojo.event.topic=new function(){
this.topics={};
this.getTopic=function(_39e){
if(!this.topics[_39e]){
this.topics[_39e]=new this.TopicImpl(_39e);
}
return this.topics[_39e];
};
this.registerPublisher=function(_39f,obj,_3a1){
var _39f=this.getTopic(_39f);
_39f.registerPublisher(obj,_3a1);
};
this.subscribe=function(_3a2,obj,_3a4){
var _3a2=this.getTopic(_3a2);
_3a2.subscribe(obj,_3a4);
};
this.unsubscribe=function(_3a5,obj,_3a7){
var _3a5=this.getTopic(_3a5);
_3a5.unsubscribe(obj,_3a7);
};
this.destroy=function(_3a8){
this.getTopic(_3a8).destroy();
delete this.topics[_3a8];
};
this.publishApply=function(_3a9,args){
var _3a9=this.getTopic(_3a9);
_3a9.sendMessage.apply(_3a9,args);
};
this.publish=function(_3ab,_3ac){
var _3ab=this.getTopic(_3ab);
var args=[];
for(var x=1;x<arguments.length;x++){
args.push(arguments[x]);
}
_3ab.sendMessage.apply(_3ab,args);
};
};
dojo.event.topic.TopicImpl=function(_3af){
this.topicName=_3af;
this.subscribe=function(_3b0,_3b1){
var tf=_3b1||_3b0;
var to=(!_3b1)?dj_global:_3b0;
dojo.event.kwConnect({srcObj:this,srcFunc:"sendMessage",adviceObj:to,adviceFunc:tf});
};
this.unsubscribe=function(_3b4,_3b5){
var tf=(!_3b5)?_3b4:_3b5;
var to=(!_3b5)?null:_3b4;
dojo.event.kwDisconnect({srcObj:this,srcFunc:"sendMessage",adviceObj:to,adviceFunc:tf});
};
this._getJoinPoint=function(){
return dojo.event.MethodJoinPoint.getForMethod(this,"sendMessage");
};
this.setSquelch=function(_3b8){
this._getJoinPoint().squelch=_3b8;
};
this.destroy=function(){
this._getJoinPoint().disconnect();
};
this.registerPublisher=function(_3b9,_3ba){
dojo.event.connect(_3b9,_3ba,this,"sendMessage");
};
this.sendMessage=function(_3bb){
};
};
dojo.provide("dojo.event.browser");
dojo._ie_clobber=new function(){
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
this.clobber=function(_3be){
var na;
var tna;
if(_3be){
tna=_3be.all||_3be.getElementsByTagName("*");
na=[_3be];
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
var _3c2={};
for(var i=na.length-1;i>=0;i=i-1){
var el=na[i];
try{
if(el&&el["__clobberAttrs__"]){
for(var j=0;j<el.__clobberAttrs__.length;j++){
nukeProp(el,el.__clobberAttrs__[j]);
}
nukeProp(el,"__clobberAttrs__");
nukeProp(el,"__doClobber__");
}
}
catch(e){
}
}
na=null;
};
};
if(dojo.render.html.ie){
dojo.addOnUnload(function(){
dojo._ie_clobber.clobber();
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
dojo._ie_clobber.clobberNodes=[];
});
}
dojo.event.browser=new function(){
var _3c6=0;
this.clean=function(node){
if(dojo.render.html.ie){
dojo._ie_clobber.clobber(node);
}
};
this.addClobberNode=function(node){
if(!dojo.render.html.ie){
return;
}
if(!node["__doClobber__"]){
node.__doClobber__=true;
dojo._ie_clobber.clobberNodes.push(node);
node.__clobberAttrs__=[];
}
};
this.addClobberNodeAttrs=function(node,_3ca){
if(!dojo.render.html.ie){
return;
}
this.addClobberNode(node);
for(var x=0;x<_3ca.length;x++){
node.__clobberAttrs__.push(_3ca[x]);
}
};
this.removeListener=function(node,_3cd,fp,_3cf){
if(!_3cf){
var _3cf=false;
}
_3cd=_3cd.toLowerCase();
if((_3cd=="onkey")||(_3cd=="key")){
if(dojo.render.html.ie){
this.removeListener(node,"onkeydown",fp,_3cf);
}
_3cd="onkeypress";
}
if(_3cd.substr(0,2)=="on"){
_3cd=_3cd.substr(2);
}
if(node.removeEventListener){
node.removeEventListener(_3cd,fp,_3cf);
}
};
this.addListener=function(node,_3d1,fp,_3d3,_3d4){
if(!node){
return;
}
if(!_3d3){
var _3d3=false;
}
_3d1=_3d1.toLowerCase();
if((_3d1=="onkey")||(_3d1=="key")){
if(dojo.render.html.ie){
this.addListener(node,"onkeydown",fp,_3d3,_3d4);
}
_3d1="onkeypress";
}
if(_3d1.substr(0,2)!="on"){
_3d1="on"+_3d1;
}
if(!_3d4){
var _3d5=function(evt){
if(!evt){
evt=window.event;
}
var ret=fp(dojo.event.browser.fixEvent(evt,this));
if(_3d3){
dojo.event.browser.stopEvent(evt);
}
return ret;
};
}else{
_3d5=fp;
}
if(node.addEventListener){
node.addEventListener(_3d1.substr(2),_3d5,_3d3);
return _3d5;
}else{
if(typeof node[_3d1]=="function"){
var _3d8=node[_3d1];
node[_3d1]=function(e){
_3d8(e);
return _3d5(e);
};
}else{
node[_3d1]=_3d5;
}
if(dojo.render.html.ie){
this.addClobberNodeAttrs(node,[_3d1]);
}
return _3d5;
}
};
this.isEvent=function(obj){
return (typeof obj!="undefined")&&(typeof Event!="undefined")&&(obj.eventPhase);
};
this.currentEvent=null;
this.callListener=function(_3db,_3dc){
if(typeof _3db!="function"){
dojo.raise("listener not a function: "+_3db);
}
dojo.event.browser.currentEvent.currentTarget=_3dc;
return _3db.call(_3dc,dojo.event.browser.currentEvent);
};
this.stopPropagation=function(){
dojo.event.browser.currentEvent.cancelBubble=true;
};
this.preventDefault=function(){
dojo.event.browser.currentEvent.returnValue=false;
};
this.keys={KEY_BACKSPACE:8,KEY_TAB:9,KEY_CLEAR:12,KEY_ENTER:13,KEY_SHIFT:16,KEY_CTRL:17,KEY_ALT:18,KEY_PAUSE:19,KEY_CAPS_LOCK:20,KEY_ESCAPE:27,KEY_SPACE:32,KEY_PAGE_UP:33,KEY_PAGE_DOWN:34,KEY_END:35,KEY_HOME:36,KEY_LEFT_ARROW:37,KEY_UP_ARROW:38,KEY_RIGHT_ARROW:39,KEY_DOWN_ARROW:40,KEY_INSERT:45,KEY_DELETE:46,KEY_HELP:47,KEY_LEFT_WINDOW:91,KEY_RIGHT_WINDOW:92,KEY_SELECT:93,KEY_NUMPAD_0:96,KEY_NUMPAD_1:97,KEY_NUMPAD_2:98,KEY_NUMPAD_3:99,KEY_NUMPAD_4:100,KEY_NUMPAD_5:101,KEY_NUMPAD_6:102,KEY_NUMPAD_7:103,KEY_NUMPAD_8:104,KEY_NUMPAD_9:105,KEY_NUMPAD_MULTIPLY:106,KEY_NUMPAD_PLUS:107,KEY_NUMPAD_ENTER:108,KEY_NUMPAD_MINUS:109,KEY_NUMPAD_PERIOD:110,KEY_NUMPAD_DIVIDE:111,KEY_F1:112,KEY_F2:113,KEY_F3:114,KEY_F4:115,KEY_F5:116,KEY_F6:117,KEY_F7:118,KEY_F8:119,KEY_F9:120,KEY_F10:121,KEY_F11:122,KEY_F12:123,KEY_F13:124,KEY_F14:125,KEY_F15:126,KEY_NUM_LOCK:144,KEY_SCROLL_LOCK:145};
this.revKeys=[];
for(var key in this.keys){
this.revKeys[this.keys[key]]=key;
}
this.fixEvent=function(evt,_3df){
if(!evt){
if(window["event"]){
evt=window.event;
}
}
if((evt["type"])&&(evt["type"].indexOf("key")==0)){
evt.keys=this.revKeys;
for(var key in this.keys){
evt[key]=this.keys[key];
}
if(evt["type"]=="keydown"&&dojo.render.html.ie){
switch(evt.keyCode){
case evt.KEY_SHIFT:
case evt.KEY_CTRL:
case evt.KEY_ALT:
case evt.KEY_CAPS_LOCK:
case evt.KEY_LEFT_WINDOW:
case evt.KEY_RIGHT_WINDOW:
case evt.KEY_SELECT:
case evt.KEY_NUM_LOCK:
case evt.KEY_SCROLL_LOCK:
case evt.KEY_NUMPAD_0:
case evt.KEY_NUMPAD_1:
case evt.KEY_NUMPAD_2:
case evt.KEY_NUMPAD_3:
case evt.KEY_NUMPAD_4:
case evt.KEY_NUMPAD_5:
case evt.KEY_NUMPAD_6:
case evt.KEY_NUMPAD_7:
case evt.KEY_NUMPAD_8:
case evt.KEY_NUMPAD_9:
case evt.KEY_NUMPAD_PERIOD:
break;
case evt.KEY_NUMPAD_MULTIPLY:
case evt.KEY_NUMPAD_PLUS:
case evt.KEY_NUMPAD_ENTER:
case evt.KEY_NUMPAD_MINUS:
case evt.KEY_NUMPAD_DIVIDE:
break;
case evt.KEY_PAUSE:
case evt.KEY_TAB:
case evt.KEY_BACKSPACE:
case evt.KEY_ENTER:
case evt.KEY_ESCAPE:
case evt.KEY_PAGE_UP:
case evt.KEY_PAGE_DOWN:
case evt.KEY_END:
case evt.KEY_HOME:
case evt.KEY_LEFT_ARROW:
case evt.KEY_UP_ARROW:
case evt.KEY_RIGHT_ARROW:
case evt.KEY_DOWN_ARROW:
case evt.KEY_INSERT:
case evt.KEY_DELETE:
case evt.KEY_F1:
case evt.KEY_F2:
case evt.KEY_F3:
case evt.KEY_F4:
case evt.KEY_F5:
case evt.KEY_F6:
case evt.KEY_F7:
case evt.KEY_F8:
case evt.KEY_F9:
case evt.KEY_F10:
case evt.KEY_F11:
case evt.KEY_F12:
case evt.KEY_F12:
case evt.KEY_F13:
case evt.KEY_F14:
case evt.KEY_F15:
case evt.KEY_CLEAR:
case evt.KEY_HELP:
evt.key=evt.keyCode;
break;
default:
if(evt.ctrlKey||evt.altKey){
var _3e1=evt.keyCode;
if(_3e1>=65&&_3e1<=90&&evt.shiftKey==false){
_3e1+=32;
}
if(_3e1>=1&&_3e1<=26&&evt.ctrlKey){
_3e1+=96;
}
evt.key=String.fromCharCode(_3e1);
}
}
}else{
if(evt["type"]=="keypress"){
if(dojo.render.html.opera){
if(evt.which==0){
evt.key=evt.keyCode;
}else{
if(evt.which>0){
switch(evt.which){
case evt.KEY_SHIFT:
case evt.KEY_CTRL:
case evt.KEY_ALT:
case evt.KEY_CAPS_LOCK:
case evt.KEY_NUM_LOCK:
case evt.KEY_SCROLL_LOCK:
break;
case evt.KEY_PAUSE:
case evt.KEY_TAB:
case evt.KEY_BACKSPACE:
case evt.KEY_ENTER:
case evt.KEY_ESCAPE:
evt.key=evt.which;
break;
default:
var _3e1=evt.which;
if((evt.ctrlKey||evt.altKey||evt.metaKey)&&(evt.which>=65&&evt.which<=90&&evt.shiftKey==false)){
_3e1+=32;
}
evt.key=String.fromCharCode(_3e1);
}
}
}
}else{
if(dojo.render.html.ie){
if(!evt.ctrlKey&&!evt.altKey&&evt.keyCode>=evt.KEY_SPACE){
evt.key=String.fromCharCode(evt.keyCode);
}
}else{
if(dojo.render.html.safari){
switch(evt.keyCode){
case 63232:
evt.key=evt.KEY_UP_ARROW;
break;
case 63233:
evt.key=evt.KEY_DOWN_ARROW;
break;
case 63234:
evt.key=evt.KEY_LEFT_ARROW;
break;
case 63235:
evt.key=evt.KEY_RIGHT_ARROW;
break;
default:
evt.key=evt.charCode>0?String.fromCharCode(evt.charCode):evt.keyCode;
}
}else{
evt.key=evt.charCode>0?String.fromCharCode(evt.charCode):evt.keyCode;
}
}
}
}
}
}
if(dojo.render.html.ie){
if(!evt.target){
evt.target=evt.srcElement;
}
if(!evt.currentTarget){
evt.currentTarget=(_3df?_3df:evt.srcElement);
}
if(!evt.layerX){
evt.layerX=evt.offsetX;
}
if(!evt.layerY){
evt.layerY=evt.offsetY;
}
var doc=(evt.srcElement&&evt.srcElement.ownerDocument)?evt.srcElement.ownerDocument:document;
var _3e3=((dojo.render.html.ie55)||(doc["compatMode"]=="BackCompat"))?doc.body:doc.documentElement;
if(!evt.pageX){
evt.pageX=evt.clientX+(_3e3.scrollLeft||0);
}
if(!evt.pageY){
evt.pageY=evt.clientY+(_3e3.scrollTop||0);
}
if(evt.type=="mouseover"){
evt.relatedTarget=evt.fromElement;
}
if(evt.type=="mouseout"){
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
dojo.provide("dojo.event.*");
dojo.provide("dojo.widget.Manager");
dojo.widget.manager=new function(){
this.widgets=[];
this.widgetIds=[];
this.topWidgets={};
var _3e5={};
var _3e6=[];
this.getUniqueId=function(_3e7){
return _3e7+"_"+(_3e5[_3e7]!=undefined?++_3e5[_3e7]:_3e5[_3e7]=0);
};
this.add=function(_3e8){
this.widgets.push(_3e8);
if(!_3e8.extraArgs["id"]){
_3e8.extraArgs["id"]=_3e8.extraArgs["ID"];
}
if(_3e8.widgetId==""){
if(_3e8["id"]){
_3e8.widgetId=_3e8["id"];
}else{
if(_3e8.extraArgs["id"]){
_3e8.widgetId=_3e8.extraArgs["id"];
}else{
_3e8.widgetId=this.getUniqueId(_3e8.widgetType);
}
}
}
if(this.widgetIds[_3e8.widgetId]){
dojo.debug("widget ID collision on ID: "+_3e8.widgetId);
}
this.widgetIds[_3e8.widgetId]=_3e8;
};
this.destroyAll=function(){
for(var x=this.widgets.length-1;x>=0;x--){
try{
this.widgets[x].destroy(true);
delete this.widgets[x];
}
catch(e){
}
}
};
this.remove=function(_3ea){
if(dojo.lang.isNumber(_3ea)){
var tw=this.widgets[_3ea].widgetId;
delete this.widgetIds[tw];
this.widgets.splice(_3ea,1);
}else{
this.removeById(_3ea);
}
};
this.removeById=function(id){
if(!dojo.lang.isString(id)){
id=id["widgetId"];
if(!id){
dojo.debug("invalid widget or id passed to removeById");
return;
}
}
for(var i=0;i<this.widgets.length;i++){
if(this.widgets[i].widgetId==id){
this.remove(i);
break;
}
}
};
this.getWidgetById=function(id){
if(dojo.lang.isString(id)){
return this.widgetIds[id];
}
return id;
};
this.getWidgetsByType=function(type){
var lt=type.toLowerCase();
var _3f1=(type.indexOf(":")<0?function(x){
return x.widgetType.toLowerCase();
}:function(x){
return x.getNamespacedType();
});
var ret=[];
dojo.lang.forEach(this.widgets,function(x){
if(_3f1(x)==lt){
ret.push(x);
}
});
return ret;
};
this.getWidgetsByFilter=function(_3f6,_3f7){
var ret=[];
dojo.lang.every(this.widgets,function(x){
if(_3f6(x)){
ret.push(x);
if(_3f7){
return false;
}
}
return true;
});
return (_3f7?ret[0]:ret);
};
this.getAllWidgets=function(){
return this.widgets.concat();
};
this.getWidgetByNode=function(node){
var w=this.getAllWidgets();
node=dojo.byId(node);
for(var i=0;i<w.length;i++){
if(w[i].domNode==node){
return w[i];
}
}
return null;
};
this.byId=this.getWidgetById;
this.byType=this.getWidgetsByType;
this.byFilter=this.getWidgetsByFilter;
this.byNode=this.getWidgetByNode;
var _3fd={};
var _3fe=["dojo.widget"];
for(var i=0;i<_3fe.length;i++){
_3fe[_3fe[i]]=true;
}
this.registerWidgetPackage=function(_400){
if(!_3fe[_400]){
_3fe[_400]=true;
_3fe.push(_400);
}
};
this.getWidgetPackageList=function(){
return dojo.lang.map(_3fe,function(elt){
return (elt!==true?elt:undefined);
});
};
this.getImplementation=function(_402,_403,_404,ns){
var impl=this.getImplementationName(_402,ns);
if(impl){
var ret=_403?new impl(_403):new impl();
return ret;
}
};
function buildPrefixCache(){
for(var _408 in dojo.render){
if(dojo.render[_408]["capable"]===true){
var _409=dojo.render[_408].prefixes;
for(var i=0;i<_409.length;i++){
_3e6.push(_409[i].toLowerCase());
}
}
}
}
var _40b=function(_40c,_40d){
if(!_40d){
return null;
}
for(var i=0,l=_3e6.length,_410;i<=l;i++){
_410=(i<l?_40d[_3e6[i]]:_40d);
if(!_410){
continue;
}
for(var name in _410){
if(name.toLowerCase()==_40c){
return _410[name];
}
}
}
return null;
};
var _412=function(_413,_414){
var _415=dojo.evalObjPath(_414,false);
return (_415?_40b(_413,_415):null);
};
this.getImplementationName=function(_416,ns){
var _418=_416.toLowerCase();
ns=ns||"dojo";
var imps=_3fd[ns]||(_3fd[ns]={});
var impl=imps[_418];
if(impl){
return impl;
}
if(!_3e6.length){
buildPrefixCache();
}
var _41b=dojo["namespace"].get(ns);
if(!_41b){
dojo.namespace.register(ns,ns+".widget");
_41b=dojo["namespace"].get(ns);
}
if(_41b){
_41b.resolve(_416);
}
impl=_412(_418,_41b.getModule(_416));
if(impl){
return (imps[_418]=impl);
}
_41b=dojo["namespace"].require(ns);
if((_41b)&&(_41b.resolver)){
_41b.resolve(_416);
impl=_412(_418,_41b.getModule(_416));
if(impl){
return (imps[_418]=impl);
}
}
dojo.deprecated("dojo.widget.Manager.getImplementationName","Could not locate widget implementation for \""+_416+"\" in \""+_41b.module+"\" registered to namespace \""+_41b.name+"\". "+"Developers must specify correct namespaces for all non-Dojo widgets","0.5");
for(var i=0;i<_3fe.length;i++){
impl=_412(_418,_3fe[i]);
if(impl){
return (imps[_418]=impl);
}
}
throw new Error("Could not locate widget implementation for \""+_416+"\" in \""+_41b.module+"\" registered to namespace \""+_41b.name+"\"");
};
this.resizing=false;
this.onWindowResized=function(){
if(this.resizing){
return;
}
try{
this.resizing=true;
for(var id in this.topWidgets){
var _41e=this.topWidgets[id];
if(_41e.checkSize){
_41e.checkSize();
}
}
}
catch(e){
}
finally{
this.resizing=false;
}
};
if(typeof window!="undefined"){
dojo.addOnLoad(this,"onWindowResized");
dojo.event.connect(window,"onresize",this,"onWindowResized");
}
};
(function(){
var dw=dojo.widget;
var dwm=dw.manager;
var h=dojo.lang.curry(dojo.lang,"hitch",dwm);
var g=function(_423,_424){
dw[(_424||_423)]=h(_423);
};
g("add","addWidget");
g("destroyAll","destroyAllWidgets");
g("remove","removeWidget");
g("removeById","removeWidgetById");
g("getWidgetById");
g("getWidgetById","byId");
g("getWidgetsByType");
g("getWidgetsByFilter");
g("getWidgetsByType","byType");
g("getWidgetsByFilter","byFilter");
g("getWidgetByNode","byNode");
dw.all=function(n){
var _426=dwm.getAllWidgets.apply(dwm,arguments);
if(arguments.length>0){
return _426[n];
}
return _426;
};
g("registerWidgetPackage");
g("getImplementation","getWidgetImplementation");
g("getImplementationName","getWidgetImplementationName");
dw.widgets=dwm.widgets;
dw.widgetIds=dwm.widgetIds;
dw.root=dwm.root;
})();
dojo.provide("dojo.uri.Uri");
dojo.uri=new function(){
this.dojoUri=function(uri){
return new dojo.uri.Uri(dojo.hostenv.getBaseScriptUri(),uri);
};
this.moduleUri=function(_428,uri){
var loc=dojo.hostenv.getModulePrefix(_428);
if(!loc){
return null;
}
if(loc.lastIndexOf("/")!=loc.length-1){
loc+="/";
}
return new dojo.uri.Uri(dojo.hostenv.getBaseScriptUri()+loc,uri);
};
this.Uri=function(){
var uri=arguments[0];
for(var i=1;i<arguments.length;i++){
if(!arguments[i]){
continue;
}
var _42d=new dojo.uri.Uri(arguments[i].toString());
var _42e=new dojo.uri.Uri(uri.toString());
if(_42d.path==""&&_42d.scheme==null&&_42d.authority==null&&_42d.query==null){
if(_42d.fragment!=null){
_42e.fragment=_42d.fragment;
}
_42d=_42e;
}else{
if(_42d.scheme==null){
_42d.scheme=_42e.scheme;
if(_42d.authority==null){
_42d.authority=_42e.authority;
if(_42d.path.charAt(0)!="/"){
var path=_42e.path.substring(0,_42e.path.lastIndexOf("/")+1)+_42d.path;
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
_42d.path=segs.join("/");
}
}
}
}
uri="";
if(_42d.scheme!=null){
uri+=_42d.scheme+":";
}
if(_42d.authority!=null){
uri+="//"+_42d.authority;
}
uri+=_42d.path;
if(_42d.query!=null){
uri+="?"+_42d.query;
}
if(_42d.fragment!=null){
uri+="#"+_42d.fragment;
}
}
this.uri=uri.toString();
var _432="^(([^:/?#]+):)?(//([^/?#]*))?([^?#]*)(\\?([^#]*))?(#(.*))?$";
var r=this.uri.match(new RegExp(_432));
this.scheme=r[2]||(r[1]?"":null);
this.authority=r[4]||(r[3]?"":null);
this.path=r[5];
this.query=r[7]||(r[6]?"":null);
this.fragment=r[9]||(r[8]?"":null);
if(this.authority!=null){
_432="^((([^:]+:)?([^@]+))@)?([^:]*)(:([0-9]+))?$";
r=this.authority.match(new RegExp(_432));
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
dojo.provide("dojo.uri.*");
dojo.provide("dojo.html.common");
dojo.lang.mixin(dojo.html,dojo.dom);
dojo.html.body=function(){
dojo.deprecated("dojo.html.body() moved to dojo.body()","0.5");
return dojo.body();
};
dojo.html.getEventTarget=function(evt){
if(!evt){
evt=dojo.global().event||{};
}
var t=(evt.srcElement?evt.srcElement:(evt.target?evt.target:null));
while((t)&&(t.nodeType!=1)){
t=t.parentNode;
}
return t;
};
dojo.html.getViewport=function(){
var _436=dojo.global();
var _437=dojo.doc();
var w=0;
var h=0;
if(dojo.render.html.mozilla){
w=_437.documentElement.clientWidth;
h=_436.innerHeight;
}else{
if(!dojo.render.html.opera&&_436.innerWidth){
w=_436.innerWidth;
h=_436.innerHeight;
}else{
if(!dojo.render.html.opera&&dojo.exists(_437,"documentElement.clientWidth")){
var w2=_437.documentElement.clientWidth;
if(!w||w2&&w2<w){
w=w2;
}
h=_437.documentElement.clientHeight;
}else{
if(dojo.body().clientWidth){
w=dojo.body().clientWidth;
h=dojo.body().clientHeight;
}
}
}
}
return {width:w,height:h};
};
dojo.html.getScroll=function(){
var _43b=dojo.global();
var _43c=dojo.doc();
var top=_43b.pageYOffset||_43c.documentElement.scrollTop||dojo.body().scrollTop||0;
var left=_43b.pageXOffset||_43c.documentElement.scrollLeft||dojo.body().scrollLeft||0;
return {top:top,left:left,offset:{x:left,y:top}};
};
dojo.html.getParentByType=function(node,type){
var _441=dojo.doc();
var _442=dojo.byId(node);
type=type.toLowerCase();
while((_442)&&(_442.nodeName.toLowerCase()!=type)){
if(_442==(_441["body"]||_441["documentElement"])){
return null;
}
_442=_442.parentNode;
}
return _442;
};
dojo.html.getAttribute=function(node,attr){
node=dojo.byId(node);
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
return dojo.html.getAttribute(dojo.byId(node),attr)?true:false;
};
dojo.html.getCursorPosition=function(e){
e=e||dojo.global().event;
var _44a={x:0,y:0};
if(e.pageX||e.pageY){
_44a.x=e.pageX;
_44a.y=e.pageY;
}else{
var de=dojo.doc().documentElement;
var db=dojo.body();
_44a.x=e.clientX+((de||db)["scrollLeft"])-((de||db)["clientLeft"]);
_44a.y=e.clientY+((de||db)["scrollTop"])-((de||db)["clientTop"]);
}
return _44a;
};
dojo.html.isTag=function(node){
node=dojo.byId(node);
if(node&&node.tagName){
for(var i=1;i<arguments.length;i++){
if(node.tagName.toLowerCase()==String(arguments[i]).toLowerCase()){
return String(arguments[i]).toLowerCase();
}
}
}
return "";
};
if(dojo.render.html.ie){
if(window.location.href.substr(0,6).toLowerCase()!="https:"){
(function(){
var _44f=dojo.doc().createElement("script");
_44f.src="javascript:'dojo.html.createExternalElement=function(doc, tag){ return doc.createElement(tag); }'";
dojo.doc().getElementsByTagName("head")[0].appendChild(_44f);
})();
}
}else{
dojo.html.createExternalElement=function(doc,tag){
return doc.createElement(tag);
};
}
dojo.html._callDeprecated=function(_452,_453,args,_455,_456){
dojo.deprecated("dojo.html."+_452,"replaced by dojo.html."+_453+"("+(_455?"node, {"+_455+": "+_455+"}":"")+")"+(_456?"."+_456:""),"0.5");
var _457=[];
if(_455){
var _458={};
_458[_455]=args[1];
_457.push(args[0]);
_457.push(_458);
}else{
_457=args;
}
var ret=dojo.html[_453].apply(dojo.html,args);
if(_456){
return ret[_456];
}else{
return ret;
}
};
dojo.html.getViewportWidth=function(){
return dojo.html._callDeprecated("getViewportWidth","getViewport",arguments,null,"width");
};
dojo.html.getViewportHeight=function(){
return dojo.html._callDeprecated("getViewportHeight","getViewport",arguments,null,"height");
};
dojo.html.getViewportSize=function(){
return dojo.html._callDeprecated("getViewportSize","getViewport",arguments);
};
dojo.html.getScrollTop=function(){
return dojo.html._callDeprecated("getScrollTop","getScroll",arguments,null,"top");
};
dojo.html.getScrollLeft=function(){
return dojo.html._callDeprecated("getScrollLeft","getScroll",arguments,null,"left");
};
dojo.html.getScrollOffset=function(){
return dojo.html._callDeprecated("getScrollOffset","getScroll",arguments,null,"offset");
};
dojo.provide("dojo.a11y");
dojo.a11y={imgPath:dojo.uri.dojoUri("src/widget/templates/images"),doAccessibleCheck:true,accessible:null,checkAccessible:function(){
if(this.accessible===null){
this.accessible=false;
if(this.doAccessibleCheck==true){
this.accessible=this.testAccessible();
}
}
return this.accessible;
},testAccessible:function(){
this.accessible=false;
if(dojo.render.html.ie||dojo.render.html.mozilla){
var div=document.createElement("div");
div.style.backgroundImage="url(\""+this.imgPath+"/tab_close.gif\")";
dojo.body().appendChild(div);
var _45b=null;
if(window.getComputedStyle){
var _45c=getComputedStyle(div,"");
_45b=_45c.getPropertyValue("background-image");
}else{
_45b=div.currentStyle.backgroundImage;
}
var _45d=false;
if(_45b!=null&&(_45b=="none"||_45b=="url(invalid-url:)")){
this.accessible=true;
}
dojo.body().removeChild(div);
}
return this.accessible;
},setCheckAccessible:function(_45e){
this.doAccessibleCheck=_45e;
},setAccessibleMode:function(){
if(this.accessible===null){
if(this.checkAccessible()){
dojo.render.html.prefixes.unshift("a11y");
}
}
return this.accessible;
}};
dojo.provide("dojo.widget.Widget");
dojo.declare("dojo.widget.Widget",null,function(){
this.children=[];
this.extraArgs={};
},{parent:null,children:[],extraArgs:{},isTopLevel:false,isModal:false,isEnabled:true,isHidden:false,isContainer:false,widgetId:"",widgetType:"Widget","namespace":"dojo",getNamespacedType:function(){
return (this.namespace?this.namespace+":"+this.widgetType:this.widgetType).toLowerCase();
return (this["namespace"]?this["namespace"]+":"+this.widgetType:this.widgetType).toLowerCase();
},toString:function(){
return "[Widget "+this.getNamespacedType()+", "+(this.widgetId||"NO ID")+"]";
},repr:function(){
return this.toString();
},enable:function(){
this.isEnabled=true;
},disable:function(){
this.isEnabled=false;
},hide:function(){
this.isHidden=true;
},show:function(){
this.isHidden=false;
},onResized:function(){
this.notifyChildrenOfResize();
},notifyChildrenOfResize:function(){
for(var i=0;i<this.children.length;i++){
var _460=this.children[i];
if(_460.onResized){
_460.onResized();
}
}
},create:function(args,_462,_463,ns){
if(ns){
this["namespace"]=ns;
}
this.satisfyPropertySets(args,_462,_463);
this.mixInProperties(args,_462,_463);
this.postMixInProperties(args,_462,_463);
dojo.widget.manager.add(this);
this.buildRendering(args,_462,_463);
this.initialize(args,_462,_463);
this.postInitialize(args,_462,_463);
this.postCreate(args,_462,_463);
return this;
},destroy:function(_465){
this.destroyChildren();
this.uninitialize();
this.destroyRendering(_465);
dojo.widget.manager.removeById(this.widgetId);
},destroyChildren:function(){
var _466;
var i=0;
while(this.children.length>i){
_466=this.children[i];
if(_466 instanceof dojo.widget.Widget){
this.removeChild(_466);
_466.destroy();
continue;
}
i++;
}
},getChildrenOfType:function(type,_469){
var ret=[];
var _46b=dojo.lang.isFunction(type);
if(!_46b){
type=type.toLowerCase();
}
for(var x=0;x<this.children.length;x++){
if(_46b){
if(this.children[x] instanceof type){
ret.push(this.children[x]);
}
}else{
if(this.children[x].widgetType.toLowerCase()==type){
ret.push(this.children[x]);
}
}
if(_469){
ret=ret.concat(this.children[x].getChildrenOfType(type,_469));
}
}
return ret;
},getDescendants:function(){
var _46d=[];
var _46e=[this];
var elem;
while((elem=_46e.pop())){
_46d.push(elem);
if(elem.children){
dojo.lang.forEach(elem.children,function(elem){
_46e.push(elem);
});
}
}
return _46d;
},isFirstChild:function(){
return this===this.parent.children[0];
},isLastChild:function(){
return this===this.parent.children[this.parent.children.length-1];
},satisfyPropertySets:function(args){
return args;
},mixInProperties:function(args,frag){
if((args["fastMixIn"])||(frag["fastMixIn"])){
for(var x in args){
this[x]=args[x];
}
return;
}
var _475;
var _476=dojo.widget.lcArgsCache[this.widgetType];
if(_476==null){
_476={};
for(var y in this){
_476[((new String(y)).toLowerCase())]=y;
}
dojo.widget.lcArgsCache[this.widgetType]=_476;
}
var _478={};
for(var x in args){
if(!this[x]){
var y=_476[(new String(x)).toLowerCase()];
if(y){
args[y]=args[x];
x=y;
}
}
if(_478[x]){
continue;
}
_478[x]=true;
if((typeof this[x])!=(typeof _475)){
if(typeof args[x]!="string"){
this[x]=args[x];
}else{
if(dojo.lang.isString(this[x])){
this[x]=args[x];
}else{
if(dojo.lang.isNumber(this[x])){
this[x]=new Number(args[x]);
}else{
if(dojo.lang.isBoolean(this[x])){
this[x]=(args[x].toLowerCase()=="false")?false:true;
}else{
if(dojo.lang.isFunction(this[x])){
if(args[x].search(/[^\w\.]+/i)==-1){
this[x]=dojo.evalObjPath(args[x],false);
}else{
var tn=dojo.lang.nameAnonFunc(new Function(args[x]),this);
dojo.event.kwConnect({srcObj:this,srcFunc:x,adviceObj:this,adviceFunc:tn});
}
}else{
if(dojo.lang.isArray(this[x])){
this[x]=args[x].split(";");
}else{
if(this[x] instanceof Date){
this[x]=new Date(Number(args[x]));
}else{
if(typeof this[x]=="object"){
if(this[x] instanceof dojo.uri.Uri){
this[x]=args[x];
}else{
var _47a=args[x].split(";");
for(var y=0;y<_47a.length;y++){
var si=_47a[y].indexOf(":");
if((si!=-1)&&(_47a[y].length>si)){
this[x][_47a[y].substr(0,si).replace(/^\s+|\s+$/g,"")]=_47a[y].substr(si+1);
}
}
}
}else{
this[x]=args[x];
}
}
}
}
}
}
}
}
}else{
this.extraArgs[x.toLowerCase()]=args[x];
}
}
},postMixInProperties:function(args,frag,_47e){
},initialize:function(args,frag,_481){
return false;
},postInitialize:function(args,frag,_484){
return false;
},postCreate:function(args,frag,_487){
return false;
},uninitialize:function(){
return false;
},buildRendering:function(args,frag,_48a){
dojo.unimplemented("dojo.widget.Widget.buildRendering, on "+this.toString()+", ");
return false;
},destroyRendering:function(){
dojo.unimplemented("dojo.widget.Widget.destroyRendering");
return false;
},cleanUp:function(){
dojo.unimplemented("dojo.widget.Widget.cleanUp");
return false;
},addedTo:function(_48b){
},addChild:function(_48c){
dojo.unimplemented("dojo.widget.Widget.addChild");
return false;
},removeChild:function(_48d){
for(var x=0;x<this.children.length;x++){
if(this.children[x]===_48d){
this.children.splice(x,1);
break;
}
}
return _48d;
},resize:function(_48f,_490){
this.setWidth(_48f);
this.setHeight(_490);
},setWidth:function(_491){
if((typeof _491=="string")&&(_491.substr(-1)=="%")){
this.setPercentageWidth(_491);
}else{
this.setNativeWidth(_491);
}
},setHeight:function(_492){
if((typeof _492=="string")&&(_492.substr(-1)=="%")){
this.setPercentageHeight(_492);
}else{
this.setNativeHeight(_492);
}
},setPercentageHeight:function(_493){
return false;
},setNativeHeight:function(_494){
return false;
},setPercentageWidth:function(_495){
return false;
},setNativeWidth:function(_496){
return false;
},getPreviousSibling:function(){
var idx=this.getParentIndex();
if(idx<=0){
return null;
}
return this.parent.children[idx-1];
},getSiblings:function(){
return this.parent.children;
},getParentIndex:function(){
return dojo.lang.indexOf(this.parent.children,this,true);
},getNextSibling:function(){
var idx=this.getParentIndex();
if(idx==this.parent.children.length-1){
return null;
}
if(idx<0){
return null;
}
return this.parent.children[idx+1];
}});
dojo.widget.lcArgsCache={};
dojo.widget.tags={};
dojo.widget.tags.addParseTreeHandler=function(type){
dojo.deprecated("addParseTreeHandler",". ParseTreeHandlers are now reserved for components. Any unfiltered DojoML tag without a ParseTreeHandler is assumed to be a widget","0.5");
};
dojo.widget.tags["dojo:propertyset"]=function(_49a,_49b,_49c){
var _49d=_49b.parseProperties(_49a["dojo:propertyset"]);
};
dojo.widget.tags["dojo:connect"]=function(_49e,_49f,_4a0){
var _4a1=_49f.parseProperties(_49e["dojo:connect"]);
};
dojo.widget.buildWidgetFromParseTree=function(type,frag,_4a4,_4a5,_4a6,_4a7){
dojo.a11y.setAccessibleMode();
var _4a8=type.split(":");
_4a8=(_4a8.length==2)?_4a8[1]:type;
var _4a9=_4a7||_4a4.parseProperties(frag[frag["namespace"]+":"+_4a8]);
var _4aa=dojo.widget.manager.getImplementation(_4a8,null,null,frag["namespace"]);
if(!_4aa){
throw new Error("cannot find \""+type+"\" widget");
}else{
if(!_4aa.create){
throw new Error("\""+type+"\" widget object has no \"create\" method and does not appear to implement *Widget");
}
}
_4a9["dojoinsertionindex"]=_4a6;
var ret=_4aa.create(_4a9,frag,_4a5,frag["namespace"]);
return ret;
};
dojo.widget.defineWidget=function(_4ac,_4ad,_4ae,init,_4b0){
if(dojo.lang.isString(arguments[3])){
dojo.widget._defineWidget(arguments[0],arguments[3],arguments[1],arguments[4],arguments[2]);
}else{
var args=[arguments[0]],p=3;
if(dojo.lang.isString(arguments[1])){
args.push(arguments[1],arguments[2]);
}else{
args.push("",arguments[1]);
p=2;
}
if(dojo.lang.isFunction(arguments[p])){
args.push(arguments[p],arguments[p+1]);
}else{
args.push(null,arguments[p]);
}
dojo.widget._defineWidget.apply(this,args);
}
};
dojo.widget.defineWidget.renderers="html|svg|vml";
dojo.widget._defineWidget=function(_4b3,_4b4,_4b5,init,_4b7){
var _4b8=_4b3.split(".");
var type=_4b8.pop();
var regx="\\.("+(_4b4?_4b4+"|":"")+dojo.widget.defineWidget.renderers+")\\.";
var r=_4b3.search(new RegExp(regx));
_4b8=(r<0?_4b8.join("."):_4b3.substr(0,r));
dojo.widget.manager.registerWidgetPackage(_4b8);
var pos=_4b8.indexOf(".");
var _4bd=(pos>-1)?_4b8.substring(0,pos):_4b8;
_4b7=(_4b7)||{};
_4b7.widgetType=type;
if((!init)&&(_4b7["classConstructor"])){
init=_4b7.classConstructor;
delete _4b7.classConstructor;
}
dojo.declare(_4b3,_4b5,init,_4b7);
};
dojo.provide("dojo.widget.Parse");
dojo.widget.Parse=function(_4be){
this.propertySetsList=[];
this.fragment=_4be;
this.createComponents=function(frag,_4c0){
var _4c1=[];
var _4c2=false;
try{
if((frag)&&(frag["tagName"])&&(frag!=frag["nodeRef"])){
var _4c3=dojo.widget.tags;
var tna=String(frag["tagName"]).split(";");
for(var x=0;x<tna.length;x++){
var ltn=(tna[x].replace(/^\s+|\s+$/g,"")).toLowerCase();
frag.tagName=ltn;
if(_4c3[ltn]){
_4c2=true;
var ret=_4c3[ltn](frag,this,_4c0,frag["index"]);
_4c1.push(ret);
}else{
if(ltn.indexOf(":")==-1){
ltn="dojo:"+ltn;
}
var ret=dojo.widget.buildWidgetFromParseTree(ltn,frag,this,_4c0,frag["index"]);
if(ret){
_4c2=true;
_4c1.push(ret);
}
}
}
}
}
catch(e){
dojo.debug("dojo.widget.Parse: error:"+e);
}
if(!_4c2){
_4c1=_4c1.concat(this.createSubComponents(frag,_4c0));
}
return _4c1;
};
this.createSubComponents=function(_4c8,_4c9){
var frag,_4cb=[];
for(var item in _4c8){
frag=_4c8[item];
if((frag)&&(typeof frag=="object")&&(frag!=_4c8.nodeRef)&&(frag!=_4c8["tagName"])){
_4cb=_4cb.concat(this.createComponents(frag,_4c9));
}
}
return _4cb;
};
this.parsePropertySets=function(_4cd){
return [];
};
this.parseProperties=function(_4ce){
var _4cf={};
for(var item in _4ce){
if((_4ce[item]==_4ce["tagName"])||(_4ce[item]==_4ce.nodeRef)){
}else{
if((_4ce[item]["tagName"])&&(dojo.widget.tags[_4ce[item].tagName.toLowerCase()])){
}else{
if((_4ce[item][0])&&(_4ce[item][0].value!="")&&(_4ce[item][0].value!=null)){
try{
if(item.toLowerCase()=="dataprovider"){
var _4d1=this;
this.getDataProvider(_4d1,_4ce[item][0].value);
_4cf.dataProvider=this.dataProvider;
}
_4cf[item]=_4ce[item][0].value;
var _4d2=this.parseProperties(_4ce[item]);
for(var _4d3 in _4d2){
_4cf[_4d3]=_4d2[_4d3];
}
}
catch(e){
dojo.debug(e);
}
}
}
switch(item.toLowerCase()){
case "checked":
if(!_4cf[item]||typeof _4cf[item]=="string"){
_4cf[item]=Boolean(!_4cf[item]||_4cf[item].toLowerCase()=="checked");
}
break;
case "disabled":
if(!_4cf[item]||typeof _4cf[item]=="string"){
_4cf[item]=Boolean(!_4cf[item]||_4cf[item].toLowerCase()=="disabled");
}
break;
}
}
}
return _4cf;
};
this.getDataProvider=function(_4d4,_4d5){
dojo.io.bind({url:_4d5,load:function(type,_4d7){
if(type=="load"){
_4d4.dataProvider=_4d7;
}
},mimetype:"text/javascript",sync:true});
};
this.getPropertySetById=function(_4d8){
for(var x=0;x<this.propertySetsList.length;x++){
if(_4d8==this.propertySetsList[x]["id"][0].value){
return this.propertySetsList[x];
}
}
return "";
};
this.getPropertySetsByType=function(_4da){
var _4db=[];
for(var x=0;x<this.propertySetsList.length;x++){
var cpl=this.propertySetsList[x];
var cpcc=cpl["componentClass"]||cpl["componentType"]||null;
var _4df=this.propertySetsList[x]["id"][0].value;
if((cpcc)&&(_4df==cpcc[0].value)){
_4db.push(cpl);
}
}
return _4db;
};
this.getPropertySets=function(_4e0){
var ppl="dojo:propertyproviderlist";
var _4e2=[];
var _4e3=_4e0["tagName"];
if(_4e0[ppl]){
var _4e4=_4e0[ppl].value.split(" ");
for(var _4e5 in _4e4){
if((_4e5.indexOf("..")==-1)&&(_4e5.indexOf("://")==-1)){
var _4e6=this.getPropertySetById(_4e5);
if(_4e6!=""){
_4e2.push(_4e6);
}
}else{
}
}
}
return (this.getPropertySetsByType(_4e3)).concat(_4e2);
};
this.createComponentFromScript=function(_4e7,_4e8,_4e9,ns){
_4e9.fastMixIn=true;
var ltn=(ns||"dojo")+":"+_4e8.toLowerCase();
if(dojo.widget.tags[ltn]){
return [dojo.widget.tags[ltn](_4e9,this,null,null,_4e9)];
}
return [dojo.widget.buildWidgetFromParseTree(ltn,_4e9,this,null,null,_4e9)];
};
};
dojo.widget._parser_collection={"dojo":new dojo.widget.Parse()};
dojo.widget.getParser=function(name){
if(!name){
name="dojo";
}
if(!this._parser_collection[name]){
this._parser_collection[name]=new dojo.widget.Parse();
}
return this._parser_collection[name];
};
dojo.widget.createWidget=function(name,_4ee,_4ef,_4f0){
var _4f1=false;
var _4f2=(typeof name=="string");
if(_4f2){
var pos=name.indexOf(":");
var ns=(pos>-1)?name.substring(0,pos):"dojo";
if(pos>-1){
name=name.substring(pos+1);
}
var _4f5=name.toLowerCase();
var _4f6=ns+":"+_4f5;
_4f1=(dojo.byId(name)&&(!dojo.widget.tags[_4f6]));
}
if((arguments.length==1)&&((_4f1)||(!_4f2))){
var xp=new dojo.xml.Parse();
var tn=(_4f1)?dojo.byId(name):name;
return dojo.widget.getParser().createComponents(xp.parseElement(tn,null,true))[0];
}
function fromScript(_4f9,name,_4fb,ns){
_4fb[_4f6]={dojotype:[{value:_4f5}],nodeRef:_4f9,fastMixIn:true};
_4fb["namespace"]=ns;
return dojo.widget.getParser().createComponentFromScript(_4f9,name,_4fb,ns);
}
_4ee=_4ee||{};
var _4fd=false;
var tn=null;
var h=dojo.render.html.capable;
if(h){
tn=document.createElement("span");
}
if(!_4ef){
_4fd=true;
_4ef=tn;
if(h){
dojo.body().appendChild(_4ef);
}
}else{
if(_4f0){
dojo.dom.insertAtPosition(tn,_4ef,_4f0);
}else{
tn=_4ef;
}
}
var _4ff=fromScript(tn,name.toLowerCase(),_4ee,ns);
if(!_4ff||!_4ff[0]||typeof _4ff[0].widgetType=="undefined"){
throw new Error("createWidget: Creation of \""+name+"\" widget failed.");
}
if(_4fd){
if(_4ff[0].domNode.parentNode){
_4ff[0].domNode.parentNode.removeChild(_4ff[0].domNode);
}
}
return _4ff[0];
};
dojo.provide("dojo.html.style");
dojo.html.getClass=function(node){
node=dojo.byId(node);
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
return cs.replace(/^\s+|\s+$/g,"");
};
dojo.html.getClasses=function(node){
var c=dojo.html.getClass(node);
return (c=="")?[]:c.split(/\s+/g);
};
dojo.html.hasClass=function(node,_505){
return (new RegExp("(^|\\s+)"+_505+"(\\s+|$)")).test(dojo.html.getClass(node));
};
dojo.html.prependClass=function(node,_507){
_507+=" "+dojo.html.getClass(node);
return dojo.html.setClass(node,_507);
};
dojo.html.addClass=function(node,_509){
if(dojo.html.hasClass(node,_509)){
return false;
}
_509=(dojo.html.getClass(node)+" "+_509).replace(/^\s+|\s+$/g,"");
return dojo.html.setClass(node,_509);
};
dojo.html.setClass=function(node,_50b){
node=dojo.byId(node);
var cs=new String(_50b);
try{
if(typeof node.className=="string"){
node.className=cs;
}else{
if(node.setAttribute){
node.setAttribute("class",_50b);
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
dojo.html.removeClass=function(node,_50e,_50f){
try{
if(!_50f){
var _510=dojo.html.getClass(node).replace(new RegExp("(^|\\s+)"+_50e+"(\\s+|$)"),"$1$2");
}else{
var _510=dojo.html.getClass(node).replace(_50e,"");
}
dojo.html.setClass(node,_510);
}
catch(e){
dojo.debug("dojo.html.removeClass() failed",e);
}
return true;
};
dojo.html.replaceClass=function(node,_512,_513){
dojo.html.removeClass(node,_513);
dojo.html.addClass(node,_512);
};
dojo.html.classMatchType={ContainsAll:0,ContainsAny:1,IsOnly:2};
dojo.html.getElementsByClass=function(_514,_515,_516,_517,_518){
var _519=dojo.doc();
_515=dojo.byId(_515)||_519;
var _51a=_514.split(/\s+/g);
var _51b=[];
if(_517!=1&&_517!=2){
_517=0;
}
var _51c=new RegExp("(\\s|^)(("+_51a.join(")|(")+"))(\\s|$)");
var _51d=_51a.join(" ").length;
var _51e=[];
if(!_518&&_519.evaluate){
var _51f=".//"+(_516||"*")+"[contains(";
if(_517!=dojo.html.classMatchType.ContainsAny){
_51f+="concat(' ',@class,' '), ' "+_51a.join(" ') and contains(concat(' ',@class,' '), ' ")+" ')";
if(_517==2){
_51f+=" and string-length(@class)="+_51d+"]";
}else{
_51f+="]";
}
}else{
_51f+="concat(' ',@class,' '), ' "+_51a.join(" ') or contains(concat(' ',@class,' '), ' ")+" ')]";
}
var _520=_519.evaluate(_51f,_515,null,XPathResult.ANY_TYPE,null);
var _521=_520.iterateNext();
while(_521){
try{
_51e.push(_521);
_521=_520.iterateNext();
}
catch(e){
break;
}
}
return _51e;
}else{
if(!_516){
_516="*";
}
_51e=_515.getElementsByTagName(_516);
var node,i=0;
outer:
while(node=_51e[i++]){
var _524=dojo.html.getClasses(node);
if(_524.length==0){
continue outer;
}
var _525=0;
for(var j=0;j<_524.length;j++){
if(_51c.test(_524[j])){
if(_517==dojo.html.classMatchType.ContainsAny){
_51b.push(node);
continue outer;
}else{
_525++;
}
}else{
if(_517==dojo.html.classMatchType.IsOnly){
continue outer;
}
}
}
if(_525==_51a.length){
if((_517==dojo.html.classMatchType.IsOnly)&&(_525==_524.length)){
_51b.push(node);
}else{
if(_517==dojo.html.classMatchType.ContainsAll){
_51b.push(node);
}
}
}
}
return _51b;
}
};
dojo.html.getElementsByClassName=dojo.html.getElementsByClass;
dojo.html.toCamelCase=function(_527){
var arr=_527.split("-"),cc=arr[0];
for(var i=1;i<arr.length;i++){
cc+=arr[i].charAt(0).toUpperCase()+arr[i].substring(1);
}
return cc;
};
dojo.html.toSelectorCase=function(_52b){
return _52b.replace(/([A-Z])/g,"-$1").toLowerCase();
};
dojo.html.getComputedStyle=function(node,_52d,_52e){
node=dojo.byId(node);
var _52d=dojo.html.toSelectorCase(_52d);
var _52f=dojo.html.toCamelCase(_52d);
if(!node||!node.style){
return _52e;
}else{
if(document.defaultView&&dojo.html.isDescendantOf(node,node.ownerDocument)){
try{
var cs=document.defaultView.getComputedStyle(node,"");
if(cs){
return cs.getPropertyValue(_52d);
}
}
catch(e){
if(node.style.getPropertyValue){
return node.style.getPropertyValue(_52d);
}else{
return _52e;
}
}
}else{
if(node.currentStyle){
return node.currentStyle[_52f];
}
}
}
if(node.style.getPropertyValue){
return node.style.getPropertyValue(_52d);
}else{
return _52e;
}
};
dojo.html.getStyleProperty=function(node,_532){
node=dojo.byId(node);
return (node&&node.style?node.style[dojo.html.toCamelCase(_532)]:undefined);
};
dojo.html.getStyle=function(node,_534){
var _535=dojo.html.getStyleProperty(node,_534);
return (_535?_535:dojo.html.getComputedStyle(node,_534));
};
dojo.html.setStyle=function(node,_537,_538){
node=dojo.byId(node);
if(node&&node.style){
var _539=dojo.html.toCamelCase(_537);
node.style[_539]=_538;
}
};
dojo.html.setStyleText=function(_53a,text){
try{
_53a.style.cssText=text;
}
catch(e){
_53a.setAttribute("style",text);
}
};
dojo.html.copyStyle=function(_53c,_53d){
if(!_53d.style.cssText){
_53c.setAttribute("style",_53d.getAttribute("style"));
}else{
_53c.style.cssText=_53d.style.cssText;
}
dojo.html.addClass(_53c,dojo.html.getClass(_53d));
};
dojo.html.getUnitValue=function(node,_53f,_540){
var s=dojo.html.getComputedStyle(node,_53f);
if((!s)||((s=="auto")&&(_540))){
return {value:0,units:"px"};
}
var _542=s.match(/(\-?[\d.]+)([a-z%]*)/i);
if(!_542){
return dojo.html.getUnitValue.bad;
}
return {value:Number(_542[1]),units:_542[2].toLowerCase()};
};
dojo.html.getUnitValue.bad={value:NaN,units:""};
dojo.html.getPixelValue=function(node,_544,_545){
var _546=dojo.html.getUnitValue(node,_544,_545);
if(isNaN(_546.value)){
return 0;
}
if((_546.value)&&(_546.units!="px")){
return NaN;
}
return _546.value;
};
dojo.html.setPositivePixelValue=function(node,_548,_549){
if(isNaN(_549)){
return false;
}
node.style[_548]=Math.max(0,_549)+"px";
return true;
};
dojo.html.styleSheet=null;
dojo.html.insertCssRule=function(_54a,_54b,_54c){
if(!dojo.html.styleSheet){
if(document.createStyleSheet){
dojo.html.styleSheet=document.createStyleSheet();
}else{
if(document.styleSheets[0]){
dojo.html.styleSheet=document.styleSheets[0];
}else{
return null;
}
}
}
if(arguments.length<3){
if(dojo.html.styleSheet.cssRules){
_54c=dojo.html.styleSheet.cssRules.length;
}else{
if(dojo.html.styleSheet.rules){
_54c=dojo.html.styleSheet.rules.length;
}else{
return null;
}
}
}
if(dojo.html.styleSheet.insertRule){
var rule=_54a+" { "+_54b+" }";
return dojo.html.styleSheet.insertRule(rule,_54c);
}else{
if(dojo.html.styleSheet.addRule){
return dojo.html.styleSheet.addRule(_54a,_54b,_54c);
}else{
return null;
}
}
};
dojo.html.removeCssRule=function(_54e){
if(!dojo.html.styleSheet){
dojo.debug("no stylesheet defined for removing rules");
return false;
}
if(dojo.render.html.ie){
if(!_54e){
_54e=dojo.html.styleSheet.rules.length;
dojo.html.styleSheet.removeRule(_54e);
}
}else{
if(document.styleSheets[0]){
if(!_54e){
_54e=dojo.html.styleSheet.cssRules.length;
}
dojo.html.styleSheet.deleteRule(_54e);
}
}
return true;
};
dojo.html._insertedCssFiles=[];
dojo.html.insertCssFile=function(URI,doc,_551,_552){
if(!URI){
return;
}
if(!doc){
doc=document;
}
var _553=dojo.hostenv.getText(URI,false,_552);
if(_553===null){
return;
}
_553=dojo.html.fixPathsInCssText(_553,URI);
if(_551){
var idx=-1,node,ent=dojo.html._insertedCssFiles;
for(var i=0;i<ent.length;i++){
if((ent[i].doc==doc)&&(ent[i].cssText==_553)){
idx=i;
node=ent[i].nodeRef;
break;
}
}
if(node){
var _558=doc.getElementsByTagName("style");
for(var i=0;i<_558.length;i++){
if(_558[i]==node){
return;
}
}
dojo.html._insertedCssFiles.shift(idx,1);
}
}
var _559=dojo.html.insertCssText(_553);
dojo.html._insertedCssFiles.push({"doc":doc,"cssText":_553,"nodeRef":_559});
if(_559&&djConfig.isDebug){
_559.setAttribute("dbgHref",URI);
}
return _559;
};
dojo.html.insertCssText=function(_55a,doc,URI){
if(!_55a){
return;
}
if(!doc){
doc=document;
}
if(URI){
_55a=dojo.html.fixPathsInCssText(_55a,URI);
}
var _55d=doc.createElement("style");
_55d.setAttribute("type","text/css");
var head=doc.getElementsByTagName("head")[0];
if(!head){
dojo.debug("No head tag in document, aborting styles");
return;
}else{
head.appendChild(_55d);
}
if(_55d.styleSheet){
_55d.styleSheet.cssText=_55a;
}else{
var _55f=doc.createTextNode(_55a);
_55d.appendChild(_55f);
}
return _55d;
};
dojo.html.fixPathsInCssText=function(_560,URI){
function iefixPathsInCssText(){
var _562=/AlphaImageLoader\(src\=['"]([\t\s\w()\/.\\'"-:#=&?~]*)['"]/;
while(_563=_562.exec(_560)){
url=_563[1].replace(_565,"$2");
if(!_566.exec(url)){
url=(new dojo.uri.Uri(URI,url).toString());
}
str+=_560.substring(0,_563.index)+"AlphaImageLoader(src='"+url+"'";
_560=_560.substr(_563.index+_563[0].length);
}
return str+_560;
}
if(!_560||!URI){
return;
}
var _563,str="",url="";
var _568=/url\(\s*([\t\s\w()\/.\\'"-:#=&?]+)\s*\)/;
var _566=/(file|https?|ftps?):\/\//;
var _565=/^[\s]*(['"]?)([\w()\/.\\'"-:#=&?]*)\1[\s]*?$/;
if(dojo.render.html.ie55||dojo.render.html.ie60){
_560=iefixPathsInCssText();
}
while(_563=_568.exec(_560)){
url=_563[1].replace(_565,"$2");
if(!_566.exec(url)){
url=(new dojo.uri.Uri(URI,url).toString());
}
str+=_560.substring(0,_563.index)+"url("+url+")";
_560=_560.substr(_563.index+_563[0].length);
}
return str+_560;
};
dojo.html.setActiveStyleSheet=function(_569){
var i=0,a,els=dojo.doc().getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")){
a.disabled=true;
if(a.getAttribute("title")==_569){
a.disabled=false;
}
}
}
};
dojo.html.getActiveStyleSheet=function(){
var i=0,a,els=dojo.doc().getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("title")&&!a.disabled){
return a.getAttribute("title");
}
}
return null;
};
dojo.html.getPreferredStyleSheet=function(){
var i=0,a,els=dojo.doc().getElementsByTagName("link");
while(a=els[i++]){
if(a.getAttribute("rel").indexOf("style")!=-1&&a.getAttribute("rel").indexOf("alt")==-1&&a.getAttribute("title")){
return a.getAttribute("title");
}
}
return null;
};
dojo.html.applyBrowserClass=function(node){
var drh=dojo.render.html;
var _575={dj_ie:drh.ie,dj_ie55:drh.ie55,dj_ie6:drh.ie60,dj_ie7:drh.ie70,dj_iequirks:drh.ie&&drh.quirks,dj_opera:drh.opera,dj_opera8:drh.opera&&(Math.floor(dojo.render.version)==8),dj_opera9:drh.opera&&(Math.floor(dojo.render.version)==9),dj_khtml:drh.khtml,dj_safari:drh.safari,dj_gecko:drh.mozilla};
for(var p in _575){
if(_575[p]){
dojo.html.addClass(node,p);
}
}
};
dojo.provide("dojo.widget.DomWidget");
dojo.widget._cssFiles={};
dojo.widget._cssStrings={};
dojo.widget._templateCache={};
dojo.widget.defaultStrings={dojoRoot:dojo.hostenv.getBaseScriptUri(),baseScriptUri:dojo.hostenv.getBaseScriptUri()};
dojo.widget.fillFromTemplateCache=function(obj,_578,_579,_57a){
var _57b=_578||obj.templatePath;
var _57c=dojo.widget._templateCache;
if(!obj["widgetType"]){
do{
var _57d="__dummyTemplate__"+dojo.widget._templateCache.dummyCount++;
}while(_57c[_57d]);
obj.widgetType=_57d;
}
var wt=obj.widgetType;
var ts=_57c[wt];
if(!ts){
_57c[wt]={"string":null,"node":null};
if(_57a){
ts={};
}else{
ts=_57c[wt];
}
}
if((!obj.templateString)&&(!_57a)){
obj.templateString=_579||ts["string"];
}
if((!obj.templateNode)&&(!_57a)){
obj.templateNode=ts["node"];
}
if((!obj.templateNode)&&(!obj.templateString)&&(_57b)){
var _580=dojo.hostenv.getText(_57b);
if(_580){
_580=_580.replace(/^\s*<\?xml(\s)+version=[\'\"](\d)*.(\d)*[\'\"](\s)*\?>/im,"");
var _581=_580.match(/<body[^>]*>\s*([\s\S]+)\s*<\/body>/im);
if(_581){
_580=_581[1];
}
}else{
_580="";
}
obj.templateString=_580;
if(!_57a){
_57c[wt]["string"]=_580;
}
}
if((!ts["string"])&&(!_57a)){
ts.string=obj.templateString;
}
};
dojo.widget._templateCache.dummyCount=0;
dojo.widget.attachProperties=["dojoAttachPoint","id"];
dojo.widget.eventAttachProperty="dojoAttachEvent";
dojo.widget.onBuildProperty="dojoOnBuild";
dojo.widget.waiNames=["waiRole","waiState"];
dojo.widget.wai={waiRole:{name:"waiRole","namespace":"http://www.w3.org/TR/xhtml2",alias:"x2",prefix:"wairole:"},waiState:{name:"waiState","namespace":"http://www.w3.org/2005/07/aaa",alias:"aaa",prefix:""},setAttr:function(node,ns,attr,_585){
if(dojo.render.html.ie){
node.setAttribute(this[ns].alias+":"+attr,this[ns].prefix+_585);
}else{
node.setAttributeNS(this[ns]["namespace"],attr,this[ns].prefix+_585);
}
},getAttr:function(node,ns,attr){
if(dojo.render.html.ie){
return node.getAttribute(this[ns].alias+":"+attr);
}else{
return node.getAttributeNS(this[ns]["namespace"],attr);
}
}};
dojo.widget.attachTemplateNodes=function(_589,_58a,_58b){
var _58c=dojo.dom.ELEMENT_NODE;
function trim(str){
return str.replace(/^\s+|\s+$/g,"");
}
if(!_589){
_589=_58a.domNode;
}
if(_589.nodeType!=_58c){
return;
}
var _58e=_589.all||_589.getElementsByTagName("*");
var _58f=_58a;
for(var x=-1;x<_58e.length;x++){
var _591=(x==-1)?_589:_58e[x];
var _592=[];
if(!_58a.widgetsInTemplate||!_591.getAttribute("dojoType")){
for(var y=0;y<this.attachProperties.length;y++){
var _594=_591.getAttribute(this.attachProperties[y]);
if(_594){
_592=_594.split(";");
for(var z=0;z<_592.length;z++){
if(dojo.lang.isArray(_58a[_592[z]])){
_58a[_592[z]].push(_591);
}else{
_58a[_592[z]]=_591;
}
}
break;
}
}
var _596=_591.getAttribute(this.eventAttachProperty);
if(_596){
var evts=_596.split(";");
for(var y=0;y<evts.length;y++){
if((!evts[y])||(!evts[y].length)){
continue;
}
var _598=null;
var tevt=trim(evts[y]);
if(evts[y].indexOf(":")>=0){
var _59a=tevt.split(":");
tevt=trim(_59a[0]);
_598=trim(_59a[1]);
}
if(!_598){
_598=tevt;
}
var tf=function(){
var ntf=new String(_598);
return function(evt){
if(_58f[ntf]){
_58f[ntf](dojo.event.browser.fixEvent(evt,this));
}
};
}();
dojo.event.browser.addListener(_591,tevt,tf,false,true);
}
}
for(var y=0;y<_58b.length;y++){
var _59e=_591.getAttribute(_58b[y]);
if((_59e)&&(_59e.length)){
var _598=null;
var _59f=_58b[y].substr(4);
_598=trim(_59e);
var _5a0=[_598];
if(_598.indexOf(";")>=0){
_5a0=dojo.lang.map(_598.split(";"),trim);
}
for(var z=0;z<_5a0.length;z++){
if(!_5a0[z].length){
continue;
}
var tf=function(){
var ntf=new String(_5a0[z]);
return function(evt){
if(_58f[ntf]){
_58f[ntf](dojo.event.browser.fixEvent(evt,this));
}
};
}();
dojo.event.browser.addListener(_591,_59f,tf,false,true);
}
}
}
}
var _5a3=_591.getAttribute(this.templateProperty);
if(_5a3){
_58a[_5a3]=_591;
}
dojo.lang.forEach(dojo.widget.waiNames,function(name){
var wai=dojo.widget.wai[name];
var val=_591.getAttribute(wai.name);
if(val){
if(val.indexOf("-")==-1){
dojo.widget.wai.setAttr(_591,wai.name,"role",val);
}else{
var _5a7=val.split("-");
dojo.widget.wai.setAttr(_591,wai.name,_5a7[0],_5a7[1]);
}
}
},this);
var _5a8=_591.getAttribute(this.onBuildProperty);
if(_5a8){
eval("var node = baseNode; var widget = targetObj; "+_5a8);
}
}
};
dojo.widget.getDojoEventsFromStr=function(str){
var re=/(dojoOn([a-z]+)(\s?))=/gi;
var evts=str?str.match(re)||[]:[];
var ret=[];
var lem={};
for(var x=0;x<evts.length;x++){
if(evts[x].length<1){
continue;
}
var cm=evts[x].replace(/\s/,"");
cm=(cm.slice(0,cm.length-1));
if(!lem[cm]){
lem[cm]=true;
ret.push(cm);
}
}
return ret;
};
dojo.declare("dojo.widget.DomWidget",dojo.widget.Widget,function(){
if((arguments.length>0)&&(typeof arguments[0]=="object")){
this.create(arguments[0]);
}
},{templateNode:null,templateString:null,templateCssString:null,preventClobber:false,domNode:null,containerNode:null,widgetsInTemplate:false,addChild:function(_5b0,_5b1,pos,ref,_5b4){
if(!this.isContainer){
dojo.debug("dojo.widget.DomWidget.addChild() attempted on non-container widget");
return null;
}else{
if(_5b4==undefined){
_5b4=this.children.length;
}
this.addWidgetAsDirectChild(_5b0,_5b1,pos,ref,_5b4);
this.registerChild(_5b0,_5b4);
}
return _5b0;
},addWidgetAsDirectChild:function(_5b5,_5b6,pos,ref,_5b9){
if((!this.containerNode)&&(!_5b6)){
this.containerNode=this.domNode;
}
var cn=(_5b6)?_5b6:this.containerNode;
if(!pos){
pos="after";
}
if(!ref){
if(!cn){
cn=dojo.body();
}
ref=cn.lastChild;
}
if(!_5b9){
_5b9=0;
}
_5b5.domNode.setAttribute("dojoinsertionindex",_5b9);
if(!ref){
cn.appendChild(_5b5.domNode);
}else{
if(pos=="insertAtIndex"){
dojo.dom.insertAtIndex(_5b5.domNode,ref.parentNode,_5b9);
}else{
if((pos=="after")&&(ref===cn.lastChild)){
cn.appendChild(_5b5.domNode);
}else{
dojo.dom.insertAtPosition(_5b5.domNode,cn,pos);
}
}
}
},registerChild:function(_5bb,_5bc){
_5bb.dojoInsertionIndex=_5bc;
var idx=-1;
for(var i=0;i<this.children.length;i++){
if(this.children[i].dojoInsertionIndex<=_5bc){
idx=i;
}
}
this.children.splice(idx+1,0,_5bb);
_5bb.parent=this;
_5bb.addedTo(this,idx+1);
delete dojo.widget.manager.topWidgets[_5bb.widgetId];
},removeChild:function(_5bf){
dojo.dom.removeNode(_5bf.domNode);
return dojo.widget.DomWidget.superclass.removeChild.call(this,_5bf);
},getFragNodeRef:function(frag){
if(!frag){
return null;
}
if(!frag[this.getNamespacedType()]){
dojo.raise("Error: no frag for widget type "+this.getNamespacedType()+", id "+this.widgetId+" (maybe a widget has set it's type incorrectly)");
}
return frag[this.getNamespacedType()]["nodeRef"];
},postInitialize:function(args,frag,_5c3){
var _5c4=this.getFragNodeRef(frag);
if(_5c3&&(_5c3.snarfChildDomOutput||!_5c4)){
_5c3.addWidgetAsDirectChild(this,"","insertAtIndex","",args["dojoinsertionindex"],_5c4);
}else{
if(_5c4){
if(this.domNode&&(this.domNode!==_5c4)){
var _5c5=_5c4.parentNode.replaceChild(this.domNode,_5c4);
}
}
}
if(_5c3){
_5c3.registerChild(this,args.dojoinsertionindex);
}else{
dojo.widget.manager.topWidgets[this.widgetId]=this;
}
if(this.widgetsInTemplate){
var _5c6=new dojo.xml.Parse();
var _5c7;
var _5c8=this.domNode.getElementsByTagName("*");
for(var i=0;i<_5c8.length;i++){
if(_5c8[i].getAttribute("dojoAttachPoint")=="subContainerWidget"){
_5c7=_5c8[i];
}
if(_5c8[i].getAttribute("dojoType")){
_5c8[i].setAttribute("_isSubWidget",true);
}
}
if(this.isContainer&&!this.containerNode){
if(_5c7){
var src=this.getFragNodeRef(frag);
if(src){
dojo.dom.moveChildren(src,_5c7);
frag["dojoDontFollow"]=true;
}
}else{
dojo.debug("No subContainerWidget node can be found in template file for widget "+this);
}
}
var _5cb=_5c6.parseElement(this.domNode,null,true);
dojo.widget.getParser().createSubComponents(_5cb,this);
var _5cc=[];
var _5cd=[this];
var w;
while((w=_5cd.pop())){
for(var i=0;i<w.children.length;i++){
var _5cf=w.children[i];
if(_5cf._processedSubWidgets||!_5cf.extraArgs["_issubwidget"]){
continue;
}
_5cc.push(_5cf);
if(_5cf.isContainer){
_5cd.push(_5cf);
}
}
}
for(var i=0;i<_5cc.length;i++){
var _5d0=_5cc[i];
if(_5d0._processedSubWidgets){
dojo.debug("This should not happen: widget._processedSubWidgets is already true!");
return;
}
_5d0._processedSubWidgets=true;
if(_5d0.extraArgs["dojoattachevent"]){
var evts=_5d0.extraArgs["dojoattachevent"].split(";");
for(var j=0;j<evts.length;j++){
var _5d3=null;
var tevt=dojo.string.trim(evts[j]);
if(tevt.indexOf(":")>=0){
var _5d5=tevt.split(":");
tevt=dojo.string.trim(_5d5[0]);
_5d3=dojo.string.trim(_5d5[1]);
}
if(!_5d3){
_5d3=tevt;
}
if(dojo.lang.isFunction(_5d0[tevt])){
dojo.event.kwConnect({srcObj:_5d0,srcFunc:tevt,targetObj:this,targetFunc:_5d3});
}else{
alert(tevt+" is not a function in widget "+_5d0);
}
}
}
if(_5d0.extraArgs["dojoattachpoint"]){
this[_5d0.extraArgs["dojoattachpoint"]]=_5d0;
}
}
}
if(this.isContainer&&!frag["dojoDontFollow"]){
dojo.widget.getParser().createSubComponents(frag,this);
}
},buildRendering:function(args,frag){
var ts=dojo.widget._templateCache[this.widgetType];
if(args["templatecsspath"]){
args["templateCssPath"]=args["templatecsspath"];
}
var _5d9=args["templateCssPath"]||this.templateCssPath;
if(_5d9&&!dojo.widget._cssFiles[_5d9.toString()]){
if((!this.templateCssString)&&(_5d9)){
this.templateCssString=dojo.hostenv.getText(_5d9);
this.templateCssPath=null;
}
dojo.widget._cssFiles[_5d9.toString()]=true;
}
if((this["templateCssString"])&&(!this.templateCssString["loaded"])){
dojo.html.insertCssText(this.templateCssString,null,_5d9);
if(!this.templateCssString){
this.templateCssString="";
}
this.templateCssString.loaded=true;
}
if((!this.preventClobber)&&((this.templatePath)||(this.templateNode)||((this["templateString"])&&(this.templateString.length))||((typeof ts!="undefined")&&((ts["string"])||(ts["node"]))))){
this.buildFromTemplate(args,frag);
}else{
this.domNode=this.getFragNodeRef(frag);
}
this.fillInTemplate(args,frag);
},buildFromTemplate:function(args,frag){
var _5dc=false;
if(args["templatepath"]){
_5dc=true;
args["templatePath"]=args["templatepath"];
}
dojo.widget.fillFromTemplateCache(this,args["templatePath"],null,_5dc);
var ts=dojo.widget._templateCache[this.widgetType];
if((ts)&&(!_5dc)){
if(!this.templateString.length){
this.templateString=ts["string"];
}
if(!this.templateNode){
this.templateNode=ts["node"];
}
}
var _5de=false;
var node=null;
var tstr=this.templateString;
if((!this.templateNode)&&(this.templateString)){
_5de=this.templateString.match(/\$\{([^\}]+)\}/g);
if(_5de){
var hash=this.strings||{};
for(var key in dojo.widget.defaultStrings){
if(dojo.lang.isUndefined(hash[key])){
hash[key]=dojo.widget.defaultStrings[key];
}
}
for(var i=0;i<_5de.length;i++){
var key=_5de[i];
key=key.substring(2,key.length-1);
var kval=(key.substring(0,5)=="this.")?dojo.lang.getObjPathValue(key.substring(5),this):hash[key];
var _5e5;
if((kval)||(dojo.lang.isString(kval))){
_5e5=new String((dojo.lang.isFunction(kval))?kval.call(this,key,this.templateString):kval);
while(_5e5.indexOf("\"")>-1){
_5e5=_5e5.replace("\"","&quot;");
}
tstr=tstr.replace(_5de[i],_5e5);
}
}
}else{
this.templateNode=this.createNodesFromText(this.templateString,true)[0];
if(!_5dc){
ts.node=this.templateNode;
}
}
}
if((!this.templateNode)&&(!_5de)){
dojo.debug("DomWidget.buildFromTemplate: could not create template");
return false;
}else{
if(!_5de){
node=this.templateNode.cloneNode(true);
if(!node){
return false;
}
}else{
node=this.createNodesFromText(tstr,true)[0];
}
}
this.domNode=node;
this.attachTemplateNodes();
if(this.isContainer&&this.containerNode){
var src=this.getFragNodeRef(frag);
if(src){
dojo.dom.moveChildren(src,this.containerNode);
}
}
},attachTemplateNodes:function(_5e7,_5e8){
if(!_5e7){
_5e7=this.domNode;
}
if(!_5e8){
_5e8=this;
}
return dojo.widget.attachTemplateNodes(_5e7,_5e8,dojo.widget.getDojoEventsFromStr(this.templateString));
},fillInTemplate:function(){
},destroyRendering:function(){
try{
delete this.domNode;
}
catch(e){
}
},cleanUp:function(){
},getContainerHeight:function(){
dojo.unimplemented("dojo.widget.DomWidget.getContainerHeight");
},getContainerWidth:function(){
dojo.unimplemented("dojo.widget.DomWidget.getContainerWidth");
},createNodesFromText:function(){
dojo.unimplemented("dojo.widget.DomWidget.createNodesFromText");
}});
dojo.provide("dojo.html.display");
dojo.html._toggle=function(node,_5ea,_5eb){
node=dojo.byId(node);
_5eb(node,!_5ea(node));
return _5ea(node);
};
dojo.html.show=function(node){
node=dojo.byId(node);
if(dojo.html.getStyleProperty(node,"display")=="none"){
dojo.html.setStyle(node,"display",(node.dojoDisplayCache||""));
node.dojoDisplayCache=undefined;
}
};
dojo.html.hide=function(node){
node=dojo.byId(node);
if(typeof node["dojoDisplayCache"]=="undefined"){
var d=dojo.html.getStyleProperty(node,"display");
if(d!="none"){
node.dojoDisplayCache=d;
}
}
dojo.html.setStyle(node,"display","none");
};
dojo.html.setShowing=function(node,_5f0){
dojo.html[(_5f0?"show":"hide")](node);
};
dojo.html.isShowing=function(node){
return (dojo.html.getStyleProperty(node,"display")!="none");
};
dojo.html.toggleShowing=function(node){
return dojo.html._toggle(node,dojo.html.isShowing,dojo.html.setShowing);
};
dojo.html.displayMap={tr:"",td:"",th:"",img:"inline",span:"inline",input:"inline",button:"inline"};
dojo.html.suggestDisplayByTagName=function(node){
node=dojo.byId(node);
if(node&&node.tagName){
var tag=node.tagName.toLowerCase();
return (tag in dojo.html.displayMap?dojo.html.displayMap[tag]:"block");
}
};
dojo.html.setDisplay=function(node,_5f6){
dojo.html.setStyle(node,"display",((_5f6 instanceof String||typeof _5f6=="string")?_5f6:(_5f6?dojo.html.suggestDisplayByTagName(node):"none")));
};
dojo.html.isDisplayed=function(node){
return (dojo.html.getComputedStyle(node,"display")!="none");
};
dojo.html.toggleDisplay=function(node){
return dojo.html._toggle(node,dojo.html.isDisplayed,dojo.html.setDisplay);
};
dojo.html.setVisibility=function(node,_5fa){
dojo.html.setStyle(node,"visibility",((_5fa instanceof String||typeof _5fa=="string")?_5fa:(_5fa?"visible":"hidden")));
};
dojo.html.isVisible=function(node){
return (dojo.html.getComputedStyle(node,"visibility")!="hidden");
};
dojo.html.toggleVisibility=function(node){
return dojo.html._toggle(node,dojo.html.isVisible,dojo.html.setVisibility);
};
dojo.html.setOpacity=function(node,_5fe,_5ff){
node=dojo.byId(node);
var h=dojo.render.html;
if(!_5ff){
if(_5fe>=1){
if(h.ie){
dojo.html.clearOpacity(node);
return;
}else{
_5fe=0.999999;
}
}else{
if(_5fe<0){
_5fe=0;
}
}
}
if(h.ie){
if(node.nodeName.toLowerCase()=="tr"){
var tds=node.getElementsByTagName("td");
for(var x=0;x<tds.length;x++){
tds[x].style.filter="Alpha(Opacity="+_5fe*100+")";
}
}
node.style.filter="Alpha(Opacity="+_5fe*100+")";
}else{
if(h.moz){
node.style.opacity=_5fe;
node.style.MozOpacity=_5fe;
}else{
if(h.safari){
node.style.opacity=_5fe;
node.style.KhtmlOpacity=_5fe;
}else{
node.style.opacity=_5fe;
}
}
}
};
dojo.html.clearOpacity=function(node){
node=dojo.byId(node);
var ns=node.style;
var h=dojo.render.html;
if(h.ie){
try{
if(node.filters&&node.filters.alpha){
ns.filter="";
}
}
catch(e){
}
}else{
if(h.moz){
ns.opacity=1;
ns.MozOpacity=1;
}else{
if(h.safari){
ns.opacity=1;
ns.KhtmlOpacity=1;
}else{
ns.opacity=1;
}
}
}
};
dojo.html.getOpacity=function(node){
node=dojo.byId(node);
var h=dojo.render.html;
if(h.ie){
var opac=(node.filters&&node.filters.alpha&&typeof node.filters.alpha.opacity=="number"?node.filters.alpha.opacity:100)/100;
}else{
var opac=node.style.opacity||node.style.MozOpacity||node.style.KhtmlOpacity||1;
}
return opac>=0.999999?1:Number(opac);
};
dojo.provide("dojo.html.layout");
dojo.html.sumAncestorProperties=function(node,prop){
node=dojo.byId(node);
if(!node){
return 0;
}
var _60b=0;
while(node){
if(dojo.html.getComputedStyle(node,"position")=="fixed"){
return 0;
}
var val=node[prop];
if(val){
_60b+=val-0;
if(node==dojo.body()){
break;
}
}
node=node.parentNode;
}
return _60b;
};
dojo.html.setStyleAttributes=function(node,_60e){
node=dojo.byId(node);
var _60f=_60e.replace(/(;)?\s*$/,"").split(";");
for(var i=0;i<_60f.length;i++){
var _611=_60f[i].split(":");
var name=_611[0].replace(/\s*$/,"").replace(/^\s*/,"").toLowerCase();
var _613=_611[1].replace(/\s*$/,"").replace(/^\s*/,"");
switch(name){
case "opacity":
dojo.html.setOpacity(node,_613);
break;
case "content-height":
dojo.html.setContentBox(node,{height:_613});
break;
case "content-width":
dojo.html.setContentBox(node,{width:_613});
break;
case "outer-height":
dojo.html.setMarginBox(node,{height:_613});
break;
case "outer-width":
dojo.html.setMarginBox(node,{width:_613});
break;
default:
node.style[dojo.html.toCamelCase(name)]=_613;
}
}
};
dojo.html.boxSizing={MARGIN_BOX:"margin-box",BORDER_BOX:"border-box",PADDING_BOX:"padding-box",CONTENT_BOX:"content-box"};
dojo.html.getAbsolutePosition=dojo.html.abs=function(node,_615,_616){
node=dojo.byId(node,node.ownerDocument);
var ret={x:0,y:0};
var bs=dojo.html.boxSizing;
if(!_616){
_616=bs.CONTENT_BOX;
}
var _619=2;
var _61a;
switch(_616){
case bs.MARGIN_BOX:
_61a=3;
break;
case bs.BORDER_BOX:
_61a=2;
break;
case bs.PADDING_BOX:
default:
_61a=1;
break;
case bs.CONTENT_BOX:
_61a=0;
break;
}
var h=dojo.render.html;
var db=document["body"]||document["documentElement"];
if(h.ie){
with(node.getBoundingClientRect()){
ret.x=left-2;
ret.y=top-2;
}
}else{
if(document.getBoxObjectFor){
_619=1;
try{
var bo=document.getBoxObjectFor(node);
ret.x=bo.x-dojo.html.sumAncestorProperties(node,"scrollLeft");
ret.y=bo.y-dojo.html.sumAncestorProperties(node,"scrollTop");
}
catch(e){
}
}else{
if(node["offsetParent"]){
var _61e;
if((h.safari)&&(node.style.getPropertyValue("position")=="absolute")&&(node.parentNode==db)){
_61e=db;
}else{
_61e=db.parentNode;
}
if(node.parentNode!=db){
var nd=node;
if(dojo.render.html.opera){
nd=db;
}
ret.x-=dojo.html.sumAncestorProperties(nd,"scrollLeft");
ret.y-=dojo.html.sumAncestorProperties(nd,"scrollTop");
}
var _620=node;
do{
var n=_620["offsetLeft"];
if(!h.opera||n>0){
ret.x+=isNaN(n)?0:n;
}
var m=_620["offsetTop"];
ret.y+=isNaN(m)?0:m;
_620=_620.offsetParent;
}while((_620!=_61e)&&(_620!=null));
}else{
if(node["x"]&&node["y"]){
ret.x+=isNaN(node.x)?0:node.x;
ret.y+=isNaN(node.y)?0:node.y;
}
}
}
}
if(_615){
var _623=dojo.html.getScroll();
ret.y+=_623.top;
ret.x+=_623.left;
}
var _624=[dojo.html.getPaddingExtent,dojo.html.getBorderExtent,dojo.html.getMarginExtent];
if(_619>_61a){
for(var i=_61a;i<_619;++i){
ret.y+=_624[i](node,"top");
ret.x+=_624[i](node,"left");
}
}else{
if(_619<_61a){
for(var i=_61a;i>_619;--i){
ret.y-=_624[i-1](node,"top");
ret.x-=_624[i-1](node,"left");
}
}
}
ret.top=ret.y;
ret.left=ret.x;
return ret;
};
dojo.html.isPositionAbsolute=function(node){
return (dojo.html.getComputedStyle(node,"position")=="absolute");
};
dojo.html._sumPixelValues=function(node,_628,_629){
var _62a=0;
for(var x=0;x<_628.length;x++){
_62a+=dojo.html.getPixelValue(node,_628[x],_629);
}
return _62a;
};
dojo.html.getMargin=function(node){
return {width:dojo.html._sumPixelValues(node,["margin-left","margin-right"],(dojo.html.getComputedStyle(node,"position")=="absolute")),height:dojo.html._sumPixelValues(node,["margin-top","margin-bottom"],(dojo.html.getComputedStyle(node,"position")=="absolute"))};
};
dojo.html.getBorder=function(node){
return {width:dojo.html.getBorderExtent(node,"left")+dojo.html.getBorderExtent(node,"right"),height:dojo.html.getBorderExtent(node,"top")+dojo.html.getBorderExtent(node,"bottom")};
};
dojo.html.getBorderExtent=function(node,side){
return (dojo.html.getStyle(node,"border-"+side+"-style")=="none"?0:dojo.html.getPixelValue(node,"border-"+side+"-width"));
};
dojo.html.getMarginExtent=function(node,side){
return dojo.html._sumPixelValues(node,["margin-"+side],dojo.html.isPositionAbsolute(node));
};
dojo.html.getPaddingExtent=function(node,side){
return dojo.html._sumPixelValues(node,["padding-"+side],true);
};
dojo.html.getPadding=function(node){
return {width:dojo.html._sumPixelValues(node,["padding-left","padding-right"],true),height:dojo.html._sumPixelValues(node,["padding-top","padding-bottom"],true)};
};
dojo.html.getPadBorder=function(node){
var pad=dojo.html.getPadding(node);
var _637=dojo.html.getBorder(node);
return {width:pad.width+_637.width,height:pad.height+_637.height};
};
dojo.html.getBoxSizing=function(node){
var h=dojo.render.html;
var bs=dojo.html.boxSizing;
if((h.ie)||(h.opera)){
var cm=document["compatMode"];
if((cm=="BackCompat")||(cm=="QuirksMode")){
return bs.BORDER_BOX;
}else{
return bs.CONTENT_BOX;
}
}else{
if(arguments.length==0){
node=document.documentElement;
}
var _63c=dojo.html.getStyle(node,"-moz-box-sizing");
if(!_63c){
_63c=dojo.html.getStyle(node,"box-sizing");
}
return (_63c?_63c:bs.CONTENT_BOX);
}
};
dojo.html.isBorderBox=function(node){
return (dojo.html.getBoxSizing(node)==dojo.html.boxSizing.BORDER_BOX);
};
dojo.html.getBorderBox=function(node){
node=dojo.byId(node);
return {width:node.offsetWidth,height:node.offsetHeight};
};
dojo.html.getPaddingBox=function(node){
var box=dojo.html.getBorderBox(node);
var _641=dojo.html.getBorder(node);
return {width:box.width-_641.width,height:box.height-_641.height};
};
dojo.html.getContentBox=function(node){
node=dojo.byId(node);
var _643=dojo.html.getPadBorder(node);
return {width:node.offsetWidth-_643.width,height:node.offsetHeight-_643.height};
};
dojo.html.setContentBox=function(node,args){
node=dojo.byId(node);
var _646=0;
var _647=0;
var isbb=dojo.html.isBorderBox(node);
var _649=(isbb?dojo.html.getPadBorder(node):{width:0,height:0});
var ret={};
if(typeof args.width!="undefined"){
_646=args.width+_649.width;
ret.width=dojo.html.setPositivePixelValue(node,"width",_646);
}
if(typeof args.height!="undefined"){
_647=args.height+_649.height;
ret.height=dojo.html.setPositivePixelValue(node,"height",_647);
}
return ret;
};
dojo.html.getMarginBox=function(node){
var _64c=dojo.html.getBorderBox(node);
var _64d=dojo.html.getMargin(node);
return {width:_64c.width+_64d.width,height:_64c.height+_64d.height};
};
dojo.html.setMarginBox=function(node,args){
node=dojo.byId(node);
var _650=0;
var _651=0;
var isbb=dojo.html.isBorderBox(node);
var _653=(!isbb?dojo.html.getPadBorder(node):{width:0,height:0});
var _654=dojo.html.getMargin(node);
var ret={};
if(typeof args.width!="undefined"){
_650=args.width-_653.width;
_650-=_654.width;
ret.width=dojo.html.setPositivePixelValue(node,"width",_650);
}
if(typeof args.height!="undefined"){
_651=args.height-_653.height;
_651-=_654.height;
ret.height=dojo.html.setPositivePixelValue(node,"height",_651);
}
return ret;
};
dojo.html.getElementBox=function(node,type){
var bs=dojo.html.boxSizing;
switch(type){
case bs.MARGIN_BOX:
return dojo.html.getMarginBox(node);
case bs.BORDER_BOX:
return dojo.html.getBorderBox(node);
case bs.PADDING_BOX:
return dojo.html.getPaddingBox(node);
case bs.CONTENT_BOX:
default:
return dojo.html.getContentBox(node);
}
};
dojo.html.toCoordinateObject=dojo.html.toCoordinateArray=function(_659,_65a,_65b){
if(_659 instanceof Array||typeof _659=="array"){
dojo.deprecated("dojo.html.toCoordinateArray","use dojo.html.toCoordinateObject({left: , top: , width: , height: }) instead","0.5");
while(_659.length<4){
_659.push(0);
}
while(_659.length>4){
_659.pop();
}
var ret={left:_659[0],top:_659[1],width:_659[2],height:_659[3]};
}else{
if(!_659.nodeType&&!(_659 instanceof String||typeof _659=="string")&&("width" in _659||"height" in _659||"left" in _659||"x" in _659||"top" in _659||"y" in _659)){
var ret={left:_659.left||_659.x||0,top:_659.top||_659.y||0,width:_659.width||0,height:_659.height||0};
}else{
var node=dojo.byId(_659);
var pos=dojo.html.abs(node,_65a,_65b);
var _65f=dojo.html.getMarginBox(node);
var ret={left:pos.left,top:pos.top,width:_65f.width,height:_65f.height};
}
}
ret.x=ret.left;
ret.y=ret.top;
return ret;
};
dojo.html.setMarginBoxWidth=dojo.html.setOuterWidth=function(node,_661){
return dojo.html._callDeprecated("setMarginBoxWidth","setMarginBox",arguments,"width");
};
dojo.html.setMarginBoxHeight=dojo.html.setOuterHeight=function(){
return dojo.html._callDeprecated("setMarginBoxHeight","setMarginBox",arguments,"height");
};
dojo.html.getMarginBoxWidth=dojo.html.getOuterWidth=function(){
return dojo.html._callDeprecated("getMarginBoxWidth","getMarginBox",arguments,null,"width");
};
dojo.html.getMarginBoxHeight=dojo.html.getOuterHeight=function(){
return dojo.html._callDeprecated("getMarginBoxHeight","getMarginBox",arguments,null,"height");
};
dojo.html.getTotalOffset=function(node,type,_664){
return dojo.html._callDeprecated("getTotalOffset","getAbsolutePosition",arguments,null,type);
};
dojo.html.getAbsoluteX=function(node,_666){
return dojo.html._callDeprecated("getAbsoluteX","getAbsolutePosition",arguments,null,"x");
};
dojo.html.getAbsoluteY=function(node,_668){
return dojo.html._callDeprecated("getAbsoluteY","getAbsolutePosition",arguments,null,"y");
};
dojo.html.totalOffsetLeft=function(node,_66a){
return dojo.html._callDeprecated("totalOffsetLeft","getAbsolutePosition",arguments,null,"left");
};
dojo.html.totalOffsetTop=function(node,_66c){
return dojo.html._callDeprecated("totalOffsetTop","getAbsolutePosition",arguments,null,"top");
};
dojo.html.getMarginWidth=function(node){
return dojo.html._callDeprecated("getMarginWidth","getMargin",arguments,null,"width");
};
dojo.html.getMarginHeight=function(node){
return dojo.html._callDeprecated("getMarginHeight","getMargin",arguments,null,"height");
};
dojo.html.getBorderWidth=function(node){
return dojo.html._callDeprecated("getBorderWidth","getBorder",arguments,null,"width");
};
dojo.html.getBorderHeight=function(node){
return dojo.html._callDeprecated("getBorderHeight","getBorder",arguments,null,"height");
};
dojo.html.getPaddingWidth=function(node){
return dojo.html._callDeprecated("getPaddingWidth","getPadding",arguments,null,"width");
};
dojo.html.getPaddingHeight=function(node){
return dojo.html._callDeprecated("getPaddingHeight","getPadding",arguments,null,"height");
};
dojo.html.getPadBorderWidth=function(node){
return dojo.html._callDeprecated("getPadBorderWidth","getPadBorder",arguments,null,"width");
};
dojo.html.getPadBorderHeight=function(node){
return dojo.html._callDeprecated("getPadBorderHeight","getPadBorder",arguments,null,"height");
};
dojo.html.getBorderBoxWidth=dojo.html.getInnerWidth=function(){
return dojo.html._callDeprecated("getBorderBoxWidth","getBorderBox",arguments,null,"width");
};
dojo.html.getBorderBoxHeight=dojo.html.getInnerHeight=function(){
return dojo.html._callDeprecated("getBorderBoxHeight","getBorderBox",arguments,null,"height");
};
dojo.html.getContentBoxWidth=dojo.html.getContentWidth=function(){
return dojo.html._callDeprecated("getContentBoxWidth","getContentBox",arguments,null,"width");
};
dojo.html.getContentBoxHeight=dojo.html.getContentHeight=function(){
return dojo.html._callDeprecated("getContentBoxHeight","getContentBox",arguments,null,"height");
};
dojo.html.setContentBoxWidth=dojo.html.setContentWidth=function(node,_676){
return dojo.html._callDeprecated("setContentBoxWidth","setContentBox",arguments,"width");
};
dojo.html.setContentBoxHeight=dojo.html.setContentHeight=function(node,_678){
return dojo.html._callDeprecated("setContentBoxHeight","setContentBox",arguments,"height");
};
dojo.provide("dojo.html.util");
dojo.html.getElementWindow=function(_679){
return dojo.html.getDocumentWindow(_679.ownerDocument);
};
dojo.html.getDocumentWindow=function(doc){
if(dojo.render.html.safari&&!doc._parentWindow){
var fix=function(win){
win.document._parentWindow=win;
for(var i=0;i<win.frames.length;i++){
fix(win.frames[i]);
}
};
fix(window.top);
}
if(dojo.render.html.ie&&window!==document.parentWindow&&!doc._parentWindow){
doc.parentWindow.execScript("document._parentWindow = window;","Javascript");
var win=doc._parentWindow;
doc._parentWindow=null;
return win;
}
return doc._parentWindow||doc.parentWindow||doc.defaultView;
};
dojo.html.gravity=function(node,e){
node=dojo.byId(node);
var _681=dojo.html.getCursorPosition(e);
with(dojo.html){
var _682=getAbsolutePosition(node,true);
var bb=getBorderBox(node);
var _684=_682.x+(bb.width/2);
var _685=_682.y+(bb.height/2);
}
with(dojo.html.gravity){
return ((_681.x<_684?WEST:EAST)|(_681.y<_685?NORTH:SOUTH));
}
};
dojo.html.gravity.NORTH=1;
dojo.html.gravity.SOUTH=1<<1;
dojo.html.gravity.EAST=1<<2;
dojo.html.gravity.WEST=1<<3;
dojo.html.overElement=function(_686,e){
_686=dojo.byId(_686);
var _688=dojo.html.getCursorPosition(e);
var bb=dojo.html.getBorderBox(_686);
var _68a=dojo.html.getAbsolutePosition(_686,true,dojo.html.boxSizing.BORDER_BOX);
var top=_68a.y;
var _68c=top+bb.height;
var left=_68a.x;
var _68e=left+bb.width;
return (_688.x>=left&&_688.x<=_68e&&_688.y>=top&&_688.y<=_68c);
};
dojo.html.renderedTextContent=function(node){
node=dojo.byId(node);
var _690="";
if(node==null){
return _690;
}
for(var i=0;i<node.childNodes.length;i++){
switch(node.childNodes[i].nodeType){
case 1:
case 5:
var _692="unknown";
try{
_692=dojo.html.getStyle(node.childNodes[i],"display");
}
catch(E){
}
switch(_692){
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
_690+="\n";
_690+=dojo.html.renderedTextContent(node.childNodes[i]);
_690+="\n";
break;
case "none":
break;
default:
if(node.childNodes[i].tagName&&node.childNodes[i].tagName.toLowerCase()=="br"){
_690+="\n";
}else{
_690+=dojo.html.renderedTextContent(node.childNodes[i]);
}
break;
}
break;
case 3:
case 2:
case 4:
var text=node.childNodes[i].nodeValue;
var _694="unknown";
try{
_694=dojo.html.getStyle(node,"text-transform");
}
catch(E){
}
switch(_694){
case "capitalize":
var _695=text.split(" ");
for(var i=0;i<_695.length;i++){
_695[i]=_695[i].charAt(0).toUpperCase()+_695[i].substring(1);
}
text=_695.join(" ");
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
switch(_694){
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
if(/\s$/.test(_690)){
text.replace(/^\s/,"");
}
break;
}
_690+=text;
break;
default:
break;
}
}
return _690;
};
dojo.html.createNodesFromText=function(txt,trim){
if(trim){
txt=txt.replace(/^\s+|\s+$/g,"");
}
var tn=dojo.doc().createElement("div");
tn.style.visibility="hidden";
dojo.body().appendChild(tn);
var _699="none";
if((/^<t[dh][\s\r\n>]/i).test(txt.replace(/^\s+/))){
txt="<table><tbody><tr>"+txt+"</tr></tbody></table>";
_699="cell";
}else{
if((/^<tr[\s\r\n>]/i).test(txt.replace(/^\s+/))){
txt="<table><tbody>"+txt+"</tbody></table>";
_699="row";
}else{
if((/^<(thead|tbody|tfoot)[\s\r\n>]/i).test(txt.replace(/^\s+/))){
txt="<table>"+txt+"</table>";
_699="section";
}
}
}
tn.innerHTML=txt;
if(tn["normalize"]){
tn.normalize();
}
var _69a=null;
switch(_699){
case "cell":
_69a=tn.getElementsByTagName("tr")[0];
break;
case "row":
_69a=tn.getElementsByTagName("tbody")[0];
break;
case "section":
_69a=tn.getElementsByTagName("table")[0];
break;
default:
_69a=tn;
break;
}
var _69b=[];
for(var x=0;x<_69a.childNodes.length;x++){
_69b.push(_69a.childNodes[x].cloneNode(true));
}
tn.style.display="none";
dojo.body().removeChild(tn);
return _69b;
};
dojo.html.placeOnScreen=function(node,_69e,_69f,_6a0,_6a1,_6a2,_6a3){
if(_69e instanceof Array||typeof _69e=="array"){
_6a3=_6a2;
_6a2=_6a1;
_6a1=_6a0;
_6a0=_69f;
_69f=_69e[1];
_69e=_69e[0];
}
if(_6a2 instanceof String||typeof _6a2=="string"){
_6a2=_6a2.split(",");
}
if(!isNaN(_6a0)){
_6a0=[Number(_6a0),Number(_6a0)];
}else{
if(!(_6a0 instanceof Array||typeof _6a0=="array")){
_6a0=[0,0];
}
}
var _6a4=dojo.html.getScroll().offset;
var view=dojo.html.getViewport();
node=dojo.byId(node);
var _6a6=node.style.display;
node.style.display="";
var bb=dojo.html.getBorderBox(node);
var w=bb.width;
var h=bb.height;
node.style.display=_6a6;
if(!(_6a2 instanceof Array||typeof _6a2=="array")){
_6a2=["TL"];
}
var _6aa,_6ab,_6ac=Infinity,_6ad;
for(var _6ae=0;_6ae<_6a2.length;++_6ae){
var _6af=_6a2[_6ae];
var _6b0=true;
var tryX=_69e-(_6af.charAt(1)=="L"?0:w)+_6a0[0]*(_6af.charAt(1)=="L"?1:-1);
var tryY=_69f-(_6af.charAt(0)=="T"?0:h)+_6a0[1]*(_6af.charAt(0)=="T"?1:-1);
if(_6a1){
tryX-=_6a4.x;
tryY-=_6a4.y;
}
if(tryX<0){
tryX=0;
_6b0=false;
}
if(tryY<0){
tryY=0;
_6b0=false;
}
var x=tryX+w;
if(x>view.width){
x=view.width-w;
_6b0=false;
}else{
x=tryX;
}
x=Math.max(_6a0[0],x)+_6a4.x;
var y=tryY+h;
if(y>view.height){
y=view.height-h;
_6b0=false;
}else{
y=tryY;
}
y=Math.max(_6a0[1],y)+_6a4.y;
if(_6b0){
_6aa=x;
_6ab=y;
_6ac=0;
_6ad=_6af;
break;
}else{
var dist=Math.pow(x-tryX-_6a4.x,2)+Math.pow(y-tryY-_6a4.y,2);
if(_6ac>dist){
_6ac=dist;
_6aa=x;
_6ab=y;
_6ad=_6af;
}
}
}
if(!_6a3){
node.style.left=_6aa+"px";
node.style.top=_6ab+"px";
}
return {left:_6aa,top:_6ab,x:_6aa,y:_6ab,dist:_6ac,corner:_6ad};
};
dojo.html.placeOnScreenPoint=function(node,_6b7,_6b8,_6b9,_6ba){
dojo.deprecated("dojo.html.placeOnScreenPoint","use dojo.html.placeOnScreen() instead","0.5");
return dojo.html.placeOnScreen(node,_6b7,_6b8,_6b9,_6ba,["TL","TR","BL","BR"]);
};
dojo.html.placeOnScreenAroundElement=function(node,_6bc,_6bd,_6be,_6bf,_6c0){
var best,_6c2=Infinity;
_6bc=dojo.byId(_6bc);
var _6c3=_6bc.style.display;
_6bc.style.display="";
var mb=dojo.html.getElementBox(_6bc,_6be);
var _6c5=mb.width;
var _6c6=mb.height;
var _6c7=dojo.html.getAbsolutePosition(_6bc,true,_6be);
_6bc.style.display=_6c3;
for(var _6c8 in _6bf){
var pos,_6ca,_6cb;
var _6cc=_6bf[_6c8];
_6ca=_6c7.x+(_6c8.charAt(1)=="L"?0:_6c5);
_6cb=_6c7.y+(_6c8.charAt(0)=="T"?0:_6c6);
pos=dojo.html.placeOnScreen(node,_6ca,_6cb,_6bd,true,_6cc,true);
if(pos.dist==0){
best=pos;
break;
}else{
if(_6c2>pos.dist){
_6c2=pos.dist;
best=pos;
}
}
}
if(!_6c0){
node.style.left=best.left+"px";
node.style.top=best.top+"px";
}
return best;
};
dojo.html.scrollIntoView=function(node){
if(!node){
return;
}
if(dojo.render.html.ie){
if(dojo.html.getBorderBox(node.parentNode).height<node.parentNode.scrollHeight){
node.scrollIntoView(false);
}
}else{
if(dojo.render.html.mozilla){
node.scrollIntoView(false);
}else{
var _6ce=node.parentNode;
var _6cf=_6ce.scrollTop+dojo.html.getBorderBox(_6ce).height;
var _6d0=node.offsetTop+dojo.html.getMarginBox(node).height;
if(_6cf<_6d0){
_6ce.scrollTop+=(_6d0-_6cf);
}else{
if(_6ce.scrollTop>node.offsetTop){
_6ce.scrollTop-=(_6ce.scrollTop-node.offsetTop);
}
}
}
}
};
dojo.provide("dojo.gfx.color");
dojo.gfx.color.Color=function(r,g,b,a){
if(dojo.lang.isArray(r)){
this.r=r[0];
this.g=r[1];
this.b=r[2];
this.a=r[3]||1;
}else{
if(dojo.lang.isString(r)){
var rgb=dojo.gfx.color.extractRGB(r);
this.r=rgb[0];
this.g=rgb[1];
this.b=rgb[2];
this.a=g||1;
}else{
if(r instanceof dojo.gfx.color.Color){
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
dojo.gfx.color.Color.fromArray=function(arr){
return new dojo.gfx.color.Color(arr[0],arr[1],arr[2],arr[3]);
};
dojo.extend(dojo.gfx.color.Color,{toRgb:function(_6d7){
if(_6d7){
return this.toRgba();
}else{
return [this.r,this.g,this.b];
}
},toRgba:function(){
return [this.r,this.g,this.b,this.a];
},toHex:function(){
return dojo.gfx.color.rgb2hex(this.toRgb());
},toCss:function(){
return "rgb("+this.toRgb().join()+")";
},toString:function(){
return this.toHex();
},blend:function(_6d8,_6d9){
var rgb=null;
if(dojo.lang.isArray(_6d8)){
rgb=_6d8;
}else{
if(_6d8 instanceof dojo.gfx.color.Color){
rgb=_6d8.toRgb();
}else{
rgb=new dojo.gfx.color.Color(_6d8).toRgb();
}
}
return dojo.gfx.color.blend(this.toRgb(),rgb,_6d9);
}});
dojo.gfx.color.named={white:[255,255,255],black:[0,0,0],red:[255,0,0],green:[0,255,0],lime:[0,255,0],blue:[0,0,255],navy:[0,0,128],gray:[128,128,128],silver:[192,192,192]};
dojo.gfx.color.blend=function(a,b,_6dd){
if(typeof a=="string"){
return dojo.gfx.color.blendHex(a,b,_6dd);
}
if(!_6dd){
_6dd=0;
}
_6dd=Math.min(Math.max(-1,_6dd),1);
_6dd=((_6dd+1)/2);
var c=[];
for(var x=0;x<3;x++){
c[x]=parseInt(b[x]+((a[x]-b[x])*_6dd));
}
return c;
};
dojo.gfx.color.blendHex=function(a,b,_6e2){
return dojo.gfx.color.rgb2hex(dojo.gfx.color.blend(dojo.gfx.color.hex2rgb(a),dojo.gfx.color.hex2rgb(b),_6e2));
};
dojo.gfx.color.extractRGB=function(_6e3){
var hex="0123456789abcdef";
_6e3=_6e3.toLowerCase();
if(_6e3.indexOf("rgb")==0){
var _6e5=_6e3.match(/rgba*\((\d+), *(\d+), *(\d+)/i);
var ret=_6e5.splice(1,3);
return ret;
}else{
var _6e7=dojo.gfx.color.hex2rgb(_6e3);
if(_6e7){
return _6e7;
}else{
return dojo.gfx.color.named[_6e3]||[255,255,255];
}
}
};
dojo.gfx.color.hex2rgb=function(hex){
var _6e9="0123456789ABCDEF";
var rgb=new Array(3);
if(hex.indexOf("#")==0){
hex=hex.substring(1);
}
hex=hex.toUpperCase();
if(hex.replace(new RegExp("["+_6e9+"]","g"),"")!=""){
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
rgb[i]=_6e9.indexOf(rgb[i].charAt(0))*16+_6e9.indexOf(rgb[i].charAt(1));
}
return rgb;
};
dojo.gfx.color.rgb2hex=function(r,g,b){
if(dojo.lang.isArray(r)){
g=r[1]||0;
b=r[2]||0;
r=r[0]||0;
}
var ret=dojo.lang.map([r,g,b],function(x){
x=new Number(x);
var s=x.toString(16);
while(s.length<2){
s="0"+s;
}
return s;
});
ret.unshift("#");
return ret.join("");
};
dojo.provide("dojo.lfx.Animation");
dojo.lfx.Line=function(_6f2,end){
this.start=_6f2;
this.end=end;
if(dojo.lang.isArray(_6f2)){
var diff=[];
dojo.lang.forEach(this.start,function(s,i){
diff[i]=this.end[i]-s;
},this);
this.getValue=function(n){
var res=[];
dojo.lang.forEach(this.start,function(s,i){
res[i]=(diff[i]*n)+s;
},this);
return res;
};
}else{
var diff=end-_6f2;
this.getValue=function(n){
return (diff*n)+this.start;
};
}
};
dojo.lfx.easeDefault=function(n){
if(dojo.render.html.khtml){
return (parseFloat("0.5")+((Math.sin((n+parseFloat("1.5"))*Math.PI))/2));
}else{
return (0.5+((Math.sin((n+1.5)*Math.PI))/2));
}
};
dojo.lfx.easeIn=function(n){
return Math.pow(n,3);
};
dojo.lfx.easeOut=function(n){
return (1-Math.pow(1-n,3));
};
dojo.lfx.easeInOut=function(n){
return ((3*Math.pow(n,2))-(2*Math.pow(n,3)));
};
dojo.lfx.IAnimation=function(){
};
dojo.lang.extend(dojo.lfx.IAnimation,{curve:null,duration:1000,easing:null,repeatCount:0,rate:25,handler:null,beforeBegin:null,onBegin:null,onAnimate:null,onEnd:null,onPlay:null,onPause:null,onStop:null,play:null,pause:null,stop:null,connect:function(evt,_701,_702){
if(!_702){
_702=_701;
_701=this;
}
_702=dojo.lang.hitch(_701,_702);
var _703=this[evt]||function(){
};
this[evt]=function(){
var ret=_703.apply(this,arguments);
_702.apply(this,arguments);
return ret;
};
return this;
},fire:function(evt,args){
if(this[evt]){
this[evt].apply(this,(args||[]));
}
return this;
},repeat:function(_707){
this.repeatCount=_707;
return this;
},_active:false,_paused:false});
dojo.lfx.Animation=function(_708,_709,_70a,_70b,_70c,rate){
dojo.lfx.IAnimation.call(this);
if(dojo.lang.isNumber(_708)||(!_708&&_709.getValue)){
rate=_70c;
_70c=_70b;
_70b=_70a;
_70a=_709;
_709=_708;
_708=null;
}else{
if(_708.getValue||dojo.lang.isArray(_708)){
rate=_70b;
_70c=_70a;
_70b=_709;
_70a=_708;
_709=null;
_708=null;
}
}
if(dojo.lang.isArray(_70a)){
this.curve=new dojo.lfx.Line(_70a[0],_70a[1]);
}else{
this.curve=_70a;
}
if(_709!=null&&_709>0){
this.duration=_709;
}
if(_70c){
this.repeatCount=_70c;
}
if(rate){
this.rate=rate;
}
if(_708){
dojo.lang.forEach(["handler","beforeBegin","onBegin","onEnd","onPlay","onStop","onAnimate"],function(item){
if(_708[item]){
this.connect(item,_708[item]);
}
},this);
}
if(_70b&&dojo.lang.isFunction(_70b)){
this.easing=_70b;
}
};
dojo.inherits(dojo.lfx.Animation,dojo.lfx.IAnimation);
dojo.lang.extend(dojo.lfx.Animation,{_startTime:null,_endTime:null,_timer:null,_percent:0,_startRepeatCount:0,play:function(_70f,_710){
if(_710){
clearTimeout(this._timer);
this._active=false;
this._paused=false;
this._percent=0;
}else{
if(this._active&&!this._paused){
return this;
}
}
this.fire("handler",["beforeBegin"]);
this.fire("beforeBegin");
if(_70f>0){
setTimeout(dojo.lang.hitch(this,function(){
this.play(null,_710);
}),_70f);
return this;
}
this._startTime=new Date().valueOf();
if(this._paused){
this._startTime-=(this.duration*this._percent/100);
}
this._endTime=this._startTime+this.duration;
this._active=true;
this._paused=false;
var step=this._percent/100;
var _712=this.curve.getValue(step);
if(this._percent==0){
if(!this._startRepeatCount){
this._startRepeatCount=this.repeatCount;
}
this.fire("handler",["begin",_712]);
this.fire("onBegin",[_712]);
}
this.fire("handler",["play",_712]);
this.fire("onPlay",[_712]);
this._cycle();
return this;
},pause:function(){
clearTimeout(this._timer);
if(!this._active){
return this;
}
this._paused=true;
var _713=this.curve.getValue(this._percent/100);
this.fire("handler",["pause",_713]);
this.fire("onPause",[_713]);
return this;
},gotoPercent:function(pct,_715){
clearTimeout(this._timer);
this._active=true;
this._paused=true;
this._percent=pct;
if(_715){
this.play();
}
return this;
},stop:function(_716){
clearTimeout(this._timer);
var step=this._percent/100;
if(_716){
step=1;
}
var _718=this.curve.getValue(step);
this.fire("handler",["stop",_718]);
this.fire("onStop",[_718]);
this._active=false;
this._paused=false;
return this;
},status:function(){
if(this._active){
return this._paused?"paused":"playing";
}else{
return "stopped";
}
return this;
},_cycle:function(){
clearTimeout(this._timer);
if(this._active){
var curr=new Date().valueOf();
var step=(curr-this._startTime)/(this._endTime-this._startTime);
if(step>=1){
step=1;
this._percent=100;
}else{
this._percent=step*100;
}
if((this.easing)&&(dojo.lang.isFunction(this.easing))){
step=this.easing(step);
}
var _71b=this.curve.getValue(step);
this.fire("handler",["animate",_71b]);
this.fire("onAnimate",[_71b]);
if(step<1){
this._timer=setTimeout(dojo.lang.hitch(this,"_cycle"),this.rate);
}else{
this._active=false;
this.fire("handler",["end"]);
this.fire("onEnd");
if(this.repeatCount>0){
this.repeatCount--;
this.play(null,true);
}else{
if(this.repeatCount==-1){
this.play(null,true);
}else{
if(this._startRepeatCount){
this.repeatCount=this._startRepeatCount;
this._startRepeatCount=0;
}
}
}
}
}
return this;
}});
dojo.lfx.Combine=function(_71c){
dojo.lfx.IAnimation.call(this);
this._anims=[];
this._animsEnded=0;
var _71d=arguments;
if(_71d.length==1&&(dojo.lang.isArray(_71d[0])||dojo.lang.isArrayLike(_71d[0]))){
_71d=_71d[0];
}
dojo.lang.forEach(_71d,function(anim){
this._anims.push(anim);
anim.connect("onEnd",dojo.lang.hitch(this,"_onAnimsEnded"));
},this);
};
dojo.inherits(dojo.lfx.Combine,dojo.lfx.IAnimation);
dojo.lang.extend(dojo.lfx.Combine,{_animsEnded:0,play:function(_71f,_720){
if(!this._anims.length){
return this;
}
this.fire("beforeBegin");
if(_71f>0){
setTimeout(dojo.lang.hitch(this,function(){
this.play(null,_720);
}),_71f);
return this;
}
if(_720||this._anims[0].percent==0){
this.fire("onBegin");
}
this.fire("onPlay");
this._animsCall("play",null,_720);
return this;
},pause:function(){
this.fire("onPause");
this._animsCall("pause");
return this;
},stop:function(_721){
this.fire("onStop");
this._animsCall("stop",_721);
return this;
},_onAnimsEnded:function(){
this._animsEnded++;
if(this._animsEnded>=this._anims.length){
this.fire("onEnd");
}
return this;
},_animsCall:function(_722){
var args=[];
if(arguments.length>1){
for(var i=1;i<arguments.length;i++){
args.push(arguments[i]);
}
}
var _725=this;
dojo.lang.forEach(this._anims,function(anim){
anim[_722](args);
},_725);
return this;
}});
dojo.lfx.Chain=function(_727){
dojo.lfx.IAnimation.call(this);
this._anims=[];
this._currAnim=-1;
var _728=arguments;
if(_728.length==1&&(dojo.lang.isArray(_728[0])||dojo.lang.isArrayLike(_728[0]))){
_728=_728[0];
}
var _729=this;
dojo.lang.forEach(_728,function(anim,i,_72c){
this._anims.push(anim);
if(i<_72c.length-1){
anim.connect("onEnd",dojo.lang.hitch(this,"_playNext"));
}else{
anim.connect("onEnd",dojo.lang.hitch(this,function(){
this.fire("onEnd");
}));
}
},this);
};
dojo.inherits(dojo.lfx.Chain,dojo.lfx.IAnimation);
dojo.lang.extend(dojo.lfx.Chain,{_currAnim:-1,play:function(_72d,_72e){
if(!this._anims.length){
return this;
}
if(_72e||!this._anims[this._currAnim]){
this._currAnim=0;
}
var _72f=this._anims[this._currAnim];
this.fire("beforeBegin");
if(_72d>0){
setTimeout(dojo.lang.hitch(this,function(){
this.play(null,_72e);
}),_72d);
return this;
}
if(_72f){
if(this._currAnim==0){
this.fire("handler",["begin",this._currAnim]);
this.fire("onBegin",[this._currAnim]);
}
this.fire("onPlay",[this._currAnim]);
_72f.play(null,_72e);
}
return this;
},pause:function(){
if(this._anims[this._currAnim]){
this._anims[this._currAnim].pause();
this.fire("onPause",[this._currAnim]);
}
return this;
},playPause:function(){
if(this._anims.length==0){
return this;
}
if(this._currAnim==-1){
this._currAnim=0;
}
var _730=this._anims[this._currAnim];
if(_730){
if(!_730._active||_730._paused){
this.play();
}else{
this.pause();
}
}
return this;
},stop:function(){
var _731=this._anims[this._currAnim];
if(_731){
_731.stop();
this.fire("onStop",[this._currAnim]);
}
return _731;
},_playNext:function(){
if(this._currAnim==-1||this._anims.length==0){
return this;
}
this._currAnim++;
if(this._anims[this._currAnim]){
this._anims[this._currAnim].play(null,true);
}
return this;
}});
dojo.lfx.combine=function(_732){
var _733=arguments;
if(dojo.lang.isArray(arguments[0])){
_733=arguments[0];
}
if(_733.length==1){
return _733[0];
}
return new dojo.lfx.Combine(_733);
};
dojo.lfx.chain=function(_734){
var _735=arguments;
if(dojo.lang.isArray(arguments[0])){
_735=arguments[0];
}
if(_735.length==1){
return _735[0];
}
return new dojo.lfx.Chain(_735);
};
dojo.provide("dojo.html.color");
dojo.html.getBackgroundColor=function(node){
node=dojo.byId(node);
var _737;
do{
_737=dojo.html.getStyle(node,"background-color");
if(_737.toLowerCase()=="rgba(0, 0, 0, 0)"){
_737="transparent";
}
if(node==document.getElementsByTagName("body")[0]){
node=null;
break;
}
node=node.parentNode;
}while(node&&dojo.lang.inArray(["transparent",""],_737));
if(_737=="transparent"){
_737=[255,255,255,0];
}else{
_737=dojo.gfx.color.extractRGB(_737);
}
return _737;
};
dojo.provide("dojo.lfx.html");
dojo.lfx.html._byId=function(_738){
if(!_738){
return [];
}
if(dojo.lang.isArrayLike(_738)){
if(!_738.alreadyChecked){
var n=[];
dojo.lang.forEach(_738,function(node){
n.push(dojo.byId(node));
});
n.alreadyChecked=true;
return n;
}else{
return _738;
}
}else{
var n=[];
n.push(dojo.byId(_738));
n.alreadyChecked=true;
return n;
}
};
dojo.lfx.html.propertyAnimation=function(_73b,_73c,_73d,_73e,_73f){
_73b=dojo.lfx.html._byId(_73b);
var _740={"propertyMap":_73c,"nodes":_73b,"duration":_73d,"easing":_73e||dojo.lfx.easeDefault};
var _741=function(args){
if(args.nodes.length==1){
var pm=args.propertyMap;
if(!dojo.lang.isArray(args.propertyMap)){
var parr=[];
for(var _745 in pm){
pm[_745].property=_745;
parr.push(pm[_745]);
}
pm=args.propertyMap=parr;
}
dojo.lang.forEach(pm,function(prop){
if(dj_undef("start",prop)){
if(prop.property!="opacity"){
prop.start=parseInt(dojo.html.getComputedStyle(args.nodes[0],prop.property));
}else{
prop.start=dojo.html.getOpacity(args.nodes[0]);
}
}
});
}
};
var _747=function(_748){
var _749=[];
dojo.lang.forEach(_748,function(c){
_749.push(Math.round(c));
});
return _749;
};
var _74b=function(n,_74d){
n=dojo.byId(n);
if(!n||!n.style){
return;
}
for(var s in _74d){
if(s=="opacity"){
dojo.html.setOpacity(n,_74d[s]);
}else{
n.style[s]=_74d[s];
}
}
};
var _74f=function(_750){
this._properties=_750;
this.diffs=new Array(_750.length);
dojo.lang.forEach(_750,function(prop,i){
if(dojo.lang.isFunction(prop.start)){
prop.start=prop.start(prop,i);
}
if(dojo.lang.isFunction(prop.end)){
prop.end=prop.end(prop,i);
}
if(dojo.lang.isArray(prop.start)){
this.diffs[i]=null;
}else{
if(prop.start instanceof dojo.gfx.color.Color){
prop.startRgb=prop.start.toRgb();
prop.endRgb=prop.end.toRgb();
}else{
this.diffs[i]=prop.end-prop.start;
}
}
},this);
this.getValue=function(n){
var ret={};
dojo.lang.forEach(this._properties,function(prop,i){
var _757=null;
if(dojo.lang.isArray(prop.start)){
}else{
if(prop.start instanceof dojo.gfx.color.Color){
_757=(prop.units||"rgb")+"(";
for(var j=0;j<prop.startRgb.length;j++){
_757+=Math.round(((prop.endRgb[j]-prop.startRgb[j])*n)+prop.startRgb[j])+(j<prop.startRgb.length-1?",":"");
}
_757+=")";
}else{
_757=((this.diffs[i])*n)+prop.start+(prop.property!="opacity"?prop.units||"px":"");
}
}
ret[dojo.html.toCamelCase(prop.property)]=_757;
},this);
return ret;
};
};
var anim=new dojo.lfx.Animation({beforeBegin:function(){
_741(_740);
anim.curve=new _74f(_740.propertyMap);
},onAnimate:function(_75a){
dojo.lang.forEach(_740.nodes,function(node){
_74b(node,_75a);
});
}},_740.duration,null,_740.easing);
if(_73f){
for(var x in _73f){
if(dojo.lang.isFunction(_73f[x])){
anim.connect(x,anim,_73f[x]);
}
}
}
return anim;
};
dojo.lfx.html._makeFadeable=function(_75d){
var _75e=function(node){
if(dojo.render.html.ie){
if((node.style.zoom.length==0)&&(dojo.html.getStyle(node,"zoom")=="normal")){
node.style.zoom="1";
}
if((node.style.width.length==0)&&(dojo.html.getStyle(node,"width")=="auto")){
node.style.width="auto";
}
}
};
if(dojo.lang.isArrayLike(_75d)){
dojo.lang.forEach(_75d,_75e);
}else{
_75e(_75d);
}
};
dojo.lfx.html.fade=function(_760,_761,_762,_763,_764){
_760=dojo.lfx.html._byId(_760);
var _765={property:"opacity"};
if(!dj_undef("start",_761)){
_765.start=_761.start;
}else{
_765.start=function(){
return dojo.html.getOpacity(_760[0]);
};
}
if(!dj_undef("end",_761)){
_765.end=_761.end;
}else{
dojo.raise("dojo.lfx.html.fade needs an end value");
}
var anim=dojo.lfx.propertyAnimation(_760,[_765],_762,_763);
anim.connect("beforeBegin",function(){
dojo.lfx.html._makeFadeable(_760);
});
if(_764){
anim.connect("onEnd",function(){
_764(_760,anim);
});
}
return anim;
};
dojo.lfx.html.fadeIn=function(_767,_768,_769,_76a){
return dojo.lfx.html.fade(_767,{end:1},_768,_769,_76a);
};
dojo.lfx.html.fadeOut=function(_76b,_76c,_76d,_76e){
return dojo.lfx.html.fade(_76b,{end:0},_76c,_76d,_76e);
};
dojo.lfx.html.fadeShow=function(_76f,_770,_771,_772){
_76f=dojo.lfx.html._byId(_76f);
dojo.lang.forEach(_76f,function(node){
dojo.html.setOpacity(node,0);
});
var anim=dojo.lfx.html.fadeIn(_76f,_770,_771,_772);
anim.connect("beforeBegin",function(){
if(dojo.lang.isArrayLike(_76f)){
dojo.lang.forEach(_76f,dojo.html.show);
}else{
dojo.html.show(_76f);
}
});
return anim;
};
dojo.lfx.html.fadeHide=function(_775,_776,_777,_778){
var anim=dojo.lfx.html.fadeOut(_775,_776,_777,function(){
if(dojo.lang.isArrayLike(_775)){
dojo.lang.forEach(_775,dojo.html.hide);
}else{
dojo.html.hide(_775);
}
if(_778){
_778(_775,anim);
}
});
return anim;
};
dojo.lfx.html.wipeIn=function(_77a,_77b,_77c,_77d){
_77a=dojo.lfx.html._byId(_77a);
var _77e=[];
dojo.lang.forEach(_77a,function(node){
var _780={overflow:null};
node.style.visibility="hidden";
node.style.display="block";
var anim=dojo.lfx.propertyAnimation(node,{"height":{start:1,end:function(){
return node.scrollHeight;
}}},_77b,_77c);
anim.connect("beforeBegin",function(){
_780.overflow=dojo.html.getStyle(node,"overflow");
with(node.style){
if(_780.overflow=="visible"){
overflow="hidden";
}
visibility="visible";
height="1px";
}
dojo.html.show(node);
});
anim.connect("onEnd",function(){
with(node.style){
overflow=_780.overflow;
height="";
visibility="visible";
}
if(_77d){
_77d(node,anim);
}
});
_77e.push(anim);
});
return dojo.lfx.combine(_77e);
};
dojo.lfx.html.wipeOut=function(_782,_783,_784,_785){
_782=dojo.lfx.html._byId(_782);
var _786=[];
dojo.lang.forEach(_782,function(node){
var _788={overflow:null};
var anim=dojo.lfx.propertyAnimation(node,{"height":{start:function(){
return dojo.html.getContentBox(node).height;
},end:1}},_783,_784,{"beforeBegin":function(){
_788.overflow=dojo.html.getStyle(node,"overflow");
if(_788.overflow=="visible"){
node.style.overflow="hidden";
}
node.style.visibility="visible";
dojo.html.show(node);
},"onEnd":function(){
with(node.style){
overflow=_788.overflow;
visibility="hidden";
height="1px";
}
if(_785){
_785(node,anim);
}
}});
_786.push(anim);
});
return dojo.lfx.combine(_786);
};
dojo.lfx.html.slideTo=function(_78a,_78b,_78c,_78d,_78e){
_78a=dojo.lfx.html._byId(_78a);
var _78f=[];
var _790=dojo.html.getComputedStyle;
if(dojo.lang.isArray(_78b)){
dojo.deprecated("dojo.lfx.html.slideTo(node, array)","use dojo.lfx.html.slideTo(node, {top: value, left: value});","0.5");
_78b={top:_78b[0],left:_78b[1]};
}
dojo.lang.forEach(_78a,function(node){
var top=null;
var left=null;
var init=(function(){
var _795=node;
return function(){
var pos=_790(_795,"position");
top=(pos=="absolute"?node.offsetTop:parseInt(_790(node,"top"))||0);
left=(pos=="absolute"?node.offsetLeft:parseInt(_790(node,"left"))||0);
if(!dojo.lang.inArray(["absolute","relative"],pos)){
var ret=dojo.html.abs(_795,true);
dojo.html.setStyleAttributes(_795,"position:absolute;top:"+ret.y+"px;left:"+ret.x+"px;");
top=ret.y;
left=ret.x;
}
};
})();
init();
var anim=dojo.lfx.propertyAnimation(node,{"top":{start:top,end:(_78b.top||0)},"left":{start:left,end:(_78b.left||0)}},_78c,_78d,{"beforeBegin":init});
if(_78e){
anim.connect("onEnd",function(){
_78e(_78a,anim);
});
}
_78f.push(anim);
});
return dojo.lfx.combine(_78f);
};
dojo.lfx.html.slideBy=function(_799,_79a,_79b,_79c,_79d){
_799=dojo.lfx.html._byId(_799);
var _79e=[];
var _79f=dojo.html.getComputedStyle;
if(dojo.lang.isArray(_79a)){
dojo.deprecated("dojo.lfx.html.slideBy(node, array)","use dojo.lfx.html.slideBy(node, {top: value, left: value});","0.5");
_79a={top:_79a[0],left:_79a[1]};
}
dojo.lang.forEach(_799,function(node){
var top=null;
var left=null;
var init=(function(){
var _7a4=node;
return function(){
var pos=_79f(_7a4,"position");
top=(pos=="absolute"?node.offsetTop:parseInt(_79f(node,"top"))||0);
left=(pos=="absolute"?node.offsetLeft:parseInt(_79f(node,"left"))||0);
if(!dojo.lang.inArray(["absolute","relative"],pos)){
var ret=dojo.html.abs(_7a4,true);
dojo.html.setStyleAttributes(_7a4,"position:absolute;top:"+ret.y+"px;left:"+ret.x+"px;");
top=ret.y;
left=ret.x;
}
};
})();
init();
var anim=dojo.lfx.propertyAnimation(node,{"top":{start:top,end:top+(_79a.top||0)},"left":{start:left,end:left+(_79a.left||0)}},_79b,_79c).connect("beforeBegin",init);
if(_79d){
anim.connect("onEnd",function(){
_79d(_799,anim);
});
}
_79e.push(anim);
});
return dojo.lfx.combine(_79e);
};
dojo.lfx.html.explode=function(_7a8,_7a9,_7aa,_7ab,_7ac){
var h=dojo.html;
_7a8=dojo.byId(_7a8);
_7a9=dojo.byId(_7a9);
var _7ae=h.toCoordinateObject(_7a8,true);
var _7af=document.createElement("div");
h.copyStyle(_7af,_7a9);
if(_7a9.explodeClassName){
_7af.className=_7a9.explodeClassName;
}
with(_7af.style){
position="absolute";
display="none";
}
dojo.body().appendChild(_7af);
with(_7a9.style){
visibility="hidden";
display="block";
}
var _7b0=h.toCoordinateObject(_7a9,true);
with(_7a9.style){
display="none";
visibility="visible";
}
var _7b1={opacity:{start:0.5,end:1}};
dojo.lang.forEach(["height","width","top","left"],function(type){
_7b1[type]={start:_7ae[type],end:_7b0[type]};
});
var anim=new dojo.lfx.propertyAnimation(_7af,_7b1,_7aa,_7ab,{"beforeBegin":function(){
h.setDisplay(_7af,"block");
},"onEnd":function(){
h.setDisplay(_7a9,"block");
_7af.parentNode.removeChild(_7af);
}});
if(_7ac){
anim.connect("onEnd",function(){
_7ac(_7a9,anim);
});
}
return anim;
};
dojo.lfx.html.implode=function(_7b4,end,_7b6,_7b7,_7b8){
var h=dojo.html;
_7b4=dojo.byId(_7b4);
end=dojo.byId(end);
var _7ba=dojo.html.toCoordinateObject(_7b4,true);
var _7bb=dojo.html.toCoordinateObject(end,true);
var _7bc=document.createElement("div");
dojo.html.copyStyle(_7bc,_7b4);
if(_7b4.explodeClassName){
_7bc.className=_7b4.explodeClassName;
}
dojo.html.setOpacity(_7bc,0.3);
with(_7bc.style){
position="absolute";
display="none";
backgroundColor=h.getStyle(_7b4,"background-color").toLowerCase();
}
dojo.body().appendChild(_7bc);
var _7bd={opacity:{start:1,end:0.5}};
dojo.lang.forEach(["height","width","top","left"],function(type){
_7bd[type]={start:_7ba[type],end:_7bb[type]};
});
var anim=new dojo.lfx.propertyAnimation(_7bc,_7bd,_7b6,_7b7,{"beforeBegin":function(){
dojo.html.hide(_7b4);
dojo.html.show(_7bc);
},"onEnd":function(){
_7bc.parentNode.removeChild(_7bc);
}});
if(_7b8){
anim.connect("onEnd",function(){
_7b8(_7b4,anim);
});
}
return anim;
};
dojo.lfx.html.highlight=function(_7c0,_7c1,_7c2,_7c3,_7c4){
_7c0=dojo.lfx.html._byId(_7c0);
var _7c5=[];
dojo.lang.forEach(_7c0,function(node){
var _7c7=dojo.html.getBackgroundColor(node);
var bg=dojo.html.getStyle(node,"background-color").toLowerCase();
var _7c9=dojo.html.getStyle(node,"background-image");
var _7ca=(bg=="transparent"||bg=="rgba(0, 0, 0, 0)");
while(_7c7.length>3){
_7c7.pop();
}
var rgb=new dojo.gfx.color.Color(_7c1);
var _7cc=new dojo.gfx.color.Color(_7c7);
var anim=dojo.lfx.propertyAnimation(node,{"background-color":{start:rgb,end:_7cc}},_7c2,_7c3,{"beforeBegin":function(){
if(_7c9){
node.style.backgroundImage="none";
}
node.style.backgroundColor="rgb("+rgb.toRgb().join(",")+")";
},"onEnd":function(){
if(_7c9){
node.style.backgroundImage=_7c9;
}
if(_7ca){
node.style.backgroundColor="transparent";
}
if(_7c4){
_7c4(node,anim);
}
}});
_7c5.push(anim);
});
return dojo.lfx.combine(_7c5);
};
dojo.lfx.html.unhighlight=function(_7ce,_7cf,_7d0,_7d1,_7d2){
_7ce=dojo.lfx.html._byId(_7ce);
var _7d3=[];
dojo.lang.forEach(_7ce,function(node){
var _7d5=new dojo.gfx.color.Color(dojo.html.getBackgroundColor(node));
var rgb=new dojo.gfx.color.Color(_7cf);
var _7d7=dojo.html.getStyle(node,"background-image");
var anim=dojo.lfx.propertyAnimation(node,{"background-color":{start:_7d5,end:rgb}},_7d0,_7d1,{"beforeBegin":function(){
if(_7d7){
node.style.backgroundImage="none";
}
node.style.backgroundColor="rgb("+_7d5.toRgb().join(",")+")";
},"onEnd":function(){
if(_7d2){
_7d2(node,anim);
}
}});
_7d3.push(anim);
});
return dojo.lfx.combine(_7d3);
};
dojo.lang.mixin(dojo.lfx,dojo.lfx.html);
dojo.provide("dojo.lfx.*");
dojo.provide("dojo.lfx.toggle");
dojo.lfx.toggle.plain={show:function(node,_7da,_7db,_7dc){
dojo.html.show(node);
if(dojo.lang.isFunction(_7dc)){
_7dc();
}
},hide:function(node,_7de,_7df,_7e0){
dojo.html.hide(node);
if(dojo.lang.isFunction(_7e0)){
_7e0();
}
}};
dojo.lfx.toggle.fade={show:function(node,_7e2,_7e3,_7e4){
dojo.lfx.fadeShow(node,_7e2,_7e3,_7e4).play();
},hide:function(node,_7e6,_7e7,_7e8){
dojo.lfx.fadeHide(node,_7e6,_7e7,_7e8).play();
}};
dojo.lfx.toggle.wipe={show:function(node,_7ea,_7eb,_7ec){
dojo.lfx.wipeIn(node,_7ea,_7eb,_7ec).play();
},hide:function(node,_7ee,_7ef,_7f0){
dojo.lfx.wipeOut(node,_7ee,_7ef,_7f0).play();
}};
dojo.lfx.toggle.explode={show:function(node,_7f2,_7f3,_7f4,_7f5){
dojo.lfx.explode(_7f5||{x:0,y:0,width:0,height:0},node,_7f2,_7f3,_7f4).play();
},hide:function(node,_7f7,_7f8,_7f9,_7fa){
dojo.lfx.implode(node,_7fa||{x:0,y:0,width:0,height:0},_7f7,_7f8,_7f9).play();
}};
dojo.provide("dojo.widget.HtmlWidget");
dojo.declare("dojo.widget.HtmlWidget",dojo.widget.DomWidget,{widgetType:"HtmlWidget",templateCssPath:null,templatePath:null,lang:"",toggle:"plain",toggleDuration:150,animationInProgress:false,initialize:function(args,frag){
},postMixInProperties:function(args,frag){
if(this.lang===""){
this.lang=null;
}
this.toggleObj=dojo.lfx.toggle[this.toggle.toLowerCase()]||dojo.lfx.toggle.plain;
},getContainerHeight:function(){
dojo.unimplemented("dojo.widget.HtmlWidget.getContainerHeight");
},getContainerWidth:function(){
return this.parent.domNode.offsetWidth;
},setNativeHeight:function(_7ff){
var ch=this.getContainerHeight();
},createNodesFromText:function(txt,wrap){
return dojo.html.createNodesFromText(txt,wrap);
},destroyRendering:function(_803){
try{
if(!_803&&this.domNode){
dojo.event.browser.clean(this.domNode);
}
this.domNode.parentNode.removeChild(this.domNode);
delete this.domNode;
}
catch(e){
}
},isShowing:function(){
return dojo.html.isShowing(this.domNode);
},toggleShowing:function(){
if(this.isHidden){
this.show();
}else{
this.hide();
}
},show:function(){
this.animationInProgress=true;
this.isHidden=false;
this.toggleObj.show(this.domNode,this.toggleDuration,null,dojo.lang.hitch(this,this.onShow),this.explodeSrc);
},onShow:function(){
this.animationInProgress=false;
this.checkSize();
},hide:function(){
this.animationInProgress=true;
this.isHidden=true;
this.toggleObj.hide(this.domNode,this.toggleDuration,null,dojo.lang.hitch(this,this.onHide),this.explodeSrc);
},onHide:function(){
this.animationInProgress=false;
},_isResized:function(w,h){
if(!this.isShowing()){
return false;
}
var wh=dojo.html.getMarginBox(this.domNode);
var _807=w||wh.width;
var _808=h||wh.height;
if(this.width==_807&&this.height==_808){
return false;
}
this.width=_807;
this.height=_808;
return true;
},checkSize:function(){
if(!this._isResized()){
return;
}
this.onResized();
},resizeTo:function(w,h){
dojo.html.setMarginBox(this.domNode,{width:w,height:h});
if(this.isShowing()){
this.onResized();
}
},resizeSoon:function(){
if(this.isShowing()){
dojo.lang.setTimeout(this,this.onResized,0);
}
},onResized:function(){
dojo.lang.forEach(this.children,function(_80b){
if(_80b.checkSize){
_80b.checkSize();
}
});
}});
dojo.provide("dojo.widget.*");

