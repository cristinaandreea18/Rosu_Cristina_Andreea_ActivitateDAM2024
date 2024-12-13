package com.example.app5;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BigBookAPIActivity extends AppCompatActivity {
private String API_KEY = "ef2949d6c25645e08cb16331ee09f2a9";
private String url;
private int ok;
private String carte;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_big_book_apiactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner sp = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.optiuni,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(adapter);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        Button btnCauta = findViewById(R.id.idBtnCodAPI);
        btnCauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String optiune = sp.getSelectedItem().toString();

                EditText edCheie = findViewById(R.id.idETCodAPI);
                String cheie = edCheie.getText().toString();

                if(optiune.equals("Cautare Carte dupa Cod")){
                    url = "https://api.bigbookapi.com/"+cheie+"?api-key="+API_KEY;
                    ok=1;
                }
                else if(optiune.equals("Cautare autori dupa nume")){
                    url = "https://api.bigbookapi.com/search-authors?name="+cheie+"&api-key="+API_KEY;
                    ok=2;
                }

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
                         StringBuilder response = new StringBuilder();
                         String linie = null;
                         while((linie= reader.readLine())!=null){
                             response.append(linie);
                         }
                         reader.close();
                         con.disconnect();

                         StringBuilder rez = new StringBuilder();
                         if(ok==1){
                            JSONObject responseObject = new JSONObject(response.toString());
                            carte = responseObject.getString("title");
                         }
                          else if(ok==2){
                            JSONObject responseObject = new JSONObject(response.toString());
                            JSONArray array = responseObject.getJSONArray("authors");
                            for(int i=0;i<array.length();i++){
                                JSONObject author = array.getJSONObject(i);
                                String id = author.getString("id");
                                String nume = author.getString("name");
                                rez.append(id+","+nume+"\n");
                            }
                         }

                         handler.post(new Runnable() {
                             @Override
                             public void run() {
                                 TextView tvCod = findViewById(R.id.idTVCarteAPI);
                                 if(ok==1)
                                 tvCod.setText(carte);
                                 else
                                     if(ok==2){
                                        tvCod.setText(rez.toString());
                                     }
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