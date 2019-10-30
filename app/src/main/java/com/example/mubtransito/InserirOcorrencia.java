package com.example.mubtransito;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

public class InserirOcorrencia extends AppCompatActivity {
    final int REQUEST_IMAGE_CAPTURE = 1;
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

        //verifica se foi permitido o uso da camera
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 0);
        }


        //recuperando os elementos da interface
        ImageButton tirar = (ImageButton) findViewById(R.id.imgOcorrenciaConsulta);

        //metodo para o click do botão
        tirar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //tirarFoto();
                dispatchTakePictureIntent();
            }

            private void dispatchTakePictureIntent() {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView imageViewFoto = (ImageView) findViewById(R.id.imgOcorrenciaConsulta);
            imageViewFoto.setImageBitmap(imageBitmap);
        }


    }

}