package se.kth.id1212.websocketapplication.client.view;

import java.util.Arrays;
import java.util.Scanner;
import se.kth.id1212.websocketapplication.client.controller.Controller;
import se.kth.id1212.websocketapplication.client.net.OutputHandler;
import se.kth.id1212.websocketapplication.common.Message;
import se.kth.id1212.websocketapplication.common.MsgType;

/**
 *
 * @author Diaco Uthman
 */
public class ChatClient implements Runnable{
    private String userName;
    private boolean receivedCmds = false;
    private Controller contr;
    private final Scanner console = new Scanner(System.in);
    private final ThreadSafeStdOut outMgr = new ThreadSafeStdOut();
    
    public void start(){
        if(receivedCmds){
            return;
        }
        receivedCmds=true;
        
        contr = new Controller();
        new Thread(this).start();
    }
    
    @Override
    public void run() {
        welcomeUser();
        while(this.receivedCmds){
            //showMenu();
            try{
                String input = console.nextLine();
                String[] cmd = input.split(" ");
                Command ourCommand;
                try{
                    ourCommand = Command.valueOf(cmd[0].toUpperCase());
                }catch (Throwable failedToReadCmd) {
                    ourCommand = Command.NO_COMMAND;
                }
                
                switch(ourCommand){
                    default :
                        Message mes = new Message(MsgType.BROADCAST, input, this.userName);
                        System.out.println(mes.getBody());
                        contr.sendMessage(mes);
                }
            }catch (Exception e) {
                outMgr.println("Operation failed");
                System.err.println(e);
            }
        }
    }

    private void welcomeUser() {
        this.outMgr.println("---- Welcome to quickchat-service ----");
        this.outMgr.println("-- Enter your username please --");
        this.userName = this.console.nextLine();
    }

    private void showMenu() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private class ConsoleOutput implements OutputHandler {
        @Override
        public void handleMsg(String msg) {
            outMgr.println((String) msg);
        }
    }
}
