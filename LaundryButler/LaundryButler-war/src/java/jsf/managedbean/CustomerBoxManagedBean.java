/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import entity.Box;
import entity.Customer;
import entity.SharedBoxPermission;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author XUAN
 */
@Named(value = "customerBoxManagedBean")
@RequestScoped
public class CustomerBoxManagedBean implements Serializable{
    
    @Inject
    private SignUpAndLoginManagedBean signUpAndLoginManagedBean;
    
    private Box box;
    private Customer customer;
    private List<Box> boxes;
    private List <Box> boxesIsShared ;
    
    
    public CustomerBoxManagedBean() {
        box = new Box();
        boxes = new ArrayList<>();
        customer = new Customer();
        boxesIsShared = new ArrayList<>();
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
        
        customer = signUpAndLoginManagedBean.getAccountManagementRemote().getCustomer();
        if(signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllBoxByCustomerId(customer.getCustomerId()) != null)
            boxes = signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllBoxByCustomerId(customer.getCustomerId());
    }
    
    public void updateBoxIsExpress (Box box) {
        System.out.println("updateBoxIsExpress is called");
        if (customer.getExpress() >= 0){
            if (box.getIsExpress()){
                box.setIsExpress(false);
                int num = customer.getExpress();
                num ++;
                customer.setExpress(num);
                signUpAndLoginManagedBean.getAccountManagementRemote().updateCutomerProfile(customer);
                signUpAndLoginManagedBean.getLaundryOrderManagementRemote().updateBox(box);
            } else {
                if (customer.getExpress() != 0) {
                    box.setIsExpress(true);
                    int num = customer.getExpress();
                    num --;
                    customer.setExpress(num);
                    signUpAndLoginManagedBean.getAccountManagementRemote().updateCutomerProfile(customer);
                    signUpAndLoginManagedBean.getLaundryOrderManagementRemote().updateBox(box);
                } else {
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sorry, you dont have enough express cleaning!","Sorry, you dont have enough  express cleaning!"));
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sorry, you dont have enough express cleaning!","Sorry, you dont have enough  express cleaning!"));
        }
    }
    
    public void updateBoxAllowSharing (Box box){
        System.out.println("updateBoxIsShared is called");
        if (box.getAllowSharing()){
            box.setAllowSharing(false);
            List<SharedBoxPermission> sharedBoxPermissions = signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllSharedBoxPermissionByBoxId(box.getBoxId());
            List<Long> tempList = new ArrayList<Long>();
            if (sharedBoxPermissions != null) {
                for (int i = 0; i < sharedBoxPermissions.size(); i ++){
                    tempList.add(sharedBoxPermissions.get(i).getSharedBoxPermissionId());
                }
                for (int i = 0; i < tempList.size(); i ++){
                    signUpAndLoginManagedBean.getLaundryOrderManagementRemote().deleteSharedBoxPermission(tempList.get(i));
                }
            }
        } else {
            System.out.println("false to true");
            box.setAllowSharing(true);
        }
        signUpAndLoginManagedBean.getLaundryOrderManagementRemote().updateBox(box);
        System.out.println("updateBoxIsShared status: " + box.getIsShared());
    }
    
    //Status Code
    //0: reuqested, pending
    //1: accepted
    //-1: denied
    
    public void updateSharedBoxPermission (Long shareBoxPermissionId){
        SharedBoxPermission sharedBoxPermission = signUpAndLoginManagedBean.getLaundryOrderManagementRemote().retrieveSharedBoxPermissionByPermissionId(shareBoxPermissionId);
        List<SharedBoxPermission> sharedBoxPermissionList = signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllSharedBoxPermissionByBoxId(sharedBoxPermission.getBox().getBoxId());
        if (sharedBoxPermissionList != null){
            for (int i = 0; i < sharedBoxPermissionList.size(); i ++){
                if (sharedBoxPermissionList.get(i).getSharedBoxPermissionId().equals(shareBoxPermissionId)){
                    sharedBoxPermissionList.get(i).setStatus(1);
                } else {
                    sharedBoxPermissionList.get(i).setStatus(-1);
                }
                signUpAndLoginManagedBean.getLaundryOrderManagementRemote().updateSharedBoxPermission(sharedBoxPermissionList.get(i));
            }
        }
        sharedBoxPermission.getBox().setIsShared(true);
        signUpAndLoginManagedBean.getLaundryOrderManagementRemote().updateBox(sharedBoxPermission.getBox());
        if(signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllBoxByCustomerId(customer.getCustomerId()) != null)
            boxes = signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllBoxByCustomerId(customer.getCustomerId());
    }
    
    public Customer sharingCustomer (Box box){
        List<SharedBoxPermission> sharedBoxPermissionList = signUpAndLoginManagedBean.getLaundryOrderManagementRemote().viewAllSharedBoxPermissionByBoxId(box.getBoxId());
        Customer returnCustomer = null;
        if (sharedBoxPermissionList != null){
            for (int i = 0; i < sharedBoxPermissionList.size(); i ++){
                if (sharedBoxPermissionList.get(i).getStatus().equals(new Integer(1)) ){
                    returnCustomer = sharedBoxPermissionList.get(i).getNeighbour();
                }
            }
        }
        return returnCustomer;
    }
    
    public void acceptRequest (SharedBoxPermission sharedBoxPermission){
        sharedBoxPermission.setStatus(1);
        signUpAndLoginManagedBean.getLaundryOrderManagementRemote().updateSharedBoxPermission(sharedBoxPermission);
    }
    
    public void denyRequest (SharedBoxPermission sharedBoxPermission){
        sharedBoxPermission.setStatus(-1);
        signUpAndLoginManagedBean.getLaundryOrderManagementRemote().updateSharedBoxPermission(sharedBoxPermission);
    }
    
    public void updateBoxDryCleaning (Box box){
        int change = box.getDryCleaning() - signUpAndLoginManagedBean.getLaundryOrderManagementRemote().retrieveBoxByBoxId(box.getBoxId()).getDryCleaning();
        System.out.println("updateBoxDryCleaning is called");
        if (change - customer.getDryCleaning() >0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sorry, you dont have enough dry cleaning!","Sorry, you dont have enough dry cleaning!"));
        } else {
            signUpAndLoginManagedBean.getLaundryOrderManagementRemote().updateBox(box);
            customer.setDryCleaning(customer.getDryCleaning() - change);
            signUpAndLoginManagedBean.getAccountManagementRemote().updateCutomerProfile(customer);
            System.out.println("updateBoxDryCleaning number: " + box.getDryCleaning());
        }
    }
    
    public void updateBox (Box box) {
        signUpAndLoginManagedBean.getLaundryOrderManagementRemote().updateBox(box);
    }
    
    public void deleteBox (Box boxToDelete) {
        
        if(signUpAndLoginManagedBean.getLaundryOrderManagementRemote().deleteBox(boxToDelete.getBoxId())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
        }
    }
    
    public Box getBox() {
        return box;
    }
    
    public void setBox(Box box) {
        this.box = box;
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public List<Box> getBoxes() {
        return boxes;
    }
    
    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }
    
    public List<Box> getBoxesIsShared() {
        return boxesIsShared;
    }
    
    public void setBoxesIsShared(List<Box> boxesIsShared) {
        this.boxesIsShared = boxesIsShared;
    }
}
