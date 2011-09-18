package javax.faces.template;

import java.io.IOException;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.application.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.plugin.Manage;
import javax.faces.plugin.PluginManager;


@SuppressWarnings("serial")
@ManagedBean(eager=true)
@Manage(folder="templates",metadata="template.xml")
public final class TemplateManager extends PluginManager<Template> {

	/**
     * <p>selected template parameter</p>
     */
    public static final String SELECTED_TEMPLATE = "javax.faces.view.TEMPLATE";
    
    /**
     * <p>template var name</p>
     */
    
    public static final String TEMPLATE_VAR_NAME = "template";
    
    
    
	public TemplateManager() {
		
		super(new TemplateLoader());	
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
		
		return "/"+getConfiguration().folder()+"/"+template.getId()+"/"+Template.PAGE;
		
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
	
	public List<Template> getTemplates() {
		
		return plugins;
		
	}

	public Template getTemplate(String id) {
		
		return getPlugin(id);
	}
	
}
