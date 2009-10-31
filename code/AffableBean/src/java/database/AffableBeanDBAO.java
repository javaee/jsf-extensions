/*
 * Copyright 2009 Sun Microsystems, Inc.
 * All rights reserved.  You may not modify, use,
 * reproduce, or distribute this software except in
 * compliance with  the terms of the License at:
 * http://developer.sun.com/berkeley_license.html
 */


package database;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.*;
import java.util.*;
import exceptions.*;
import javax.persistence.*;


public class AffableBeanDBAO {

    private EntityManagerFactory emf;

    public AffableBeanDBAO() {
        emf = Persistence.createEntityManagerFactory("AffableBeanPU");
    }

    public List getCategories() throws CategoriesNotFoundException {

        EntityManager em = emf.createEntityManager();
        try {
            return em.createNamedQuery("Category.findAll").getResultList();
        } catch (Exception ex) {
            throw new CategoriesNotFoundException(
                    "Couldn't get categories: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public Category getCategory(String categoryId) throws CategoryNotFoundException {

        EntityManager em = emf.createEntityManager();

        try {
            // PK for Category is of type Short, so convert categoryId to Short
            Short catId = Short.parseShort(categoryId);
            Category requestedCategory = em.find(Category.class, catId);

            if (requestedCategory == null) {
                throw new CategoryNotFoundException(
                        "Couldn't get category: " + categoryId);
            }

            return requestedCategory;
        } finally {
            em.close();
        }
    }

    public List getCategoryProducts(String categoryId) throws ProductsNotFoundException {

        EntityManager em = emf.createEntityManager();

        // Product entity requires Category instance for categoryId
        Category c = new Category();

        // PK for Category is of type Short, so convert categoryId to Short
        Short cid = Short.parseShort(categoryId);

        c.setId(cid);

        try {

//            // This works if there are no foreign keys (findByCategoryId named query exists)
//            List<Product> productList =
//                    em.createNamedQuery("Product.findByCategoryId").
//                    setParameter("categoryId", c).
//                    getResultList();

            // use EntityManager's createQuery method to create a query in JP QL
            List<Product> productList =
                    em.createQuery("SELECT p FROM Product p WHERE p.categoryId = :categoryId").
                    setParameter("categoryId", c).
                    getResultList();

//            // the following is an example using EntityManager's createNativeQuery method
//            // which enables usage of SQL syntax native to the (MySQL) database
//            List<Product> productList =
//                    em.createNativeQuery("SELECT * FROM product WHERE category_id = " +
//                    categoryId, Product.class).
//                    getResultList();

            return productList;
        } catch (Exception ex) {
            throw new ProductsNotFoundException(
                    "Could not get products: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    public Product getProduct(String productId) throws ProductNotFoundException {

        EntityManager em = emf.createEntityManager();

        try {
            Integer pid = Integer.valueOf(productId);
            Product requestedProduct = em.find(Product.class, pid);

            if (requestedProduct == null) {
                throw new ProductNotFoundException("Couldn't find product: " + productId);
            }
            return requestedProduct;
        } finally {
            em.close();
        }

    }

    public Customer processOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber) {

        EntityManager em = emf.createEntityManager();

        EntityTransaction et = em.getTransaction();
        et.begin();

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCityRegion(cityRegion);
        customer.setCcNumber(ccNumber);

        try {
            em.persist(customer);

            et.commit();
        } catch (PersistenceException pe) {
            et.rollback();
            throw pe;
        } finally {
            em.close();
        }
        return customer;

    }

//    public void processOrder(Customer customer) {
//
//        em.persist(customer);
//
//        Customer testCustomer = em.find(Customer.class, 1);
//        testCustomer.setName("Jack Black");
//
//    }


    // TODO: buyProducts sounds like something what should happen in one transaction
    public void buyProducts(ShoppingCart cart) throws OrderException {
        List items = cart.getItems();
        Iterator i = items.iterator();

        try {
            while (i.hasNext()) {
                ShoppingCartItem sci = (ShoppingCartItem) i.next();
                Product product = (Product) sci.getItem();
                Integer id = product.getId();
                int quantity = sci.getQuantity();
                buyProduct(id, quantity);
            }
        } catch (Exception ex) {
            throw new OrderException("Commit failed: " + ex.getMessage());
        }
    }

    public void buyProduct(Integer productId, int quantity) throws OrderException {

        EntityManager em = emf.createEntityManager();

        try {
            Product requestedProduct = em.find(Product.class, productId);

//            if (requestedProduct != null) {
//                int inventory = requestedProduct.getInventory();
//
//                if ((inventory - quantity) >= 0) {
//                    int newInventory = inventory - quantity;
//                    requestedProduct.setInventory(newInventory);
//                } else {
//                    throw new OrderException(
//                            "Not enough of " + productId
//                            + " in stock to complete order.");
//                }
//            }
        } catch (Exception ex) {
            throw new OrderException(
                    "Couldn't purchase product: " + productId + ex.getMessage());
        }
    }

}
