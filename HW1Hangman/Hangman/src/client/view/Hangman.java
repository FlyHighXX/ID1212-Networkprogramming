/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.view;

import client.controller.Controller;
import client.net.OutputHandler;
import java.util.Scanner;

/**
 *
 * @author Diaco Uthman
 */
public class Hangman implements Runnable {
    private static final String PROMPT = "> ";
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
            try{
                CmdLine cmdLine = new CmdLine(readNextLine());
                switch(cmdLine.getCmd()){
                    case QUIT:
                        this.receivingCmds=false;
                        contr.disconnect();
                        break;

                    case CONNECT :
                        contr.connect(cmdLine.getParameter(0),
                                    Integer.parseInt(cmdLine.getParameter(1)),
                                    new ConsoleOutput());
                        break;
                    case USER :
                        contr.sendUsername(cmdLine.getParameter(0));
                        break;
                    default :
                        contr.sendMsg(cmdLine.getUserInput());
                }
            }catch (Exception e) {
            outMgr.println("Operation failed");
            System.err.println(e);
            }
        }
    }
    
    private String readNextLine() {
        outMgr.print(PROMPT);
        return console.nextLine();
    }
    
    private class ConsoleOutput implements OutputHandler {
        @Override
        public void handleMsg(String msg) {
            outMgr.println((String) msg);
            outMgr.print(PROMPT);
        }
    }
}
