/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

import java.io.Serializable;

/**
 * Abstrakcyjna klasa wiadomosci/komunikatu.
 * Zawiera pola wspolne dla wszystkich rodzajow komunikatow.
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public abstract class Message implements Serializable {

  /*
   * identyfikator uzytkownika, do ktorego odnosi sie dana wiadomosc
   */
  public String UserID;

  /*
   * zwraca typ komunikatu, dzieki czemu mozna zrzutowac
   * go na konkretna klase i wyciagnac szczegolowe dane
   */
  public abstract MessageType getType();

}
