/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common Development
 * and Distribution License("CDDL") (collectively, the "License").  You
 * may not use this file except in compliance with the License. You can obtain
 * a copy of the License at https://glassfish.dev.java.net/public/CDDL+GPL.html
 * or glassfish/bootstrap/legal/LICENSE.txt.  See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * When distributing the software, include this License Header Notice in each
 * file and include the License file at glassfish/bootstrap/legal/LICENSE.txt.
 * Sun designates this particular file as subject to the "Classpath" exception
 * as provided by Sun in the GPL Version 2 section of the License file that
 * accompanied this code.  If applicable, add the following below the License
 * Header, with the fields enclosed by brackets [] replaced by your own
 * identifying information: "Portions Copyrighted [year]
 * [name of copyright owner]"
 *
 * Contributor(s):
 *
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

package stockfaces;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import javax.faces.component.UIForm;
import javax.faces.component.UIGraphic;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.component.UIPanel;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 * This bean has methods to retrieve stock information from
 * the Yahoo quote service.
 */ 
public class Bean {

    private static final String SERVICE_URL =
            "http://quote.yahoo.com/d/quotes.csv";

    /**
     * Action method that is used to retrieve stock information.
     * This method uses two helper methods - one to get the
     * stock information, and the other to dynamically build
     * the "data" components for the UI.
     */
    public void getStockInfo(ActionEvent ae) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIForm form = (UIForm)context.getViewRoot().findComponent("form");
        if (form != null) {
            UIInput component = (UIInput)form.findComponent("proxyHost");
            String proxyHost = (String)component.getValue();
            component = (UIInput)form.findComponent("proxyPort");
            String proxyPort = (String)component.getValue();
            if (proxyHost != null && proxyPort != null) {
                try {
                    System.setProperty("http.proxyHost", proxyHost); 
                    System.setProperty("http.proxyPort", proxyPort); 
                } catch (SecurityException e) {
                }
            }
            component = (UIInput)form.findComponent("symbol");
            if (component != null) {
                String symbolInput = (String)component.getValue();
                if (symbolInput != null) {
                    String[] symbols = symbolInput.split("\\s");
                    String[] stockData = null;
                    try {
                        stockData = getStockData(symbols);
                        buildUI(stockData);
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    /**
     * Helper method to get the stock data.
     */
    private String[] getStockData(String[] symbols)
        throws IOException, MalformedURLException {
        String[] data = new String[symbols.length];
        for (int i=0; i<symbols.length; i++) {
            StringBuffer sb = new StringBuffer(SERVICE_URL);
            sb.append("?s=");
            sb.append(symbols[i]);
            sb.append("&f=snol1cp2v=.csv");
            String url = sb.toString();
            URLConnection urlConn = null;
            InputStreamReader inputReader = null;
            BufferedReader buff = null;
            try {
                urlConn = new URL(url).openConnection();
                inputReader = new InputStreamReader(
                    urlConn.getInputStream());
                buff = new BufferedReader(inputReader);
                data[i] = buff.readLine();
            } catch (MalformedURLException e){
            } catch (IOException ioe) {
            } finally {
                if (inputReader != null) {
                    try {
                        inputReader.close();
                        buff.close();
                    } catch (Exception e) {}
                }
            }
        }
        return data;
    }

    /**
     * Helper method to dynamically add JSF components to display
     * the data.
     */
    private void buildUI(String[] stockData) {
        FacesContext context = FacesContext.getCurrentInstance();
        UIForm form = (UIForm)context.getViewRoot().findComponent("form");
        UIPanel dataPanel = (UIPanel)form.findComponent("stockdata");
        String buffer = null;
        dataPanel.getChildren().clear();
        for (int i=0; i<stockData.length; i++) {
            buffer = stockData[i];
            buffer = buffer.replace( "\"", "" );
            String[] data = buffer.split("\\,");
            UIOutput outputComponent = null;
            UIGraphic imageComponent = null;
            double openPrice = 0;
            double lastPrice = 0;
            double change = 0;
            for (int j=0; j<7; j++) {
                outputComponent = new UIOutput();
                if (j < 2) {
                    outputComponent.setValue(data[j]); 
                }
                if (j == 2) {
                    openPrice = new Double(data[j]).doubleValue();
                    outputComponent.setValue(data[j]); 
                }
                if (j == 3) {
                    lastPrice = new Double(data[j]).doubleValue();
                    change = lastPrice - openPrice;
                    change = round(change, 2);
                    outputComponent.setValue(data[j]); 
                }
                if (j == 4) {
                    if (change < 0) {
                        imageComponent = new UIGraphic();
                        imageComponent.setUrl("images/down_r.gif");
                        dataPanel.getChildren().add(imageComponent);
                        outputComponent.getAttributes().put("styleClass",
                            "down-color");
                    } else if (change > 0) {
                        imageComponent = new UIGraphic();
                        imageComponent.setUrl("images/up_g.gif");
                        dataPanel.getChildren().add(imageComponent);
                        outputComponent.getAttributes().put("styleClass",
                            "up-color");
                    }
                    outputComponent.setValue(String.valueOf(change));
                } else {
                    if (j == 5) {
                        if (change < 0) {
                            outputComponent.getAttributes().put("styleClass",
                                "down-color");
                        } else if (change > 0) {
                            outputComponent.getAttributes().put("styleClass",
                                "up-color");
                        }
                    }
                    
                    outputComponent.setValue(data[j]); 
                }
                dataPanel.getChildren().add(outputComponent);
            }
        }
        dataPanel.setRendered(true);
    }

    private double round(double val, int places) {
	long factor = (long)Math.pow(10,places);

	// Shift the decimal the correct number of places
	// to the right.
	val = val * factor;

	// Round to the nearest integer.
	long tmp = Math.round(val);

	// Shift the decimal the correct number of places
	// back to the left.
	return (double)tmp / factor;
    }
}
