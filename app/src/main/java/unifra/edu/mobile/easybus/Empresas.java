package unifra.edu.mobile.easybus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;

public class Empresas extends AppCompatActivity {

    Intent it;
    private TextView empMedianeira, empDores, empSalgado, empGabardo, empViacao;
    private LinearLayout dadosGabardo, dadosMedianeira, dadosDores, dadosViacao, dadosSalgado;
    private boolean ativo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);

//        it = new Intent(this,Principal.class);
        ativo = false;
        empGabardo = (TextView) findViewById(R.id.empGabardo);
        dadosGabardo = (LinearLayout) findViewById(R.id.dadosGabardo);
        dadosGabardo.setVisibility(View.GONE);

        empDores = (TextView) findViewById(R.id.empDores);
        dadosDores = (LinearLayout) findViewById(R.id.dadosDores);
        dadosDores.setVisibility(View.GONE);

        empSalgado = (TextView) findViewById(R.id.empSalgado);
        dadosSalgado = (LinearLayout) findViewById(R.id.dadosSalgado);
        dadosSalgado.setVisibility(View.GONE);

        empMedianeira = (TextView) findViewById(R.id.empMedianeira);
        dadosMedianeira = (LinearLayout) findViewById(R.id.dadosMedianeira);
        dadosMedianeira.setVisibility(View.GONE);

        empViacao = (TextView) findViewById(R.id.empViacao);
        dadosViacao = (LinearLayout) findViewById(R.id.dadosViacao);
        dadosViacao.setVisibility(View.GONE);


        empGabardo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ativo) {
                    dadosGabardo.setVisibility(View.VISIBLE);
                    ativo = true;
                } else {
                    dadosGabardo.setVisibility(View.GONE);
                    ativo = false;
                }
            }
        });

        empDores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ativo) {
                    dadosDores.setVisibility(View.VISIBLE);
                    ativo = true;
                } else {
                    dadosDores.setVisibility(View.GONE);
                    ativo = false;
                }
            }
        });

        empMedianeira.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ativo) {
                    dadosMedianeira.setVisibility(View.VISIBLE);
                    ativo = true;
                } else {
                    dadosMedianeira.setVisibility(View.GONE);
                    ativo = false;
                }
            }
        });

        empSalgado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ativo) {
                    dadosSalgado.setVisibility(View.VISIBLE);
                    ativo = true;
                } else {
                    dadosSalgado.setVisibility(View.GONE);
                    ativo = false;
                }

            }
        });

        empViacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ativo) {
                    dadosViacao.setVisibility(View.VISIBLE);
                    ativo = true;
                } else {
                    dadosViacao.setVisibility(View.GONE);
                    ativo = false;
                }
            }
        });

    }


    public void onClick(View v) throws JSONException {
        TextView tv = (TextView) v;
        String empresa = tv.getText().toString();
//        showDetail(empresa);
    }

//    public void showDetail(String empresa) throws JSONException {
//        String result = "";
//        try {
//            InputStream in = this.getAssets().open("empresas.json");
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//            String line = "";
//            StringBuilder linhas = new StringBuilder();
//            while ((line = br.readLine()) != null) {
//                linhas.append(line + "\n");
//            }
//            result = linhas.toString();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        JSONObject jObject = new JSONObject(result);
//        JSONArray jHors = jObject.getJSONArray("empresas");
//        StringBuilder lines = new StringBuilder();
//        String nome;
//        String endereco;
//        String telefone;
//        String email;
//        String site;
//
//
//        for (int i = 0; i < jHors.length(); i++) {
//            JSONObject obj = jHors.getJSONObject(i);
//            System.out.println(empresa);
//            System.out.println(obj.optString("nome"));
//
//            if (obj.optString("nome").contains(empresa)) {
//
//                nome = obj.optString("nome");
//                endereco = obj.optString("endereco");
//                telefone = obj.optString("telefone");
//                email = obj.optString("email");
//                site = obj.optString("site");
//                lines.append("Nome: " + nome + "\n");
//                lines.append("Endereço: " + endereco + "\n");
//                lines.append("Telefone: " + telefone + "\n");
//                lines.append("Email: " + email + "\n");
//                lines.append("Site: " + site + "\n");
//                dadosEmpresa.setTextSize(18);
//                dadosEmpresa.setTextColor(Color.BLUE);
//                dadosEmpresa.setText(lines.toString());
//                break;
//
//            } else {
//                dadosEmpresa.setText("Não foi possível mostrar os detalhes da empresa!");
//                continue;
//            }
//
//        }
//    }

    public void openPrincipalClick(View v) {
        finish();
    }
}
