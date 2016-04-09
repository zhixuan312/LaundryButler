/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MailService;

import javax.ejb.Stateless;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author lixinsun
 */
@Stateless
public class MailService implements MailServiceRemote, MailServiceLocal {

  private static MailService theService = null;

  private static Session mailSession;

  private static final String HOST = "smtp.gmail.com";
  private static final int PORT = 587;
  private static final String USER = "victorsunlixin@gmail.com";
  private static final String PASSWORD = "hvqqfrmzjrngvfha";
  private static final String FROM = "Laundry Butler <service@laundrybutler.com>";

  public MailService() {
    
  }

  @Override
  public void sendMessage(String recipient, String subject, String content) throws MessagingException {
    
    System.out.println("Send email started!");
    
    final String username = "victorsunlixin@gmail.com";
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
			message.setFrom(new InternetAddress(FROM));
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
