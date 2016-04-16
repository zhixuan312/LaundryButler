/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import entity.Address;
import entity.Card;
import entity.Customer;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author XUAN
 */
@Named(value = "customerProfileManagedBean")
@SessionScoped
public class CustomerProfileManagedBean implements Serializable{
    
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    Customer customer;
    Address address;
    Card card;
    List<Address> addresses;
    List<Card> cards;
    
    public CustomerProfileManagedBean() {
        customer = new Customer();
        address = new Address();
        card = new Card();
        addresses = new ArrayList<>();
        cards = new ArrayList<>();
    }
    
    @PostConstruct
    public void init(){
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        try{
            if(ec.getSessionMap().get("login") == null){
                ec.redirect("login.xhtml?faces-redirect=true");
            } else {
                if(ec.getSessionMap().get("login").equals(false)) {
                    ec.redirect("login.xhtml?faces-redirect=true");
                }
            }
        } catch(IOException ex) {
            ex.printStackTrace();
        }
        customer = accountManagementRemote.getCustomer();
        if (accountManagementRemote.viewAllAddressByCustomerId(customer.getCustomerId()) != null) {
            addresses = accountManagementRemote.viewAllAddressByCustomerId(customer.getCustomerId());
        }
        if (accountManagementRemote.viewAllCardByCustomerId(customer.getCustomerId()) != null){
            cards = accountManagementRemote.viewAllCardByCustomerId(customer.getCustomerId());
        }
    }
    
    public void updateCustomerProfile (ActionEvent event) {
        if(accountManagementRemote.updateCutomerProfile(customer)){
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
//            customer = accountManagementRemote.getCustomer();
            try{
                ec.redirect("home.xhtml?faces-redirect=true");
                
            } catch(IOException ex) {
                ex.printStackTrace();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
        }
    }
    
    public void cancelUpdateCustomerProfile (ActionEvent event){
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("customerProfile.xhtml?faces-redirect=true");
            
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void createAddress (ActionEvent event) {
        if (accountManagementRemote.createAddress(address)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to create","Fail to create"));
        }
    }
    
    public void cancelCreateAddress(ActionEvent event){
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("customerProfile.xhtml?faces-redirect=true");
            
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateAddress (ActionEvent event) {
        if(accountManagementRemote.updateAddress(address)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
        }
    }
    
    public void deleteAddress (ActionEvent event) {
        Address addressToDelete = (Address)event.getComponent().getAttributes().get("addressToDelete");
        if(accountManagementRemote.deleteAddress(addressToDelete.getAddressId())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
        }
    }
    
    public void createCard (ActionEvent event) {
        if (accountManagementRemote.createCard(card)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to create","Fail to create"));
        }
    }
    
    public void cancelCreateCard(ActionEvent event){
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("customerProfile.xhtml?faces-redirect=true");
            
        } catch(IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateCard (ActionEvent event) {
        if(accountManagementRemote.updateCard(card)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
        }
    }
    
    public void deleteCard (ActionEvent event) {
        Card cardToDelete = (Card)event.getComponent().getAttributes().get("cardToDelete");
        if(accountManagementRemote.deleteAddress(cardToDelete.getCardId())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
        }
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public Address getAddress() {
        return address;
    }
    
    public void setAddress(Address address) {
        this.address = address;
    }
    
    public Card getCard() {
        return card;
    }
    
    public void setCard(Card card) {
        this.card = card;
    }
    
    public List<Address> getAddresses() {
        return addresses;
    }
    
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
    
    public List<Card> getCards() {
        return cards;
    }
    
    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
