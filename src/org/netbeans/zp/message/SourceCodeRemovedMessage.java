/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

/**
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class SourceCodeRemovedMessage extends Message {

  public String FileID;
  public int RemovePosition;
  public int RemoveLength;

  public MessageType getType() {
	return MessageType.SourceCodeRemoved;
  }

}
