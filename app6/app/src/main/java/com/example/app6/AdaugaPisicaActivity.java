package com.example.app6;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import androidx.room.Room;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AdaugaPisicaActivity extends AppCompatActivity {
private PisicaDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_pisica);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner sp = findViewById(R.id.idSpRasa);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.rase,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(adapter);

        database = Room.databaseBuilder(getApplicationContext(), PisicaDatabase.class,"Pisici.db").build();
        Intent it = getIntent();
        int id;
        if(it.hasExtra("pisica")){
            Pisica p = it.getParcelableExtra("pisica");

            EditText etStapan = findViewById(R.id.idETNumeStapan);
            Spinner spRasa = findViewById(R.id.idSpRasa);
            EditText etNume = findViewById(R.id.idEtNume);
            RadioGroup rgGen = findViewById(R.id.idRgGen);

            etStapan.setText(p.getStapan());

            String rasa = p.getRasa();
            sp.setSelection(adapter.getPosition(rasa));

            etNume.setText(p.getNume());

            String gen = p.getGen();
            if("Femela".equals(gen)){
                ((RadioButton)findViewById(R.id.idRbFemela)).setChecked(true);
            }
            else
            if("Mascul".equals(gen)){
                ((RadioButton)findViewById(R.id.idRbMascul)).setChecked(true);
            }
        }

        Button btnAdauga = findViewById(R.id.idBtnAdaugaPisica);
        btnAdauga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etStapan = findViewById(R.id.idETNumeStapan);
                String stapan = etStapan.getText().toString();

                String rasa = sp.getSelectedItem().toString();

                EditText etNume = findViewById(R.id.idEtNume);
                String nume = etNume.getText().toString();

                RadioGroup rgGen = findViewById(R.id.idRgGen);
                int id = rgGen.getCheckedRadioButtonId();

                RadioButton rbGen = findViewById(id);
                String gen = rbGen.getText().toString();

                Pisica pisica = new Pisica(stapan,nume,rasa,gen);
                Toast.makeText(getApplicationContext(),pisica.toString(),Toast.LENGTH_LONG).show();

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            FileOutputStream file = openFileOutput("pisici.txt",MODE_APPEND);
                            OutputStreamWriter output = new OutputStreamWriter(file);
                            BufferedWriter writer = new BufferedWriter(output);
                            writer.write(pisica.toString());

                            writer.close();
                            output.close();
                            file.close();
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                        database.daoPisica().insert(pisica);
                    }
                });

                Intent it = new Intent();
                it.putExtra("pisica",pisica);
                setResult(RESULT_OK,it);
                finish();
            }
        });
    }
}