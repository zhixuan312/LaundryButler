package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class SharedBoxPermission implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long sharedBoxPermissionId;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Box box;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Customer customer;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Customer neighbour;
    private Integer status;

    public SharedBoxPermission() {
    }

    public Long getSharedBoxPermissionId() {
        return sharedBoxPermissionId;
    }

    public void setSharedBoxPermissionId(Long id) {
        this.sharedBoxPermissionId = id;
    }

    public Box getBox() {
        return box;
    }

    public void setBox(Box box) {
        this.box = box;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Customer getNeighbour() {
        return neighbour;
    }

    public void setNeighbour(Customer neighbour) {
        this.neighbour = neighbour;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (sharedBoxPermissionId != null ? sharedBoxPermissionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the sharedBoxPermissionId fields are not set
        if (!(object instanceof SharedBoxPermission)) {
            return false;
        }
        SharedBoxPermission other = (SharedBoxPermission) object;
        if ((this.sharedBoxPermissionId == null && other.sharedBoxPermissionId != null) || (this.sharedBoxPermissionId != null && !this.sharedBoxPermissionId.equals(other.sharedBoxPermissionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SharedBoxPermission[ id=" + sharedBoxPermissionId + " ]";
    }

}
