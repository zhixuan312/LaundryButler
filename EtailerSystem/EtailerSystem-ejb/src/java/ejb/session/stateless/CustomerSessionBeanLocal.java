package ejb.session.stateless;

import datamodel.Customer;
import javax.ejb.Local;



@Local
public interface CustomerSessionBeanLocal
{
    public Integer createCustomer(Customer customer);

    public Customer retrieveCustomerByEmail(String email);
    
    public Integer updateCustomer(Customer customer);
    
    public Integer deleteCustomer(Customer customer);
}