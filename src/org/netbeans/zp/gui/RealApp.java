/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * RealApp.java
 *
 * Created on 2011-11-11, 22:40:22
 */

package org.netbeans.zp.gui;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import org.jdesktop.application.Action;
import org.jivesoftware.smack.XMPPException;
import org.netbeans.zp.client.XMPPClient;

/**
 *
 * @author Dom
 */
public class RealApp extends javax.swing.JDialog implements Runnable {
    private String nickname;
    private ArrayList<String> buddies;
    private ArrayList<String> selectedItems;
    private DefaultListModel items = new DefaultListModel();
    int selectedItemsNumber = 0;
    private ListSelectionListener ll = new ListSelectionListener(){
        public void valueChanged(ListSelectionEvent e){
            if (e.getValueIsAdjusting())
                return;
            selectedItemsNumber = jList1.getSelectedValues().length;
            if ( selectedItemsNumber == 1){
                //rozpocznij rozmowę
                selectedItems = new ArrayList<String>();
                selectedItems.add(jList1.getSelectedValue().toString());
            }
            else{
                //rozpocznij konferencję
                //dodać do konstruktora RealApp pobieranie loginu!!
                selectedItems = new ArrayList<String>();
                for (Object item : jList1.getSelectedValues())
                    selectedItems.add(item.toString());
            }
        }
    };
    

    /** Creates new form RealApp */
    public RealApp(java.awt.Frame parent, boolean modal, String login) {
        super(parent, modal);
        initComponents();
        this.setMinimumSize(new Dimension(400, 400));
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        nickname = login;
        buddies = XMPPClient.getInstance().getBuddiesList();
        fillBuddiesList();
    }
    
    public void fillBuddiesList(){
        jList1 = new JList(items);
        for (int i = 0; i < buddies.size(); i++){
            items.addElement(buddies.get(i));
        }
    }
    
    @Action
    public void showAddContactBox(){
        if (addContactBox == null){
            addContactBox = new AddContact(KomunikatorApp.getApplication().getMainFrame(), true, items);
            addContactBox.setTitle("Dodaj znajomego");
            addContactBox.setLocationRelativeTo(this);
            addContactBox.setVisible(true);
            addContactBox.setEnabled(true);
        }
    }
    
    @Action
    public void showStartChatBox(){
        if (startChatBox == null){
            startChatBox = new StartChat(KomunikatorApp.getApplication().getMainFrame(), true, nickname, buddies);
            startChatBox.setTitle("Rozpocznij rozmowę z...");
            startChatBox.setLocationRelativeTo(this);
            startChatBox.setVisible(true);
            startChatBox.setEnabled(true);
        }
    }
    
