package com.example.app6;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListaPisiciActivity extends AppCompatActivity {
private List<Pisica> pisici = null;
private PisicaDatabase database;
private int isModificat = 0;
private ArrayAdapter<Pisica> adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_pisici);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = Room.databaseBuilder(getApplicationContext(), PisicaDatabase.class,"Pisici.db").build();
        ListView lv = findViewById(R.id.idLVpPisici);

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());
        executor.execute(new Runnable() {
            @Override
            public void run() {
                pisici = database.daoPisica().getPisici();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,pisici);
                        lv.setAdapter(adapter);
                    }
                });
            }
        });

    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

            Intent intentModificat = new Intent(getApplicationContext(), AdaugaPisicaActivity.class);
            intentModificat.putExtra("pisica",pisici.get(i));
            isModificat = i;
            startActivityForResult(intentModificat,666);

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
                    //database.daoPisica().deletePisica(pisici.get(i));

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences sp = getSharedPreferences("PisiciFav",MODE_PRIVATE);
                            SharedPreferences.Editor ed = sp.edit();
                            ed.putString(pisici.get(i).getKey(),pisici.get(i).toString());
                            ed.commit();

//                            pisici.remove(i);
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
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==666){
                Pisica pisicaUpdate = data.getParcelableExtra("pisica");

                Executor executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.myLooper());

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        database.daoPisica().updatePisica(pisicaUpdate);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                pisici.set(isModificat,pisicaUpdate);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        }
    }
}