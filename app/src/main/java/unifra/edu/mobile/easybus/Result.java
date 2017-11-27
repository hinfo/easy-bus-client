package unifra.edu.mobile.easybus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Result extends AppCompatActivity {
    Intent it;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        it = new Intent(this, Principal.class);
        Intent params = getIntent();
        if (params != null){
            String linha = params.getStringExtra("linha");
            String direcao = params.getStringExtra("direcao");
            String periodo = params.getStringExtra("periodo");
            Toast.makeText(Result.this,"Dados recebidos: \n" +
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
        TextView textoArquivo =  (TextView)  findViewById(R.id.result);
        textoArquivo.setText("");
        String result = "";
        try {
            InputStream in = this.getAssets().open("bus.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            StringBuilder linhas = new StringBuilder();
            while ((line = br.readLine()) != null){
//                textoArquivo.setText(textoArquivo.getText() + line + "\n");
                System.out.println(line);
                linhas.append(line + "\n");
            }
        result = linhas.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject  jObject = new JSONObject(result);
        String name = jObject.getString("linha");
        textoArquivo.setText(name);
//        System.out.println(name);
    }

    public void openPrincipalClick(View v){
        finish();
    }
}
