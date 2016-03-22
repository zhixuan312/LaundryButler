package AccountManagement;

import entity.Customer;
import entity.Address;
import entity.Card;
import entity.Employee;
import entity.Transaction;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateful;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HoRenSen
 */
@Stateful
@Local(AccountManagementLocal.class)
@Remote(AccountManagementRemote.class)
public class AccountManagement implements AccountManagementRemote, AccountManagementLocal {
    
    @PersistenceContext
    private EntityManager em;
    
    private Boolean isLoggedIn = false;
    private long currentCustomerId = 0;
    private Customer customer;
    
    @Override
    public Boolean register(Customer customer) {
        
        em.persist(customer);
        em.flush();
        
        return true;
    }
    
    @Override
    public Boolean activate(String verificationCode) {
        
        if(customer.getVerificationCode().equals(verificationCode)){
            customer.setVerificationCode(verificationCode);
            em.merge(customer);
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public Boolean login(String email, String password) {
        try{
            String jpql = "SELECT c FROM Customer c WHERE c.email = " + email + "AND c.password = " + password;
            Query query = em.createQuery(jpql);
            query.setParameter(1, email);
            Customer currentCustomer = (Customer)query.getSingleResult();
            customer = currentCustomer;
            isLoggedIn = true;
            currentCustomerId = currentCustomer.getCustomerId();
            return true;
        } catch(NoResultException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public Customer viewAccount() {
        return customer;
    }
    
    @Override
    public Transaction viewTransactionHistory() {
        return null;
    }
    
    @Override
    public Boolean logout() {
        customer = null;
        return true;
    }
    
    @Override
    public Boolean updateCutomerProfile (Customer customer) {
        em.merge(customer);
        em.flush();
        return true;
    }
    
    @Override
    public List<Customer> viewAllRecordedCustomer (){
        List<Customer> customers = null;
        try{
            String jpql = "SELECT c FROM Customer c";
            Query query = em.createQuery(jpql);
            customers = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return customers;
    }
    
    @Override
    public Boolean createNewEmployee (Employee employee){
        em.persist(employee);
        em.flush();
        
        return true;
    }
    
    @Override
    public Boolean changeIsAdminStatus (Long employeeId){
        try {
            Employee employee = em.find(Employee.class, employeeId);
            employee.setIsAdmin(true);
            em.merge(employee);
            em.flush();
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    @Override
    public Boolean updateEmployeeProfile (Employee employee) {
        em.merge(employee);
        em.flush();
        return true;
    }
    
    @Override
    public List<Employee> viewAllRecordedEmployee (){
        List<Employee> employees = null;
        try{
            String jpql = "SELECT e FROM Employee e";
            Query query = em.createQuery(jpql);
            employees = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return employees;
    }
    
    @Override
    public Boolean deleteEmployee (Long employeeId){
        Employee employee = em.find(Employee.class, employeeId);
        em.remove(employee);
        return true;
    }
    
    @Override
    public Boolean createAddress (Address address) {
        em.persist(address);
        em.flush();
        
        return true;
    }
    
    @Override
    public Boolean updateAddress (Address address){
        em.merge(address);
        em.flush();
        return true;
    }
    
    @Override
    public List<Address> viewAllAddressByCustomer(Long customerId){
        try{
            String jpql = "SELECT a FROM Address a WHERE a.customer.customerId = " + customerId;
            Query query = em.createQuery(jpql);
            List<Address> addresses = query.getResultList();
            return addresses;
        } catch(NoResultException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<Address> viewAllRecordedAddress (){
        List<Address> addresses = null;
        try{
            String jpql = "SELECT a FROM Address a";
            Query query = em.createQuery(jpql);
            addresses = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return addresses;
    }
    
    @Override
    public Boolean deleteAddress (Long addressId){
        Address address = em.find(Address.class, addressId);
        em.remove(address);
        return true;
    }
    
    @Override
    public Boolean createCard (Card card) {
        em.persist(card);
        em.flush();
        
        return true;
    }
    
    @Override
    public Boolean updateCard (Card card){
        em.merge(card);
        em.flush();
        return true;
    }
    
    @Override
    public List<Card> viewAllCardByCustomer(Long customerId){
        try{
            String jpql = "SELECT c FROM Card c WHERE c.customer.customerId = " + customerId;
            Query query = em.createQuery(jpql);
            List<Card> card = query.getResultList();
            return card;
        } catch(NoResultException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public List<Card> viewAllRecordedCard (){
        List<Card> cards = null;
        try{
            String jpql = "SELECT c FROM Card c";
            Query query = em.createQuery(jpql);
            cards = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        return cards;
    }
    
    @Override
    public Boolean deleteCard (Long cardId){
        Card card = em.find(Card.class, cardId);
        em.remove(card);
        return true;
    }
}