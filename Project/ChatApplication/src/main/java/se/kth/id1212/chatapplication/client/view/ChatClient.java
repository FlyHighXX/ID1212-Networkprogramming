package se.kth.id1212.chatapplication.client.view;

import java.util.Scanner;
import se.kth.id1212.chatapplication.client.net.OutputHandler;
import se.kth.id1212.chatapplication.client.net.ServerConnection;
import se.kth.id1212.chatapplication.common.Message;

/**
 *
 * @author Diaco Uthman
 */
public class ChatClient implements Runnable {
    private boolean receivedCmds=false;
    private final Scanner console = new Scanner(System.in);
    private final SafeOutput outMgr = new SafeOutput();
    private final ServerConnection serverConnection = new ServerConnection(new ConsoleOutput());
    
    public void start(){
        if(this.receivedCmds){
            return;
        }
        this.receivedCmds=true;
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        welcomeUser();
        while(this.receivedCmds){
            int choice = showMenu();
            switch(choice){
                case 1 :
                    serverConnection.showUsers();
                    break;
                case 2 :
                    serverConnection.enterConversation();
                    this.outMgr.println("You are now in the chat. To exit, write quit");
                    boolean inChat=true;
                    while(inChat){
                        String input = this.console.nextLine();
                        if(input.equals("quit")){
                            inChat=false;
                        }
                        serverConnection.sendMessage(input);
                    }
                    serverConnection.leaveConversation();
                    break;
                default :
                    outMgr.println("Enter a correct command");
            }
        }
    }
    
    private void welcomeUser() {
        this.outMgr.println("---- Welcome to quickchat-service ----");
        this.outMgr.println("-- Enter your username please --");
        String userName = this.console.nextLine();
        this.serverConnection.setUsername(userName);
    }

    private int showMenu() {
        this.outMgr.println("---- Now you can choose what to do : ----");
        this.outMgr.println(" -- Press 1 to show current users -- ");
        this.outMgr.println(" -- Press 2 to enter a conversation  -- ");
        String choice = this.console.nextLine();
        return Integer.valueOf(choice);
    }
    
    private class ConsoleOutput implements OutputHandler {

        @Override
        public void printMessage(Message msg) {
            outMgr.println((String)msg.getSender() + ": "+(String) msg.getBody());
        }

        @Override
        public void printUsername(Message msg) {
            outMgr.println((String)msg.getSender());
        }
    }
}
