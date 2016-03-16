package ejb.session.stateful;

import datamodel.Customer;
import datamodel.SalesTransactionLineItem;
import java.util.ArrayList;



public interface ShoppingSessionBeanLocal
{
    public Boolean login(String email, String password);
    
    public void logout();
    
    public Integer addProductItemToShoppingCart(String skuCode, Integer quantity);

    public Customer getCustomer();
    
    public void setCustomer(Customer customer);

    public ArrayList<SalesTransactionLineItem> getSalesTransactionLineItems();   
}