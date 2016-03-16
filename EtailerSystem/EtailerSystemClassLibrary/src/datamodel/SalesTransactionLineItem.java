package datamodel;

import java.io.Serializable;
import java.math.BigDecimal;



public class SalesTransactionLineItem implements Serializable 
{
    private Integer salesTransactionId;
    private Integer lineItemNumber;    
    private Integer quantity;    
    private BigDecimal unitPrice;    
    private BigDecimal subTotal;    
    private ProductItem productItem;

    
    
    public SalesTransactionLineItem() 
    {
    }
    
    
    
    public SalesTransactionLineItem(Integer quantity, ProductItem productItem)
    {        
        this.quantity = quantity;
        this.productItem = productItem;
    }

    

    public SalesTransactionLineItem(Integer salesTransactionId, Integer lineItemNumber, Integer quantity, BigDecimal unitPrice, BigDecimal subTotal, ProductItem productItem)
    {
        this.salesTransactionId = salesTransactionId;
        this.lineItemNumber = lineItemNumber;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
    }
    

    
    public Integer getSalesTransactionId() {
        return salesTransactionId;
    }

    public void setSalesTransactionId(Integer salesTransactionId) {
        this.salesTransactionId = salesTransactionId;
    }

    public Integer getLineItemNumber() {
        return lineItemNumber;
    }

    public void setLineItemNumber(Integer lineItemNumber) {
        this.lineItemNumber = lineItemNumber;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }   

    public ProductItem getProductItem() {
        return productItem;
    }

    public void setProductItem(ProductItem productItem) {
        this.productItem = productItem;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) salesTransactionId;
        hash += (int) lineItemNumber;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SalesTransactionLineItem)) {
            return false;
        }
        SalesTransactionLineItem other = (SalesTransactionLineItem) object;
        if ((this.salesTransactionId == null && other.salesTransactionId != null) || (this.salesTransactionId != null && !this.salesTransactionId.equals(other.salesTransactionId))) {
            return false;
        }
        if ((this.lineItemNumber == null && other.lineItemNumber != null) || (this.lineItemNumber != null && !this.lineItemNumber.equals(other.lineItemNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datamodel.SalesTransactionLineItem[ salesTransactionId=" + salesTransactionId + "," + " ]";
    }   
}