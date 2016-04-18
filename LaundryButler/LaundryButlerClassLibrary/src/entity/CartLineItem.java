package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class CartLineItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartLineItemId;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Customer customer;
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Product product;
    private Integer quantity;

    public CartLineItem() {
    }

    public Long getCartLineItemId() {
        return cartLineItemId;
    }

    public void setCartLineItemId(Long cartLineItemId) {
        this.cartLineItemId = cartLineItemId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartLineItemId != null ? cartLineItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the cartLineItemId fields are not set
        if (!(object instanceof CartLineItem)) {
            return false;
        }
        CartLineItem other = (CartLineItem) object;
        if ((this.cartLineItemId == null && other.cartLineItemId != null) || (this.cartLineItemId != null && !this.cartLineItemId.equals(other.cartLineItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.CartLineItem[ id=" + cartLineItemId + " ]";
    }

}
