package unifra.edu.mobile.easybus;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
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
import java.util.ArrayList;
import java.util.List;

import unifra.edu.mobile.easybus.bean.Linha;

public class Principal extends AppCompatActivity {

    String resposta = "";
    String linha = "";
    String direcao = "";
    String periodo = "";
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
//        readFilePopulateBd("ufsm.txt");
        createDB();


    }

    public void createDB() {
        db = openOrCreateDatabase(BANCO, Context.MODE_PRIVATE, null);
        db.execSQL("CREATE TABLE IF NOT EXISTS horarios (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "HORA TEXT, " +
                "DESCRICAO TEXT, " +
                "DIRECAO TEXT, " +
                "LINHA TEXT, " +
                "PERIODO TEXT," +
                "EMPRESA TEXT);");
        db.close();

        List<Linha> horarios = new ArrayList<>();
        horarios = readFilePopulateBd("ufsm.txt");
        System.out.println("Populando o banco");
        for (Linha linha : horarios) {
            inserir(linha);
//            System.out.println("Dados: " + linha.getDirecao());
        }


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

    public void showResultClick(View v) {
        linha = (String) spLinhas.getSelectedItem();
        direcao = (String) spDirecao.getSelectedItem();
        periodo = (String) spPeriodo.getSelectedItem();
        telaResult.putExtra("linha", linha);
        telaResult.putExtra("direcao", direcao);
        telaResult.putExtra("periodo", periodo);
        telaResult.putExtra("banco", BANCO);
        startActivity(telaResult);
    }

    public void buscaHorarios(String linha, String direcao, String periodo) {
        db = openOrCreateDatabase(BANCO, Context.MODE_PRIVATE, null);
        Cursor rows = db.rawQuery("SELECT * FROM horarios " +
                "WHERE linha= '" + linha +
                "AND direcao = '" + direcao + "'", null);
        if (rows.moveToFirst()) {
            do {
                String hora = rows.getString(1);
                String lin = rows.getString(2);
                String sentido = rows.getString(3);
                String descricao = rows.getString(4);
            } while (rows.moveToNext());
        }
        db.close();
    }

    public void inserirDadosSQL(Linha linha) {
        db = openOrCreateDatabase(BANCO, Context.MODE_PRIVATE, null);
        db.execSQL("INSERT INTO horarios (HORA, DESCRICAO, DIRECAO, LINHA, PERIODO) VALUES (" +
                "'" + linha.getHora() + "','" + linha.getDescricao() + "','" + linha.getDirecao() +
                "','" + linha.getNome() +
                "','" + linha.getPeriodo() + "')");
        db.close();

    }

    public void inserir(Linha linhaDeOnibus) {
        ContentValues valores = new ContentValues();
        valores.put("hora", linhaDeOnibus.getHora());
        valores.put("descricao", linhaDeOnibus.getDescricao());
        valores.put("linha", linhaDeOnibus.getNome());
        valores.put("direcao", linhaDeOnibus.getDirecao());
        valores.put("periodo", linhaDeOnibus.getPeriodo());
        valores.put("empresa", linhaDeOnibus.getEmpresa());
        db = openOrCreateDatabase(BANCO, Context.MODE_PRIVATE, null);
        db.insert("horarios", null, valores);
        db.close();
//        Toast.makeText(getApplicationContext(), "Dados inseridos",
//                Toast.LENGTH_SHORT).show();
    }

    public List<Linha> readFilePopulateBd(String nomeArquivo) {
        String result = "";
        List<Linha> horariosDeOnibus = new ArrayList<>();
        try {
            InputStream in = this.getAssets().open(nomeArquivo);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            String[] dados = null;
            StringBuilder linhas = new StringBuilder();
            while ((line = br.readLine()) != null) {
                Linha linha = new Linha();
                dados = line.split(";");
                System.out.println("Tamanho dos dados da linha: " + dados.length);
                if (dados.length > 1) {
//                System.out.println("hora: " + dados[0]);
//                System.out.println("Descrição: " + dados[1]);
//                System.out.println("Direção: " + dados[2]);
                    System.out.println("Nome: " + dados[3]);
                    System.out.println("Período: " + dados[4]);
                    linha.setHora(dados[0]);
                    linha.setDescricao(dados[1]);
                    linha.setDirecao(dados[2]);
                    linha.setNome(dados[3]);
                    linha.setPeriodo(dados[4]);
                    linha.setEmpresa("teste");
//                System.out.println(line); // log
                    horariosDeOnibus.add(linha);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return horariosDeOnibus;
    }

    public void excluirSQL(int id) {
        db = openOrCreateDatabase(BANCO, Context.MODE_PRIVATE, null);
        db.execSQL("DELETE FROM horarios WHERE id = " + id);
        db.close();
        Toast.makeText(getApplicationContext(), "Registro Excluído",
                Toast.LENGTH_SHORT).show();
    }


    public void showHorarios(View v) {
        startActivity(telaHorarios);
    }

    public void showEmpresas(View v) {
        startActivity(telaEmpresas);
    }

    public void showItinerarios(View v) {
        startActivity(telaItinerarios);
    }


}
