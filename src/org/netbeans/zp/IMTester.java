package org.netbeans.zp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import org.jivesoftware.smack.XMPPException;
import org.netbeans.zp.client.ClientMessageListener;
import org.netbeans.zp.client.XMPPClient;
import org.netbeans.zp.client.Register;
import org.netbeans.zp.message.GroupMessage;
import org.netbeans.zp.message.Message;
import org.netbeans.zp.message.MessageType;

/**
 * Testowanie klienta XMPP w konsoli.
 * Klasa tymczasowa, finalnie moze zostac wywalona.
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public class IMTester {

  public static void main(String args[]) throws IOException, XMPPException {

    System.out.println("You shall not pass...");
//    System.out.println("Connecting...");
//    XMPPClient.getInstance().connect();
//
//// Żeby sprawdzić jak działa rejestracja trzeba odkomentować dwie poniższe linijki i zakomentować całą resztę.
////    Register r = new Register(client);
////     (new Thread(r)).start();
//
//    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//
//    System.out.print("login: ");
//    String login = br.readLine();
//    System.out.print("password: ");
//    String password = br.readLine();
//
//    System.out.println("Loging in...");
//    XMPPClient.getInstance().login(login, password);
//
//    System.out.print("Logged in. Action: ");
//    String action = br.readLine();
//
//    String[] actionTokens = action.split("\\s");
//    if (actionTokens[0].equals("create")) {
//      System.out.println("Creating room " + actionTokens[1] + " as " + actionTokens[2]);
//      //String roomID = XMPPClient.getInstance().createCollaboration(actionTokens[1], actionTokens[2]);
//      System.out.println("Created room " + roomID);
//    } else if (actionTokens[0].equals("join")) {
//      System.out.println("Joining room " + actionTokens[1] + " as " + actionTokens[2]);
//      XMPPClient.getInstance().joinCollaboration(actionTokens[1], actionTokens[2]);
//    }
//
//    XMPPClient.getInstance().addMessageListener(new ClientMessageListener() {
//
//      public void handle(Message message) {
//        if (message.getType() == MessageType.GroupMessage) {
//          GroupMessage msg = (GroupMessage) message;
//          System.out.println("<" + msg.UserID + "> " + msg.Body);
//        }
//      }
//    });
//
////	NewSourceAddedMessage m = new NewSourceAddedMessage();
////	m.FileDirectory = "src/test/";
////	m.FileName = "main.cpp";
////	m.SourceCode = "int main(int argc, char** argv) { return 0; }";
////	client.sendCodeMessage( m );
////
//    String msg;
//    while (!(msg = br.readLine()).equals("end")) {
//      XMPPClient.getInstance().sendChatMessage(msg);
//    }
  }
}
