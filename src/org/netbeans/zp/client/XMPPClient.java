/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.zp.client;

import java.net.NetPermission;
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
import org.jivesoftware.smack.filter.PacketFilter;
import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.packet.Packet;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.netbeans.zp.message.GroupMessage;
import org.netbeans.zp.message.PrivateMessage;

/**
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class XMPPClient implements PacketListener
{

  private static String SERVER_ADDRESS = "draugr.de";
  private static String SERVER_CONFERENCE_ADDRESS = "conference.draugr.de";
  private static int SERVER_PORT = 5222;
  private XMPPConnection _connection;
  private MultiUserChat _collaboration;
  private ArrayList<ClientMessageListener> _messageListeners;

  static {
	XMPPConnection.DEBUG_ENABLED = true;
  }

  public XMPPClient() {
	_messageListeners = new ArrayList<ClientMessageListener>();
  }

  public void connect() throws XMPPException {
	ConnectionConfiguration config = new ConnectionConfiguration(SERVER_ADDRESS, SERVER_PORT);
	_connection = new XMPPConnection(config);
	_connection.addPacketListener(this, new PacketFilter() {

	  public boolean accept(Packet packet) {
		return true;
	  }
	});
	_connection.connect();
  }

  public void disconnect() {
	_connection.disconnect();
  }

  public void login(String userName, String password) throws XMPPException {
	_connection.login(userName, password);
  }

  public void register(String userName, String password) throws XMPPException {
	// TODO
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

  public void addMessageListener(ClientMessageListener listener) {
	_messageListeners.add(listener);
  }

  public void removeMessageListener(ClientMessageListener listener) {
	_messageListeners.remove(listener);
  }

  public void processPacket(Packet packet) {
	
	org.jivesoftware.smack.packet.Message message = (org.jivesoftware.smack.packet.Message)packet;
	if (message.getProperty("metadata") != null) {
	  org.netbeans.zp.message.Message metadata = (org.netbeans.zp.message.Message)message.getProperty("metadata");
	  for (ClientMessageListener l : _messageListeners) {
		l.handle(metadata);
	  }
	}
  }

  public String createCollaboration(String room, String owner) throws XMPPException {
	String roomID = String.format("%s@%s", room, SERVER_CONFERENCE_ADDRESS);

	_collaboration = new MultiUserChat(_connection, roomID);
	_collaboration.create(owner);
	_collaboration.sendConfigurationForm(new Form(Form.TYPE_SUBMIT));

	return roomID;
  }

  public void joinCollaboration(String room, String nickname) throws XMPPException {
	_collaboration = new MultiUserChat(_connection, room);
	_collaboration.join(nickname);
  }

  public void sendChatMessage(String message) throws XMPPException {
	GroupMessage groupMsg = new GroupMessage();
	groupMsg.Body = message;
	sendCodeMessage(groupMsg);
  }

  public void sendChatMessage(String message, String to) throws XMPPException {
	PrivateMessage privateMsg = new PrivateMessage();
	privateMsg.UserID = _collaboration.getNickname();
	privateMsg.Body = message;
	
	org.jivesoftware.smack.packet.Message msg = new Message(to);
	msg.setProperty("metadata", privateMsg);
	
	_connection.sendPacket(msg);
  }

  public void sendCodeMessage(org.netbeans.zp.message.Message message) throws XMPPException {
	org.jivesoftware.smack.packet.Message msg = _collaboration.createMessage();
	message.UserID = _collaboration.getNickname();
	msg.setProperty("metadata", message);
	_collaboration.sendMessage(msg);
  }


}
