package javax.faces.template;

import javax.faces.application.Resource;

public interface ResourceResolver {

	public Resource createResource(String resourceName, String libraryName);
	
}
