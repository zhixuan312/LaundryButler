/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import entity.Customer;
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
@Named(value = "adminCustomerManagedBean")
@SessionScoped
public class AdminCustomerManagedBean implements Serializable {
    
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private Employee admin;
    private List<Customer> customers;
    private Customer selectCustomer;
    
    public AdminCustomerManagedBean() {
        admin = new Employee();
        customers = new ArrayList<>();
        selectCustomer = new Customer();
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
        
        admin = accountManagementRemote.getEmployee();
        customers = accountManagementRemote.viewAllRecordedCustomer();
    }
    
    public void updateCustomer (ActionEvent event){
        if(accountManagementRemote.updateCutomerProfile(selectCustomer)){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
        }
    }
    
    public Employee getAdmin() {
        return admin;
    }
    
    public void setAdmin(Employee admin) {
        this.admin = admin;
    }
    
    public List<Customer> getCustomers() {
        return customers;
    }
    
    public void setCustomers(List<Customer> customers) {
        this.customers = customers;
    }
    
    public Customer getSelectCustomer() {
        return selectCustomer;
    }
    
    public void setSelectCustomer(Customer selectCustomer) {
        this.selectCustomer = selectCustomer;
    }
    
}
