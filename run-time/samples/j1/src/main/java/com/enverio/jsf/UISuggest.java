package com.enverio.jsf;

import com.enverio.jsf.render.ServerSuggestRenderer;
import java.io.IOException;
import java.util.Map;
import javax.el.MethodExpression;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.sun.faces.extensions.avatar.event.EventCallback;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.FacesEvent;
import javax.faces.render.Renderer;

public class UISuggest extends HtmlInputText implements PhaseListener {

    public final static String COMPONENT_FAMILY = "com.enverio";

    public final static String COMPONENT_TYPE = "com.enverio.Suggest";

    private MethodExpression from;

    public UISuggest() {
        super();
    }

    public String getFamily() {
        return COMPONENT_FAMILY;
    }

    public void afterPhase(PhaseEvent event) {
        FacesContext faces = event.getFacesContext();
        Map<String, String> p = faces.getExternalContext()
                .getRequestParameterMap();

        String clientId = p.get(COMPONENT_TYPE);
        if (clientId != null) {
            try {
                faces.getViewRoot().invokeOnComponent(faces, clientId, new EventCallback(clientId, "suggest"));
            } finally {
                // this event finishes the response
                faces.responseComplete();
            }
        }
    }

    public void beforePhase(PhaseEvent event) {
        // no op
    }

    public PhaseId getPhaseId() {
        return PhaseId.APPLY_REQUEST_VALUES;
    }

    public MethodExpression getFrom() {
        return from;
    }

    public void setFrom(MethodExpression suggest) {
        this.from = suggest;
    }

    public void restoreState(FacesContext faces, Object obj) {
        Object[] v = (Object[]) obj;
        super.restoreState(faces, v[0]);
        this.from = (MethodExpression) v[1];
    }

    public Object saveState(FacesContext faces) {
        Object[] v = new Object[2];
        v[0] = super.saveState(faces);
        v[1] = this.from;
        return v;
    }
    
    public void broadcast(FacesEvent event)
        throws AbortProcessingException {

        if (event == null) {
            throw new NullPointerException();
        }

        if (!(event instanceof SuggestEvent)) {
            return;
        }
        SuggestEvent suggestion = (SuggestEvent) event;
        FacesContext context = FacesContext.getCurrentInstance();
        
        Renderer r = getRenderer(context);
        if (null != r && r instanceof ServerSuggestRenderer) {
            try {
                ((ServerSuggestRenderer)r).onSuggest(context, 
                        suggestion.getSuggestSource());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
       
    }

}
