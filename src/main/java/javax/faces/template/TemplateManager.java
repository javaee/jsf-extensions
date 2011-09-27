package javax.faces.template;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
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
	
	private String getRealPath() {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		ServletContext context=(ServletContext) facesContext.getExternalContext().getContext();
		return context.getRealPath("/");
	}
	
	private void addPhaseListener() {
		
		LifecycleFactory factory = (LifecycleFactory)
		FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		Lifecycle lifecycle= factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
		lifecycle.addPhaseListener(this);
	}

	@SuppressWarnings("unchecked")
	private void addResourceHandler() {
		
		Application application=FacesContext.getCurrentInstance().getApplication();
		application.setResourceHandler(new ResourceHandlerImpl(this));		
	}

	private void addTemplate(Folder folder) {
		
		Document metadata=folder.getDocument(Template.METADATA);
		if(metadata!=null) {
			try {
				Template template=loader.load(metadata); 
				template.setMetadata(metadata);
				addTemplate(template);
			} catch(Exception e) {
				logger.log(Level.SEVERE, "error while loading template "+metadata, e);
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
		
		Object id=getSessionParameter(Template.SELECTED);
		return id!=null?
			   getTemplate(id.toString()):
			   getDefaultTemplate();
	}
	
	private Template getDefaultTemplate() {
		
		Template template=getTemplate(getInitParameter(Template.SELECTED));
		return template!=null?
			   template :
			   templates.get(0);
	}
	
	private boolean selectTemplate() {
		
		String id=getRequestParameter(Template.VAR_NAME);
		if(id!=null)selectTemplate(getTemplate(id));
		return getSessionParameter(Template.SELECTED)!=null;
		
	}
	
	private void selectTemplate(Template template) {
	
		if(template!=null) {
			addSessionParameter(Template.SELECTED, template.getId());
			addSessionParameter(Template.VAR_NAME,getPage(template));
		}
		
	}
	
	private String getPage(Template template) {
		
		return "/"+TEMPLATE_FOLDER+"/"+template.getId()+"/"+Template.PAGE;
		
	}
	
	@Override
	public void beforePhase(PhaseEvent event) {
		
		if(!selectTemplate())
			selectTemplate(getDefaultTemplate());
							  
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
