package com.enverio.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Product implements Serializable {
    
    private String description;
    private long id;
    private String manufacturer;
    private final List<Uom> uoms = new ArrayList<Uom>(); 

    public Product() {
        super();
    }
    
    public void addUom(Uom uom) {
        this.uoms.add(uom);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }
    
    public Uom getUom(String name) {
        if (name != null) {
            for (Uom u : this.uoms) {
                if (name.equals(u.getUnits())) {
                    return u;
                }
            }
        }
        return null;
    }

    public List<Uom> getUoms() {
        return uoms;
    }

    public String toString() {
        return "Product["+this.id+","+this.description+","+this.manufacturer+"]";
    }

}
