/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import TransactionManagement.TransactionManagementRemote;
import entity.Address;
import entity.CartLineItem;
import entity.Customer;
import entity.Transaction;
import entity.TransactionLineItem;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import com.stripe.Stripe;
import com.stripe.model.Charge;
import java.util.HashMap;
import java.util.Map;

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

    private Customer customer;
    private List<Address> address;
    private Address newAddress;
    private List<TransactionLineItem> readyToPayTransactionLineItems;
    private Transaction transaction;
    private Double totalAmount;

    public CheckOutManagedBean() {
        customer = new Customer();
        address = new ArrayList<>();
        newAddress = new Address();
        readyToPayTransactionLineItems = new ArrayList<>();
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

    public void createAddress(ActionEvent event) {
        if (accountManagementRemote.createAddress(newAddress)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to create", "Fail to create"));
        }
    }

    public void checkOut(ActionEvent event) {
        transactionManagementRemote.getTranscation();
    }

    public void createStripeCharge(String amount, String description) throws Exception {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();

        String stripeToken = ec.getRequestParameterMap().get("stripeToken");

        if (stripeToken != null && stripeToken.trim().length() > 0) {
            Stripe.apiKey = ec.getInitParameter("StripeTestSecretKey");
            Map<String, Object> chargeParams = new HashMap<>();
            chargeParams.put("amount", amount);
            chargeParams.put("currency", "SGD");
            chargeParams.put("source", stripeToken);
            chargeParams.put("description", description);
            Charge charge = Charge.create(chargeParams);
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

    public List<Address> getAddress() {
        return address;
    }

    public void setAddress(List<Address> address) {
        this.address = address;
    }

    public Address getNewAddress() {
        return newAddress;
    }

    public void setNewAddress(Address newAddress) {
        this.newAddress = newAddress;
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
