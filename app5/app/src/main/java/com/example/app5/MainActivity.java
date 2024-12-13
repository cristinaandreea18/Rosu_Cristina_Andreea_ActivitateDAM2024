package com.example.app5;

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
private List <Carte> carti = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        carti = new ArrayList<>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAdaugaCarte = findViewById(R.id.idBtnAdaugareCarte);
        btnAdaugaCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), AdaugaCarteActivity.class);
                startActivityForResult(it,1811);
            }
        });

        Button btnListaCarti = findViewById(R.id.idBtnListaCarti);
        btnListaCarti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), ListaCartiActivity.class);
                it.putParcelableArrayListExtra("carte", (ArrayList<? extends Parcelable>) carti);
                startActivity(it);
            }
        });

        Button btnListaImagini = findViewById(R.id.idBtnListaImagini);
        btnListaImagini.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), ListaImaginiActivity.class);
                startActivity(it);
            }
        });

        Button btnCautaAPI = findViewById(R.id.idBtnAPI);
        btnCautaAPI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), BigBookAPIActivity.class);
                startActivity(it);
            }
        });

        Button btnPreferences = findViewById(R.id.idBtnSharedPreferences);
        btnPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), SharedPreferencesActivity.class);
                startActivity(it);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            if(requestCode==1811){
                Carte carte = data.getParcelableExtra("carte");
                carti.add(carte);
            }
        }
    }
}