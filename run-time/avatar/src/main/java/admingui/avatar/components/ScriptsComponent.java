package admingui.avatar.components;

import javax.faces.component.UIOutput;

/**
 * 
 */
public class ScriptsComponent extends UIOutput {
    public ScriptsComponent() {
        super();
        setRendererType("admingui.avatar.Scripts");
    }

    /** 
     * <p>Return the family for this component.</p> 
     */ 
    public String getFamily() { 
        return "admingui.avatar.Scripts"; 
    }

    
}
