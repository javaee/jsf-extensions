package javax.faces.plugin;

import java.io.IOException;

import javax.faces.module.Module;
import javax.faces.module.ModuleLoader;
import javax.faces.plugin.Document;
import junit.framework.TestCase;


public class TestModuleLoad extends TestCase{

	public void testLoad() throws IOException, Exception {
	
		Document metadata =new Document("src/test/resources/module.xml");
		assertEquals(true,metadata.exists());
		ModuleLoader loader=new ModuleLoader();
		Module module=loader.load(metadata);
		assertNotNull(module);
		assertEquals("CRM", module.getName());
		assertEquals("1.0",module.getVersion());
		assertEquals("04/10/2010", module.getCreationDate());
		assertEquals("lamine",module.getAuthor());
		assertEquals("laminba2003@yahoo.fr", module.getAuthorEmail());
		assertEquals("http://java.net",module.getAuthorUrl());
		assertEquals("MODULE_DESCRIPTION",module.getDescription());
		
	}
}
