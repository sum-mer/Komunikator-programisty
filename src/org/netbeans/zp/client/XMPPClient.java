/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.zp.client;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.IQ.Type;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.netbeans.zp.message.GroupMessage;
import org.netbeans.zp.message.PrivateMessage;

/**
 * Klient komunikujacy sie za pomoca XMPP z serwerem Jabbera.
 * Potrafi otrzymywac wiadomosci roznych typow i przekazywac je
 * do nasluchujacych obiektow, np. GUI moze nasluchiwac komunikatow,
 * na ktore powinno odpowiednio zareagowac.
 * Mozna za jej pomoca rowniez wysylac komunikaty, tj. GUI powinno
 * za pomoca tej klasy informowac reszte klientow, ze np. nalezy
 * zaznaczyc konkretny kawalek kodu lub dodac jakis nowy, itp.
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class XMPPClient implements PacketListener {

  /*
   * adres serwera, przez ktory odbywac sie bedzie komunikacja
   */
  private static String SERVER_ADDRESS = "draugr.de";

  /*
   * adres konferencyjny serwera (grupowy chat), dla kazdego inny
   */
  private static String SERVER_CONFERENCE_ADDRESS = "conference.draugr.de";

  /*
   * port na ktorym odbywa sie komunikacja z serwerem (Jabber)
   */
  private static int SERVER_PORT = 5222;

  /*
   * polaczenie z serwerem Jabbera
   */
  private XMPPConnection _connection;

  /*
   * uchwyt do pokoju, w ktorym nastepuje wspolpraca
   */
  private MultiUserChat _collaboration;

  /*
   * kontener z nasluchiwaczami komunikatow
   */
  private ArrayList<ClientMessageListener> _messageListeners;
  
  /*
   * Obiekt rejestrujący użytkownika
   */
  private RegistrationListener _registrationListener;

  static {
    XMPPConnection.DEBUG_ENABLED = true; // wlacza/wylacza debugowanie XMPP
  }

  /*
   * Konstruktor - tworzy klienta
   */
  public XMPPClient() {
    _messageListeners = new ArrayList<ClientMessageListener>();
  }

  /*
   * Laczy sie z serwerem Jabbera
   */
  public void connect() throws XMPPException {
    ConnectionConfiguration config = new ConnectionConfiguration(SERVER_ADDRESS, SERVER_PORT);
    _connection = new XMPPConnection(config);
    _connection.addPacketListener(this, new PacketFilter() {

      public boolean accept(Packet packet) {
        return true;
      }
    });
    try {
      _connection.connect();
    } catch (XMPPException ex) {
      System.out.println(ex.getMessage());
    }
  }

  /*
   * Rozlacza sie z serwerem Jabbera
   */
  public void disconnect() {
    _connection.disconnect();
  }

  /*
   * Loguje sie na serwer Jabbera (konto musi byc wczesniej utworzone)
   * @param userName login uzytkownika
   * @param password haslo uzytkownika
   */
  public void login(String userName, String password) throws XMPPException {
    _connection.login(userName, password);
  }

  /*
   * Rejestruje na serwerze nowego uzytkownika
   * @param attributes Mapa atrubutów wymaganych do rejestracji
   */
  public void register(Map<String, String> attributes) throws XMPPException {
    Registration r = new Registration();
    r.setAttributes(attributes);
    r.setType(Type.SET);
    _connection.sendPacket(r);
  }
  
  /*
   * Wysyła do serwera pakiet pytający o wymagane pola rejestracyjne.
   */
  public void getRegistrationFields() {
    Registration r = new Registration();
    _connection.sendPacket(r);
  }

  /*
   * Zwraca liste znajomych *zalogowanego* uzytkownika
   */
  public ArrayList<String> getBuddiesList() {
    Roster roster = _connection.getRoster();
    Collection<RosterEntry> entries = roster.getEntries();

    ArrayList<String> buddies = new ArrayList<String>();
    for (RosterEntry r : entries) {
      buddies.add(r.getUser());
    }

    return buddies;
  }

  /*
   * Dodaje nasluchiwacza wiadomosci
   * @param listener nasluchiwacz wiadomosci
   */
  public void addMessageListener(ClientMessageListener listener) {
    _messageListeners.add(listener);
  }
  
  /*
   * Usuwa nasluchiwacza wiadomosci
   * @param listener nasluchiwacz wiadomosci
   */
  public void removeMessageListener(ClientMessageListener listener) {
    _messageListeners.remove(listener);
  }

  /**
   * Ustawia obiekt odbierający komunikaty rejestracji
   * @param listener Nasłuchiwacz rejestracji
   */
  public void setRegistrationListener(RegistrationListener listener) {
    _registrationListener = listener;
  }
  /*
   * Obsluguje podany pakiet XMPP - rozsyla go do wszystkich nasluchiwaczy,
   * jeśli pakiet jest pakietem rejestracyjnym trafie do odpowiedniego słuchacza.
   */
  @Override
  public void processPacket(Packet packet) {

    if (packet instanceof Registration) {
      if (_registrationListener == null) throw new RuntimeException("Brak nasłuchiwacza procesu rejestracji.");
      Registration r = (Registration) packet;

      if (r.getType() == Type.RESULT) {
        if (r.getInstructions() != null) { // Jeśli ma instrukcję jest to pakiet informacyjny
          Map<String, String> attributes = r.getAttributes();
          _registrationListener.setRegistrationFields(attributes.keySet());
          
        } else {
          _registrationListener.success();
        }
      } else if (r.getType() == Type.ERROR) {
        _registrationListener.error(r.getError()); 
      }
    } else {
      org.jivesoftware.smack.packet.Message message = (org.jivesoftware.smack.packet.Message) packet;
      if (message.getProperty("metadata") != null) {
        org.netbeans.zp.message.Message metadata = (org.netbeans.zp.message.Message) message.getProperty("metadata");
        for (ClientMessageListener l : _messageListeners) {
          l.handle(metadata);
        }
      }
    }
  }

  /*
   * Tworzy nowa pokoj, w ktorym nastepowac bedzie wspolpraca
   * @param room nazwa stolu, np. "robimyProjekt_z_ZP"
   * @param owner nick jakim bedziemy sie jako tworcy pokoju posluwiac na nim
   */
  public String createCollaboration(String room, String owner) throws XMPPException {
    String roomID = String.format("%s@%s", room, SERVER_CONFERENCE_ADDRESS);

    _collaboration = new MultiUserChat(_connection, roomID);
    _collaboration.create(owner);
    _collaboration.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));

    return roomID;
  }

  /*
   * Dolacza do podanego pokoju celem wspolpracy
   * @param room *pelna* nazwa pokoju, np. "robimyProjekt_z_ZP@conference.draugr.de"
   * @param nickname nick pod jakim bedziemy widoczni w pokoju
   */
  public void joinCollaboration(String room, String nickname) throws XMPPException {
    _collaboration = new MultiUserChat(_connection, room);
    _collaboration.join(nickname);
  }

  /*
   * Wysyla wiadomosc tekstowa do wszystkich w pokoju
   * @param message tresc wiadomosci
   */
  public void sendChatMessage(String message) throws XMPPException {
    GroupMessage groupMsg = new GroupMessage();
    groupMsg.Body = message;
    sendCodeMessage(groupMsg);
  }

  /*
   * Wysyla prywatna wiadomosc tekstowa do konkretnego uzytkownika
   * @param message tresc wiadomosci
   * @param to identyfikator odbiorcy, moze byc globalny (bartek@draugr.de)
  albo lokalny (robimyProjekt_z_ZP@conference.draugr.de/dowolny_nick_uzytkownika)
   */
  public void sendChatMessage(String message, String to) throws XMPPException {
    PrivateMessage privateMsg = new PrivateMessage();
    privateMsg.UserID = _collaboration.getNickname();
    privateMsg.Body = message;

    org.jivesoftware.smack.packet.Message msg = new Message(to);
    msg.setProperty("metadata", privateMsg);

    _connection.sendPacket(msg);
  }

  /*
   * Wysyla wiadomosc odnoszaca sie do kodu do wszystkich w pokoju.
   * Za pomoca tej funkcji nalezy przesylac np. informacje o wykonanych
   * lokalnie modyfikacjach kodu, ustawieniu kursora, zaznaczeniu
   * konkretnego fragmentu kodu, itp.
   * @param message obiekt dowolnej klasy komunikatu dziedziczacej po Message
   */
  public void sendCodeMessage(org.netbeans.zp.message.Message message) throws XMPPException {
    org.jivesoftware.smack.packet.Message msg = _collaboration.createMessage();
    message.UserID = _collaboration.getNickname();
    msg.setProperty("metadata", message);
    _collaboration.sendMessage(msg);
  }
}
