package se.kth.id1212.chatapplication.client.net;

import se.kth.id1212.chatapplication.common.Message;

/**
 * Handles broadcast messages from server.
 */
public interface OutputHandler {

    /**
     * prints a message
     * @param msg - The message instance
     */
    public void printMessage(Message msg);

    /**
     * Prints a username
     * @param msg - The message instance
     */
    public void printUsername(Message msg);
}