package unifra.edu.mobile.easybus;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Principal extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        String url = "http://easy-bus.herokuapp.com";
       //testgit
        new Acessa().execute(url);
    }


    public class Acessa extends AsyncTask<String, String,String> {

        @Override
        protected String doInBackground(String... params) {
            String resposta = "";
            HttpURLConnection urlConnection = null;
            BufferedReader br = null;

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();
                InputStream in = urlConnection.getInputStream();
                br = new BufferedReader(new InputStreamReader(in));

                String inputLine;
                StringBuffer buffer = new StringBuffer();

                while ((inputLine = br.readLine()) != null) {
                    resposta = inputLine;
                    buffer.append(resposta);
                }
                br.close();
                System.out.println(buffer);
            } catch (MalformedURLException e) {
                e.printStackTrace();
                resposta = "Erro ao acessar a URL";
            } catch (IOException e) {
                e.printStackTrace();
                resposta = "Não foi possível receber os dados";
            } finally {
                urlConnection.disconnect();
            }


            return resposta;
        }

        @Override
        protected void onPostExecute(String ip) {
            super.onPostExecute(ip);
//            textView.setText(ip.toString());
        }
    }

    public void checkVersion() throws MalformedURLException {
        String url = "https://easy-bus.herokuapp.com/";
        new Acessa().execute(url);

    }



}
