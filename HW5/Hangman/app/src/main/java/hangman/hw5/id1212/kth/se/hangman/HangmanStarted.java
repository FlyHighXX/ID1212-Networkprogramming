package hangman.hw5.id1212.kth.se.hangman;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class HangmanStarted extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hangman_started);

        // Get the intent that started this activity and extract the string
        Intent intent = getIntent();
        String message = intent.getStringExtra(HangmanMain.EXTRA_MESSAGE);

        // Capture the layout's TextView and set the String as its text
        TextView textView = findViewById(R.id.textView);
        textView.setText(message);
    }
}