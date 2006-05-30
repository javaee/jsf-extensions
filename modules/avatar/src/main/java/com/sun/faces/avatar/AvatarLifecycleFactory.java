package com.sun.faces.avatar;

import java.util.Iterator;

import javax.faces.lifecycle.Lifecycle;
import javax.faces.lifecycle.LifecycleFactory;

public class AvatarLifecycleFactory extends LifecycleFactory {
    
    private final LifecycleFactory parent;

    public AvatarLifecycleFactory(LifecycleFactory parent) {
        this.parent = parent;
        this.addLifecycle(AvatarLifecycle.LIFECYCLE_ID, new AvatarLifecycle(getLifecycle(LifecycleFactory.DEFAULT_LIFECYCLE)));
    }

    public void addLifecycle(String id, Lifecycle lf) {
        this.parent.addLifecycle(id, lf);
    }

    public Lifecycle getLifecycle(String id) {
        return this.parent.getLifecycle(id);
    }

    public Iterator<String> getLifecycleIds() {
        return this.parent.getLifecycleIds();
    }

}
