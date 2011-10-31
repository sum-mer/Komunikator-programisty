/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

/**
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class SelectionMadeMessage extends Message {

  public String FileID;
  public int SelectionStart;
  public int SelectionLength;

  public MessageType getType() {
	return MessageType.SelectionMade;
  }

}
