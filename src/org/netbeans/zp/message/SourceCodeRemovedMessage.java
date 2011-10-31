/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

import java.io.Serializable;

/**
 * Komunikat - usunieto fragment kodu z pliku zrodlowego.
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class SourceCodeRemovedMessage extends Message implements Serializable {

  /*
   * identyfikator pliku zrodlowego (patrz javadoc do NewSourceAddedMessage)
   */
  public String FileID;

  /*
   * pozycja od ktorej usunieto kod
   */
  public int RemovePosition;

  /*
   * ilosc usunietych znakow (> 0)
   */
  public int RemoveLength;

  public MessageType getType() {
	return MessageType.SourceCodeRemoved;
  }

}
