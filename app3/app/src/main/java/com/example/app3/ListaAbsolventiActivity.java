package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class ListaAbsolventiActivity extends AppCompatActivity {
private List<Absolvent> absolventi= null;
private AbsolventAdapter adaterAbs = null;
private int isModificat = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        Button btn = findViewById(R.id.button);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent it = new Intent(getApplicationContext(), MainActivity.class);
//                it.putExtra("val1",13);
//                it.putExtra("val2",11);
//                startActivity(it);
//            }
//        });

        Intent it = getIntent();
        absolventi = it.getParcelableArrayListExtra("absolvent");

        ListView lv = findViewById(R.id.idLvAbsolventi);

//        ArrayAdapter<Absolvent>adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_activated_1,absolventi);
//        lv.setAdapter(adapter);

        adaterAbs = new AbsolventAdapter(absolventi,R.layout.item_row, getApplicationContext());
        lv.setAdapter(adaterAbs);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(getApplicationContext(),absolventi.get(i).toString(),Toast.LENGTH_LONG);
                Intent it = new Intent(getApplicationContext(), AdaugaAbsolvent.class);
                it.putExtra("absolvent",absolventi.get(i));
                isModificat = i;
                setResult(RESULT_OK,it);
                startActivityForResult(it,200);
            }
        });

        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
//                absolventi.remove(i);
//                adapter.notifyDataSetChanged();
                return false;
            }
        });
    }

}