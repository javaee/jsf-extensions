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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A map for storing internationalized properties during file generation.
 *
 * @author gjmurphy
 */
public class PropertyBundleMap implements Map {
    
    Map<Object,Object> map = new HashMap<Object,Object>();
    List<Object> keyList = new ArrayList<Object>();
    
    PropertyBundleMap(String qualifiedName) {
        this.setQualifiedName(qualifiedName);
    }

    private String qualifiedName;

    public String getQualifiedName() {
        return this.qualifiedName;
    }
    
    void setQualifiedName(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }

    public Object remove(Object key) {
        this.keyList.remove(key);
        return this.map.remove(key);
    }

    public Object get(Object key) {
        return this.map.get(key);
    }

    public boolean containsKey(Object key) {
        return this.map.containsKey(key);
    }

    public boolean containsValue(Object value) {
        return this.map.containsValue(value);
    }

    public Collection<Object> values() {
        return this.map.values();
    }

    public int size() {
        return this.map.size();
    }

    public Object put(Object key, Object value) {
        if (this.map.put(key, value) == null) {
            this.keyList.add(key);
            return null;
        }
        return key;
    }
    
    public void putAll(Map map) {
        this.map.putAll(map);
        this.keyList.addAll(map.keySet());
    }

    public boolean isEmpty() {
        return this.map.isEmpty();
    }

    public void clear() {
        this.map.clear();
        this.keyList.clear();
    }

    public Set<Map.Entry<Object, Object>> entrySet() {
        return this.map.entrySet();
    }

    public Set<Object> keySet() {
        return this.map.keySet();
    }
    
    public List<Object> keyList() {
        return this.keyList;
    }
    
}