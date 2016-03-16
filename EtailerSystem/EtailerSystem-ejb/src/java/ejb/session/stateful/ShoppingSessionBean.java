package ejb.session.stateful;

import datamodel.Customer;
import datamodel.ProductItem;
import datamodel.SalesTransactionLineItem;
import ejb.session.stateless.CustomerSessionBeanLocal;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;



@Stateful
@Local(ShoppingSessionBeanLocal.class)
@Remote(ShoppingSessionBeanRemote.class)

public class ShoppingSessionBean implements ShoppingSessionBeanLocal, ShoppingSessionBeanRemote
{
    @EJB
    CustomerSessionBeanLocal customerSessionBeanLocal;
    
    private Customer customer;
    private ArrayList<SalesTransactionLineItem> salesTransactionLineItems;

    
    
    public ShoppingSessionBean() 
    {
        customer = null;
        salesTransactionLineItems = new ArrayList<>();
    }    
    
    
    
    @Override
    public Boolean login(String email, String password)
    {
        customer = customerSessionBeanLocal.retrieveCustomerByEmail(email);
        
        if(customer != null)
        {
            if(customer.getPassword().equals(password))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    
    
    @Override
    public void logout()
    {
        customer = null;
        salesTransactionLineItems = new ArrayList<>();
    }
    
    
    
    @Override
    public Integer addProductItemToShoppingCart(String skuCode, Integer quantity)
    {
        Boolean existingProductItem = false;
        Integer newQuantity = 0;
                        
        for(SalesTransactionLineItem salesTransactionLineItem:salesTransactionLineItems)
        {
            if(salesTransactionLineItem.getProductItem().getSkuCode().equals(skuCode))
            {                
                newQuantity = salesTransactionLineItem.getQuantity() + quantity;
                salesTransactionLineItem.setQuantity(newQuantity);
                existingProductItem = true;
                
                break;
            }
        }
        
        if(!existingProductItem)
        {
            newQuantity = quantity;
            salesTransactionLineItems.add(new SalesTransactionLineItem(newQuantity, new ProductItem(skuCode)));
        }
        
        return newQuantity;
    }
    

    
    
    @Override
    public Customer getCustomer() {
        return customer;
    }

    @Override
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public ArrayList<SalesTransactionLineItem> getSalesTransactionLineItems() {
        return salesTransactionLineItems;
    }

    public void setSalesTransactionLineItems(ArrayList<SalesTransactionLineItem> salesTransactionLineItems) {
        this.salesTransactionLineItems = salesTransactionLineItems;
    }
}