/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.OrderHasProduct;
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
public class OrderHasProductFacade {
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;

    public void create(OrderHasProduct orderHasProduct) {
        em.persist(orderHasProduct);
    }

    public void edit(OrderHasProduct orderHasProduct) {
        em.merge(orderHasProduct);
    }

    public void remove(OrderHasProduct orderHasProduct) {
        em.remove(em.merge(orderHasProduct));
    }

    public OrderHasProduct find(Object id) {
        return em.find(OrderHasProduct.class, id);
    }

    public List<OrderHasProduct> findAll() {
        return em.createQuery("select object(o) from OrderHasProduct as o").getResultList();
    }

    public List<OrderHasProduct> findRange(int[] range) {
        Query q = em.createQuery("select object(o) from OrderHasProduct as o");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        return ((Long) em.createQuery("select count(o) from OrderHasProduct as o").getSingleResult()).intValue();
    }

}
