package entity;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;
import javax.persistence.Column;

@Entity
public class ProductCategoryEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer productCategoryId;
    @Column(unique = true)
    private String name;
    @OneToMany(mappedBy = "productCategoryEntity")
    private List<ProductItemEntity> productItemEntities;

    public ProductCategoryEntity() {
    }

    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductItemEntity> getProductItemEntities() {
        return productItemEntities;
    }

    public void setProductItemEntities(List<ProductItemEntity> productItemEntities) {
        this.productItemEntities = productItemEntities;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public ProductCategoryEntity(Integer productCategoryId, String name, List<ProductItemEntity> productItemEntities) {
        this.productCategoryId = productCategoryId;
        this.name = name;
        this.productItemEntities = productItemEntities;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (productCategoryId != null ? productCategoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ProductCategoryEntity)) {
            return false;
        }
        ProductCategoryEntity other = (ProductCategoryEntity) object;
        if ((this.productCategoryId == null && other.productCategoryId != null) || (this.productCategoryId != null && !this.productCategoryId.equals(other.productCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.ProductCategoryEntity[ id=" + productCategoryId + " ]";
    }

}
