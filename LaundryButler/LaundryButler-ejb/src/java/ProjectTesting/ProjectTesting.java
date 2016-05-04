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
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local (ProjectTestingLocal.class) 
@Remote (ProjectTestingRemote.class)
public class ProjectTesting implements ProjectTestingRemote, ProjectTestingLocal {
    @PersistenceContext
    private EntityManager em;
    
    @Override 
    public void createCustomer(Customer customer) {
        em.persist(customer);
        em.flush();
    }
  
    @Override
    public void createAddress(Address address) {
        em.persist(address);
        em.flush();
    }
    
    @Override
    public void updateAddress(Address address) {
        em.merge(address);
        em.flush();
    }
    
    @Override
    public void createEmployee(Employee employee) {
        em.persist(employee);
        em.flush();
    }
    
    @Override
    public void createProduct(Product product) {
        em.persist(product);
        em.flush();
    }

}
