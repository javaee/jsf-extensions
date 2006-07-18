package com.enverio.model;

import java.io.Serializable;

public class Status implements Serializable {

    private boolean accepted;
    
    private String description;
    
    public Status() {
        super();
    }
    
    public Status(boolean accepted, String description) {
        this.accepted = accepted;
        this.description = description;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    public String toString() {
        return this.description;
    }
}
