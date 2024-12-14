package com.example.app6;

import android.nfc.cardemulation.HostNfcFService;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ApiNinjasAnimalsActivity extends AppCompatActivity {
private String API_KEY = "ld2siCHQUfwqh2G6fINP7A==pOHyVn1zmwmNmQlj";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_api_ninjas_animals);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        Button btnCauta = findViewById(R.id.idBtnCautaDupaCheie);
        btnCauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etCheie = findViewById(R.id.idETCheie);
                String cheie = etCheie.getText().toString();

                String url = "https://api.api-ninjas.com/v1/animals?name=" + cheie + "&X-Api-Key=" + API_KEY;

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection con = null;
                        try{
                            URL apiURL = new URL(url);
                            con = (HttpURLConnection) apiURL.openConnection();
                            con.setRequestMethod("GET");
                            con.connect();

                            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
                            BufferedReader reader = new BufferedReader(inputStreamReader);
                            StringBuilder builder = new StringBuilder();
                            String linie = null;

                            while((linie=reader.readLine())!=null){
                                builder.append(linie);
                            }
                            reader.close();
                            con.disconnect();

                            StringBuilder rez = new StringBuilder();
                            JSONArray responseArray = new JSONArray(builder.toString());
                            for(int i=0;i<responseArray.length();i++) {
                                JSONObject jsonObject = responseArray.getJSONObject(i);
                                String nume = jsonObject.getString("name");

                                JSONObject taxonomy = jsonObject.getJSONObject("taxonomy");

                                String scientific;
                                if(taxonomy.has("scientific_name")){
                                    scientific = taxonomy.getString("scientific_name");}
                                else
                                    scientific="N/A";

                                rez.append(nume+";"+scientific+"\n");
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    TextView tvRezultat = findViewById(R.id.idTVRezultat);
                                    tvRezultat.setText(rez);
                                }
                            });

                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                    }
                });
            }
        });
    }
}