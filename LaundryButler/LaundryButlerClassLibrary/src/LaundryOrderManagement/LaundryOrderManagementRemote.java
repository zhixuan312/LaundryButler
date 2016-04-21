/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package LaundryOrderManagement;

import entity.Box;
import entity.SharedBoxPermission;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author XUAN
 */
@Remote
public interface LaundryOrderManagementRemote {
    
    public Boolean createBox (Box box);
    
    public Boolean updateBox (Box box);
    
    public List<Box> viewAllBox ();
    
    public List<Box> viewAllBoxByEmployeeId (Long employeeId);
    
    public List<Box> viewAllBoxByCustomerId (Long customerId);
    
    public Boolean deleteBox (Long boxId);
    
    public Boolean deleteBoxes (List<Box> boxes);
    
    public Boolean createSharedBoxPermission (SharedBoxPermission sharedBoxPermission);
    
    public Boolean updateSharedBoxPermission (SharedBoxPermission sharedBoxPermission);
    
    public List<SharedBoxPermission> viewAllSharedBoxPermission ();
    
    public List<SharedBoxPermission> viewAllSharedBoxPermissionByCustomerId (Long customerId);
    
    public List<SharedBoxPermission> viewAllSharedBoxPermissionByBoxId (Long boxId);
    
    public Boolean deleteSharedBoxPermission (Long sharedBoxPermissionId);
    
    public Boolean deleteSharedBoxPermissions (List<SharedBoxPermission> sharedBoxPermissions);
    
    public Box retrieveBoxByBoxId (Long boxId);
    
    public SharedBoxPermission retrieveSharedBoxPermissionByPermissionId (Long permissionId);
    
}
