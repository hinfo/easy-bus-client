package unifra.edu.mobile.easybus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Empresas extends AppCompatActivity {

    Intent it;
    private TextView tvEmp1, tvEmp2, tvEmp3, tvEmp4, dadosEmpresa;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);

//        it = new Intent(this,Principal.class);
        tvEmp1 = (TextView) findViewById(R.id.emp1);
        dadosEmpresa = (TextView) findViewById(R.id.dadosEmpresa);
//        tvEmp1.setText("Gabardo");


//        Create dynamically textViews
//        LinearLayout ll = new LinearLayout(this);
//        ll.setOrientation(LinearLayout.VERTICAL);
//        //ll.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT));
//        ll.setGravity(Gravity.CENTER);
//        ll.addView(tvEmp1);
//        setContentView(ll);

    }

    public void onClick(View v) throws JSONException {
        TextView tv = (TextView) v;
        String empresa = tv.getText().toString();
        showDetail(empresa);
    }

    public void showDetail(String empresa) throws JSONException {
        String result = "";
        try {
            InputStream in = this.getAssets().open("empresas.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line = "";
            StringBuilder linhas = new StringBuilder();
            while ((line = br.readLine()) != null) {
                linhas.append(line + "\n");
            }
            result = linhas.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject jObject = new JSONObject(result);
        JSONArray jHors = jObject.getJSONArray("empresas");
        StringBuilder lines = new StringBuilder();
        String nome;
        String endereco;
        String telefone;
        String email;
        String site;


        for (int i = 0; i < jHors.length(); i++) {
            JSONObject obj = jHors.getJSONObject(i);
            System.out.println(empresa);
            System.out.println(obj.optString("nome"));

            if (obj.optString("nome").contains(empresa)) {

                nome = obj.optString("nome");
                endereco = obj.optString("endereco");
                telefone = obj.optString("telefone");
                email = obj.optString("email");
                site = obj.optString("site");
                lines.append("Nome: " + nome + "\n");
                lines.append("Endereço: " + endereco + "\n");
                lines.append("Telefone: " + telefone + "\n");
                lines.append("Email: " + email + "\n");
                lines.append("Site: " + site + "\n");
                dadosEmpresa.setText(lines.toString());
                break;

            } else {
                dadosEmpresa.setText("Não foi possível mostrar os detalhes da empresa!");
                continue;
            }

        }
    }

    public void openPrincipalClick(View v) {
        finish();
    }
}
