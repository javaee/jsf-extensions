/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author troy
 */
@Embeddable
public class OrderedProductPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "custorder_id")
    private int custorderId;
    @Basic(optional = false)
    @Column(name = "product_id")
    private int productId;

    public OrderedProductPK() {
    }

    public OrderedProductPK(int custorderId, int productId) {
        this.custorderId = custorderId;
        this.productId = productId;
    }

    public int getCustorderId() {
        return custorderId;
    }

    public void setCustorderId(int custorderId) {
        this.custorderId = custorderId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) custorderId;
        hash += (int) productId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof OrderedProductPK)) {
            return false;
        }
        OrderedProductPK other = (OrderedProductPK) object;
        if (this.custorderId != other.custorderId) {
            return false;
        }
        if (this.productId != other.productId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.OrderedProductPK[custorderId=" + custorderId + ", productId=" + productId + "]";
    }

}
