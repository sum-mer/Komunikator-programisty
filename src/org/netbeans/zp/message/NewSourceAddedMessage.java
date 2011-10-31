/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

/**
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class NewSourceAddedMessage extends Message {

  public String FileName;
  public String FileDirectory;
  public String SourceCode;

  public MessageType getType() {
	return MessageType.NewSourceAdded;
  }

}
