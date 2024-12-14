package com.example.app7;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.InputDeviceCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


public class ImaginiActivity extends AppCompatActivity {
private List<Bitmap> imagini=null;
private List<ImagineDomeniu>lista = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagini = new ArrayList<>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imagini);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lv = findViewById(R.id.idLVImagini);
        List<String>linkuri = new ArrayList<>();
        linkuri.add("https://media.bathandbodyworks.ro/catalog/product/y/0/y0nEJ_028005576.jpg?store=bbw_ro&image-type=small_image&auto=webp&format=pjpg&width=400&height=500&fit=cover");
        linkuri.add("https://frankfurt.apollo.olxcdn.com/v1/files/628ai1db4uil1-RO/image;s=750x1000");
        linkuri.add("https://media.bathandbodyworks.ro/catalog/product/b/3/b3a6df4019edfe2804556249b318efd57604625b_5109.jpg?store=bbw_ro&image-type=image&auto=webp&format=pjpg&width=405&height=540&fit=cover");

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
               for(String link:linkuri){
                   HttpURLConnection con = null;
                   try{
                       URL url = new URL(link);
                       con = (HttpURLConnection) url.openConnection();
                       InputStream is = con.getInputStream();
                       imagini.add(BitmapFactory.decodeStream(is));

                   } catch (MalformedURLException e) {
                       throw new RuntimeException(e);
                   } catch (IOException e) {
                       throw new RuntimeException(e);
                   }
               }
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        lista = new ArrayList<>();

                        lista.add(new ImagineDomeniu("Strawberry melon",imagini.get(0),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fbathandbodyworks.ro%2Flumanari%2Ftoate-lumanarile%2Flumanari-cu-un-singur-fitil.html&psig=AOvVaw2YeHusjFDJ8gYJNPduoCFW&ust=1734283634090000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCPCDhJTkp4oDFQAAAAAdAAAAABAE"));
                        lista.add(new ImagineDomeniu("Bath and Body Works",imagini.get(1),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.olx.ro%2Fd%2Foferta%2Fbath-body-works-lumanari-parfumate-411-gr-import-usa-IDeuQJ8.html&psig=AOvVaw2YeHusjFDJ8gYJNPduoCFW&ust=1734283634090000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCPCDhJTkp4oDFQAAAAAdAAAAABAP"));
                        lista.add(new ImagineDomeniu("Under the Christmas tree",imagini.get(2),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fbathandbodyworks.ro%2Funder-the-christmas-tree-lumanare-cu-trei-fitiluri-14-5-oz-411-g.html&psig=AOvVaw0wdFkX4DogC6Vt1sZ40ZFL&ust=1734283734574000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCPjihcXkp4oDFQAAAAAdAAAAABAE"));

                        AdapterImagine adapter = new AdapterImagine(lista, getApplicationContext(),R.layout.imagine_layout);
                        lv.setAdapter(adapter);

                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent it = new Intent(getApplicationContext(), WebViewActivity.class);
                                it.putExtra("link",lista.get(i).getLink());
                                startActivity(it);
                            }
                        });
                    }
                });
            }
        });

    }
}