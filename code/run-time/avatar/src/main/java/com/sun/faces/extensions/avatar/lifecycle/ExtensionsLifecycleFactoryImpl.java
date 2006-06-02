/*
 * ExtensionsLifecycleFactoryImpl.java
 *
 * Created on May 15, 2006, 4:27 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.faces.extensions.avatar.lifecycle;

import java.util.Iterator;
import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

/**
 *
 * @author edburns
 */
public class ExtensionsLifecycleFactoryImpl extends LifecycleFactory {
    
    private LifecycleFactory parent = null;
    
    /** Creates a new instance of ExtensionsLifecycleFactoryImpl */
    public ExtensionsLifecycleFactoryImpl(LifecycleFactory parent) {
        this.parent = parent;
        this.parent.addLifecycle("com.sun.faces.lifecycle.AJAX",
                new AjaxLifecycle(this.parent.getLifecycle("DEFAULT")));
    }

    public Lifecycle getLifecycle(String string) {
        return parent.getLifecycle(string);
    }

    public void addLifecycle(String string, Lifecycle lifecycle) {
        parent.addLifecycle(string, lifecycle);
    }

    public Iterator<String> getLifecycleIds() {
        return parent.getLifecycleIds();
    }
    
    
}
