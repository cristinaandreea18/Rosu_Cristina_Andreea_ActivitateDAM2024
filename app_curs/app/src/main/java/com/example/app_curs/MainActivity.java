package com.example.app_curs;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.snackbar.Snackbar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
private TextView myTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });

        //un prim ex
//        final TextView tvmesaj = findViewById(R.id.tv_mesaj);
//        final EditText edMesaj = findViewById(R.id.ed_mesaj);
//        Button btnSend = findViewById(R.id.button_send);
//
//        btnSend.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String mesaj = edMesaj.getText().toString();
//                tvmesaj.setText(mesaj);
//                edMesaj.setText("");
//            }
//        });
        //
//        final Button myBtn;
//        myBtn = findViewById(R.id.my_btn);
//        myTv = findViewById(R.id.my_tv);

        //myBtn.setOnClickListener(this);
    }

    //
    @Override
    public void onClick(View view) {
//        switch(view.getId()){
//            case R.id.my_btn:
//                myTv.setText("Text schimbat cu interfata View.OnClick(...)");
//                break;

//        if(view.getId()==R.id.my_btn)
//        {
//            myTv.setText("Text schimbat cu interfata View.OnClick(...)");
//        }
    }

    //
//    public void schimbaText(View view) {
//        myTv.setText("Text schimbat cu metoda pe onClick din layout");
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();
        inf.inflate(R.menu.my_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.meniu_despre)
        {
            View view = findViewById(R.id.main);
            //Toast.makeText(this,"Afisare continut",Toast.LENGTH_LONG).show();
            Snackbar snk = Snackbar.make(view,"Autor:Andreea",Snackbar.LENGTH_LONG);
            snk.setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    snk.dismiss();
                }
            });
            snk.show();
        }
        else if(item.getItemId()==R.id.meniu_shop)
        {
            Toast.makeText(this,"Fereastra shopping",Toast.LENGTH_LONG).show();
        }
        return super.onOptionsItemSelected(item);
    }
}

