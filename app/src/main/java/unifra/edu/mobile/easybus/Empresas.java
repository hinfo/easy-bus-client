package unifra.edu.mobile.easybus;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Empresas extends AppCompatActivity {

    Intent it;
    private TextView tvEmp1, tvEmp2, tvEmp3, tvEmp4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresas);

//        it = new Intent(this,Principal.class);
        tvEmp1 = (TextView) findViewById(R.id.emp1);
//        tvEmp1.setText("Gabardo");



//        Create dynamically textViews
//        LinearLayout ll = new LinearLayout(this);
//        ll.setOrientation(LinearLayout.VERTICAL);
//        //ll.setLayoutParams(new LinearLayout.LayoutParams(WRAP_CONTENT));
//        ll.setGravity(Gravity.CENTER);
//        ll.addView(tvEmp1);
//        setContentView(ll);

    }
    public void openPrincipalClick(View v){
        finish();
    }
}
