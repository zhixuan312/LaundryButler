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
import javax.inject.Inject;
import org.primefaces.event.CellEditEvent;

@Named(value = "adminEmployeeManagedBean")
@SessionScoped
public class AdminEmployeeManagedBean implements Serializable {

    @Inject
    private SignUpAndLoginManagedBean signUpAndLoginManagedBean;
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
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        try {
            if (ec.getSessionMap().get("login") == null) {
                ec.redirect("index.xhtml?faces-redirect=true");
            } else {
                if (ec.getSessionMap().get("login").equals(false)) {
                    ec.redirect("index.xhtml?faces-redirect=true");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        admin = signUpAndLoginManagedBean.getAccountManagementRemote().getEmployee();
        employees = getEmployeeList();
        boxes = laundryOrderManagementRemote.viewAllBoxByEmployeeId(admin.getEmployeeId());
    }

    public List<Employee> getEmployeeList(){
        List<Employee> tempList = signUpAndLoginManagedBean.getAccountManagementRemote().viewAllRecordedEmployee();
        for (int i =0; i < tempList.size(); i ++) {
            if (tempList.get(i).getEmployeeId().equals(admin.getEmployeeId())){
                tempList.remove(i);
            }
        }
        return tempList;
    }
    public void updateEmployee(ActionEvent event) {
        if (admin.getIsAdmin()) {
            if (signUpAndLoginManagedBean.getAccountManagementRemote().updateEmployeeProfile(selectedEmployee)) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Success!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update", "Fail to update"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please login with admin account!", "Please login with admin account!"));
        }
    }
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesContext context = FacesContext.getCurrentInstance();
            selectedEmployee = context.getApplication().evaluateExpressionGet(context, "#{employee}", Employee.class);
            signUpAndLoginManagedBean.getAccountManagementRemote().updateEmployeeProfile(selectedEmployee);
            selectedEmployee = null;
        }
    }

    public void deleteEmployee(ActionEvent event) {
        if (admin.getIsAdmin()) {
            Employee employeeToDelete = (Employee) event.getComponent().getAttributes().get("employeeToDelete");

            signUpAndLoginManagedBean.getAccountManagementRemote().deleteEmployee(employeeToDelete.getEmployeeId());
            employees.remove(employeeToDelete);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Employee " + employeeToDelete.getLastName() + " " + employeeToDelete.getLastName() + " deleted successfully", "Employee " + employeeToDelete.getLastName() + " " + employeeToDelete.getLastName() + " deleted successfully"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please login with admin account!", "Please login with admin account!"));
        }
    }

    public void updateBox(Box box) {
        selectBox = box;
        if (laundryOrderManagementRemote.updateBox(selectBox)) {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

            try {
                ec.redirect("http://localhost:8080/LaundryButler-war/employee.xhtml?faces-redirect=true#mybox");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update", "Fail to update"));
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
