package laundrybutlerclient;

import AccountManagement.AccountManagementRemote;
import entity.Customer;

/**
 *
 * @author HoRenSen
 */
public class Controller {
    
    AccountManagementRemote am;
    
    public Controller(AccountManagementRemote amr) {
        this.am = amr;
    }
    
    void runClientApplication() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Deo");
        
        am.register(customer);
    }

}
