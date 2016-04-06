/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductManagement;

import entity.Product;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author XUAN
 */
@Local
public interface ProductManagementLocal {
    
    public Long createProduct (Product product);
    
    public Boolean updateProduct (Product product);
    
    public List<Product> viewAllProduct ();
    
    public Boolean deleteProduct (Long productId);
    
    public Boolean deleteProducts (List<Product> products);
}
