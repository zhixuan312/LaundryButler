package laundrybutlerclient;

import javax.ejb.EJB;
import AccountManagement.AccountManagementRemote;
import ProductManagement.ProductManagementRemote;

public class Main {

    @EJB
    private static AccountManagementRemote accountManagementRemote;
    @EJB
    private static ProductManagementRemote productManagementRemote;

    public static void main(String[] args) {

        Controller controller = new Controller(accountManagementRemote, productManagementRemote);
        controller.runClientApplication();

    }

}
