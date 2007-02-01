package com.enverio.model;

import java.io.Serializable;

public class OrderLine implements Serializable {
    
    private static long NEXT_ID = 1;
    
    private long id;

    private int quantity;
    
    private Product product;
    
    private String uom;
    
    public OrderLine() {
        super();
    }
    
    public OrderLine(Product product, String uom, int quantity) {
        this.id = NEXT_ID++;
        this.product = product;
        this.uom = uom;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUom() {
        return uom;
    }

    public void setUom(String uom) {
        this.uom = uom;
    }
    
    public double getTotal() {
        Uom u = this.getProductUom();
        if (u != null) {
            return this.quantity * u.getPrice();
        }
        return 0.0;
    }
    
    public Uom getProductUom() {
        return this.product.getUom(this.uom);
    }
    
    public Status getStatus() {
        Uom u = this.getProductUom();
        if (u == null) {
            return new Status(false, "UOM is invalid");
        }
        if (u.getQuantity() == 0) {
            return new Status(false, "Back Ordered");
        }
        if (u.getQuantity() < this.quantity) {
            return new Status(false, "Partial Back Order");
        }
        return null;
    }

    public String toString() {
        return "OrderLine["+this.id+","+this.product+","+this.uom+","+this.quantity+"]";
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
