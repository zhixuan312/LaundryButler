package entity;

import entity.key.SalesTransactionLineItemEntityKey;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;

@Entity
@IdClass(SalesTransactionLineItemEntityKey.class)
public class SalesTransactionLineItemEntity implements Serializable {

    @Id
    private Integer salesTransactionId;
    @Id
    private Integer lineItemNumber;
    private Integer quantity;
    private BigDecimal unitPrice;
    private BigDecimal subTotal;
    @ManyToOne
    private ProductItemEntity productItemEntity;

    public SalesTransactionLineItemEntity() {
    }

    public SalesTransactionLineItemEntity(Integer salesTransactionId, Integer lineItemNumber, Integer quantity, BigDecimal unitPrice, BigDecimal subTotal, ProductItemEntity productItemEntity) {
        this.salesTransactionId = salesTransactionId;
        this.lineItemNumber = lineItemNumber;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.subTotal = subTotal;
        this.productItemEntity = productItemEntity;
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

    public ProductItemEntity getProductItemEntity() {
        return productItemEntity;
    }

    public void setProductItemEntity(ProductItemEntity productItemEntity) {
        this.productItemEntity = productItemEntity;
    }

    public Integer getSalesTransactionId() {
        return salesTransactionId;
    }

    public void setSalesTransactionId(Integer salesTransactionId) {
        this.salesTransactionId = salesTransactionId;
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
        if (!(object instanceof SalesTransactionLineItemEntity)) {
            return false;
        }
        SalesTransactionLineItemEntity other = (SalesTransactionLineItemEntity) object;
        if ((this.salesTransactionId == null && other.salesTransactionId != null) || (this.salesTransactionId != null && !this.salesTransactionId.equals(other.salesTransactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SalesTransactionLineItemEntity[ id=" + salesTransactionId + " ]";
    }

}
