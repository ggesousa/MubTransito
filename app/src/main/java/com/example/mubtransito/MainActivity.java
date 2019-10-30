package com.example.mubtransito;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.mubtransito.conexao.Utils;
import com.example.mubtransito.roomDatabase.UsuarioDatabase;
import com.example.mubtransito.roomDatabase.entities.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    EditText ctLoginEmail, ctLoginSenha;
    TextView ctCadastrar;
    Button btLogin;
    ProgressDialog load;
    String emailLogin, senhaLogin;

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
                emailLogin = ctLoginEmail.getText().toString();
                senhaLogin = ctLoginSenha.getText().toString();
                GetJson login = null;
                if (login == null) {
                    login = new GetJson();
                } else {
                    login.cancel(true);
                    login = new GetJson();
                }
                login.execute();
            }
        });
    }

    private class GetJson extends AsyncTask<Void, Void, String> {

        private AlertDialog alert;
        private Utils util = new Utils();
        private int aux = 0;

        @Override
        protected void onPreExecute() {
            load = ProgressDialog.show(MainActivity.this,
                    "Por favor aguarde...", "Realizando login...");
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                JSONObject json = new JSONObject();
                json.put("email", emailLogin);
                json.put("senha", senhaLogin);
                return util.postTeste("http://192.168.137.1:8080/user/login", json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            Usuario usuario = new Usuario();
            try {
                JSONObject jsonObject = new JSONObject(string);
                Log.w("RETORNO LOGIN: ", string);

                try {
                    /*
                     ** Atualizando no objeto usuário
                     */

                    System.out.println("ID DO USUÁRIOOOOOOOOOOOOOO" + jsonObject.getLong("id"));

                    usuario.setId(1L);
                    usuario.setIdUsuarioAPI(Long.parseLong(jsonObject.getString("id")));
                    usuario.setNome(jsonObject.getString("nomeUsuario"));
                    usuario.setEmail(jsonObject.getString("email"));
                    usuario.setNvAcesso(jsonObject.getInt("nvAcesso"));
                    usuario.setManterLogado(true);

                    /*
                     ** Disparando requisção para consulta se o usuário é um agente de trânsito.
                     */

                    try {
                        String agente = util.getInfFromGET("http://192.168.137.1:8080/agente/buscar-agente-usuario/"
                                + usuario.getIdUsuarioAPI());
                        jsonObject = new JSONObject(agente);
                        aux = jsonObject.getInt("id");
                    } catch (Exception e) {
                        Log.e("Erro ao buscar agente", e.toString());
                        e.printStackTrace();
                    }

                    /*
                     ** Verificando se o usuário é um agente de trânsito
                     */
                    if (aux == 0) {
                        usuario.setAgente(false);
                        usuario.setCidade("Não informada");
                    } else {
                        usuario.setAgente(true);
                        usuario.setCidade(jsonObject.getJSONObject("departamento").getJSONObject("endereco").getString("cidade"));
                    }

                    /*
                     ** Inserindo/Atualizando dados do usuário LOCALMENTE
                     */
                    atualizarUsuarioLocal(usuario);
                    Intent intent = new Intent(getBaseContext(), MenuPrincipal.class);
                    startActivity(intent);
                } catch (Exception e) {
                    Log.e("Error ON LOGIN", e.toString());
                    modalErro();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            load.dismiss();
        }

        protected void modalErro() {
            // Criando gerador de Alerta
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("Erro...");
            builder.setMessage("E-mail ou senha incorretos... :/");
            builder.setPositiveButton("Tentar novamente", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    alert.dismiss();
                }
            });
            alert = builder.create();
            alert.show();
        }

        private Long atualizarUsuarioLocal(Usuario usuario) {
            Long id = UsuarioDatabase
                    .getInstance(getBaseContext())
                    .getUsuarioDAO()
                    .insert(usuario);
            return id;
        }
    }
}
