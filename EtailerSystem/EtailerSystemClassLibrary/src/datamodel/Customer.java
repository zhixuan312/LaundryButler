package datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class Customer implements Serializable 
{

    private Integer customerId;
    private String firstName;    
    private String lastName;    
    private String email;    
    private String password;        
    private String addressLine1;    
    private String addressLine2;    
    private String addressLine3;    
    private String postalCode;    
    private String country;    
    private List<SalesTransaction> salesTransactions;

    
    
    public Customer() 
    {
        salesTransactions = new ArrayList<>();
    }
            

    
    public Customer(Integer customerId, String firstName, String lastName, String email, String password, String addressLine1, String addressLine2, String addressLine3, String postalCode, String country) 
    {
        this.customerId = customerId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.addressLine3 = addressLine3;
        this.postalCode = postalCode;
        this.country = country;
    }

    
    
    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
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

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine3() {
        return addressLine3;
    }

    public void setAddressLine3(String addressLine3) {
        this.addressLine3 = addressLine3;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public List<SalesTransaction> getSalesTransactions() {
        return salesTransactions;
    }

    public void setSalesTransactions(List<SalesTransaction> salesTransactions) {
        this.salesTransactions = salesTransactions;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (customerId != null ? customerId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
        return "datamodel.Customer[ customerId=" + customerId + " ]";
    }    
}