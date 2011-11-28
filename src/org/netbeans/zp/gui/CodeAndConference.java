/*
 * CodeAndConference.java
 *
 * Created on 2011-11-22, 10:27:21
 */
package org.netbeans.zp.gui;

import java.awt.Color;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileSystemView;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.netbeans.zp.client.ClientMessageListener;
import org.netbeans.zp.client.XMPPClient;
import org.netbeans.zp.message.CursorPositionedMessage;
import org.netbeans.zp.message.GroupMessage;
import org.netbeans.zp.message.Message;
import org.netbeans.zp.message.MessageType;
import org.netbeans.zp.message.NewSourceAddedMessage;
import org.netbeans.zp.message.SelectionMadeMessage;
import org.netbeans.zp.message.SourceCodeInsertedMessage;
import org.netbeans.zp.message.SourceCodeRemovedMessage;
import jsyntaxpane.DefaultSyntaxKit;

/**
 *
 * @author Tomek
 */
public class CodeAndConference extends javax.swing.JDialog implements ClientMessageListener, Runnable {

  private MultiUserChat collaboration = null;
  private ArrayList<String> f;
  private String n;
  private String roomID;
  private ArrayList<JEditorPane> editorsList;
  private int filesNumber = 0;
  private boolean isSaved = false;
  private boolean hasName = false;
  private boolean action = false;
  private String fileName;
  private String path;
  private File file;
  private JDialog chatBox;
  private JFileChooser fileToChoose;
  private DocumentListener dlist = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
            }
            
            public void removeUpdate(DocumentEvent e) {
            }
 
            public void insertUpdate(DocumentEvent e) {
                if (path!=null && action==false) {
                      editorArea1.setCaretColor(Color.red);
                      SourceCodeInsertedMessage msg = new  SourceCodeInsertedMessage();
                      msg.FileID = fileName;
                      msg.InsertPosition = 0;
                      for (JEditorPane item : editorsList) {
                        if (item.getName() == null ? fileName == null : item.getName().equals(path+fileName)) {
                         msg.InsertedCode = item.getText();
                          break;
                        }
                      }
                      msg.UserID = n;
                      try {
                        XMPPClient.getInstance().sendCodeMessage(collaboration, msg);
                      } catch (XMPPException ex) {
                        Logger.getLogger(CodeAndChat.class.getName()).log(Level.SEVERE, null, ex);
                      }
                  }  
                  else if(action==false) {
                      editorArea1.setCaretColor(Color.red);
                      SourceCodeInsertedMessage msg = new  SourceCodeInsertedMessage();
                      msg.FileID = fileName + "_d";
                      msg.InsertPosition = 0;
                      for (JEditorPane item : editorsList) {
                        if (item.getName() == null ? fileName == null : item.getName().equals(fileName)) {
                         msg.InsertedCode = item.getText();
                          break;
                        }
                      }
                      msg.UserID = n;
                      try {
                        XMPPClient.getInstance().sendCodeMessage(collaboration, msg);
                      } catch (XMPPException ex) {
                        Logger.getLogger(CodeAndChat.class.getName()).log(Level.SEVERE, null, ex);
                      }
                    }
                    action=false;
                    editorArea1.setCaretColor(Color.black);
                }
            };
  
  
  private InputMethodListener imlNS = new InputMethodListener() {

    public void inputMethodTextChanged(InputMethodEvent event) {
      NewSourceAddedMessage msg = null;
      msg.FileDirectory = path;
      msg.FileName = fileName;
      for (JEditorPane item : editorsList) {
        if (item.getName() == null ? fileName == null : item.getName().equals(fileName)) {
          msg.SourceCode = item.getText();
          break;
        }
      }
      msg.UserID = n;
      try {
        XMPPClient.getInstance().sendCodeMessage(collaboration, msg);
      } catch (XMPPException ex) {
        Logger.getLogger(CodeAndChat.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

    public void caretPositionChanged(InputMethodEvent event) {
      CursorPositionedMessage msg = null;
      msg.FileID = editorArea1.getName();
      msg.CursorPosition = editorArea1.getCaretPosition();
      msg.UserID = n;
      editorArea1.addInputMethodListener(null);
      try {
        XMPPClient.getInstance().sendCodeMessage(collaboration, msg);
      } catch (XMPPException ex) {
        Logger.getLogger(CodeAndChat.class.getName()).log(Level.SEVERE, null, ex);
      }
    }
  };

  /** Creates new form CodeAndConference */
  public CodeAndConference(java.awt.Frame parent, boolean modal,
          String nickname, String roomName, boolean joinRoom) throws XMPPException {
    super(parent, modal);
    initComponents();
    editorsList = new ArrayList();
    n = nickname;
    if ( joinRoom ) { 
        collaboration = XMPPClient.getInstance().joinCollaboration(roomName, n);
    } else {
        collaboration = XMPPClient.getInstance().createCollaboration(roomName, n);
    }
    XMPPClient.getInstance().addMessageListener(this);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        chatArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        friendsList = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        msgArea = new javax.swing.JTextArea();
        sendBtn = new javax.swing.JButton();
        saveBtn = new javax.swing.JButton();
        chooseFileBtn = new javax.swing.JButton();
        sendPM = new javax.swing.JButton();
        commentBtn = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        editorArea1 = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        jScrollPane2.setName("jScrollPane2"); // NOI18N

        chatArea.setColumns(20);
        chatArea.setRows(5);
        chatArea.setName("chatArea"); // NOI18N
        jScrollPane2.setViewportView(chatArea);

        jScrollPane3.setName("jScrollPane3"); // NOI18N

        friendsList.setName("friendsList"); // NOI18N
        jScrollPane3.setViewportView(friendsList);

        jScrollPane4.setName("jScrollPane4"); // NOI18N

        msgArea.setColumns(20);
        msgArea.setRows(5);
        msgArea.setName("msgArea"); // NOI18N
        jScrollPane4.setViewportView(msgArea);

        org.jdesktop.application.ResourceMap resourceMap = org.jdesktop.application.Application.getInstance().getContext().getResourceMap(CodeAndConference.class);
        sendBtn.setText(resourceMap.getString("sendBtn.text")); // NOI18N
        sendBtn.setName("sendBtn"); // NOI18N
        sendBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendBtnMouseClicked(evt);
            }
        });
        sendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                none(evt);
            }
        });

        saveBtn.setText(resourceMap.getString("saveBtn.text")); // NOI18N
        saveBtn.setName("saveBtn"); // NOI18N
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });

        chooseFileBtn.setText(resourceMap.getString("chooseFileBtn.text")); // NOI18N
        chooseFileBtn.setName("chooseFileBtn"); // NOI18N
        chooseFileBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                chooseFileBtnMouseClicked(evt);
            }
        });

        sendPM.setText(resourceMap.getString("sendPM.text")); // NOI18N
        sendPM.setName("sendPM"); // NOI18N
        sendPM.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sendPMMouseClicked(evt);
            }
        });

        commentBtn.setText(resourceMap.getString("commentBtn.text")); // NOI18N
        commentBtn.setName("commentBtn"); // NOI18N
        commentBtn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                commentBtnMouseClicked(evt);
            }
        });

        jTabbedPane1.setName("jTabbedPane1"); // NOI18N

        jScrollPane1.setName("jScrollPane1"); // NOI18N

        editorArea1.setName("editorArea1"); // NOI18N
        editorArea1.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                editorArea1CaretPositionChanged(evt);
            }
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
                editorArea1InputMethodTextChanged(evt);
            }
        });
        jScrollPane1.setViewportView(editorArea1);

        jTabbedPane1.addTab(resourceMap.getString("jScrollPane1.TabConstraints.tabTitle"), jScrollPane1); // NOI18N

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chooseFileBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 336, Short.MAX_VALUE)
                        .addComponent(saveBtn))
                    .addComponent(jTabbedPane1))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(commentBtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                        .addComponent(sendBtn))
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(sendPM, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE))
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 365, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendBtn)
                    .addComponent(saveBtn)
                    .addComponent(chooseFileBtn)
                    .addComponent(sendPM)
                    .addComponent(commentBtn))
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void none(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_none
      // TODO add your handling code here:
    }//GEN-LAST:event_none

    private void chooseFileBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_chooseFileBtnMouseClicked
      // TODO add your handling code here:
    //  editorArea1.addInputMethodListener(imlNS);
      editorArea1.getDocument().addDocumentListener(dlist);
      if (fileToChoose == null) {
        fileToChoose = new JFileChooser();
        fileToChoose.showOpenDialog(this);
        fileToChoose.setVisible(true);
        file = fileToChoose.getSelectedFile();
        path = file.getPath();
        fileName = file.getName();
        FileReader fr;
        BufferedReader br;
        String s, s2;
        s = "";
        try {
          fr = new FileReader(path);
          br = new BufferedReader(fr);
          try {
            while ((s2 = br.readLine()) != null) {
              s = s + s2;
            }
            fr.close();
            filesNumber++;
          } catch (IOException ex) {
            Logger.getLogger(CodeAndConference.class.getName()).log(Level.SEVERE, null, ex);
          }
        } catch (FileNotFoundException ex) {
          Logger.getLogger(CodeAndConference.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (filesNumber == 1) {    
          DefaultSyntaxKit.initKit();
          editorArea1.setContentType("text/java");  
          editorArea1.setText(s);
          jTabbedPane1.setTitleAt(0, fileName);
          editorArea1.setName(path+fileName);
          editorsList.add(editorArea1);
          editorArea1.addInputMethodListener(imlNS);
          editorArea1.getDocument().addDocumentListener(dlist);

          NewSourceAddedMessage msg = new NewSourceAddedMessage();
          msg.FileDirectory = path;
          msg.FileName = fileName;

          for (JEditorPane item : editorsList) {   
                if (item.getName() == null ? fileName == null : item.getName().equals(path+fileName)) {
                  msg.SourceCode = item.getText();
                  break;
                }
              }
          msg.UserID = n;
          try {
            XMPPClient.getInstance().sendCodeMessage(collaboration, msg);
          } catch (XMPPException ex) {
            Logger.getLogger(CodeAndChat.class.getName()).log(Level.SEVERE, null, ex);
          }
        } else { 
          JScrollPane jScrollPane = new JScrollPane();
          JEditorPane editorArea = new JEditorPane();
          editorArea.setText(s);
          jScrollPane.setViewportView(editorArea);
          jTabbedPane1.addTab(fileName, jScrollPane);

          jScrollPane.setName("jScrollPane" + filesNumber);
          editorArea.setName(fileName);
          editorArea.addInputMethodListener(imlNS);
          editorsList.add(editorArea);
        }
      } else {
        fileToChoose = null;
        chooseFileBtnMouseClicked(evt);
      }
    }//GEN-LAST:event_chooseFileBtnMouseClicked

    private void sendBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendBtnMouseClicked
      // TODO add your handling code here:
      String msg = n + "\n" + msgArea.getText();
      try {
        XMPPClient.getInstance().sendChatMessage(collaboration, msg);
      } catch (XMPPException ex) {
        Logger.getLogger(CodeAndChat.class.getName()).log(Level.SEVERE, null, ex);
      }
    }//GEN-LAST:event_sendBtnMouseClicked

    private void commentBtnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_commentBtnMouseClicked
      // TODO add your handling code here:
      String comment = "\n\"" + editorArea1.getSelectedText() + "\"\n";
      msgArea.setText(comment);
    }//GEN-LAST:event_commentBtnMouseClicked

    private void sendPMMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sendPMMouseClicked
      // TODO add your handling code here:
      chatBox = new Chat(KomunikatorApp.getApplication().getMainFrame(), true,
              n, friendsList.getSelectedValue().toString());
      chatBox.setTitle(friendsList.getSelectedValue().toString());
    }//GEN-LAST:event_sendPMMouseClicked

