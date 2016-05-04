package laundrybutlerclient;

import ProjectTesting.ProjectTestingRemote;
import entity.Address;
import entity.Customer;
import entity.Employee;
import entity.Product;
import java.util.Random;

public class Controller {

    ProjectTestingRemote projectTestingRemote;

    public Controller(ProjectTestingRemote projectTestingRemote) {
        
     
        this.projectTestingRemote = projectTestingRemote;
    }

    void runCreateCustomer() {
        //customer
        String[] firstNames = {"Alice", "Bob", "Charlie", "Daniel", "Emily", "Frank", "Gary", "Helen", "Ivy", "James"};
        String[] lastNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String[] emails = {"a@gmail.com", "b@gmail.com", "c@gmail.com", "d@gmail.com", "e@gmail.com", "f@gmail.com", "g@gmail.com", "h@gmail.com", "i@gmail.com", "j@gmail.com"};
        String[] verifications = {"1111", "2222", "3333", "4444", "5555", "6666", "7777", "8888", "9999", "0000"};
        String[] passwords = {"1111", "2222", "3333", "4444", "5555", "6666", "7777", "8888", "9999", "0000"};
        Boolean[] booleanArray = {true, false};

        //address
        String [] blocks = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String [] floors = {"1","2","3","4","5","6","7","8","9","10"};
        String [] addressUnits = {"1","2","3","4","5","6","7","8","9","10"};
        String[] streets = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String[] postalCodes = {"000000", "111111"};

        for (int i = 0; i < 10; i++) {
            Customer customer = new Customer();
            Address address = new Address();
            Random random = new Random();

            customer.setCustomerId(random.nextLong());
            customer.setFirstName(firstNames[random.nextInt(firstNames.length)]);   
            customer.setLastName(lastNames[random.nextInt(lastNames.length)]);
            customer.setIsFaceBook(booleanArray[random.nextInt(booleanArray.length)]);
            customer.setAccountStatus("Verified");
            customer.setEmail(emails[random.nextInt(emails.length)]);
            customer.setVerificationCode(verifications[random.nextInt(verifications.length)]);
            customer.setPassword(passwords[random.nextInt(passwords.length)]);
            customer.setMobile("+6591091201"); //Rensen's no
            
            
            address.setAddressId(random.nextLong());
            address.setBlock(blocks[random.nextInt(blocks.length)]);
            address.setFloor(floors[random.nextInt(floors.length)]);
            address.setUnit(addressUnits[random.nextInt(addressUnits.length)]);
            address.setStreet(streets[random.nextInt(streets.length)]);
            address.setCountry("Singapore");
            address.setPostalCode(postalCodes[random.nextInt(postalCodes.length)]);
            address.setCustomer(customer);
            //accountManagementRemote.register(customer);


            projectTestingRemote.createCustomer(customer);
            projectTestingRemote.updateAddress(address);
            
            
            //projectTestingRemote.updateAddress(address);

        }
    }
 
    void runCreateEmployee() {
       
        String[] emFirstNames = {"Alice", "Bob", "Charlie", "Daniel", "Emily", "Frank", "Gary", "Helen", "Ivy", "James"};
        String[] emLastNames = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
        String[] emEmails = {"a@gmail.com", "b@gmail.com", "c@gmail.com", "d@gmail.com", "e@gmail.com", "f@gmail.com", "g@gmail.com", "h@gmail.com", "i@gmail.com", "j@gmail.com"};
        String[] emPasswords = {"1111", "2222", "3333", "4444", "5555", "6666", "7777", "8888", "9999", "0000"};
        Boolean[] booleanArray = {true, false};
        
        for (int i = 0; i < 10; i++) {
            Employee employee = new Employee();

            Random random = new Random();

            employee.setEmployeeId(random.nextLong());
            employee.setEmployeeId(random.nextLong());
            String emFirstName = emFirstNames[random.nextInt(emFirstNames.length)];
            employee.setFirstName(emFirstName);
            String emLastName = emLastNames[random.nextInt(emLastNames.length)];
            employee.setLastName(emLastName);
            employee.setEmail(emEmails[random.nextInt(emEmails.length)]);
            employee.setPassword(emPasswords[random.nextInt(emPasswords.length)]);
            employee.setMobile("+6591091201");
            employee.setIsAdmin(booleanArray[random.nextInt(booleanArray.length)]);


            projectTestingRemote.createEmployee(employee);


        }

    }
    void runCreateProduct() {
       
        String[] names = {"12 Boxes", "24 Boxes", "48 Boxes", "1 Box", "Express Laundry", "Dry Cleaning"};
        String[] descriptions = {"Weekly delivery of empty box for the next 12 weeks", "Weekly delivery of empty box for the next 24 weeks", "Weekly delivery of empty box for the next 48 weeks", "One-time pickup", "Delivery within 24 hours", "Clean with organic solvent without using water"};
        Integer[] units = {12, 24, 48, 1, 1, 0};
        Long[] ids = {Long.valueOf(1), Long.valueOf(2), Long.valueOf(3), Long.valueOf(4), Long.valueOf(5), Long.valueOf(6)};
        Double[] prices = {383.90, 671.90, 1199.90, 34.90, 9.90, 5.90};
        
        for (int i = 0; i < 6; i++) {
            Product product = new Product();
            product.setProductId(ids[i]);
            product.setName(names[i]);
            product.setDescription(descriptions[i]);
            product.setNumberOfUnits(units[i]);
            product.setPrice(prices[i]);
            
            projectTestingRemote.createProduct(product);
       
        }
    }
}
