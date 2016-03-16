package etailersystemclient;

import datamodel.Customer;
import datamodel.ProductCategory;
import datamodel.ProductItem;
import datamodel.SalesTransactionLineItem;
import ejb.session.stateful.ShoppingSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.ProductCatalogueSessionBeanRemote;
import java.util.ArrayList;
import java.util.Scanner;
import util.helper.BigDecimalHelper;

public class Controller {

    CustomerSessionBeanRemote customerSessionBeanRemote;
    ProductCatalogueSessionBeanRemote productCatalogueSessionBeanRemote;
    ShoppingSessionBeanRemote shoppingSessionBeanRemote;

    public Controller() {
    }

    public Controller(CustomerSessionBeanRemote customerSessionBeanRemote, ProductCatalogueSessionBeanRemote productCatalogueSessionBeanRemote, ShoppingSessionBeanRemote shoppingSessionBeanRemote) {
        this.customerSessionBeanRemote = customerSessionBeanRemote;
        this.productCatalogueSessionBeanRemote = productCatalogueSessionBeanRemote;
        this.shoppingSessionBeanRemote = shoppingSessionBeanRemote;
    }

    public void runSystem() {
        Scanner scanner = new Scanner(System.in);
        Integer response;

        while (true) {
            System.out.println("*** Welcome to Etailer System ***\n");
            System.out.println("1: Customer Management");
            System.out.println("2: Product Catalogue Management");
            System.out.println("3: Shopping Management");
            System.out.println("4: Exit\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response.equals(1)) {
                    menuCustomerManagement();
                } else if (response == 2) {
                    menuProductCatalogueManagement();
                } else if (response == 3) {
                    menuShoppingManagement();
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 4) {
                break;
            }
        }
    }

