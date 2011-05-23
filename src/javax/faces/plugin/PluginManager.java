package javax.faces.plugin;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.Resource;


public abstract class PluginManager<T extends Plugin>  {

	protected  final List<T> 	plugins;
	protected  PluginLoader<T>  loader;
	
	
	public PluginManager() {
		
		plugins=new ArrayList<T>();
		
	}
	
	public PluginManager(PluginLoader<T> loader) {
	
			this();
			this.loader=loader;							
	}

	public void load() throws Exception {
		
	}
	
	public T load(Resource metadata) throws Exception{
		
			T plugin=loader.load(metadata);
			plugin.setMetadata(metadata);
			return plugin;
			
	}
	
	public void add(T plugin) {
		
			plugins.add(plugin);
			
	}
		
	public void remove(T plugin)  {
		
			plugins.remove(plugin);
	}
	
	public List<T> getPlugins() {
		
			return plugins;
	}

	public PluginLoader<T> getLoader() {
		return loader;
	}

	public void setLoader(PluginLoader<T> loader) {
		this.loader = loader;
	}

}