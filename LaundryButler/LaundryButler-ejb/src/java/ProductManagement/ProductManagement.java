/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ProductManagement;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author XUAN
 */
@Stateless
@Local(ProductManagementLocal.class)
@Remote(ProductManagementRemote.class)
public class ProductManagement implements ProductManagementRemote, ProductManagementLocal {

    @PersistenceContext
    private EntityManager em;
    
    
}
