/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

import java.io.Serializable;

/**
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class SourceCodeInsertedMessage extends Message implements Serializable {

  public String FileID;
  public int InsertPosition;
  public String InsertedCode;

  public MessageType getType() {
	return MessageType.SourceCodeInserted;
  }

}
