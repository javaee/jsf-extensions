/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database.entity.controller;

import database.entity.Customer;
import database.entity.controller.exceptions.IllegalOrphanException;
import database.entity.controller.exceptions.NonexistentEntityException;
import database.entity.controller.exceptions.RollbackFailureException;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import database.entity.CustomerOrder;
import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.UserTransaction;

/**
 *
 * @author troy
 */
public class CustomerJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "AffableBeanPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Customer customer) throws RollbackFailureException, Exception {
        if (customer.getCustomerOrderCollection() == null) {
            customer.setCustomerOrderCollection(new ArrayList<CustomerOrder>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<CustomerOrder> attachedCustomerOrderCollection = new ArrayList<CustomerOrder>();
            for (CustomerOrder customerOrderCollectionCustomerOrderToAttach : customer.getCustomerOrderCollection()) {
                customerOrderCollectionCustomerOrderToAttach = em.getReference(customerOrderCollectionCustomerOrderToAttach.getClass(), customerOrderCollectionCustomerOrderToAttach.getOrderId());
                attachedCustomerOrderCollection.add(customerOrderCollectionCustomerOrderToAttach);
            }
            customer.setCustomerOrderCollection(attachedCustomerOrderCollection);
            em.persist(customer);
            for (CustomerOrder customerOrderCollectionCustomerOrder : customer.getCustomerOrderCollection()) {
                Customer oldCustomerIdOfCustomerOrderCollectionCustomerOrder = customerOrderCollectionCustomerOrder.getCustomerId();
                customerOrderCollectionCustomerOrder.setCustomerId(customer);
                customerOrderCollectionCustomerOrder = em.merge(customerOrderCollectionCustomerOrder);
                if (oldCustomerIdOfCustomerOrderCollectionCustomerOrder != null) {
                    oldCustomerIdOfCustomerOrderCollectionCustomerOrder.getCustomerOrderCollection().remove(customerOrderCollectionCustomerOrder);
                    oldCustomerIdOfCustomerOrderCollectionCustomerOrder = em.merge(oldCustomerIdOfCustomerOrderCollectionCustomerOrder);
                }
            }
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

    public void edit(Customer customer) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer persistentCustomer = em.find(Customer.class, customer.getCustomerId());
            Collection<CustomerOrder> customerOrderCollectionOld = persistentCustomer.getCustomerOrderCollection();
            Collection<CustomerOrder> customerOrderCollectionNew = customer.getCustomerOrderCollection();
            List<String> illegalOrphanMessages = null;
            for (CustomerOrder customerOrderCollectionOldCustomerOrder : customerOrderCollectionOld) {
                if (!customerOrderCollectionNew.contains(customerOrderCollectionOldCustomerOrder)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain CustomerOrder " + customerOrderCollectionOldCustomerOrder + " since its customerId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<CustomerOrder> attachedCustomerOrderCollectionNew = new ArrayList<CustomerOrder>();
            for (CustomerOrder customerOrderCollectionNewCustomerOrderToAttach : customerOrderCollectionNew) {
                customerOrderCollectionNewCustomerOrderToAttach = em.getReference(customerOrderCollectionNewCustomerOrderToAttach.getClass(), customerOrderCollectionNewCustomerOrderToAttach.getOrderId());
                attachedCustomerOrderCollectionNew.add(customerOrderCollectionNewCustomerOrderToAttach);
            }
            customerOrderCollectionNew = attachedCustomerOrderCollectionNew;
            customer.setCustomerOrderCollection(customerOrderCollectionNew);
            customer = em.merge(customer);
            for (CustomerOrder customerOrderCollectionNewCustomerOrder : customerOrderCollectionNew) {
                if (!customerOrderCollectionOld.contains(customerOrderCollectionNewCustomerOrder)) {
                    Customer oldCustomerIdOfCustomerOrderCollectionNewCustomerOrder = customerOrderCollectionNewCustomerOrder.getCustomerId();
                    customerOrderCollectionNewCustomerOrder.setCustomerId(customer);
                    customerOrderCollectionNewCustomerOrder = em.merge(customerOrderCollectionNewCustomerOrder);
                    if (oldCustomerIdOfCustomerOrderCollectionNewCustomerOrder != null && !oldCustomerIdOfCustomerOrderCollectionNewCustomerOrder.equals(customer)) {
                        oldCustomerIdOfCustomerOrderCollectionNewCustomerOrder.getCustomerOrderCollection().remove(customerOrderCollectionNewCustomerOrder);
                        oldCustomerIdOfCustomerOrderCollectionNewCustomerOrder = em.merge(oldCustomerIdOfCustomerOrderCollectionNewCustomerOrder);
                    }
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Short id = customer.getCustomerId();
                if (findCustomer(id) == null) {
                    throw new NonexistentEntityException("The customer with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Short id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer customer;
            try {
                customer = em.getReference(Customer.class, id);
                customer.getCustomerId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customer with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<CustomerOrder> customerOrderCollectionOrphanCheck = customer.getCustomerOrderCollection();
            for (CustomerOrder customerOrderCollectionOrphanCheckCustomerOrder : customerOrderCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Customer (" + customer + ") cannot be destroyed since the CustomerOrder " + customerOrderCollectionOrphanCheckCustomerOrder + " in its customerOrderCollection field has a non-nullable customerId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(customer);
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

    public List<Customer> findCustomerEntities() {
        return findCustomerEntities(true, -1, -1);
    }

    public List<Customer> findCustomerEntities(int maxResults, int firstResult) {
        return findCustomerEntities(false, maxResults, firstResult);
    }

    private List<Customer> findCustomerEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Customer as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Customer findCustomer(Short id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Customer.class, id);
        } finally {
            em.close();
        }
    }

    public int getCustomerCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Customer as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
