package javax.faces.template;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
public final class TemplateManager implements PhaseListener,ResourceResolver {

	
	/**
     * <p>selected template parameter</p>
     */
    public static final String TEMPLATE_FOLDER = "templates";
    
	
	/**
     * <p>selected template parameter</p>
     */
    public static final String SELECTED_TEMPLATE = "javax.faces.view.TEMPLATE";
    
   
    /**
     * <p>template loader</p>
     */
    
    
    private final TemplateLoader loader;
    
    /**
     * <p>templates</p>
     */
	
	protected  final List<Template> templates;
	
    
	/**
     * <p>logger</p>
     */
	
	public static final Logger logger=Logger.getLogger(TemplateManager.class.getName());
	
    
	public TemplateManager() {
		
		this.loader=new TemplateLoader();	
		templates=new ArrayList<Template>();	
	}
	
	
	@PostConstruct
	public void load()  {
			
		boolean hasTemplates=load(getRealPath());
		if(hasTemplates) {
			addPhaseListener();
			addResourceHandler();
		}
	}
	
	protected boolean load(String path) {
		
		Folder root=new Folder(path,TEMPLATE_FOLDER);
		for(Folder folder : root.getSubFolders()) {
			Document metadata=folder.getDocument(Template.METADATA);
			try {
				  add(metadata);
				} catch(Exception e) {
					logger.log(Level.SEVERE, "error while loading plugin "+metadata, e);
			}
		}
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
	
	protected String getRealPath() {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		ServletContext context=(ServletContext) facesContext.getExternalContext().getContext();
		return context.getRealPath("/");
		
	}
	
	public void add(Document metadata) throws Exception {
		
		if(metadata!=null) {
			Template template=load(metadata);
			template.setMetadata(metadata);
			add(template);
		}	
	}
	

	public Template load(Resource metadata) throws Exception{
		
		return loader.load(metadata);
		
	}
	
	public void add(Template template) {
		
		template.setIndex(1+getSize());
		templates.add(template);
		
	}
		
	public void remove(Template template)  {
		
		templates.remove(template);
		
	}		
	
	protected String getRequestParameter(String key) {
		
		FacesContext context=FacesContext.getCurrentInstance();
		return context.getExternalContext().getRequestParameterMap().get(key);	
	}
	
	protected String getInitParameter(String key) {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		return facesContext.getExternalContext().getInitParameter(key);
	}
	
	protected Object getSessionParameter(String key) {
		
		return getSessionMap().get(key);
		
	}
	
	protected void addSessionParameter(String key,String value) {
		
		getSessionMap().put(key, value);
		
	}
	
	protected Map<String,Object> getSessionMap() {
		
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

	private Template getSelectedTemplate() {
		
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
				getRequestParameter(Template.VAR_NAME))
				);
	}
	
	public String selectTemplate(Template template) {
	
		addSessionParameters(template);
		return redirect();
		
	}
	
	private void addSessionParameters(Template template) {
	
		addSessionParameter(SELECTED_TEMPLATE, template.getId());
		addSessionParameter(Template.VAR_NAME,getPage(template));
		
	}
	
	private String getPage(Template template) {
		
		return "/"+TEMPLATE_FOLDER+"/"+template.getId()+"/"+Template.PAGE;
		
	}
	
	private String redirect() {
		
		FacesContext ctx = FacesContext.getCurrentInstance();
        ExternalContext extContext = ctx.getExternalContext();
        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, ctx.getViewRoot().getViewId()));
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
