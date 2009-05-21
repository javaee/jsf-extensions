package com.sun.faces.extensions.avatar.components;

import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;
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
public class AjaxZone extends UICommand implements Serializable, 
						   NamingContainer {
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
     * <p>Request attribute that indicates a <code>script</code> tag
     * pointing to com_sun_faces_ajax_zone.js has been rendered
     * already.</p>
     */

    public static final String ZONE_JS_LINKED = 
	"com.sun.faces.extensions.avatar.LINKED/com_sun_faces_ajax_zone.js";

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
        Map<String,Object> requestMap = 
	FacesContext.getCurrentInstance().getExternalContext().getRequestMap();
        List<AjaxZone> zoneList = (List<AjaxZone>) 
	    requestMap.get(ALL_ZONE_LIST);
        if (null == zoneList) {
            zoneList = new ArrayList<AjaxZone>();
            requestMap.put(ALL_ZONE_LIST, zoneList);
        }

        return zoneList;
    }

    public List<AjaxZone> getRenderedZoneList() {
        Map<String,Object> requestMap = 
	    FacesContext.getCurrentInstance().getExternalContext().
	    getRequestMap();
        List<AjaxZone> zoneList = (List<AjaxZone>) 
	    requestMap.get(RENDERED_ZONE_LIST);
        // If this is an AjaxRequest, the effective zoneList is
        // (allZoneList - (allZoneList - renderedZoneList))
        // where renderedZoneList is the list of zones specified
        // in the render request parameter for this request
        if (null == zoneList) {
            zoneList = new ArrayList<AjaxZone>();
            requestMap.put(RENDERED_ZONE_LIST, zoneList);
            List<AjaxZone> allZoneList = getAllZoneList();
            List<AjaxZone> allZonesMinusRenderedComponents = 
		new ArrayList<AjaxZone>();

            zoneList.addAll(allZoneList);
            allZonesMinusRenderedComponents.addAll(allZoneList);
            allZonesMinusRenderedComponents.
		removeAll(AsyncResponse.getInstance().
			  getRenderedComponentSubtrees());
            zoneList.removeAll(allZonesMinusRenderedComponents);
        }

        return zoneList;
    }    

    private static final String ALL_ZONE_LIST = AsyncResponse.FACES_PREFIX + 
	"ALL_AJAX_ZONE_LIST";
    private static final String RENDERED_ZONE_LIST = 
	AsyncResponse.FACES_PREFIX + "RENDERED_AJAX_ZONE_LIST";

    
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
     * <p>CSS style class(es) to be applied to the outermost HTML
     * element when this component is rendered.</p>
     */

    private String styleClass = null;

    /**
     * <p>CSS style class(es) to be applied to the outermost HTML
     * element when this component is rendered.</p>
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
     * <p>CSS style class(es) to be applied to the outermost HTML
     * element when this component is rendered.</p>
     * @see #getStyleClass()
     */
    public void setStyleClass(String styleClass) {
        this.styleClass = styleClass;
    }
    
    /**
     * <p>The name of a JavaScript function that tells the 
     * system which kinds of elements within this zone to instrument with 
     * Ajax behavior.</p>
     */
    private String inspectElement = null;

    /**
     * <p>Get the name of a JavaScript function that tells the 
     * system which kinds of elements within this zone to instrument with 
     * Ajax behavior.</p>  
     */
    public String getInspectElement() {
        if (this.inspectElement != null) {
            return this.inspectElement;
        }
        ValueExpression ve = getValueExpression("inspectElement");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the name of a JavaScript function that tells the 
     * system which kinds of elements within this zone to instrument with 
     * Ajax behavior.</p>
     * @see #getInspectElement()
     */
    public void setInspectElement(String inspectElement) {
        this.inspectElement = inspectElement;
    }
    
    /**
     * <p>The name of a JavaScript event to intercept for sending
     * Ajax requests. If not specified, the
     * "onclick" JavaScript event is intercepted.</p>
     */
    private String eventType = null;

    /**
     * <p>Get the name of a JavaScript event to intercept for sending
     * Ajax requests. If not specified, the
     * "onclick" JavaScript event is intercepted.</p>
     */
    public String getEventType() {
        if (this.eventType != null) {
            return this.eventType;
        }
        ValueExpression ve = getValueExpression("eventType");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the name of a JavaScript event to intercept for sending
     * Ajax requests. If not specified, the
     * "onclick" JavaScript event is intercepted.</p>
     * @see #getEventType()
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    
    /**
     * <p>The name of a JavaScript function to be called when
     * the JavaScript event specified by the value of the
     * <code>eventType</code> occurs.</p>
     */
    private String collectPostData = null;

    /**
     * <p>Get the name of a JavaScript function to be called when
     * the JavaScript event specified by the value of the
     * <code>eventType</code> occurs.</p>
     */
    public String getCollectPostData() {
        if (this.collectPostData != null) {
            return this.collectPostData;
        }
        ValueExpression ve = getValueExpression("collectPostData");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the name of a JavaScript function to be called when
     * the JavaScript event specified by the value of the
     * <code>eventType</code> occurs.</p>
     * @see #getCollectPostData()
     */
    public void setCollectPostData(String collectPostData) {
        this.collectPostData = collectPostData;
    }

    /**
     * <p>The name of a JavaScript function to be called after
     * the new content from the server for this zone has been
     * installed into the view.</p>
     */
    private String postReplace = null;

    /**
     * <p>Get the name of a JavaScript function to be called after
     * the new content from the server for this zone has been
     * installed into the view.</p>
     */
    public String getPostReplace() {
        if (this.postReplace != null) {
            return this.postReplace;
        }
        ValueExpression ve = getValueExpression("postReplace");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the name of a JavaScript function to be called after
     * the new content from the server for this zone has been
     * installed into the view.</p>
     * @see #getPostReplace()
     */
    public void setPostReplace(String postReplace) {
        this.postReplace = postReplace;
    }
    
    /**
     * <p>The name of a JavaScript function
     * that will be called when the system needs to replace a chunk
     * of markup in the view based on the return from the server.</p>
     */
    private String replaceElement = null;

    /**
     * <p>Get the name of a JavaScript function
     * that will be called when the system needs to replace a chunk
     * of markup in the view based on the return from the server.</p>
     */
    public String getReplaceElement() {
        if (this.replaceElement != null) {
            return this.replaceElement;
        }
        ValueExpression ve = getValueExpression("replaceElement");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the name of a JavaScript function
     * that will be called when the system needs to replace a chunk
     * of markup in the view based on the return from the server.</p>
     * @see #getReplaceElement()
     */
    public void setReplaceElement(String replaceElement) {
        this.replaceElement = replaceElement;
    }
    
    /**
     * <p>The name of a JavaScript function to be called to
     * provide a closure argument that will be passed to the Ajax
     * request and made available to the Ajax response in the
     * <code>replaceElement</code> or <code>postReplace</code>
     * functions.</p>
     */
    private String getCallbackData = null;

    /**
     * <p>Get the name of a JavaScript function to be called to
     * provide a closure argument that will be passed to the Ajax
     * request and made available to the Ajax response in the
     * <code>replaceElement</code> or <code>postReplace</code>
     * functions.</p>
     */
    public String getGetCallbackData() {
        if (this.getCallbackData != null) {
            return this.getCallbackData;
        }
        ValueExpression ve = getValueExpression("getCallbackData");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set the name of a JavaScript function to be called to
     * provide a closure argument that will be passed to the Ajax
     * request and made available to the Ajax response in the
     * <code>replaceElement</code> or <code>postReplace</code>
     * functions.</p>
     * @see #getGetCallbackData()
     */
    public void setGetCallbackData(String getCallbackData) {
        this.getCallbackData = getCallbackData;
    }
    
    /**
     * <p>A comma separated list of client ids for which the
     * "execute" portion of the request processing lifecycle will
     * be run when an Ajax transaction originates from within this
     * zone.  If not specified, the "execute" portion of the
     * request processing lifecycle will be run on this zone
     * only.</p>
     */
    private String execute = null;

    /**
     * <p>Get a comma separated list of client ids for which the
     * "execute" portion of the request processing lifecycle will
     * be run when an Ajax transaction originates from within this
     * zone.  If not specified, the "execute" portion of the
     * request processing lifecycle will be run on this zone
     * only.</p>
     */
    public String getExecute() {
        if (this.execute != null) {
            return this.execute;
        }
        ValueExpression ve = getValueExpression("execute");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set a comma separated list of client ids for which the
     * "execute" portion of the request processing lifecycle will
     * be run when an Ajax transaction originates from within this
     * zone.  If not specified, the "execute" portion of the
     * request processing lifecycle will be run on this zone
     * only.</p>
     * @see #getExecute()
     */
    public void setExecute(String execute) {
        this.execute = execute;
    }
    
    /**
     * <p>A comma separated list of client ids for which the
     * "render" portion of the request processing lifecycle will be
     * run when an Ajax transaction originates from within this
     * zone.  If not specified, the "render" portion of the request
     * processing lifecycle will be run on this zone only.</p>
     */
    private String render = null;

    /**
     * <p>Get a comma separated list of client ids for which the
     * "render" portion of the request processing lifecycle will be
     * run when an Ajax transaction originates from within this
     * zone.  If not specified, the "render" portion of the request
     * processing lifecycle will be run on this zone only.</p>
     */
    public String getRender() {
        if (this.render != null) {
            return this.render;
        }
        ValueExpression ve = getValueExpression("render");
        if (ve != null) {
            return (String) ve.getValue(getFacesContext().getELContext());
        }
        return null;
    }

    /**
     * <p>Set a comma separated list of client ids for which the
     * "render" portion of the request processing lifecycle will be
     * run when an Ajax transaction originates from within this
     * zone.  If not specified, the "render" portion of the request
     * processing lifecycle will be run on this zone only.</p>
     * @see #getRender()
     */
    public void setRender(String render) {
        this.render = render;
    }
    
    /**
     * <p>Restore the state of this component.</p>
     */
    public void restoreState(FacesContext context, Object state) {
        Object values[] = (Object[]) state;
        super.restoreState(context, values[0]);
        this.style = (String) values[1];
        this.styleClass = (String) values[2];
        this.inspectElement = (String) values[3];
        this.eventType = (String) values[4];
        this.collectPostData = (String) values[5];
        this.postReplace = (String) values[6];
        this.replaceElement = (String) values[7];
        this.getCallbackData = (String) values[8];
        this.execute = (String) values[9];
        this.render = (String) values[10];
    }

    /**
     * <p>Save the state of this component.</p>
     */
    public Object saveState(FacesContext context) {
        Object values[] = new Object[11];
        values[0] = super.saveState(context);
        values[1] = this.style;
        values[2] = this.styleClass;
        values[3] = this.inspectElement;
        values[4] = this.eventType;
        values[5] = this.collectPostData;
        values[6] = this.postReplace;
        values[7] = this.replaceElement;
        values[8] = this.getCallbackData;
        values[9] = this.execute;
        values[10] = this.render;
        return values;
    }
    
}
