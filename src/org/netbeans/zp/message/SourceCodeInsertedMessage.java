/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

/**
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class SourceCodeInsertedMessage extends Message {

  public String FileID;
  public int InsertPosition;
  public String InsertedCode;

  public MessageType getType() {
	return MessageType.SourceCodeInserted;
  }

}
