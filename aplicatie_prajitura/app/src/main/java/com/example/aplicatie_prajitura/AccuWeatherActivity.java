package com.example.aplicatie_prajitura;

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
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AccuWeatherActivity extends AppCompatActivity {
    String apiKey = "aYsIaZVUYUiMVG33MIXQv5C1ZvSiBnEG";
    String cheieOras;
    String min;
    String max;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_accu_weather);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        Button btnSearch = findViewById(R.id.idBtnCautaOras);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText orasET =  findViewById(R.id.idETNumeOras);
                String oras = orasET.getText().toString();

                String url = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=" + apiKey + "&q=" + oras;
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection con = null;
                        try {
                            URL apiURL = new URL(url);
                            con = (HttpURLConnection) apiURL.openConnection();
                            con.setRequestMethod("GET");
                            con.connect();

                            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
                            BufferedReader reader = new BufferedReader(inputStreamReader);
                            StringBuilder response = new StringBuilder();
                            String line;

                            while((line = reader.readLine())!=null) {
                                response.append(line);
                            }
                            reader.close();
                            con.disconnect();

                            JSONArray vector = new JSONArray(response.toString());
                            JSONObject obiect = vector.getJSONObject(0);
                            cheieOras = obiect.getString("Key");

                            TextView orasTV = findViewById(R.id.idKeyTV);

                            String urlWeather = "http://dataservice.accuweather.com/forecasts/v1/daily/1day/" + cheieOras
                                    + "?apikey=" + apiKey + "&metric=true";
                            apiURL = new URL(urlWeather);
                            con = (HttpURLConnection) apiURL.openConnection();
                            con.setRequestMethod("GET");
                            con.connect();

                            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                            response = new StringBuilder();
                            while((line = reader.readLine()) != null){
                                response.append(line);
                            }

                            reader.close();
                            con.disconnect();

                            JSONObject object = new JSONObject(response.toString());
                            JSONObject dailyForecasts = object.getJSONArray("DailyForecasts").getJSONObject(0);
                            JSONObject temperature = dailyForecasts.getJSONObject("Temperature");
                            min = temperature.getJSONObject("Minimum").getString("Value");
                            max = temperature.getJSONObject("Maximum").getString("Value");

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    TextView tvCod = findViewById(R.id.idKeyTV);
                                    tvCod.setText("Cheie oras:" + cheieOras + "\ntemperatura minima:"
                                            + min + "\ntemperatura maxima:" + max );
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