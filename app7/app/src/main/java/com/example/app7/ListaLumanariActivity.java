package com.example.app7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;
import android.os.Handler;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ListaLumanariActivity extends AppCompatActivity {
private List<Lumanare> lumanari;
private ArrayAdapter<Lumanare>adapter= null;
private LumanareDatabase database;
private int isModificat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_lumanari);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = Room.databaseBuilder(getApplicationContext(), LumanareDatabase.class,"Lumanari.db").build();
        ListView lv = findViewById(R.id.idLVLumanari);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
               lumanari = database.daoLumanare().selectLumanari();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,lumanari);
                        lv.setAdapter(adapter);
                    }
                });
            }
        });

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            Intent intentModificat = new Intent(getApplicationContext(), AdaugaLumanareActivity.class);
            intentModificat.putExtra("lumanare",lumanari.get(i));
            isModificat=i;
            startActivityForResult(intentModificat,200);
        }
    });

    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
            Executor executor = Executors.newSingleThreadExecutor();
            Handler handler = new Handler(Looper.myLooper());
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    //database.daoLumanare().delete(lumanari.get(i));

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences sp = getSharedPreferences("LumanariFav",MODE_PRIVATE);
                            SharedPreferences.Editor ed = sp.edit();
                            ed.putString(lumanari.get(i).getKey(),lumanari.get(i).toString());
                            ed.commit();
//                            lumanari.remove(i);
//                            adapter.notifyDataSetChanged();
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
        if(resultCode==RESULT_OK){
            if(requestCode==200){
                Lumanare lumanareModificata = data.getParcelableExtra("lumanare");

                Executor executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.myLooper());
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        database.daoLumanare().update(lumanareModificata);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                lumanari.set(isModificat,lumanareModificata);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}