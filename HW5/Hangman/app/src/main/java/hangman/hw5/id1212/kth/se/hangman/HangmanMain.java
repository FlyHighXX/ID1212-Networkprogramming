package hangman.hw5.id1212.kth.se.hangman;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

public class HangmanMain extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "hangman.hw5.id1212.kth.se.hangman";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /** Called when the user wants to start a new game **/
    public void startGame(View view){
        Intent intent = new Intent(this, HangmanStarted.class);
        startActivity(intent);
    }

    /** Called when user hits Send button **/
    /**
    public void sendMessage(View view){
        Intent intent = new Intent(this, HangmanStarted.class);
        EditText editText = (EditText) findViewById(R.id.TextForButton);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }**/
}
