package javax.faces.plugin;
import java.io.File;
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
	public static final Logger logger=Logger.getLogger("PluginManager");
	   
	
	public PluginManager() {
		
			plugins=new ArrayList<T>();
		
	}
	
	public PluginManager(PluginLoader<T> loader) {
	
			this();
			this.loader=loader;							
	}

	@PostConstruct
	public void load()  {
			
			ServletContext context=(ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
			String realPath=context.getRealPath("/");
			load(realPath);
			
	}
	
	public void load(String path) {
		
		Manage manage=getClass().getAnnotation(Manage.class);
		Folder pluginFolder=new Folder(path+File.separator+manage.folder());
		for(Folder folder : pluginFolder.getSubFolders()) {
			Document metadata=new Document(folder+File.separator+manage.metadata());
			if(metadata.exists()) {
				try {
					T plugin=load(metadata);
					if(plugin!=null) {
						plugin.setFolder(folder);
						add(plugin);
						logger.info("adding plugin "+plugin.getId());
					}
				} catch(Exception e) {
					logger.log(Level.SEVERE, "error while loading plugin "+metadata, e);
				}
			}
		} 		
	}
	
	public T load(Resource metadata) throws Exception{
		
			return loader.load(metadata);
	}
	
	public void add(T plugin) {
		
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