package com.example.app2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AdaugaStudentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button btnAdaugaStudent = findViewById(R.id.idBtnAdaugaStudent);

        Spinner spAnStudiu = findViewById(R.id.idSpAnStudiu);
        ArrayAdapter<CharSequence>adapter1 = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.anStudiu,
                android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spAnStudiu.setAdapter(adapter1);

        Spinner spCurs = findViewById(R.id.idSpCurs);
        ArrayAdapter<CharSequence>adapter2 = ArrayAdapter.createFromResource(getApplicationContext(),
                R.array.cursuri,
                android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spCurs.setAdapter(adapter2);

        btnAdaugaStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etNume = findViewById(R.id.idEtNume);
                String nume = etNume.getText().toString();

                RadioGroup rgGen = findViewById(R.id.idRgGen);
                int id = rgGen.getCheckedRadioButtonId();
                RadioButton rbGen = findViewById(id);
                String gen = rbGen.getText().toString();

                String anStr = spAnStudiu.getSelectedItem().toString();
                int an =Integer.parseInt(anStr);
                String curs = spCurs.getSelectedItem().toString();

                RatingBar rbRating = findViewById(R.id.idRbRating);
                float rating = rbRating.getRating();

                Student stud = new Student(nume,gen,an,curs,rating);
                Toast.makeText(getApplicationContext(),stud.toString(),Toast.LENGTH_LONG).show();

                Intent it = new Intent();
                it.putExtra("student",stud);
                setResult(RESULT_OK,it);
                finish();
            }
        });
    }
}