package javax.faces.plugin;

import javax.faces.application.Resource;

public interface ResourceResolver {

	public Resource createResource(String resourceName, String libraryName);
	
}
