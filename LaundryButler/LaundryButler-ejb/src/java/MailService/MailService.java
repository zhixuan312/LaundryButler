package MailService;

import javax.ejb.Stateless;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@Stateless
public class MailService implements MailServiceRemote, MailServiceLocal {

    private static MailService theService = null;

    private static Session mailSession;

    public MailService() {

    }

    @Override
    public void sendMessage(String recipient, String subject, String content) throws MessagingException {

        System.out.println("Send email started!");

        final String username = "";
        final String password = "hvqqfrmzjrngvfha";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("Laundry Butler <service@laundrybutler.com>"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(recipient));
            message.setSubject(subject);
            message.setText(content);

            //Transport.send(message);
            System.out.println("Done");

        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Send email finished!");
    }

}
