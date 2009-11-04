/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ejbs;

import entity.OrderedProduct;
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
public class OrderedProductFacade {
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;

    public void create(OrderedProduct orderedProduct) {
        em.persist(orderedProduct);
    }

    public void edit(OrderedProduct orderedProduct) {
        em.merge(orderedProduct);
    }

    public void remove(OrderedProduct orderedProduct) {
        em.remove(em.merge(orderedProduct));
    }

    public OrderedProduct find(Object id) {
        return em.find(OrderedProduct.class, id);
    }

    public List<OrderedProduct> findAll() {
        return em.createQuery("select object(o) from OrderedProduct as o").getResultList();
    }

    public List<OrderedProduct> findRange(int[] range) {
        Query q = em.createQuery("select object(o) from OrderedProduct as o");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        return ((Long) em.createQuery("select count(o) from OrderedProduct as o").getSingleResult()).intValue();
    }

}
