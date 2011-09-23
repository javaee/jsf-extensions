package javax.faces.remote;

import java.io.IOException;
import java.io.OutputStream;
import javax.faces.template.TemplateManager;
import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import javax.faces.template.Template;

@Path("/templates")
public class TemplateService {

	@GET
	@Produces("application/xml")
	public String getTemplatesAsXML(@Context ServletContext context)  {
		
		TemplateManager templateManager=(TemplateManager) context.getAttribute("templateManager");
		StringBuffer buffer=new StringBuffer();
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		buffer.append("<templates>\r\n");
		for(Template template:templateManager.getTemplates()) {
			
			buffer.append("<template>\r\n");
			buffer.append("<id>"+template.getIndex()+"</id>");
			buffer.append("<name>"+template.getName()+"</name>");
			buffer.append("<creationDate>"+template.getCreationDate()+"</creationDate>");
			buffer.append("<author>"+template.getAuthor()+"</author>");
			buffer.append("<authorEmail>"+template.getAuthorEmail()+"</authorEmail>");
			buffer.append("<authorUrl>"+template.getAuthorUrl()+"</authorUrl>");
			buffer.append("<creationDate>"+template.getCreationDate()+"</creationDate>");
			buffer.append("<copyright>"+template.getCopyright()+"</copyright>");
			buffer.append("<license>"+template.getLicense()+"</license>");
			buffer.append("<description>"+template.getDescription()+"</description>\r\n");
			buffer.append("</template>\r\n");
			
		}
		buffer.append("</templates>");
		return buffer.toString();
		
	}
	
	@GET @Path("/{id}")
	@Produces("application/xml")
	public  String getTemplateAsXML(@Context ServletContext context,@PathParam("id")int index)  {
		
		TemplateManager templateManager=(TemplateManager) context.getAttribute("templateManager");
		StringBuffer buffer=new StringBuffer();
		Template template;
		try {
			template=templateManager.getTemplates().get(index-1);
		} catch(ArrayIndexOutOfBoundsException e) {
			throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
		}
		buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n");
		buffer.append("<template>\r\n");
		buffer.append("<id>"+template.getIndex()+"</id>");
		buffer.append("<name>"+template.getName()+"</name>");
		buffer.append("<creationDate>"+template.getCreationDate()+"</creationDate>");
		buffer.append("<author>"+template.getAuthor()+"</author>");
		buffer.append("<authorEmail>"+template.getAuthorEmail()+"</authorEmail>");
		buffer.append("<authorUrl>"+template.getAuthorUrl()+"</authorUrl>");
		buffer.append("<creationDate>"+template.getCreationDate()+"</creationDate>");
		buffer.append("<copyright>"+template.getCopyright()+"</copyright>");
		buffer.append("<license>"+template.getLicense()+"</license>");
		buffer.append("<description>"+template.getDescription()+"</description>\r\n");
		buffer.append("</template>\r\n");
		return buffer.toString();
		
	}
	
	
	
	@GET @Path("/download/{id}")
	@Produces("application/zip")
	public StreamingOutput download(@Context final ServletContext context,@PathParam("id")final int index)  {
		
		return new StreamingOutput() {
				public void write(OutputStream out) throws IOException,WebApplicationException {
					
					TemplateManager templateManager=(TemplateManager) context.getAttribute("templateManager");
					Template template;
					try {
						template=templateManager.getTemplates().get(index-1);
					} catch(ArrayIndexOutOfBoundsException e) {
						throw new WebApplicationException(Response.status(Response.Status.NOT_FOUND).build());
					}
					template.getMetadata().getFolder().zip(out);
			}
		};
		
	}
	
	
}
