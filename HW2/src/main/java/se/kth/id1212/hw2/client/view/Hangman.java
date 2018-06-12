package se.kth.id1212.hw2.client.view;

import io.netty.channel.ChannelHandlerContext;
import java.util.Scanner;
import se.kth.id1212.hw2.client.net.ServerConnection;

/**
 *
 * @author Diaco Uthman
 */
public class Hangman implements Runnable{
    private final Scanner console = new Scanner(System.in);
    private boolean receivingCmds = false;
    private final ThreadSafeStdOut outMgr = new ThreadSafeStdOut();
    private final ServerConnection serverConnection;
    
    public Hangman(ChannelHandlerContext ctx)throws Exception{
        this.serverConnection = new ServerConnection(ctx);
    }

    public void start() {
        if(this.receivingCmds){
           return; 
        }
        this.receivingCmds=true;
    }

    @Override
    public void run() {
        start();
        while(this.receivingCmds){
            System.out.println("---- HANGMAN MENU ----");
            System.out.println("---- Write one of the following commands, followed by required arguments ----");
            System.out.println("---- start, guess, gameinfo, quit ----");
            try{
                String[] cmd = readNextLine().split(" ");
                Command ourCommand;
                try{
                    ourCommand = Command.valueOf(cmd[0].toUpperCase());
                }catch (Throwable failedToReadCmd) {
                    ourCommand = Command.NO_COMMAND;
                }
                
                switch(ourCommand){
                    case QUIT:
                        this.serverConnection.disconnect();
                        this.receivingCmds=false;
                        break;
                    case START :
                        this.serverConnection.startNewGame();
                        break;
                    case GUESS :
                        this.serverConnection.newGuess(cmd[1]);
                        break;
                    case GAMEINFO :
                        this.serverConnection.gameInfo();
                        break;
                    default :
                        outMgr.println("Enter a correct command");
                }
            }catch (Exception e) {
                outMgr.println("Operation failed");
                System.err.println(e);
            }
        }
    }
    private String readNextLine() {
        return console.nextLine();
    }
}
