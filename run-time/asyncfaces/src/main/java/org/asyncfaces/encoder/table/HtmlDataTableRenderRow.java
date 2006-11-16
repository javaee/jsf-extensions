/*
 *
 * Created on 16 novembre 2006, 1.06
 */

package org.asyncfaces.encoder.table;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.component.NamingContainer;
import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.ResponseWriter;
import org.asyncfaces.encoder.CompositeEncoder;
import org.asyncfaces.encoder.CompositeEncoder.ComponentIdList;

/**
 *
 * @author agori
 */
public class HtmlDataTableRenderRow extends CompositeEncoder {
    
    private Integer index;
    private UIData data;
    
    public void setComponent(UIComponent comp) {
        super.setComponent(comp);
        this.data = (UIData) comp;
    }
    
    
    
    public Map<String, String> getParameters() {
        Map<String, String> map = new HashMap<String, String>();
        map.put("index", getIndex().toString());
        return map;
    }
    
    private Integer getIndex() {
        if (null == index) {
            Map<String, String> prms = getTarget(comp.getClientId(context)).getParameters();
            String param = prms.values().iterator().next();
            index = new Integer(param);
        }
        return index;
    }
    
    
    public String getClientHandler() {
        return "AsyncFaces.HtmlDataTableRowRender";
    }
    
    public String getClientId() {
        return comp.getClientId(context);
    }
    
    protected CompositeEncoder.ComponentIdList getGroup() {
        List<UIComponent> children = data.getChildren();
        ComponentIdList result = new ComponentIdList();
        int index = getIndex();
        String dataId = getClientId();
        for(UIComponent column : children) {
            List<UIComponent> colChildren = column.getChildren();
            
            result.openGroup();
            for (UIComponent child : column.getChildren()) {
                String id = dataId + NamingContainer.SEPARATOR_CHAR +
                        index + NamingContainer.SEPARATOR_CHAR +
                        child.getId();
                result.addToCurrentGroup(id);
            }
            result.closeGroup();
        }
        return result;
    }
    
    protected void encodeBeforeGroup(int groupIndex) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.startElement("div", comp);
    }
    
    protected void encodeAfterGroup(int groupIndex) throws IOException {
        ResponseWriter writer = context.getResponseWriter();
        writer.endElement("div");
    }
    
}
