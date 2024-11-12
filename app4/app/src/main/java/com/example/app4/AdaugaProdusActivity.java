package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdaugaProdusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_produs);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner spCategorie = findViewById(R.id.idSpCategorie);
        ArrayAdapter<CharSequence>categorii = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.categorii,
                android.R.layout.simple_spinner_item
        );
        categorii.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spCategorie.setAdapter(categorii);

        Button btnAdaugaStudent = findViewById(R.id.idBtnAdaugaProdus);
        btnAdaugaStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etDenumire = findViewById(R.id.idEtDenumire);
                String denumire = etDenumire.getText().toString();

                String categorie = spCategorie.getSelectedItem().toString();

                RadioGroup rgUnitate = findViewById(R.id.idRgUnitate);
                int id = rgUnitate.getCheckedRadioButtonId();
                RadioButton rbUnitate = findViewById(id);
                String unitate = rbUnitate.getText().toString();

                EditText etPret = findViewById(R.id.idEtPret);
                String pretS = etPret.getText().toString();
                Float pret = Float.parseFloat(pretS);

                EditText etData = findViewById(R.id.idEtData);
                String data = etDenumire.getText().toString();

                CheckBox cbBio = findViewById(R.id.idCbEsteBio);
                boolean bio = cbBio.isChecked();

                Produs p = new Produs(categorie,denumire,unitate,pret,bio,data);
                Toast.makeText(getApplicationContext(),p.toString(),Toast.LENGTH_LONG).show();
                Intent it = getIntent();
                it.putExtra("produs",p);
                setResult(RESULT_OK,it);
                finish();
            }
        });
    }
}