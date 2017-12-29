package client.view;

import client.controller.Controller;
import client.net.OutputHandler;
import java.util.Scanner;

/**
 *
 * @author Diaco Uthman
 */
public class Hangman implements Runnable {
    private Controller contr;
    private final Scanner console = new Scanner(System.in);
    private boolean receivingCmds = false;
    private final ThreadSafeStdOut outMgr = new ThreadSafeStdOut();
    
    public void start() {
        if(this.receivingCmds){
           return; 
        }
        this.receivingCmds=true;
        contr = new Controller();
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(this.receivingCmds){
            System.out.println("---- HANGMAN MENU ----");
            System.out.println("---- Write one of the following commands, followed by required arguments ----");
            System.out.println("---- connect, start, guess, gameinfo, quit ----");
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
                        this.receivingCmds=false;
                        contr.disconnect();
                        break;

                    case CONNECT :
                        contr.connect(cmd[1],
                                    Integer.parseInt(cmd[2]),new ConsoleOutput());
                        break;
                    case START :
                        contr.startNewGame();
                        break;
                    case GUESS :
                        contr.guessingLetter(cmd[1]);
                        break;
                    case GAMEINFO :
                        contr.getGameInfo();
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
    
    private class ConsoleOutput implements OutputHandler {
        @Override
        public void handleMsg(String msg) {
            outMgr.println((String) msg);
        }
    }
}
