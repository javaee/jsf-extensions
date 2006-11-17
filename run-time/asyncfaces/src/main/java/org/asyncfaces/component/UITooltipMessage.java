/*
 *
 * Created on 17 novembre 2006, 12.14
 */

package org.asyncfaces.component;

import java.util.HashMap;
import java.util.Map;
import javax.faces.component.html.HtmlMessage;
import javax.faces.context.FacesContext;
import org.asyncfaces.Util;

/**
 *
 * @author agori
 */
public class UITooltipMessage extends HtmlMessage {
    
    public static final String COMPONENT_TYPE = "org.asyncfaces.TooltipMessage";
    
    public static final String DEFAULT_RENDERER_TYPE = "org.asyncfaces.TooltipMessageRenderer";
    
    public static final String ASSOCIATIONS_KEY = "UITooltipMessage.associations";
    
    
    private Boolean renderOnlyIfAjax;
    
    public UITooltipMessage() {
        super();
        setRendererType(DEFAULT_RENDERER_TYPE);
    }
    
    public String getFamily() {
        return "org.asyncfaces.TooltipMessage";
    }
    
    public Object saveState(FacesContext context) {
        Object values[] = new Object[2];
        values[0] = super.saveState(context);
        values[1] = renderOnlyIfAjax;
        
        return values;
        
    }
    
    
    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        
        renderOnlyIfAjax = (Boolean) values[1];
    }
    
 
    public Boolean getRenderOnlyIfAjax() {
        Boolean value = (Boolean) Util.getPropertyBoolean(this,
                renderOnlyIfAjax, "renderOnlyIfAjax");
        if (null == value) {
            value = true;
        }
        return value;
    }
    
    public void setRenderOnlyIfAjax(Boolean renderOnlyIfAjax) {
        this.renderOnlyIfAjax = renderOnlyIfAjax;
    }

    public void setFor(String newFor) {
        super.setFor(newFor);
        addAssociation();
    }
    
    private void addAssociation() {
        FacesContext context = FacesContext.getCurrentInstance();
        Map requestMap = context.getExternalContext().getRequestMap();
        Map associations = (Map) requestMap.get(ASSOCIATIONS_KEY);
        if (null == associations) {
            associations = new HashMap();
            requestMap.put(ASSOCIATIONS_KEY, associations);
        }
        associations.put(getFor(), this);
    }
    

}

