package com.enverio.jsf;

import java.io.IOException;
import java.util.Map;

import javax.el.ELContext;
import javax.el.MethodExpression;
import javax.faces.component.ContextCallback;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.convert.Converter;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import com.sun.faces.extensions.avatar.event.EventCallback;

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
}
