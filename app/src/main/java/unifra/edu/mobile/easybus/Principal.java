package unifra.edu.mobile.easybus;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
    Intent telaFavoritos, telaEmpresas, telaItinerarios, telaResult;
    Spinner spLinhas, spDirecao, spPeriodo;
    SQLiteDatabase db;
    SQLiteOpenHelper statusDb;
    String BANCO = "onibus.db";
    private boolean teste;
    private boolean exist;
    private final double versionDB = 1.0;
    private double newVersionDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        teste = true;

        //webservice
        String url = "http://easy-bus.herokuapp.com";

        try {
            newVersionDB = checkVersion(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        spLinhas = (Spinner) findViewById(R.id.spLinhas);
        spDirecao = (Spinner) findViewById(R.id.spDirecao);
        spPeriodo = (Spinner) findViewById(R.id.spPeriodo);

        telaFavoritos = new Intent(this, Favoritos.class);
        telaEmpresas = new Intent(this, Empresas.class);
        telaItinerarios = new Intent(this, Itinerarios.class);
        telaResult = new Intent(this, Result.class);

        //Testando se o banco já existe e está populado
        exist = checkFileInstall();
        if (!exist) {
            createDB();
        } else {
            System.out.println("Banco não existe!\nCriando o banco.");
        }

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
//            System.out.println("Populando o banco");
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

    public double checkVersion(String url) throws MalformedURLException {
//        String url = "https://easy-bus.herokuapp.com/";
        new Acessa().execute(url);
        double version = 1.0;

        return version;
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
//                System.out.println("Tamanho dos dados da linha: " + dados.length);
                if (dados.length > 1) {
//                System.out.println("hora: " + dados[0]);
//                System.out.println("Descrição: " + dados[1]);
//                System.out.println("Direção: " + dados[2]);
//                    System.out.println("Nome: " + dados[3]);
//                    System.out.println("Período: " + dados[4]);
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


    public void showFavoritos(View v) {
        startActivity(telaFavoritos);
    }

    public void showEmpresas(View v) {
        startActivity(telaEmpresas);
    }

    public void showItinerarios(View v) {
        startActivity(telaItinerarios);
    }

    public void clickSair(View v) {
        finish();
    }

    public void saveFileInstall() {
        String fileName = "install.txt";
        try {
            FileOutputStream out = openFileOutput(fileName, MODE_APPEND);
            StringBuilder dados = new StringBuilder();
            dados.append("instalado\n");
            dados.append("Version: 1.0");
            out.write(dados.toString().getBytes());
            out.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkFileInstall() {
        boolean fileExist;
        String fileName = "install.txt";
        File f = getFileStreamPath(fileName);

        if (f.exists()) {
            fileExist = true;
        } else {
            saveFileInstall();
            fileExist = false;
        }

        return fileExist;

    }


}
