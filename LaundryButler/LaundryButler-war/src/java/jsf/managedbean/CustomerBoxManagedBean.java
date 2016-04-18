/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import LaundryOrderManagement.LaundryOrderManagementRemote;
import entity.Box;
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
@Named(value = "customerBoxManagedBean")
@SessionScoped
public class CustomerBoxManagedBean implements Serializable{
    
    @EJB
    private LaundryOrderManagementRemote laundryOrderManagementRemote;
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private Box box;
    private Customer customer;
    private List<Box> boxes;
    
    public CustomerBoxManagedBean() {
        box = new Box();
        boxes = new ArrayList<>();
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
        boxes = laundryOrderManagementRemote.viewAllBoxByCustomerId(customer.getCustomerId());
    }
    
    public void updateBox (ActionEvent event) {
        if(laundryOrderManagementRemote.updateBox(box)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
        }
    }
    
    public void deleteBox (ActionEvent event) {
        Box boxToDelete = (Box)event.getComponent().getAttributes().get("boxToDelete");
        if(laundryOrderManagementRemote.deleteBox(boxToDelete.getBoxId())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
        }
    }
    
    public Box getBox() {
        return box;
    }
    
    public void setBox(Box box) {
        this.box = box;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public List<Box> getBoxes() {
        return boxes;
    }
    
    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
    
    
}
