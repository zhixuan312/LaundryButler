package MailService;

import javax.ejb.Remote;
import javax.mail.MessagingException;

@Remote
public interface MailServiceRemote {

    public void sendMessage(String recipient, String subject, String message) throws MessagingException;

}
