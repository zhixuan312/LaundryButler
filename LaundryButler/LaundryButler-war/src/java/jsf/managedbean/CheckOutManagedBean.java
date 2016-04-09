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
    private List<CartLineItem> readyToPayCartItems;
    private List<TransactionLineItem> readyToPayTransactionLineItems;
    private Transaction transaction;
    
    public CheckOutManagedBean() {
        customer = new Customer();
        address = new ArrayList<>();
        newAddress = new Address();
        readyToPayCartItems = new ArrayList<>();
        readyToPayTransactionLineItems = new ArrayList<>();
        transaction = new Transaction();
    }
    
    @PostConstruct
    public void init()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        try
        {
            if(ec.getSessionMap().get("login") == null)
            {
                ec.redirect("login.xhtml?faces-redirect=true");
            }
            else
            {
                if(ec.getSessionMap().get("login").equals(false))
                {
                    ec.redirect("login.xhtml?faces-redirect=true");
                }
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        customer = accountManagementRemote.getCustomer();
        readyToPayCartItems = customer.getSelectedCartLineItems();
        convertFromCartLineItemToTransactionLineItem();
    }
    
    public void createAddress (ActionEvent event) {
        if (accountManagementRemote.createAddress(newAddress)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to create","Fail to create"));
        }
    }
    
    public void convertFromCartLineItemToTransactionLineItem(){
        if(readyToPayCartItems.isEmpty()){
            for(int i = 0; i < readyToPayCartItems.size(); i ++) {
                TransactionLineItem transactionLineItem = new TransactionLineItem();
                transactionLineItem.setQuantity(readyToPayCartItems.get(i).getQuantity());
                transactionLineItem.setUnitCharge(readyToPayCartItems.get(i).getProduct().getPrice());
                transactionLineItem.setTotalCharge(readyToPayCartItems.get(i).getProduct().getPrice() * readyToPayCartItems.get(i).getQuantity());
                transactionLineItem.setProduct(readyToPayCartItems.get(i).getProduct());
                transactionManagementRemote.createTransactionLineItem(transactionLineItem);
                readyToPayTransactionLineItems.add(transactionLineItem);
            }
        }
    }
    
    public void checkOut(ActionEvent event){
        transaction.setCustomer(customer);
        Double totalCharge = new Double(0);
        if (!readyToPayTransactionLineItems.isEmpty()){
            for (int i = 0; i < readyToPayTransactionLineItems.size(); i ++){
                totalCharge = totalCharge + readyToPayTransactionLineItems.get(i).getTotalCharge();
            }
            transaction.setTotalCharge(totalCharge);
        } else {
            transaction.setTotalCharge(new Double (0));
        }
        transaction.setTransactionDateTime(new Date());
        if (transactionManagementRemote.createTransaction(transaction)){
            if (!readyToPayTransactionLineItems.isEmpty()){
                for (int j = 0; j < readyToPayTransactionLineItems.size(); j ++) {
                    readyToPayTransactionLineItems.get(j).setTransaction(transaction);
                    transactionManagementRemote.updateTransactionLineItem(readyToPayTransactionLineItems.get(j));
                }
            }
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
    
    public List<CartLineItem> getReadyToPayCartItems() {
        return readyToPayCartItems;
    }
    
    public void setReadyToPayCartItems(List<CartLineItem> readyToPayCartItems) {
        this.readyToPayCartItems = readyToPayCartItems;
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
