package com.example.app4;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Menu;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity2 extends AppCompatActivity {
    private List<Produs> produse =null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        produse = new ArrayList<>();
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
        if (itemId == R.id.action_add) {
            Intent it = new Intent(getApplicationContext(), AdaugaProdusActivity.class);
            startActivityForResult(it, 1811);
        }
        else if(itemId == R.id.action_view_list ) {
            Intent it2 = new Intent(getApplicationContext(), ListaProduseActivity.class);
            it2.putParcelableArrayListExtra("produs", (ArrayList<? extends Parcelable>) produse);
            startActivity(it2);
        }
        else if(itemId ==  R.id.action_view_images){
                Intent it3 = new Intent(getApplicationContext(), ListaImaginiActivity.class);
                startActivity(it3);
        }
        else if(itemId ==  R.id.action_rating_product){
            Intent it4 = new Intent(getApplicationContext(), RatingProduseActivity.class);
            startActivity(it4);
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK) {
            if(requestCode==1811) {
                Produs p = data.getParcelableExtra("produs");
                produse.add(p);
            }
        }
    }
}