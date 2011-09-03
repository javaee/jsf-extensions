package javax.faces.plugin;


public interface PluginLoader<T extends Plugin> {

	
	public T load(Document metadata) throws Exception;
	
	
}
