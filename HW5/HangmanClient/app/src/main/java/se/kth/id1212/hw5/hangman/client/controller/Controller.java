package se.kth.id1212.hw5.hangman.client.controller;

import android.widget.EditText;

import java.io.BufferedReader;
import java.io.Serializable;

import se.kth.id1212.hw5.hangman.client.net.ServerConnection;
import se.kth.id1212.hw5.hangman.client.view.GameStarted;

public class Controller implements Serializable{
    private ServerConnection connection;

    public void connect(final String ipAddress, final String username, final int port) {
        /**CompletableFuture.runAsync(() ->{
            connection.connect(ipAdress,username,port);
        });**/
        connection = new ServerConnection(ipAddress,port,username);
    }

    public String getUsername() {
        return connection.getUsername();
    }

    public void startNewGame(GameStarted game) {
        connection.startNewGame(game);
    }

    public BufferedReader getOutputStream(){
        return connection.getOutputStream();
    }

    public void requestGameInfo() {
        connection.requestGameInfo();
    }

    public void makeNewGuess(EditText guessEditText) {
        connection.makeNewGuess(guessEditText);
    }

    public void setGameContext(GameStarted gameContext) {
        connection.setGameContext(gameContext);
    }

    public void quitGame() {
        connection.quitGame();
    }
}
