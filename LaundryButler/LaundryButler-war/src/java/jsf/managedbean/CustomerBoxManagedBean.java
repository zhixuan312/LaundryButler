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
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author XUAN
 */
@Named(value = "customerBoxManagedBean")
@RequestScoped
public class CustomerBoxManagedBean implements Serializable{
    
    @EJB
    private LaundryOrderManagementRemote laundryOrderManagementRemote;
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private Box box;
    private Customer customer;
    private List<Box> boxes;
    
    
    public CustomerBoxManagedBean() {
        box = new Box();
        boxes = new ArrayList<>();
        customer = new Customer();
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
        if(laundryOrderManagementRemote.viewAllBoxByCustomerId(customer.getCustomerId()) != null)
            boxes = laundryOrderManagementRemote.viewAllBoxByCustomerId(customer.getCustomerId());
    }
    
    public void updateBoxIsExpress (Box box) {
        System.out.println("updateBoxIsExpress is called");
        if (customer.getExpress() > 0){
            if (box.getIsExpress()){
                box.setIsExpress(false);
            } else {
                box.setIsExpress(true);
            }
            laundryOrderManagementRemote.updateBox(box);
            if (box.getIsExpress())
                isExpressBox ();
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sorry, you dont have enough express cleaning!","Sorry, you dont have enough  express cleaning!"));
        }
    }
    
    public void isExpressBox (){
        int num = customer.getExpress();
        num --;
        customer.setExpress(num);
        accountManagementRemote.updateCutomerProfile(customer);
    }
    
    public void updateBoxAllowSharing (Box box){
        System.out.println("updateBoxIsShared is called");
        if (box.getAllowSharing()){
            box.setAllowSharing(false);
        } else {
            box.setAllowSharing(true);
        }
        laundryOrderManagementRemote.updateBox(box);
        System.out.println("updateBoxIsShared status: " + box.getIsShared());
    }
    
    public void updateBoxDryCleaning (Box box){
        long change = box.getDryCleaning() - laundryOrderManagementRemote.retrieveBoxByBoxId(box.getBoxId()).getDryCleaning();
        System.out.println("updateBoxDryCleaning is called");
        if (change - customer.getDryCleaning() >0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Sorry, you dont have enough dry cleaning!","Sorry, you dont have enough dry cleaning!"));
        } else {
            laundryOrderManagementRemote.updateBox(box);
            customer.setDryCleaning(customer.getDryCleaning() );
            accountManagementRemote.updateCutomerProfile(customer);
            System.out.println("updateBoxDryCleaning number: " + box.getDryCleaning());
        }
    }
    
    public void deleteBox (Box boxToDelete) {
        
        if(laundryOrderManagementRemote.deleteBox(boxToDelete.getBoxId())){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
        }
    }
    
    public void getIsSharedBoxFromOthers (){
        List <Box> boxes = laundryOrderManagementRemote.viewAllBox();
        List <Integer> tempList = new ArrayList<Integer> ();
        
        for(int i = 0 ; i < boxes.size(); i ++) {
            if (boxes.get(i).getCustomer().getCustomerId().equals(customer.getCustomerId())){
                tempList.add(i);
            } else if (!boxes.get(i).getAllowSharing()){
                tempList.add(i);
            }
        }
        if (!tempList.isEmpty()){
            for (int i =0; i < tempList.size();i++) {
                boxes.remove(i);
            }
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
}