    private void menuCustomerManagement() {
        Scanner scanner = new Scanner(System.in);
        Integer response;

        while (true) {
            System.out.println("*** Etailer System > Customer Management ***\n");
            System.out.println("1: Create Customer");
            System.out.println("2: Update Customer");
            System.out.println("3: Delete Customer");
            System.out.println("4: Back to Main Menu\n");
            response = 0;

            while (response < 1 || response > 4) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    createCustomer();
                } else if (response == 2) {
                    updateCustomer();
                } else if (response == 3) {
                    deleteCustomer();
                } else if (response == 4) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 4) {
                return;
            }
        }
    }

    private void createCustomer() {
        Customer customer = new Customer();
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Etailer System > Customer Management > Create Customer ***\n");
        System.out.print("First Name: ");
        customer.setFirstName(scanner.nextLine());
        System.out.print("Last Name: ");
        customer.setLastName(scanner.nextLine());
        System.out.print("Email: ");
        customer.setEmail(scanner.nextLine());
        System.out.print("Password: ");
        customer.setPassword(scanner.nextLine());
        System.out.print("Address Line 1: ");
        customer.setAddressLine1(scanner.nextLine());
        System.out.print("Address Line 2: ");
        customer.setAddressLine2(scanner.nextLine());
        System.out.print("Address Line 3: ");
        customer.setAddressLine3(scanner.nextLine());
        System.out.print("Postal Code: ");
        customer.setPostalCode(scanner.nextLine());
        System.out.print("Country: ");
        customer.setCountry(scanner.nextLine());

        Integer newCustomerId = customerSessionBeanRemote.createCustomer(customer);

        if (newCustomerId != null) {
            System.out.println("New customer " + newCustomerId + " created successfully!\n\n");
        } else {
            System.out.println("An error has occurred while creating the new customer!\n\n");
        }
    }
    
    private void updateCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Etailer System > Customer Management > Update Customer ***\n");
        System.out.println("Select customer by email to update profile: ");
        String email = scanner.nextLine();
        Customer existingCustomer = customerSessionBeanRemote.retrieveCustomerByEmail(email);
        System.out.println("First Name: ");
        existingCustomer.setFirstName(scanner.nextLine());
        System.out.println("Last Name: ");
        existingCustomer.setLastName(scanner.nextLine());
        System.out.println("Email: ");
        existingCustomer.setEmail(scanner.nextLine());
        System.out.println("Password: ");
        existingCustomer.setPassword(scanner.nextLine());
        System.out.println("Address Line 1: ");
        existingCustomer.setAddressLine1(scanner.nextLine());
        System.out.println("Address Line 2: ");
        existingCustomer.setAddressLine2(scanner.nextLine());
        System.out.println("Address Line 3: ");
        existingCustomer.setAddressLine3(scanner.nextLine());
        System.out.println("Postal Code: ");
        existingCustomer.setPostalCode(scanner.nextLine());
        System.out.println("Country: ");
        existingCustomer.setCountry(scanner.nextLine());
        
        Integer updatedCustomerId = customerSessionBeanRemote.updateCustomer(existingCustomer);
        
        if (updatedCustomerId != null) {
            System.out.println("Customer " + updatedCustomerId + " updated successfully!\n\n");
        } else {
            System.out.println("An error has occurred while updating customer " + updatedCustomerId + "!\n\n");
        }
    }
    
    private void deleteCustomer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Etailer System > Customer Management > Delete Customer ***\n");
        System.out.println("Enter customer email to delete: ");
        String email = scanner.nextLine();
        Customer existingCustomer = customerSessionBeanRemote.retrieveCustomerByEmail(email);
        Integer deletedCustomerId = customerSessionBeanRemote.deleteCustomer(existingCustomer);
        
        if (deletedCustomerId != null) {
            System.out.println("Customer " + deletedCustomerId + " deleted successfully!\n\n");
        } else {
            System.out.println("An error has occurred while deleting customer " + deletedCustomerId + "!\n\n");
        }
    }

    private void menuProductCatalogueManagement() {
        Scanner scanner = new Scanner(System.in);
        Integer response;

        while (true) {
            System.out.println("*** Etailer System > Product Catalogue Management ***\n");
            System.out.println("1: Create Product Category");
            System.out.println("2: Retrieve All Product Categories");
            System.out.println("3: Create Product Item");
            System.out.println("4: Retrieve All Product Items");
            System.out.println("5: Back to Main Menu\n");
            response = 0;

            while (response < 1 || response > 5) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    createProductCategory();
                } else if (response == 2) {
                    retrieveAllProductCategories();
                } else if (response == 3) {
                    createProductItem();
                } else if (response == 4) {
                    retrieveAllProductItems();
                } else if (response == 5) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 5) {
                return;
            }
        }
    }

    private void createProductCategory() {
        ProductCategory productCategory = new ProductCategory();
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Etailer System > Product Catalogue Management > Create Product Category ***\n");
        System.out.print("Name: ");
        productCategory.setName(scanner.nextLine());

        Integer newProductCategoryId = productCatalogueSessionBeanRemote.createProductCategory(productCategory);

        if (newProductCategoryId != null) {
            System.out.println("New product category " + newProductCategoryId + " created successfully!\n\n");
        } else {
            System.out.println("An error has occurred while creating the new product category!\n\n");
        }
    }

    private void retrieveAllProductCategories() {
        ArrayList<ProductCategory> productCategories = productCatalogueSessionBeanRemote.retrieveAllProductCategories();

        System.out.println("*** Etailer System > Product Catalogue Management > Retrieve All Product Categories ***\n");

        for (ProductCategory productCategory : productCategories) {
            System.out.println(productCategory.getProductCategoryId() + "\t" + productCategory.getName());
        }

        System.out.println("-- End of Listing --\n\n");
    }

    private void createProductItem() {
        ProductItem productItem = new ProductItem();
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Etailer System > Product Catalogue Management > Create Product Item ***\n");
        System.out.print("SKU Code: ");
        productItem.setSkuCode(scanner.nextLine());
        System.out.print("Name: ");
        productItem.setName(scanner.nextLine());
        System.out.print("Description: ");
        productItem.setDescription(scanner.nextLine());
        System.out.print("Product Category Id: ");
        productItem.getProductCategory().setProductCategoryId(scanner.nextInt());
        System.out.print("Quantity On Hand: ");
        productItem.setQuantityOnHand(scanner.nextInt());
        System.out.print("Price: ");
        productItem.setPrice(scanner.nextBigDecimal());

        String newProductItemSkuCode = productCatalogueSessionBeanRemote.createProductItem(productItem);

        if (newProductItemSkuCode != null) {
            System.out.println("New product item " + newProductItemSkuCode + " created successfully!\n\n");
        } else {
            System.out.println("An error has occurred while creating the new product item!\n\n");
        }
    }

    private void retrieveAllProductItems() {
        ArrayList<ProductItem> productItems = productCatalogueSessionBeanRemote.retrieveAllProductItems();

        System.out.println("*** Etailer System > Product Catalogue Management > Retrieve All Product Items ***\n");

        for (ProductItem productItem : productItems) {
            System.out.println(productItem.getSkuCode() + "\t" + productItem.getName() + "\t" + productItem.getDescription() + "\t" + productItem.getProductCategory().getName() + "\t" + productItem.getQuantityOnHand() + "\t" + BigDecimalHelper.formatCurrency(productItem.getPrice()));
        }

        System.out.println("-- End of Listing --\n\n");
    }

    private void menuShoppingManagement() {
        Scanner scanner = new Scanner(System.in);
        Integer response;

        while (true) {
            System.out.println("*** Etailer System > Shopping Management ***\n");
            System.out.println("1: Customer Login");
            System.out.println("2: Browse All Product Items");
            System.out.println("3: Add Product Item to Shopping Cart");
            System.out.println("4: View Shopping Cart");
            System.out.println("5: Customer Logout");
            System.out.println("6: Back to Main Menu\n");
            response = 0;

            while (response < 1 || response > 6) {
                System.out.print("> ");

                response = scanner.nextInt();

                if (response == 1) {
                    customerLogin();
                } else if (response == 2) {
                    browseAllProductItems();
                } else if (response == 3) {
                    addProductItemToShoppingCart();
                } else if (response == 4) {
                    viewShoppingCart();
                } else if (response == 5) {
                    customerLogout();
                } else if (response == 6) {
                    break;
                } else {
                    System.out.println("Invalid option, please try again!\n");
                }
            }

            if (response == 6) {
                return;
            }
        }
    }

    private void customerLogin() {
        Scanner scanner = new Scanner(System.in);
        String email = null;
        String password = null;

        System.out.println("*** Etailer System > Shopping Management > Customer Login ***\n");

        if (shoppingSessionBeanRemote.getCustomer() == null) {
            System.out.print("Email: ");
            email = scanner.nextLine();
            System.out.print("Password: ");
            password = scanner.nextLine();

            if (shoppingSessionBeanRemote.login(email, password)) {
                System.out.println("Login successful, welcome back " + shoppingSessionBeanRemote.getCustomer().getFirstName() + " " + shoppingSessionBeanRemote.getCustomer().getLastName() + "!\n");
            } else {
                System.out.println("Invalid login credential, please try again!\n");
            }
        } else {
            System.out.println("You are already login!\n");
        }
    }

    private void browseAllProductItems() {
        ArrayList<ProductItem> productItems = productCatalogueSessionBeanRemote.retrieveAllProductItems();

        System.out.println("*** Etailer System > Shopping Management > Browse All Product Items ***\n");

        for (ProductItem productItem : productItems) {
            System.out.println(productItem.getSkuCode() + "\t" + productItem.getName() + "\t" + productItem.getProductCategory().getName() + "\t" + BigDecimalHelper.formatCurrency(productItem.getPrice()));
        }

        System.out.println("-- End of Listing --\n\n");
    }

    private void addProductItemToShoppingCart() {
        if (shoppingSessionBeanRemote.getCustomer() == null) {
            System.out.println("Please login first!\n\n");

            return;
        }

        String skuCode = null;
        Integer quantity = 0;
        Scanner scanner = new Scanner(System.in);

        System.out.println("*** Etailer System > Shopping Management > Add Product Item to Shopping Cart ***\n");
        System.out.print("SKU Code: ");
        skuCode = scanner.nextLine();
        System.out.print("Quantity: ");
        quantity = scanner.nextInt();

        Integer newQuantity = shoppingSessionBeanRemote.addProductItemToShoppingCart(skuCode, quantity);
        System.out.println("Product item added to shopping cart successfully with quantity " + newQuantity + "!\n\n");
    }

    private void viewShoppingCart() {
        if (shoppingSessionBeanRemote.getCustomer() == null) {
            System.out.println("Please login first!\n\n");

            return;
        }

        ArrayList<SalesTransactionLineItem> salesTransactionLineItems = shoppingSessionBeanRemote.getSalesTransactionLineItems();

        System.out.println("*** Etailer System > Shopping Management > View Shopping Cart ***\n");

        for (SalesTransactionLineItem salesTransactionLineItem : salesTransactionLineItems) {
            System.out.println(salesTransactionLineItem.getProductItem().getSkuCode() + "\t" + salesTransactionLineItem.getQuantity());
        }

        System.out.println("-- End of Listing --\n\n");
    }

    private void customerLogout() {
        System.out.println("*** Etailer System > Shopping Management > Customer Login ***\n");

        shoppingSessionBeanRemote.logout();

        System.out.println("You have logout successfully!\n");
    }
}
