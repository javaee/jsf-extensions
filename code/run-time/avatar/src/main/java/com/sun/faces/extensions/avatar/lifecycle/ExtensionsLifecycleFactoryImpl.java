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
        //check whether the lifecycleId already exists
        if (alreadyCreated("com.sun.faces.lifecycle.PARTIAL")) {
            return;
        }
        this.parent.addLifecycle("com.sun.faces.lifecycle.PARTIAL",
                new PartialTraversalLifecycle(this.parent.getLifecycle("DEFAULT")));
    }

    public Lifecycle getLifecycle(String string) {
        return parent.getLifecycle(string);
    }

    public void addLifecycle(String string, Lifecycle lifecycle) {
        //check whether the lifecycleId already exists
        if (alreadyCreated(string)) {
            return;
        }
        parent.addLifecycle(string, lifecycle);
    }

    public Iterator<String> getLifecycleIds() {
        return parent.getLifecycleIds();
    }
    
    boolean alreadyCreated(String lifecycleId) {
        Iterator<String> iter = this.getLifecycleIds();
        while(iter.hasNext()) {
            String existingId = iter.next();
            if (existingId.equals(lifecycleId)) {   //let NPE be thrown
                return true;
            }
        }
        return false;
    }
}
