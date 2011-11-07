package org.netbeans.zp.client;

import java.util.Set;
import org.jivesoftware.smack.packet.XMPPError;
import org.jivesoftware.smack.packet.XMPPError.Type;

/**
 * Interfejs nasłuchujący komunikatów związanych z rejestracją.
 * Powinna go implemoentować klasa GUI odpowiedzialna za rejestrację.
 * 
 * @author Bartłomiej Bułat <bartek.bulat at gmail.com>
 */
public interface RegistrationListener {
  
  /**
   * Serwer zwrócił wymagane pola do rejestracji.
   * @param fields Zbiór wymaganych pól.
   */
  public void setRegistrationFields(Set<String> fields);
  
  /**
   * Rejestracja zakończona sukcesem.
   */
  public void success();
  
  /**
   * Procedura rejestracji zwróciła błąd.
   * Opis poszczególnych kodów: http://xmpp.org/extensions/xep-0086.html
   * @param code Kod błedy
   * @param type Typ błędu
   */
  public void error(XMPPError er);
  
}
