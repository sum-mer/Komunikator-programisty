/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.zp.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.XMPPError;
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
  public void setRegistrationFields(Set<String> fields) {
    Map<String,String> attr = new HashMap<String, String>();
        for( String s: fields ) {
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
  public void success() {
    System.out.println("Pomyślnie zarejestrowano użytkownika");
  }

  @Override
  public void error(XMPPError er) {
    System.out.println("Błąd " + er.getCode() + " type: " + er.getType().toString() + "msg: " + er.getMessage());
    c.getRegistrationFields();
  }

  @Override
  public void run() {
    c.getRegistrationFields();
  }
  
  
  
  
}
