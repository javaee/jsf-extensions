/*
 *
 * Created on 14 novembre 2006, 13.48
 */

package org.asyncfaces.component;

import java.util.Map;
import javax.faces.component.UIComponent;
import javax.faces.component.UIComponentBase;
import javax.faces.component.UIParameter;
import javax.faces.context.FacesContext;
import org.asyncfaces.Target;
import org.asyncfaces.renderkit.RenderUtils;

/**
 *
 * @author agori
 */
public class UITarget extends UIComponentBase {
    
    static public final String COMPONENT_TYPE = "org.asyncfaces.Target";
    
    public UITarget() {
        super();
    }
    
    public String getFamily() {
        return "org.asyncfaces.Target";
    }
    
    public Target buildTarget() {
        FacesContext context = FacesContext.getCurrentInstance();
        Target target = new Target();
        Map atts = getAttributes();
        target.setClientId(RenderUtils.getAttr(context, this, "for"));
        target.setServerHandler(RenderUtils.getAttr(context, this, "method"));
        
        for (UIComponent comp : getChildren()) {
            if (comp instanceof UIParameter) {
                UIParameter param = (UIParameter) comp;
                target.getParameters().put(param.getName(), (String) param.getValue());
            }
        }
        return target;
    }
    
}
