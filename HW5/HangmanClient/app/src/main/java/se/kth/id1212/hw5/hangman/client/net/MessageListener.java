package se.kth.id1212.hw5.hangman.client.net;

import java.io.BufferedReader;
import java.io.IOException;

import se.kth.id1212.hw5.hangman.client.view.Command;
import se.kth.id1212.hw5.hangman.client.view.GameStarted;
import se.kth.id1212.hw5.hangman.client.view.MainActivity;
import se.kth.id1212.hw5.hangman.client.view.ServerMessage;

public class MessageListener extends Thread {
    
    private final BufferedReader input;
    private final boolean recieved;
    private final ServerMessage serverMessage;
    private Command msgtype;

    public MessageListener(ServerMessage serverMessage, BufferedReader input) {
        this.serverMessage = serverMessage;
        this.input = input;
        this.recieved = true;
    }

    public void setMsgtype(Command type){
        this.msgtype=type;
    }

    @Override
    public void run(){
        System.out.println("Messagelistener started!");
        try{
            while(recieved){
                handleMsg(input.readLine());
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleMsg(String message){
        if(recieved){
            serverMessage.handleMessage(message,this.msgtype);
        }
    }
}
