package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import LaundryOrderManagement.LaundryOrderManagementRemote;
import com.twilio.sdk.TwilioRestException;
import entity.Customer;
import entity.Employee;
import extensions.EmailSender;
import extensions.TextSender;
import java.io.IOException;
import java.io.Serializable;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Map;
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

public class SignUpAndLoginManagedBean implements Serializable {
    
    @EJB
    private AccountManagementRemote accountManagementRemote;
    @EJB
    private LaundryOrderManagementRemote laundryOrderManagementRemote;
    
    private Customer customer;
    private Employee employee;
    private String email;
    private String password;
    private String last_name;
    private String first_name;
    private String gender;
    private String mobile;
    private String verificationCode;
    private ExternalContext ec;
    private String referringId;
    
    @PostConstruct
    public void init() {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        Map<String, String> parameters = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        referringId = parameters.get("referrerid");
        if (referringId == null) {
            referringId = "";
        }
    }
    
    public SignUpAndLoginManagedBean() {
        customer = new Customer();
        customer.setIsFaceBook(false);
        email = "";
        password = "";
        last_name = "";
        first_name = "";
        gender = "";
        mobile = "";
        verificationCode = "";
        referringId = "";
        ec = FacesContext.getCurrentInstance().getExternalContext();
        
    }
    
    public void customerLogin() {
        try {
            ec = FacesContext.getCurrentInstance().getExternalContext();
            if (accountManagementRemote.customerLogin(email, password)) {
                if (accountManagementRemote.getCustomer().getAccountStatus().equalsIgnoreCase("Pending Verification")) {
                    ec.getSessionMap().put("login", true);
                    ec.redirect("verification.xhtml?faces-redirect=true");
                } else if (accountManagementRemote.getCustomer().getAccountStatus().equalsIgnoreCase("Suspended")) {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Suspended account", "Suspended account"));
                } else {
                    ec.getSessionMap().put("login", true);
                    ec.redirect("home.xhtml?faces-redirect=true");
                }
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential", "Invalid login credential"));
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void verification() {
        try {
            ec = FacesContext.getCurrentInstance().getExternalContext();
            if (verificationCode.equals(accountManagementRemote.getCustomer().getVerificationCode())) {
                System.out.println("verificationCode = " + verificationCode + " verificationCodeTrue = " + accountManagementRemote.getCustomer().getVerificationCode());
                Customer newCustomer = accountManagementRemote.getCustomer();
                newCustomer.setAccountStatus("Verified");
                accountManagementRemote.updateCutomerProfile(newCustomer);
                customerLogin();
            } else {
                ec.getSessionMap().put("login", true);
                ec.redirect("verification.xhtml?faces-redirect=true");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void customerLoginByFaceBook(ActionEvent event) {
        try {
            ec = FacesContext.getCurrentInstance().getExternalContext();
            if (accountManagementRemote.customerLoginByFaceBook(email)) {
                ec.getSessionMap().put("login", true);
                ec.redirect("home.xhtml?faces-redirect=true");
            } else {
                this.customer.setEmail(email);
                SecureRandom random = new SecureRandom();
                this.customer.setPassword(new BigInteger(130, random).toString(32));
                this.customer.setLastName(getLast_name());
                this.customer.setFirstName(getFirst_name());
                this.customer.setMobile(getMobile());
                this.customer.setGender(getGender());
                this.customer.setIsFaceBook(true);
                this.createCustomer(event);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void employeeLogin(ActionEvent event) {
        try {
            ec = FacesContext.getCurrentInstance().getExternalContext();
            if (accountManagementRemote.employeeLogin(email, password)) {
                ec.getSessionMap().put("login", true);
                ec.redirect("employee.xhtml?faces-redirect=true");
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid login credential", "Invalid login credential"));
            }
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void cancelLogin(ActionEvent event) {
        try {
            ec = FacesContext.getCurrentInstance().getExternalContext();
            ec.redirect("SignUpAndLogin.xhtml?faces-redirect=true");
            
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void logout(ActionEvent event) {
        try {
            ec = FacesContext.getCurrentInstance().getExternalContext();
            accountManagementRemote.logout();
            
            ((HttpSession) ec.getSession(true)).invalidate();
            ec.invalidateSession();
            ec.redirect("index.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    
    public void createCustomer(ActionEvent event) {
        Customer customerExist = null;
        if (!referringId.equals("")){
            customerExist = accountManagementRemote.retrieveCustomerByCustomerId(Long.valueOf(referringId).longValue());
            if (customerExist != null )
                customer.setDryCleaning(1);
        }
        String verificationCode = accountManagementRemote.register(customer);
        if (!verificationCode.equals("-1")) {
            this.email = customer.getEmail();
            this.password = customer.getPassword();
            
            // Try to text the verification code to the phone number of the new customer
            try {
                TextSender ts = new TextSender();
                ts.setBodyMessage("Hi " + customer.getFirstName() + ". Your LaundryButler verification code is " + verificationCode + ".");
                ts.setRecipientPhoneNumber(customer.getMobile());
                ts.sendText();
            } catch (TwilioRestException tre) {
                tre.printStackTrace();
            }
            
            // Try to email the verification code to the email of the new customer
            try {
                EmailSender es = new EmailSender();
                es.setSubject("Welcome to LaundryButler. Please verify your account.");
                es.setMessage("Your verification code is " + verificationCode + ".");
                es.setRecipientEmail(customer.getEmail());
                es.sendEmail();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            if (customerExist != null){
                customerExist.setDryCleaning(customerExist.getDryCleaning()+1);
                accountManagementRemote.updateCutomerProfile(customerExist);
            }
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New customer registered successfully!", "Your registration verification code is " + verificationCode));
            this.customerLogin();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Email is been registered", "Email is been registered"));
        }
    }
    
    public Employee getEmployee() {
        return employee;
    }
    
    public void setEmployee(Employee employee) {
        this.employee = employee;
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
    
    public AccountManagementRemote getAccountManagementRemote() {
        return accountManagementRemote;
    }
    
    public void setAccountManagementRemote(AccountManagementRemote accountManagementRemote) {
        this.accountManagementRemote = accountManagementRemote;
    }
    
    public String getMobile() {
        return mobile;
    }
    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    
    public LaundryOrderManagementRemote getLaundryOrderManagementRemote() {
        return laundryOrderManagementRemote;
    }
    
    public void setLaundryOrderManagementRemote(LaundryOrderManagementRemote laundryOrderManagementRemote) {
        this.laundryOrderManagementRemote = laundryOrderManagementRemote;
    }
    
    public String getVerificationCode() {
        return verificationCode;
    }
    
    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
    
    public String getReferringId() {
        return referringId;
    }
    
    public void setReferringId(String referringId) {
        this.referringId = referringId;
    }
    
}
