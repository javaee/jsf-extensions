/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database.entity.controller;

import database.entity.CustomerOrder;
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
import database.entity.Customer;
import database.entity.OrderedProduct;
import java.util.ArrayList;
import java.util.Collection;
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
        if (customerOrder.getOrderedProductCollection() == null) {
            customerOrder.setOrderedProductCollection(new ArrayList<OrderedProduct>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Customer customerId = customerOrder.getCustomerId();
            if (customerId != null) {
                customerId = em.getReference(customerId.getClass(), customerId.getCustomerId());
                customerOrder.setCustomerId(customerId);
            }
            Collection<OrderedProduct> attachedOrderedProductCollection = new ArrayList<OrderedProduct>();
            for (OrderedProduct orderedProductCollectionOrderedProductToAttach : customerOrder.getOrderedProductCollection()) {
                orderedProductCollectionOrderedProductToAttach = em.getReference(orderedProductCollectionOrderedProductToAttach.getClass(), orderedProductCollectionOrderedProductToAttach.getOrderedProductPK());
                attachedOrderedProductCollection.add(orderedProductCollectionOrderedProductToAttach);
            }
            customerOrder.setOrderedProductCollection(attachedOrderedProductCollection);
            em.persist(customerOrder);
            if (customerId != null) {
                customerId.getCustomerOrderCollection().add(customerOrder);
                customerId = em.merge(customerId);
            }
            for (OrderedProduct orderedProductCollectionOrderedProduct : customerOrder.getOrderedProductCollection()) {
                CustomerOrder oldCustomerOrderOfOrderedProductCollectionOrderedProduct = orderedProductCollectionOrderedProduct.getCustomerOrder();
                orderedProductCollectionOrderedProduct.setCustomerOrder(customerOrder);
                orderedProductCollectionOrderedProduct = em.merge(orderedProductCollectionOrderedProduct);
                if (oldCustomerOrderOfOrderedProductCollectionOrderedProduct != null) {
                    oldCustomerOrderOfOrderedProductCollectionOrderedProduct.getOrderedProductCollection().remove(orderedProductCollectionOrderedProduct);
                    oldCustomerOrderOfOrderedProductCollectionOrderedProduct = em.merge(oldCustomerOrderOfOrderedProductCollectionOrderedProduct);
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

    public void edit(CustomerOrder customerOrder) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CustomerOrder persistentCustomerOrder = em.find(CustomerOrder.class, customerOrder.getOrderId());
            Customer customerIdOld = persistentCustomerOrder.getCustomerId();
            Customer customerIdNew = customerOrder.getCustomerId();
            Collection<OrderedProduct> orderedProductCollectionOld = persistentCustomerOrder.getOrderedProductCollection();
            Collection<OrderedProduct> orderedProductCollectionNew = customerOrder.getOrderedProductCollection();
            List<String> illegalOrphanMessages = null;
            for (OrderedProduct orderedProductCollectionOldOrderedProduct : orderedProductCollectionOld) {
                if (!orderedProductCollectionNew.contains(orderedProductCollectionOldOrderedProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderedProduct " + orderedProductCollectionOldOrderedProduct + " since its customerOrder field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (customerIdNew != null) {
                customerIdNew = em.getReference(customerIdNew.getClass(), customerIdNew.getCustomerId());
                customerOrder.setCustomerId(customerIdNew);
            }
            Collection<OrderedProduct> attachedOrderedProductCollectionNew = new ArrayList<OrderedProduct>();
            for (OrderedProduct orderedProductCollectionNewOrderedProductToAttach : orderedProductCollectionNew) {
                orderedProductCollectionNewOrderedProductToAttach = em.getReference(orderedProductCollectionNewOrderedProductToAttach.getClass(), orderedProductCollectionNewOrderedProductToAttach.getOrderedProductPK());
                attachedOrderedProductCollectionNew.add(orderedProductCollectionNewOrderedProductToAttach);
            }
            orderedProductCollectionNew = attachedOrderedProductCollectionNew;
            customerOrder.setOrderedProductCollection(orderedProductCollectionNew);
            customerOrder = em.merge(customerOrder);
            if (customerIdOld != null && !customerIdOld.equals(customerIdNew)) {
                customerIdOld.getCustomerOrderCollection().remove(customerOrder);
                customerIdOld = em.merge(customerIdOld);
            }
            if (customerIdNew != null && !customerIdNew.equals(customerIdOld)) {
                customerIdNew.getCustomerOrderCollection().add(customerOrder);
                customerIdNew = em.merge(customerIdNew);
            }
            for (OrderedProduct orderedProductCollectionNewOrderedProduct : orderedProductCollectionNew) {
                if (!orderedProductCollectionOld.contains(orderedProductCollectionNewOrderedProduct)) {
                    CustomerOrder oldCustomerOrderOfOrderedProductCollectionNewOrderedProduct = orderedProductCollectionNewOrderedProduct.getCustomerOrder();
                    orderedProductCollectionNewOrderedProduct.setCustomerOrder(customerOrder);
                    orderedProductCollectionNewOrderedProduct = em.merge(orderedProductCollectionNewOrderedProduct);
                    if (oldCustomerOrderOfOrderedProductCollectionNewOrderedProduct != null && !oldCustomerOrderOfOrderedProductCollectionNewOrderedProduct.equals(customerOrder)) {
                        oldCustomerOrderOfOrderedProductCollectionNewOrderedProduct.getOrderedProductCollection().remove(orderedProductCollectionNewOrderedProduct);
                        oldCustomerOrderOfOrderedProductCollectionNewOrderedProduct = em.merge(oldCustomerOrderOfOrderedProductCollectionNewOrderedProduct);
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
                Integer id = customerOrder.getOrderId();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CustomerOrder customerOrder;
            try {
                customerOrder = em.getReference(CustomerOrder.class, id);
                customerOrder.getOrderId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The customerOrder with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<OrderedProduct> orderedProductCollectionOrphanCheck = customerOrder.getOrderedProductCollection();
            for (OrderedProduct orderedProductCollectionOrphanCheckOrderedProduct : orderedProductCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This CustomerOrder (" + customerOrder + ") cannot be destroyed since the OrderedProduct " + orderedProductCollectionOrphanCheckOrderedProduct + " in its orderedProductCollection field has a non-nullable customerOrder field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Customer customerId = customerOrder.getCustomerId();
            if (customerId != null) {
                customerId.getCustomerOrderCollection().remove(customerOrder);
                customerId = em.merge(customerId);
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
