/*
 * The contents of this file are subject to the terms
 * of the Common Development and Distribution License
 * (the License).  You may not use this file except in
 * compliance with the License.
 * 
 * You can obtain a copy of the license at
 * https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * See the License for the specific language governing
 * permissions and limitations under the License.
 * 
 * When distributing Covered Code, include this CDDL
 * Header Notice in each file and include the License file
 * at https://woodstock.dev.java.net/public/CDDLv1.0.html.
 * If applicable, add the following below the CDDL Header,
 * with the fields enclosed by brackets [] replaced by
 * you own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 * 
 * Copyright 2007 Sun Microsystems, Inc. All rights reserved.
 */

package com.sun.faces.mirror;

import com.sun.mirror.declaration.ClassDeclaration;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Represents a JSF renderer class declared in the current compilation unit.
 *
 * @author gjmurphy
 */
public class DeclaredRendererInfo extends DeclaredClassInfo {
    
    static String VALUE = "value";
    
    Map<String,Object> annotationValueMap;
    List<RendersInfo> renderings;
    
    DeclaredRendererInfo(Map<String,Object> annotationValueMap, ClassDeclaration decl) {
        super(decl);
        this.annotationValueMap = annotationValueMap;
        renderings = new ArrayList<RendersInfo>();
        if (this.annotationValueMap.containsKey(VALUE)) {
	    String qn = decl.getQualifiedName();
            for (Object value : (List) this.annotationValueMap.get(VALUE)) {
                Map nestedAnnotationValueMap = (Map) value;
                renderings.add(new RendersInfo(nestedAnnotationValueMap, qn));
            }
        }
    }
    
    public List<RendersInfo> getRenderings() {
        return this.renderings;
    }
    
    /**
     * Represents a single rendering declared within a renderer annotation.
     */
    static public class RendersInfo {
        
        static String RENDERER_CLASS = "rendererClass";
        static String RENDERER_TYPE = "rendererType";
        static String COMPONENT_FAMILY = "componentFamily";
        
        Map annotationValueMap;
	String className;
        
        RendersInfo(Map annotationValueMap, String className) {
            this.annotationValueMap = annotationValueMap;
	    this.className = className;
        }
        
	/**
	 *  The renderer class (the qualified name by default).
	 */
        public String getRendererClass() {
            if (this.annotationValueMap.containsKey(RENDERER_CLASS)) {
                return (String) this.annotationValueMap.get(RENDERER_CLASS);
	    }
	    return this.className;
	}

        /**
         * The renderer type.
         */
        public String getRendererType() {
            if (this.annotationValueMap.containsKey(RENDERER_TYPE))
                return (String) this.annotationValueMap.get(RENDERER_TYPE);
            String[] componentFamilies = this.getComponentFamilies();
            if (componentFamilies.length > 0)
                return componentFamilies[0];
            return null;
        }
        
        /**
         * One or more component families to which this render type applies.
         */
        public String[] getComponentFamilies() {
            if (this.annotationValueMap.containsKey(COMPONENT_FAMILY)) {
                List componentFamilies = (List) this.annotationValueMap.get(COMPONENT_FAMILY);
                if (componentFamilies != null)
                    return (String[]) componentFamilies.toArray(new String[componentFamilies.size()]);
            }
            return new String[0];
        }
    }
    
}
