package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class TransactionLineItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transactionLineItemId;
    private Integer quantity;
    private Double unitCharge;
    private Double totalCharge;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Product product;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Transaction transaction;

    public TransactionLineItem() {
    }

    public Long getTransactionLineItemId() {
        return transactionLineItemId;
    }

    public void setTransactionLineItemId(Long transactionLineItemId) {
        this.transactionLineItemId = transactionLineItemId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }

    public Double getUnitCharge() {
        return unitCharge;
    }

    public void setUnitCharge(Double unitCharge) {
        this.unitCharge = unitCharge;
    }

    public Double getTotalCharge() {
        return totalCharge;
    }

    public void setTotalCharge(Double totalCharge) {
        this.totalCharge = totalCharge;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (transactionLineItemId != null ? transactionLineItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the transactionLineItemId fields are not set
        if (!(object instanceof TransactionLineItem)) {
            return false;
        }
        TransactionLineItem other = (TransactionLineItem) object;
        if ((this.transactionLineItemId == null && other.transactionLineItemId != null) || (this.transactionLineItemId != null && !this.transactionLineItemId.equals(other.transactionLineItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.TransactionLineItem[ id=" + transactionLineItemId + " ]";
    }

}
