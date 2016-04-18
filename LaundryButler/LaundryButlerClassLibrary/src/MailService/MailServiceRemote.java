/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MailService;

import javax.ejb.Remote;
import javax.mail.MessagingException;

/**
 *
 * @author lixinsun
 */
@Remote
public interface MailServiceRemote {
  public void sendMessage(String recipient, String subject, String message) throws MessagingException;
  
}
