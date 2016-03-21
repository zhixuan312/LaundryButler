package session.customer;

import entity.Customer;
import entity.Transaction;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author HoRenSen
 */
@Stateful
@Local(AccountManagementLocal.class)
@Remote(AccountManagementRemote.class)
public class AccountManagement implements AccountManagementRemote, AccountManagementLocal {

    @PersistenceContext
    private EntityManager em;
    
    private Boolean isLoggedIn = false;
    private long currentCustomerId = 0;

    @Override
    public Boolean register(Customer customer) {
        return false;
    }
    
    @Override
    public Boolean activate(String verificationCode) {
        return false;
    }
    
    @Override
    public Boolean login(String username, String password) {
        return false;
    }
    
    @Override
    public Customer viewAccount() {
        return null;
    }
    
    @Override
    public Transaction viewTransactionHistory() {
        return null;
    }

    @Override
    public Boolean logout() {
        return false;
    }
}