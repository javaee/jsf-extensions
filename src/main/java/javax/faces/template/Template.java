package javax.faces.template;

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
    
    
    public Resource getResource(String resourceName,String library) {
    	
    	return folder.getDocument(resourceName, library);
		
    }
    
}
