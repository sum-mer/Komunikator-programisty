/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.zp.client;

import java.util.ArrayList;
import java.util.Collection;
import org.jivesoftware.smack.Chat;
import org.jivesoftware.smack.ConnectionConfiguration;
import org.jivesoftware.smack.MessageListener;
import org.jivesoftware.smack.PacketListener;
import org.jivesoftware.smack.Roster;
import org.jivesoftware.smack.RosterEntry;
import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.muc.MultiUserChat;

/**
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class XMPPClient implements MessageListener, PacketListener
{

  private static String SERVER_ADDRESS = "draugr.de";
  private static String SERVER_CONFERENCE_ADDRESS = "conference.draugr.de";
  private static int SERVER_PORT = 5222;
  private XMPPConnection _connection;
  private MultiUserChat _collaboration;

  static {
	//XMPPConnection.DEBUG_ENABLED = true;
  }

  public XMPPClient() {
	
  }

  public void connect() throws XMPPException {
	ConnectionConfiguration config = new ConnectionConfiguration(SERVER_ADDRESS, SERVER_PORT);
	_connection = new XMPPConnection(config);
	_connection.connect();
  }

  public void login(String userName, String password) throws XMPPException {
	_connection.login(userName, password);
  }

  public void register(String userName, String password) throws XMPPException {
	// TODO
  }

  public void sendMessage(String message, String to) throws XMPPException {
	Chat chat = _connection.getChatManager().createChat(to, this);
	chat.sendMessage(message);
  }

  public ArrayList<String> getBuddiesList() {
	Roster roster = _connection.getRoster();
	Collection<RosterEntry> entries = roster.getEntries();

	ArrayList<String> buddies = new ArrayList<String>();
	for (RosterEntry r : entries) {
	  buddies.add(r.getUser());
	}

	return buddies;
  }

  public void disconnect() {
	_connection.disconnect();
  }

  public void processMessage(Chat chat, Message message) {

  }

  public void processPacket(Packet packet) {
	
	Message message = (Message)packet;
	if (message.getProperty("metadata") != null) {

	}
	else {
	  System.out.println(message.getFrom() + ": " + message.getBody());
	}
  }

  public String createCollaboration(String room, String owner) throws XMPPException {
	String roomID = String.format("%s@%s", room, SERVER_CONFERENCE_ADDRESS);

	_collaboration = new MultiUserChat(_connection, roomID);
	_collaboration.addMessageListener(this);
	_collaboration.create(owner);
	_collaboration.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));

	return roomID;
  }

  public void joinCollaboration(String room, String nickname) throws XMPPException {
	_collaboration = new MultiUserChat(_connection, room);
	_collaboration.addMessageListener(this);
	_collaboration.join(nickname);
  }

  public void sendChatMessage(String message) throws XMPPException {
	_collaboration.sendMessage(message);
  }

  public void sendChatMessage(String message, String to) throws XMPPException {
	Chat chat = _connection.getChatManager().createChat(to, this);
    chat.sendMessage(message);
  }

  public void sendCodeMessage(org.netbeans.zp.message.Message message) throws XMPPException {
	org.jivesoftware.smack.packet.Message msg = new Message();
	msg.setProperty("metadata", message);
	_collaboration.sendMessage(msg);
  }


}
