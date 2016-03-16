package datamodel;

import java.io.Serializable;
import java.math.BigDecimal;



public class ProductItem implements Serializable
{
    private String skuCode; 
    private String name;    
    private String description;    
    private Integer quantityOnHand;    
    private BigDecimal price;    
    private ProductCategory productCategory;    

    
    
    public ProductItem()
    {
        productCategory = new ProductCategory();
    }
    
    
    
    public ProductItem(String skuCode)
    {
        this.skuCode = skuCode;        
    }

    

    public ProductItem(String skuCode, String name, String description, Integer quantityOnHand, BigDecimal price, ProductCategory productCategory)
    {
        this.skuCode = skuCode;
        this.name = name;
        this.description = description;
        this.quantityOnHand = quantityOnHand;
        this.price = price;
        this.productCategory = productCategory;
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

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
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
        if (!(object instanceof ProductItem)) {
            return false;
        }
        ProductItem other = (ProductItem) object;
        if ((this.skuCode == null && other.skuCode != null) || (this.skuCode != null && !this.skuCode.equals(other.skuCode))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "datamodel.ProductItem[ skuCode=" + skuCode + " ]";
    }    
}