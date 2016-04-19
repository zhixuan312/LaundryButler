package extensions;

import java.util.Properties;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class EmailSender {

    private static final String HOST = "smtp.starhub.net.sg";
    private static final String SENDER_EMAIL = "support@laundrybutler.com";
    private static final String SENDER_NAME = "LaundryButler";
    private String recipientEmail;
    private String subject;
    private String message;

    public EmailSender() {
    }

    public void createEmail() {
        try {
            Properties properties = System.getProperties();
            properties.put("mail.smtp.host", HOST);
            Session session = Session.getDefaultInstance(properties);
            MimeMessage mm = new MimeMessage(session);
            mm.setFrom(new InternetAddress(SENDER_EMAIL, SENDER_NAME));
            mm.addRecipient(Message.RecipientType.TO, new InternetAddress(recipientEmail));
            mm.setSubject(subject);
            mm.setContent(message, "text/html");
            Transport.send(mm);
        } catch (Exception ex) {
            System.out.println("Try changing to a different host address.");
        }
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
