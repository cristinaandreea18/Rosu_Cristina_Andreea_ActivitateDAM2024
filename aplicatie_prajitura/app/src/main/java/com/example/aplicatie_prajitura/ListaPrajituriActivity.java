package com.example.aplicatie_prajitura;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Database;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListaPrajituriActivity extends AppCompatActivity {
    private List<Prajitura> prajituri;
    private int isModificat =0;
    private PrajituraAdapter adapter = null;
    PrajituraDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_prajituri);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Intent it = getIntent();
        //prajituri = it.getParcelableArrayListExtra("listaPrajituri1");

        database = Room.databaseBuilder(getApplicationContext(),PrajituraDatabase.class,"Prajituri.db").build();
        ListView lv = findViewById(R.id.prajituriLV);
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                prajituri = database.daoPrajitura().getPrajituri();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        adapter = new PrajituraAdapter(prajituri,getApplicationContext(),R.layout.row_item);
                        lv.setAdapter(adapter);
                    }
                });
            }
        });


//      ArrayAdapter<Prajitura> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,prajituri);
//      lv.setAdapter(adapter);


//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(getApplicationContext(),prajituri.get(i).toString(),Toast.LENGTH_LONG).show();
//            }
//    });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intentModifica = new Intent(getApplicationContext(),AdaugaPrajituraActivity.class);
                intentModifica.putExtra("prajitura1",prajituri.get(i));
                isModificat = i;
                startActivityForResult(intentModifica,200);
                Toast.makeText(getApplicationContext(),prajituri.get(i).toString(),Toast.LENGTH_LONG).show();
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
                        database.daoPrajitura().deletePrajitura(prajituri.get(i));
                        handler.post(new Runnable() {
                        @Override
                            public void run() {
                                prajituri.remove(i);
                                adapter.notifyDataSetChanged();
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
            if(requestCode==200) {
                Prajitura prajituraModificata = data.getParcelableExtra("prajitura1");

                Executor executor = Executors.newSingleThreadExecutor();
                Handler handler = new Handler(Looper.myLooper());

                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        database.daoPrajitura().updatePrajitura(prajituraModificata);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                prajituri.set(isModificat,prajituraModificata);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    }
                });
            }
        }
    }
}