/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.netbeans.zp.client;

import org.netbeans.zp.message.Message;

/**
 *
 * @author Bartłomiej Hyży <hyzy.bartlomiej at gmail.com>
 */
public interface ClientMessageListener {

  public void handle(Message message);

}
