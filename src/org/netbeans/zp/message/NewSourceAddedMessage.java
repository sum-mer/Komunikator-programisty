/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.message;

import java.io.Serializable;

/**
 * Komunikat - dodano nowy plik zrodlowy do pokoju do wspolpracy nad nim.
 * W innych komunikatach wystepuje identyfikator pliku zrodlowego -
 * jest to ( FileDirectory + FileName ), np. "src/foo/bar/test.cpp".
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class NewSourceAddedMessage extends Message implements Serializable {

  /*
   * nazwa pliku zrodlowego (np. "test.cpp")
   */
  public String FileName;

  /*
   * wzgledna sciezka katalogu nadrzednego pliku (np. "src/foo/bar/")
   */
  public String FileDirectory;

  /*
   * tresc pliku zrodlowego (kod)
   */
  public String SourceCode;

  public MessageType getType() {
	return MessageType.NewSourceAdded;
  }

}
