package javax.faces.template;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.faces.FacesException;
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
    public static final String SELECTED_TEMPLATE_ID = "javax.faces.view.TEMPLATE";
    
    /**
     * <p>template var name</p>
     */
    
    public static final String TEMPLATE_VAR_NAME = "template";
    
    /**
     * <p>templates folder</p>
     */
    
    public static final String TEMPLATE_FOLDER = "templates";
    
    
	public TemplateManager() {
		
		super(new TemplateLoader());
		
	}
	
	@Override
	public void loadPlugins() {
		
		super.loadPlugins();
		if(getSize()>0) {
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
	
	private Template getDefaultTemplate() {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		String id=facesContext.getExternalContext().getInitParameter(SELECTED_TEMPLATE_ID);
		Template template=getTemplate(id);
		return template!=null? template : getTemplates().get(0);
		
	}
	
	private Template getSelectedTemplate() {
		
		Template template=null;
		Object id=getSessionMap().get(SELECTED_TEMPLATE_ID);
		if(id!=null) template=getTemplate(id.toString());
		return template!=null? template : getDefaultTemplate();
		
	}
	
	
	public Resource getResource(String resourceName,String library) {
		
		if(resourceName.equals(Template.THUMBNAIL))
    		return folder.getDocument(Template.THUMBNAIL,library);
		return getSelectedTemplate().getResource(resourceName, library);    
		
	}

	
	public List<Template> getTemplates() {
		
		return plugins;
		
	}

	public Template getTemplate(String id) {
		
		return getPlugin(id);
		
	}
	
	public String selectTemplate() {
		
		FacesContext context=FacesContext.getCurrentInstance();
		String id=context.getExternalContext().getRequestParameterMap().get(TEMPLATE_VAR_NAME);
		return selectTemplate(getTemplate(id));
		
	}
	
	
	public String selectTemplate(Template template) {
		
		if(template!=null) {
			Map<String,Object> map=getSessionMap();
			map.put(SELECTED_TEMPLATE_ID, template.getId());
			map.put("template",getPage(template));
			FacesContext ctx = FacesContext.getCurrentInstance();
	        ExternalContext extContext = ctx.getExternalContext();
	        String url = extContext.encodeActionURL(ctx.getApplication().getViewHandler().getActionURL(ctx, ctx.getViewRoot().getViewId()));
	        try {
	            extContext.redirect(url);
	        } catch (IOException ioe) {
	            throw new FacesException(ioe);
	
	        }
		}
		return null;
	}
	
	private String getPage(Template template) {
		
		return "/"+TEMPLATE_FOLDER+"/"+template.getId()+"/"+Template.PAGE;
		
	}

	@Override
	public void afterPhase(PhaseEvent event) {
		
	}
	
	@Override
	public void beforePhase(PhaseEvent event) {
		
		Object value=getSessionMap().get(SELECTED_TEMPLATE_ID);
		if(value==null) selectTemplate(getDefaultTemplate());
		
	}
	
	
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
	
	public static TemplateManager getInstance() {
		
		FacesContext context=FacesContext.getCurrentInstance();
		Map<String,Object> map=context.getExternalContext().getApplicationMap();
		return (TemplateManager) map.get("templateManager");
		
	}
	
}
