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
import javax.ejb.Local;

/**
 *
 * @author XUAN
 */
@Local
public interface TransactionManagementLocal {
    
    public Boolean createTransaction (Transaction transaction);
    
    public Boolean updateTransaction (Transaction transaction);
    
    public List<Transaction> viewAllTransaction ();
    
    public List<Transaction> viewAllTransactionByCustomerId (Long customerId);
    
    public Boolean deleteTransaction (Long transactionId);
    
    public Boolean createTransactionLineItem (TransactionLineItem transactionLineItem);
    
    public Boolean updateTransactionLineItem (TransactionLineItem transactionLineItem);
    
    public List<TransactionLineItem> viewAllTransactionLineItem ();
    
    public List<TransactionLineItem> viewAllTransactionLineItemByTransactionId (Long transactionId);
    
    public List<TransactionLineItem> viewAllTransactionLineItemByProductId (Long productId);
    
    public Boolean deleteTransactionLineItem (Long transactionLineItemId);
    
    public Boolean createCartLineItem (CartLineItem cartLineItem);
    
    public Boolean updateCartLineItem (CartLineItem cartLineItem);
    
    public List<CartLineItem> viewAllCartLineItem ();
    
    public List<CartLineItem> viewAllCartLineItemByCustomerId (Long customerId);
    
    public List<CartLineItem> viewAllCartLineItemByProductId (Long productId);
    
    public Boolean deleteCartLineItem (Long cartLineItemId);
    
}
