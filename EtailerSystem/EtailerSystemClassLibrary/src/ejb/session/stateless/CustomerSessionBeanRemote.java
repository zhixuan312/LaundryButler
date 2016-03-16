package ejb.session.stateless;

import datamodel.Customer;



public interface CustomerSessionBeanRemote
{
    public Integer createCustomer(Customer customer);
    
    public Customer retrieveCustomerByEmail(String email);
    
    public Integer updateCustomer(Customer customer);
    
    public Integer deleteCustomer(Customer customer);
}