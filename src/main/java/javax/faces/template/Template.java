package javax.faces.template;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.Resource;
import javax.faces.plugin.Plugin;

public class Template extends Plugin {

	 /**
     * <p>Facelets page.</p>
     */
    public static final String PAGE = "template.xhtml";

    /**
     * <p>Thumbnail.</p>
     */
    public static final String THUMBNAIL = "template.png";
    
    /**
     * <p>Template Locations.</p>
     */
    
    private final List<String> locations;
    
    
    public Template() {
    	
    	locations=new ArrayList<String>();
    	
    }
    
    public Resource getResource(String resourceName,String library) {
    	
    	return folder.getDocument(resourceName, library);
		
    }
    
    public void addLocation(String name) {
    	
    	locations.add(name);
    	
    }
    
    public void removeLocation(String name) {
    	
    	locations.remove(name);
    	
    }

	public List<String> getLocations() {
		return locations;
	}
    
    
}
