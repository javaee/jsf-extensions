package javax.faces.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.faces.template.Template;
import javax.faces.template.TemplateLoader;
import junit.framework.TestCase;

public class TestTemplateLoad extends TestCase{

	public void testLoad() throws IOException, Exception {
	
		File file =new File("src/test/resources/template.xml");
		assertEquals(true,file.exists());
		TemplateLoader loader=new TemplateLoader();
		Template template=loader.load(new FileInputStream(file));
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
