package datamodel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class ProductCategory implements Serializable
{
    private Integer productCategoryId;
    private String name;    
    private List<ProductItem> productItems;

    
    
    public ProductCategory() 
    {
        productItems = new ArrayList<>();
    }

    

    public ProductCategory(Integer productCategoryId, String name)
    {
        this.productCategoryId = productCategoryId;
        this.name = name;
    }

    
    
    public Integer getProductCategoryId() {
        return productCategoryId;
    }

    public void setProductCategoryId(Integer productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<ProductItem> getProductItems() {
        return productItems;
    }

    public void setProductItems(List<ProductItem> productItems) {
        this.productItems = productItems;
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
        if (!(object instanceof ProductCategory)) {
            return false;
        }
        ProductCategory other = (ProductCategory) object;
        if ((this.productCategoryId == null && other.productCategoryId != null) || (this.productCategoryId != null && !this.productCategoryId.equals(other.productCategoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datamodel.ProductCategory[ productCategoryId=" + productCategoryId + " ]";
    }    
}