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
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author XUAN
 */
@Named(value = "customerBoxManagedBean")
@RequestScoped
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
        customer = new Customer();
    }
    
    @PostConstruct
    public void init()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        try
        {
            if(ec.getSessionMap().get("login") == null)
            {
                ec.redirect("index.xhtml?faces-redirect=true");
            }
            else
            {
                if(ec.getSessionMap().get("login").equals(false))
                {
                    ec.redirect("index.xhtml?faces-redirect=true");
                }
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        
        customer = accountManagementRemote.getCustomer();
        if(laundryOrderManagementRemote.viewAllBoxByCustomerId(customer.getCustomerId()) != null)
            boxes = laundryOrderManagementRemote.viewAllBoxByCustomerId(customer.getCustomerId());
    }
    
    public void updateBoxIsExpress (Box box) {
        if (box.getIsExpress()){
            box.setIsExpress(false);
        } else {
            box.setIsExpress(true);
        }
        laundryOrderManagementRemote.updateBox(box);
        if (box.getIsExpress())
            isExpressBox ();
        
    }
    
    public void isExpressBox (){
        int num = customer.getExpress();
        num --;
        customer.setExpress(num);
        accountManagementRemote.updateCutomerProfile(customer);
    }
    
    public void updateBoxIsShared (Box box){
        if (box.getIsShared()){
            box.setAllowSharing(false);
        } else {
            box.setAllowSharing(true);
        }
        laundryOrderManagementRemote.updateBox(box);
    }
    
    public void updateBoxDryCleaning (Box box){
        laundryOrderManagementRemote.updateBox(box);
        customer.setDryCleaning(customer.getDryCleaning() + 1);
        accountManagementRemote.updateCutomerProfile(customer);
    }
    
    public void deleteBox (Box boxToDelete) {
        
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
