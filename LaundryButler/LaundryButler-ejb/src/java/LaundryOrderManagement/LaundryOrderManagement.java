package LaundryOrderManagement;

import entity.Box;
import entity.SharedBoxPermission;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateful
public class LaundryOrderManagement implements LaundryOrderManagementRemote, LaundryOrderManagementLocal {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean createBox(Box box) {
        try {
            em.persist(box);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateBox(Box box) {
        try {
            em.merge(box);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<Box> viewAllBox() {
        List<Box> boxes = null;
        try {
            String jpql = "SELECT b FROM Box b ORDER BY b.createdDateTime DESC";
            Query query = em.createQuery(jpql);
            boxes = query.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return boxes;
    }

    @Override
    public List<Box> viewAllBoxByEmployeeId(Long employeeId) {
        List<Box> boxes = null;
        try {
            String jpql = "SELECT b FROM Box b WHERE b.employee.employeeId = " + employeeId;
            Query query = em.createQuery(jpql);
            boxes = query.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return boxes;
    }

    @Override
    public List<Box> viewAllBoxByCustomerId(Long customerId) {
        List<Box> boxes = null;
        try {
            String jpql = "SELECT b FROM Box b WHERE b.customer.customerId = " + customerId;
            Query query = em.createQuery(jpql);
            boxes = query.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return boxes;
    }

    @Override
    public Box retrieveBoxByBoxId(Long boxId) {
        try {
            String jpql = "SELECT b FROM Box b WHERE b.boxId= " + boxId;
            Query query = em.createQuery(jpql);
            return (Box) query.getSingleResult();
        } catch (NoResultException ex) {
            return new Box();
        }

    }

    @Override
    public Boolean deleteBox(Long boxId) {
        try {
            Box box = em.find(Box.class, boxId);
            em.remove(box);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteBoxes(List<Box> boxes) {
        for (Box box : boxes) {
            deleteBox(box.getBoxId());
        }
        return true;
    }

    @Override
    public Boolean createSharedBoxPermission(SharedBoxPermission sharedBoxPermission) {
        try {
            em.persist(sharedBoxPermission);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean updateSharedBoxPermission(SharedBoxPermission sharedBoxPermission) {
        try {
            em.merge(sharedBoxPermission);
            em.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<SharedBoxPermission> viewAllSharedBoxPermission() {
        List<SharedBoxPermission> sharedBoxPermissions = null;
        try {
            String jpql = "SELECT sbp FROM SharedBoxPermission sbp";
            Query query = em.createQuery(jpql);
            sharedBoxPermissions = query.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return sharedBoxPermissions;
    }

    @Override
    public List<SharedBoxPermission> viewAllSharedBoxPermissionByCustomerId(Long customerId) {
        List<SharedBoxPermission> sharedBoxPermissions = null;
        try {
            String jpql = "SELECT sbp FROM SharedBoxPermission sbp WHERE sbp.customer.customerId = " + customerId;
            Query query = em.createQuery(jpql);
            sharedBoxPermissions = query.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return sharedBoxPermissions;
    }

    @Override
    public List<SharedBoxPermission> viewAllSharedBoxPermissionByBoxId(Long boxId) {
        List<SharedBoxPermission> sharedBoxPermissions = null;
        try {
            String jpql = "SELECT sbp FROM SharedBoxPermission sbp WHERE sbp.box.boxId = " + boxId;
            Query query = em.createQuery(jpql);
            sharedBoxPermissions = query.getResultList();
        } catch (NoResultException ex) {
            ex.printStackTrace();
        }
        return sharedBoxPermissions;
    }

    @Override
    public SharedBoxPermission retrieveSharedBoxPermissionByPermissionId(Long permissionId) {
        try {
            String jpql = "SELECT sbp FROM SharedBoxPermission sbp WHERE sbp.sharedBoxPermissionId = " + permissionId;
            Query query = em.createQuery(jpql);
            return (SharedBoxPermission) query.getSingleResult();
        } catch (NoResultException ex) {
            return new SharedBoxPermission();
        }
    }

    @Override
    public Boolean deleteSharedBoxPermission(Long sharedBoxPermissionId) {
        try {
            SharedBoxPermission sharedBoxPermission = em.find(SharedBoxPermission.class, sharedBoxPermissionId);
            em.remove(sharedBoxPermission);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Boolean deleteSharedBoxPermissions(List<SharedBoxPermission> sharedBoxPermissions) {
        for (SharedBoxPermission sharedBoxPermission : sharedBoxPermissions) {
            deleteSharedBoxPermission(sharedBoxPermission.getSharedBoxPermissionId());
        }
        return true;
    }
}
