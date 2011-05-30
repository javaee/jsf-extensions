package javax.faces.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;



public class Document   {

	private final File file;
		
	public Document(File file) {
		
		this.file=file;	
	}
	
	public Document(String file) {
		
		this.file=new File(file);
		
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
}
