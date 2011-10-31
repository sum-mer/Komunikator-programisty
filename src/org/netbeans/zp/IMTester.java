/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.zp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.jivesoftware.smack.XMPPException;
import org.netbeans.zp.client.XMPPClient;

/**
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class IMTester
{

  public static void main(String args[]) throws IOException, XMPPException {

	XMPPClient client = new XMPPClient();
	System.out.println("Connecting...");
	client.connect();

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

	System.out.print("login: ");
	String login = br.readLine();
	System.out.print("password: ");
	String password = br.readLine();

	System.out.println("Loging in...");
	client.login(login, password);

	System.out.print("Logged in. Action: ");
	String action = br.readLine();

	String[] actionTokens = action.split("\\s");
	if (actionTokens[0].equals("create")) {
	  System.out.println("Creating room " + actionTokens[1] + " as " + actionTokens[2]);
	  String roomID = client.createCollaboration(actionTokens[1], actionTokens[2]);
	  System.out.println("Created room " + roomID);
	}
	else if (actionTokens[0].equals("join")) {
	  System.out.println("Joining room " + actionTokens[1] + " as " + actionTokens[2]);
	  client.joinCollaboration(actionTokens[1], actionTokens[2]);
	}

	String msg;
	while ( !(msg = br.readLine()).equals("end") ) {
	  client.sendChatMessage(msg);
	}
  }
}
