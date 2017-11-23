package unifra.edu.mobile.easybus;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Principal extends AppCompatActivity {

    String resposta = "";
    String linha = "";
    String direcao = "";
    String periodo = "" ;
    Intent telaHorarios, telaEmpresas, telaItinerarios, telaResult;
    Spinner spLinhas, spDirecao, spPeriodo;
    SQLiteDatabase db;
    String BANCO = "onibus.db";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);



        String url = "http://easy-bus.herokuapp.com";
        //testgit
        new Acessa().execute(url);

        spLinhas = (Spinner) findViewById(R.id.spLinhas);
        spDirecao = (Spinner) findViewById(R.id.spDirecao);
        spPeriodo = (Spinner) findViewById(R.id.spPeriodo);

        telaHorarios = new Intent(this, Horarios.class);
        telaEmpresas = new Intent(this, Empresas.class);
        telaItinerarios = new Intent(this, Itinerarios.class);
        telaResult = new Intent(this, Result.class);


    }

    public void createDB(){
        db = openOrCreateDatabase(BANCO, Context.MODE_PRIVATE,null);
        db.execSQL("CREATE TABLE IF NOT EXISTS periodo (" +
                "ID INTEGER PRIMARY KEY, " +
                "DESCRICAO TEXT);" );
        db.close();

    }


    public class Acessa extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            BufferedReader br = null;

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                br = new BufferedReader(new InputStreamReader(in));

                String inputLine;
                StringBuffer buffer = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    resposta = inputLine;
                    buffer.append(resposta);
                }
                br.close();
                System.out.println(buffer);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                resposta = "Erro ao acessar a URL";
            } catch (IOException e) {
                e.printStackTrace();
                resposta = "Não foi possível receber os dados\nSem Conexão com a internet";
            } finally {
                urlConnection.disconnect();
            }


            return resposta;
        }

        @Override
        protected void onPostExecute(String resposta) {
            super.onPostExecute(resposta);
            Toast.makeText(Principal.this, resposta, Toast.LENGTH_LONG).show();
//            textView.setText(ip.toString());
        }
    }

    public void checkVersion() throws MalformedURLException {
        String url = "https://easy-bus.herokuapp.com/";
        new Acessa().execute(url);
    }

    public void showResultClick(View v){
        linha =  (String) spLinhas.getSelectedItem();
        direcao = (String) spDirecao.getSelectedItem();
        periodo = (String) spPeriodo.getSelectedItem();
        telaResult.putExtra("linha", linha);
        telaResult.putExtra("direcao", direcao);
        telaResult.putExtra("periodo", periodo);
        startActivity(telaResult);
    }

    public void showHorarios(View v){
        startActivity(telaHorarios);
    }

    public void showEmpresas(View v){
        startActivity(telaEmpresas);
    }

    public void showItinerarios(View v){
        startActivity(telaItinerarios);
    }


}
