/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package ProductManagement;

import entity.CartLineItem;
import entity.Product;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author XUAN
 */
@Stateless
@Local(ProductManagementLocal.class)
@Remote(ProductManagementRemote.class)
public class ProductManagement implements ProductManagementRemote, ProductManagementLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    private CartLineItem cartLineItem;
    
    @Override
    public Long createProduct (Product product) {
        try {
            em.persist(product);
            em.flush();
            return product.getProductId();
        } catch (Exception e) {
            return new Long(-1);
        }
    }
    
    @Override
    public Boolean updateProduct (Product product){
        try {
            em.merge(product);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public List<Product> viewAllProduct (){
        List<Product> products = null;
        try{
            String jpql = "SELECT p FROM Product p";
            Query query = em.createQuery(jpql);
            products = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return products;
    }
    
    @Override
    public Boolean deleteProduct (Long productId){
        try {
            Product product = em.find(Product.class, productId);
            em.remove(product);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean deleteProducts (List<Product> products){
        for (Product product:products){
            deleteProduct(product.getProductId());
        }
        return true;
    }
    
    @Override
    public Long createCartLineItem (CartLineItem cartLineItem) {
        try {
            em.persist(cartLineItem);
            em.flush();
            return cartLineItem.getCartLineItemId();
        } catch (Exception e) {
            return new Long(-1);
        }
    }
    
    @Override
    public Boolean updateCartLineItem (CartLineItem cartLineItem){
        try {
            em.merge(cartLineItem);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public List<CartLineItem> viewAllCartLineItemByCustomerId (Long customerId){
        List<CartLineItem> cartLineItems = new ArrayList<>();
        try{
            String jpql = "SELECT cli FROM CartLineItem cli WHERE cli.customer.customerId = '" + customerId + "'";
            Query query = em.createQuery(jpql);
            cartLineItems = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return cartLineItems;
    }
    
    @Override
    public Boolean deleteCartLineItem (Long cartLineItemId){
        try {
            CartLineItem cartLineItem = em.find(CartLineItem.class, cartLineItemId);
            em.remove(cartLineItem);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public CartLineItem getCartLineItem() {
        return cartLineItem;
    }
    
    @Override
    public void setCartLineItem(CartLineItem cartLineItem) {
        this.cartLineItem = cartLineItem;
    }
}
