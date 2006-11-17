/*
 *
 * Created on 11 novembre 2006, 21.21
 */

package org.asyncfaces;

import com.sun.faces.extensions.avatar.lifecycle.ComponentEncoder;
import com.sun.faces.extensions.avatar.lifecycle.DefaultEncoderHandler;
import com.sun.faces.extensions.avatar.lifecycle.EncoderHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import org.asyncfaces.encoder.BaseEncoder;

/**
 *
 * @author agori
 */
public class AsyncFacesEncoderHandler implements EncoderHandler {
    
    private Map<String, ComponentEncoder> encoders = new HashMap<String, ComponentEncoder>();
    private Map<String, Target> targets = new HashMap<String, Target>();
    
    
    public AsyncFacesEncoderHandler(List<String> renders) {
        int i = 0;
        for (String render : renders) {
            ComponentEncoder encoder = null;
            String clientId = render;
            
            if (render.contains(".")) {
                Parser parser = new Parser(render);
                Target target = parser.parse();
                targets.put(clientId, target);
                encoder = target.getEncoderInstance();
                clientId = target.getClientId();
                targets.put(clientId, target);
            }
            renders.set(i, clientId);
            encoders.put(clientId, encoder);
            
            ++i;
        }
        
    }
    
    public ComponentEncoder getEncoder(UIComponent component) {
        FacesContext context = FacesContext.getCurrentInstance();
        ComponentEncoder encoder = encoders.get(component.getClientId(context));
        if (null == encoder) {
            return new DefaultEncoderHandler.DefaultComponentEncoder(component);
        }
        BaseEncoder base = (BaseEncoder) encoder;
        base.setComponent(component);
        return encoder;
    }
    
    public Target getTarget(String id) {
        return targets.get(id);
    }
    
    public Map<String, Target> getTargets() {
        return targets;
    }
    
}
