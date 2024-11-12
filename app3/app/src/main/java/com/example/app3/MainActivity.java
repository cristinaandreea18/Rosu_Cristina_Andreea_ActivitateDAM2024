package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private List<Absolvent> absolventi = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        absolventi = new ArrayList<>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
//        Intent it = getIntent();
//        int val1= it.getIntExtra("val1",0);
//        int val2 = it.getIntExtra("val2",0);
//        Toast.makeText(getApplicationContext(),"Suma:"+(val1+val2),Toast.LENGTH_LONG).show();

    Button btnMain = findViewById(R.id.idBtnMain);
    btnMain.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it = new Intent(getApplicationContext(),AdaugaAbsolvent.class);
            startActivityForResult(it,1811);
        }
    });

    Button btnLista = findViewById(R.id.idBtnLista);
    btnLista.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent it = new Intent(getApplicationContext(), ListaAbsolventiActivity.class);
            it.putParcelableArrayListExtra("absolvent", (ArrayList<? extends Parcelable>) absolventi);
            startActivity(it);
        }
    });
    }

//    public void deschide(View v) {
//        Intent it = new Intent(getApplicationContext(), MainActivity2.class);
//        startActivity(it);
//    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK) {
            if(requestCode ==1811) {
                Absolvent a = data.getParcelableExtra("absolvent");
                absolventi.add(a);
            }
        }
    }
}