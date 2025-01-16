package com.example.aplicatie_prajitura;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AdaugaPrajituraActivity extends AppCompatActivity {

    PrajituraDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adauga_prajitura);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = Room.databaseBuilder(getApplicationContext(),PrajituraDatabase.class,"Prajituri.db").build();

        Intent it = getIntent();
        if(it.hasExtra("prajitura1")){
            Prajitura p2 = it.getParcelableExtra("prajitura1");

            EditText etNume = findViewById(R.id.idEtNumePrajitura);
            EditText etPret = findViewById(R.id.idEtPret);
            EditText etCantitate = findViewById(R.id.idEtCantitate);
            DatePicker dataExpirareDp = findViewById(R.id.idDpDataExpirare);
            CheckBox checkBoxAreGlazura = findViewById(R.id.idCbAreGlazura);

            etNume.setText(p2.getNume());
            etPret.setText("" + p2.getPret());
            etCantitate.setText("" + p2.getCantitate());

            String[]split = p2.getDataExpirare().split("/");
            int zi = Integer.parseInt(split[0]);
            int luna = Integer.parseInt(split[1]);
            int an = Integer.parseInt(split[2]);

            dataExpirareDp.init(an,luna-1,zi,null);
            checkBoxAreGlazura.setChecked(p2.getAreGlazura());
        }
    }

    public void crearePrajitura(View view) {
        EditText etNume = findViewById(R.id.idEtNumePrajitura);
        String numePrajitura = etNume.getText().toString();

        EditText etPret = findViewById(R.id.idEtPret);
        String StrPret = etPret.getText().toString();
        Double dPret = Double.parseDouble(StrPret);

        EditText etCantitate = findViewById(R.id.idEtCantitate);
        String StrCantitate = etCantitate.getText().toString();
        Double dCantitate = Double.parseDouble(StrCantitate);

        CheckBox checkBoxAreGlazura = findViewById(R.id.idCbAreGlazura);
        boolean areGlazura = checkBoxAreGlazura.isChecked();

        DatePicker dataExpirareDp = findViewById(R.id.idDpDataExpirare);
        int zi = dataExpirareDp.getDayOfMonth();
        int luna = dataExpirareDp.getMonth()+1;
        int an = dataExpirareDp.getYear();

        String dataExpirare = zi + "/" + luna + "/" + an;
        Prajitura p1 = new Prajitura(numePrajitura,dPret,dCantitate,dataExpirare,areGlazura);

        Executor executor = Executors.newSingleThreadExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try{
                    FileOutputStream file;
                    file =openFileOutput("prajituri.txt",MODE_APPEND);
                    OutputStreamWriter output = new OutputStreamWriter(file);
                    BufferedWriter writer = new BufferedWriter(output);
                    writer.write(p1.toString());
                    writer.close();
                    output.close();
                    file.close();

                    CheckBox esteDisponibilOnline = findViewById(R.id.idDisponibilOnline);
                    boolean dispOnline = esteDisponibilOnline.isChecked();
                    if(dispOnline) {
                        //FirebaseDatabase database = FirebaseDatabase.getInstance();
                        //DatabaseReference myRef = database.getReference("prajituri");

                        try{
                            FileOutputStream file2;
                            file2 =openFileOutput("PrajituriDispobileOnline.txt",MODE_APPEND);
                            OutputStreamWriter output2 = new OutputStreamWriter(file2);
                            BufferedWriter writer2 = new BufferedWriter(output2);
                            writer2.write(p1.toString());
                            writer2.close();
                            output2.close();
                            file2.close();
                        }catch (FileNotFoundException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                database.daoPrajitura().insert(p1);
            }
        });

        Intent it = new Intent();
        it.putExtra("prajitura1", p1);
        setResult(RESULT_OK, it);
        finish();
    }
}