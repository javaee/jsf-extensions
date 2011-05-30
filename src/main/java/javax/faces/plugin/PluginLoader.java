package javax.faces.plugin;

import java.io.InputStream;


public interface PluginLoader<T extends Plugin> {

	
	public T load(InputStream metadata) throws Exception;
	
	
}
