package com.example.aplicatie2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void deschidereActivitate(View v){
        Intent it = new Intent(getApplicationContext(),Activitate2.class);
        startActivity(it);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("activitate","onPause");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("activitate","onResume");
        Toast.makeText(this,R.string.mesaj,Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("activitate","onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activitate","onDestroy");
    }
}