package client.controller;

import client.net.OutputHandler;
import client.net.ServerConnection;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 *
 * @author Diaco Uthman
 */
public class Controller {
    private final ServerConnection serverConnection = new ServerConnection();
    public void disconnect() {
        try {
            serverConnection.disconnect();
        }catch(IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
    }

    public void connect(String host, int port, OutputHandler out) {
    try {
            serverConnection.connect(host, port, out);
    }catch(IOException ioe) {
        throw new UncheckedIOException(ioe);
    }
    out.handleMsg("Connected to " + host + ":" + port);
    }

    public void guessingLetter(String userInput) {
        serverConnection.guessingLetter(userInput);
    }
    
    public void startNewGame(){
        this.serverConnection.startNewGame();
    }

    public void getGameInfo() {
        this.serverConnection.getGameInfo();
    }
    
}
