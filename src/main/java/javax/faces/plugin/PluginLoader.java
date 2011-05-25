package javax.faces.plugin;

import javax.faces.application.Resource;


public interface PluginLoader<T extends Plugin> {

	
	public T load(Resource metadata) throws Exception;
	
	
}
