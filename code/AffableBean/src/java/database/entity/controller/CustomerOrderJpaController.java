/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database.entity.controller;

import database.entity.CustomerOrder;
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
public class CustomerOrderJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "AffableBeanPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CustomerOrder customerOrder) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            em.persist(customerOrder);
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

    public void edit(CustomerOrder customerOrder) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            customerOrder = em.merge(customerOrder);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = customerOrder.getId();
                if (findCustomerOrder(id) == null) {
                    throw new NonexistentEntityException("The customerOrder with id " + id + " no longer exists.");
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
            CustomerOrder customerOrder;
            try {
                customerOrder = em.getReference(CustomerOrder.class, id);
                customerOrder.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customerOrder with id " + id + " no longer exists.", enfe);
            }
            em.remove(customerOrder);
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

    public List<CustomerOrder> findCustomerOrderEntities() {
        return findCustomerOrderEntities(true, -1, -1);
    }

    public List<CustomerOrder> findCustomerOrderEntities(int maxResults, int firstResult) {
        return findCustomerOrderEntities(false, maxResults, firstResult);
    }

    private List<CustomerOrder> findCustomerOrderEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from CustomerOrder as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public CustomerOrder findCustomerOrder(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CustomerOrder.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerOrderCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from CustomerOrder as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
