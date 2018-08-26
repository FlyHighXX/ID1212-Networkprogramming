package se.kth.id1212.hw5.hangman.client.view;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.ExecutionException;

import hangman.hw5.id1212.kth.se.hangmanclient.R;
import se.kth.id1212.hw5.hangman.client.controller.Controller;
import se.kth.id1212.hw5.hangman.client.net.MessageListener;

public class MainActivity extends AppCompatActivity{
    public static final String EXTRA_MESSAGE = "";
    Controller contr;
    TextView response;
    EditText editTextAddress, editTextPort, editUsername;
    Button buttonConnect, buttonClear;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupWindow();
    }

    private void connect() {
        try {
            new ConnectServer().execute().get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setupWindow() {
        editTextAddress = (EditText) findViewById(R.id.addressEditText);
        editTextPort = (EditText) findViewById(R.id.portEditText);
        editUsername = (EditText) findViewById(R.id.usernameEditText);
        buttonConnect = (Button) findViewById(R.id.connectButton);
        buttonClear = (Button) findViewById(R.id.clearButton);
        response = (TextView) findViewById(R.id.responseTextView);
        contr=new Controller();
        final MainActivity context = this;
        buttonConnect.setOnClickListener(view -> {
            try{
                connect();
                Intent intent = new Intent(context, GameStarted.class);
                GameStarted.contr=contr;
                startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }

        });

        buttonClear.setOnClickListener(view -> response.setText(""));
    }

    private class ConnectServer extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            MainActivity.this.contr.connect(editTextAddress.getText().toString(), editUsername.getText().toString(), Integer.parseInt(editTextPort.getText().toString()));
            return null;
        }
    }
}
