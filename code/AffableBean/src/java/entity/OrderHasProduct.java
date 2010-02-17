/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author troy
 */
@Entity
@Table(name = "order_has_product")
@NamedQueries({
    @NamedQuery(name = "OrderHasProduct.findAll", query = "SELECT o FROM OrderHasProduct o"),
    @NamedQuery(name = "OrderHasProduct.findByOrderId", query = "SELECT o FROM OrderHasProduct o WHERE o.orderHasProductPK.orderId = :orderId"),
    @NamedQuery(name = "OrderHasProduct.findByProductId", query = "SELECT o FROM OrderHasProduct o WHERE o.orderHasProductPK.productId = :productId"),
    @NamedQuery(name = "OrderHasProduct.findByQuantity", query = "SELECT o FROM OrderHasProduct o WHERE o.quantity = :quantity")})
public class OrderHasProduct implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected OrderHasProductPK orderHasProductPK;
    @Basic(optional = false)
    @Column(name = "quantity")
    private String quantity;
    @JoinColumn(name = "order_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private CustomerOrder customerOrder;
    @JoinColumn(name = "product_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Product product;

    public OrderHasProduct() {
    }

    public OrderHasProduct(OrderHasProductPK orderHasProductPK) {
        this.orderHasProductPK = orderHasProductPK;
    }

    public OrderHasProduct(OrderHasProductPK orderHasProductPK, String quantity) {
        this.orderHasProductPK = orderHasProductPK;
        this.quantity = quantity;
    }

    public OrderHasProduct(int orderId, int productId) {
        this.orderHasProductPK = new OrderHasProductPK(orderId, productId);
    }

    public OrderHasProductPK getOrderHasProductPK() {
        return orderHasProductPK;
    }

    public void setOrderHasProductPK(OrderHasProductPK orderHasProductPK) {
        this.orderHasProductPK = orderHasProductPK;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (orderHasProductPK != null ? orderHasProductPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderHasProduct)) {
            return false;
        }
        OrderHasProduct other = (OrderHasProduct) object;
        if ((this.orderHasProductPK == null && other.orderHasProductPK != null) || (this.orderHasProductPK != null && !this.orderHasProductPK.equals(other.orderHasProductPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderHasProduct[orderHasProductPK=" + orderHasProductPK + "]";
    }

}
