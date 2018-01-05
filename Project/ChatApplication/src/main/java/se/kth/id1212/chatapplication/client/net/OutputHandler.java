package se.kth.id1212.chatapplication.client.net;

import se.kth.id1212.chatapplication.common.Message;

/**
 * Handles broadcast messages from server.
 */
public interface OutputHandler {
    /**
     * Called when a broadcast message from the server has been received. That message originates
     * from one of the clients.
     *
     * @param msg The message from the server.
     */
    public void printMessage(Message msg);
    public void printUsername(Message msg);
}