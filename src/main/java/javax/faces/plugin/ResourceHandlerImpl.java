package javax.faces.plugin;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.context.FacesContext;


public class ResourceHandlerImpl<T extends ResourceResolver> extends ResourceHandlerWrapper{
	
	private final T resolver;
	private final ResourceHandler wrapped;	
	
	
	public ResourceHandlerImpl(T resolver) {
		
		this.resolver=resolver;
		this.wrapped=FacesContext.getCurrentInstance().getApplication().getResourceHandler();
		
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
		
		Resource resource=resolver.resolveResource(resourceName, library,contentType);
		return resource!=null ? resource : wrapped.createResource(resourceName, library);
		
	}

	@Override
	public ResourceHandler getWrapped() {
		return wrapped;
	}

}
