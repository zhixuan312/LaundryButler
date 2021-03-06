package jsf.managedbean;

import entity.Box;
import entity.Customer;
import entity.SharedBoxPermission;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "requestSharedPermissionManagedBean")
@RequestScoped
public class RequestSharedPermissionManagedBean implements Serializable {

    @Inject
    private SignUpAndLoginManagedBean signUpAndLoginManagedBean;
    @Inject
    private CustomerBoxManagedBean customerBoxManagedBean;

    private Customer customer;
    private List<Box> allowedSharingBoxes;
    String answer;

    public RequestSharedPermissionManagedBean() {
        customer = new Customer();
        allowedSharingBoxes = new ArrayList<>();
        answer = "";
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

        customer = signUpAndLoginManagedBean.getAccountManagementRemote().getCustomer();
        if (signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllBox() != null) {
            allowedSharingBoxes = retrieveAllAvailableBoxes();
        }
    }

    public List<Box> retrieveAllAvailableBoxes() {
        List<Box> tempAllowedSharingBoxes = signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllBox();
        List<Box> answerBoxes = new ArrayList<>();
        for (int i = 0; i < tempAllowedSharingBoxes.size(); i++) {
            if (tempAllowedSharingBoxes.get(i).getAllowSharing() && !tempAllowedSharingBoxes.get(i).getCustomer().equals(customer)) {
                answerBoxes.add(tempAllowedSharingBoxes.get(i));
            }
        }
        return answerBoxes;
    }

    public String requestStatus(Box box) {
        List<SharedBoxPermission> sharedBoxPermissions = new ArrayList<>();
        if (signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllSharedBoxPermissionByBoxId(box.getBoxId()) != null) {
            sharedBoxPermissions = signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllSharedBoxPermissionByBoxId(box.getBoxId());
            boolean myRequest = false;
            for (int i = 0; i < sharedBoxPermissions.size(); i++) {
                if (sharedBoxPermissions.get(i).getNeighbour().equals(customer) && sharedBoxPermissions.get(i).getBox().equals(box)) {
                    myRequest = true;
                    if (sharedBoxPermissions.get(i).getStatus().equals(new Integer(1))) {
                        answer = "Allowed";
                    } else if (sharedBoxPermissions.get(i).getStatus().equals(new Integer(-1))) {
                        answer = "Denied";
                    } else {
                        answer = "Pending";
                    }
                }
            }
            if (!myRequest) {
                answer = "Send Request";
            }
        } else {
            answer = "Send Request";
        }
        return answer;
    }

    public void sendRequest(Box box) {

        List<SharedBoxPermission> sharedBoxPermissions = new ArrayList<>();
        if (signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllSharedBoxPermissionByBoxId(box.getBoxId()) != null) {
            sharedBoxPermissions = signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllSharedBoxPermissionByBoxId(box.getBoxId());
            boolean myRequest = false;
            for (int i = 0; i < sharedBoxPermissions.size(); i++) {
                if (sharedBoxPermissions.get(i).getNeighbour().equals(customer) && sharedBoxPermissions.get(i).getBox().equals(box)) {
                    myRequest = true;
                }
            }
            if (!myRequest) {
                SharedBoxPermission sharedBoxPermission = new SharedBoxPermission();
                sharedBoxPermission.setCustomer(box.getCustomer());
                sharedBoxPermission.setNeighbour(customer);
                sharedBoxPermission.setStatus(0);
                sharedBoxPermission.setBox(box);
                //signUpAndLoginManagedBean.getLaundryOrderManagementRemote().createSharedBoxPermission(sharedBoxPermission);
                box.getSharedBoxPermissions().add(sharedBoxPermission);
                signUpAndLoginManagedBean.getLaundryOrderManagementRemote().updateBox(box);
            }
        } else {
            SharedBoxPermission sharedBoxPermission = new SharedBoxPermission();
            sharedBoxPermission.setCustomer(box.getCustomer());
            sharedBoxPermission.setNeighbour(customer);
            sharedBoxPermission.setStatus(0);
            sharedBoxPermission.setBox(box);
            //signUpAndLoginManagedBean.getLaundryOrderManagementRemote().createSharedBoxPermission(sharedBoxPermission);
            box.getSharedBoxPermissions().add(sharedBoxPermission);
            signUpAndLoginManagedBean.getLaundryOrderManagementRemote().updateBox(box);
        }
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<Box> getAllowedSharingBoxes() {
        return allowedSharingBoxes;
    }

    public void setAllowedSharingBoxes(List<Box> allowedSharingBoxes) {
        this.allowedSharingBoxes = allowedSharingBoxes;
    }
}
