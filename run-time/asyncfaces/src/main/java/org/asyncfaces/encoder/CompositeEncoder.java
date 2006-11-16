/*
 *
 * Created on 16 novembre 2006, 11.03
 */

package org.asyncfaces.encoder;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.FacesException;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIViewRoot;

/**
 *
 * @author agori
 */
abstract public class CompositeEncoder extends BaseEncoder {
    
    abstract protected void encodeBeforeGroup(int groupIndex) throws IOException;
    
    abstract protected void encodeAfterGroup(int groupIndex) throws IOException;
    
    abstract protected ComponentIdList getGroup();
    
    public void encodeMarkup() throws IOException  {
        ComponentIdList idList = getGroup();
        
        int index = 0;
        
        UIViewRoot root = context.getViewRoot();
        
        final ContextCallback RENDER_CALLBACK  = new RenderContextCallback();
        
        for (Iterator<List<String>> it = idList.iterator(); it.hasNext();) {
            List<String> list = it.next();
            encodeBeforeGroup(index);
            for (String subId : list) {
                root.invokeOnComponent(context, subId, RENDER_CALLBACK);
            }
            encodeAfterGroup(index++);
        }
    }
    
    static public class ComponentIdList {
        
        private List<List<String>> groups = new ArrayList<List<String>>();
        private List<String> currentGroup;
        private boolean grouped;
        
        public ComponentIdList addAsUniqueId(String id) {
            addComponentId(id);
            return this;
        }
        
        public void openGroup() {
            grouped = true;
            currentGroup = new ArrayList<String>();
        }
        
        public void closeGroup() {
            grouped = false;
            if (!currentGroup.isEmpty()) {
                groups.add(currentGroup);
            }
            currentGroup = null;
        }
        
        public void addToCurrentGroup(String id) {
            if (null == currentGroup) {
                throw new FacesException("group is not opened");
            }
            currentGroup.add(id);
        }
        
        public List<String> addComponentId(String id) {
            List<String> ids = new ArrayList<String>();
            ids.add(id);
            groups.add(ids);
            return ids;
        }
        
        public Iterator<List<String>> iterator() {
            return groups.iterator();
        }
    }
    
}
