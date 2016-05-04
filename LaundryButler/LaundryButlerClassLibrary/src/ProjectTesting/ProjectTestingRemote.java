/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProjectTesting;

import entity.Address;
import entity.Customer;
import entity.Employee;
import entity.Product;


/**
 *
 * @author user
 */
public interface ProjectTestingRemote {
    public void createCustomer(Customer customer);
    public void createAddress(Address address);
    public void updateAddress(Address address);
    public void createEmployee(Employee employee);
     public void createProduct(Product product);
}
