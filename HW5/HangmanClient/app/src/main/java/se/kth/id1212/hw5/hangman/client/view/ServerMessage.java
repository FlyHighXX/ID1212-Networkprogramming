package se.kth.id1212.hw5.hangman.client.view;

public interface ServerMessage {
    void handleMessage(String serverMessage, Command msgType);
}
