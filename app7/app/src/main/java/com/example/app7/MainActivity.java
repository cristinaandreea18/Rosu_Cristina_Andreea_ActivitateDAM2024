package com.example.app7;

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

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private List<Lumanare> lumanari = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lumanari = new ArrayList();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAdaugaLumanare = findViewById(R.id.idBtnAdauga);
        btnAdaugaLumanare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), AdaugaLumanareActivity.class);
                startActivityForResult(it,1811);
            }
        });

        Button btnLista = findViewById(R.id.idBtnLista);
        btnLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), ListaLumanariActivity.class);
                it.putParcelableArrayListExtra("lumanare", (ArrayList<? extends Parcelable>) lumanari);
                startActivity(it);
            }
        });

        Button btnPreferinte = findViewById(R.id.idBtnPreferinte);
        btnPreferinte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), SharedPreferencesActivity.class);
                startActivity(it);
            }
        });

        Button btnImagini = findViewById(R.id.idBtnImagini);
        btnImagini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), ImaginiActivity.class);
                startActivity(it);
            }
        });

        Button btnAPI = findViewById(R.id.idBtnAPI);
        btnAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), PokemonAPIActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1811){
                Lumanare l = data.getParcelableExtra("lumanare");
                lumanari.add(l);
            }
        }
    }
}