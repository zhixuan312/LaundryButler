package ejb.session.stateless;

import datamodel.ProductCategory;
import datamodel.ProductItem;
import java.util.ArrayList;



public interface ProductCatalogueSessionBeanLocal
{
    public Integer createProductCategory(ProductCategory productCategory);
    
    public ArrayList<ProductCategory> retrieveAllProductCategories();
    
    public String createProductItem(ProductItem productItem);

    public ArrayList<ProductItem> retrieveAllProductItems();
}