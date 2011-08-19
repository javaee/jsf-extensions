package javax.faces.template;

import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.application.ResourceHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.plugin.Manage;
import javax.faces.plugin.PluginManager;


@ManagedBean(eager=true)
@Manage(folder="templates",metadata="template.xml")
public class TemplateManager extends PluginManager<Template> {

	/**
     * <p>selected template context param</p>
     */
    public static final String SELECTED_TEMPLATE = "javax.faces.view.TEMPLATE";
    
    /**
     * <p>templates folder</p>
     */
    
    public static final String TEMPLATE_FOLDER = "templates";
    
   
    /**
     * <p>current template.</p>
     */
    
    protected Template template;
    
    
    public static final Logger logger=Logger.getLogger("TemplateManager");
    
    
	public TemplateManager() {
		
		super(new TemplateLoader());
		
	}
	
	@Override
	public void load() {
		
		super.load();
		List<Template> templates=getTemplates();
		FacesContext context=FacesContext.getCurrentInstance();
		String id =context.getExternalContext().getInitParameter(SELECTED_TEMPLATE);
		Template template=get(id);
		if(template==null) template=templates.size()>0?templates.get(0):null;
		if(template!=null) {
			setTemplate(template);
			Application application=context.getApplication();
			ResourceHandler defaultHandler=application.getResourceHandler();
			application.setResourceHandler(new ResourceHandlerImpl(defaultHandler));
		}
		
	}
	
	
	public void setTemplate(Template template) {
		
		if(this.template!=template) {
			
			FacesContext context=FacesContext.getCurrentInstance();
			Map<String,Object> map=context.getExternalContext().getApplicationMap();
			map.put(SELECTED_TEMPLATE, template);
			String page="/"+TEMPLATE_FOLDER+"/"+template.getId()+"/"+Template.PAGE;
			map.put("template",page);
			this.template=template;
			logger.info("selecting template "+template.getId());
			
		}
	}
	
	public Template getTemplate() {
		
		return template;
		
	}
	
	public List<Template> getTemplates() {
		
		return plugins;
		
	}
	
}
