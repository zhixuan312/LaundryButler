package jsf.managedbean;

import TransactionManagement.TransactionManagementRemote;
import entity.Employee;
import entity.Transaction;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

@Named(value = "transactionManagedBean")
@SessionScoped
public class TransactionManagedBean implements Serializable {

    @EJB
    private TransactionManagementRemote transactionManagementLocal;
    @Inject
    private SignUpAndLoginManagedBean signUpAndLoginManagedBean;

    private Employee admin;
    private List<Transaction> transactions;
    private Transaction selectedTransaction;

    public TransactionManagedBean() {
        admin = new Employee();
        transactions = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

        try {
            if (ec.getSessionMap().get("login") == null) {
                ec.redirect("index.xhtml?faces-redirect=true");
            } else {
                if (ec.getSessionMap().get("login").equals(false)) {
                    ec.redirect("index.xhtml?faces-redirect=true");
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        admin = signUpAndLoginManagedBean.getAccountManagementRemote().getEmployee();
        transactions = transactionManagementLocal.viewAllTransaction();
    }

    public Employee getAdmin() {
        return admin;
    }

    public void setAdmin(Employee admin) {
        this.admin = admin;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public TransactionManagementRemote getTransactionManagementLocal() {
        return transactionManagementLocal;
    }

    public void setTransactionManagementLocal(TransactionManagementRemote transactionManagementLocal) {
        this.transactionManagementLocal = transactionManagementLocal;
    }

    public SignUpAndLoginManagedBean getSignUpAndLoginManagedBean() {
        return signUpAndLoginManagedBean;
    }

    public void setSignUpAndLoginManagedBean(SignUpAndLoginManagedBean signUpAndLoginManagedBean) {
        this.signUpAndLoginManagedBean = signUpAndLoginManagedBean;
    }

    public Transaction getSelectedTransaction() {
        return selectedTransaction;
    }

    public void setSelectedTransaction(Transaction selectedTransaction) {
        this.selectedTransaction = selectedTransaction;
    }
    
}
