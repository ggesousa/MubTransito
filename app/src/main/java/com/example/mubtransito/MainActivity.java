package com.example.mubtransito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText ctLoginEmail, ctLoginSenha;
    TextView ctCadastrar;
    Button btLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ctLoginEmail = (EditText) findViewById(R.id.ctLoginEmail);
        ctLoginSenha = (EditText) findViewById(R.id.ctLoginSenha);
        btLogin = (Button) findViewById(R.id.btLogin);

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MenuPrincipal.class);
                startActivity(intent);
            }
        });
    }
}
