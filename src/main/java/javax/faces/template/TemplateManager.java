package javax.faces.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;


@SuppressWarnings("serial")
@ApplicationScoped
@ManagedBean(eager=true)
public class TemplateManager implements PhaseListener,ResourceResolver {

	/**
     * <p>templates folder</p>
     */
    public static final String TEMPLATE_FOLDER = "templates";
    
	/**
     * <p>selected template parameter</p>
     */
    public static final String SELECTED_TEMPLATE = "javax.faces.view.TEMPLATE";
   
    /**
     * <p>template var name</p>
     */
    
    public static final String TEMPLATE_VAR_NAME = "template";

    
    /**
     * <p>template loader</p>
     */
    
    private final TemplateLoader loader;
    
    /**
     * <p>templates list</p>
     */
	
	private  final List<Template> templates;
	
	/**
     * <p>logger</p>
     */
	
	public static final Logger logger=Logger.getLogger(TemplateManager.class.getName());
	
    
	public TemplateManager() {
		
		this.loader=new TemplateLoader();	
		templates=new CopyOnWriteArrayList<Template>();

	}
	
	@PostConstruct
	public void load()  {
			
		boolean hasTemplates=loadTemplates(getRealPath());
		if(hasTemplates) {
			addPhaseListener();
			addResourceHandler();
		}	
	}
	
	public boolean loadTemplates(String path) {
		
		Folder folder=new Folder(path,TEMPLATE_FOLDER);
		for(Folder child : folder.getSubFolders()) 
			addTemplate(child);
		return templates.size()>0;
		
	}
	
	@SuppressWarnings("unchecked")
	private void addResourceHandler() {
		
		Application application=FacesContext.getCurrentInstance().getApplication();
		application.setResourceHandler(new ResourceHandlerImpl(this));
		
	}

	
	private void addPhaseListener() {
		
		LifecycleFactory factory = (LifecycleFactory)
		FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		Lifecycle lifecycle= factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
		lifecycle.addPhaseListener(this);
		
	}
	
	private String getRealPath() {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		ServletContext context=(ServletContext) facesContext.getExternalContext().getContext();
		return context.getRealPath("/");
		
	}
	
	public void addTemplate(Folder folder) {
		
		Document metadata=folder.getDocument(Template.METADATA);
		if(metadata!=null) {
			try {
				Template template=loader.load(metadata); 
				template.setMetadata(metadata);
				addTemplate(template);
			} catch(Exception e) {
				logger.log(Level.SEVERE, "error while loading template ", e);
			}
		}	
	}
	
	public void addTemplate(Template template) {
		
		template.setIndex(1+getSize());
		templates.add(template);
		
	}
		
	public void removeTemplate(Template template)  {
		
		templates.remove(template);
		
	}		
	
	private String getRequestParameter(String key) {
		
		FacesContext context=FacesContext.getCurrentInstance();
		return context.getExternalContext().getRequestParameterMap().get(key);	
	}
	
	private String getInitParameter(String key) {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		return facesContext.getExternalContext().getInitParameter(key);
	}
	
	private Object getSessionParameter(String key) {
		
		return getSessionMap().get(key);
		
	}
	
	private void addSessionParameter(String key,String value) {
		
		getSessionMap().put(key, value);
		
	}
	
	private Map<String,Object> getSessionMap() {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		ExternalContext externalContext=facesContext.getExternalContext();
		Map<String,Object> map=externalContext.getSessionMap();
		return map;
		
	}
	
	public int getSize() {
		
		return templates.size();	
	}
	
	@Override
	public Resource createResource(String resourceName,String libraryName) {
		
		return resourceName.equals(Template.THUMBNAIL)?
				getTemplate(libraryName).getResource(resourceName):
				getSelectedTemplate().getResource(resourceName, libraryName);    
	}

	public Template getSelectedTemplate() {
		
		Object id=getSessionParameter(SELECTED_TEMPLATE);
		return id!=null?
			   getTemplate(id.toString()):
			   getDefaultTemplate();
	}
	
	private Template getDefaultTemplate() {
		
		Template template=getTemplate(getInitParameter(SELECTED_TEMPLATE));
		return template!=null?
			   template :
			   getTemplates().get(0);
	}
	
	public String selectTemplate() {
		
		return selectTemplate(getTemplate(
				getRequestParameter(TEMPLATE_VAR_NAME))
				);
	}
	
	public String selectTemplate(Template template) {
	
		addSessionParameters(template);
		return redirect();
		
	}
	
	private void addSessionParameters(Template template) {
	
		addSessionParameter(SELECTED_TEMPLATE, template.getId());
		addSessionParameter(TEMPLATE_VAR_NAME,getPage(template));
		
	}
	
	private String getPage(Template template) {
		
		return "/"+TEMPLATE_FOLDER+"/"+template.getId()+"/"+Template.PAGE;
		
	}
	
	private String redirect() {
		
		FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String view=getRequestParameter("view");
        String url = view==null?extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, ctx.getViewRoot().getViewId())):
        	extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, "/"+view));
        try {
            extContext.redirect(url);
        } catch (IOException ioe) {
            throw new FacesException(ioe);
        }
        return null;
	}
	
	@Override
	public void beforePhase(PhaseEvent event) {
		
		@SuppressWarnings("unused")
		String selectTemplate=getSessionParameter(SELECTED_TEMPLATE)==null? 
							  selectTemplate(getDefaultTemplate()):
							  null;	
	}
	
	@Override
	public void afterPhase(PhaseEvent event) {
		
	}
	
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
	
	
	public List<Template> getTemplates() {
		
		return templates;
		
	}

	public Template getTemplate(String id) {
		
		for(Template template : templates) {
			if(template.getId().equalsIgnoreCase(id))
				return template;
		}
		return null;
	}
	
}
