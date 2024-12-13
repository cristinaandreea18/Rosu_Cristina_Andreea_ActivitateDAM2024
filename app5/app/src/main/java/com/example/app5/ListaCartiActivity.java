package com.example.app5;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListaCartiActivity extends AppCompatActivity {
private List<Carte> carti = null;
private AdapterCarte adapterCarte = null;
private int isModificat = 0;
private CarteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_carti);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        Intent it = getIntent();
//        carti = it.getParcelableArrayListExtra("carte");

        database = Room.databaseBuilder(getApplicationContext(), CarteDatabase.class,"Carti.db").build();
        ListView lvCarti = findViewById(R.id.idLVListaCarti);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                carti = database.daoCarte().getCarti();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapterCarte = new AdapterCarte(carti,getApplicationContext(),R.layout.item_row);
                        lvCarti.setAdapter(adapterCarte);
                    }
                });
            }
        });

        lvCarti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),carti.get(i).toString(),Toast.LENGTH_LONG).show();
                Intent it = new Intent(getApplicationContext(), AdaugaCarteActivity.class);
                it.putExtra("carte",carti.get(i));
                isModificat = i;
                startActivityForResult(it,999);
            }
        });

        lvCarti.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Executor executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.myLooper());

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        //database.daoCarte().deleteCarte(carti.get(i));

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                SharedPreferences sp = getSharedPreferences("obiecteFav",MODE_APPEND);
                                SharedPreferences.Editor ed = sp.edit();
                                ed.putString(carti.get(i).getKey(),carti.get(i).toString());
                                ed.commit();

                                //carti.remove(i);
                                //adapterCarte.notifyDataSetChanged();
                            }
                        });
                    }
                });
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            if(requestCode == 999){
                Carte carteModificata = data.getParcelableExtra("carte");

                Executor executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.myLooper());

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        database.daoCarte().updateCarte(carteModificata);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                carti.set(isModificat,carteModificata);
                                adapterCarte.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        }
    }
}