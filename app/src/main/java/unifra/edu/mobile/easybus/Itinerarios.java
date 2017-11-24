package unifra.edu.mobile.easybus;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Itinerarios extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerarios);
    }
    public void showHorarios(View v){
        LinearLayout ll = (LinearLayout) findViewById(R.id.sv_horarios);
    }
    public void openPrincipalClick(View v){
        finish();
    }
}
