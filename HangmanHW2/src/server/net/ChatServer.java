package server.net;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import server.controller.Controller;

/**
 * Receives chat messages and broadcasts them to all chat clients. All communication to/from any
 * chat node pass this server.
 */
public class ChatServer {
    private static final int LINGER_TIME = 5000;
    private static final int TIMEOUT_HALF_HOUR = 1800000;
    private int portNo = 8080;

    /**
     * @param args Takes one command line argument, the number of the port on which the server will
     *             listen, the default is <code>8080</code>.
     */
    public static void main(String[] args) {
        ChatServer server = new ChatServer();
        server.parseArguments(args);
        server.serve();
    }
    /**
     * Sends the specified message to all connected clients
     *
     * @param msg The message to broadcast.
     */
    void guessWord(String msg, Controller contr) {
        try{
            contr.newGuess(msg);
        }
        catch (IOException e){
            System.err.println(e);
        }
    }

    private void serve() {
        try {
            ServerSocket listeningSocket = new ServerSocket(portNo);
            while (true) {
                Socket clientSocket = listeningSocket.accept();
                startHandler(clientSocket);
            }
        } catch (IOException e) {
            System.err.println("Server failure.");
        }
    }

    private void startHandler(Socket clientSocket) throws SocketException {
        clientSocket.setSoLinger(true, LINGER_TIME);
        clientSocket.setSoTimeout(TIMEOUT_HALF_HOUR);
        ClientHandler handler = new ClientHandler(this, clientSocket);
        Thread handlerThread = new Thread(handler);
        handlerThread.setPriority(Thread.MAX_PRIORITY);
        handlerThread.start();
    }

    private void parseArguments(String[] arguments) {
        if (arguments.length > 0) {
            try {
                portNo = Integer.parseInt(arguments[1]);
            } catch (NumberFormatException e) {
                System.err.println("Invalid port number, using default.");
            }
        }
    }

    String getUpdatedWord(Controller contr) {
        return contr.getWord();
    }

    void newGame(Controller contr) {
        contr.newGame();
    }

    int getScore(Controller contr) {
        return contr.getScore();
    }

    int getRemainingAttempts(Controller contr) {
        return contr.getRemainingAttempts();
    }

    void endGame(Controller contr) {
        contr.endGame();
    }

    boolean currentGame(Controller contr) {
        return contr.currentGame();
    }

    boolean checkWord(Controller contr) {
        return contr.checkWord();
    }
}