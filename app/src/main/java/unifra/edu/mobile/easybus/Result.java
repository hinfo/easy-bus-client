package unifra.edu.mobile.easybus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ScrollView;
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
    ScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        it = new Intent(this, Principal.class);
        Intent params = getIntent();
        tableLayout = (TableLayout) findViewById(R.id.resultado);
//        scrollView = (ScrollView) findViewById(R.id.resultados);
        if (params != null) {
            String linha = params.getStringExtra("linha");
            String direcao = params.getStringExtra("direcao");
            String periodo = params.getStringExtra("periodo");
            Toast.makeText(Result.this, "Dados recebidos: \n" +
                    "Linha : " + linha +
                    "\nDireção: " + direcao +
                    "\nPeriodo: " + periodo, Toast.LENGTH_LONG).show();
        }
        try {
            visualizarArquivo();
        } catch (JSONException e) {
            e.printStackTrace();
        }
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

    public void openPrincipalClick(View v) {
        finish();
    }
}
