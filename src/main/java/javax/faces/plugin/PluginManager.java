package javax.faces.plugin;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.application.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;



@ApplicationScoped
public abstract class PluginManager<T extends Plugin>  {

	protected  final List<T> 	plugins;
	protected  PluginLoader<T>  loader;
	protected  Folder folder;
	public static final Logger logger=Logger.getLogger(PluginManager.class.getName());
	/**
     * <p>plugins folder</p>
     */
    
    public static final String PLUGIN_FOLDER = "javax.faces.plugin.FOLDER";
    
	
	public PluginManager() {
		
		plugins=new ArrayList<T>();
		
	}
	
	public PluginManager(PluginLoader<T> loader) {
	
		this();
		this.loader=loader;							
	}

	@PostConstruct
	public void load()  {
			
		FacesContext facesContext=FacesContext.getCurrentInstance();
		ServletContext context=(ServletContext) facesContext.getExternalContext().getContext();
		String repository=context.getInitParameter(PLUGIN_FOLDER);
		if(repository!=null)
			load(repository);
		else 
			load(context.getRealPath("/"));
		
	}
	
	public void load(String path) {
		
		plugins.clear();
		this.folder=new Folder(path,getConfiguration().folder());
		for(Folder folder : this.folder.getSubFolders()) {
			Document metadata=new Document(folder,getConfiguration().metadata());
			try {
				  add(metadata);
				} catch(Exception e) {
					logger.log(Level.SEVERE, "error while loading plugin "+metadata, e);
			}
		}
	}
	
	private Manage getConfiguration() {
		
		return getClass().getAnnotation(Manage.class);
		
	}
	
	public void add(Document metadata) throws Exception {
		
		if(metadata.exists()) {
			T plugin=loader.load(metadata);
			plugin.setFolder(metadata.getFolder());
			add(plugin);
		}
		
	}

	public T load(Resource metadata) throws Exception{
		
		return loader.load(metadata);
	}
	
	
	public void add(T plugin) {
		
		plugin.setIndex(1+getSize());
		plugins.add(plugin);
		
	}
		
	public void remove(T plugin)  {
		
		plugins.remove(plugin);
		
	}
	
	public List<T> getPlugins() {
		
		return plugins;
	}
	
	public int getSize() {
		
		return plugins.size();
		
	}
	
	public T get(String id) {
		
		for(T plugin : plugins) {
			if(plugin.getId().equalsIgnoreCase(id))
				return plugin;
		}
		return null;
	}

	public PluginLoader<T> getLoader() {
		return loader;
	}

	public void setLoader(PluginLoader<T> loader) {
		this.loader = loader;
	}

}