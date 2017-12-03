package unifra.edu.mobile.easybus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Itinerarios extends AppCompatActivity {

    TextView itinerario, tvUfsm, tvCircular;
    LinearLayout layoutUFSM, layoutCircular;
    boolean ativo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerarios);
        tvUfsm = (TextView) findViewById(R.id.tvUfsm);
        tvCircular = (TextView) findViewById(R.id.tvCircular);

        layoutUFSM = (LinearLayout) findViewById(R.id.descUFSM);
        layoutUFSM.setVisibility(View.GONE);

        layoutCircular = (LinearLayout) findViewById(R.id.descCircular);
        layoutCircular.setVisibility(View.GONE);
        ativo = false;

        tvUfsm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ativo) {
                    layoutUFSM.setVisibility(View.VISIBLE);
                    ativo = true;
                } else {
                    layoutUFSM.setVisibility(View.GONE);
                    ativo = false;
                }
            }
        });

        tvCircular.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ativo) {
                layoutCircular.setVisibility(View.VISIBLE);
                    ativo = true;

                } else {
                    layoutCircular.setVisibility(View.GONE);
                    ativo = false;
                }
            }
        });

    }

    public void onClick(View v){

    }
    public void openPrincipalClick(View v){
        finish();
    }
}
