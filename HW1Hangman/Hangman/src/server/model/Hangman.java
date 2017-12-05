/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Diaco Uthman
 */
public class Hangman {
    private final List<String> words = new ArrayList<>();
    private final String file = "words.txt";
    private String chosenWord;
    private int remainingAttempts;
    private int score;
    private String currentWord;
    public Hangman(){
        try {
            setWords();
        } catch (IOException ex) {
            System.err.println(ex);
        }
        this.chosenWord = generateWord();
        this.currentWord = generateSecretWord();
        this.remainingAttempts = this.chosenWord.length();
        this.score=0;
    }
    public void newGuess(String msg) throws IOException {
        if(msg.length()==1){
            String currWord = this.currentWord;
            fixWord(msg.charAt(0));
            if(currWord.equals(this.currentWord)){
                
            }else{
                this.remainingAttempts-=1;
            }
        }
        else if(msg.length()==this.chosenWord.length()){
            if(msg.equals(this.chosenWord)){
                this.chosenWord=null;
                this.score+=1;
            }
        }
        else if(this.remainingAttempts==0){
            this.score-=1;
            this.chosenWord=null;
        }
        else{
            throw new IOException("Wrong input");
        }
    }

    private String generateWord(){
        Random randomGenerator = new Random();
        return words.get(randomGenerator.nextInt(words.size()));
    }

    private void fixWord(char letter) {
        StringBuilder modifiedWord = new StringBuilder();
        for(int i=0; i<this.chosenWord.length();i++){
            if(this.chosenWord.charAt(i)==letter){
                modifiedWord.append(letter);
            }
            else{
                modifiedWord.append(this.currentWord.charAt(i));
            }
        }
        this.currentWord=modifiedWord.toString();
    }

    private void setWords () throws FileNotFoundException, IOException{
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.length() > 1)
                    this.words.add(line);
            }
        }catch(FileNotFoundException e){
            throw new FileNotFoundException("The file was not found");
        }catch(IOException e){
            throw new IOException("Something went wrong with the reading of the file");
        }
    }

    private String generateSecretWord() {
        StringBuilder newWord = new StringBuilder();
        for(int i=0; i<chosenWord.length();i++){
            newWord.append("_");
        }
        return newWord.toString();
    }
    
    public int getScore(){
        return this.score;
    }
    
    public String getWord(){
        return this.currentWord;
    }
}
