package com.example.mubtransito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AlterarOcorrencia extends AppCompatActivity {

    TextView tvNomeUsuario, tvLocalOcorrencia, tvDataOcorrencia, tvHorarioOcorrencia, tvStatusOcorrencia;
    EditText ctObservacoes;
    Button btAlterarDadosOcorrencia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alterar_ocorrencia);

        /*
        ** Setando campos e Text Views as variáveis
         */
        tvNomeUsuario = findViewById(R.id.tvAltOcrNmUsuario);
        tvLocalOcorrencia = findViewById(R.id.tvLocalOcorrencia);
        tvDataOcorrencia = findViewById(R.id.tvDataOcorrencia);
        tvHorarioOcorrencia = findViewById(R.id.tvHorarioOcorrencia);
        tvStatusOcorrencia = findViewById(R.id.tvStatusOcorrencia);
        ctObservacoes = findViewById(R.id.ctObservacoes);
        btAlterarDadosOcorrencia = findViewById(R.id.btAlterarDadosOcorrencia);

        /*
        ** On click do botão...
         */
        btAlterarDadosOcorrencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    /*
    ** Async Task responsável por buscar a ocorrência...
     */
}
