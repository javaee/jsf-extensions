package javax.faces.template;

import javax.faces.application.ResourceHandler;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.plugin.Manage;
import javax.faces.plugin.PluginManager;


@ManagedBean(eager=true)
@Manage(folder="templates",metadata="template.xml")
public class TemplateManager extends PluginManager<Template> {

	/**
     * <p>The selected template.</p>
     */
    public static final String SELECTED_TEMPLATE = "javax.faces.TEMPLATE";
    
    /**
     * <p>The templates folder</p>
     */
    
    public static final String TEMPLATE_FOLDER = "templates";
    
    /**
     * <p>The facelets page.</p>
     */
    public static final String TEMPLATE_FILE = "template.xhtml";

    
    
	public TemplateManager() {
		
		super(new TemplateLoader());
		
	}
	
	@Override
	public void load() {
		
		super.load();
		FacesContext context=FacesContext.getCurrentInstance();
		String selectedTemplate =context.getExternalContext().getInitParameter(SELECTED_TEMPLATE);
		Template template=get(selectedTemplate);
		if(template==null) {
			if(plugins.size()>0)
				template=plugins.get(0);
		}
		if(template!=null) {
			context.getExternalContext().getApplicationMap().put(SELECTED_TEMPLATE, template);
			context.getExternalContext().getApplicationMap().put("template","/"+TEMPLATE_FOLDER+"/"+selectedTemplate+"/"+TEMPLATE_FILE);
			ResourceHandler current=context.getApplication().getResourceHandler();
			context.getApplication().setResourceHandler(new ResourceHandlerImpl(current));
		}
		
	}
	
}
