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
@Named(value = "adminEmployeeManagedBean")
@SessionScoped
public class AdminEmployeeManagedBean implements Serializable {
    
    @EJB
    private AccountManagementRemote accountManagementRemote;
    @EJB
    private LaundryOrderManagementRemote laundryOrderManagementRemote;
    
    private Employee admin;
    private Employee selectedEmployee;
    private List<Employee> employees;
    private List<Box> boxes;
    private Box selectBox;
    
    public AdminEmployeeManagedBean() {
        admin = new Employee();
        selectedEmployee = new Employee();
        employees = new ArrayList<>();
        boxes = new ArrayList<>();
        selectBox = new Box();
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
        employees = accountManagementRemote.viewAllRecordedEmployee();
        boxes = laundryOrderManagementRemote.viewAllBoxByEmployeeId(admin.getEmployeeId());
    }
    
    public void updateEmployee(ActionEvent event){
        if (admin.getIsAdmin()){
            if(accountManagementRemote.updateEmployeeProfile(selectedEmployee)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please login with admin account!","Please login with admin account!"));
        }
    }
    
    public void deleteEmployee(ActionEvent event){
        if (admin.getIsAdmin()){
            Employee employeeToDelete = (Employee)event.getComponent().getAttributes().get("employeeToDelete");
            
            accountManagementRemote.deleteEmployee(employeeToDelete.getEmployeeId());
            employees.remove(employeeToDelete);
            
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Employee " + employeeToDelete.getLastName() + " " + employeeToDelete.getLastName() + " deleted successfully", "Employee " + employeeToDelete.getLastName() + " " + employeeToDelete.getLastName() + " deleted successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please login with admin account!","Please login with admin account!"));
        }
    }
    
    public void updateBox (ActionEvent event) {
        if(laundryOrderManagementRemote.updateBox(selectBox)){
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            try
            {
                
                ec.redirect("employee.xhtml?faces-redirect=true#myBox");
                
            } catch(IOException ex)
            {
                ex.printStackTrace();
            }
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
    
    public Employee getSelectedEmployee() {
        return selectedEmployee;
    }
    
    public void setSelectedEmployee(Employee selectedEmployee) {
        this.selectedEmployee = selectedEmployee;
    }
    
    public List<Employee> getEmployees() {
        return employees;
    }
    
    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
    
    public List<Box> getBoxes() {
        return boxes;
    }
    
    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
    
    public Box getSelectBox() {
        return selectBox;
    }
    
    public void setSelectBox(Box selectBox) {
        this.selectBox = selectBox;
    }
    
}
