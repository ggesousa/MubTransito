package com.example.mubtransito.conexao;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class NetworkUtils {
    //Responsável por carregar o JSON e realizar a conexão
    public static String getJSONFromAPI(String url) {
        String retorno = "";

        try {
            URL apiEnd = new URL(url);
            int codigoResposta;
            HttpURLConnection conexao;
            InputStream is;

            conexao = (HttpURLConnection) apiEnd.openConnection();
            conexao.setRequestMethod("GET");
            conexao.setReadTimeout(15000);
            conexao.setConnectTimeout(15000);
            conexao.connect();

            codigoResposta = conexao.getResponseCode();
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = conexao.getInputStream();
            } else {
                is = conexao.getErrorStream();
            }
            retorno = converterInputStreamToString(is);
            is.close();
            conexao.disconnect();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return retorno;
    }

    private static String converterInputStreamToString(InputStream is) {
        StringBuffer buffer = new StringBuffer();
        try {
            BufferedReader br;
            String linha;

            br = new BufferedReader(new InputStreamReader(is));
            while ((linha = br.readLine()) != null) {
                buffer.append(linha);
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return buffer.toString();
    }

    public static String post(final JSONObject data, String endPoint){
        int codigoResposta;
        InputStream is;
        String retorno = "";
        try{
            final URL url = new URL(endPoint);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");

            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            final OutputStream outputStream = connection.getOutputStream();
            final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(data.toString());
            bufferedWriter.flush();
            bufferedWriter.close();

            connection.connect();

            codigoResposta = connection.getResponseCode();
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }
            retorno = converterInputStreamToString(is);
            is.close();
            connection.disconnect();

            //final InputStream inputStream = connection.getInputStream();
            return retorno;
            //new Scanner(inputStream, "UTF-8").next();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String put(final JSONObject data, String endPoint){
        int codigoResposta;
        InputStream is;
        String retorno = "";
        try{
            final URL url = new URL(endPoint);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-type", "application/json");

            connection.setRequestMethod("PUT");
            connection.setDoInput(true);
            connection.setDoOutput(true);

            final OutputStream outputStream = connection.getOutputStream();
            final BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));

            bufferedWriter.write(data.toString());
            bufferedWriter.flush();
            bufferedWriter.close();

            connection.connect();

            codigoResposta = connection.getResponseCode();
            if (codigoResposta < HttpURLConnection.HTTP_BAD_REQUEST) {
                is = connection.getInputStream();
            } else {
                is = connection.getErrorStream();
            }
            retorno = converterInputStreamToString(is);
            is.close();
            connection.disconnect();

            //final InputStream inputStream = connection.getInputStream();
            return retorno;
            //new Scanner(inputStream, "UTF-8").next();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
