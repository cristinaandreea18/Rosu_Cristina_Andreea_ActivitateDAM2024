package com.example.app5;

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
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ListaImaginiActivity extends AppCompatActivity {
    private List<Bitmap>imagini = null;
    private List<ImagineDomeniu>lista = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagini = new ArrayList<>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lista_imagini);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        List<String> linkuriImagini = new ArrayList<>();
        linkuriImagini.add("https://nemira.ro/media/catalog/product/cache/1/image/363x/040ec09b1e35df139433887a97daa66f/p/a/partea-intunecata-a-magiei-seria-culorile-magiei-partea-i.jpg");
        linkuriImagini.add("https://nemira.ro/media/catalog/product/cache/1/image/363x/040ec09b1e35df139433887a97daa66f/a/d/adunarea-umbrelor-seria-culorile-magiei-partea-a-ii-a.jpg");
        linkuriImagini.add("https://nemira.ro/media/catalog/product/cache/1/image/363x/040ec09b1e35df139433887a97daa66f/v/e/ve-schwab---invocarea-luminii---c1_2.jpg");

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                for(String link:linkuriImagini){
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
                        lista.add(new ImagineDomeniu("Partea intunecata a magiei",imagini.get(0),"https://nemira.ro/media/catalog/product/cache/1/image/650x/040ec09b1e35df139433887a97daa66f/p/a/partea-intunecata-a-magiei-seria-culorile-magiei-partea-i.jpg"));
                        lista.add(new ImagineDomeniu("Adunarea Umbrelor",imagini.get(1),"https://nemira.ro/media/catalog/product/cache/1/image/650x/040ec09b1e35df139433887a97daa66f/a/d/adunarea-umbrelor-seria-culorile-magiei-partea-a-ii-a.jpg"));
                        lista.add(new ImagineDomeniu("Invocarea luminii",imagini.get(2),"https://nemira.ro/media/catalog/product/cache/1/image/650x/040ec09b1e35df139433887a97daa66f/v/e/ve-schwab---invocarea-luminii---c1_2.jpg"));

                        ListView lv = findViewById(R.id.idLVImagini);
                        ImagineAdapter adapter = new ImagineAdapter(lista,getApplicationContext(),R.layout.imagine_layout);
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