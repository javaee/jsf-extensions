/*
 *
 * Created on 11 novembre 2006, 22.27
 */

package org.asyncfaces.encoder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.faces.component.UIComponent;
import org.asyncfaces.Target;
import org.asyncfaces.Util;

/**
 *
 * @author agori
 */
public class SetClassName extends BaseEncoder  {
    
    protected boolean valid;
    
    public void encodeMarkup() throws IOException {
        
    }
    
    public String getClientId() {
        return comp.getClientId(context);
    }
    
    public Map<String, String> getParameters() {
        Target target = getTarget(comp.getClientId(context));
        Map<String, String> prms = target.getParameters();
        String className = valid ? prms.get("onSuccess") : prms.get("onError");
        
        Map<String, String> res = new HashMap<String, String>();
        res.put("className", className);
        return res;
    }
    
    
    public String getClientHandler() {
        return "AsyncFaces.SetClassName";
    }
    
    public void setComponent(UIComponent comp) {
        super.setComponent(comp);
        valid = Util.isValid();
    }
    
}
