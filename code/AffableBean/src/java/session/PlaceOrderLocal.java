/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import cart.ShoppingCart;
import entity.Customer;
import entity.CustomerOrder;
import javax.ejb.Local;

/**
 *
 * @author troy
 */
@Local
public interface PlaceOrderLocal {

    public boolean placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart);

    public Customer addCustomer(String name, String email, String phone, String address, String cityRegion, String ccNumber);

    public CustomerOrder addOrder(Customer customer, ShoppingCart cart);

    public void addOrderedItems(ShoppingCart cart, CustomerOrder order);
}