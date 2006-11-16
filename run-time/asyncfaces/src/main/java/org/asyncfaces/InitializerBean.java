/*
 *
 * Created on 12 novembre 2006, 11.21
 */

package org.asyncfaces;

import com.sun.faces.extensions.avatar.renderkit.ScriptsRenderer;

/**
 *
 * @author agori
 */
public class InitializerBean {
    
    public InitializerBean() {
        String[] resources = ScriptsRenderer.scriptIds;
        String[] newResources = new String[resources.length + 1];
        int i = 0;
        for (String res : resources) {
            newResources[i++] = res;
        }
        newResources[i] = "/META-INF/asyncfaces.js";
        ScriptsRenderer.scriptIds = newResources;
    }
    
}
