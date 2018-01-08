package server.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UncheckedIOException;
import java.net.Socket;
import java.util.StringJoiner;
import common.MsgType;
import server.controller.Controller;

/**
 * Handles all communication with one particular chat client.
 */
class ClientHandler implements Runnable {
    private final Controller contr = new Controller();
    private final HangmanServer server;
    private final Socket clientSocket;
    private BufferedReader fromClient;
    private PrintWriter toClient;
    private boolean connected;

    /**
     * Creates a new instance, which will handle communication with one specific client connected to
     * the specified socket.
     *
     * @param clientSocket The socket to which this handler's client is connected.
     */
    ClientHandler(HangmanServer server, Socket clientSocket) {
        this.server = server;
        this.clientSocket = clientSocket;
        connected = true;
    }

    /**
     * The run loop handling all communication with the connected client.
     */
    @Override
    public void run() {
        try {
            fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toClient = new PrintWriter(clientSocket.getOutputStream(), true);
        } catch (IOException ioe) {
            throw new UncheckedIOException(ioe);
        }
        while (connected) {
            try {
                Message msg = new Message(fromClient.readLine());
                switch (msg.msgType) {
                    case GAMEINFO :
                        if(contr.currentGame()){
                            this.toClient.println("Current score: " + server.getScore(contr) + 
                                "\nCurrent remaining attempts: " + server.getRemainingAttempts(contr));
                        }else{
                            this.toClient.println("Current score: " + server.getScore(contr) + 
                                "\nThere is no current game");
                        }
                        break;
                    case START:
                        server.newGame(contr);
                        this.toClient.println(server.getUpdatedWord(contr));
                        break;
                    case GUESS:
                        if(!server.currentGame(contr)){
                            this.toClient.println("There is no current game. Start a new one by running the start command.");
                            continue;
                        }
                        try{
                            server.guessWord(msg.msgBody[0], contr);
                        }catch(NullPointerException e){
                            continue;
                        }
                        if(server.getRemainingAttempts(contr)==0){
                            this.toClient.println("Game over");
                            this.server.endGame(contr);
                            continue;
                        }
                        if(server.checkWord(contr)){
                            this.toClient.println("YOU'VE WON");
                            continue;
                        }
                        this.toClient.println(server.getUpdatedWord(contr));
                        break;
                    case DISCONNECT:
                        disconnectClient();
                        this.toClient.println("Client has left the game");
                        break;
                    default:
                        throw new IOException("Received corrupt message: " + msg.receivedString);
                }
            } catch (IOException ioe) {
                disconnectClient();
                System.err.println(ioe);
            }
        }
    }

    /**
     * Sends the specified message to the connected client.
     *
     * @param msg The message to send.
     */
    void sendMsg(String msg) {
        StringJoiner joiner = new StringJoiner("##");
        joiner.add(MsgType.BROADCAST.toString());
        joiner.add(msg);
        toClient.println(joiner.toString());
    }

    private void disconnectClient() {
        
        try {
            clientSocket.close();
        } catch (IOException ex) {
            System.err.println("error");
        }
        
        connected = false;
    }

    private static class Message {
        private MsgType msgType;
        private String[] msgBody;
        private String receivedString;

        private Message(String receivedString) {
            parse(receivedString);
            this.receivedString = receivedString;
        }

        private void parse(String strToParse) {
            try {
                System.err.println(strToParse);
                String[] msgTokens = strToParse.split("##");
                this.msgType = MsgType.valueOf(msgTokens[0].toUpperCase());
                if(msgTokens.length<2){
                    this.msgBody = null;
                    return;
                }
                
                String tempStr = msgTokens[1];
                this.msgBody = tempStr.split(" ");
            } catch (Throwable throwable) {
                System.err.println(throwable);
            }
        }
    }
}