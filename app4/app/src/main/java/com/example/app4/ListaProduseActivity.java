package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ListaProduseActivity extends AppCompatActivity {
private List<Produs> produse = null;
private ProdusAdapter adapterProd = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_produse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent it = getIntent();
        produse = it.getParcelableArrayListExtra("produs");
        ListView lvProduse = findViewById(R.id.idLv);

        adapterProd = new ProdusAdapter(R.layout.item_row,produse, getApplicationContext());
        lvProduse.setAdapter(adapterProd);
//        ArrayAdapter<Produs>adapter = new ArrayAdapter(getApplicationContext(),
//                android.R.layout.simple_list_item_activated_1,produse);
//        lvProduse.setAdapter(adapter);


        lvProduse.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),produse.get(i).toString(),Toast.LENGTH_LONG).show();

            }
        });

        lvProduse.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                produse.remove(i);
                adapterProd.notifyDataSetChanged();
                return false;
            }
        });
    }

}