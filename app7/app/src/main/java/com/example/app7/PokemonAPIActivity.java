package com.example.app7;

import android.os.Bundle;
import android.os.Handler;

import android.os.Looper;
import android.view.View;
import android.widget.Button;
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
import java.util.logging.LogRecord;

public class PokemonAPIActivity extends AppCompatActivity {
private String PokeAPI;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pokemon_apiactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnCauta = findViewById(R.id.button);
        btnCauta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Executor executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.myLooper());

                PokeAPI = "https://pokeapi.co/api/v2/pokemon/ditto";
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection con = null;
                        try{
                            URL urlAPI = new URL(PokeAPI);
                            con = (HttpURLConnection) urlAPI.openConnection();
                            con.connect();
                            con.setRequestMethod("GET");

                            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
                            BufferedReader reader = new BufferedReader(inputStreamReader);
                            StringBuilder response = new StringBuilder();
                            String linie = null;

                            while((linie=reader.readLine())!=null){
                                response.append(linie);
                            }

                            reader.close();
                            con.disconnect();

                            StringBuilder sb = new StringBuilder();
                            JSONObject jsonObject = new JSONObject(response.toString());

                            sb.append("Abilities:\n");
                            JSONArray jsonArray = jsonObject.getJSONArray("abilities");
                            for(int i=0;i<jsonArray.length();i++){
                                JSONObject abilityObj = jsonArray.getJSONObject(i);
                                JSONObject ability = abilityObj.getJSONObject("ability");
                                String name = ability.getString("name");
                                sb.append(name + "\n");
                            }

                            sb.append("Game indices:\n");
                            JSONArray jsIndices = jsonObject.getJSONArray("game_indices");
                            for(int i=0;i<jsIndices.length();i++){
                                JSONObject index = jsIndices.getJSONObject(i);
                                String indexS = index.getString("game_index");

                                JSONObject version = index.getJSONObject("version");
                                String name = version.getString("name");
                                sb.append(indexS + "-" + name+ "\n");
                            }

                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    TextView tv = findViewById(R.id.idTVPokemon);
                                    tv.setText(sb);
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