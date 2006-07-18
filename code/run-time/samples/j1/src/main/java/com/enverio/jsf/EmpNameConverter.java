package com.enverio.jsf;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;

import com.enverio.model.Employee;

public class EmpNameConverter implements Converter {

    public EmpNameConverter() {
        super();
    }

    public Object getAsObject(FacesContext faces, UIComponent component, String str) {
        return str;
    }

    public String getAsString(FacesContext faces, UIComponent component, Object obj) {
        Employee emp = (Employee) obj;
        return emp.getLastName() + ", " + emp.getFirstName();
    }

}
