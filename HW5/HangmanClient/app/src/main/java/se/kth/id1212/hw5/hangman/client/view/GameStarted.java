package se.kth.id1212.hw5.hangman.client.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import hangman.hw5.id1212.kth.se.hangmanclient.R;
import se.kth.id1212.hw5.hangman.client.controller.Controller;
import se.kth.id1212.hw5.hangman.client.net.MessageListener;

public class GameStarted extends AppCompatActivity implements ServerMessage{
    public static Controller contr;
    private TextView gameWordText, gameinfoText, welcomeText;
    private EditText guessEditText;
    private Button startGameButton, newGuessButton, gameInfoButton, quitGameButton;
    public MessageListener messageListener;
    private boolean started=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_started);
        contr.setGameContext(this);
        createMessageListener();
        setWindow();
    }

    private void createMessageListener() {
        try {
            new CreateMessageListener().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void setWindow() {
        this.gameWordText=(TextView) findViewById(R.id.gameWordText);
        this.gameinfoText=(TextView) findViewById(R.id.gameInfoText);
        this.welcomeText=(TextView) findViewById(R.id.welcomeText);

        this.welcomeText.setText("Welcome To Hangman " + contr.getUsername() + "!");

        this.guessEditText=(EditText) findViewById(R.id.guessEditText);

        startGameButton=(Button) findViewById(R.id.startGameButton);
        startGameButton.setOnClickListener(view -> {
            contr.startNewGame(this);
            started=true;
            startGameButton.setVisibility(View.GONE);
        });
        this.newGuessButton=(Button) findViewById(R.id.newGuessButton);
        newGuessButton.setOnClickListener(view -> {
            contr.makeNewGuess(guessEditText);
            guessEditText.getText().clear();
        });
        this.gameInfoButton=(Button) findViewById(R.id.gameInfoButton);
        this.gameInfoButton.setOnClickListener(view -> {
            gameinfoText.setText("");
            contr.requestGameInfo();
        });
        this.quitGameButton=(Button) findViewById(R.id.quitGameButton);
        this.quitGameButton.setOnClickListener(view -> {
            contr.quitGame();
            Intent intent = new Intent(GameStarted.this, MainActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void handleMessage(String serverMessage, Command msgType) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(serverMessage.equals("Game over")){
                    startGameButton.setVisibility(View.VISIBLE);
                }
                System.out.println(serverMessage);
                switch (msgType){
                    case START:
                        gameWordText.setText(serverMessage);
                        break;
                    case GAMEINFO:
                        gameinfoText.append(serverMessage);
                        break;
                    case GUESS:
                        gameWordText.setText(serverMessage);
                        break;
                    case QUIT:

                }

            }
        });
    }

    private class CreateMessageListener extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            GameStarted.this.messageListener = new MessageListener(GameStarted.this, GameStarted.this.contr.getOutputStream());
            new Thread(messageListener).start();
            return null;
        }
    }
}
