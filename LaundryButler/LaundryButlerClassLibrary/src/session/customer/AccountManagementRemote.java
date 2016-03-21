package session.customer;

import entity.Customer;
import entity.Transaction;

/**
 *
 * @author HoRenSen
 */
public interface AccountManagementRemote {

    public Boolean register(Customer customer);
    
    public Boolean activate(String verificationCode);
    
    public Boolean login(String username, String password);

    public Customer viewAccount();
    
    public Transaction viewTransactionHistory();
    
    public Boolean logout();
    
}
