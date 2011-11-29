/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.netbeans.zp.junit;

import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smack.packet.Registration;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.netbeans.zp.client.RegistrationListener;
import org.netbeans.zp.client.XMPPClient;
import static org.junit.Assert.*;

/**
 *
 * @author Bartłomiej Bułat <bartek.bulat at gmail.com>
 */
public class XMPPClientTest {

  static String login;
  static String pass;
  XMPPClient c;
  final TestRegistrationListener rl = new TestRegistrationListener();

  class TestRegistrationListener implements RegistrationListener {

    public Registration inv;
    public Registration succ;
    public Registration err;

    @Override
    public void invitation(Registration msg) {
      inv = msg;
      synchronized (this) {
        notifyAll();
      }
    }

    @Override
    public void success(Registration msg) {
      succ = msg;
      synchronized (this) {
        notifyAll();
      }
    }

    @Override
    public void error(Registration msg) {
      err = msg;
      synchronized (this) {
        notifyAll();
      }
    }
  };

  public XMPPClientTest() {
    c = XMPPClient.getInstance();
    c.setRegistrationListener(rl);
  }

  @BeforeClass
  public static void setUpClass() throws Exception {
    login = UUID.randomUUID().toString().substring(0, 8);
    pass = UUID.randomUUID().toString().substring(0, 8);
    
  }

  @AfterClass
  public static void tearDownClass() throws Exception {
  }

  @Before
  public void setUp() {
  }

  @After
  public void tearDown() {
  }

  /**
   * Połącz z serwerem
   */
  @Test
  public void test1Connect() {
    try {
      if (! c.isConnected()) c.connect();
    } catch (XMPPException ex) {
      assertTrue("Should connect", false);
    }
  }

  @Test
  public void test2Registration() throws InterruptedException {
    String lastId;



    lastId = c.getInvitationMessage();

    synchronized (rl) {
      rl.wait(2000);
    }

    synchronized (rl) {
      rl.wait(30000);
    }

    assertNotNull("Should get inv msg", rl.inv);
    assertEquals("Should get proper inv msg", lastId, rl.inv.getPacketID());
    assertTrue("Should get 2 req fields", rl.inv.getAttributes().size() == 2);
    assertTrue("One of fields should be named 'username'", rl.inv.getAttributes().keySet().contains("username"));
    assertTrue("One of fields should be named 'passowrd'", rl.inv.getAttributes().keySet().contains("password"));

    Map<String, String> attr = rl.inv.getAttributes();

    attr.put("username", login);
    attr.put("password", pass);

    try {
      System.out.println(login + " + " + pass);
      lastId = c.register(attr);
    } catch (XMPPException ex) {
      assertTrue("Should send reg package", false);
    }

    synchronized (rl) {
      rl.wait(2000);
    }

    if (rl.err != null) {
      System.out.println(rl.err.getError().getMessage());
    }

    assertNotNull("Should get succ msg", rl.succ);
    assertEquals("Should get proper succ msg", lastId, rl.succ.getPacketID());

    try {
      lastId = c.register(attr);
    } catch (XMPPException ex) {
      assertTrue("Should send reg package", false);
    }

    synchronized (rl) {
      rl.wait(2000);
    }

    assertNotNull("Should get err msg", rl.err);
    assertEquals("Should get err no 500 - wait", 500, rl.err.getError().getCode());

  }

  @Test
  public void test3Login() {
    try {
      try {
        c.login(login, "wrong");
      } finally {
      }
      assertTrue("Should not login", false);
    } catch (XMPPException ex) {
    }
    
    try {
      c.disconnect();
      c.connect();
    } catch (XMPPException ex) {
      assertTrue("Should reconnect", false);
    }

    try {
      System.out.println(login + " + " + pass);
      c.login(login, pass);
    } catch (XMPPException ex) {
      assertTrue("Should login: " + ex.getMessage(), false);
    }
  }

  @Test
  public void test4Conferences() throws InterruptedException {
    String lastId = "";
    try {
      MultiUserChat muc = c.createCollaboration("Room_" + login, login);
    } catch (XMPPException ex) {
      assertTrue("Should create colaboration", false);
    }

  }

  @Test
  public void test5RemoveUser() throws InterruptedException {
    String lastId = "";
    rl.inv = null;
    rl.succ = null;
    rl.err = null;

    try {
      lastId = c.removeUser();
    } catch (XMPPException ex) {
      assertTrue("Should send canceling msg", false);
    }

    synchronized (rl) {
      rl.wait(2000);
    }

    assertNotNull("Should get succ msg after canceling", rl.succ);
    assertEquals("Should get proper succ msg after canceling", lastId, rl.succ.getPacketID());
    assertTrue("Should get proper canceling succ msg after canceling", rl.succ.getAttributes().containsKey("remove"));
  }
}
