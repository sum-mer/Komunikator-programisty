package org.netbeans.zp.client;

import org.jivesoftware.smack.packet.Presence;



/***
 * Interfejs do nasluchiwania komunikatow związanych z dostępnością użytkowników.
 * Powinny go implementowac klasy odpowiedzialne za GUI
 *
 * @author Tomek
 */
public interface PresenceListener {
    /*
     * Obsługa gdy użytkownik w pokoju wejdzie w stan "Available"
     */
    public void available(Presence msg);
    
    /*
     * Obsługa gdy użytkownik w pokoju wejdzie w stan "Unavailable"
     */
    public void unavailable(Presence msg);
}
