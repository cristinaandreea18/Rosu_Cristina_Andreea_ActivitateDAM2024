package com.example.app8;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Database;
import androidx.room.Room;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AdaugaMarkerActivity extends AppCompatActivity {
private MarkerDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_marker);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Spinner sp = findViewById(R.id.idSpMarca);
        ArrayAdapter<CharSequence>adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.marca_marker,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(adapter);

        database = Room.databaseBuilder(getApplicationContext(), MarkerDatabase.class,"Markere.db").build();

        Button btnAdaugaMarker = findViewById(R.id.idBtnAdauga);
        Executor executor = Executors.newSingleThreadExecutor();

        Intent it = getIntent();
        if(it.hasExtra("marker")){
            Marker m = it.getParcelableExtra("marker");

            EditText etCod = findViewById(R.id.idETCod);
            etCod.setText(""+m.getCodCuloare());

            String marca = m.getMarca();
            sp.setSelection(adapter.getPosition(marca));

            EditText etCategorie = findViewById(R.id.idETCategorie);
            etCategorie.setText(m.getCategorie());

            CheckBox cbPermanent = findViewById(R.id.idCBPermanent);
            cbPermanent.setChecked(m.getEstePermanent());
        }

        btnAdaugaMarker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText etCod = findViewById(R.id.idETCod);
                int cod = Integer.parseInt(etCod.getText().toString());

                String marca = sp.getSelectedItem().toString();

                EditText etCategorie = findViewById(R.id.idETCategorie);
                String categorie = etCategorie.getText().toString();

                CheckBox cbPermanent = findViewById(R.id.idCBPermanent);
                Boolean permanent = cbPermanent.isChecked();

                Marker marker = new Marker(cod,marca,categorie,permanent);
                Toast.makeText(getApplicationContext(),marker.toString(),Toast.LENGTH_LONG).show();

                executor.execute(new Runnable() {
                    {
                        try {
                            FileOutputStream file = openFileOutput("Markere.txt",MODE_APPEND);
                            OutputStreamWriter output = new OutputStreamWriter(file);
                            BufferedWriter writer = new BufferedWriter(output);
                            writer.write(marker.toString());

                            writer.close();
                            output.close();
                            file.close();

                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }


                    @Override
                    public void run() {
                     database.daoMarker().insert(marker);
                    }
                });

                Intent it = getIntent();
                it.putExtra("marker",marker);
                setResult(RESULT_OK,it);
                finish();
            }
        });
    }
}