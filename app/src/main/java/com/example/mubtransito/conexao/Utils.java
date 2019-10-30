package com.example.mubtransito.conexao;

import android.util.Log;
import com.example.mubtransito.conexao.pojos.LoginPojo;
import org.json.JSONException;
import org.json.JSONObject;

public class Utils {


    public LoginPojo getInformacao(String endPoint) {
        String json;
        LoginPojo retorno;
        json = NetworkUtils.getJSONFromAPI(endPoint);
        Log.i("Resultado", json);
        retorno = parseJson(json);

        return retorno;
    }

    public String getInfFromGET(String endPoint){
        String retorno = NetworkUtils.getJSONFromAPI(endPoint);
        Log.i("Resultado: ", retorno);
        return retorno;
    }

    public String postTeste(String endPoint, JSONObject json) throws JSONException {
        String retorno = NetworkUtils.post(json, endPoint);
        Log.i("Resultado", retorno);
        return retorno;
    }

    public String putSend(String endPoint, JSONObject json){
        String retorno = NetworkUtils.put(json,endPoint);
        Log.i("Resultado: ", retorno);
        return retorno;
    }

    public LoginPojo parseJson(String json) {
        try {
            LoginPojo login = new LoginPojo();
            JSONObject jsonObject = new JSONObject(json);

            /*JSONArray array = new JSONArray("login");
            JSONObject objArray = array.getJSONObject(0);
            JSONObject obj = jsonObject.getJSONObject("login");*/

            login.setEmail(jsonObject.getString("email"));
            login.setId(jsonObject.getString("id"));
            login.setStatus(jsonObject.getString("statusLogin"));

            return login;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
