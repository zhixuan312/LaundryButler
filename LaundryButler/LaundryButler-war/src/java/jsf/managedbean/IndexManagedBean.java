/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package jsf.managedbean;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

/**
 *
 * @author XUAN
 */
@Named(value = "indexManagedBean")
@SessionScoped
public class IndexManagedBean implements Serializable{
    
    public IndexManagedBean() {
    }
    
}
