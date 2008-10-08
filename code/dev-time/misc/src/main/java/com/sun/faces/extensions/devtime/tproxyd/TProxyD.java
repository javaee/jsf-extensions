/*
 * TProxyD.java
 *
 * Created on July 9, 2006, 2:00 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package com.sun.faces.extensions.devtime.tproxyd;

import java.io.InputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import javax.net.ServerSocketFactory;
import javax.net.SocketFactory;

/**
 *
 * @author edburns
 */
public class TProxyD {
    
    /** Creates a new instance of TProxyD */
    private TProxyD() {
    }
    
    private InetAddress targetAddress;
    
    private static final int DEFAULT_TARGET_PORT = 8080;
    private int targetPort;
    
    private static final int DEFAULT_PORT = 8070;
    private int myPort;
    
    private void parseArgs(String[] args) throws Exception {
        myPort = DEFAULT_PORT;
        
        targetAddress = InetAddress.getLocalHost();
        targetPort = DEFAULT_TARGET_PORT;
    }
    
    private void printUsage() {
        
    }
    
    private String readRequest(InputStream is) {
        String result = null;
        
        return result;
    }
    
    private void doProxy() throws Exception {
        ServerSocketFactory serverFactory = ServerSocketFactory.getDefault();
        SocketFactory targetFactory = SocketFactory.getDefault();
        ServerSocket s = null;
        Socket client = null, target = null;
        boolean keepRunning = true;
        String clientRequest = null;
        do {
            s = serverFactory.createServerSocket(myPort);
            client = s.accept();
            clientRequest = readRequest(client.getInputStream());
            
        } while (keepRunning);
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        TProxyD instance = new TProxyD();
        try {
            instance.parseArgs(args);
            instance.doProxy();
        }
        catch (Throwable e) {
            instance.printUsage();
        }
    }
    
}
