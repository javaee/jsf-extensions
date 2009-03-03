/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database.entity.controller;

import database.entity.CustomerBasket;
import database.entity.controller.exceptions.NonexistentEntityException;
import database.entity.controller.exceptions.RollbackFailureException;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;

/**
 *
 * @author troy
 */
public class CustomerBasketJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "AffableBeanPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CustomerBasket customerBasket) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(customerBasket);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CustomerBasket customerBasket) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            customerBasket = em.merge(customerBasket);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customerBasket.getId();
                if (findCustomerBasket(id) == null) {
                    throw new NonexistentEntityException("The customerBasket with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CustomerBasket customerBasket;
            try {
                customerBasket = em.getReference(CustomerBasket.class, id);
                customerBasket.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customerBasket with id " + id + " no longer exists.", enfe);
            }
            em.remove(customerBasket);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CustomerBasket> findCustomerBasketEntities() {
        return findCustomerBasketEntities(true, -1, -1);
    }

    public List<CustomerBasket> findCustomerBasketEntities(int maxResults, int firstResult) {
        return findCustomerBasketEntities(false, maxResults, firstResult);
    }

    private List<CustomerBasket> findCustomerBasketEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CustomerBasket as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CustomerBasket findCustomerBasket(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CustomerBasket.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerBasketCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from CustomerBasket as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
