package com.enverio.jsf;

import java.io.Serializable;

import javax.el.MethodExpression;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

public class LookupConverter implements Converter, Serializable {
    
    private MethodExpression lookup;

    public LookupConverter() {
        super();
    }
    
    public LookupConverter(MethodExpression me) {
        this.lookup = me;
    }

    public Object getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || "".equals(value)) return null;
        return this.lookup.invoke(context.getELContext(), new Object[] { value });
    }

    public String getAsString(FacesContext context, UIComponent component, Object value) {
        return (value != null) ? "" : value.toString();
    }

    public MethodExpression getLookup() {
        return lookup;
    }

    public void setLookup(MethodExpression lookup) {
        this.lookup = lookup;
    }

}
