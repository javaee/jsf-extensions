/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2018 Oracle and/or its affiliates. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License.  You can
 * obtain a copy of the License at
 * https://oss.oracle.com/licenses/CDDL+GPL-1.1
 * or LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at LICENSE.txt.
 *
 * GPL Classpath Exception:
 * Oracle designates this particular file as subject to the "Classpath"
 * exception as provided by Oracle in the GPL Version 2 section of the License
 * file that accompanied this code.
 *
 * Modifications:
 * If applicable, add the following below the License Header, with the fields
 * enclosed by brackets [] replaced by your own identifying information:
 * "Portions Copyright [year] [name of copyright owner]"
 *
 * Contributor(s):
 * If you wish your version of this file to be governed by only the CDDL or
 * only the GPL Version 2, indicate your decision by adding "[Contributor]
 * elects to include this software in this distribution under the [CDDL or GPL
 * Version 2] license."  If you don't indicate a single choice of license, a
 * recipient has the option to distribute your version of this file under
 * either the CDDL, the GPL Version 2 or to extend the choice of license to
 * its licensees as provided above.  However, if you add GPL Version 2 code
 * and therefore, elected the GPL Version 2 license, then the option applies
 * only if the new code is made subject to such option by the copyright
 * holder.
 */

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
