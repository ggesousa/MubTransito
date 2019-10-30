package com.example.mubtransito;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mubtransito.conexao.Utils;
import com.example.mubtransito.roomDatabase.UsuarioDatabase;
import com.example.mubtransito.roomDatabase.entities.Usuario;
import com.github.rtoshiro.util.format.SimpleMaskFormatter;
import com.github.rtoshiro.util.format.text.MaskTextWatcher;

import org.json.JSONException;
import org.json.JSONObject;

public class UsuarioMenu extends AppCompatActivity {
    private ImageView imgPerfilUsuario;
    private ImageButton btNomeUsuario, btSenhaUsuario, btEmailUsuario, btDataNasc;
    private TextView tvNomeUsuario, tvEmailUsuario, ctNomeUsuario, ctSenhaUsuario, ctCpf, ctEmailUsuario, ctDataNasc, ctTelefone;
    private Button btAtualizarUsuario;
    private Long idUsuario;
    private Usuario usuario;
    private String nome, email, telefone, cpf, senha, dataNascimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario_menu);

        /*
         ** Setando views nas variáveis.
         */
        imgPerfilUsuario = (ImageView) findViewById(R.id.imgPerfilUsuario);
        tvNomeUsuario = (TextView) findViewById(R.id.ctNomeUsuario);
        ctNomeUsuario = (TextView) findViewById(R.id.ctNomeUsuario);
        ctSenhaUsuario = (TextView) findViewById(R.id.ctEditSenha);
        ctCpf = (TextView) findViewById(R.id.ctCpf);
        ctEmailUsuario = (TextView) findViewById(R.id.ctEditEmail);
        ctDataNasc = (TextView) findViewById(R.id.ctEditDtNascimento);
        tvNomeUsuario = (TextView) findViewById(R.id.tvEditNome);
        tvEmailUsuario = (TextView) findViewById(R.id.tvEditEmail);
        ctTelefone = (TextView) findViewById(R.id.ctEditTelefone);
        btAtualizarUsuario = (Button) findViewById(R.id.btAlterarDados);

        /*
         ** Desativando campo de CPF
         */
        ctCpf.setEnabled(false);

        /*
         ** Executando Task de recuperação de informações através da API
         */
        RecuperarDadosPerfilTask recuperarDadosPerfilTask = null;
        if (recuperarDadosPerfilTask == null) {
            recuperarDadosPerfilTask = new RecuperarDadosPerfilTask();
        } else {
            recuperarDadosPerfilTask.cancel(true);
            recuperarDadosPerfilTask = new RecuperarDadosPerfilTask();
        }
        recuperarDadosPerfilTask.execute();

        /*
         ** Executando Task de ATUALIZAÇÃO de informações através da API e atualização LOCAL
         */
        btAtualizarUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nome = ctNomeUsuario.getText().toString();
                email = ctEmailUsuario.getText().toString();
                telefone = ctTelefone.getText().toString();
                senha = ctSenhaUsuario.getText().toString();
                dataNascimento = ctDataNasc.getText().toString();

                //Disparando AsyncTask onClick
                AtualizarUsuarioLocal atualizarUsuarioLocal = null;
                if (atualizarUsuarioLocal == null) {
                    atualizarUsuarioLocal = new AtualizarUsuarioLocal();
                } else {
                    atualizarUsuarioLocal.cancel(true);
                    atualizarUsuarioLocal = new AtualizarUsuarioLocal();
                }
                atualizarUsuarioLocal.execute();
            }
        });

        //Mascara do campo CPF
        SimpleMaskFormatter simpleMaskcpf = new SimpleMaskFormatter("NNN.NNN.NNN-NN");
        MaskTextWatcher maskcpf = new MaskTextWatcher(ctCpf, simpleMaskcpf);
        ctCpf.addTextChangedListener(maskcpf);

        //Mascara do campo Telefone
        SimpleMaskFormatter simpleMasktelefone = new SimpleMaskFormatter("(NN) N NNNN-NNNN");
        MaskTextWatcher masktelefone = new MaskTextWatcher(ctCpf, simpleMaskcpf);
        ctCpf.addTextChangedListener(masktelefone);

        //Mascara do campo Data de Nascimento
        SimpleMaskFormatter simpleMasknascimento = new SimpleMaskFormatter("NN/NN/NNNN");
        MaskTextWatcher masknascimento = new MaskTextWatcher(ctDataNasc, simpleMasknascimento);
        ctDataNasc.addTextChangedListener(masknascimento);
    }

    public void setIdUsuario(Long id) {
        this.idUsuario = id;
    }

    public Long getIdUsuario() {
        return this.idUsuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuario() {
        return this.usuario;
    }

    protected class RecuperarDadosPerfilTask extends AsyncTask<Void, Void, String> {
        private AlertDialog alert;
        private Utils util = new Utils();

        @Override
        protected String doInBackground(Void... voids) {
            Usuario usuario;
            try {
                usuario = consultarLocalmente();
                System.out.println("Identificador do usuário na API: " + usuario.getIdUsuarioAPI());
                setIdUsuario(usuario.getIdUsuarioAPI());
                setUsuario(usuario);
                return util.getInfFromGET("http://192.168.137.1:8080/user/buscar/" + usuario.getIdUsuarioAPI());
            } catch (Exception e) {
                Log.i("Error ON INSERT", e.toString());
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONObject contatoObj = jsonObject.getJSONObject("contatoUsuario");

                //Carregando CPF e deixando campo desativado
                ctCpf.setEnabled(true);
                ctCpf.setText(jsonObject.getString("cpf"));
                ctCpf.setEnabled(false);

                ctNomeUsuario.setText(jsonObject.getString("nome"));
                tvNomeUsuario.setText(jsonObject.getString("nome"));

                ctSenhaUsuario.setText(jsonObject.getString("senha"));
                ctDataNasc.setText(jsonObject.getString("dataNascimento"));

                ctEmailUsuario.setText(contatoObj.getString("email"));
                tvEmailUsuario.setText(contatoObj.getString("email"));

                ctTelefone.setText(contatoObj.getString("telefone"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    protected class AtualizarUsuarioLocal extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected String doInBackground(Void... voids) {
            String retornoCadastro = "";
            Utils util = new Utils();
            Usuario usuario = getUsuario();

            try {
                JSONObject json = new JSONObject();
                json.put("email", email);
                json.put("telefone", telefone);
                json.put("nome", nome);
                json.put("cpf", cpf);
                json.put("senha", senha);
                json.put("dataDeNascimento", dataNascimento);
                json.put("statusUsuario", 1);

                json.put("nvAcesso", 1);
                System.out.println("IDENTIFICADOR DO USUARIO: " + idUsuario);
                retornoCadastro = util.putSend("http://192.168.137.1:8080/user/alterar/" + getIdUsuario(), json);
                System.out.println(retornoCadastro);
                return retornoCadastro;
            } catch (Exception e) {
                Log.i("Error ON INSERT", e.toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(String retorno) {
            Log.i("RETORNO DO PUT: ", retorno);
            Usuario usuario = getUsuario();
            try {
                usuario = consultarLocalmente();
                usuario.setIdUsuarioAPI(idUsuario);
                usuario.setNome(ctNomeUsuario.getText().toString());
                usuario.setEmail(ctEmailUsuario.getText().toString());
                usuario.setNvAcesso(usuario.getNvAcesso());
                usuario.setManterLogado(true);

                Long id = UsuarioDatabase
                        .getInstance(getBaseContext())
                        .getUsuarioDAO()
                        .insert(usuario);

                System.out.println("Identificador on UPDATE: " + id);
                Intent intent = new Intent(getBaseContext(), MenuPrincipal.class);
                startActivity(intent);
            } catch (Exception e) {
                Log.i("Error ON UPDATE", e.toString());
            }
        }
    }

    private Usuario consultarLocalmente() {
        Usuario usuario = UsuarioDatabase
                .getInstance(getBaseContext())
                .getUsuarioDAO()
                .getUserById(1L);
        return usuario;
    }
}
