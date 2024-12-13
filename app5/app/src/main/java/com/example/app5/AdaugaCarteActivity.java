package com.example.app5;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AdaugaCarteActivity extends AppCompatActivity {
    private CarteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_carte);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner sp = findViewById(R.id.idSpEditura);
        ArrayAdapter<CharSequence>edituri = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.Edituri,
                android.R.layout.simple_spinner_item
        );
        edituri.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(edituri);

        database = Room.databaseBuilder(getApplicationContext(), CarteDatabase.class,"Carti.db").build();
        Intent it = getIntent();
        if(it.hasExtra("carte")){
            Carte c = it.getParcelableExtra("carte");

            EditText etNume = findViewById(R.id.idETNume);
            EditText etAutor = findViewById(R.id.idETAutor);
            Spinner spEditura = findViewById(R.id.idSpEditura);
            EditText etPret = findViewById(R.id.idETPret);
            RatingBar rating = findViewById(R.id.ratingBar);

            etNume.setText(c.getNume());
            etAutor.setText(c.getAutor());

            String editura = c.getEditura();
            ArrayAdapter<CharSequence>adapter = (ArrayAdapter<CharSequence>) sp.getAdapter();
            int pozitie = adapter.getPosition(editura);

            spEditura.setSelection(pozitie);

            etPret.setText(""+c.getPret());
            rating.setRating(c.getRating());
        }

        Button btnAdaugaCarte = findViewById(R.id.idbtnAdaugare);
        btnAdaugaCarte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etNume = findViewById(R.id.idETNume);
                String nume = etNume.getText().toString();

                EditText etAutor = findViewById(R.id.idETAutor);
                String autor = etAutor.getText().toString();

                String editura = sp.getSelectedItem().toString();

                EditText etPret = findViewById(R.id.idETPret);
                float pret = Float.parseFloat(etPret.getText().toString());

                RatingBar ratingBar = findViewById(R.id.ratingBar);
                float rating = ratingBar.getRating();

                Carte carte = new Carte(nume,autor,editura,pret,rating);
                Toast.makeText(getApplicationContext(),carte.toString(),Toast.LENGTH_LONG).show();

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            FileOutputStream file;
                            file = openFileOutput("carti.txt",MODE_APPEND);
                            OutputStreamWriter output = new OutputStreamWriter(file);
                            BufferedWriter writer = new BufferedWriter(output);
                            writer.write(carte.toString());
                            writer.close();
                            output.close();
                            file.close();
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        database.daoCarte().insert(carte);
                    }});

                Intent it = getIntent();
                it.putExtra("carte",carte);
                setResult(RESULT_OK, it);
                finish();
            }
        });
    }
}