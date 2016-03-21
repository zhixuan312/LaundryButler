package laundrybutlerclient;

import javax.ejb.EJB;
import session.customer.AccountManagementRemote;

public class Main {

    @EJB
    private static AccountManagementRemote accountManagementRemote;

    public static void main(String[] args) {

        Controller controller = new Controller(accountManagementRemote);
        controller.runClientApplication();

    }

}
