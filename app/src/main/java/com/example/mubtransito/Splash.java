package com.example.mubtransito;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class Splash extends AppCompatActivity {

    private final int DURACION_SPLASH = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {
                //DESCOMENTAR PARA FUNFAR
                /*ConsultarLocalmenteTask atualizarUsuarioLocal = null;
                if (atualizarUsuarioLocal == null) {
                    atualizarUsuarioLocal = new ConsultarLocalmenteTask();
                } else {
                    atualizarUsuarioLocal.cancel(true);
                    atualizarUsuarioLocal = new ConsultarLocalmenteTask();
                }
                atualizarUsuarioLocal.execute();
                finish();*/

                /*
                COMENTAR APÓS DESCOMENTAR A DE CIMA
                 */
                Intent intent = new Intent(Splash.this, MenuPrincipal.class);
                startActivity(intent);
            }

        }, DURACION_SPLASH);
    }
}
