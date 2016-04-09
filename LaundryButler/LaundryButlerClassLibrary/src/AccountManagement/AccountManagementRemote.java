package AccountManagement;

import entity.Address;
import entity.Card;
import entity.CartLineItem;
import entity.Customer;
import entity.Employee;
import entity.Transaction;
import java.util.List;

/**
 *
 * @author HoRenSen
 */
public interface AccountManagementRemote {
    
    public Long register(Customer customer);
    
    public Boolean activate(long customerId, long verificationCode);
    
    public Boolean customerLogin(String email, String password);
    
    public Customer viewAccount();
    
    public Transaction viewTransactionHistory();
    
    public Boolean logout();
    
    public Boolean updateCutomerProfile (Customer customer);
    
    public List<Customer> viewAllRecordedCustomer ();
    
    public Long createNewEmployee (Employee employee);
    
    public Boolean changeIsAdminStatus (Long employeeId);
    
    public Boolean updateEmployeeProfile (Employee employee);
    
    public List<Employee> viewAllRecordedEmployee ();
    
    public Boolean deleteEmployee (Long employeeId);
    
    public Boolean createAddress (Address address);
    
    public Boolean updateAddress (Address address);
    
    public List<Address> viewAllAddressByCustomerId(Long customerId);
    
    public List<Address> viewAllRecordedAddress ();
    
    public Boolean deleteAddress (Long addressId);
    
    public Boolean deleteAddresses (List<Address> addresses);
    
    public Boolean createCard (Card card);
    
    public Boolean updateCard (Card card);
    
    public List<Card> viewAllCardByCustomerId(Long customerId);
    
    public List<Card> viewAllRecordedCard ();
    
    public Boolean deleteCard (Long cardId);
    
    public Boolean employeeLogin(String email, String password);
    
    public Customer getCustomer ();
    
    public Employee getEmployee();
    
    public Customer retrieveCustomerByEmail(String email);
    
    public Employee retrieveEmployeeByEmail(String email);
    
    public Boolean addCartLineItemToCart(CartLineItem cartLineItem);
    
    public Boolean removeCartLineItemFromCart (CartLineItem cartLineItem);
    
    public Boolean addCartLineItemForCheckOut(CartLineItem cartLineItem);
    
    public Boolean resetCartLineItemForCheckOut();
    
}
