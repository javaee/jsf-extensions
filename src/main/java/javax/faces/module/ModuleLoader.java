package javax.faces.module;

import java.io.InputStream;
import javax.faces.application.Resource;
import javax.faces.plugin.PluginLoader;
import org.apache.commons.digester.Digester;


public class ModuleLoader implements PluginLoader<Module> {

	private Digester digester;
	
	
	public ModuleLoader() {
		
		digester=new Digester();
		digester.addObjectCreate("module", Module.class);
		digester.addSetProperties("module");
		digester.addBeanPropertySetter("module/name");
		digester.addBeanPropertySetter("module/version");
		digester.addBeanPropertySetter("module/creationDate");
		digester.addBeanPropertySetter("module/author");
		digester.addBeanPropertySetter("module/authorEmail");
		digester.addBeanPropertySetter("module/authorUrl");
		digester.addBeanPropertySetter("module/copyright");
		digester.addBeanPropertySetter("module/license");
		digester.addBeanPropertySetter("module/description");
		
	}
	
	@Override
	public Module load(Resource metadata) throws Exception {
		
		InputStream in=metadata.getInputStream();
		Module module=(Module) digester.parse(in);
		in.close();
		return module;
		
	}


}
