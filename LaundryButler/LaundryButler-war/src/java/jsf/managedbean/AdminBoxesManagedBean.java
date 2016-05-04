package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import LaundryOrderManagement.LaundryOrderManagementRemote;
import com.twilio.sdk.TwilioRestException;
import entity.Box;
import entity.Employee;
import extensions.TextSender;
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
import org.primefaces.event.CellEditEvent;

@Named(value = "adminBoxesManagedBean")
@SessionScoped
public class AdminBoxesManagedBean implements Serializable {

    @EJB
    private LaundryOrderManagementRemote laundryOrderManagementRemote;
    @EJB
    private AccountManagementRemote accountManagementRemote;

    private List<Box> boxes;
    private Employee admin;
    private Box selectedBox;

    public AdminBoxesManagedBean() {
        admin = new Employee();
        boxes = new ArrayList<>();
        selectedBox = new Box();
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
        boxes = laundryOrderManagementRemote.viewAllBox();
    }

    public void updateBox(ActionEvent event) {
        if (laundryOrderManagementRemote.updateBox(selectedBox)) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update successful", "Update successful"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Update fail", "Update fail"));
        }
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesContext context = FacesContext.getCurrentInstance();
            selectedBox = context.getApplication().evaluateExpressionGet(context, "#{box}", Box.class);
            laundryOrderManagementRemote.updateBox(selectedBox);
            selectedBox = null;
        }

        // Send text message about status update
        try {
            TextSender ts = new TextSender();
            String message = "Box " + selectedBox.getBoxId() + " Update\n"
                    + "Status: " + selectedBox.getStatus() + "\n"
                    + "Handled by: " + selectedBox.getEmployee().getFirstName() + "(" + selectedBox.getEmployee().getMobile() + ")\n"
                    + "Passcode: " + selectedBox.getBoxPasscode() + "\n";
            ts.setBodyMessage(message);
            ts.setRecipientPhoneNumber(selectedBox.getCustomer().getMobile());
            ts.sendText();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public Employee getAdmin() {
        return admin;
    }

    public void setAdmin(Employee admin) {
        this.admin = admin;
    }

    public Box getSelectedBox() {
        return selectedBox;
    }

    public void setSelectedBox(Box selectedBox) {
        this.selectedBox = selectedBox;
    }

}
