package javax.faces.plugin;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.FactoryFinder;
import javax.faces.application.Application;
import javax.faces.application.Resource;
import javax.faces.bean.ApplicationScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;
import javax.servlet.ServletContext;



@SuppressWarnings("serial")
@ApplicationScoped
public abstract class PluginManager<T extends Plugin> implements PhaseListener {

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
			
		String repository=getInitParameter(PLUGIN_REPOSITORY);
		boolean loaded=repository!=null?loadPlugins(repository):loadPlugins(getRealPath());
		if(loaded) {
			addPhaseListener();
			addResourceHandler();
		}
		
	}
	
	protected boolean loadPlugins(String path) {
		
		this.folder=new Folder(path,getConfiguration().folder());
		for(Folder folder : this.folder.getSubFolders()) {
			Document metadata=new Document(folder,getConfiguration().metadata());
			try {
				  addPlugin(metadata);
				} catch(Exception e) {
					logger.log(Level.SEVERE, "error while loading plugin "+metadata, e);
			}
		}
		return plugins.size()>0;
		
	}
	
	protected String getRealPath() {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		ServletContext context=(ServletContext) facesContext.getExternalContext().getContext();
		return context.getRealPath("/");
		
	}
	
	public void addPlugin(Document metadata) throws Exception {
		
		if(metadata.exists()) {
			T plugin=loadPlugin(metadata);
			plugin.setFolder(metadata.getFolder());
			addPlugin(plugin);
		}	
	}
	

	@SuppressWarnings("unchecked")
	protected void addResourceHandler() {
		
		Application application=FacesContext.getCurrentInstance().getApplication();
		application.setResourceHandler(new ResourceHandlerImpl(this,application.getResourceHandler()));
		
	}
	
	protected void addPhaseListener() {
		
		LifecycleFactory factory = (LifecycleFactory)
		FactoryFinder.getFactory(FactoryFinder.LIFECYCLE_FACTORY);
		Lifecycle lifecycle= factory.getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE);
		lifecycle.addPhaseListener(this);
		
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
	
	public abstract Resource resolveResource(String resourceName,String library);
		
	
	protected String getRequestParameter(String key) {
		
		FacesContext context=FacesContext.getCurrentInstance();
		return context.getExternalContext().getRequestParameterMap().get(key);	
	}
	
	protected String getInitParameter(String key) {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		return facesContext.getExternalContext().getInitParameter(key);
	}
	
	protected Object getSessionParameter(String key) {
		
		return getSessionMap().get(key);
		
	}
	
	protected void addSessionParameter(String key,String value) {
		
		getSessionMap().put(key, value);
		
	}
	
	protected Map<String,Object> getSessionMap() {
		
		FacesContext facesContext=FacesContext.getCurrentInstance();
		ExternalContext externalContext=facesContext.getExternalContext();
		Map<String,Object> map=externalContext.getSessionMap();
		return map;
		
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
	
	@Override
	public void beforePhase(PhaseEvent event) {
		
	}
	
	@Override
	public void afterPhase(PhaseEvent event) {
		
	}
	
	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}
	
}