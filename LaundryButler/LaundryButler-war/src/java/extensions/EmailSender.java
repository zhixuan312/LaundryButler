package extensions;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private String host;
    private String senderEmail;
    private String senderName;
    private String recipientEmail;
    private String subject;
    private String message;

    public EmailSender() {
        // initialise default values
        host = "smtp.starhub.net.sg";
        senderEmail = "support@laundrybutler.com";
        senderName = "LaundryButler";
    }

    public void createEmail() throws Exception {
        Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        Session session = Session.getDefaultInstance(properties);
        MimeMessage mm = new MimeMessage(session);
        mm.setFrom(new InternetAddress(senderEmail, senderName));
        mm.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
        mm.setSubject(subject);
        mm.setContent(message, "text/html");
        Transport.send(mm);
    }

    public String getHost() {
        return host;
    }

    public String getSenderEmail() {
        return senderEmail;
    }

    public String getSenderName() {
        return senderName;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
