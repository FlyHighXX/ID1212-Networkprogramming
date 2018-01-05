/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.kth.id1212.chatapplication.common;

/**
 *
 * @author Diaco Uthman
 */
public enum MsgType {
    /**
     * Command is used when a new message is to be sent to the server
     */
    NEWMESSAGE,
    
    NEWUSER, 
    
    /**
     * When the server wants to broadcast something to all relevant clients, 
     *  then the BROADCAST command will be used.
     */
    BROADCAST,
    
    /**
     * When the server sends an username to the client, for printing purposes, this command is used.
     */
    SHOWUSER,
    
    JOINCONVERSATION,LEAVECONVERSATION
}
