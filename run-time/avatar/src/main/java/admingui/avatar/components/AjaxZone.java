package admingui.avatar.components;

import admingui.avatar.lifecycle.AsyncResponse;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.faces.component.ContextCallback;
import javax.faces.component.UICommand;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.el.MethodInfo;
import javax.faces.component.UIComponent;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;
import javax.el.ValueExpression;
import javax.faces.component.NamingContainer;

/**
 * 
 */
public class AjaxZone extends UICommand implements Serializable, NamingContainer {
    public AjaxZone() {
        super();
        setRendererType("admingui.AjaxZone");
    }

    /** 
     * <p>Return the family for this component.</p> 
     */ 
    public String getFamily() { 
        return "admingui.AjaxZone"; 
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
        List<AjaxZone> zoneList = getAllZoneList();
        if (!zoneList.contains(this)) {
            zoneList.add(this);
        }
    }
    
    public List<AjaxZone> getAllZoneList() {
        Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        List<AjaxZone> zoneList = (List<AjaxZone>) requestMap.get(ALL_ZONE_LIST);
        if (null == zoneList) {
            zoneList = new ArrayList<AjaxZone>();
            requestMap.put(ALL_ZONE_LIST, zoneList);
        }

        return zoneList;
    }

    public List<AjaxZone> getRenderedZoneList() {
        Map<String,Object> requestMap = FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        List<AjaxZone> zoneList = (List<AjaxZone>) requestMap.get(RENDERED_ZONE_LIST);
        // If this is an AjaxRequest, the effective zoneList is
        // (allZoneList - (allZoneList - renderedZoneList))
        // where renderedZoneList is the list of zones specified
        // in the render request parameter for this request
        if (null == zoneList) {
            zoneList = new ArrayList<AjaxZone>();
            requestMap.put(RENDERED_ZONE_LIST, zoneList);
            List<AjaxZone> allZoneList = getAllZoneList();
            List<AjaxZone> allZonesMinusRenderedComponents = new ArrayList<AjaxZone>();

            zoneList.addAll(allZoneList);
            allZonesMinusRenderedComponents.addAll(allZoneList);
            allZonesMinusRenderedComponents.removeAll(AsyncResponse.getInstance().getRenderedComponentSubtrees());
            zoneList.removeAll(allZonesMinusRenderedComponents);
        }

        return zoneList;
    }    

    private static final String ALL_ZONE_LIST = AsyncResponse.FACES_PREFIX + "ALL_AJAX_ZONE_LIST";
    private static final String RENDERED_ZONE_LIST = AsyncResponse.FACES_PREFIX + "RENDERED_AJAX_ZONE_LIST";

    
    /* --------------- style and styleClass ------------- */
    
    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    private String style = null;

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyle() {
        if (this.style != null) {
            return this.style;
        }
        ValueExpression ve = getValueExpression("style");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style(s) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyle()
     */
    public void setStyle(String style) {
        this.style = style;
    }

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */

    private String styleClass = null;

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     */
    public String getStyleClass() {
        if (this.styleClass != null) {
            return this.styleClass;
        }
        ValueExpression ve = getValueExpression("styleClass");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML element when this 
     * component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    
    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        this.style = (String) values[1];
        this.styleClass = (String) values[2];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext context) {
        Object values[] = new Object[3];
        values[0] = super.saveState(context);
        values[1] = this.style;
        values[2] = this.styleClass;
        return values;
    }
    
}
