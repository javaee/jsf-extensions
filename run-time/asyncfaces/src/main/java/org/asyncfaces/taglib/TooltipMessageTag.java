/*
 *
 * Created on 17 novembre 2006, 13.08
 */

package org.asyncfaces.taglib;

import java.util.Map;
import javax.el.ELContext;
import javax.el.ValueExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.webapp.UIComponentELTag;
import org.asyncfaces.component.UITooltipMessage;

/**
 *
 * @author agori
 */
public class TooltipMessageTag extends UIComponentELTag {
    
    private ValueExpression delay;
    private ValueExpression renderOnlyIfAjax;
    private ValueExpression style;
    private ValueExpression styleClass;
    private ValueExpression _for;
    private ValueExpression showSummary;
    private ValueExpression showDetail;
    private ValueExpression dir;
    private ValueExpression lang;
    
    public TooltipMessageTag() {
        super();
    }
    
    public void release() {
        super.release();
        delay = null;
        renderOnlyIfAjax = null;
        style = null;
        styleClass = null;
        _for = null;
        showSummary = null;
        showDetail = null;
        dir = null;
        lang = null;
    }
    
    public String getComponentType() {
        return UITooltipMessage.COMPONENT_TYPE;
    }
    
    public String getRendererType() {
        return UITooltipMessage.DEFAULT_RENDERER_TYPE;
    }
    
    protected void setProperties(UIComponent component) {
        super.setProperties(component);
        
        UITooltipMessage message = (UITooltipMessage) component;
        Map<String, Object> attributes = message.getAttributes();
        FacesContext context = FacesContext.getCurrentInstance();
        ELContext elContext = context.getELContext();
        
        if (renderOnlyIfAjax != null) {
            if (!renderOnlyIfAjax.isLiteralText()) {
                message.setValueExpression("renderOnlyIfAjax", renderOnlyIfAjax);
            } else {
                Boolean value = new Boolean(renderOnlyIfAjax.getExpressionString());
                message.setRenderOnlyIfAjax(value);
            }
        }
        
        if (_for != null) {
            if (!_for.isLiteralText()) {
                message.setValueExpression("for", _for);
            } else {
                message.setFor(_for.getExpressionString());
            }
        }
        
        
        if (style != null) {
            attributes.put("style", style.getValue(elContext));
        }
        
        if (styleClass != null) {
            attributes.put("styleClass", styleClass.getValue(elContext));
        }
        
        if (showSummary != null) {
            if (!showSummary.isLiteralText()) {
                message.setValueExpression("showSummary", showSummary);
            } else {
                Boolean value = new Boolean(showSummary.getExpressionString());
                message.setShowSummary(value);
            }
        }
        
        if (showDetail != null) {
            if (!showDetail.isLiteralText()) {
                message.setValueExpression("showDetail", showDetail);
            } else {
                Boolean value = new Boolean(showDetail.getExpressionString());
                message.setShowDetail(value);
            }
        }
        
        if (dir != null) {
            if (!dir.isLiteralText()) {
                attributes.put("dir", dir.getValue(elContext));
            } else {
                attributes.put("dir", dir.getExpressionString());
            }
        }
        
        if (lang != null) {
            if (!lang.isLiteralText()) {
                attributes.put("lang", lang.getValue(elContext));
            } else {
                attributes.put("lang", lang.getExpressionString());
            }
        }
        
       
        
        
    }
    
    public ValueExpression getDelay() {
        return delay;
    }
    
    public void setDelay(ValueExpression delay) {
        this.delay = delay;
    }
    
    public ValueExpression getRenderOnlyIfAjax() {
        return renderOnlyIfAjax;
    }
    
    public void setRenderOnlyIfAjax(ValueExpression renderOnlyIfAjax) {
        this.renderOnlyIfAjax = renderOnlyIfAjax;
    }
    
    public ValueExpression getStyle() {
        return style;
    }
    
    public void setStyle(ValueExpression style) {
        this.style = style;
    }
    
    public ValueExpression getStyleClass() {
        return styleClass;
    }
    
    public void setStyleClass(ValueExpression styleClass) {
        this.styleClass = styleClass;
    }
    
    public ValueExpression getFor() {
        return _for;
    }
    
    public void setFor(ValueExpression _for) {
        this._for = _for;
    }
    
    public ValueExpression getShowSummary() {
        return showSummary;
    }
    
    public void setShowSummary(ValueExpression showSummary) {
        this.showSummary = showSummary;
    }
    
    public ValueExpression getShowDetail() {
        return showDetail;
    }
    
    public void setShowDetail(ValueExpression showDetail) {
        this.showDetail = showDetail;
    }
    
    public ValueExpression getDir() {
        return dir;
    }
    
    public void setDir(ValueExpression dir) {
        this.dir = dir;
    }
    
    public ValueExpression getLang() {
        return lang;
    }
    
    public void setLang(ValueExpression lang) {
        this.lang = lang;
    }
    
}
