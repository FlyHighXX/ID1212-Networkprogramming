package se.kth.id1212.websocketapplication.client.startup;

import se.kth.id1212.websocketapplication.client.view.ChatClient;

/**
 *
 * @author Diaco Uthman
 */
public class ClientStartUp {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        new ChatClient().start();
    }
    
}
