package javax.faces.template;
import javax.faces.application.Resource;


public class Template {

	 /**
     * <p>Facelets page.</p>
     */
    public static final String PAGE = "template.xhtml";

    /**
     * <p>Thumbnail.</p>
     */
    public static final String THUMBNAIL = "template.png";
    
  
    /**
     * <p>Template metadata</p>
     */
    public static final String METADATA = "template.xml";
    
    
    
    protected String name="";
	protected String description="";
	protected String creationDate="";
	protected String version="1.0";
	protected String author="";
	protected String authorEmail="";
	protected String authorUrl="";
	protected String license="GPL";
	protected String copyright="";
	protected Document metadata;
	protected int index;
	
	
	public Template() {
		
    }
    
    public Resource getResource(String resourceName) {
    	
    	return metadata.getFolder().getDocument(resourceName);
    	
    }
    
    public Resource getResource(String resourceName,String libraryName) {
    	
    	return metadata.getFolder().getDocument(resourceName, libraryName);
    	
    }
    
    public String getId() {
		return metadata.getFolder().getName();
	}
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getAuthorEmail() {
		return authorEmail;
	}

	public void setAuthorEmail(String authorEmail) {
		this.authorEmail = authorEmail;
	}

	public String getAuthorUrl() {
		return authorUrl;
	}

	public void setAuthorUrl(String authorUrl) {
		this.authorUrl = authorUrl;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getCopyright() {
		return copyright;
	}

	public void setCopyright(String copyright) {
		this.copyright = copyright;
	}

	public Document getMetadata() {
		return metadata;
	}

	public void setMetadata(Document metadata) {
		this.metadata = metadata;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}
    
    
}
