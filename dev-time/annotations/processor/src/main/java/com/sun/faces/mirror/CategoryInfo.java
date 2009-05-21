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

import java.util.Map;

/**
 * Represents a category descriptor.
 *
 * @author gjmurphy
 */
public class CategoryInfo implements Comparable {
    
    static final String NAME = "name";
    static final String SORT_KEY = "sortKey";
    
    Map<String,Object> annotationValueMap;
    
    CategoryInfo(Map<String,Object> annotationValueMap) {
        this.annotationValueMap = annotationValueMap;
    }
    
    public String getName() {
        return (String) this.annotationValueMap.get(NAME);
    }
    
    public String getSortKey() {
        if (this.annotationValueMap.containsKey(SORT_KEY))
            return (String) this.annotationValueMap.get(SORT_KEY);
        return this.getName();
    }

    public boolean equals(Object obj) {
        if (obj instanceof CategoryInfo)
            return ((CategoryInfo) obj).getName().equals(this.getName());
        return false;
    }

    public int compareTo(Object obj) {
        if (obj instanceof CategoryInfo)
            return this.getSortKey().compareTo(((CategoryInfo) obj).getSortKey());
        return -1;
    }

    private String fieldName;

    public String getFieldName() {
        return this.fieldName;
    }

    void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }
    
}
