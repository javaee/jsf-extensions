package com.sun.faces.avatar.client;

import javax.faces.context.ResponseWriter;
import javax.faces.context.ResponseWriterWrapper;

public class ClientWriter extends ResponseWriterWrapper {

    private final ResponseWriter rw;
    
    public ClientWriter(ResponseWriter rw) {
        this.rw = rw;
    }

    protected ResponseWriter getWrapped() {
        return this.rw;
    }

}
