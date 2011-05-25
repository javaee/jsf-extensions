package javax.faces.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import javax.faces.application.Resource;
import javax.faces.context.FacesContext;


public final class Document extends Resource  {

	private final File file;
		
	public Document(File file) {
		
		this.file=file;	
	}
	
	public Document(String file) {
		
		this(new File(file));
	}

	
	public InputStream getInputStream() throws IOException {
		
		return new FileInputStream(file);
		
	}
	

	public boolean exists() {
		
		return file.exists();
		
	}


	@Override
	public String getRequestPath() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, String> getResponseHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL getURL() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean userAgentNeedsUpdate(FacesContext arg0) {
		// TODO Auto-generated method stub
		return false;
	}
	
}
