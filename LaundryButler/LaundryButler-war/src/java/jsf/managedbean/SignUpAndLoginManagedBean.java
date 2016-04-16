package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import entity.Customer;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.http.HttpSession;



@Named(value = "signUpAndLoginManagedBean")
@SessionScoped

public class SignUpAndLoginManagedBean implements Serializable
{
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private Customer customer;
    private String email;
    private String password;
    private String last_name;
    private String first_name;
    private String gender;
    
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
        last_name="";
        first_name="";
        gender="";
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
    
    public void customerLoginByFaceBook(ActionEvent event){
        try
        {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            if(accountManagementRemote.customerLoginByFaceBook(email))
            {
                ec.getSessionMap().put("login", true);
                ec.redirect("http://localhost:8080/LaundryButler-war/home.xhtml?faces-redirect=true");
            }
            else
            {
                this.customer.setEmail(email);
                SecureRandom random = new SecureRandom();
                this.customer.setPassword(new BigInteger(130, random).toString(32));
                this.customer.setLastName(getLast_name());
                this.customer.setFirstName(getFirst_name());
                this.customer.setGender(getGender());
                this.customer.setIsFaceBook(true);
                this.createCustomer(event);
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
            accountManagementRemote.logout();
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
        customer.setIsFaceBook(false);
        String verificationCode = accountManagementRemote.register(customer);
        if (!verificationCode.equals("-1")){
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
    
    public String getLast_name() {
        return last_name;
    }
    
    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }
    
    public String getFirst_name() {
        return first_name;
    }
    
    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }
    
    public String getGender() {
        return gender;
    }
    
    public void setGender(String gender) {
        this.gender = gender;
    }
}