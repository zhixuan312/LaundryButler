package ProductManagement;

import entity.CartLineItem;
import entity.Product;
import java.util.List;
import javax.ejb.Local;

@Local
public interface ProductManagementLocal {

    public Long createProduct(Product product);

    public Boolean updateProduct(Product product);

    public List<Product> viewAllProduct();

    public Boolean deleteProduct(Long productId);

    public Boolean deleteProducts(List<Product> products);

    public Long createCartLineItem(CartLineItem cartLineItem);

    public Boolean updateCartLineItem(CartLineItem cartLineItem);

    public List<CartLineItem> viewAllCartLineItemByCustomerId(Long customerId);

    public Boolean deleteCartLineItem(Long cartLineItemId);

    public CartLineItem getCartLineItem();

    public void setCartLineItem(CartLineItem cartLineItem);

}
