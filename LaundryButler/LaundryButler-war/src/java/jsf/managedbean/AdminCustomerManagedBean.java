package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import com.twilio.sdk.TwilioRestException;
import entity.Customer;
import entity.Employee;
import entity.Product;
import extensions.TextSender;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import org.primefaces.event.CellEditEvent;

@Named(value = "adminCustomerManagedBean")
@SessionScoped
public class AdminCustomerManagedBean implements Serializable {

    @EJB
    private AccountManagementRemote accountManagementRemote;

    private Employee admin;
    private List<Customer> customers;
    private Customer selectCustomer;
    private String textMessage;

    public AdminCustomerManagedBean() {
        admin = new Employee();
        customers = new ArrayList<>();
        selectCustomer = new Customer();
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

        admin = accountManagementRemote.getEmployee();
        customers = accountManagementRemote.viewAllRecordedCustomer();
    }

    public void updateCustomer(ActionEvent event) {
        if (accountManagementRemote.updateCutomerProfile(selectCustomer)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", "Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update", "Fail to update"));
        }
    }
    
    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();
         
        if(newValue != null && !newValue.equals(oldValue)) {
            FacesContext context = FacesContext.getCurrentInstance();
            selectCustomer = context.getApplication().evaluateExpressionGet(context, "#{customer}", Customer.class);
            accountManagementRemote.updateCutomerProfile(selectCustomer);
            
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void sendTextAnnouncement(ActionEvent event) {
        
        System.out.println(textMessage);
        
        TextSender ts = new TextSender();       
        ts.setBodyMessage(textMessage);
        
        for (Customer customer:customers) {
            ts.setRecipientPhoneNumber(customer.getMobile());
            try {
                ts.sendText();
            } catch (TwilioRestException ex) {
                // phone no. is not valid
            }
        }
        
        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("employee.xhtml?faces-redirect=true");
        } catch (IOException ex) {
            Logger.getLogger(AdminCustomerManagedBean.class.getName()).log(Level.SEVERE, null, ex);
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

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public AccountManagementRemote getAccountManagementRemote() {
        return accountManagementRemote;
    }

    public void setAccountManagementRemote(AccountManagementRemote accountManagementRemote) {
        this.accountManagementRemote = accountManagementRemote;
    }
    
}
