/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package database.entity.controller;

import database.entity.Product;
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
import database.entity.Category;
import database.entity.OrderedProduct;
import java.util.ArrayList;
import java.util.Collection;
import javax.transaction.UserTransaction;

/**
 *
 * @author troy
 */
public class ProductJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "AffableBeanPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Product product) throws RollbackFailureException, Exception {
        if (product.getOrderedProductCollection() == null) {
            product.setOrderedProductCollection(new ArrayList<OrderedProduct>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Category categoryId = product.getCategoryId();
            if (categoryId != null) {
                categoryId = em.getReference(categoryId.getClass(), categoryId.getCategoryId());
                product.setCategoryId(categoryId);
            }
            Collection<OrderedProduct> attachedOrderedProductCollection = new ArrayList<OrderedProduct>();
            for (OrderedProduct orderedProductCollectionOrderedProductToAttach : product.getOrderedProductCollection()) {
                orderedProductCollectionOrderedProductToAttach = em.getReference(orderedProductCollectionOrderedProductToAttach.getClass(), orderedProductCollectionOrderedProductToAttach.getOrderedProductPK());
                attachedOrderedProductCollection.add(orderedProductCollectionOrderedProductToAttach);
            }
            product.setOrderedProductCollection(attachedOrderedProductCollection);
            em.persist(product);
            if (categoryId != null) {
                categoryId.getProductCollection().add(product);
                categoryId = em.merge(categoryId);
            }
            for (OrderedProduct orderedProductCollectionOrderedProduct : product.getOrderedProductCollection()) {
                Product oldProductOfOrderedProductCollectionOrderedProduct = orderedProductCollectionOrderedProduct.getProduct();
                orderedProductCollectionOrderedProduct.setProduct(product);
                orderedProductCollectionOrderedProduct = em.merge(orderedProductCollectionOrderedProduct);
                if (oldProductOfOrderedProductCollectionOrderedProduct != null) {
                    oldProductOfOrderedProductCollectionOrderedProduct.getOrderedProductCollection().remove(orderedProductCollectionOrderedProduct);
                    oldProductOfOrderedProductCollectionOrderedProduct = em.merge(oldProductOfOrderedProductCollectionOrderedProduct);
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

    public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Product persistentProduct = em.find(Product.class, product.getProductId());
            Category categoryIdOld = persistentProduct.getCategoryId();
            Category categoryIdNew = product.getCategoryId();
            Collection<OrderedProduct> orderedProductCollectionOld = persistentProduct.getOrderedProductCollection();
            Collection<OrderedProduct> orderedProductCollectionNew = product.getOrderedProductCollection();
            List<String> illegalOrphanMessages = null;
            for (OrderedProduct orderedProductCollectionOldOrderedProduct : orderedProductCollectionOld) {
                if (!orderedProductCollectionNew.contains(orderedProductCollectionOldOrderedProduct)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain OrderedProduct " + orderedProductCollectionOldOrderedProduct + " since its product field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (categoryIdNew != null) {
                categoryIdNew = em.getReference(categoryIdNew.getClass(), categoryIdNew.getCategoryId());
                product.setCategoryId(categoryIdNew);
            }
            Collection<OrderedProduct> attachedOrderedProductCollectionNew = new ArrayList<OrderedProduct>();
            for (OrderedProduct orderedProductCollectionNewOrderedProductToAttach : orderedProductCollectionNew) {
                orderedProductCollectionNewOrderedProductToAttach = em.getReference(orderedProductCollectionNewOrderedProductToAttach.getClass(), orderedProductCollectionNewOrderedProductToAttach.getOrderedProductPK());
                attachedOrderedProductCollectionNew.add(orderedProductCollectionNewOrderedProductToAttach);
            }
            orderedProductCollectionNew = attachedOrderedProductCollectionNew;
            product.setOrderedProductCollection(orderedProductCollectionNew);
            product = em.merge(product);
            if (categoryIdOld != null && !categoryIdOld.equals(categoryIdNew)) {
                categoryIdOld.getProductCollection().remove(product);
                categoryIdOld = em.merge(categoryIdOld);
            }
            if (categoryIdNew != null && !categoryIdNew.equals(categoryIdOld)) {
                categoryIdNew.getProductCollection().add(product);
                categoryIdNew = em.merge(categoryIdNew);
            }
            for (OrderedProduct orderedProductCollectionNewOrderedProduct : orderedProductCollectionNew) {
                if (!orderedProductCollectionOld.contains(orderedProductCollectionNewOrderedProduct)) {
                    Product oldProductOfOrderedProductCollectionNewOrderedProduct = orderedProductCollectionNewOrderedProduct.getProduct();
                    orderedProductCollectionNewOrderedProduct.setProduct(product);
                    orderedProductCollectionNewOrderedProduct = em.merge(orderedProductCollectionNewOrderedProduct);
                    if (oldProductOfOrderedProductCollectionNewOrderedProduct != null && !oldProductOfOrderedProductCollectionNewOrderedProduct.equals(product)) {
                        oldProductOfOrderedProductCollectionNewOrderedProduct.getOrderedProductCollection().remove(orderedProductCollectionNewOrderedProduct);
                        oldProductOfOrderedProductCollectionNewOrderedProduct = em.merge(oldProductOfOrderedProductCollectionNewOrderedProduct);
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
                Integer id = product.getProductId();
                if (findProduct(id) == null) {
                    throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
            Product product;
            try {
                product = em.getReference(Product.class, id);
                product.getProductId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<OrderedProduct> orderedProductCollectionOrphanCheck = product.getOrderedProductCollection();
            for (OrderedProduct orderedProductCollectionOrphanCheckOrderedProduct : orderedProductCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the OrderedProduct " + orderedProductCollectionOrphanCheckOrderedProduct + " in its orderedProductCollection field has a non-nullable product field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Category categoryId = product.getCategoryId();
            if (categoryId != null) {
                categoryId.getProductCollection().remove(product);
                categoryId = em.merge(categoryId);
            }
            em.remove(product);
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

    public List<Product> findProductEntities() {
        return findProductEntities(true, -1, -1);
    }

    public List<Product> findProductEntities(int maxResults, int firstResult) {
        return findProductEntities(false, maxResults, firstResult);
    }

    private List<Product> findProductEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Product as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Product findProduct(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Product.class, id);
        } finally {
            em.close();
        }
    }

    public int getProductCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Product as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}
