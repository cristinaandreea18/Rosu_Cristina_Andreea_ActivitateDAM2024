package com.example.aplicatie_prajitura;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ListaPrajituriActivity extends AppCompatActivity {
    private List<Prajitura> prajituri;
    private int isModificat =0;
    private PrajituraAdapter adapter = null;

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

        Intent it = getIntent();
        prajituri = it.getParcelableArrayListExtra("listaPrajituri1");

        ListView lv = findViewById(R.id.prajituriLV);

//      ArrayAdapter<Prajitura> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,prajituri);
//      lv.setAdapter(adapter);

        adapter = new PrajituraAdapter(prajituri,getApplicationContext(),R.layout.row_item);
        lv.setAdapter(adapter);

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
                prajituri.remove(i);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==200) {
                prajituri.set(isModificat,data.getParcelableExtra("prajitura1"));
                adapter.notifyDataSetChanged();
            }

        }
    }

}