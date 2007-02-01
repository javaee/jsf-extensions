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
