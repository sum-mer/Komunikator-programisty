package org.netbeans.zp.client;

import org.jivesoftware.smack.packet.Registration;

/**
 * Interfejs nasłuchujący komunikatów związanych z rejestracją.
 * Powinna go implementować klasa GUI odpowiedzialna za rejestrację
 * i/lub zarządzanie kontem.
 * 
 * @author Bartłomiej Bułat <bartek.bulat at gmail.com>
 */
public interface RegistrationListener {
  
  /**
   * Serwer zwrócił wiadomość z zaproszeniem.
   * @param msg Wiadomość serwera
   */
  public void invitation(Registration msg);
  
  /**
   * Operacja związana z rejestracją (dodanie, aktualizacja lub usunięcie użytkownika) zakończona sukcesem.
   * @param msg Wiadomość serwera
   */
  public void success(Registration msg);
  
  /**
   * Procedura związana z rejestracją zwróciła błąd.
   * Opis poszczególnych kodów: http://xmpp.org/extensions/xep-0086.html
   * @param msg Wiadomość serwera
   */
  public void error(Registration msg);
  
}