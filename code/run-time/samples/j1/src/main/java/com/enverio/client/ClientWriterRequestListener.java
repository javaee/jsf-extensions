/*
 * ClientWriterRequestListener.java
 *
 * Created on June 23, 2006, 6:15 AM
 */

package com.enverio.client;

import javax.servlet.ServletRequestListener;
import javax.servlet.ServletRequestEvent;

/**
 *
 * @author  edburns
 * @version
 *
 * Web application lifecycle listener.
 */

public class ClientWriterRequestListener implements ServletRequestListener {
    /**
     * ### Method from ServletRequestListener ###
     * 
     * The request is about to come into scope of the web application.
     */
    public void requestInitialized(ServletRequestEvent evt) {
        // TODO add your code here:
    }

    /**
     * ### Method from ServletRequestListener ###
     * 
     * The request is about to go out of scope of the web application.
     */
    public void requestDestroyed(ServletRequestEvent evt) {
        ClientWriter cw = ClientWriter.getInstance(false);
        if (null != cw) {
            cw.clearInstance();
        }
    }
}
