package com.example.app3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdaugaAbsolvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_absolvent);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spSpecializare = findViewById(R.id.idSpSpecializare);
        ArrayAdapter<CharSequence>specializari = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.specializari,
                android.R.layout.simple_spinner_item
        );
        specializari.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spSpecializare.setAdapter(specializari);

        Button btnAdauga = findViewById(R.id.idBtnAdaugaAbsolvent);
        btnAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etNume = findViewById(R.id.idEtNume);
                String nume = etNume.getText().toString();

                RadioGroup rgFormaFinantare = findViewById(R.id.idRgFormaFinantare);
                int id = rgFormaFinantare.getCheckedRadioButtonId();

                RadioButton rbFormaFinantare = findViewById(id);
                String finantare = rbFormaFinantare.getText().toString();

                String specializare = spSpecializare.getSelectedItem().toString();
                EditText etData = findViewById(R.id.idEtDataInmatriculare) ;
                String data = etData.getText().toString();

                Absolvent ab = new Absolvent(nume,specializare,finantare,data);
                Toast.makeText(getApplicationContext(),ab.toString(),Toast.LENGTH_LONG).show();

                Intent it = getIntent();
                it.putExtra("absolvent",ab);
                setResult(RESULT_OK,it);
                finish();
            }
        });
        Intent it = getIntent();
        if(it.hasExtra("absolvent")) {
            Absolvent a = it.getParcelableExtra("absolvent");
            EditText etNume = findViewById(R.id.idEtNume);
            etNume.setText(a.getNume());

            Spinner spSpec = findViewById(R.id.idSpSpecializare);
            String spec = a.getSpecializare();
            spSpec.setSelection(specializari.getPosition(spec));

            RadioGroup rgFinantare = findViewById(R.id.idRgFormaFinantare);
            String fin = a.getFormaFinantare();
            if("Buget".equals(fin)) {
                ((RadioButton)findViewById(R.id.idRbBuget)).setChecked(true);
            }
            else
            if("Taxa".equals(fin)) {
                ((RadioButton)findViewById(R.id.idRbTaxa)).setChecked(true);
            }
            EditText etData = findViewById(R.id.idEtDataInmatriculare);
            etData.setText(a.getDataInmatriculare());
        }
    }

}