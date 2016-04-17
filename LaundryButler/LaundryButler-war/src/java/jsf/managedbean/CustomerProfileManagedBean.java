/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import entity.Address;
import entity.Customer;
import invoiceGenerator.PDFTest1;
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
public class CustomerProfileManagedBean implements Serializable {
    
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private Customer customer;
    private Address address;
    private String oldPassword;
    private String newPassword;
    private List<Address> addresses;
    private boolean hasAddress;
//    private String message;
    
    public CustomerProfileManagedBean() {
        customer = new Customer();
        address = new Address();
        addresses = new ArrayList<>();
        oldPassword = "";
        newPassword = "";
        hasAddress = false;
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
        if (accountManagementRemote.viewAllAddressByCustomerId(customer.getCustomerId()) != null) {
            addresses = accountManagementRemote.viewAllAddressByCustomerId(customer.getCustomerId());
            if (!addresses.isEmpty()){
                hasAddress = true;
            }
        }
    }
    
    public void updateCustomerProfile(ActionEvent event) {
        
        if (accountManagementRemote.updateCutomerProfile(customer)) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            try {
                ec.redirect("home.xhtml?faces-redirect=true");
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update", "Fail to update"));
        }
    }
    
    public Boolean checkPassword(ActionEvent event) {
        if (customer.getIsFaceBook()) {
            customer.setIsFaceBook(false);
            customer.setPassword(newPassword);
            if (accountManagementRemote.updateCutomerProfile(customer)) {
                ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                try {
                    ec.redirect("home.xhtml?faces-redirect=true");
                    
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Success!"));
                return true;
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update", "Fail to update"));
                return false;
            }
        } else {
            if (customer.getPassword().equals(oldPassword)) {
                customer.setPassword(newPassword);
                if (accountManagementRemote.updateCutomerProfile(customer)) {
                    ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
                    try {
                        ec.redirect("home.xhtml?faces-redirect=true");
                        
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Success!"));
                } else {
                    
                }
                return true;
            } else {
                System.out.println("######## old password wrong!");
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Invalid old password.", "Invalid old password."));
                
                return false;
            }
        }
    }
    
    public void cancelUpdateCustomerProfile(ActionEvent event) {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("customerProfile.xhtml?faces-redirect=true");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void createAddress(ActionEvent event) {
        if (accountManagementRemote.createAddress(address)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to create", "Fail to create"));
        }
    }
    
    public void cancelCreateAddress(ActionEvent event) {
        try {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("customerProfile.xhtml?faces-redirect=true");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void updateAddress(ActionEvent event) {
        if (accountManagementRemote.updateAddress(address)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update", "Fail to update"));
        }
    }
    
    public void deleteAddress(ActionEvent event) {
        Address addressToDelete = (Address) event.getComponent().getAttributes().get("addressToDelete");
        if (accountManagementRemote.deleteAddress(addressToDelete.getAddressId())) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update", "Fail to update"));
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
    
    public List<Address> getAddresses() {
        return addresses;
    }
    
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }
    
    public String getOldPassword() {
        return oldPassword;
    }
    
    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }
    
    public String getNewPassword() {
        return newPassword;
    }
    
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public boolean isHasAddress() {
        return hasAddress;
    }

    public void setHasAddress(boolean hasAddress) {
        this.hasAddress = hasAddress;
    }
}
