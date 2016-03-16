package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import javax.persistence.ManyToOne;

@Entity
public class ProductItemEntity implements Serializable {

    @Id
    private String skuCode;
    private String name;
    private String description;
    private Integer quantityOnHand;
    private BigDecimal price;
    @ManyToOne
    private ProductCategoryEntity productCategoryEntity;

    public ProductItemEntity() {
    }

    public String getId() {
        return skuCode;
    }

    public void setId(String id) {
        this.skuCode = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getQuantityOnHand() {
        return quantityOnHand;
    }

    public void setQuantityOnHand(Integer quantityOnHand) {
        this.quantityOnHand = quantityOnHand;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public ProductCategoryEntity getProductCategoryEntity() {
        return productCategoryEntity;
    }

    public void setProductCategoryEntity(ProductCategoryEntity productCategoryEntity) {
        this.productCategoryEntity = productCategoryEntity;
    }

    public ProductItemEntity(String skuCode, String name, String description, Integer quantityOnHand, BigDecimal price, ProductCategoryEntity productCategoryEntities) {
        this.skuCode = skuCode;
        this.name = name;
        this.description = description;
        this.quantityOnHand = quantityOnHand;
        this.price = price;
        this.productCategoryEntity = productCategoryEntities;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (skuCode != null ? skuCode.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductItemEntity)) {
            return false;
        }
        ProductItemEntity other = (ProductItemEntity) object;
        if ((this.skuCode == null && other.skuCode != null) || (this.skuCode != null && !this.skuCode.equals(other.skuCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductItemEntity[ id=" + skuCode + " ]";
    }

}
