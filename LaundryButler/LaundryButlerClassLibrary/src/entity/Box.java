package entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Box implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long boxId;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Customer customer;
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Customer payer;
    private String status;
    private Double price;
    private Boolean hasReqeustedDryCleaning;
    private Boolean hasRequestedExpressService;
    @Temporal(TemporalType.TIMESTAMP)
    private Date pickupDateTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date deliveryDateTime;
    private String boxPasscode;
    private Boolean allowSharing;
    private Boolean isShared;
    private Address address;
    @ManyToOne(fetch = FetchType.EAGER, optional = true)
    private Employee employee;
    private byte rating;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDateTime;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "box")
    private List<SharedBoxPermission> sharedBoxPermissions;

    public Box() {
    }

    public Long getBoxId() {
        return boxId;
    }

    public void setBoxId(Long boxId) {
        this.boxId = boxId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getPayer() {
        return payer;
    }

    public void setPayer(Customer payer) {
        this.payer = payer;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getPickupDateTime() {
        return pickupDateTime;
    }

    public void setPickupDateTime(Date pickupDateTime) {
        this.pickupDateTime = pickupDateTime;
    }

    public Date getDeliveryDateTime() {
        return deliveryDateTime;
    }

    public void setDeliveryDateTime(Date deliveryDateTime) {
        this.deliveryDateTime = deliveryDateTime;
    }

    public String getBoxPasscode() {
        return boxPasscode;
    }

    public void setBoxPasscode(String boxPasscode) {
        this.boxPasscode = boxPasscode;
    }

    public Boolean getAllowSharing() {
        return allowSharing;
    }

    public void setAllowSharing(Boolean allowSharing) {
        this.allowSharing = allowSharing;
    }

    public Boolean getIsShared() {
        return isShared;
    }

    public void setIsShared(Boolean isShared) {
        this.isShared = isShared;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public byte getRating() {
        return rating;
    }

    public void setRating(byte rating) {
        this.rating = rating;
    }

    public Date getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(Date createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public List<SharedBoxPermission> getSharedBoxPermissions() {
        return sharedBoxPermissions;
    }

    public void setSharedBoxPermissions(List<SharedBoxPermission> sharedBoxPermissions) {
        this.sharedBoxPermissions = sharedBoxPermissions;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Boolean getHasReqeustedDryCleaning() {
        return hasReqeustedDryCleaning;
    }

    public void setHasReqeustedDryCleaning(Boolean hasReqeustedDryCleaning) {
        this.hasReqeustedDryCleaning = hasReqeustedDryCleaning;
    }

    public Boolean getHasRequestedExpressService() {
        return hasRequestedExpressService;
    }

    public void setHasRequestedExpressService(Boolean hasRequestedExpressService) {
        this.hasRequestedExpressService = hasRequestedExpressService;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (boxId != null ? boxId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the boxId fields are not set
        if (!(object instanceof Box)) {
            return false;
        }
        Box other = (Box) object;
        if ((this.boxId == null && other.boxId != null) || (this.boxId != null && !this.boxId.equals(other.boxId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Box[ id=" + boxId + " ]";
    }

}
