/*
 *
 * Created on 13 novembre 2006, 12.17
 */

package org.asyncfaces;

import com.sun.faces.extensions.avatar.lifecycle.ComponentEncoder;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.FacesException;

/**
 *
 * @author agori
 */
public class Target implements Serializable {
    
    private String clientId;
    private String serverHandler;
    private Map<String, String> parameters = new HashMap<String, String>();
    
    
    
    public boolean equals(Object obj) {
        if (null == obj || ! (obj instanceof Target)) {
            return false;
        }
        if (null == clientId || null == serverHandler) {
            return false;
        }
        
        Target target = (Target) obj;
        return clientId.equals(target.getClientId()) &&
                serverHandler.equals(target.getServerHandler());
    }
    
    public String getClientId() {
        return clientId;
    }
    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    
    
    public Map<String, String> getParameters() {
        return parameters;
    }
    
    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
    }
    
    public String getServerHandler() {
        return serverHandler;
    }
    
    public void setServerHandler(String serverHandler) {
        this.serverHandler = serverHandler;
    }
    
    public ComponentEncoder getEncoderInstance() {
        if (null == serverHandler) {
            return null;
        }
        try {
            Class clazz = Class.forName(serverHandler);
            return (ComponentEncoder) clazz.newInstance();
        } catch (Exception ex) {
            throw new FacesException(ex);
        }
    }
    
    public void print() {
        System.out.println("clientId = " + getClientId());
        System.out.println("serverHandler = " + getServerHandler());
        for (Map.Entry entry : getParameters().entrySet()) {
            System.out.println("param = " + entry.getKey() + ":" + entry.getValue());
        }
    }

    
}
