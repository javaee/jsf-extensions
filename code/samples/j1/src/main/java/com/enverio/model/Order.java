package com.enverio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Order implements Serializable {
    
    private final List<OrderLine> lines = new ArrayList<OrderLine>();

    public Order() {
        super();
    }

    public List<OrderLine> getLines() {
        return lines;
    }
    
    public double getTotal() {
        double total = 0;
        for (OrderLine ol : this.lines) {
            total += ol.getTotal();
        }
        return total;
    }

}
