package unifra.edu.mobile.easybus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

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
    }
    public void openPrincipalClick(View v){
        finish();
    }
}
