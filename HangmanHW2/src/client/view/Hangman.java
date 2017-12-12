package client.view;

import client.net.ServerConnection;
import java.util.Scanner;
import client.net.CommunicationHandler;
import java.net.InetSocketAddress;

/**
 *
 * @author Diaco Uthman
 */
public class Hangman implements Runnable {
    private final Scanner console = new Scanner(System.in);
    private boolean receivingCmds = false;
    private final ThreadSafeStdOut outMgr = new ThreadSafeStdOut();
    private ServerConnection server;
    
    public void start() {
        if(this.receivingCmds){
           return; 
        }
        this.receivingCmds=true;
        server = new ServerConnection();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(this.receivingCmds){
            try{
                String[] cmd = readNextLine().split(" ");
                Command ourCommand;
                try{
                    ourCommand = Command.valueOf(cmd[0].toUpperCase());
                }catch (Throwable failedToReadCmd) {
                    ourCommand = Command.NO_COMMAND;
                }
                switch(ourCommand){
                    case CONNECT :
                        server.connect(cmd[1],
                                    Integer.parseInt(cmd[2]));
                        break;
                    default :
                        outMgr.println("Enter a correct command");
                }
                /*switch(ourCommand){
                    case QUIT:
                        this.receivingCmds=false;
                        contr.disconnect();
                        break;

                    case CONNECT :
                        server.connect(cmd[1],
                                    Integer.parseInt(cmd[2]));
                        break;
                    case START :
                        contr.startNewGame();
                        break;
                    case GUESS :
                        contr.guessingLetter(cmd[1]);
                        break;
                    case GAMEINFO :
                        contr.getGameInfo();
                    default :
                        outMgr.println("Enter a correct command");
                }*/
            }catch (Exception e) {
                outMgr.println("Operation failed");
                System.err.println(e);
            }
        }
    }
    
    private String readNextLine() {
        return console.nextLine();
    }
    
    private class ConsoleOutput implements CommunicationHandler {
        @Override
        public void receivedMsg(String msg) {
            printToConsole(msg);
        }

        @Override
        public void connected(InetSocketAddress serverAddress) {
            printToConsole("Connected to " + serverAddress.getHostName() + ":"
                           + serverAddress.getPort());
        }

        @Override
        public void disconnected() {
            printToConsole("Disconnected from server.");
        }

        private void printToConsole(String msg) {
            outMgr.println((String) msg);
        }
    }
}
