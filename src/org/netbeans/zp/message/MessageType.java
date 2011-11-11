package org.netbeans.zp.message;

/**
 * Rodzaje wiadomości otrzymywanych od- i wysyłanych do- serwera
 * przez klientów.
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public enum MessageType {

  /*
   * prywatna wiadomość
   */
  PrivateMessage,

  /*
   * wiadomość grupowa na czacie ogólnodostępnym
   */
  GroupMessage,

  /*
   * dodanie nowego pliku źródłowego do edycji
   */
  NewSourceAdded,

  /*
   * dodanie fragmentu kodu do pliku źródłowego
   */
  SourceCodeInserted,

  /*
   * usunięcie fragmentu kodu z pliku źródłowego
   */
  SourceCodeRemoved,

  /*
   * ustawienia kursora na konkretnej pozycji w pliku źródłowym
   */
  CursorPositioned,

  /*
   * zaznaczenie fragmentu kodu w pliku źródłowym
   */
  SelectionMade

}
