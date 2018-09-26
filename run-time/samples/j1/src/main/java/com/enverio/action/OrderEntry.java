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

package com.enverio.action;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.component.UIInput;
import javax.faces.component.UIOutput;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;

import com.enverio.model.Catalog;
import com.enverio.model.Company;
import com.enverio.model.Order;
import com.enverio.model.OrderLine;
import com.enverio.model.Product;
import com.enverio.model.Uom;
import com.enverio.client.ClientWriter;
import com.enverio.client.Effect;
import com.enverio.client.Element;
import com.enverio.client.Field;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;

public class OrderEntry implements Serializable {
    
    private final static Logger log = Logger.getLogger("demo.OrderEntry");

    public static void main(String[] argv) throws Exception {
        Company c = new Company();
        Catalog cat = c.getCatalog();
        Order order = new Order();

        OrderEntry oe = new OrderEntry();
        oe.setOrder(order);
        oe.setProduct(cat.findProduct(59339));
        oe.addProduct();
        oe.addProduct();
        oe.addProduct();

        System.out.println(order.getTotal());
    }

    private Order order;

    private Product product;
    
    private UIData data;
    
    private UIData header;
    
    private UIOutput total;
    
    private UIInput uom;
    
    private UIInput quantity;

    public OrderEntry() {
        super();
    }
    
    
    
    
    
    
    /**
     * Completely normal EL invocation
     */
    public void deleteLine() throws Exception {
    	OrderLine line = (OrderLine) this.header.getRowData();
    	this.order.getLines().remove(line);
    }
    
    
    
    
    /**
     * AJAX specific implementation which shows coordination
     * with the full view
     */
    public void addProduct() throws Exception {

        	
        	this.product = (Product) this.data.getRowData();
        	
        	String uomVal = (String) this.uom.getValue();
        	
        	Number qtyVal = (Number) this.quantity.getValue();
            if (qtyVal == null || qtyVal.intValue() == 0) {
            	qtyVal = new Integer(1);
            }
            
            this.order.getLines().add(0,
                    new OrderLine(this.product, uomVal, qtyVal.intValue()));
            
            
            //=========================================
            
            // Fan-out AJAX to update the UI based on Action Logic
            AsyncResponse.addToRenderList(this.total);
            AsyncResponse.addToRenderList(this.header);
            
            // optional JS effects
            /*******
            ClientWriter cw = ClientWriter.getInstance();
            cw.startScript(this.total);
            cw.select(this.total, Effect.pulsate());
            cw.select(this.uom, Effect.highlight());
            cw.select(this.quantity, Effect.highlight(), Field.clear());
            cw.endScript();
             ********/
    }
    
    
    
    
    

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public static SelectItem[] selectUom(List<Uom> list) {
        SelectItem[] si = new SelectItem[list.size()];
        Uom u;
        for (int i = 0; i < si.length; i++) {
            u = list.get(i);
            si[i] = new SelectItem(u.getUnits(), u.getUnits());
        }
        return si;
    }

    public UIData getData() {
        return data;
    }

    public void setData(UIData data) {
        this.data = data;
    }

    public UIOutput getTotal() {
        return total;
    }

    public void setTotal(UIOutput total) {
        this.total = total;
    }

	public UIInput getQuantity() {
		return quantity;
	}

	public void setQuantity(UIInput quantity) {
		this.quantity = quantity;
	}

	public UIInput getUom() {
		return uom;
	}

	public void setUom(UIInput uom) {
		this.uom = uom;
	}
	
	public void scrollDataGrid(ActionEvent event) {
        int currentRow = 1;
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent component = event.getComponent();
        Integer curRow = (Integer) component.getAttributes().get("currentRow");
        if (curRow != null)
            currentRow = curRow.intValue();

        if (this.data != null) {
            int rows = this.data.getRows();
            if (rows < 1)
                return;
            if (currentRow < 0)
                this.data.setFirst(0);
            else if (currentRow >= this.data.getRowCount())
                this.data.setFirst(this.data.getRowCount() - 1);
            else
                this.data.setFirst(currentRow - currentRow % rows);
        }
    }

	public UIData getHeader() {
		return header;
	}

	public void setHeader(UIData header) {
		this.header = header;
	}

}
