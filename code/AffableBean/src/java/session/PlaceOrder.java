/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderHasProduct;
import entity.Product;
import java.math.BigDecimal;
import java.util.Iterator;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author troy
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PlaceOrder implements PlaceOrderLocal {

    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;
    @Resource
    private SessionContext context;

    @EJB
    private ProductFacade productFacade;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public boolean placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart) {

        try {
            Customer customer = addCustomer(name, email, phone, address, cityRegion, ccNumber);
            CustomerOrder order = addOrder(customer, cart);
//            addOrderedItems(cart, order);
            return true;
        } catch (Exception e) {
            context.setRollbackOnly();
            return false;
        }
    }

    public Customer addCustomer(String name, String email, String phone, String address, String cityRegion, String ccNumber) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCityRegion(cityRegion);
        customer.setCcNumber(ccNumber);

        em.persist(customer);
        return customer;
    }

    public CustomerOrder addOrder(Customer customer, ShoppingCart cart) {

        // set up customer order
        CustomerOrder order = new CustomerOrder();
        order.setCustomerId(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));

        em.persist(order);

        return order;
    }

    public void addOrderedItems(ShoppingCart cart, CustomerOrder order) {
        
        Iterator it = cart.getItems().keySet().iterator();

        // iterate through shopping cart and add items to OrderHasProduct
        while(it.hasNext()) {

            OrderHasProduct orderedItem = new OrderHasProduct();

            // set order id
            orderedItem.setCustomerOrder(order);

            // set product id
            Object productId = it.next();
            Product product = productFacade.find(Integer.parseInt((String) productId));
            orderedItem.setProduct(product);

            // set quantity
            ShoppingCartItem item = (ShoppingCartItem) cart.getItems().get(productId.toString());
            orderedItem.setQuantity(String.valueOf(item.getQuantity()));

            em.persist(orderedItem);

        }
    }
}