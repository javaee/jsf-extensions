package com.sun.faces.extensions.avatar.components;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.component.UICommand;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.faces.context.FacesContext;

/**
 * 
 */
public class AjaxZone extends UICommand implements Serializable {
    public AjaxZone() {
        super();
        setRendererType("com.sun.faces.AjaxZone");
    }

    /** 
     * <p>Return the family for this component.</p> 
     */ 
    public String getFamily() { 
        return "com.sun.faces.AjaxZone"; 
    }

    /**
     * <p>Override the <code>UICommand</code> method to wrap the
     * argument <code>MethodExpression</code> so that any return value
     * it may yield from invocation is ignored and <code>null</code> is
     * returned instead.</p>
     */

    public void setActionExpression(final MethodExpression actionExpression) {
	MethodExpression me = new MethodExpression() {
		public boolean equals(Object other) {
		    return actionExpression.equals(other);
		}

		public String getExpressionString() {
		    return actionExpression.getExpressionString();
		}

		public int hashCode() {
		    return actionExpression.hashCode();
		}
		
		public boolean isLiteralText() {
		    return actionExpression.isLiteralText();
		}

		public MethodInfo getMethodInfo(ELContext context) {
		    return actionExpression.getMethodInfo(context);
		}

		public Object invoke(ELContext context, 
				     Object[] params) {
		    actionExpression.invoke(context, params);
		    return null;
		}
	    };
		    
        super.setActionExpression(me);
    }

    public void setId(String id) {
        super.setId(id);
        addThisToZoneList();
        
    }
    
    private void addThisToZoneList() {
        List<AjaxZone> zoneList = getZoneList();
        if (null == zoneList) {
            setZoneList(zoneList = new ArrayList<AjaxZone>());
            zoneList.add(this);
        }
        else if (!zoneList.contains(this)) {
            zoneList.add(this);
        }
    }
    
    public List<AjaxZone> getZoneList() {
        Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        List<AjaxZone> zoneList = (List<AjaxZone>) requestMap.get(ZONE_LIST);

        return zoneList;
    }
    
    public void setZoneList(List<AjaxZone> list) {
        Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        requestMap.put(ZONE_LIST, list);
    }
    

    /**
     * @return true if the scripts for all zones in this view should
     * be rendered right now.
     *
     * <p>Returns true if this AjaxZone instance is the last rendered zone 
     * in the view.
     */
    
    public boolean isRenderScriptsForAllZonesRightNow() {
        boolean result = false;
        List<AjaxZone> zoneList = getZoneList();
        
        if (null != zoneList && !zoneList.isEmpty()) {
            AjaxZone lastRenderedZone = null;
            // Find the last entry in the zoneList
            // that has its rendered property set to true
            for (int i = zoneList.size() - 1; i >= 0; i--) {
                lastRenderedZone = zoneList.get(i);
                if (lastRenderedZone.isRendered()) {
                    break;
                }
                lastRenderedZone = null;
            }
            if (null != lastRenderedZone) {
                result = (this == lastRenderedZone);
            }
        }

        return result;
    }

    private static final String ZONE_LIST = AsyncResponse.FACES_PREFIX + "AJAX_ZONE_LIST";
    
}
