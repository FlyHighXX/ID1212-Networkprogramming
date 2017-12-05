/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.controller;

import server.model.Hangman;
import java.io.IOException;

/**
 *
 * @author Diaco Uthman
 */
public class Controller {
    private final Hangman gameHandler = new Hangman();
    public void newGuess(String msg)throws IOException{
        gameHandler.newGuess(msg);
    }

    public String[] getConversation() {
        String[] str = {"hej","hejd2"};
        return str;
    }
    
}
