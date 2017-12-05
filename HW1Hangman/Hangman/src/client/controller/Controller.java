/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client.controller;

import client.net.OutputHandler;
import client.net.ServerConnection;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 *
 * @author Diaco Uthman
 */
public class Controller {
    private final ServerConnection serverConnection = new ServerConnection();
    public void disconnect() {
        try {
            serverConnection.disconnect();
    }catch(IOException ioe) {
        throw new UncheckedIOException(ioe);
    }
    }

    public void connect(String host, int port, OutputHandler consoleOutput) {
    try {
            serverConnection.connect(host, port, consoleOutput);
    }catch(IOException ioe) {
        throw new UncheckedIOException(ioe);
    }
    consoleOutput.handleMsg("Connected to " + host + ":" + port);
    }

    public void sendUsername(String parameter) {
        System.out.println("SEND USERNAME");
    }

    public void guessingLetter(String userInput) {
        serverConnection.guessingLetter(userInput);
    }
    
}
