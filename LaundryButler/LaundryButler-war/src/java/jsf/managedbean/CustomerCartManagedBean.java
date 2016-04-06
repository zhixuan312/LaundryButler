/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import ProductManagement.ProductManagementRemote;
import entity.CartLineItem;
import entity.Customer;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

/**
 *
 * @author XUAN
 */
@Named(value = "customerCartManagedBean")
@SessionScoped
public class CustomerCartManagedBean implements Serializable {
    
    @EJB
    private AccountManagementRemote accountManagementRemote;
    @EJB
    private ProductManagementRemote procutManagementRemote;
    
    private Customer customer;
    private List<CartLineItem> cartLineItems;
    private List<CartLineItem> selectedCartLineItems;
    private double totalPrice;
    private double singleItemTotalPrice; 
    private CartLineItem selectedCartLineItem;
    
    public CustomerCartManagedBean() {
        customer = new Customer();
        cartLineItems = new ArrayList<>();
        selectedCartLineItems = new ArrayList<>();
        totalPrice = 0; 
        singleItemTotalPrice = 0;
        selectedCartLineItem = new CartLineItem();
    }
    
    @PostConstruct
    public void init()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        try
        {
            if(ec.getSessionMap().get("login") == null)
            {
                ec.redirect("login.xhtml?faces-redirect=true");
            }
            else
            {
                if(ec.getSessionMap().get("login").equals(false))
                {
                    ec.redirect("login.xhtml?faces-redirect=true");
                }
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        customer = accountManagementRemote.getCustomer();
        cartLineItems = accountManagementRemote.getCustomer().getCartLineItems();
    }
    
    public void addProductToCart (ActionEvent event){
        
    }
    
    public void retireveSingleItemTotalPrice (ActionEvent event){
       singleItemTotalPrice = selectedCartLineItem.getProduct().getPrice() * selectedCartLineItem.getQuantity();
    }
    
    public void retireveTotalPrice (ActionEvent event){
        for (int i = 0; i < selectedCartLineItems.size(); i ++) {
            totalPrice = totalPrice + selectedCartLineItems.get(i).getProduct().getPrice() * selectedCartLineItems.get(i).getQuantity();
        }
    }
    
    public Customer getCustomer() {
        return customer;
    }
    
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    
    public List<CartLineItem> getCartLineItems() {
        return cartLineItems;
    }
    
    public void setCartLineItems(List<CartLineItem> cartLineItems) {
        this.cartLineItems = cartLineItems;
    }
    
    public List<CartLineItem> getSelectedCartLineItems() {
        return selectedCartLineItems;
    }
    
    public void setSelectedCartLineItems(List<CartLineItem> selectedCartLineItems) {
        this.selectedCartLineItems = selectedCartLineItems;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalAmount) {
        this.totalPrice = totalAmount;
    }

    public double getSingleItemTotalPrice() {
        return singleItemTotalPrice;
    }

    public void setSingleItemTotalPrice(double singleItemTotalPrice) {
        this.singleItemTotalPrice = singleItemTotalPrice;
    }

    public CartLineItem getSelectedCartLineItem() {
        return selectedCartLineItem;
    }

    public void setSelectedCartLineItem(CartLineItem selectedCartLineItem) {
        this.selectedCartLineItem = selectedCartLineItem;
    }
    
}
