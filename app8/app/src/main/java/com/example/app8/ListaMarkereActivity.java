package com.example.app8;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Looper;
import android.os.Handler;

import android.provider.ContactsContract;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListaMarkereActivity extends AppCompatActivity {
private List<Marker> markere = null;
private MarkerDatabase database;
private ArrayAdapter<Marker> adapter;
private int isModificat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_markere);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lv = findViewById(R.id.idLVMarkere);
        database = Room.databaseBuilder(getApplicationContext(), MarkerDatabase.class,"Markere.db").build();

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                markere = database.daoMarker().selectAll();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter= new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1, markere);
                        lv.setAdapter(adapter);
                    }
                });
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getApplicationContext(), AdaugaMarkerActivity.class);
                it.putExtra("marker", markere.get(i));
                isModificat = i;
                startActivityForResult(it,200);
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
                        //database.daoMarker().delete(markere.get(i));
                        SharedPreferences sp = getSharedPreferences("MarkereFav",MODE_PRIVATE);
                        SharedPreferences.Editor ed = sp.edit();
                        ed.putString(String.valueOf(markere.get(i).getCodCuloare()),markere.get(i).toString());
                        ed.commit();

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                //markere.remove(i);
                                //adapter.notifyDataSetChanged();
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
            if(requestCode==200){
               Marker MarkerModificat = data.getParcelableExtra("marker");
               Executor executor = Executors.newSingleThreadExecutor();
               Handler handler = new Handler(Looper.myLooper());

               executor.execute(new Runnable() {
                   @Override
                   public void run() {
                       database.daoMarker().update(MarkerModificat);

                   handler.post(new Runnable() {
                       @Override
                       public void run() {
                           markere.set(isModificat,MarkerModificat);
                           adapter.notifyDataSetChanged();
                        }
                     });
                   }
               });
            }
        }
    }
}