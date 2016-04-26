/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package TransactionManagement;

import entity.CartLineItem;
import entity.Transaction;
import entity.TransactionLineItem;
import java.util.List;
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
public class TransactionManagement implements TransactionManagementRemote, TransactionManagementLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    Transaction transcation;
    
    @Override
    public Long createTransaction (Transaction transaction) {
        try {
            em.persist(transaction);
            em.flush();
            return transaction.getTransactionId();
        } catch (Exception e) {
            return new Long(-1);
        }
    }
    
    @Override
    public Boolean updateTransaction (Transaction transaction){
        try {
            em.merge(transaction);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public List<Transaction> viewAllTransaction (){
        List<Transaction> transactions = null;
        try{
            String jpql = "SELECT t FROM Transaction t";
            Query query = em.createQuery(jpql);
            transactions = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return transactions;
    }
    
    @Override
    public List<Transaction> viewAllTransactionByCustomerId (Long customerId){
        List<Transaction> transactions = null;
        try{
            String jpql = "SELECT t FROM Transaction t WHERE t.customer.customerId = " + customerId;
            Query query = em.createQuery(jpql);
            transactions = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return transactions;
    }
    
    @Override
    public Boolean deleteTransaction (Long transactionId){
        try {
            Transaction transaction = em.find(Transaction.class, transactionId);
            em.remove(transaction);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean createTransactionLineItem (TransactionLineItem transactionLineItem) {
        try {
            em.persist(transactionLineItem);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean updateTransactionLineItem (TransactionLineItem transactionLineItem){
        try {
            em.merge(transactionLineItem);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public List<TransactionLineItem> viewAllTransactionLineItem (){
        List<TransactionLineItem> transactionLineItems = null;
        try{
            String jpql = "SELECT tli FROM TransactionLineItem tli";
            Query query = em.createQuery(jpql);
            transactionLineItems = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return transactionLineItems;
    }
    
    @Override
    public List<TransactionLineItem> viewAllTransactionLineItemByTransactionId (Long transactionId){
        List<TransactionLineItem> transactionLineItems = null;
        try{
            String jpql = "SELECT tli FROM TransactionLineItem tli WHERE tli.transaction.transactionId = " + transactionId;
            Query query = em.createQuery(jpql);
            transactionLineItems = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return transactionLineItems;
    }
    
    @Override
    public List<TransactionLineItem> viewAllTransactionLineItemByProductId (Long productId){
        List<TransactionLineItem> transactionLineItems = null;
        try{
            String jpql = "SELECT tli FROM TransactionLineItem tli WHERE tli.product.productId = " + productId;
            Query query = em.createQuery(jpql);
            transactionLineItems = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return transactionLineItems;
    }
    
    @Override
    public Boolean deleteTransactionLineItem (Long transactionLineItemId){
        try {
            TransactionLineItem transactionLineItem = em.find(TransactionLineItem.class, transactionLineItemId);
            em.remove(transactionLineItem);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean createCartLineItem (CartLineItem cartLineItem) {
        try {
            em.persist(cartLineItem);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
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
    public List<CartLineItem> viewAllCartLineItem (){
        List<CartLineItem> cartLineItems = null;
        try{
            String jpql = "SELECT cli FROM CartLineItem cli";
            Query query = em.createQuery(jpql);
            cartLineItems = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return cartLineItems;
    }
    
    @Override
    public List<CartLineItem> viewAllCartLineItemByCustomerId (Long customerId){
        List<CartLineItem> cartLineItems = null;
        try{
            String jpql = "SELECT cli FROM CartLineItem cli WHERE cli.customer.customerId = " + customerId;
            Query query = em.createQuery(jpql);
            cartLineItems = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return cartLineItems;
    }
    
    @Override
    public List<CartLineItem> viewAllCartLineItemByProductId (Long productId){
        List<CartLineItem> cartLineItems = null;
        try{
            String jpql = "SELECT tli FROM TransactionLineItem tli WHERE tli.product.productId = " + productId;
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
    public Transaction getTranscation() {
        return transcation;
    }
    
    @Override
    public void setTranscation(Transaction transcation) {
        this.transcation = transcation;
    }
}
