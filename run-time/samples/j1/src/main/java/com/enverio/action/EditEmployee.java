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

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.faces.component.UIComponent;
import javax.faces.component.UIData;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.ValueChangeEvent;

import com.enverio.client.ClientWriter;
import com.enverio.client.Effect;
import com.sun.faces.extensions.avatar.lifecycle.AsyncResponse;

public class EditEmployee {

    private final static Logger log = Logger.getLogger("demo.EditEmployee");

    public void highlight(ValueChangeEvent event) {
        FacesContext faces = FacesContext.getCurrentInstance();
        UIComponent c = event.getComponent();

        try {
            ClientWriter cw = ClientWriter.getInstance();
            cw.startScript().select(c, Effect.highlight()).endScript().close();
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error in ValueChangeListener", e);
        } 
    }

    private transient UIData datagrid;

    public void setDataGrid(UIData data) {
        this.datagrid = data;
    }
    
    public UIData getDataGrid() {
        return this.datagrid;
    }

    public void scrollDataGrid(ActionEvent event) {
        int currentRow = 1;
        FacesContext context = FacesContext.getCurrentInstance();
        UIComponent component = event.getComponent();
        Integer curRow = (Integer) component.getAttributes().get("currentRow");
        if (curRow != null)
            currentRow = curRow.intValue();

        if (this.datagrid != null) {
            int rows = this.datagrid.getRows();
            if (rows < 1)
                return;
            if (currentRow < 0)
                this.datagrid.setFirst(0);
            else if (currentRow >= this.datagrid.getRowCount())
                this.datagrid.setFirst(this.datagrid.getRowCount() - 1);
            else
                this.datagrid.setFirst(currentRow - currentRow % rows);
        }
    }
}
