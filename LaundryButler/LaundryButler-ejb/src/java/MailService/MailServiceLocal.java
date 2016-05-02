package MailService;

import javax.ejb.Local;
import javax.mail.MessagingException;

@Local
public interface MailServiceLocal {

    public void sendMessage(String recipient, String subject, String message) throws MessagingException;

}