    @Action
    public void closeApp(){
        dispose();
        KomunikatorApp.getApplication().show(KomunikatorApp.getApplication().getMainFrame());
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        menuBar1 = new java.awt.MenuBar();
        menu1 = new java.awt.Menu();
        menu2 = new java.awt.Menu();
        jPopupMenu1 = new javax.swing.JPopupMenu();
        canvas1 = new java.awt.Canvas();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jComboBox1 = new javax.swing.JComboBox();
        logoutButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        javax.swing.JMenuItem addContact = new javax.swing.JMenuItem();
        startChat = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JSeparator();
        closeChat = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        clearArchives = new javax.swing.JMenuItem();
        showArchives = new javax.swing.JMenuItem();

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance(org.netbeans.zp.gui.KomunikatorApp.class).getContext().getResourceMap(RealApp.class);
        menu1.setLabel(resourceMap.getString("menu1.label")); // NOI18N
        menuBar1.add(menu1);

        menu2.setLabel(resourceMap.getString("menu2.label")); // NOI18N
        menuBar1.add(menu2);

        jPopupMenu1.setName("jPopupMenu1"); // NOI18N
        jPopupMenu1.getAccessibleContext().setAccessibleParent(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle(resourceMap.getString("Form.title")); // NOI18N
        setMinimumSize(new java.awt.Dimension(400, 250));
        setName("Form"); // NOI18N
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        canvas1.setName("canvas1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        jList1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jList1.setName("jList1"); // NOI18N
        jList1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jList1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jList1);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Online", "Offline" }));
        jComboBox1.setName("jComboBox1"); // NOI18N

        logoutButton.setText(resourceMap.getString("logoutButton.text")); // NOI18N
        logoutButton.setName("logoutButton"); // NOI18N
        logoutButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                logoutButtonMouseClicked(evt);
            }
        });

        jLabel1.setText(resourceMap.getString("jLabel1.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jMenuBar1.setName("jMenuBar1"); // NOI18N

        jMenu1.setText(resourceMap.getString("jMenu1.text")); // NOI18N
        jMenu1.setName("jMenu1"); // NOI18N

        javax.swing.ActionMap actionMap = org.jdesktop.application.Application.getInstance(org.netbeans.zp.gui.KomunikatorApp.class).getContext().getActionMap(RealApp.class, this);
        addContact.setAction(actionMap.get("showAddContactBox")); // NOI18N
        addContact.setIcon(resourceMap.getIcon("addContact.icon")); // NOI18N
        addContact.setText(resourceMap.getString("addContact.text")); // NOI18N
        addContact.setName("addContact"); // NOI18N
        addContact.setNextFocusableComponent(startChat);
        jMenu1.add(addContact);

        startChat.setAction(actionMap.get("showStartChatBox")); // NOI18N
        startChat.setIcon(resourceMap.getIcon("startChat.icon")); // NOI18N
        startChat.setText(resourceMap.getString("startChat.text")); // NOI18N
        startChat.setName("startChat"); // NOI18N
        jMenu1.add(startChat);

        jSeparator1.setName("jSeparator1"); // NOI18N
        jMenu1.add(jSeparator1);

        closeChat.setAction(actionMap.get("closeApp")); // NOI18N
        closeChat.setIcon(resourceMap.getIcon("closeChat.icon")); // NOI18N
        closeChat.setText(resourceMap.getString("closeChat.text")); // NOI18N
        closeChat.setName("closeChat"); // NOI18N
        jMenu1.add(closeChat);

        jMenuBar1.add(jMenu1);

        jMenu2.setText(resourceMap.getString("jMenu2.text")); // NOI18N
        jMenu2.setName("jMenu2"); // NOI18N

        clearArchives.setIcon(resourceMap.getIcon("clearArchives.icon")); // NOI18N
        clearArchives.setText(resourceMap.getString("clearArchives.text")); // NOI18N
        clearArchives.setName("clearArchives"); // NOI18N
        jMenu2.add(clearArchives);

        showArchives.setIcon(resourceMap.getIcon("showArchives.icon")); // NOI18N
        showArchives.setText(resourceMap.getString("showArchives.text")); // NOI18N
        showArchives.setName("showArchives"); // NOI18N
        jMenu2.add(showArchives);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(264, 264, 264)
                        .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(logoutButton)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(71, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(canvas1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(logoutButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(27, 27, 27))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void logoutButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_logoutButtonMouseClicked
        // TODO add your handling code here:
        //wyloguj
        XMPPClient.getInstance().disconnect();
        dispose();
        KomunikatorApp.getApplication().show(KomunikatorApp.getApplication().getMainFrame());
    }//GEN-LAST:event_logoutButtonMouseClicked

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        this.dispose();
        KomunikatorApp.getApplication().show(KomunikatorApp.getApplication().getMainFrame());

    }//GEN-LAST:event_formWindowClosing

    private void jList1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jList1MouseClicked
        // TODO add your handling code here:
        if ( evt.getButton() == evt.BUTTON2 ){
            if (selectedItemsNumber == 0)
                return;
            else if (selectedItemsNumber == 1){
                if (codeAndChatBox == null){
                    codeAndChatBox = new CodeAndChat(KomunikatorApp.getApplication().getMainFrame(), 
                            true, selectedItems.get(0), nickname);
                    codeAndChatBox.setLocationRelativeTo(this);
                    codeAndChatBox.setVisible(true);
                }
                else{
                    codeAndChatBox.dispose();
                    codeAndChatBox = null;
                    jList1MouseClicked(evt);
                }
            }
            else{
                if (codeAndConferenceBox == null){
                    try {
                        codeAndConferenceBox = new CodeAndConference(KomunikatorApp.getApplication().getMainFrame(), 
                                true, selectedItems, nickname);
                    } catch (XMPPException ex) {
                        Logger.getLogger(RealApp.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    codeAndConferenceBox.setLocationRelativeTo(this);
                    codeAndConferenceBox.setVisible(true);
                }
                else{
                    codeAndConferenceBox.dispose();
                    codeAndConferenceBox = null;
                    jList1MouseClicked(evt);
                }
            }
        }
    }//GEN-LAST:event_jList1MouseClicked

    /**
    * @param args the command line arguments
    */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private java.awt.Canvas canvas1;
    private javax.swing.JMenuItem clearArchives;
    private javax.swing.JMenuItem closeChat;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JList jList1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton logoutButton;
    private java.awt.Menu menu1;
    private java.awt.Menu menu2;
    private java.awt.MenuBar menuBar1;
    private javax.swing.JMenuItem showArchives;
    javax.swing.JMenuItem startChat;
    // End of variables declaration//GEN-END:variables
    
    private JDialog addContactBox;
    private JDialog startChatBox;
    private JDialog archivesBox;
    private JDialog codeBox;
    private JDialog codeAndChatBox;
    private JDialog codeAndConferenceBox;
    private JDialog ChatDlg;

    public void run() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }
}
