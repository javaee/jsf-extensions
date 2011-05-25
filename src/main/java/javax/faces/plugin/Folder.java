package javax.faces.plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public final class Folder {

	private File file;
	
	public Folder(String name) {
		
		file=new File(name);
		
	}
	
	public List<Folder> getSubFolders() {
		
		List<Folder> folders=new ArrayList<Folder>();
		if(file.listFiles()!=null) {
			for(File file : this.file.listFiles()) {
					if(file.isDirectory())
						folders.add(new Folder(file.getAbsolutePath()));
			}
		}
		return folders;	
	}

	public String toString() {
		
		return file.getAbsolutePath();
		
	}
}
