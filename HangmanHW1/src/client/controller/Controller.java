package client.controller;

import client.net.OutputHandler;
import client.net.ServerConnection;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Diaco Uthman
 */
public class Controller {
    private final ServerConnection serverConnection = new ServerConnection();
    public void disconnect() {
        CompletableFuture.runAsync(() ->{
            try {
                serverConnection.disconnect();
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    public void connect(String host, int port, OutputHandler out) {
        CompletableFuture.runAsync(() -> {
            try {
                serverConnection.connect(host, port, out);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        out.handleMsg("Connected to " + host + ":" + port);
    }

    public void guessingLetter(String userInput) {
        CompletableFuture.runAsync(() -> this.serverConnection.guessingLetter(userInput));
    }
    
    public void startNewGame(){
        CompletableFuture.runAsync(() -> this.serverConnection.startNewGame());
    }

    public void getGameInfo() {
        CompletableFuture.runAsync(() -> this.serverConnection.getGameInfo()); 
    }
}
