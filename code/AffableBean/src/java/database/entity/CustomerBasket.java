/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author troy
 */
@Entity
@Table(name = "customer_basket")
@NamedQueries({@NamedQuery(name = "CustomerBasket.findAll", query = "SELECT c FROM CustomerBasket c"), @NamedQuery(name = "CustomerBasket.findById", query = "SELECT c FROM CustomerBasket c WHERE c.id = :id"), @NamedQuery(name = "CustomerBasket.findByQuantity", query = "SELECT c FROM CustomerBasket c WHERE c.quantity = :quantity"), @NamedQuery(name = "CustomerBasket.findByDateLastLogin", query = "SELECT c FROM CustomerBasket c WHERE c.dateLastLogin = :dateLastLogin")})
public class CustomerBasket implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "quantity")
    private String quantity;
    @Basic(optional = false)
    @Column(name = "date_last_login")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateLastLogin;

    public CustomerBasket() {
    }

    public CustomerBasket(Integer id) {
        this.id = id;
    }

    public CustomerBasket(Integer id, String quantity, Date dateLastLogin) {
        this.id = id;
        this.quantity = quantity;
        this.dateLastLogin = dateLastLogin;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Date getDateLastLogin() {
        return dateLastLogin;
    }

    public void setDateLastLogin(Date dateLastLogin) {
        this.dateLastLogin = dateLastLogin;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CustomerBasket)) {
            return false;
        }
        CustomerBasket other = (CustomerBasket) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CustomerBasket[id=" + id + "]";
    }

}
