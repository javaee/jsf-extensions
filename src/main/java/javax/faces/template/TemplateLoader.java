package javax.faces.template;

import java.io.InputStream;

import javax.faces.application.Resource;
import javax.faces.plugin.PluginLoader;
import org.apache.commons.digester.Digester;


public class TemplateLoader implements PluginLoader<Template>{

	private Digester digester;
	
	
	public TemplateLoader() {
		
		digester=new Digester();
		digester.addObjectCreate("template", Template.class);
		digester.addSetProperties("template");
		digester.addBeanPropertySetter("template/name");
		digester.addBeanPropertySetter("template/version");
		digester.addBeanPropertySetter("template/creationDate");
		digester.addBeanPropertySetter("template/author");
		digester.addBeanPropertySetter("template/authorEmail");
		digester.addBeanPropertySetter("template/authorUrl");
		digester.addBeanPropertySetter("template/license");
		digester.addBeanPropertySetter("template/description");
	}
	
	@Override
	public Template load(Resource metadata) throws Exception {
		
		InputStream in=metadata.getInputStream();
		Template template=(Template) digester.parse(in);
		in.close();
		return template;
		
	}


	
}
