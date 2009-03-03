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
import javax.persistence.Lob;
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
@Table(name = "order")
@NamedQueries({@NamedQuery(name = "CustomerOrder.findAll", query = "SELECT c FROM CustomerOrder c"), @NamedQuery(name = "CustomerOrder.findById", query = "SELECT c FROM CustomerOrder c WHERE c.id = :id"), @NamedQuery(name = "CustomerOrder.findByDate", query = "SELECT c FROM CustomerOrder c WHERE c.date = :date"), @NamedQuery(name = "CustomerOrder.findByShippingmethod", query = "SELECT c FROM CustomerOrder c WHERE c.shippingmethod = :shippingmethod"), @NamedQuery(name = "CustomerOrder.findByAddress1", query = "SELECT c FROM CustomerOrder c WHERE c.address1 = :address1"), @NamedQuery(name = "CustomerOrder.findByAddress2", query = "SELECT c FROM CustomerOrder c WHERE c.address2 = :address2"), @NamedQuery(name = "CustomerOrder.findByCity", query = "SELECT c FROM CustomerOrder c WHERE c.city = :city"), @NamedQuery(name = "CustomerOrder.findByPostcode", query = "SELECT c FROM CustomerOrder c WHERE c.postcode = :postcode")})
public class CustomerOrder implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Basic(optional = false)
    @Column(name = "shippingmethod")
    private String shippingmethod;
    @Column(name = "address1")
    private String address1;
    @Column(name = "address2")
    private String address2;
    @Column(name = "city")
    private String city;
    @Column(name = "postcode")
    private String postcode;
    @Lob
    @Column(name = "comments")
    private String comments;

    public CustomerOrder() {
    }

    public CustomerOrder(Integer id) {
        this.id = id;
    }

    public CustomerOrder(Integer id, String shippingmethod) {
        this.id = id;
        this.shippingmethod = shippingmethod;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getShippingmethod() {
        return shippingmethod;
    }

    public void setShippingmethod(String shippingmethod) {
        this.shippingmethod = shippingmethod;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
        if (!(object instanceof CustomerOrder)) {
            return false;
        }
        CustomerOrder other = (CustomerOrder) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CustomerOrder[id=" + id + "]";
    }

}
