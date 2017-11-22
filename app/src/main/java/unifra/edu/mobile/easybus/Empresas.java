package unifra.edu.mobile.easybus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Empresas extends AppCompatActivity {

    Intent it;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);
//        it = new Intent(this,Principal.class);
    }
    public void openPrincipalClick(View v){
        finish();
    }
}
