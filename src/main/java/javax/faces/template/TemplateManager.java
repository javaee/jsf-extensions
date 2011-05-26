package javax.faces.template;

import javax.faces.plugin.Manage;
import javax.faces.plugin.PluginManager;

@Manage(folder="templates",metadata="template.xml")
public class TemplateManager extends PluginManager<Template> {

	public TemplateManager() {
		
		super(new TemplateLoader());
		
	}
}
