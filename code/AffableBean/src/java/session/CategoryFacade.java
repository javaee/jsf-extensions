/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.Category;
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
public class CategoryFacade {
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;

    public void create(Category category) {
        em.persist(category);
    }

    public void edit(Category category) {
        em.merge(category);
    }

    public void remove(Category category) {
        em.remove(em.merge(category));
    }

    public Category find(Object id) {
        return em.find(Category.class, id);
    }

    public List<Category> findAll() {
        return em.createQuery("select object(o) from Category as o").getResultList();
    }

    public List<Category> findRange(int[] range) {
        Query q = em.createQuery("select object(o) from Category as o");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        return ((Long) em.createQuery("select count(o) from Category as o").getSingleResult()).intValue();
    }

}
