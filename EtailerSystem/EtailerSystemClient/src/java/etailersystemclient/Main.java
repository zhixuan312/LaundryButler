package etailersystemclient;

import ejb.session.stateful.ShoppingSessionBeanRemote;
import ejb.session.stateless.CustomerSessionBeanRemote;
import ejb.session.stateless.ProductCatalogueSessionBeanRemote;
import javax.ejb.EJB;



public class Main
{
    @EJB
    private static CustomerSessionBeanRemote customerSessionBeanRemote;
    @EJB
    private static ProductCatalogueSessionBeanRemote productCatalogueSessionBeanRemote;
    @EJB
    private static ShoppingSessionBeanRemote shoppingSessionBeanRemote;

    
    
    public Main() {
    }
    
    
    
    public static void main(String[] args)
    {
        if(customerSessionBeanRemote != null && productCatalogueSessionBeanRemote != null && shoppingSessionBeanRemote != null)
        {
            Controller controller = new Controller(customerSessionBeanRemote, productCatalogueSessionBeanRemote, shoppingSessionBeanRemote);
            controller.runSystem();
        }
        else
        {
            System.err.println("FATAL EXCEPTION: Unable to inject enterprise session beans! System will be terminated!");
        }
    }        
}