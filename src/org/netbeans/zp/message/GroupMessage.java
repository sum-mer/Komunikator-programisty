/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

import java.io.Serializable;

/**
 * Komunikat - wiadomosc grupowa wyslana dla wszystkich w pokoju.
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class GroupMessage extends Message implements Serializable {

  /*
   * tresc wiadomosci
   */
  public String Body;

  public MessageType getType() {
	return MessageType.GroupMessage;
  }

}
