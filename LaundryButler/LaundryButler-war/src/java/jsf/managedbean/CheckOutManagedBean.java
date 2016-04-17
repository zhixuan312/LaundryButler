/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import LaundryOrderManagement.LaundryOrderManagementRemote;
import ProductManagement.ProductManagementRemote;
import TransactionManagement.TransactionManagementRemote;
import entity.Customer;
import entity.Transaction;
import entity.TransactionLineItem;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import entity.Box;
import entity.CartLineItem;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

/**
 *
 * @author XUAN
 */
@Named(value = "checkOutManagedBean")
@SessionScoped
public class CheckOutManagedBean implements Serializable {
    
    @EJB
    private AccountManagementRemote accountManagementRemote;
    @EJB
    private TransactionManagementRemote transactionManagementRemote;
    @EJB
    private LaundryOrderManagementRemote laundryOrderManagementRemote;
    @EJB
    private ProductManagementRemote productManagementRemote;
    
    @Inject
    private CustomerCartManagedBean customerCartManagedBean;
    
    private Customer customer;
    private List<TransactionLineItem> transactionLineItemsForOneTransaction;
    private Transaction transaction;
    private Box box;
    
    public CheckOutManagedBean() {
        customer = new Customer();
        transactionLineItemsForOneTransaction = new ArrayList<>();
        transaction = new Transaction();
    }
    
    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        try {
            if (ec.getSessionMap().get("login") == null) {
                ec.redirect("login.xhtml?faces-redirect=true");
            } else {
                if (ec.getSessionMap().get("login").equals(false)) {
                    ec.redirect("login.xhtml?faces-redirect=true");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        customer = accountManagementRemote.getCustomer();
    }
    
    public void checkOut(ActionEvent event) {
        List<CartLineItem> cartLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
        transaction.setCustomer(customer);
        transaction.setTotalCharge(customerCartManagedBean.getTotalPrice());
        transaction.setTransactionDateTime(new Date());
        Long transactionId = transactionManagementRemote.createTransaction(transaction);
        transaction.setTransactionId(transactionId);
        
        if(!cartLineItems.isEmpty()){
            for(int i = 0; i < cartLineItems.size(); i ++) {
                TransactionLineItem transactionLineItem = new TransactionLineItem();
                transactionLineItem.setQuantity(cartLineItems.get(i).getQuantity());
                transactionLineItem.setUnitCharge(cartLineItems.get(i).getProduct().getPrice());
                transactionLineItem.setTotalCharge(cartLineItems.get(i).getProduct().getPrice() * cartLineItems.get(i).getQuantity());
                transactionLineItem.setProduct(cartLineItems.get(i).getProduct());
                transactionLineItem.setTransaction(transaction);
                transactionManagementRemote.createTransactionLineItem(transactionLineItem);
                transactionLineItemsForOneTransaction.add(transactionLineItem);
            }
        }
    }
    
    public void createStripeCharge() throws Exception {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        
        String stripeToken = ec.getRequestParameterMap().get("stripeToken");
        
        if (stripeToken != null && stripeToken.trim().length() > 0) {
            Stripe.apiKey = ec.getInitParameter("StripeTestSecretKey");
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", customerCartManagedBean.getStripeAmount());
            chargeParams.put("currency", customerCartManagedBean.getStripeCurrency());
            chargeParams.put("source", stripeToken);
            chargeParams.put("description", " ");
            Charge charge = Charge.create(chargeParams);
            
            if(charge.getStatus().equals("succeeded")){
                if (!transactionLineItemsForOneTransaction.isEmpty()){
                    for (int i = 0; i < transactionLineItemsForOneTransaction.size(); i ++) {
                        for (int j =0; j <transactionLineItemsForOneTransaction.get(i).getQuantity(); j++){
                            for (int k =0; k < transactionLineItemsForOneTransaction.get(i).getProduct().getNumberOfUnits(); k ++){
                                box.setAllowSharing(false);
                                SecureRandom random = new SecureRandom();
                                box.setBoxPasscode(new BigInteger(130, random).toString(6));
                                box.setCreatedDateTime(new Date());
                                box.setCustomer(customer);
                                box.setDeliveryDateTime(null);
                                box.setIsShared(false);
                                laundryOrderManagementRemote.createBox(box);
                            }
                        }
                        if (transactionLineItemsForOneTransaction.get(i).getProduct().getName().equals("Dry Cleaning")){
                            int dryCleaning = customer.getDryCleaning();
                            dryCleaning += transactionLineItemsForOneTransaction.get(i).getQuantity();
                            customer.setDryCleaning(dryCleaning);
                            accountManagementRemote.updateCutomerProfile(customer);
                        }
                        if (transactionLineItemsForOneTransaction.get(i).getProduct().getName().equals("Express")){
                            int express = customer.getDryCleaning();
                            express += transactionLineItemsForOneTransaction.get(i).getQuantity();
                            customer.setExpress(express);
                            accountManagementRemote.updateCutomerProfile(customer);
                        }
                    }
                }
                
                // TODO: Redirect to boxes page after successful charge
                
            }
        } else {
            System.out.println("Invalid credit card details. Payment is declined.");
        }
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public List<TransactionLineItem> getTransactionLineItemsForOneTransaction() {
        return transactionLineItemsForOneTransaction;
    }
    
    public void setTransactionLineItemsForOneTransaction(List<TransactionLineItem> transactionLineItemsForOneTransaction) {
        this.transactionLineItemsForOneTransaction = transactionLineItemsForOneTransaction;
    }
    
    public Transaction getTransaction() {
        return transaction;
    }
    
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    public Box getBox() {
        return box;
    }
    
    public void setBox(Box box) {
        this.box = box;
    }
    
}
