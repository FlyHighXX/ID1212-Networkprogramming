package se.kth.id1212.hw5.hangman.client.net;

import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.net.Socket;

import se.kth.id1212.hw5.hangman.client.view.Command;
import se.kth.id1212.hw5.hangman.client.view.GameStarted;

public class ServerConnection implements Serializable {
    private GameStarted game;
    private final int port;
    private final String ipAddress;
    private Socket socket;
    private String username;
    private PrintWriter output;
    private BufferedReader input;

    public ServerConnection(String ipAddress, int port, String username){
        this.username=username;
        this.ipAddress=ipAddress;
        this.port=port;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress(ipAddress, port), 30000);
            socket.setSoTimeout(180000);
            output = new PrintWriter(socket.getOutputStream());
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getUsername() {
        return username;
    }

    public void sendMessage(String message){
        output.println(message);
        output.flush();
    }

    public BufferedReader getInput(){
        return input;
    }

    public void startNewGame(GameStarted game) {
        this.game.messageListener.setMsgtype(Command.START);
        sendMessage(Command.START.toString());
    }

    public BufferedReader getOutputStream(){
        return input;
    }


    public void requestGameInfo() {
        this.game.messageListener.setMsgtype(Command.GAMEINFO);
        sendMsg(Command.GAMEINFO.toString());
    }

    public void makeNewGuess(EditText guessEditText) {
        this.game.messageListener.setMsgtype(Command.GUESS);
        sendMsg(Command.GUESS.toString(), guessEditText.getText().toString());
    }

    private void sendMsg(String... parts) {
        StringBuilder joiner = new StringBuilder();
        for (String part : parts) {
            joiner.append(part);
            joiner.append("##");
        }
        sendMessage(joiner.toString());
    }

    public void setGameContext(GameStarted gameContext) {
        this.game = gameContext;
    }

    public void quitGame() {
        sendMsg(Command.QUIT.toString());
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}