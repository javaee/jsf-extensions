package javax.faces.plugin;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.plugin.PluginManager;


@SuppressWarnings("unchecked")
public class ResourceHandlerImpl<T extends PluginManager> extends ResourceHandlerWrapper{
	
	private final T manager;
	private final ResourceHandler wrapped;	
	
	
	public ResourceHandlerImpl(T manager,ResourceHandler wrapped) {
		
		this.manager=manager;
		this.wrapped=wrapped;
		
	}
		
	@Override
    public Resource createResource(String resourceName)
    {
        return createResource(resourceName, null, null);
    }

    @Override
    public Resource createResource(String resourceName, String libraryName)
    {
        return createResource(resourceName, libraryName, null);
    }

	
	@Override
	public Resource createResource(String resourceName, String library,String contentType) {
		
		Resource resource=manager.resolveResource(resourceName, library);
		return resource!=null ? resource : wrapped.createResource(resourceName, library);
		
	}

	@Override
	public ResourceHandler getWrapped() {
		return wrapped;
	}

}