private void editorArea1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_editorArea1InputMethodTextChanged
// TODO add your handling code here:
    
}//GEN-LAST:event_editorArea1InputMethodTextChanged

private void editorArea1CaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_editorArea1CaretPositionChanged
// TODO add your handling code here:
}//GEN-LAST:event_editorArea1CaretPositionChanged

private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
// TODO add your handling code here:
    if (path!=null) {
        String code = editorsList.get(jTabbedPane1.getSelectedIndex()).getText();
        FileOutputStream fileOutput = null;
            try {
                fileOutput = new FileOutputStream(path);
                byte [] buf = code.getBytes();
                fileOutput.write( buf, 0, buf.length );
                fileOutput.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CodeAndConference.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CodeAndConference.class.getName()).log(Level.SEVERE, null, ex);
            }   
    } else {
        String code = editorsList.get(jTabbedPane1.getSelectedIndex()-1).getText();
        JFileChooser fr = new JFileChooser();
        FileSystemView fw = fr.getFileSystemView();
        FileOutputStream fileOutput = null;
            try {
                fileOutput = new FileOutputStream(fw.getDefaultDirectory()+"\\"+editorsList.get(jTabbedPane1.getSelectedIndex()-1).getName());
                byte [] buf = code.getBytes();
                fileOutput.write( buf, 0, buf.length );
                fileOutput.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(CodeAndConference.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(CodeAndConference.class.getName()).log(Level.SEVERE, null, ex);
            }   
    }
}//GEN-LAST:event_saveBtnActionPerformed
  /**
   * @param args the command line arguments
   */
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea chatArea;
    private javax.swing.JButton chooseFileBtn;
    private javax.swing.JButton commentBtn;
    private javax.swing.JEditorPane editorArea1;
    private javax.swing.JList friendsList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea msgArea;
    private javax.swing.JButton saveBtn;
    private javax.swing.JButton sendBtn;
    private javax.swing.JButton sendPM;
    // End of variables declaration//GEN-END:variables

    @Override
  public void handle(Message message) {
    if (!message.UserID.equals(n)) {
        if (message.getType() == MessageType.CursorPositioned) {
          CursorPositionedMessage msg = (CursorPositionedMessage) message;
          for (JEditorPane item : editorsList) {
            if (item.getName() == null ? msg.FileID == null : item.getName().equals(msg.FileID)) {
              item.setCaretPosition(msg.CursorPosition);
              break;
            }
          }
        } else if (message.getType() == MessageType.GroupMessage) {
          GroupMessage msg = (GroupMessage) message;
          chatArea.append(msg.UserID + "\n" + msg.Body);
        } else if (message.getType() == MessageType.NewSourceAdded) {
         // DefaultSyntaxKit.initKit();
          NewSourceAddedMessage msg = (NewSourceAddedMessage) message;
          filesNumber++;
          JScrollPane jScrollPane = new JScrollPane();
          JEditorPane editorArea = new JEditorPane();
          editorArea.setText(msg.SourceCode);

          jScrollPane.setViewportView(editorArea);
          jTabbedPane1.addTab(msg.FileName, jScrollPane);
          jScrollPane.setName("jScrollPane" + filesNumber);
          editorArea.setName(msg.FileName);
          fileName = msg.FileName;
          path = msg.FileDirectory;
          this.path = null;
          editorsList.add(editorArea);
          //editorArea.addInputMethodListener(imlNS);
          editorArea.getDocument().addDocumentListener(dlist);



        } else if (message.getType() == MessageType.SelectionMade) {
          SelectionMadeMessage msg = (SelectionMadeMessage) message;
          for (JEditorPane item : editorsList) {
            if (item.getName() == null ? msg.FileID == null : item.getName().equals(msg.FileID)) {
              item.setSelectionStart(msg.SelectionStart);
              item.setSelectionEnd(msg.SelectionStart + msg.SelectionLength);
              break;
            }
          }
        } else if (message.getType() == MessageType.SourceCodeInserted) {

          if (path!=null) {
              SourceCodeInsertedMessage msg = (SourceCodeInsertedMessage) message;
              msg.FileID.substring(0, msg.FileID.length()-2);
              for (JEditorPane item : editorsList) {
                if (item.getName() == null ? msg.FileID == null : item.getName().equals(path+msg.FileID.substring(0, msg.FileID.length()-2))) {
                  action = true;
                  item.setCaretPosition(msg.InsertPosition);
                  item.setText(msg.InsertedCode);
                  break;
                }
              }
          }  else {
          SourceCodeInsertedMessage msg = (SourceCodeInsertedMessage) message;
          for (JEditorPane item : editorsList) {
            if (item.getName() == null ? msg.FileID == null : item.getName().equals(msg.FileID)) {
              action = true;
              item.setCaretPosition(msg.InsertPosition);
              item.setText(msg.InsertedCode);
              break;
            }
          }
          }

        } else if (message.getType() == MessageType.SourceCodeRemoved) {
          SourceCodeRemovedMessage msg = (SourceCodeRemovedMessage) message;
          for (JEditorPane item : editorsList) {

            if (item.getName() == null ? msg.FileID == null : item.getName().equals(msg.FileID)) {
              item.setSelectionStart(msg.RemovePosition);
              item.setSelectionEnd(msg.RemovePosition + msg.RemoveLength);
              item.setText("");
              break;
            }
          }
        }
    }
  }

  @Override
  public void run() {
  }
}
