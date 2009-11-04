/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ejbs;

import entity.Category;
import entity.Product;
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
public class ProductFacade {
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;

    public void create(Product product) {
        em.persist(product);
    }

    public void edit(Product product) {
        em.merge(product);
    }

    public void remove(Product product) {
        em.remove(em.merge(product));
    }

    public Product find(int id) {
        return em.find(Product.class, id);
    }

    public List<Product> findAll() {
        return em.createQuery("select object(o) from Product as o").getResultList();
    }

    public List<Product> findForCategory(Category category) {
        return em.createQuery("SELECT p FROM Product p WHERE p.categoryId = :categoryId").
               setParameter("categoryId", category).getResultList();
    }

    public List<Product> findRange(int[] range) {
        Query q = em.createQuery("select object(o) from Product as o");
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        return ((Long) em.createQuery("select count(o) from Product as o").getSingleResult()).intValue();
    }

}
