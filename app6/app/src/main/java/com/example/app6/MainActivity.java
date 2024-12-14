package com.example.app6;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private List<Pisica> pisici = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pisici = new ArrayList<>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Button btnMain = findViewById(R.id.idBtnMain);
        btnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), AdaugaPisicaActivity.class);
                startActivityForResult(it,1811);
            }
        });

        Button btnLista = findViewById(R.id.idBtnLista);
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), ListaPisiciActivity.class);
                it.putParcelableArrayListExtra("pisica", (ArrayList<? extends Parcelable>) pisici);
                startActivity(it);
            }
        });

        Button btnAPI = findViewById(R.id.idBtnAPI);
        btnAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), ApiNinjasAnimalsActivity.class);
                startActivity(it);
            }
        });

        Button btnPisiciFav = findViewById(R.id.idBtnPisici);
        btnPisiciFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), PisiciFavoriteActivity.class);
                startActivity(it);
            }
        });

        Button btnImagini = findViewById(R.id.idBtnImagini);
        btnImagini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), ImaginiPisiciActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){
            if(requestCode==1811){
                Pisica pisica = data.getParcelableExtra("pisica");
                pisici.add(pisica);
            }
        }
    }
}