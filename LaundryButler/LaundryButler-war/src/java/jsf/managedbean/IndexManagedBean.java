package jsf.managedbean;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named(value = "indexManagedBean")
@SessionScoped
public class IndexManagedBean implements Serializable {

    public IndexManagedBean() {
    }

}
