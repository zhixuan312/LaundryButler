package ejb.session.stateless;

import datamodel.ProductCategory;
import datamodel.ProductItem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.sql.DataSource;



@Stateless
@Local(ProductCatalogueSessionBeanLocal.class)
@Remote(ProductCatalogueSessionBeanRemote.class)

public class ProductCatalogueSessionBean implements ProductCatalogueSessionBeanLocal, ProductCatalogueSessionBeanRemote
{
    @Resource(name = "dataSourceEtailerSystem")
    private DataSource dataSource;
        
    
    
    @Override
    public Integer createProductCategory(ProductCategory productCategory)
    {
        try
        {
            String sql = "INSERT INTO `etailersystem`.`productcategory` (`name`) VALUES (?);";
            
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productCategory.getName());
            Integer result = preparedStatement.executeUpdate();
            
            if(result.equals(1))
            {
                sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'etailersystem' AND TABLE_NAME = 'productcategory';";
                preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                
                if(resultSet.next())
                {
                    return resultSet.getInt("AUTO_INCREMENT") - 1;
                }
                else
                {
                    return null;
                }
            }
            else
            {
                return null;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();            
            return null;
        }
    }
    
    
    
    public ArrayList<ProductCategory> retrieveAllProductCategories()
    {
        try
        {
            ArrayList<ProductCategory> productCategories = new ArrayList<>();            
            String sql = "SELECT * FROM `etailersystem`.`productcategory` ORDER BY name ASC;";
            
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next())
            {
                productCategories.add(new ProductCategory(resultSet.getInt("productCategoryId"), resultSet.getString("name")));
            }
            
            return productCategories;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();            
            return null;
        }
    }
    
    
    
    @Override
    public String createProductItem(ProductItem productItem)
    {
        try
        {
            String sql = "INSERT INTO `etailersystem`.`productitem` (`skuCode`, `name`, `description`, `productCategoryId`, `quantityOnHand`, `price`) VALUES (?,?,?,?,?,?);";
            
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, productItem.getSkuCode());
            preparedStatement.setString(2, productItem.getName());
            preparedStatement.setString(3, productItem.getDescription());
            preparedStatement.setInt(4, productItem.getProductCategory().getProductCategoryId());
            preparedStatement.setInt(5, productItem.getQuantityOnHand());
            preparedStatement.setBigDecimal(6, productItem.getPrice());
            Integer result = preparedStatement.executeUpdate();
            
            if(result.equals(1))
            {
                return productItem.getSkuCode();
            }
            else
            {
                return null;
            }
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();            
            return null;
        }
    }
    
    
    
    @Override
    public ArrayList<ProductItem> retrieveAllProductItems()
    {
        try
        {
            ArrayList<ProductItem> productItems = new ArrayList<>();            
            String sql = "SELECT `etailersystem`.`productitem`.*, `etailersystem`.`productcategory`.name AS productCategoryName FROM `etailersystem`.`productitem` INNER JOIN `etailersystem`.`productcategory` on `etailersystem`.`productitem`.productCategoryId = `etailersystem`.`productcategory`.productCategoryId ORDER BY `etailersystem`.`productitem`.name ASC;";
            
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);            
            ResultSet resultSet = preparedStatement.executeQuery();
            
            while(resultSet.next())
            {
                productItems.add(new ProductItem(resultSet.getString("skuCode"), resultSet.getString("name"), resultSet.getString("description"), resultSet.getInt("quantityOnHand"), resultSet.getBigDecimal("price"), new ProductCategory(resultSet.getInt("productCategoryId"), resultSet.getString("productCategoryName"))));
            }
            
            return productItems;
        }
        catch(SQLException ex)
        {
            ex.printStackTrace();            
            return null;
        }
    }
}