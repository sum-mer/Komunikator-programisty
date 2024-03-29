/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * StartChat.java
 *
 * Created on 2011-11-13, 15:08:00
 */
package org.netbeans.zp.gui;

import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import org.jdesktop.application.Action;
import org.netbeans.zp.client.ClientMessageListener;
import org.netbeans.zp.client.XMPPClient;
import org.netbeans.zp.message.Message;

/**
 *
 * @author Tomek
 */
public class StartChat extends javax.swing.JDialog implements  Runnable {
    private DefaultListModel items;
    private String n;
    private String to;
    private RealApp owner;
    
    /** Creates new form StartChat */
    public StartChat(java.awt.Frame parent, boolean modal, RealApp owner, String nickname, DefaultListModel it) {
        super(parent, modal);
        initComponents();
        this.setFocusableWindowState(true);
        items = it;
        this.owner = owner;
        n = nickname;
        for (int i = 0; i < items.size(); i++){
            contactList.addItem(items.getElementAt(i));
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        contactList = new javax.swing.JComboBox();
        startChatButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.netbeans.zp.gui.KomunikatorApp.class).getContext().getResourceMap(StartChat.class);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(400, 219));
        setName("Form"); // NOI18N

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        contactList.setName("contactList"); // NOI18N

        startChatButton.setText(resourceMap.getString("startChatButton.text")); // NOI18N
        startChatButton.setName("startChatButton"); // NOI18N
        startChatButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                startChatButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(startChatButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(contactList, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(91, 91, 91)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(contactList, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(startChatButton)
                .addContainerGap(67, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void startChatButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startChatButtonMouseClicked
        // TODO add your handling code here:
        //otwieranie okienka rozmowy
        to = contactList.getSelectedItem().toString();
        dispose();
        if (ChatDlg == null){
            ChatDlg = new Chat(KomunikatorApp.getApplication().getMainFrame(), true, owner, n, to);
            ChatDlg.setTitle(contactList.getSelectedItem().toString());
            ChatDlg.setLocationRelativeTo(this);
            ChatDlg.setVisible(true);
        }
        else {
            ChatDlg.dispose();
            ChatDlg = null;
            startChatButtonMouseClicked(evt);
        }
    }//GEN-LAST:event_startChatButtonMouseClicked

    /**
     * @param args the command line arguments
     */
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox contactList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton startChatButton;
    // End of variables declaration//GEN-END:variables
    private JDialog ChatDlg;

    public void run() {
    }
}
