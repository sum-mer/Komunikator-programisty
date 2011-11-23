/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

import java.io.Serializable;
import org.netbeans.zp.message.Message;
import org.netbeans.zp.message.MessageType;

/**
 * Komunikat - uzytkownik ustawil kursor na podanej
 * pozycji w podanym pliku zrodlowym.
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class CursorPositionedMessage extends Message implements Serializable {

  /*
   * identyfikator pliku zrodlowego (patrz javadoc do NewSourceAddedMessage)
   */
  public String FileID;

  /*
   * pozycja kursora wzgledem poczatku pliku zrodlowego (0)
   */
  public int CursorPosition;

  public MessageType getType() {
	return MessageType.CursorPositioned;
  }

}