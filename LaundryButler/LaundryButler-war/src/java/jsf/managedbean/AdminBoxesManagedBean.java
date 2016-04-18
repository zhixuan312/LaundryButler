/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import LaundryOrderManagement.LaundryOrderManagementRemote;
import entity.Box;
import entity.Employee;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "adminBoxesManagedBean")
@SessionScoped
public class AdminBoxesManagedBean implements Serializable {
    
    @EJB
    private LaundryOrderManagementRemote laundryOrderManagementRemote;
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private List<Box> boxes;
    private Employee admin;
    private Box selectedBox;
    
    public AdminBoxesManagedBean() {
        admin = new Employee();
        boxes = new ArrayList<>();
        selectedBox = new Box();
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
        
        admin = accountManagementRemote.getEmployee();
        if (admin.getIsAdmin()){
            boxes = laundryOrderManagementRemote.viewAllBox();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please login with admin account!","Please login with admin account!"));
        }
    }
    
    public void updateAddress(ActionEvent event){
        if (laundryOrderManagementRemote.updateBox(selectedBox)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update successful", "Update successful"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update fail", "Update fail"));
        }
    }
    
    public List<Box> getBoxes() {
        return boxes;
    }
    
    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
    
    public Employee getAdmin() {
        return admin;
    }
    
    public void setAdmin(Employee admin) {
        this.admin = admin;
    }
    
    public Box getSelectedBox() {
        return selectedBox;
    }
    
    public void setSelectedBox(Box selectedBox) {
        this.selectedBox = selectedBox;
    }
    
}
