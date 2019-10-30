package com.example.mubtransito;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

public class InserirOcorrencia extends AppCompatActivity {
    EditText ctLocalOco, ctDataOco, ctHorarioOco;
    Spinner sp;
    ImageButton imgOcorrenciaConsulta;
    Button btEnviarOco;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir_ocorrencia);

        ctLocalOco = (EditText) findViewById(R.id.ctLocalOco);
        ctDataOco = (EditText) findViewById(R.id.ctDataOco);
        ctHorarioOco = (EditText) findViewById(R.id.ctHorarioOco);
        sp = (Spinner) findViewById(R.id.sp);
        imgOcorrenciaConsulta = (ImageButton) findViewById(R.id.imgOcorrenciaConsulta);
        btEnviarOco = (Button) findViewById(R.id.btEnviarOco);

        //Criar array para receber os dados da String
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.ocorrencias, R.layout.support_simple_spinner_dropdown_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(adapter);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Toast.makeText(getBaseContext(),sp.getSelectedItem().toString(),Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });


    }
}
