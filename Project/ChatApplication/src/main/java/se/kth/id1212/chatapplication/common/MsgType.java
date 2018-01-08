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
    
    /**
     * Command to add a new username to the server.
     */
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
    
    /**
     * When a user joins or leaves the conversation, the JOINCONVERSATION or LEAVECONVERSATION commands are used.
     */
    JOINCONVERSATION,LEAVECONVERSATION
}