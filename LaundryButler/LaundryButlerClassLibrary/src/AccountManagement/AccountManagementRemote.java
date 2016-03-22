package AccountManagement;

import entity.Address;
import entity.Card;
import entity.Customer;
import entity.Employee;
import entity.Transaction;
import java.util.List;

/**
 *
 * @author HoRenSen
 */
public interface AccountManagementRemote {
    
     public Boolean register(Customer customer);
    
    public Boolean activate(String verificationCode);
    
    public Boolean login(String email, String password);
    
    public Customer viewAccount();
    
    public Transaction viewTransactionHistory();
    
    public Boolean logout();
    
    public Boolean updateCutomerProfile (Customer customer);
    
    public List<Customer> viewAllRecordedCustomer ();
    
    public Boolean createNewEmployee (Employee employee);
    
    public Boolean changeIsAdminStatus (Long employeeId);
    
    public Boolean updateEmployeeProfile (Employee employee);
    
    public List<Employee> viewAllRecordedEmployee ();
    
    public Boolean deleteEmployee (Long employeeId);
    
    public Boolean createAddress (Address address);
    
    public Boolean updateAddress (Address address);
    
    public List<Address> viewAllAddressByCustomer(Long customerId);
    
    public List<Address> viewAllRecordedAddress ();
    
    public Boolean deleteAddress (Long addressId);
    
    public Boolean createCard (Card card);
    
    public Boolean updateCard (Card card);
    
    public List<Card> viewAllCardByCustomer(Long customerId);
    
    public List<Card> viewAllRecordedCard ();
    
    public Boolean deleteCard (Long cardId);
    
}
