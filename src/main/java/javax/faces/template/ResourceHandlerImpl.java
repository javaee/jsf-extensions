package javax.faces.template;

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
        return createResource(resourceName, null);
    }

    @Override
    public Resource createResource(String resourceName, String libraryName)
    {
    	Resource resource=resolver.createResource(resourceName, libraryName);
		return resource!=null ? resource : wrapped.createResource(resourceName, libraryName);
		
    }

    
	@Override
	public ResourceHandler getWrapped() {
		return wrapped;
	}

}
