/*
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */


package cart;

import entity.Product;
import java.util.*;


public class ShoppingCart {

    HashMap<String, ShoppingCartItem> items;
    int numberOfItems;
    double total;

    public ShoppingCart() {
        items = new HashMap<String, ShoppingCartItem>();
        numberOfItems = 0;
        total = 0;
    }

    public synchronized void add(String productId, Product product) {

        if (items.containsKey(productId)) {
            ShoppingCartItem scitem = (ShoppingCartItem) items.get(productId);
            scitem.incrementQuantity();
        } else {
            ShoppingCartItem newItem = new ShoppingCartItem(product);
            items.put(productId, newItem);
        }
    }

    public synchronized void update(String productId, Product product, String quantity) {

        int qty = -1;

        // cast quantity as int
        qty = Integer.parseInt(quantity);

        if (qty >= 0) {
            if (items.containsKey(productId)) {
                ShoppingCartItem scitem = (ShoppingCartItem) items.get(productId);
                if (qty != 0) {
                    // set item quantity to new value
                    scitem.setQuantity(qty);
                } else {
                    // if quantity equals 0, remove from cart
                    Iterator iterator = (items.keySet()).iterator();

                    String keyMatch = "";

                    while (iterator.hasNext()) {
                        String key = iterator.next().toString();
                        ShoppingCartItem value = items.get(key);
                        if (value.equals(scitem)) {
                            // retain key
                            keyMatch = key;
                        }
                    } //end while loop

                    items.remove(keyMatch);
                }
            }
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

        for (Iterator i = getItems().iterator(); i.hasNext();) {
            ShoppingCartItem item = (ShoppingCartItem) i.next();
            numberOfItems += item.getQuantity();
        }

        return numberOfItems;
    }

    public synchronized double getSubtotal() {
        double amount = 0.00;

        for (Iterator i = getItems().iterator(); i.hasNext();) {
            ShoppingCartItem item = (ShoppingCartItem) i.next();
            Product productDetails = (Product) item.getItem();

            amount += (item.getQuantity() * productDetails.getPrice().doubleValue());
        }

        return roundOff(amount);
    }

    public synchronized void calculateTotal(String surcharge) {
        double s = Double.parseDouble(surcharge);
        double amount = 0.00;

        amount = getSubtotal();
        amount = amount + s;

        total = roundOff(amount);
    }

    public synchronized double getTotal() {
        return total;
    }

    public synchronized void clear() {
        System.out.println("Clearing cart.");
        items.clear();
        numberOfItems = 0;
    }

    private double roundOff(double x) {
        long val = Math.round(x * 100); // cents

        return val / 100.0;
    }
}
