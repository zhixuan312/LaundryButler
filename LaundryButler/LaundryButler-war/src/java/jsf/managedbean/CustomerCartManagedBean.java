/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import ProductManagement.ProductManagementRemote;
import TransactionManagement.TransactionManagementRemote;
import entity.CartLineItem;
import entity.Customer;
import entity.Product;
import entity.Transaction;
import entity.TransactionLineItem;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
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
    private ProductManagementRemote productManagementRemote;
    @EJB
    private TransactionManagementRemote transactionManagementRemote;
    
    private Customer customer;
    private List<CartLineItem> cartLineItems;
    private List<CartLineItem> selectedCartLineItems;
    private double totalPrice;
    private double singleItemTotalPrice;
    private CartLineItem selectedCartLineItem;
    private CartLineItem newCartLineItem;
    private CartLineItem cartLineItemToRemove;
    private CartLineItem cartLineItemAfterEdit;
    private List<CartLineItem> readyToPayCartItems;
    private List<TransactionLineItem> readyToPayTransactionLineItems;
    private Transaction transaction;
    
    public CustomerCartManagedBean() {
        customer = new Customer();
        cartLineItems = new ArrayList<>();
        selectedCartLineItems = new ArrayList<>();
        totalPrice = 0;
        singleItemTotalPrice = 0;
        selectedCartLineItem = new CartLineItem();
        readyToPayCartItems = new ArrayList<>();
        newCartLineItem = new CartLineItem();
        cartLineItemToRemove = new CartLineItem();
        cartLineItemAfterEdit = new CartLineItem();
        readyToPayTransactionLineItems = new ArrayList<>();
        transaction = new Transaction();
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
        accountManagementRemote.resetCartLineItemForCheckOut();
        if (productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId()) != null){
            cartLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
        }
    }
    
    public void addProductToCart (Product product){
        List<CartLineItem> carLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
        if (carLineItems != null && !carLineItems.isEmpty()){
            for (int i = 0; i < carLineItems.size(); i ++) {
                if (carLineItems.get(i).getProduct().equals(product)){
                    CartLineItem cartLineItem = carLineItems.get(i);
                    int quantity = cartLineItem.getQuantity();
                    quantity ++;
                    cartLineItem.setQuantity(quantity);
                    productManagementRemote.updateCartLineItem(cartLineItem);
                }
            }
            newCartLineItem.setProduct(product);
            newCartLineItem.setCustomer(customer);
            newCartLineItem.setQuantity(1);
            productManagementRemote.createCartLineItem(newCartLineItem);
        } else {
            newCartLineItem.setProduct(product);
            newCartLineItem.setCustomer(customer);
            newCartLineItem.setQuantity(1);
            productManagementRemote.createCartLineItem(newCartLineItem);
        }
    }
    
    public void deductProductFromCart (Product product){
        List<CartLineItem> carLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
        if (!carLineItems.isEmpty()){
            for (int i = 0; i < carLineItems.size(); i ++) {
                if (carLineItems.get(i).getProduct().equals(product)){
                    CartLineItem cartLineItem = carLineItems.get(i);
                    int quantity = cartLineItem.getQuantity();
                    quantity --;
                    cartLineItem.setQuantity(quantity);
                    productManagementRemote.updateCartLineItem(cartLineItem);
                }
            }
        }
    }
    
    public void removeProductFromCart (Product product){
        List<CartLineItem> carLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
        if (!carLineItems.isEmpty()){
            for (int i = 0; i < carLineItems.size(); i ++) {
                if (carLineItems.get(i).getProduct().equals(product)){
                    Long cartLineItemId = carLineItems.get(i).getCartLineItemId();
                    productManagementRemote.deleteCartLineItem(cartLineItemId);
                }
            }
        }
        
    }
    
    public boolean isCartEmpty(){
        for (int i = 0; i < cartLineItems.size(); i ++){
            if (cartLineItems.get(i).getQuantity() != 0){
                return false;
            }
        }
        return true;
    }
    
    public void retireveSingleItemTotalPrice (ActionEvent event){
        singleItemTotalPrice = selectedCartLineItem.getProduct().getPrice() * selectedCartLineItem.getQuantity();
    }
    
    public void retireveTotalPrice (ActionEvent event){
        for (int i = 0; i < selectedCartLineItems.size(); i ++) {
            totalPrice = totalPrice + selectedCartLineItems.get(i).getProduct().getPrice() * selectedCartLineItems.get(i).getQuantity();
        }
    }
    
    public void checkOutButton(ActionEvent event){
        if (!selectedCartLineItems.isEmpty()){
            for (int i = 0; i < selectedCartLineItems.size(); i ++){
                accountManagementRemote.addCartLineItemForCheckOut(selectedCartLineItems.get(i));
            }
        }
        
        readyToPayCartItems = customer.getCartLineItems();
        
        if(!readyToPayCartItems.isEmpty()){
            for(int i = 0; i < readyToPayCartItems.size(); i ++) {
                TransactionLineItem transactionLineItem = new TransactionLineItem();
                transactionLineItem.setQuantity(readyToPayCartItems.get(i).getQuantity());
                transactionLineItem.setUnitCharge(readyToPayCartItems.get(i).getProduct().getPrice());
                transactionLineItem.setTotalCharge(readyToPayCartItems.get(i).getProduct().getPrice() * readyToPayCartItems.get(i).getQuantity());
                transactionLineItem.setProduct(readyToPayCartItems.get(i).getProduct());
                transactionManagementRemote.createTransactionLineItem(transactionLineItem);
                readyToPayTransactionLineItems.add(transactionLineItem);
            }
        }
        
        transaction.setCustomer(customer);
        Double totalCharge = new Double(0);
        if (!readyToPayTransactionLineItems.isEmpty()){
            for (int i = 0; i < readyToPayTransactionLineItems.size(); i ++){
                totalCharge = totalCharge + readyToPayTransactionLineItems.get(i).getTotalCharge();
            }
            transaction.setTotalCharge(totalCharge);
        } else {
            transaction.setTotalCharge(new Double (0));
        }
        transaction.setTransactionDateTime(new Date());
        if (transactionManagementRemote.createTransaction(transaction)){
            if (!readyToPayTransactionLineItems.isEmpty()){
                for (int j = 0; j < readyToPayTransactionLineItems.size(); j ++) {
                    readyToPayTransactionLineItems.get(j).setTransaction(transaction);
                    transactionManagementRemote.updateTransactionLineItem(readyToPayTransactionLineItems.get(j));
                }
            }
        }
        transactionManagementRemote.setTranscation(transaction);
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
    
    public CartLineItem getNewCartLineItem() {
        return newCartLineItem;
    }
    
    public void setNewCartLineItem(CartLineItem newCartLineItem) {
        this.newCartLineItem = newCartLineItem;
    }
    
    public CartLineItem getCartLineItemToRemove() {
        return cartLineItemToRemove;
    }
    
    public void setCartLineItemToRemove(CartLineItem cartLineItemToRemove) {
        this.cartLineItemToRemove = cartLineItemToRemove;
    }
    
    public CartLineItem getCartLineItemAfterEdit() {
        return cartLineItemAfterEdit;
    }
    
    public void setCartLineItemAfterEdit(CartLineItem cartLineItemAfterEdit) {
        this.cartLineItemAfterEdit = cartLineItemAfterEdit;
    }
    
}
