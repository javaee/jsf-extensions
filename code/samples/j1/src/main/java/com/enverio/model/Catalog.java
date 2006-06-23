package com.enverio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Catalog implements Serializable {

    private final List<Product> products = new ArrayList<Product>();
    
    public Catalog() {
        super();
    }
    
    public void addProduct(Product prd) {
        this.products.add(prd);
    }
    
    public Product findProduct(long id) {
        for (Product p : this.products) {
            if (id == p.getId()) {
                return p;
            }
        }
        return null;
    }

    public List<Product> getProducts() {
        return products;
    }
    

}
