package javax.faces.template;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;


public class ResourceHandlerImpl extends ResourceHandlerWrapper{
	
	private ResourceHandler wrapped;	
	
	public ResourceHandlerImpl(ResourceHandler wrapped) {
		
		this.wrapped=wrapped;
	}
	
	@Override
	public Resource createResource(String resourceName, String library) {
		
		TemplateManager templateManager=TemplateManager.getInstance();
		Resource resource=templateManager.getResource(resourceName, library);
		return resource!=null ? resource : wrapped.createResource(resourceName, library);
		
	}

	@Override
	public ResourceHandler getWrapped() {
		return wrapped;
	}

}
