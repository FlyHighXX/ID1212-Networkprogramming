package client.net;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.StringJoiner;
import common.MsgType;

/**
 * Manages all communication with the server.
 */
public class ServerConnection {
    private static final int TIMEOUT_HALF_HOUR = 1800000;
    private static final int TIMEOUT_HALF_MINUTE = 30000;
    private Socket socket;
    private PrintWriter toServer;
    private BufferedReader fromServer;
    private volatile boolean connected;

    /**
     * Creates a new instance and connects to the specified server. Also starts a listener thread
     * receiving broadcast messages from server.
     *
     * @param host             Host name or IP address of server.
     * @param port             Server's port number.
     * @param out
     * @throws IOException If failed to connect.
     */
    public void connect(String host, int port, OutputHandler out) throws
            IOException {
        socket = new Socket();
        socket.connect(new InetSocketAddress(host, port), TIMEOUT_HALF_MINUTE);
        socket.setSoTimeout(TIMEOUT_HALF_HOUR);
        connected = true;
        toServer = new PrintWriter(socket.getOutputStream(), true);
        fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        new Thread(new Listener(out)).start();
    }

    /**
     * Closes the connection with the server and stops the broadcast listener thread.
     *
     * @throws IOException If failed to close socket.
     */
    public void disconnect() throws IOException {
        sendMsg(MsgType.DISCONNECT.toString());
        socket.close();
        socket = null;
        connected = false;
    }

    public void startNewGame() {
        sendMsg(MsgType.START.toString());
    }

    private void sendMsg(String... parts) {
        StringJoiner joiner = new StringJoiner("##");
        for (String part : parts) {
            joiner.add(part);
        }
        toServer.println(joiner.toString());
    }
    /**
     * Sends a guess to the server. The server will return whether the guess was right or not.
     * @param userInput - The input from the user.
     */
    public void guessingLetter(String userInput) {
        sendMsg(MsgType.GUESS.toString(),userInput);
    }

    public void getGameInfo() {
        sendMsg(MsgType.GAMEINFO.toString());
    }

    private class Listener implements Runnable {
        private final OutputHandler out;
        public Listener(OutputHandler out){
            this.out=out;
        }
        @Override
        public void run() {
            try {
                for (;;) {
                    String mes = fromServer.readLine();
                    this.out.handleMsg(mes);
                }
            } catch (IOException connectionFailure) {
                if (connected) {
                    this.out.handleMsg("Lost connection.");
                }
            }
        }
    }
}