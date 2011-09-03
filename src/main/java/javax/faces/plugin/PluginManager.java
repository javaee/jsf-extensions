package javax.faces.plugin;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;



@ApplicationScoped
public abstract class PluginManager<T extends Plugin>  {

	/**
     * <p>plugins</p>
     */
	
	protected  final List<T> 	plugins;
	
	/**
     * <p>Plugin Loader</p>
     */
    
	protected  PluginLoader<T>  loader;
	
	/**
     * <p>plugins folder</p>
     */
    
	protected  Folder folder;
	
	/**
     * <p>logger</p>
     */
	
	public static final Logger logger=Logger.getLogger(PluginManager.class.getName());
	
	/**
     * <p>PLUGIN REPOSITORY</p>
     */
    
    public static final String PLUGIN_REPOSITORY = "javax.faces.plugin.REPOSITORY";
    
	
	public PluginManager() {
		
		plugins=new ArrayList<T>();
		
	}
	
	public PluginManager(PluginLoader<T> loader) {
	
		this();
		this.loader=loader;							
	}

	@PostConstruct
	public void loadPlugins()  {
			
		FacesContext facesContext=FacesContext.getCurrentInstance();
		ServletContext context=(ServletContext) facesContext.getExternalContext().getContext();
		String repository=context.getInitParameter(PLUGIN_REPOSITORY);
		if(repository!=null)
			loadPlugins(repository);
		else 
			loadPlugins(context.getRealPath("/"));	
	}
	
	public void loadPlugins(String path) {
		
		plugins.clear();
		this.folder=new Folder(path,getConfiguration().folder());
		for(Folder folder : this.folder.getSubFolders()) {
			Document metadata=new Document(folder,getConfiguration().metadata());
			try {
				  addPlugin(metadata);
				} catch(Exception e) {
					logger.log(Level.SEVERE, "error while loading plugin "+metadata, e);
			}
		}
	}
	
	
	public void addPlugin(Document metadata) throws Exception {
		
		if(metadata.exists()) {
			T plugin=loadPlugin(metadata);
			plugin.setFolder(metadata.getFolder());
			addPlugin(plugin);
		}
		
	}

	public T loadPlugin(Document metadata) throws Exception{
		
		return loader.load(metadata);
	}
	
	
	public void addPlugin(T plugin) {
		
		plugin.setIndex(1+getSize());
		plugins.add(plugin);
		
	}
		
	public void removePlugin(T plugin)  {
		
		plugins.remove(plugin);
		
	}
	
	public List<T> getPlugins() {
		
		return plugins;
	}
	
	public int getSize() {
		
		return plugins.size();
		
	}
	
	public T getPlugin(String id) {
		
		for(T plugin : plugins) {
			if(plugin.getId().equalsIgnoreCase(id))
				return plugin;
		}
		return null;
	}

	private Manage getConfiguration() {
		
		return getClass().getAnnotation(Manage.class);
		
	}
	
	public PluginLoader<T> getLoader() {
		return loader;
	}

	public void setLoader(PluginLoader<T> loader) {
		this.loader = loader;
	}

}