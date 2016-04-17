/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import LaundryOrderManagement.LaundryOrderManagementRemote;
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
    
    @Inject
    private CustomerCartManagedBean customerCartManagedBean;
    
    private Customer customer;
    private List<TransactionLineItem> readyToPayTransactionLineItems;
    private Transaction transaction;
    private Double totalAmount;
    private Box box;
    
    public CheckOutManagedBean() {
        customer = new Customer();
        readyToPayTransactionLineItems = new ArrayList<>();
        transaction = new Transaction();
        totalAmount = new Double (0);
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
        transaction = transactionManagementRemote.getTranscation();
        totalAmount = transaction.getTotalCharge();
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
                List<TransactionLineItem> items = transaction.getTransactionLineItems();
                for (int i = 0; i < items.size(); i ++) {
                    for (int j = 0; j < items.get(i).getQuantity(); j ++) {
                        for (int k = 0; k < items.get(i).getProduct().getNumberOfUnits(); k ++){
                            box.setAllowSharing(false);
                            SecureRandom random = new SecureRandom();
                            box.setBoxPasscode(new BigInteger(130, random).toString(6));
                            box.setCreatedDateTime(new Date());
                            box.setCustomer(customer);
                            box.setDeliveryDateTime(null);
                            box.setIsShared(false);
                            laundryOrderManagementRemote.createBox(box);
                        }
                        if (items.get(i).getProduct().getProductId() == 5){
                            // dry cleaning
                            customer.setDryCleaning(items.get(i).getQuantity());
                            accountManagementRemote.updateCutomerProfile(customer);
                        } else if (items.get(i).getProduct().getProductId() == 6){
                            // express
                            customer.setExpress(items.get(i).getQuantity());
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
    
    public List<TransactionLineItem> getReadyToPayTransactionLineItems() {
        return readyToPayTransactionLineItems;
    }
    
    public void setReadyToPayTransactionLineItems(List<TransactionLineItem> readyToPayTransactionLineItems) {
        this.readyToPayTransactionLineItems = readyToPayTransactionLineItems;
    }
    
    public Transaction getTransaction() {
        return transaction;
    }
    
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
}
