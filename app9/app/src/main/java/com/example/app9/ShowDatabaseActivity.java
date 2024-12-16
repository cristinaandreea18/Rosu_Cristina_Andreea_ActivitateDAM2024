package com.example.app9;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ShowDatabaseActivity extends AppCompatActivity {
    private BookDatabase database ;
    private List<Book> books= null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_database);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        database = Room.databaseBuilder(getApplicationContext(), BookDatabase.class,"Books.db").build();
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                books = database.daoBook().selectAll();

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ListView lv = findViewById(R.id.idLVBooks);
                        ArrayAdapter<Book> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,books);
                        lv.setAdapter(adapter);
                    }
                });
            }
        });

    }
}