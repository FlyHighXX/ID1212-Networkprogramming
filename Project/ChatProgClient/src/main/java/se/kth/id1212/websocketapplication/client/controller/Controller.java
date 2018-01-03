package se.kth.id1212.websocketapplication.client.controller;

import se.kth.id1212.websocketapplication.client.net.OutputHandler;
import se.kth.id1212.websocketapplication.client.net.SocketConnection;
import se.kth.id1212.websocketapplication.common.Message;

/**
 *
 * @author Diaco Uthman
 */
public class Controller {
    private final SocketConnection serverConnection = new SocketConnection();

    public void sendMessage(Message mes) {
        serverConnection.sendMessage(mes);
        //serverConnection.sentTExt("hej");
    }

}
