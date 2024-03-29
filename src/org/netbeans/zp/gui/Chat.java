/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * Chat.java
 *
 * Created on 2011-11-14, 12:54:15
 */
package org.netbeans.zp.gui;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.text.DefaultCaret;
import org.jivesoftware.smack.XMPPException;
import org.netbeans.zp.client.ClientMessageListener;
import org.netbeans.zp.client.XMPPClient;
import org.netbeans.zp.message.Message;
import org.netbeans.zp.message.MessageType;
import org.netbeans.zp.message.PrivateMessage;

/**
 *
 * @author Dom
 */
public class Chat extends javax.swing.JDialog implements ClientMessageListener, Runnable {

  private String to;
  private String n;
  private RealApp owner;

  /** Creates new form Chat */
  public Chat(java.awt.Frame parent, boolean modal, RealApp owner, String nickname, String address) {
    super(parent, modal);
    initComponents();
    this.owner = owner;
    n = nickname;
    to = address;
    System.out.println(n + " to: " + to);
    jTextArea2.setText("");
    getRootPane().setDefaultButton(sendBtn);
    XMPPClient.getInstance().addMessageListener(this);


    DefaultCaret caret = (DefaultCaret) jTextArea2.getCaret();
    caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
  }

  public String getAddress() {
    return to;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();
        sendBtn = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jTextArea1.setColumns(20);
        jTextArea1.setEditable(false);
        jTextArea1.setFont(jTextArea1.getFont().deriveFont(jTextArea1.getFont().getSize()+3f));
        jTextArea1.setRows(5);
        jTextArea1.setName("jTextArea1"); // NOI18N
        jScrollPane1.setViewportView(jTextArea1);

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        jTextArea2.setColumns(20);
        jTextArea2.setFont(jTextArea2.getFont().deriveFont(jTextArea2.getFont().getSize()+3f));
        jTextArea2.setRows(5);
        jTextArea2.setName("jTextArea2"); // NOI18N
        jScrollPane2.setViewportView(jTextArea2);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.netbeans.zp.gui.KomunikatorApp.class).getContext().getResourceMap(Chat.class);
        sendBtn.setText(resourceMap.getString("sendBtn.text")); // NOI18N
        sendBtn.setName("sendBtn"); // NOI18N
        sendBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendBtnMouseClicked(evt);
            }
        });

        closeButton.setText(resourceMap.getString("closeButton.text")); // NOI18N
        closeButton.setName("closeButton"); // NOI18N
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(sendBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 248, Short.MAX_VALUE)
                        .addComponent(closeButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendBtn)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeButtonMouseClicked
      // TODO add your handling code here:
      //klikanie w krzyzyk na chacie
      dispose();
    }//GEN-LAST:event_closeButtonMouseClicked

    private void sendBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendBtnMouseClicked
      // TODO add your handling code here:
      if (jTextArea2.getText() != "") {
        String text = jTextArea2.getText() + "\n";
        System.out.println("'" + to + "@draugr.de'");

        try {
          XMPPClient.getInstance().sendChatMessage(text, to/*+"@draugr.de"*/);
        } catch (XMPPException ex) {
          Logger.getLogger(Chat.class.getName()).log(Level.SEVERE, null, ex);
        }
        jTextArea1.append("JA\n" + text);
        jTextArea2.setText("");
      }
    }//GEN-LAST:event_sendBtnMouseClicked
  /**
   * @param args the command line arguments
   */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextArea jTextArea2;
    private javax.swing.JButton sendBtn;
    // End of variables declaration//GEN-END:variables
  private JDialog fileToChoose;
  private JDialog archivesBox;
  private JDialog codeBox;

  @Override
  public void handle(Message message) {
    System.out.println("Wiadomość!");
    if (message.getType() == MessageType.PrivateMessage) {
      PrivateMessage msg = (PrivateMessage) message;
      String text = msg.UserID + "\n" + msg.Body;
      jTextArea1.append(text);
    }
  }

  @Override
  public void run() {
  }

  @Override
  public void dispose() {
    owner.removeChat(this);
    XMPPClient.getInstance().removeMessageListener(this);
    super.dispose();
  }
}
