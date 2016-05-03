package laundrybutlerclient;

import AccountManagement.AccountManagementRemote;
import ProductManagement.ProductManagementRemote;
import entity.Address;
import entity.Customer;
import entity.Employee;
import entity.Product;
import java.util.Random;

public class Controller {

    AccountManagementRemote accountManagementRemote;
    ProductManagementRemote productManagementRemote;

    public Controller(AccountManagementRemote accountManagementRemote, ProductManagementRemote productManagementRemote) {
        this.accountManagementRemote = accountManagementRemote;
        this.productManagementRemote = productManagementRemote;
    }

    void runClientApplication() {
        //customer
        String[] firstNames = {"Alice", "Bob", "Charlie", "Daniel", "Emily", "Frank", "Gary", "Helen", "Ivy", "James"};
        String[] lastNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        Boolean[] booleanArray = {true, false};

        //employee
        String[] emfirstNames = {"Alice", "Bob", "Charlie", "Daniel", "Emily", "Frank", "Gary", "Helen", "Ivy", "James"};
        String[] emlastNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};

        //address
        String[] postalCodes = {"000000", "111111"};

        //product
        String[] names = {"12 Boxes", "24 Boxes", "48 Boxes", "1 Box", "Express Laundry", "Dry Cleaning"};
        String[] descriptions = {"Weekly delivery of empty box for the next 12 weeks", "Weekly delivery of empty box for the next 24 weeks", "Weekly delivery of empty box for the next 48 weeks", "One-time pickup", "Delivery within 24 hours", "Clean with organic solvent without using water"};
        Integer[] units = {12, 24, 48, 1, 1, 1};
        Long[] ids = {Long.valueOf(1), Long.valueOf(2), Long.valueOf(3), Long.valueOf(4), Long.valueOf(5), Long.valueOf(6)};
        Double[] prices = {383.90, 671.90, 1199.90, 34.90, 9.90, 5.90};

        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            Address address = new Address();
            Random random = new Random();

            customer.setCustomerId(random.nextLong());
            String custFirstName = firstNames[random.nextInt(firstNames.length)];
            customer.setFirstName(custFirstName);
            String custLastName = firstNames[random.nextInt(lastNames.length)];
            customer.setLastName(custLastName);
            customer.setIsFaceBook(booleanArray[random.nextInt(booleanArray.length)]);
            customer.setAccountStatus("Verified");

            address.setAddressId(random.nextLong());
            String currentPostalCode = postalCodes[random.nextInt(postalCodes.length)];
            address.setPostalCode(currentPostalCode);
            address.setCustomer(customer);

            accountManagementRemote.register(customer);

        }

        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee();

            Random random = new Random();

            employee.setEmployeeId(random.nextLong());
            employee.setEmployeeId(random.nextLong());
            String emFirstName = emfirstNames[random.nextInt(emfirstNames.length)];
            employee.setFirstName(emFirstName);
            String emLastName = emlastNames[random.nextInt(emlastNames.length)];
            employee.setLastName(emLastName);
            employee.setIsAdmin(booleanArray[random.nextInt(booleanArray.length)]);

            accountManagementRemote.createNewEmployee(employee);

        }

        //create products
        for (int i = 0; i < 6; i++) {
            Product product = new Product();
            product.setProductId(ids[i]);
            product.setName(names[i]);
            product.setDescription(descriptions[i]);
            product.setNumberOfUnits(units[i]);
            product.setPrice(prices[i]);

            productManagementRemote.createProduct(product);
        }

    }
}
