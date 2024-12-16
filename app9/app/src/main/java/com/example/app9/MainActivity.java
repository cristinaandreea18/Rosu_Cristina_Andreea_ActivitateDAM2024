package com.example.app9;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import android.os.Looper;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.room.Room;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
private String API_KEY = "ef2949d6c25645e08cb16331ee09f2a9";
private List<Book>books = null;
private BookDatabase database;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        books = new ArrayList<>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        database = Room.databaseBuilder(getApplicationContext(),BookDatabase.class,"Books.db").build();

        Button btnMain = findViewById(R.id.idBtnMain);
        Button btnXML = findViewById(R.id.idBtnXML);
        btnXML.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(getApplicationContext(), ParsareXMLActivity.class);
                startActivity(it);
            }
        });

        Button btnSharedPreferences = findViewById(R.id.idBtnSharedPreferences);
        btnSharedPreferences.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("BooksFav",MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                for(Book book:books)
                    ed.putString(String.valueOf(book.getId()),book.toString());
                ed.commit();
            }
        });

        String API = "https://api.bigbookapi.com/18810398" + "?api-key=" + API_KEY;
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());
                executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpURLConnection con = null;
                        try {
                            URL url = new URL(API);
                            con = (HttpURLConnection) url.openConnection();
                            con.connect();
                            con.setRequestMethod("GET");

                            InputStreamReader inputStreamReader = new InputStreamReader(con.getInputStream());
                            BufferedReader reader = new BufferedReader(inputStreamReader);
                            StringBuilder sb = new StringBuilder();
                            String linie = null;
                            while ((linie = reader.readLine()) != null) {
                                sb.append(linie);
                            }

                            reader.close();
                            con.disconnect();

                            JSONObject book = new JSONObject(sb.toString());
                            int id = book.getInt("id");
                            String title = book.getString("title");

                            int idAuthor = 0;
                            String name = "";
                            JSONArray authorsArr = book.getJSONArray("authors");
                            for (int i = 0; i < authorsArr.length(); i++) {
                                JSONObject authorObj = authorsArr.getJSONObject(i);
                                idAuthor = authorObj.getInt("id");
                                name = authorObj.getString("name");
                            }

                            String publish_date = book.getString("publish_date");
                            int pages = book.getInt("number_of_pages");

                            JSONObject ratingObj = book.getJSONObject("rating");
                            Double ratingD = ratingObj.getDouble("average");
                            Float rating = ratingD.floatValue();

                            Book b = new Book(id, title, idAuthor, name, publish_date, pages, rating);
                            books.add(b);
                            //Toast.makeText(getApplicationContext(),b.toString(),Toast.LENGTH_LONG).show();
                            database.daoBook().insert(b);

                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                btnMain.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        Intent it = new Intent(getApplicationContext(), ShowDatabaseActivity.class);
                                        it.putParcelableArrayListExtra("books", (ArrayList<? extends Parcelable>) books);
                                        startActivity(it);
                                    }
                                });
                            }
                        });
                    };
                });
            }
    }
