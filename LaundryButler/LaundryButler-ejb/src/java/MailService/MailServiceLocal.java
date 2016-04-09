/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MailService;

import javax.ejb.Local;
import javax.mail.MessagingException;

/**
 *
 * @author lixinsun
 */
@Local
public interface MailServiceLocal {

  public void sendMessage(String recipient, String subject, String message) throws MessagingException;
  
}
