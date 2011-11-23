/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.client;

import org.netbeans.zp.message.Message;

/**
 * Interfejs do nasluchiwania komunikatow odbieranych z serwera.
 * Powinny go implementowac klasy odpowiedzialne za GUI i odpowiednio
 * interpretowac otrzymywanie komunikaty, np. komunikat o 
 * zaznaczeniu przez programiste kodu - zaznaczyc go fizycznie
 * w kontrolce kodu, itp.
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public interface ClientMessageListener {

  /*
   * Oddelegowanie obslugi podanej wiadomosci.
   * Mozna pobrac typ wiadomosci (message.getType()) i zrzutowac
   * na odbpowiednia konkretna klase, dobierajac sie w ten sposob
   * do danych charakterystycznych dla konkretnych komunikatow.
   */
  public void handle(Message message);

}