package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import entity.Customer;
import java.io.IOException;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;



@Named(value = "signUpAndLoginManagedBean")
@RequestScoped

public class SignUpAndLoginManagedBean
{
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private Customer customer;
    private String email;
    private String password;
    
    @PostConstruct
    public void init()
    {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }
    
    public SignUpAndLoginManagedBean()
    {
        customer = new Customer();
        email = "";
        password = "";
    }
    
    public void customerLogin(ActionEvent event)
    {
        try
        {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            if(accountManagementRemote.customerLogin(email, password))
            {
                ec.getSessionMap().put("login", true);
                ec.redirect("http://localhost:8080/LaundryButler-war/home.xhtml?faces-redirect=true");
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential", "Invalid login credential"));
            }
            
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
        public void employeeLogin(ActionEvent event)
    {
        try
        {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            if(accountManagementRemote.employeeLogin(email, password))
            {
                ec.getSessionMap().put("login", true);
                ec.redirect("http://localhost:8080/LaundryButler-war/employee.xhtml?faces-redirect=true");
            }
            else
            {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential", "Invalid login credential"));
            }
            
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void cancelLogin(ActionEvent event)
    {
        try
        {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("SignUpAndLogin.xhtml?faces-redirect=true");
            
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void logout(ActionEvent event)
    {
        try
        {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ((HttpSession)ec.getSession(true)).invalidate();
            ec.redirect("index.xhtml?faces-redirect=true");
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void createCustomer(ActionEvent event)
    {
        Long verificationCode = accountManagementRemote.register(customer);
        if (!verificationCode.equals(new Long("-1"))){
            this.email = customer.getEmail();
            this.password = customer.getPassword();
            customer = new Customer();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New customer registered successfully!", "Your registration verification code is " + verificationCode));
            this.customerLogin(event);

        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email is been registered", "Email is been registered"));        }
    }
    
    public void cancelRegister(ActionEvent event)
    {
        try
        {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("SignUpAndLogin.xhtml?faces-redirect=true");
            
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
}