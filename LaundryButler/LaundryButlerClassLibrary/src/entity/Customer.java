package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Customer implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String mobile;
    private String gender;
    private Boolean isFaceBook;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfBirth;
    private String accountStatus;
    private String verificationCode;
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateRegistered;
    private Integer dryCleaning; 
    private Integer express;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<Transaction> transactions;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<Address> addresses;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<CartLineItem> cartLineItems;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<Box> boxes;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "payer")
    private List<Box> payingBoxes;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "customer")
    private List<SharedBoxPermission> customerShareOuts;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "neighbour")
    private List<SharedBoxPermission> neighbourShareIns;

    public Customer() {
        this.transactions = new ArrayList<>();
        this.addresses = new ArrayList<>();
        this.cartLineItems = new ArrayList<>();
        this.express = new Integer (0);
        this.dryCleaning = new Integer (0);
        this.boxes = new ArrayList<>();
        this.payingBoxes = new ArrayList<>();
        this.customerShareOuts = new ArrayList<>();
        this.neighbourShareIns = new ArrayList<>();
        this.isFaceBook = true; 
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getDateRegistered() {
        return dateRegistered;
    }

    public void setDateRegistered(Date dateRegistered) {
        this.dateRegistered = dateRegistered;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public List<CartLineItem> getCartLineItems() {
        return cartLineItems;
    }

    public void setCartLineItems(List<CartLineItem> cartLineItems) {
        this.cartLineItems = cartLineItems;
    }

    public List<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<Box> boxes) {
        this.boxes = boxes;
    }

    public List<Box> getPayingBoxes() {
        return payingBoxes;
    }

    public void setPayingBoxes(List<Box> payingBoxes) {
        this.payingBoxes = payingBoxes;
    }

    public List<SharedBoxPermission> getCustomerShareOuts() {
        return customerShareOuts;
    }

    public void setCustomerShareOuts(List<SharedBoxPermission> customerShareOuts) {
        this.customerShareOuts = customerShareOuts;
    }

    public List<SharedBoxPermission> getNeighbourShareIns() {
        return neighbourShareIns;
    }

    public void setNeighbourShareIns(List<SharedBoxPermission> neighbourShareIns) {
        this.neighbourShareIns = neighbourShareIns;
    }

    public Integer getDryCleaning() {
        return dryCleaning;
    }

    public void setDryCleaning(Integer dryCleaning) {
        this.dryCleaning = dryCleaning;
    }

    public Boolean getIsFaceBook() {
        return isFaceBook;
    }

    public void setIsFaceBook(Boolean isFaceBook) {
        this.isFaceBook = isFaceBook;
    }

    public Integer getExpress() {
        return express;
    }

    public void setExpress(Integer express) {
        this.express = express;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the customerId fields are not set
        if (!(object instanceof Customer)) {
            return false;
        }
        Customer other = (Customer) object;
        if ((this.customerId == null && other.customerId != null) || (this.customerId != null && !this.customerId.equals(other.customerId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Customer[ id=" + customerId + " ]";
    }

}
