package javax.faces.module;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.plugin.PluginManager;

@ApplicationScoped
@ManagedBean(eager=true)
public class ModuleManager extends PluginManager<Module> {
	
	public ModuleManager() {
		
		super(new ModuleLoader());
		
	}
}
