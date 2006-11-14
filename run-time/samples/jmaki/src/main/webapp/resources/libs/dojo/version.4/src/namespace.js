/*
	Copyright (c) 2004-2006, The Dojo Foundation
	All Rights Reserved.

	Licensed under the Academic Free License version 2.1 or above OR the
	modified BSD license. For more information on Dojo licensing, see:

		http://dojotoolkit.org/community/licensing.shtml
*/

dojo.provide("dojo.namespace");

//Q: which, if any, can be marked _private? how about .dojo, .namespaces, .failed, .loading, .loaded?
//Q: under what circumstances is module an array?  pls add to docs
dojo["namespace"] = {
	dojo: "dojo",
	namespaces: {},
	failed: {},
	loading: {},
	loaded: {},
	register: function(/*String*/name, /*String*/module, /*Function?*/resolver, /*Boolean?*/noOverride){
		// summary: creates and registers a dojo.namespace.Namespace object
		if(!noOverride || !this.namespaces[name]){
			this.namespaces[name] = new dojo["namespace"].Namespace(name, module, resolver);
		}
	},
	allow: function(/*String*/name){
		// summary: Returns false if 'name' is filtered by configuration or has failed to load, true otherwise

		if(this.failed[name]){return false;} // Boolean

		var excl = djConfig.excludeNamespace;
		if(excl && dojo.lang.inArray(excl, name)){return false;} // Boolean

		// If the namespace is "dojo", or the user has not specified allowed namespaces return true.
		// Otherwise, if the user has specifically allowed this namespace, return true, otherwise false.
		var incl = djConfig.includeNamespace;
		return((name==this.dojo) || !incl || dojo.lang.inArray(incl, name)); // Boolean
	},
	get: function(/*String*/name){
		// summary
		//  Return Namespace object registered to 'name', if any
		return this.namespaces[name]; // Namespace
	},
	require: function(/*String*/name){
		// summary
	  	//  Try to ensure that 'name' is registered by loading a namespace manifest
		var ns = this.namespaces[name];
		if(ns && this.loaded[name]){return ns;} // Namespace
		if(!this.allow(name)){return false;} // Boolean
 		if(this.loading[name]){
			// FIXME: do we really ever have re-entrancy situation? this would appear to be really bad
			// original code did not throw an exception, although that seems the only course
			// adding debug output here to track if this occurs.
			dojo.debug('dojo.namespace.require: re-entrant request to load namespace "' + name + '" must fail.'); 
			return false; // Boolean
		}
		// workaround so we don't break the build system
		var req = dojo.require;
		this.loading[name] = true;
		try {
			//dojo namespace file is always in the Dojo namespace folder, not a custom namespace folder
			if(name==this.dojo){
				req("dojo.namespaces.dojo");
			}else{
				// if no registered module prefix, use ../<name> by convention
				if(!dojo.hostenv.moduleHasPrefix(name)){
					dojo.registerModulePath(name, "../" + name);
				}
				req([name, 'manifest'].join('.'), false, true);
			}
			if(!this.namespaces[name]){
				this.failed[name] = true; //only look for a namespace once
			}
		}finally{
			this.loading[name]=false;
		}
		return this.namespaces[name]; // Namespace
	}
}

dojo.registerNamespace = function(/*String*/name, /*String*/module, /*Function?*/resolver){
	// summary: maps a module name to a namespace for widgets, and optionally maps widget names to modules for auto-loading
	// description: An unregistered namespace is mapped to an eponymous module.
	//	For example, namespace acme is mapped to module acme, and widgets are
	//	assumed to belong to acme.widget. If you want to use a different widget
	//	module, use dojo.registerNamespace.
	dojo["namespace"].register.apply(dojo["namespace"], arguments);
}

dojo.registerNamespaceResolver = function(/*String*/name, /*Function*/resolver){
	// summary: a resolver function maps widget names to modules, so the
	//	widget manager can auto-load needed widget implementations
	//
	// description: The resolver provides information to allow Dojo
	//	to load widget modules on demand. When a widget is created,
	//	a namespace resolver can tell Dojo what module to require
	//	to ensure that the widget implementation code is loaded.
	//
	// name: will always be lower-case.
	//
	// example:
	//  dojo.registerNamespaceResolver("acme",
	//    function(name){ 
	//      return "acme.widget."+dojo.string.capitalize(name);
	//    }
	//  );
	var n = dojo["namespace"].namespaces[name];
	if(n){
		n.resolver = resolver;
	}
}

dojo.registerNamespaceManifest = function(/*String*/module, /*String*/path, /*String*/name, /*String*/widgetModule, /*Function?*/resolver){
	// summary:
	dojo.registerModulePath(name, path);
	dojo.registerNamespace(name, widgetModule, resolver);
}

//Q: can we just remove this function? Was it in 0.3.1?
dojo.defineNamespace = function(objRoot, /*String*/location, /*String*/nsPrefix, /*Function?*/resolver, /*Object?*/widgetPackage){
	dojo.deprecated("dojo.defineNamespace", " is replaced by other systems. See the Dojo Wiki [http://dojo.jot.com/WikiHome/Modules & Namespaces].", "0.5");
	dojo.registerNamespaceManifest(objRoot, location, nsPrefix, widgetPackage, resolver);
}

dojo["namespace"].Namespace = function(/*String*/name, module, /*Function?*/resolver){
	// summary: this object simply encapsulates Namespace data
	this.name = name;
	this.module = module;
	this.resolver = resolver;
}

dojo["namespace"].Namespace.prototype._loaded = {};
dojo["namespace"].Namespace.prototype._failed = {};

dojo["namespace"].Namespace.prototype.resolve = function(/*String*/name, /*String*/domain, /*Boolean?*/omitModuleCheck){
	//summary: map component with 'name' and 'domain' to a module via namespace resolver, if specified

	if(!this.resolver){return false;} // Boolean
	var fullName = this.resolver(name,domain);
	//only load a widget once. This is a quicker check than dojo.require does
	if(fullName && !this._loaded[fullName] && !this._failed[fullName]){
		//workaround so we don't break the build system
		var req = dojo.require;
		req(fullName, false, true); //omit the module check, we'll do it ourselves.
		if(dojo.hostenv.findModule(fullName, false)){
			this._loaded[fullName] = true;
		}else{
			if(!omit_module_check){dojo.raise("dojo.namespace.Namespace.resolve: module '" + fullName + "' not found after loading via namespace '" + this.name + "'");} 
			this._failed[fullName] = true;
		}
	}
	return Boolean(this._loaded[fullName]); // Boolean
}

dojo["namespace"].Namespace.prototype.getModule = function(/*String*/widgetName){
	// summary:

	if(!this.module){return null;} // null
	if(!dojo.lang.isArray(this.module)){ return this.module; } // String
	if(this.module.length <= 0){ return null; } // null
	if(!this.resolver){return this.module[0];}
	
	var fullName = this.resolver(widgetName);
	if(!fullName){ return this.module[0]; }
	
	var modpos=fullName.lastIndexOf(".");
	return (modpos > -1) ? fullName.substr(0, modpos) : this.module[0]; // String
}

// NOTE: rather put this in dojo.widget.Widget, but that fubars debugAtAllCosts
dojo.registerNamespace("dojo", ["dojo.widget","dojo.widget.validate"]);