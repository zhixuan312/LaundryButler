/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import LaundryOrderManagement.LaundryOrderManagementRemote;
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
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author XUAN
 */
@Named(value = "requestSharedPermissionManagedBean")
@RequestScoped
public class RequestSharedPermissionManagedBean implements Serializable {
    
    @EJB
    private LaundryOrderManagementRemote laundryOrderManagementRemote;
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private Customer customer;
    private List<Box> allowedSharingBoxes;
    String answer;
    
    public RequestSharedPermissionManagedBean() {
        customer = new Customer();
        allowedSharingBoxes = new ArrayList<>();
        answer = "";
    }
    
    @PostConstruct
    public void init()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        try
        {
            if(ec.getSessionMap().get("login") == null)
            {
                ec.redirect("index.xhtml?faces-redirect=true");
            }
            else
            {
                if(ec.getSessionMap().get("login").equals(false))
                {
                    ec.redirect("index.xhtml?faces-redirect=true");
                }
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        
        customer = accountManagementRemote.getCustomer();
        if (laundryOrderManagementRemote.viewAllBox() != null){
            allowedSharingBoxes = retrieveAllAvailableBoxes();
        }
    }
    
    public List<Box> retrieveAllAvailableBoxes (){
        List<Box> tempAllowedSharingBoxes = laundryOrderManagementRemote.viewAllBox();
        List<Box> answerBoxes = new ArrayList<>();
        for (int i = 0; i < tempAllowedSharingBoxes.size(); i ++) {
            System.out.println("is allowed sharing = " + tempAllowedSharingBoxes.get(i).getAllowSharing() + "Customer id = " + tempAllowedSharingBoxes.get(i).getCustomer().getCustomerId() + "my id = " + customer.getCustomerId());
            if (tempAllowedSharingBoxes.get(i).getAllowSharing() && !tempAllowedSharingBoxes.get(i).getCustomer().equals(customer)){
                answerBoxes.add(tempAllowedSharingBoxes.get(i));
            }
        }
        return answerBoxes;
    }
    
    public String requestStatus (Box box){
        List<SharedBoxPermission> sharedBoxPermissions = new ArrayList<>();
        if (laundryOrderManagementRemote.viewAllSharedBoxPermissionByBoxId(box.getBoxId()) != null){
            sharedBoxPermissions = laundryOrderManagementRemote.viewAllSharedBoxPermissionByBoxId(box.getBoxId());
            boolean myRequest = false;
            for (int i = 0; i < sharedBoxPermissions.size(); i ++){
                if (sharedBoxPermissions.get(i).getNeighbour().equals(customer) && sharedBoxPermissions.get(i).getBox().equals(box)){
                    myRequest = true;
                    if(sharedBoxPermissions.get(i).getStatus().equals(new Integer (1))){
                        answer = "Allowed";
                    } else if (sharedBoxPermissions.get(i).getStatus().equals(new Integer (-1))){
                        answer = "Denied";
                    } else {
                        answer = "Pending";
                    }
                }
            }
            if (!myRequest){
                answer = "Send Request";
            }
        } else {
            answer = "Send Request";
        }
        return answer;
    }
    
    public void sendRequest (Box box){
        
        List<SharedBoxPermission> sharedBoxPermissions = new ArrayList<>();
        if (laundryOrderManagementRemote.viewAllSharedBoxPermissionByBoxId(box.getBoxId()) != null){
            sharedBoxPermissions = laundryOrderManagementRemote.viewAllSharedBoxPermissionByBoxId(box.getBoxId());
            boolean myRequest = false;
            for (int i = 0; i < sharedBoxPermissions.size(); i ++){
                if (sharedBoxPermissions.get(i).getNeighbour().equals(customer) && sharedBoxPermissions.get(i).getBox().equals(box)){
                    myRequest = true;
                }
            }
            if (!myRequest){
                SharedBoxPermission sharedBoxPermission = new SharedBoxPermission();
                sharedBoxPermission.setBox(box);
                sharedBoxPermission.setCustomer(box.getCustomer());
                sharedBoxPermission.setNeighbour(customer);
                sharedBoxPermission.setStatus(0);
                laundryOrderManagementRemote.createSharedBoxPermission(sharedBoxPermission);
            }
        } else {
            SharedBoxPermission sharedBoxPermission = new SharedBoxPermission();
            sharedBoxPermission.setBox(box);
            sharedBoxPermission.setCustomer(box.getCustomer());
            sharedBoxPermission.setNeighbour(customer);
            sharedBoxPermission.setStatus(0);
            laundryOrderManagementRemote.createSharedBoxPermission(sharedBoxPermission);
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
