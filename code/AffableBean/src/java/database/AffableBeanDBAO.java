package database;

import entity.*;
import java.util.*;
import exceptions.*;
import javax.persistence.*;

public class AffableBeanDBAO {

    private EntityManager em;

    public AffableBeanDBAO(EntityManagerFactory emf) throws Exception {
        try {
            em = emf.createEntityManager();
        } catch (Exception ex) {
            throw new Exception(
                    "Couldn't open connection to database: " + ex.getMessage());
        }
    }

    public void remove() {
        try {
            em.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List getCategories() throws CategoriesNotFoundException {
        try {
            return em.createNamedQuery("Category.findAll").getResultList();
        } catch (Exception ex) {
            throw new CategoriesNotFoundException(
                    "Couldn't get categories: " + ex.getMessage());
        }
    }

    public Category getCategory(String categoryId) throws CategoryNotFoundException {

        // PK for Category is of type Short, so convert categoryId to Short
        Short catId = Short.parseShort(categoryId);
        Category requestedCategory = em.find(Category.class, catId);

        if (requestedCategory == null) {
            throw new CategoryNotFoundException(
                    "Couldn't get category: " + categoryId);
        }

        return requestedCategory;
    }

    public List getCategoryProducts(String categoryId) throws ProductsNotFoundException {

        // Product entity requires Category instance for categoryId
        Category c = new Category();

        // PK for Category is of type Short, so convert categoryId to Short
        Short cid = Short.parseShort(categoryId);

        c.setCategoryId(cid);

        try {

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
        }
    }

    public Product getProduct(String productId) throws ProductNotFoundException {

        Integer pid = Integer.valueOf(productId);
        Product requestedProduct = em.find(Product.class, pid);

        if (requestedProduct == null) {
            throw new ProductNotFoundException("Couldn't find product: " + productId);
        }

        return requestedProduct;
    }

//    public void buyBooks(ShoppingCart cart) throws OrderException {
//        Collection items = cart.getItems();
//        Iterator i = items.iterator();
//
//        try {
//            while (i.hasNext()) {
//                ShoppingCartItem sci = (ShoppingCartItem) i.next();
//                Book bd = (Book) sci.getItem();
//                String id = bd.getBookId();
//                int quantity = sci.getQuantity();
//                buyBook(id, quantity);
//            }
//        } catch (Exception ex) {
//            throw new OrderException("Commit failed: " + ex.getMessage());
//        }
//    }
//
//    public void buyBook(
//        String bookId,
//        int quantity) throws OrderException {
//        try {
//            Book requestedBook = em.find(Book.class, bookId);
//
//            if (requestedBook != null) {
//                int inventory = requestedBook.getInventory();
//
//                if ((inventory - quantity) >= 0) {
//                    int newInventory = inventory - quantity;
//                    requestedBook.setInventory(newInventory);
//                } else {
//                    throw new OrderException(
//                            "Not enough of " + bookId
//                            + " in stock to complete order.");
//                }
//            }
//        } catch (Exception ex) {
//            throw new OrderException(
//                    "Couldn't purchase book: " + bookId + ex.getMessage());
//        }
//    }
}
