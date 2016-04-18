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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;

@Named(value = "productManagedBean")
@SessionScoped

public class ProductManagedBean implements Serializable{
    
    @EJB
    private ProductManagementRemote productManagmentRemote;
    @EJB
    private AccountManagementRemote accountManagementRemote;
    
    private Employee admin;
    private Product product;
    private List<Product> productsTotal;
    private List<Product> products1;
    private List<Product> products2;
    
    public ProductManagedBean() {
        product = new Product();
        products1 = new ArrayList<>();
        products2 = new ArrayList<>();
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
        admin = accountManagementRemote.getEmployee();
        productsTotal = productManagmentRemote.viewAllProduct();
        for (int i= 0; i < 6; i ++) {
            if (i < 3){
                products1.add(productsTotal.get(i));
            } else {
                products2.add(productsTotal.get(i));
            }
        }
    }
    
    public void createProduct(ActionEvent event){
        if (admin.getIsAdmin()){
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
        if (admin.getIsAdmin()){
            if(productManagmentRemote.updateProduct(product)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!","Success!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Please login with admin","Please login with admin"));
            }
        }
    }
    
    
    public void deleteProduct (ActionEvent event) {
        if (admin.getIsAdmin()){
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
    
    public Employee getAdmin() {
        return admin;
    }
    
    public void setAdmin(Employee admin) {
        this.admin = admin;
    }
    
    public List<Product> getProducts1() {
        return products1;
    }
    
    public void setProducts1(List<Product> products1) {
        this.products1 = products1;
    }
    
    public List<Product> getProducts2() {
        return products2;
    }
    
    public void setProducts2(List<Product> products2) {
        this.products2 = products2;
    }
    
    public Employee getEmployee() {
        return admin;
    }
    
    public void setEmployee(Employee employee) {
        this.admin = employee;
    }
    
}
