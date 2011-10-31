/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

/**
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class GroupMessage extends Message {

  public String Body;

  public MessageType getType() {
	return MessageType.GroupMessage;
  }

}
