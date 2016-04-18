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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

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
    private double totalPrice;
    private CartLineItem newCartLineItem;
    private CartLineItem cartLineItemToRemove;
    private CartLineItem cartLineItemAfterEdit;
    private List<CartLineItem> readyToPayCartItems;
    private List<TransactionLineItem> readyToPayTransactionLineItems;
    private Transaction transaction;
    private String stripeCurrency;
    private String stripeLocale;
    private String stripeName;
    private String stripeAmount;
    
    public CustomerCartManagedBean() {
        customer = new Customer();
        cartLineItems = new ArrayList<>();
        totalPrice = 0;
        readyToPayCartItems = new ArrayList<>();
        newCartLineItem = new CartLineItem();
        cartLineItemToRemove = new CartLineItem();
        cartLineItemAfterEdit = new CartLineItem();
        readyToPayTransactionLineItems = new ArrayList<>();
        transaction = new Transaction();
        stripeCurrency = "SGD";
        stripeLocale = "auto";
        stripeName = "LaundryButler";
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
        if (productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId()) != null){
            cartLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
        }
    }
    
    public List<CartLineItem> refreshList(){
        if (productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId()) != null){
            cartLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
        }
        return cartLineItems;
    }
    
    public void addProductToCart (Product product){
        Boolean isThere = false;
        cartLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
        if (cartLineItems != null && !cartLineItems.isEmpty()){
            for (int i = 0; i < cartLineItems.size(); i ++) {
                if (cartLineItems.get(i).getProduct().equals(product)){
                    CartLineItem newCartLineItem = new CartLineItem();
                    newCartLineItem.setCartLineItemId(cartLineItems.get(i).getCartLineItemId());
                    newCartLineItem.setCustomer(customer);
                    newCartLineItem.setProduct(product);
                    int quantity = cartLineItems.get(i).getQuantity()+1;
                    newCartLineItem.setQuantity(quantity);
                    productManagementRemote.updateCartLineItem(newCartLineItem);
                    cartLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
                    isThere = true;
                    retrieveTotalPrice ();
                }
            }
            if (!isThere){
                newCartLineItem.setProduct(product);
                newCartLineItem.setCustomer(customer);
                newCartLineItem.setQuantity(1);
                productManagementRemote.createCartLineItem(newCartLineItem);
                cartLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
                retrieveTotalPrice ();
            }
        } else {
            newCartLineItem.setProduct(product);
            newCartLineItem.setCustomer(customer);
            newCartLineItem.setQuantity(1);
            productManagementRemote.createCartLineItem(newCartLineItem);
            cartLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
            retrieveTotalPrice ();
        }
    }
    
    public void deductProductFromCart (Product product){
        cartLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
        if (cartLineItems != null && !cartLineItems.isEmpty()){
            for (int i = 0; i < cartLineItems.size(); i ++) {
                if (cartLineItems.get(i).getProduct().equals(product)){
                    CartLineItem newCartLineItem = new CartLineItem();
                    newCartLineItem.setCartLineItemId(cartLineItems.get(i).getCartLineItemId());
                    newCartLineItem.setCustomer(customer);
                    newCartLineItem.setProduct(product);
                    int quantity = cartLineItems.get(i).getQuantity()-1;
                    if (quantity < 0) {
                        quantity = 0;
                    }
                    newCartLineItem.setQuantity(quantity);
                    productManagementRemote.updateCartLineItem(newCartLineItem);
                }
            }
        }
//        cartLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
//        for (int j = 0; j < cartLineItems.size(); j ++){
//            if (cartLineItems.get(j).getQuantity() == 0){
//                productManagementRemote.deleteCartLineItem(cartLineItems.get(j).getCartLineItemId());
//            }
//        }
        cartLineItems = productManagementRemote.viewAllCartLineItemByCustomerId(customer.getCustomerId());
        retrieveTotalPrice ();
    }
    
    public boolean isCartEmpty(){
        for (int i = 0; i < cartLineItems.size(); i ++){
            if (cartLineItems.get(i).getQuantity() != 0){
                return false;
            }
        }
        return true;
    }
    
    public void retrieveTotalPrice (){
        double tempPrice = 0;
        for (int i = 0; i < cartLineItems.size(); i ++) {
            tempPrice = tempPrice + cartLineItems.get(i).getQuantity() * cartLineItems.get(i).getProduct().getPrice();
        }
        totalPrice = tempPrice;

        stripeAmount = String.valueOf(totalPrice * 100);
        stripeAmount = stripeAmount.substring(0, stripeAmount.indexOf("."));
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
    
    public double getTotalPrice() {
        return totalPrice;
    }
    
    public void setTotalPrice(double totalAmount) {
        this.totalPrice = totalAmount;
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
    
    public TransactionManagementRemote getTransactionManagementRemote() {
        return transactionManagementRemote;
    }
    
    public void setTransactionManagementRemote(TransactionManagementRemote transactionManagementRemote) {
        this.transactionManagementRemote = transactionManagementRemote;
    }
    
    public List<CartLineItem> getReadyToPayCartItems() {
        return readyToPayCartItems;
    }
    
    public void setReadyToPayCartItems(List<CartLineItem> readyToPayCartItems) {
        this.readyToPayCartItems = readyToPayCartItems;
    }
    
    public List<TransactionLineItem> getReadyToPayTransactionLineItems() {
        return readyToPayTransactionLineItems;
    }
    
    public void setReadyToPayTransactionLineItems(List<TransactionLineItem> readyToPayTransactionLineItems) {
        this.readyToPayTransactionLineItems = readyToPayTransactionLineItems;
    }
    
    public Transaction getTransaction() {
        return transaction;
    }
    
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
    
    public String getStripeCurrency() {
        return stripeCurrency;
    }
    
    public void setStripeCurrency(String stripeCurrency) {
        this.stripeCurrency = stripeCurrency;
    }
    
    public String getStripeLocale() {
        return stripeLocale;
    }
    
    public void setStripeLocale(String stripeLocale) {
        this.stripeLocale = stripeLocale;
    }
    
    public String getStripeName() {
        return stripeName;
    }
    
    public void setStripeName(String stripeName) {
        this.stripeName = stripeName;
    }
    
    public String getStripeAmount() {
        return stripeAmount;
    }
    
    public void setStripeAmount(String stripeAmount) {
        this.stripeAmount = stripeAmount;
    }
    
}
