package com.example.aplicatie_prajitura;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private List<Prajitura> prajituri = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prajituri = new ArrayList<>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button myBtn = findViewById(R.id.idButonPrincipal);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), AdaugaPrajituraActivity.class);
                startActivityForResult(it, 1811);
            }
        });

        Button listaPrajituriBtn = findViewById(R.id.idButonPrajituri);
        listaPrajituriBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), ListaPrajituriActivity.class);
                it.putParcelableArrayListExtra("listaPrajituri1", (ArrayList<? extends Parcelable>) prajituri);
                startActivity(it);
            }
        });
    }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == 1811 ) {
                if(resultCode == RESULT_OK)
                {
                    Prajitura prajitura = data.getParcelableExtra("prajitura1");
                    prajituri.add(prajitura);
                    //prajituri.add(prajitura);
                    //TextView tv = findViewById()
                    //tv.setText(prajitura.toString()
//                if (prajitura != null) {
//                    Toast.makeText(this, prajitura.toString(), Toast.LENGTH_LONG).show();
//                }
            }
            }
    }
}