package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ListaStudenti extends AppCompatActivity {
private List<Student> studenti = null;
private int isModificat = 0;
private StudentAdapter adapter = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_studenti);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent it = getIntent();
        studenti = it.getParcelableArrayListExtra("listaStudenti");

        ListView lvStudenti = findViewById(R.id.idLvStudenti);
//        ArrayAdapter<Student> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_activated_1,studenti);
//        lvStudenti.setAdapter(adapter);
         adapter = new StudentAdapter(getApplicationContext(),R.layout.row_item,studenti);
         lvStudenti.setAdapter(adapter);

        lvStudenti.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent it = new Intent(getApplicationContext(), AdaugaStudentActivity.class);
                it.putExtra("student",studenti.get(i));
                isModificat = i;
                startActivityForResult(it,200);
                Toast.makeText(getApplicationContext(),studenti.get(i).toString(),Toast.LENGTH_LONG);
            }
        });

        lvStudenti.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                studenti.remove(i);
                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

}