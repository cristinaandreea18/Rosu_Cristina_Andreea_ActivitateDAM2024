package com.example.app8;

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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class HistoricalEventsAPIActivity extends AppCompatActivity {
private String API_KEY = "ld2siCHQUfwqh2G6fINP7A==pOHyVn1zmwmNmQlj";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_historical_events_apiactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btn = findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText ev = findViewById(R.id.editTextText);
                String event = ev.getText().toString();

                Executor executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.myLooper());

                String API = "https://api.api-ninjas.com/v1/historicalevents?text="+ event+"&X-Api-Key="+API_KEY;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection con = null;
                        try{
                            URL url = new URL(API);
                            con = (HttpURLConnection) url.openConnection();
                            con.connect();
                            con.setRequestMethod("GET");

                            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
                            BufferedReader reader = new BufferedReader(inputStreamReader);
                            StringBuilder sb = new StringBuilder();
                            String linie = null;
                            while((linie=reader.readLine())!=null){
                                sb.append(linie);
                            }

                            reader.close();
                            con.disconnect();

                            StringBuilder response = new StringBuilder();
                            JSONArray array = new JSONArray(sb.toString());
                            for(int i=0;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                String event = object.getString("event");
                                response.append(event+"\n");
                            }
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    TextView tv = findViewById(R.id.textView2);
                                    tv.setText(response);
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