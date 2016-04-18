/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import entity.Employee;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
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
@Named(value = "adminCreateEmployeeManagedBean")
@SessionScoped
public class AdminCreateEmployeeManagedBean implements Serializable {
    
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private Employee admin;
    private Employee employee;
    
    public AdminCreateEmployeeManagedBean() {
        employee = new Employee();
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
    }
    
    public void createEmployee(ActionEvent event)
    {
        if (admin.getIsAdmin()){
            Long checkNumber = accountManagementRemote.createNewEmployee(employee);
            if (!checkNumber.equals(new Long("-1"))){
                employee = new Employee();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New employee created successfully!", "New employee created successfully!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email is been used", "Email is been used"));        }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please login with admin account!","Please login with admin account!"));
        }
    }
    
    public void cancelCreateNewEmployee(ActionEvent event){
        try{
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("adminCreateEmployee.xhtml?faces-redirect=true");
            
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
}
