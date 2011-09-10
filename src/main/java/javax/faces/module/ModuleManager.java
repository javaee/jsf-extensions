package javax.faces.module;

import java.util.List;
import javax.faces.application.Resource;
import javax.faces.plugin.Manage;
import javax.faces.plugin.PluginManager;


@SuppressWarnings("serial")
@Manage(folder="modules",metadata="module.xml")
public final class ModuleManager extends PluginManager<Module> {
	
	
	public ModuleManager() {
		
		super(new ModuleLoader());

	}

	@Override
	public Resource resolveResource(String resourceName, String library,String contentType) {
		return null;
	}
	
	public List<Module> getModules() {
		
		return plugins;
		
	}

	public Module getModule(String id) {
		
		return getPlugin(id);
		
	}
	
}
