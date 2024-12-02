package com.example.aplicatie_prajitura;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
private List<Prajitura> prajituri= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        prajituri = new ArrayList<>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if(itemId == R.id.action_add){
            Intent it = new Intent(getApplicationContext(), AdaugaPrajituraActivity.class);
            startActivityForResult(it, 1811);
        }
        else
        if(itemId == R.id.action_view_list){
            Intent it = new Intent(getApplicationContext(), ListaPrajituriActivity.class);
            it.putParcelableArrayListExtra("listaPrajituri1", (ArrayList<? extends Parcelable>) prajituri);
            startActivity(it);
        }
        else
        if(itemId == R.id.action_view_images){
            Intent it = new Intent(getApplicationContext(), ListaImaginiActivity.class);
            startActivity(it);
        }
        else
        if(itemId == R.id.action_api_accuWeather){
            Intent it = new Intent(getApplicationContext(), AccuWeatherActivity.class);
            startActivity(it);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1811 ) {
            if(resultCode == RESULT_OK) {
                Prajitura prajitura = data.getParcelableExtra("prajitura1");
                prajituri.add(prajitura);
            }
        }
    }
}