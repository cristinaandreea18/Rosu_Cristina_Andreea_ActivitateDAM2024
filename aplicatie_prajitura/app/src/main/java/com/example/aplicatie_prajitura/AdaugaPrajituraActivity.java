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

public class AdaugaPrajituraActivity extends AppCompatActivity {

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

        Button btnAdaugare = findViewById(R.id.idBtnAdaugaPrajitura);
        btnAdaugare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
                //Toast.makeText(getApplicationContext(),p1.toString(),Toast.LENGTH_LONG).show();

                Intent it = new Intent();
                it.putExtra("prajitura1", p1);
                setResult(RESULT_OK, it);
                finish();//dispare din varful stivei
            }
        });
    }
}