/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database.entity.controller;

import database.entity.OrderedProduct;
import database.entity.OrderedProductPK;
import database.entity.controller.exceptions.NonexistentEntityException;
import database.entity.controller.exceptions.PreexistingEntityException;
import database.entity.controller.exceptions.RollbackFailureException;
import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import database.entity.CustomerOrder;
import database.entity.Product;
import javax.transaction.UserTransaction;

/**
 *
 * @author troy
 */
public class OrderedProductJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "AffableBeanPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(OrderedProduct orderedProduct) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (orderedProduct.getOrderedProductPK() == null) {
            orderedProduct.setOrderedProductPK(new OrderedProductPK());
        }
        orderedProduct.getOrderedProductPK().setProductId(orderedProduct.getProduct().getProductId());
        orderedProduct.getOrderedProductPK().setOrderId(orderedProduct.getCustomerOrder().getOrderId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            CustomerOrder customerOrder = orderedProduct.getCustomerOrder();
            if (customerOrder != null) {
                customerOrder = em.getReference(customerOrder.getClass(), customerOrder.getOrderId());
                orderedProduct.setCustomerOrder(customerOrder);
            }
            Product product = orderedProduct.getProduct();
            if (product != null) {
                product = em.getReference(product.getClass(), product.getProductId());
                orderedProduct.setProduct(product);
            }
            em.persist(orderedProduct);
            if (customerOrder != null) {
                customerOrder.getOrderedProductCollection().add(orderedProduct);
                customerOrder = em.merge(customerOrder);
            }
            if (product != null) {
                product.getOrderedProductCollection().add(orderedProduct);
                product = em.merge(product);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findOrderedProduct(orderedProduct.getOrderedProductPK()) != null) {
                throw new PreexistingEntityException("OrderedProduct " + orderedProduct + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(OrderedProduct orderedProduct) throws NonexistentEntityException, RollbackFailureException, Exception {
        orderedProduct.getOrderedProductPK().setProductId(orderedProduct.getProduct().getProductId());
        orderedProduct.getOrderedProductPK().setOrderId(orderedProduct.getCustomerOrder().getOrderId());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            OrderedProduct persistentOrderedProduct = em.find(OrderedProduct.class, orderedProduct.getOrderedProductPK());
            CustomerOrder customerOrderOld = persistentOrderedProduct.getCustomerOrder();
            CustomerOrder customerOrderNew = orderedProduct.getCustomerOrder();
            Product productOld = persistentOrderedProduct.getProduct();
            Product productNew = orderedProduct.getProduct();
            if (customerOrderNew != null) {
                customerOrderNew = em.getReference(customerOrderNew.getClass(), customerOrderNew.getOrderId());
                orderedProduct.setCustomerOrder(customerOrderNew);
            }
            if (productNew != null) {
                productNew = em.getReference(productNew.getClass(), productNew.getProductId());
                orderedProduct.setProduct(productNew);
            }
            orderedProduct = em.merge(orderedProduct);
            if (customerOrderOld != null && !customerOrderOld.equals(customerOrderNew)) {
                customerOrderOld.getOrderedProductCollection().remove(orderedProduct);
                customerOrderOld = em.merge(customerOrderOld);
            }
            if (customerOrderNew != null && !customerOrderNew.equals(customerOrderOld)) {
                customerOrderNew.getOrderedProductCollection().add(orderedProduct);
                customerOrderNew = em.merge(customerOrderNew);
            }
            if (productOld != null && !productOld.equals(productNew)) {
                productOld.getOrderedProductCollection().remove(orderedProduct);
                productOld = em.merge(productOld);
            }
            if (productNew != null && !productNew.equals(productOld)) {
                productNew.getOrderedProductCollection().add(orderedProduct);
                productNew = em.merge(productNew);
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
                OrderedProductPK id = orderedProduct.getOrderedProductPK();
                if (findOrderedProduct(id) == null) {
                    throw new NonexistentEntityException("The orderedProduct with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(OrderedProductPK id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            OrderedProduct orderedProduct;
            try {
                orderedProduct = em.getReference(OrderedProduct.class, id);
                orderedProduct.getOrderedProductPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The orderedProduct with id " + id + " no longer exists.", enfe);
            }
            CustomerOrder customerOrder = orderedProduct.getCustomerOrder();
            if (customerOrder != null) {
                customerOrder.getOrderedProductCollection().remove(orderedProduct);
                customerOrder = em.merge(customerOrder);
            }
            Product product = orderedProduct.getProduct();
            if (product != null) {
                product.getOrderedProductCollection().remove(orderedProduct);
                product = em.merge(product);
            }
            em.remove(orderedProduct);
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

    public List<OrderedProduct> findOrderedProductEntities() {
        return findOrderedProductEntities(true, -1, -1);
    }

    public List<OrderedProduct> findOrderedProductEntities(int maxResults, int firstResult) {
        return findOrderedProductEntities(false, maxResults, firstResult);
    }

    private List<OrderedProduct> findOrderedProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from OrderedProduct as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public OrderedProduct findOrderedProduct(OrderedProductPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(OrderedProduct.class, id);
        } finally {
            em.close();
        }
    }

    public int getOrderedProductCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from OrderedProduct as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
