package com.example.seminar4_1098;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdaugaAutomobilActivity extends AppCompatActivity {

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

        Button adaugare = findViewById(R.id.idButonAdaugaMasina);
        adaugare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            EditText etMarca = findViewById(R.id.idMarca);
            String marca = etMarca.getText().toString();

            EditText etAnFabricatie = findViewById(R.id.idAnFabricatie);
            String StrgAnFabricatie = etAnFabricatie.getText().toString();
            int anFabricatie = Integer.parseInt(StrgAnFabricatie);

            EditText etPutere = findViewById(R.id.idPutere);
            String StrgPutere = etPutere.getText().toString();
            int putere = Integer.parseInt(StrgPutere);

            EditText etVitezaMaxima = findViewById(R.id.idVitezaMaxima);
            String StrgVitezaMaxima = etVitezaMaxima.getText().toString();
            int vitezaViteza = Integer.parseInt(StrgVitezaMaxima);

            RadioGroup rgCombustibil = findViewById(R.id.radioGroup);
            int idSelectat = rgCombustibil.getCheckedRadioButtonId();
            RadioButton rbCombustibil = findViewById(idSelectat);
            String tipCombustibil = rbCombustibil.getText().toString();

            Automobil automobil= new Automobil(marca,anFabricatie,tipCombustibil,putere,vitezaViteza);

            Toast.makeText(getApplicationContext(),automobil.toString(),Toast.LENGTH_LONG).show();
            }
        });
    }
}