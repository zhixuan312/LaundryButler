package entity.key;

import java.io.Serializable;

public class SalesTransactionLineItemEntityKey implements Serializable {

    private Integer salesTransactionId;
    private Integer lineItemNumber;

    public SalesTransactionLineItemEntityKey() {
    }

    public SalesTransactionLineItemEntityKey(Integer salesTransactionId, Integer lineItemNumber) {
        this();

        this.salesTransactionId = salesTransactionId;
        this.lineItemNumber = lineItemNumber;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (salesTransactionId != null ? salesTransactionId.hashCode() : 0);
        hash += (lineItemNumber != null ? lineItemNumber.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof SalesTransactionLineItemEntityKey)) {
            return false;
        }
        SalesTransactionLineItemEntityKey other = (SalesTransactionLineItemEntityKey) object;
        if ((this.salesTransactionId == null && other.salesTransactionId != null) || (this.salesTransactionId != null && !this.salesTransactionId.equals(other.salesTransactionId))
                || (this.lineItemNumber == null && other.lineItemNumber != null) || (this.lineItemNumber != null && !this.lineItemNumber.equals(other.lineItemNumber))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SalesTransactionLineItemEntity[ salesTransactionId=" + salesTransactionId + ", lineItemNumber=" + lineItemNumber + " ]";
    }
}
