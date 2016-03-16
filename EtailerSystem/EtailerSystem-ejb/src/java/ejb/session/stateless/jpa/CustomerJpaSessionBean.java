package ejb.session.stateless.jpa;

import javax.ejb.Local;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Local(CustomerJpaSessionBeanLocal.class)
@Remote(CustomerJpaSessionBeanRemote.class)
public class CustomerJpaSessionBean implements CustomerJpaSessionBeanRemote, CustomerJpaSessionBeanLocal {
    @PersistenceContext
    private EntityManager em;
}
