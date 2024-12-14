package com.example.app7;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
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

public class AdaugaLumanareActivity extends AppCompatActivity {
private LumanareDatabase database;
private  Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_lumanare);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

       spinner = findViewById(R.id.idSPAroma);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getApplicationContext(),
                R.array.arome,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        database = Room.databaseBuilder(getApplicationContext(), LumanareDatabase.class, "Lumanari.db").build();

        Intent it = getIntent();
        if (it.hasExtra("lumanare")) {
            Lumanare l = it.getParcelableExtra("lumanare");

            EditText etCod = findViewById(R.id.idETCod);
            etCod.setText(String.valueOf(l.getCod()));

            Spinner sp = findViewById(R.id.idSPAroma);
            String aroma = l.getAroma();
            sp.setSelection(adapter.getPosition(aroma));

            EditText etTimpArdere = findViewById(R.id.idETTimpArdere);
            etTimpArdere.setText(String.valueOf(l.getTimpArdere()));

            EditText etNrFitiluri = findViewById(R.id.idETnrFitiluri);
            etNrFitiluri.setText(String.valueOf(l.getNrFitiluri()));
        }
    }

        public void creareLumanare(View view){
                EditText etCod = findViewById(R.id.idETCod);
                int cod = Integer.parseInt(etCod.getText().toString());

                String aroma = spinner.getSelectedItem().toString();

                EditText etTimpArdere = findViewById(R.id.idETTimpArdere);
                int timp = Integer.parseInt(etTimpArdere.getText().toString());

                EditText etNrFitiluri = findViewById(R.id.idETnrFitiluri);
                int fitiluri = Integer.parseInt(etNrFitiluri.getText().toString());

                Lumanare lumanare = new Lumanare(cod,aroma,fitiluri,timp);
                Toast.makeText(getApplicationContext(),lumanare.toString(),Toast.LENGTH_LONG).show();

                Executor executor = Executors.newSingleThreadExecutor();
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            FileOutputStream file = openFileOutput("lumanari.txt",MODE_APPEND);
                            OutputStreamWriter output = new OutputStreamWriter(file);
                            BufferedWriter writer = new BufferedWriter(output);

                            writer.write(lumanare.toString()+"\n");
                            writer.close();
                            output.close();
                            file.close();
                        } catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }

                        database.daoLumanare().insert(lumanare);
                    }
                });

                Intent it = new Intent();
                it.putExtra("lumanare",lumanare);
                setResult(RESULT_OK,it);
                finish();
            }
}