package javax.faces.template;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.context.FacesContext;



public class Document  extends Resource {

	private final File file;
		
	public Document(File file) {
		
		this.file=file;	
	}
	
	public Document(String file) {
		
		this.file=new File(file);
		
	}

	public Document(Folder parent,String fileName) {
		
		this.file=new File(parent+File.separator+fileName);
		
	}
	
	public Document(File parent,String fileName) {
		
		this(new Folder(parent),fileName);
		
	}

	public Document(String parent,String fileName) {
		
		this(new Folder(parent),fileName);
		
	}
	
	public InputStream getInputStream() throws IOException {
		
		return new FileInputStream(file);
		
	}
	
	public boolean exists() {
		
		return file.exists();
		
	}
	
	public String toString() {
		
		return file.toString();
		
	}
	
	@Override
	public String getResourceName() {
		
		return file.getName();
		
	}
	
	@Override
	public String getLibraryName() {
		
		return file.getParentFile().getName();
		
	}

	@Override
	public String getRequestPath() {
		
			String uri;
	        FacesContext context = FacesContext.getCurrentInstance();
	        String servletPath = context.getExternalContext().getRequestServletPath();
	        String pathInfo = context.getExternalContext().getRequestPathInfo();
            String mapping = getMappingForRequest(servletPath, pathInfo);
            if (isPrefixMapped(mapping)) {
                uri = mapping + ResourceHandler.RESOURCE_IDENTIFIER + '/' +
                      getResourceName();
            } else {
                uri = ResourceHandler.RESOURCE_IDENTIFIER + '/' + getResourceName() +
                      mapping;
            }
            if (null != getLibraryName()) {
                uri += "?ln=" + getLibraryName();
            }
            uri = context.getApplication().getViewHandler()
            .getResourceURL(context,
                            uri);
      return uri;

	        
	}
	
	private boolean isPrefixMapped(String mapping) {
        return (mapping.charAt(0) == '/');
    }

	 private String getMappingForRequest(String servletPath, String pathInfo) {

	        if (servletPath == null) {
	            return null;
	        }
	        if (servletPath.length() == 0) {
	            return "/*";
	        }
	        if (pathInfo != null) {
	            return servletPath;
	        } else if (servletPath.indexOf('.') < 0) {
	            return servletPath;
	        } else {
	            return servletPath.substring(servletPath.lastIndexOf('.'));
	        }
	    }
	
	@Override
	public Map<String, String> getResponseHeaders() {
		Map<String,String> map=new HashMap<String, String>();
		map.put("Pragma","no-cache");
		map.put("Expires", "0");
		map.put("Cache-Control","no-cache");
		return map;
	}

	@Override
	public URL getURL() {
		
		try {
			return file.toURI().toURL();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean userAgentNeedsUpdate(FacesContext arg0) {
		return true;
	}
	
	public Folder getFolder() {
		
		return new Folder(file.getParentFile());	
	}
	
	@Override
	public String getContentType() {
		
		FacesContext context=FacesContext.getCurrentInstance();
		return context.getExternalContext().getMimeType(getResourceName());
		
	}
	
}
