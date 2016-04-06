/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import AccountManagement.AccountManagementRemote;
import ProductManagement.ProductManagementRemote;
import entity.Employee;
import entity.Product;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@Named(value = "pricingManagedBean")
@RequestScoped

public class PricingManagedBean {
    
    @EJB
    private ProductManagementRemote productManagmentRemote;
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private Employee employee;
    private Product product;
    private List<Product> products;
    
    public PricingManagedBean() {
        product = new Product();
        products = new ArrayList<>();
    }
    
    @PostConstruct
    public void init()
    {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
        
        try
        {
            if(ec.getSessionMap().get("login") == null)
            {
                ec.redirect("login.xhtml?faces-redirect=true");
            }
            else
            {
                if(ec.getSessionMap().get("login").equals(false))
                {
                    ec.redirect("login.xhtml?faces-redirect=true");
                }
            }
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
        employee = accountManagementRemote.getEmployee();
        products = productManagmentRemote.viewAllProduct();
    }
    
    public void createProduct(ActionEvent event){
        if (employee.getIsAdmin()){
            long productId = productManagmentRemote.createProduct(product);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "New product " + productId + "created successfully!","New product " + productId + "created successfully!"));
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please login with admin","Please login with admin"));
        }
    }
    
    public void cancelCreateProduct(ActionEvent event){
        try
        {
            ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
            
            ec.redirect("pricing.xhtml?faces-redirect=true");
            
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
    
    public void updateProduct (ActionEvent event) {
        if (employee.getIsAdmin()){
            if(productManagmentRemote.updateProduct(product)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please login with admin","Please login with admin"));
            }
        }
    }
    
    
    public void deleteProduct (ActionEvent event) {
        if (employee.getIsAdmin()){
            Product productToDelete = (Product)event.getComponent().getAttributes().get("productToDelete");
            if(productManagmentRemote.deleteProduct(productToDelete.getProductId())){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Fail to update","Fail to update"));
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please login with admin","Please login with admin"));
        }
    }
    
    public Product getProduct() {
        return product;
    }
    
    public void setProduct(Product product) {
        this.product = product;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    
}
