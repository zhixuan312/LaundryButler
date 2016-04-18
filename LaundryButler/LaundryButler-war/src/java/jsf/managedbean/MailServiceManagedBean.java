package jsf.managedbean;

import MailService.MailServiceRemote;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.mail.MessagingException;



@Named(value = "mailServiceManagedBean")
@RequestScoped

public class MailServiceManagedBean implements Serializable
{
    @EJB
    private MailServiceRemote ms;
    
    private String name;
    private String email;
    private String comments;
    
    private String recipient;
    private String subject;
    private String message;
    private String statusMessage = "";
    
    @PostConstruct
    public void init()
    {
        FacesContext.getCurrentInstance().getExternalContext().getSession(true);
    }
    
    public MailServiceManagedBean()
    {

    }
    public void send(ActionEvent event) {
      System.out.println("Managed Bean start to send email...");
      
      recipient = "LaundryButlerFounder <zhangzhixuan312@gmail.com>, LaundryButlerFounder <horensen1@gmail.com>, , TechSupport<victorsunlixin@gmail.com>";
      subject = "IS2150 Testing Email Contact";
      message = "hello world";
        statusMessage = "Message Sent";
        try {
            ms.sendMessage(recipient, subject, message);
        }
        catch(MessagingException ex) {
            statusMessage = ex.getMessage();
        }
        //return "index";  // redisplay page with status message
    }

  /**
   * @return the recipient
   */
  public String getRecipient() {
    return recipient;
  }

  /**
   * @param recipient the recipient to set
   */
  public void setRecipient(String recipient) {
    this.recipient = recipient;
  }

  /**
   * @return the subject
   */
  public String getSubject() {
    return subject;
  }

  /**
   * @param subject the subject to set
   */
  public void setSubject(String subject) {
    this.subject = subject;
  }

  /**
   * @return the message
   */
  public String getMessage() {
    return message;
  }

  /**
   * @param message the message to set
   */
  public void setMessage(String message) {
    this.message = message;
  }

  /**
   * @return the statusMessage
   */
  public String getStatusMessage() {
    return statusMessage;
  }

  /**
   * @param statusMessage the statusMessage to set
   */
  public void setStatusMessage(String statusMessage) {
    this.statusMessage = statusMessage;
  }

  /**
   * @return the name
   */
  public String getName() {
    return name;
  }

  /**
   * @param name the name to set
   */
  public void setName(String name) {
    this.name = name;
  }

  /**
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * @param email the email to set
   */
  public void setEmail(String email) {
    this.email = email;
  }

  /**
   * @return the comments
   */
  public String getComments() {
    return comments;
  }

  /**
   * @param comments the comments to set
   */
  public void setComments(String comments) {
    this.comments = comments;
  }
    
    
}