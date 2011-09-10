package javax.faces.plugin;

import javax.faces.application.Resource;

public interface ResourceResolver {

	public Resource resolveResource(String resourceName, String library,String contentType);
	
}
