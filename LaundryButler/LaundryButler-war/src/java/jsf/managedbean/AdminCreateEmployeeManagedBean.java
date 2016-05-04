package jsf.managedbean;

import entity.Employee;
import extensions.EmailSender;
import extensions.TextSender;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

@Named(value = "adminCreateEmployeeManagedBean")
@RequestScoped
public class AdminCreateEmployeeManagedBean implements Serializable {

    @Inject
    private SignUpAndLoginManagedBean signUpAndLoginManagedBean;
    @Inject
    private AdminEmployeeManagedBean adminEmployeeManagedBean;

    private Employee employee;
            
    public AdminCreateEmployeeManagedBean() {
        employee = new Employee();
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
        signUpAndLoginManagedBean.getAccountManagementRemote().getEmployee();
    }

    public void createEmployee(ActionEvent event) {
        
        Long checkNumber = signUpAndLoginManagedBean.getAccountManagementRemote().createNewEmployee(employee);

        if (!checkNumber.equals(new Long("-1"))) {
            String message = employee.getFirstName() + ", welcome to the LaundryButler team. Your new account has been created. Your email is " + employee.getEmail() + " and your password is " + employee.getPassword() + ". You are advised to change your password. We look forward to see you, " + employee.getFirstName() + ".";

            // Send a text message to the new employee
            try {
                TextSender ts = new TextSender();
                ts.setBodyMessage(message);
                ts.setRecipientPhoneNumber(employee.getMobile());
                ts.sendText();
            } catch (Exception ex) {
            }

            // Send an email message to the new employee
            try {
                EmailSender es = new EmailSender();
                es.setSubject("Your LaundryButler employee account");
                es.setMessage(message);
                es.setRecipientEmail(employee.getEmail());
                es.sendEmail();
            } catch (Exception ex) {
            }
            
            // Add the new employee in the frontend list of employees
            employee.setDateEmployed(new Date());
            adminEmployeeManagedBean.getEmployees().add(employee);

            // Reset entered values in the new employee registration form
            employee = new Employee();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New employee created successfully!", "New employee created successfully!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email is been used", "Email is been used"));
        }

        // Refresh LaundryButler Management Console
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("employee.xhtml?faces-redirect=true");
        } catch (Exception ex) {
        }
    }

    public SignUpAndLoginManagedBean getSignUpAndLoginManagedBean() {
        return signUpAndLoginManagedBean;
    }

    public void setSignUpAndLoginManagedBean(SignUpAndLoginManagedBean signUpAndLoginManagedBean) {
        this.signUpAndLoginManagedBean = signUpAndLoginManagedBean;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public AdminEmployeeManagedBean getAdminEmployeeManagedBean() {
        return adminEmployeeManagedBean;
    }

    public void setAdminEmployeeManagedBean(AdminEmployeeManagedBean adminEmployeeManagedBean) {
        this.adminEmployeeManagedBean = adminEmployeeManagedBean;
    }

}
