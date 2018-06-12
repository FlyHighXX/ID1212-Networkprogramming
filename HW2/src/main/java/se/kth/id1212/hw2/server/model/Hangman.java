package se.kth.id1212.hw2.server.model;

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
    private final String file = "word.txt";
    private String chosenWord;
    private int remainingAttempts;
    private int score;
    private String currentWord;
    private boolean currentGame;
    public Hangman(){
        currentGame=false;
        try {
            setWords(); 
        } catch (IOException ex) {
            System.err.println(ex);
        }
        this.score=0;
    }
    
    public void newGame(){
        this.chosenWord = generateWord();
        this.currentWord = generateSecretWord();
        this.remainingAttempts = this.chosenWord.length();
        this.currentGame = true;
        System.out.println(this.chosenWord);
    }
    public void newGuess(String msg) throws IOException {
        if(msg.length()==1){
            String currWord = this.currentWord;
            fixWord(msg.toLowerCase().charAt(0));
            if(currWord.equals(this.currentWord)){
                this.remainingAttempts-=1;
            }
        }
        else if(msg.length()==this.chosenWord.length()){
            if(msg.equals(this.chosenWord)){
                this.score+=1;
                this.currentWord=msg;
                this.currentGame=false;
            }
        }
        else if(this.remainingAttempts==0){
            this.score-=1;
            this.currentGame=false;
        }
        else if(this.currentWord.equals(this.chosenWord)){
            this.score+=1;
            this.currentGame=false;
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
                line = line.toLowerCase();
                if(line.length() > 1)
                    this.words.add(line);
            }
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
    
    public String getFullWord(){
        return this.chosenWord;
    }

    public int getRemainingAttempts() {
        return this.remainingAttempts;
    }

    public void endGame() {
        this.chosenWord=null;
        this.currentWord=null;
        this.score-=1;
        this.currentGame=false;
    }

    public boolean currentGame() {
        return this.currentGame;
    }

    public boolean checkWord() {
        return this.chosenWord.equals(this.currentWord);
    }
}
