package jsf.managedbean;

import entity.Employee;
import extensions.EmailSender;
import extensions.TextSender;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
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

    private Employee employee;
    private List<Employee> employees;
            
    public AdminCreateEmployeeManagedBean() {
        employee = new Employee();
        employees = new ArrayList<>();
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
        employees = signUpAndLoginManagedBean.getAccountManagementRemote().viewAllRecordedEmployee();
    }

    public void createEmployee(ActionEvent event) {
        
        // Bug to fix:
        // employee.firstName, lastName, etc everything is null.
//        employee.setEmail("zhanghixuan");
//        employee.setPassword("123");
//        employee.setFirstName("happy");
//        employee.setLastName("sad");
//        System.out.println("Employee name = " + employee.getEmail());
        Long checkNumber = signUpAndLoginManagedBean.getAccountManagementRemote().createNewEmployee(employee);

        if (!checkNumber.equals(new Long("-1"))) {
            String message = employee.getFirstName() + ", welcome to the LaundryButler team. Your new account has been created. Your email is " + employee.getEmail() + " and your password is " + employee.getPassword() + ". You are also advised to change your password. We look forward to see you, " + employee.getFirstName() + ".";

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

            // Reset entered values in the new employee registration form
            employee = new Employee();
            employees = signUpAndLoginManagedBean.getAccountManagementRemote().viewAllRecordedEmployee();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New employee created successfully!", "New employee created successfully!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email is been used", "Email is been used"));
        }

        // Refresh employee console
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

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }
}
