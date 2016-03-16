package ejb.session.stateless;

import datamodel.Customer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.annotation.Resource;
import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.sql.DataSource;



@Stateless
@Local(CustomerSessionBeanLocal.class)
@Remote(CustomerSessionBeanRemote.class)

public class CustomerSessionBean implements CustomerSessionBeanLocal, CustomerSessionBeanRemote
{
    @Resource(name = "dataSourceEtailerSystem")
    private DataSource dataSource;
    
    
    
    @Override
    public Integer createCustomer(Customer customer)
    {
        try
        {
            String sql = "INSERT INTO `etailersystem`.`customer` (`firstName`,`lastName`,`email`,`password`,`addressLine1`,`addressLine2`,`addressLine3`,`postalCode`,`country`) VALUES (?,?,?,?,?,?,?,?,?);";
            
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, customer.getFirstName());
            preparedStatement.setString(2, customer.getLastName());
            preparedStatement.setString(3, customer.getEmail());
            preparedStatement.setString(4, customer.getPassword());
            preparedStatement.setString(5, customer.getAddressLine1());
            preparedStatement.setString(6, customer.getAddressLine2());
            preparedStatement.setString(7, customer.getAddressLine3());
            preparedStatement.setString(8, customer.getPostalCode());
            preparedStatement.setString(9, customer.getCountry());
            Integer result = preparedStatement.executeUpdate();
            
            if(result.equals(1))
            {
                sql = "SELECT `AUTO_INCREMENT` FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'etailersystem' AND TABLE_NAME = 'customer';";
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
    
    
    
    @Override
    public Customer retrieveCustomerByEmail(String email)
    {
        try
        {            
            String sql = "SELECT * FROM `etailersystem`.`customer` WHERE email = ?;";
            
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            
            if(resultSet.next())
            {
                return new Customer(resultSet.getInt("customerId"), resultSet.getString("firstName"), resultSet.getString("lastName"), resultSet.getString("email"), resultSet.getString("password"), resultSet.getString("addressLine1"), resultSet.getString("addressLine2"), resultSet.getString("addressLine3"), resultSet.getString("postalCode"), resultSet.getString("country"));
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
    public Integer updateCustomer(Customer c)
    {
        int id = c.getCustomerId();
        String firstName = c.getFirstName();
        String lastName = c.getLastName();
        String email = c.getEmail();
        String password = c.getPassword();
        String addressLine1 = c.getAddressLine1();
        String addressLine2 = c.getAddressLine2();
        String addressLine3 = c.getAddressLine3();
        String postalCode = c.getPostalCode();
        String country = c.getCountry();
        String sql = "UPDATE customer "
                + "SET firstName = '" + firstName + "'"
                + ", lastName = '" + lastName + "'"
                + ", email = '" + email + "'"
                + ", password = '" + password + "'"
                + ", addressLine1 = '" + addressLine1 + "'"
                + ", addressLine2 = '" + addressLine2 + "'"
                + ", addressLine3 = '" + addressLine3 + "'"
                + ", postalCode = '" + postalCode + "'"
                + ", country = '" + country + "' "
                + "WHERE customerId = " + id;
        
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            return id;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    @Override
    public Integer deleteCustomer(Customer c) {
        int id = c.getCustomerId();
        String sql = "DELETE FROM customer WHERE customerId = " + c.getCustomerId();
        
        try {
            Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            return id;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}