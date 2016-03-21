package laundrybutlerclient;

import session.customer.AccountManagementRemote;

/**
 *
 * @author HoRenSen
 */
public class Controller {
    
    AccountManagementRemote accountManagementRemote;
    
    public Controller(AccountManagementRemote amr) {
        this.accountManagementRemote = amr;
    }
    
    void runClientApplication() {
        
    }

}
