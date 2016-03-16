package datamodel;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



public class SalesTransaction implements Serializable
{
    private Integer salesTransactionId; 
    private Integer totalLineItem;    
    private Integer totalQuantity;    
    private BigDecimal totalAmount;    
    private Date transactionDateTime;    
    private List<SalesTransactionLineItem> salesTransactionLineItems;    
    private Customer customer;

    
    
    public SalesTransaction()
    {
        salesTransactionLineItems = new ArrayList<>();
    }

            

    public SalesTransaction(Integer salesTransactionId, Integer totalLineItem, Integer totalQuantity, BigDecimal totalAmount, Date transactionDateTime, Customer customer) 
    {
        this.salesTransactionId = salesTransactionId;
        this.totalLineItem = totalLineItem;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.transactionDateTime = transactionDateTime;
        this.customer = customer;
    }

    
    
    public Integer getSalesTransactionId() {
        return salesTransactionId;
    }

    public void setSalesTransactionId(Integer salesTransactionId) {
        this.salesTransactionId = salesTransactionId;
    }

    public Integer getTotalLineItem() {
        return totalLineItem;
    }

    public void setTotalLineItem(Integer totalLineItem) {
        this.totalLineItem = totalLineItem;
    }

    public Integer getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(Integer totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Date getTransactionDateTime() {
        return transactionDateTime;
    }

    public void setTransactionDateTime(Date transactionDateTime) {
        this.transactionDateTime = transactionDateTime;
    }

    public List<SalesTransactionLineItem> getSalesTransactionLineItems() {
        return salesTransactionLineItems;
    }

    public void setSalesTransactionLineItems(List<SalesTransactionLineItem> salesTransactionLineItems) {
        this.salesTransactionLineItems = salesTransactionLineItems;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (salesTransactionId != null ? salesTransactionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalesTransaction)) {
            return false;
        }
        SalesTransaction other = (SalesTransaction) object;
        if ((this.salesTransactionId == null && other.salesTransactionId != null) || (this.salesTransactionId != null && !this.salesTransactionId.equals(other.salesTransactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datamodel.SalesTransaction[ salesTransactionId=" + salesTransactionId + " ]";
    }    
}