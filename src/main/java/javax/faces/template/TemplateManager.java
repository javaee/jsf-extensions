package javax.faces.template;

import java.util.List;
import java.util.Map;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.faces.plugin.Manage;
import javax.faces.plugin.PluginManager;


@SuppressWarnings("serial")
@ManagedBean(eager=true)
@Manage(folder="templates",metadata="template.xml")
public class TemplateManager extends PluginManager<Template> implements PhaseListener{

	/**
     * <p>context parameter</p>
     */
    public static final String VIEW_TEMPLATE = "javax.faces.view.TEMPLATE";
    
    /**
     * <p>templates folder</p>
     */
    
    public static final String TEMPLATE_FOLDER = "templates";
    
    
	public TemplateManager() {
		
		super(new TemplateLoader());
		
	}
	
	@Override
	public void load() {
		
		super.load();
		List<Template> templates=getTemplates();
		if(templates.size()>0) {
			addPhaseListener();
			initResourceHandler();
		}	
	}
	
	private void addPhaseListener() {
		
		LifecycleFactory factory = (LifecycleFactory)
		FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		Lifecycle lifecycle= factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
		lifecycle.addPhaseListener(this);
	}
	
	private void initResourceHandler() {
		
		FacesContext context=FacesContext.getCurrentInstance();
		Application application=context.getApplication();
		ResourceHandler defaultHandler=application.getResourceHandler();
		application.setResourceHandler(new ResourceHandlerImpl(defaultHandler));
	}
	
	private Map<String,Object> getSessionMap() {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		ExternalContext externalContext=facesContext.getExternalContext();
		Map<String,Object> map=externalContext.getSessionMap();
		return map;
	}
	
	private String getDefaultTemplate() {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		return facesContext.getExternalContext().getInitParameter(VIEW_TEMPLATE);	
	}
	
	private String getSelectedTemplate() {
		
		return getSessionMap().get(VIEW_TEMPLATE).toString();
		
	}
	
	public Resource getResource(String resourceName,String library) {
		
		if(resourceName.equals(Template.THUMBNAIL))
    		return folder.getDocument(Template.THUMBNAIL,library);
		Template template=get(getSelectedTemplate());
		return template.getResource(resourceName, library);    
		
	}

	
	public List<Template> getTemplates() {
		
		return plugins;
		
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		
	}
	
	@Override
	public void beforePhase(PhaseEvent event) {
		
		Map<String,Object> map=getSessionMap();
		Object value=map.get(VIEW_TEMPLATE);
		if(value==null) {
			Template template=get(getDefaultTemplate());
			if(template==null) template=getTemplates().get(0);
			selectTemplate(template);
		}	
	}
	
	public void selectTemplate(Template template) {
		
		Map<String,Object> map=getSessionMap();
		map.put(VIEW_TEMPLATE, template.getId());
		map.put("template",getPage(template));
		
	}
	
	private String getPage(Template template) {
		
		return "/"+TEMPLATE_FOLDER+"/"+template.getId()+"/"+Template.PAGE;
		
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
	
	public static TemplateManager getInstance() {
		
		FacesContext context=FacesContext.getCurrentInstance();
		Map<String,Object> map=context.getExternalContext().getApplicationMap();
		TemplateManager templateManager=(TemplateManager) map.get("templateManager");
		return templateManager;
		
	}
	
}
