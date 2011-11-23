/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

import java.io.Serializable;

/**
 * Komunikat - dodano nowy fragment kodu do pliku zrodlowego.
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class SourceCodeInsertedMessage extends Message implements Serializable {

  /*
   * identyfikator pliku zrodlowego (patrz javadoc do NewSourceAddedMessage)
   */
  public String FileID;

  /*
   * miejsce gdzie dokanano wstawienia, wzgledem poczatku pliku (0)
   */
  public int InsertPosition;

  /*
   * tresc kodu ktory zostal wstawiony
   */
  public String InsertedCode;

  public MessageType getType() {
	return MessageType.SourceCodeInserted;
  }

}