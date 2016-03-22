package laundrybutlerclient;

import javax.ejb.EJB;
import AccountManagement.AccountManagementRemote;

public class Main {

    @EJB
    private static AccountManagementRemote accountManagementRemote;

    public static void main(String[] args) {

        Controller controller = new Controller(accountManagementRemote);
        controller.runClientApplication();

    }

}
