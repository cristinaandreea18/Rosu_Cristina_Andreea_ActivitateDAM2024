package com.example.app10;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
private List<Cursa> curse = null;
private CursaDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        curse = new ArrayList<>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = Room.databaseBuilder(getApplicationContext(),CursaDatabase.class,"Curse.db").build();

        Button btnIncarca = findViewById(R.id.idBtnIncarca);
        Button btnSalveaza = findViewById(R.id.idBtnSalveaza);
        Button btnStergeTot = findViewById(R.id.idBtnStergeTot);

        SharedPreferences sp = getSharedPreferences("CurseFav",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                int nr = 0;
                List<Cursa>curse = database.daoCursa().getAll();

                for(Cursa c:curse){
                    if(c.getEsteManual()){
                        nr++;
                    }
                }
                ed.putInt("numar",nr);
                ed.commit();

                int finalNr = nr;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                            Toast.makeText(getApplicationContext(),"Numar inregistrari manuale:" + finalNr,Toast.LENGTH_LONG).show();
                    }
                });
            }
        });

        btnStergeTot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<Cursa>curseManuale = new ArrayList<>();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        for(Cursa c:curse){
                            if(c.getEsteManual()){
                                database.daoCursa().delete(c);
                                curseManuale.add(c);
                            }
                        }

                        curse.removeAll(curseManuale);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EditText rezultat = findViewById(R.id.idETAfisare);
                                rezultat.setText(curse.toString());
                            }
                        });
                    }
                });
            }
        });

        btnSalveaza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etId = findViewById(R.id.idETId);
                EditText etDestinatie = findViewById(R.id.idETDestinatie);
                EditText etDistanta = findViewById(R.id.idETDistanta);

                int id = Integer.parseInt(etId.getText().toString());
                String destinatie = etDestinatie.getText().toString();
                int  distanta = Integer.parseInt(etDistanta.getText().toString());

                Boolean esteManual = Boolean.valueOf("true");

                Cursa cursa = new Cursa(id,destinatie,distanta,esteManual);
                curse.add(cursa);
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder sb = new StringBuilder();
                        database.daoCursa().insert(cursa);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                for(Cursa c:curse){
                                    sb.append(c.toString());
                                }

                                EditText et = findViewById(R.id.idETAfisare);
                                et.setText(sb);
                            }
                        });
                    }
                });
            }
        });

        String urlJSON = "https://pdm.ase.ro/curse.json";
        btnIncarca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        StringBuilder rezultat = new StringBuilder();
                        HttpURLConnection con = null;
                        try{
                            URL url = new URL(urlJSON);
                            con = (HttpURLConnection) url.openConnection();
                            con.connect();
                            con.setRequestMethod("GET");

                            InputStreamReader is = new InputStreamReader(con.getInputStream());
                            BufferedReader reader = new BufferedReader(is);
                            StringBuilder sb = new StringBuilder();
                            String linie = null;
                            while((linie=reader.readLine())!=null){
                                sb.append(linie);
                            }

                            JSONObject jsonObject = new JSONObject(sb.toString());
                            JSONArray curseArr = jsonObject.getJSONArray("curse");
                            for(int i=0;i<curseArr.length();i++){
                                JSONObject cursa = curseArr.getJSONObject(i);
                                int id = cursa.getInt("id");
                                String destinatie = cursa.getString("destinatie");
                                int distanta = cursa.getInt("distanta");
                                Boolean esteManual = Boolean.valueOf("false");

                                Cursa c = new Cursa(id,destinatie,distanta,esteManual);
                                curse.add(c);
                                database.daoCursa().insert(c);
                                rezultat.append(c.toString());
                            }


                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                EditText etRez = findViewById(R.id.idETAfisare);
                                etRez.setText(rezultat.toString());
                            }
                        });
                    }
                });
            }
        });

    }
}