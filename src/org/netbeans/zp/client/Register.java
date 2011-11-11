package org.netbeans.zp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Registration;
import org.netbeans.zp.IMTester;

/**
 * Przykładowa rejestracja (Później do usunięcia)
 * @author Bartłomiej Bułat <bartek.bulat at gmail.com>
 */
public class Register implements RegistrationListener, Runnable {

  XMPPClient c = null;
  BufferedReader br;
  
  public Register(XMPPClient c) {
    this.c = c;
    c.setRegistrationListener(this);
    
    br = new BufferedReader(new InputStreamReader(System.in));
  }
  
  @Override
  public void invitation(Registration msg) {
    Map<String,String> attr = msg.getAttributes();
        for( String s: attr.keySet() ) {
          System.out.println("Wartosc " + s + ": ");
          try {
            String v = br.readLine();
            attr.put(s, v);
          } catch (IOException ex) {
            Logger.getLogger(IMTester.class.getName()).log(Level.SEVERE, null, ex);
          }
        }
    try {
      c.register(attr);
    } catch (XMPPException ex) {
      Logger.getLogger(Register.class.getName()).log(Level.SEVERE, null, ex);
    }
  }

  @Override
  public void success(Registration msg) {
    System.out.println("Pomyślnie zarejestrowano użytkownika");
  }

  @Override
  public void error(Registration msg) {
    System.out.println("Błąd " + msg.getError().getCode() + " type: " + msg.getError().getType().toString() + "msg: " + msg.getError().getMessage());
    c.getInvitationMessage();
  }

  @Override
  public void run() {
    c.getInvitationMessage();
  }
  
  
  
  
}
