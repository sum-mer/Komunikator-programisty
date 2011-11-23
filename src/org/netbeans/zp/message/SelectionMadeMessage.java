/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

import java.io.Serializable;

/**
 * Komunikat - dokonano zaznaczenia w podanym kodzie zrodlowym.
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class SelectionMadeMessage extends Message implements Serializable {

  /*
   * identyfikator pliku zrodlowego (patrz javadoc do NewSourceAddedMessage)
   */
  public String FileID;

  /*
   * poczatek zaznaczenia wzgledem poczatku pliku (0)
   */
  public int SelectionStart;

  /*
   * dlugosc zaznaczenia w znakach (> 0)
   */
  public int SelectionLength;

  public MessageType getType() {
	return MessageType.SelectionMade;
  }

}