/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ejbs;

import entity.CustomerOrder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author david
 */
@Stateless
public class CustomerOrderFacade {
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;

    public void create(CustomerOrder customerOrder) {
        em.persist(customerOrder);
    }

    public void edit(CustomerOrder customerOrder) {
        em.merge(customerOrder);
    }

    public void remove(CustomerOrder customerOrder) {
        em.remove(em.merge(customerOrder));
    }

    public CustomerOrder find(Object id) {
        return em.find(CustomerOrder.class, id);
    }

    public List<CustomerOrder> findAll() {
        return em.createQuery("select object(o) from CustomerOrder as o").getResultList();
    }

    public List<CustomerOrder> findRange(int[] range) {
        Query q = em.createQuery("select object(o) from CustomerOrder as o");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        return ((Long) em.createQuery("select count(o) from CustomerOrder as o").getSingleResult()).intValue();
    }

}
