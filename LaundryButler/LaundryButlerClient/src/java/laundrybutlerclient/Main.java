package laundrybutlerclient;

import javax.ejb.EJB;
import ProductManagement.ProductManagementRemote;
import ProjectTesting.ProjectTestingRemote;

public class Main {

    
    
    @EJB 
    private static ProjectTestingRemote projectTestingRemote;

    public static void main(String[] args) {

        Controller controller = new Controller(projectTestingRemote);
        controller.runCreateCustomer();
        controller.runCreateEmployee();
        controller.runCreateProduct();

    }

}
