package javax.faces.plugin;

import java.io.IOException;
import javax.faces.plugin.Document;
import javax.faces.template.Template;
import javax.faces.template.TemplateLoader;
import junit.framework.TestCase;


public class TestTemplateLoad extends TestCase{

	public void testLoad() throws IOException, Exception {
	
		Document metadata =new Document("src/test/resources/template.xml");
		assertEquals(true,metadata.exists());
		TemplateLoader loader=new TemplateLoader();
		Template template=loader.load(metadata);
		assertNotNull(template);
		assertEquals("Roses", template.getName());
		assertEquals("1.0",template.getVersion());
		assertEquals("04/10/2010", template.getCreationDate());
		assertEquals("lamine",template.getAuthor());
		assertEquals("laminba2003@yahoo.fr", template.getAuthorEmail());
		assertEquals("http://java.net",template.getAuthorUrl());
		assertEquals("TEMPLATE_DESCRIPTION",template.getDescription());
		
	}
}
