package com.sun.faces.avatar;

import javax.faces.FacesException;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseListener;
import javax.faces.lifecycle.Lifecycle;

public class AvatarLifecycle extends Lifecycle {
    
    public final static String LIFECYCLE_ID = "AVATAR";
    
    private final Lifecycle parent;

    public AvatarLifecycle(Lifecycle parent) {
        this.parent = parent;
    }

    public void addPhaseListener(PhaseListener listener) {
        this.parent.addPhaseListener(listener);
    }

    public void execute(FacesContext ctx) throws FacesException {
        PartialContext pc = PartialContext.getInstance();
        if (pc.isPartial()) {
            this.executePartial(ctx);
        } else {
            // TODO add elseif for resource
            this.parent.execute(ctx);
        }
    }
    
    public void executeResource(FacesContext ctx) throws FacesException {
        
    }
    
    private void executePartial(FacesContext ctx) throws FacesException {
        // setup response writer
        // etc
    }

    public PhaseListener[] getPhaseListeners() {
        return this.parent.getPhaseListeners();
    }

    public void removePhaseListener(PhaseListener listener) {
        this.parent.removePhaseListener(listener);
    }

    public void render(FacesContext ctx) throws FacesException {
        PartialContext pc = PartialContext.getInstance();
        if (pc.isPartial()) {
            this.renderPartial(ctx);
        } else {
            // TODO add resource
            this.parent.render(ctx);
        }
    }
    
    private void renderPartial(FacesContext ctx) throws FacesException {
        
    }

}
