package unifra.edu.mobile.easybus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Result extends AppCompatActivity {
    Intent it;
    TableLayout tableLayout;
    SQLiteDatabase db;
    String BANCO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        it = new Intent(this, Principal.class);
        Intent params = getIntent();
        tableLayout = (TableLayout) findViewById(R.id.resultado);
//        scrollView = (ScrollView) findViewById(R.id.resultados);
        String sql = "";
        if (params != null) {
            BANCO = params.getStringExtra("banco");
            String linha = params.getStringExtra("linha");
            String direcao = params.getStringExtra("direcao");
            String periodo = params.getStringExtra("periodo");
            System.out.println("Linha: " + linha);
            System.out.println("Direcao : " + direcao);
            System.out.println("Periodo : " + periodo);
//            sql = "SELECT * FROM horarios " +
//                    "WHERE linha='" +linha +"'" +
//                    " AND direcao='" + direcao + "'" +
//                    " AND periodo='" + periodo + "';";
//            sql = "SELECT * FROM horarios " +
//                    "WHERE linha='" +linha +"';";

            sql = "SELECT * FROM horarios " +
            "WHERE linha='" +linha +"'" +
            " AND direcao='" + direcao + "';";

        }
            buscaHorarios(sql);
    }

    public void visualizarArquivo() throws JSONException {
        String result = "";
        try {
            InputStream in = this.getAssets().open("horarios.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            StringBuilder linhas = new StringBuilder();
            while ((line = br.readLine()) != null) {
//                textoArquivo.setText(textoArquivo.getText() + line + "\n");
//                System.out.println(line);
                linhas.append(line + "\n");
            }
            result = linhas.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jObject = new JSONObject(result);
        JSONArray jHors = jObject.getJSONArray("linhas");
        StringBuilder lines = new StringBuilder();
        String hora;
        String descricao;
        String direcao;

        for (int i = 0; i < jHors.length(); i++) {
            TableRow tableRow = new TableRow(this);
            TextView text1 = new TextView(this);
            TextView text2 = new TextView(this);
            TextView text3 = new TextView(this);

            JSONObject obj = jHors.getJSONObject(i);
            hora = obj.optString("hora");
            descricao = obj.optString("descricao");
            direcao = obj.optString("direcao");

            text1.setText(hora);
            text1.setWidth(5);
            text2.setText(descricao);
            text2.setWidth(150);
            text3.setText(direcao);
            tableRow.addView(text1);
            tableRow.addView(text2);
            tableRow.addView(text3);
            tableLayout.addView(tableRow);
        }
    }
    public void buscaHorarios(String sql){
        db = openOrCreateDatabase(BANCO, Context.MODE_PRIVATE, null);

        Cursor rows = db.rawQuery(sql,null);
//        Cursor rows = db.rawQuery("SELECT * FROM horarios " +
//                "WHERE linha= '" +linha +
//                "AND direcao = '"+direcao+"'", null);
        if (rows.moveToFirst()){
            Toast.makeText(Result.this,"Mostrando os resultados", Toast.LENGTH_LONG).show();
            do {
                TableRow tableRow = new TableRow(this);
                TextView text1 = new TextView(this);
                TextView text2 = new TextView(this);
                TextView text3 = new TextView(this);
                String hora = rows.getString(1);
                String descricao = rows.getString(2);
                String direcao = rows.getString(3);
                String nome = rows.getString(4);
                text1.setText(hora);
                text1.setWidth(5);
                text2.setText(descricao);
                text2.setWidth(150);
                text3.setText(direcao);
                tableRow.addView(text1);
                tableRow.addView(text2);
                tableRow.addView(text3);
                tableLayout.addView(tableRow);


            } while (rows.moveToNext());
        } else {
            Toast.makeText(Result.this,"Nenhum dado encontrado!",Toast.LENGTH_LONG).show();
        }
        db.close();
    }

    public void openPrincipalClick(View v) {
        finish();
    }
}
