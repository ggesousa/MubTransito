package com.example.mubtransito;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.mubtransito.conexao.Utils;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import org.json.JSONException;
import org.json.JSONObject;

public class CadastrarUsuario extends AppCompatActivity {
    private TextView ctNomeUsuario, ctEmailUsuario, ctSenhaUsuario, ctCpfUsuario, ctTelefoneUsuario, ctNascimentoUsuario;
   private  Button btCadastrarUsuario;
    private ProgressDialog load;
    private String nome, email, telefone, cpf, senha, dataNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar_usuario);

        ctNomeUsuario = (TextView) findViewById(R.id.ctCadNomeUsuario);
        ctEmailUsuario = (TextView) findViewById(R.id.ctCadEmailUsuario);
        ctSenhaUsuario = (TextView) findViewById(R.id.ctCadSenhaUsuario);
        ctCpfUsuario = (TextView) findViewById(R.id.ctCadCpfUsuario);
        btCadastrarUsuario = (Button) findViewById(R.id.btCadastrarUsuario);
        ctTelefoneUsuario = (TextView) findViewById(R.id.ctTelefone);
        ctNascimentoUsuario = (TextView) findViewById(R.id.ctCadNascimentoUsuario);

        btCadastrarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome = ctNomeUsuario.getText().toString();
                email = ctEmailUsuario.getText().toString();
                telefone = ctTelefoneUsuario.getText().toString();
                cpf = ctCpfUsuario.getText().toString();
                senha = ctSenhaUsuario.getText().toString();
                dataNascimento = ctNascimentoUsuario.getText().toString();

                CadastrarUsuarioPost cadastrarUsuarioPost = null;
                if (cadastrarUsuarioPost == null) {
                    cadastrarUsuarioPost = new CadastrarUsuarioPost();
                } else {
                    cadastrarUsuarioPost.cancel(true);
                    cadastrarUsuarioPost = new CadastrarUsuarioPost();
                }
                cadastrarUsuarioPost.execute();
            }
        });

        //Mascara do campo Telefone
        SimpleMaskFormatter simpleMasktelefone = new SimpleMaskFormatter("(NN) N NNNN-NNNN");
        MaskTextWatcher masktelefone = new MaskTextWatcher(ctTelefoneUsuario, simpleMasktelefone);
        ctTelefoneUsuario.addTextChangedListener(masktelefone);

        //Mascara do campo CPF
        SimpleMaskFormatter simpleMaskcpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskcpf = new MaskTextWatcher(ctCpfUsuario, simpleMaskcpf);
        ctCpfUsuario.addTextChangedListener(maskcpf);

        //Mascara do campo Data de Nascimento
        SimpleMaskFormatter simpleMasknascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher masknascimento = new MaskTextWatcher(ctNascimentoUsuario, simpleMasknascimento);
        ctNascimentoUsuario.addTextChangedListener(masknascimento);
    }

    private class CadastrarUsuarioPost extends AsyncTask<Void, Void, String> {

        private AlertDialog alert;

        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(CadastrarUsuario.this,
                    "Por favor aguarde...", "Cadastrando usuário");
        }

        @Override
        protected void onPostExecute(String s) {
            Log.i("RESULTADO: ", s);
            String titulo = "", texto = "";
            int status = 0;

            load.dismiss();

            if (status == 500) {
                titulo = "Erro ao cadastrar :(";
                texto = "Seu e-mail ou CPF já estão cadastrados. Consulte contato@voxelsbrasil.com para mais informações!";
                modalAlert(titulo, texto, false);
            } else {
                titulo = "Obrigado por se cadastrar!";
                texto = "Agora é só realizar o login :)";
                modalAlert(titulo, texto, true);

            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            Utils util = new Utils();
            String retornoCadastro = "";
            try {
                JSONObject json = new JSONObject();
                json.put("email", email);
                json.put("telefone", telefone);
                json.put("nome", nome);
                json.put("cpf", cpf);
                json.put("senha", senha);
                json.put("dataDeNascimento", dataNascimento);
                json.put("nvAcesso", 1);
                json.put("statusUsuario", 1);

                retornoCadastro = util.postTeste("http://192.168.137.1:8080/user/inserir", json);
                return retornoCadastro;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void modalAlert(String titulo, String texto, final boolean redirecionar){
            // Criando gerador de Alerta
            final AlertDialog.Builder builder = new AlertDialog.Builder(CadastrarUsuario.this);
            builder.setTitle(titulo);
            builder.setMessage(texto);

            builder.setPositiveButton("Ok!", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alert.dismiss();
                    if(redirecionar == true){
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
            alert = builder.create();
            alert.show();
        }
    }
}
