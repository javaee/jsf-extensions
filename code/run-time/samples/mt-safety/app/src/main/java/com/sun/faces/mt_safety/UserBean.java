package com.sun.faces.mt_safety;

import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Named;

@Named
@SessionScoped
public class UserBean implements Serializable {
    
    public String getName() {
        Flash myFlash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
        return myFlash.getClass().getName();
    }


    
    
}

