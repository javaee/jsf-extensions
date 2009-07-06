/*
 * Copyright 2007 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */


package cart;

import entity.Product;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.HashMap;
import java.util.List;


public class ShoppingCart {

    HashMap<String, ShoppingCartItem> items;
    int numberOfItems;

    public ShoppingCart() {
        items = new HashMap<String, ShoppingCartItem>();
        numberOfItems = 0;
    }

    public void add(String productId, Product product, String qty) {

        // cast quantity as int
        int quantity = Integer.parseInt(qty);

        if (items.containsKey(productId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(productId);
            if (quantity == 1) {
                scitem.incrementQuantity();
            } else {
                scitem.setQuantity(scitem.getQuantity() + quantity);
            }
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(product);
            if (quantity > 1) {
                newItem.setQuantity(quantity);
            }
            items.put(productId, newItem);
        }
    }

    public void update(String productId, Product product, String qty) {

        // cast quantity as int
        int quantity = Integer.parseInt(qty);

        if (items.containsKey(productId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(productId);
            if (quantity != 0) {
                // set item quantity to new value
                scitem.setQuantity(quantity);
            } else {
                // if quantity equals 0, remove from cart
                Iterator iterator = (items.keySet()).iterator();
                while(iterator.hasNext()) {
                    String key = iterator.next().toString();
                    ShoppingCartItem value = items.get(key);
                    if(value.equals(scitem)){
                        //if value exists in the map then print
                        items.remove(key);
                    }
                } //end while loop
            }
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(product);
            if (quantity > 1) {
                newItem.setQuantity(quantity);
            }
            items.put(productId, newItem);
        }
    }

    public synchronized void remove(String bookId) {
        if (items.containsKey(bookId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(bookId);
            scitem.decrementQuantity();

            if (scitem.getQuantity() <= 0) {
                items.remove(bookId);
            }

            numberOfItems--;
        }
    }

    public synchronized List<ShoppingCartItem> getItems() {
        List<ShoppingCartItem> results = new ArrayList<ShoppingCartItem>();
        results.addAll(this.items.values());

        return results;
    }

    @Override
    protected void finalize() throws Throwable {
        items.clear();
    }

    public synchronized int getNumberOfItems() {
        numberOfItems = 0;

        for (Iterator i = getItems()
                              .iterator(); i.hasNext();) {
            ShoppingCartItem item = (ShoppingCartItem) i.next();
            numberOfItems += item.getQuantity();
        }

        return numberOfItems;
    }

    public synchronized double getTotal() {
        double amount = 0.0;

        for (Iterator i = getItems()
                              .iterator(); i.hasNext();) {
            ShoppingCartItem item = (ShoppingCartItem) i.next();
            Product productDetails = (Product) item.getItem();

            amount += (item.getQuantity() * productDetails.getPrice());
        }

        return roundOff(amount);
    }

    private double roundOff(double x) {
        long val = Math.round(x * 100); // cents

        return val / 100.0;
    }

    public synchronized void clear() {
        System.out.println("Clearing cart.");
        items.clear();
        numberOfItems = 0;
    }
}
