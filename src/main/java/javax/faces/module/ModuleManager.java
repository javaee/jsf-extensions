package javax.faces.module;

import javax.faces.plugin.Manage;
import javax.faces.plugin.PluginManager;



@Manage(folder="modules",metadata="module.xml")
public class ModuleManager extends PluginManager<Module> {
	
	public ModuleManager() {
		
		super(new ModuleLoader());

	}
}
