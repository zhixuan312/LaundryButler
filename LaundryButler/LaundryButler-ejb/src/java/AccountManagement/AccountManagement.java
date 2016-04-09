package AccountManagement;

import entity.Customer;
import entity.Address;
import entity.Card;
import entity.CartLineItem;
import entity.Employee;
import entity.Transaction;
import java.util.ArrayList;
import javax.ejb.Local;
import javax.ejb.Remote;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author HoRenSen
 */
@Stateless
@Local(AccountManagementLocal.class)
@Remote(AccountManagementRemote.class)
public class AccountManagement implements AccountManagementRemote, AccountManagementLocal {
    
    @EJB
    private AccountManagementLocal accountManagementLocal;
    @PersistenceContext
    private EntityManager em;
    
    private Boolean isLoggedIn = false;
    private long currentCustomerId = 0;
    private long currentEmployeeId = 0;
    private Customer customer;
    private Employee employee;
    
    @Override
    public Long register(Customer customer) {
        boolean sameEmail = false;
        List<Customer> list = new ArrayList<>();
        try{
            String jpql = "SELECT c FROM Customer c";
            Query query = em.createQuery(jpql);
            list = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).getEmail().equals(customer.getEmail())){
                sameEmail = true;
            }
        }
        if (!sameEmail) {
            long verificationCode = (long)(Math.random()* 9999);
            customer.setVerificationCode(verificationCode);
            em.persist(customer);
            em.flush();
            
            return verificationCode;
        } else {
            return new Long("-1");
        }
    }
    
    @Override
    public Boolean activate(long customerId, long verificationCode) {
        try {
            String jpql = "SELECT c FROM Customer c WHERE c.customerId = " + customerId + "AND c.verificationCode = " + verificationCode;
            Query query = em.createQuery(jpql);
            Customer currentCustomer = (Customer)query.getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean customerLogin(String email, String password)
    {
        customer = accountManagementLocal.retrieveCustomerByEmail(email);
        
        if(customer != null)
        {
            if(customer.getPassword().equals(password))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public Customer retrieveCustomerByEmail(String email) {
        try{
            String jpql = "SELECT c FROM Customer c WHERE c.email = " + "'" + email + "'";
            Query query = em.createQuery(jpql);
            return (Customer)query.getSingleResult();
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
        customer.setSelectedCartLineItems(new ArrayList<>());
        em.merge(customer);
        em.flush();
        customer = null;
        employee = null;
        return true;
    }
    
    @Override
    public Boolean updateCutomerProfile (Customer customer) {
        try{
            em.merge(customer);
            em.flush();
            this.customer = customer;
            return true;
        } catch (Exception e) {
            return false;
        }
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
        if (customers.isEmpty()) {
            return null;
        } else {
            return customers;
        }
    }
    
    @Override
    public Long createNewEmployee (Employee employee){
        boolean sameEmail = false;
        List<Employee> list = new ArrayList<>();
        try{
            String jpql = "SELECT e FROM Employee e";
            Query query = em.createQuery(jpql);
            list = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        for (int i = 0; i < list.size(); i ++) {
            if (list.get(i).getEmail().equals(employee.getEmail())){
                sameEmail = true;
            }
        }
        if (!sameEmail) {
            em.persist(employee);
            em.flush();
            return new Long("1");
        } else {
            return new Long("-1");
        }
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
    public Boolean employeeLogin(String email, String password)
    {
        employee = accountManagementLocal.retrieveEmployeeByEmail(email);
        
        if(employee != null)
        {
            if(employee.getPassword().equals(password))
            {
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return false;
        }
    }
    
    @Override
    public Employee retrieveEmployeeByEmail(String email) {
        try{
            String jpql = "SELECT e FROM Employee e WHERE e.email = " + "'" + email + "'";
            Query query = em.createQuery(jpql);
            return (Employee)query.getSingleResult();
        } catch(NoResultException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public Boolean updateEmployeeProfile (Employee employee) {
        try {
            em.merge(employee);
            em.flush();
            this.employee = employee;
            return true;
        } catch (Exception e) {
            return false;
        }
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
        if (employees.isEmpty()) {
            return null;
        } else {
            return employees;
        }
    }
    
    @Override
    public Boolean deleteEmployee (Long employeeId){
        try {
            Employee employee = em.find(Employee.class, employeeId);
            em.remove(employee);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean createAddress (Address address) {
        try {
            em.persist(address);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean updateAddress (Address address){
        try {
            em.merge(address);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public List<Address> viewAllAddressByCustomerId(Long customerId){
        List<Address> addresses = null;
        try{
            String jpql = "SELECT a FROM Address a WHERE a.customer.customerId = " + customerId;
            Query query = em.createQuery(jpql);
            addresses = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        if (addresses.isEmpty()){
            return null;
        } else {
            return addresses;
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
        if (addresses.isEmpty()){
            return null;
        } else {
            return addresses;
        }
    }
    
    @Override
    public Boolean deleteAddress (Long addressId){
        try {
            Address address = em.find(Address.class, addressId);
            em.remove(address);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean deleteAddresses (List<Address> addresses){
        for (Address address:addresses){
            deleteAddress(address.getAddressId());
        }
        return true;
    }
    
    @Override
    public Boolean createCard (Card card) {
        try {
            em.persist(card);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean updateCard (Card card){
        try {
            em.merge(card);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public List<Card> viewAllCardByCustomerId(Long customerId){
        List<Card> cards = null;
        try{
            String jpql = "SELECT c FROM Card c WHERE c.customer.customerId = " + customerId;
            Query query = em.createQuery(jpql);
            cards = query.getResultList();
        } catch(NoResultException ex) {
            ex.printStackTrace();
        }
        if (cards.isEmpty()) {
            return null;
        } else {
            return cards;
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
        if (cards.isEmpty()) {
            return null;
        } else {
            return cards;
        }
    }
    
    @Override
    public Boolean deleteCard (Long cardId){
        try {
            Card card = em.find(Card.class, cardId);
            em.remove(card);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean addCartLineItemToCart(CartLineItem cartLineItem){
        try {
            List<CartLineItem> cart = customer.getCartLineItems();
            cart.add(cartLineItem);
            customer.setCartLineItems(cart);
            em.merge(customer);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean removeCartLineItemFromCart (CartLineItem cartLineItem){
        List<CartLineItem> cart = customer.getCartLineItems();
        for (int i = 0; i < cart.size(); i ++){
            if (cart.get(i).getCartLineItemId().equals(cartLineItem.getCartLineItemId())){
                cart.remove(i);
                customer.setCartLineItems(cart);
                em.merge(customer);
                em.flush();
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Boolean addCartLineItemForCheckOut(CartLineItem cartLineItem){
        try {
            List<CartLineItem> cart = customer.getSelectedCartLineItems();
            cart.add(cartLineItem);
            customer.setSelectedCartLineItems(cart);
            em.merge(customer);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Boolean resetCartLineItemForCheckOut(){
        try {
            customer.setSelectedCartLineItems(new ArrayList<>());
            em.merge(customer);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    @Override
    public Customer getCustomer () {
        return customer;
    }
    
    @Override
    public Employee getEmployee(){
        return employee;
    }
    
}