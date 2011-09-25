package javax.faces.template;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class Folder {

	private File file;
	
	public Folder(File file) {
		
		this.file=file;
		
	}
	public Folder(String name) {
		
		file=new File(name);
		
	}
	
	public Folder(Folder parent,String name) {
		
		this.file=new File(parent+File.separator+name);
		
	}
	
	public Folder(File file,String name) {
		
		this(new Folder(file),name);
		
	}
	
	public Folder(String parent,String name) {
		
		this(new Folder(parent),name);
	
	}

	public List<Folder> getSubFolders() {
		
		List<Folder> folders=new ArrayList<Folder>();
		File[] files=file.listFiles();  
		if(files!=null) {
			for(File file : files) {
					if(file.isDirectory())
						folders.add(new Folder(file));
			}
		}
		return folders;	
	}

	public Document getDocument(String resourceName,String libraryName) {
		
		Document document=libraryName!=null?
				new Document(file+File.separator+libraryName,resourceName):
				new Document(file,resourceName);
		return document.exists()?
				document:
				null;
			
	}
	
	public Document getDocument(String resourceName) {
		
		Document document=new Document(file,resourceName);
		return document.exists()?
			   document:
			   null;
		
	}
	
	public Folder getParent() {
		
		return new Folder(file.getParentFile().getAbsolutePath());
		
	}
	
	public void zip(OutputStream out) throws IOException {
		
		ZipOutputStream zip=new ZipOutputStream(out);
		zip(file,file,zip);
		zip.close();
	}
		
	
	private void zip(File root,File folder,ZipOutputStream zip) throws IOException {
		
		String path=root.getParentFile().getAbsolutePath();
		File[] files=folder.listFiles();
		if(files!=null) {
			for(File file : files) {
				String entryName=path.endsWith(File.separator)?
						file.getAbsolutePath().substring(path.length()):
						file.getAbsolutePath().substring(path.length()+1);
				if(file.isFile()) {
					ZipEntry entry=new ZipEntry(entryName);
					zip.putNextEntry(entry);
					InputStream in=new FileInputStream(file);
					write(in,zip);
					in.close();
					zip.closeEntry();
				}
				else if(file.isDirectory()) {
					ZipEntry entry=new ZipEntry(entryName+"/");
					zip.putNextEntry(entry);
					zip.closeEntry();
					zip(root,file,zip);
				}

				
			}
		}
		
	}
	

	private void write(InputStream in,OutputStream out) throws IOException {
		
		in=new BufferedInputStream(in,512);
		out=new BufferedOutputStream(out,512);
		byte[] buffer=new byte[512];
		int c=0;
			while( (c=in.read(buffer,0,buffer.length))!=-1)
				out.write(buffer,0,c);
		out.flush();
		
	}
	
	public String toString() {
		
		return file.toString();
		
	}
	
	public String getName() {
		
		return file.getName();
		
	}
	
}
