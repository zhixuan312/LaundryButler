package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class SalesTransactionEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer salesTransactionId;
    private Integer totalLineItem;
    private Integer totalQuantity;
    private BigDecimal totalAmount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDateTime;
    @OneToMany
    private List<SalesTransactionLineItemEntity> salesTransactionLineItemEntities;
    @ManyToOne
    private CustomerEntity customerEntity;

    public SalesTransactionEntity() {
    }

    public SalesTransactionEntity(Integer salesTransactionId, Integer totalLineItem, Integer totalQuantity, BigDecimal totalAmount, Date transactionDateTime, List<SalesTransactionLineItemEntity> salesTransactionLineItemEntities, CustomerEntity customerEntity) {
        this.salesTransactionId = salesTransactionId;
        this.totalLineItem = totalLineItem;
        this.totalQuantity = totalQuantity;
        this.totalAmount = totalAmount;
        this.transactionDateTime = transactionDateTime;
        this.salesTransactionLineItemEntities = salesTransactionLineItemEntities;
        this.customerEntity = customerEntity;
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
        if (!(object instanceof SalesTransactionEntity)) {
            return false;
        }
        SalesTransactionEntity other = (SalesTransactionEntity) object;
        if ((this.salesTransactionId == null && other.salesTransactionId != null) || (this.salesTransactionId != null && !this.salesTransactionId.equals(other.salesTransactionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SalesTransactionEntity[ id=" + salesTransactionId + " ]";
    }

}
