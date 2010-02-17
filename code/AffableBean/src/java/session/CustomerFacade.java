/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.Customer;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author troy
 */
@Stateless
public class CustomerFacade {
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;

    public void create(Customer customer) {
        em.persist(customer);
    }

    public void edit(Customer customer) {
        em.merge(customer);
    }

    public void remove(Customer customer) {
        em.remove(em.merge(customer));
    }

    public Customer find(Object id) {
        return em.find(Customer.class, id);
    }

    public List<Customer> findAll() {
        return em.createQuery("select object(o) from Customer as o").getResultList();
    }

    public List<Customer> findRange(int[] range) {
        Query q = em.createQuery("select object(o) from Customer as o");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        return ((Long) em.createQuery("select count(o) from Customer as o").getSingleResult()).intValue();
    }

}
