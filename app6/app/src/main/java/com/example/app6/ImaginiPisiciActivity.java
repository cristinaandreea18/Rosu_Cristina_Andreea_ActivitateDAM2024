package com.example.app6;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Looper;

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
import android.os.Handler;
import android.widget.ListView;

public class ImaginiPisiciActivity extends AppCompatActivity {
private List<Bitmap>imagini = null;
private List<ImagineDomeniu>lista = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagini = new ArrayList<>();
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_imagini_pisici);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        List<String>linkuri = new ArrayList<>();
        linkuri.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ4YA42sWaUw88GH1RHtlMRYOmipCMF72CHhw&s");
        linkuri.add("https://mediacdn.libertatea.ro/unsafe/800x600/smart/filters:format(webp):contrast(8):quality(75)/https://mediacdn.libertatea.ro/unsafe/870x0/smart/filters:format(webp):contrast(8):quality(75)/https://static4.libertatea.ro/wp-content/uploads/2020/09/british-shorthair-cele-mai-frumoase-pisici.jpg");
        linkuri.add("https://img.a1.ro/0/2018/8/17/793369/15345156119d37d6a6.jpg?w=800&h=452&c=1");

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
                        lista= new ArrayList<>();
                        lista.add(new ImagineDomeniu("Luna",imagini.get(0),"https://www.google.com/imgres?q=imagini%20pisici&imgurl=https%3A%2F%2Fstatic.eva.ro%2Fimg%2Fauto_resized%2Fdb%2Farticle%2F200%2F553%2F847537l-320x200-b-a8ee1f3b.jpg%3F&imgrefurl=https%3A%2F%2Fwww.eva.ro%2Fdespre-poze-pisici%2F%3Fpstart%3D2&docid=8G5fvpW6yejygM&tbnid=bUtK5LwpLvm0fM&vet=12ahUKEwi14KvNiaeKAxUjgf0HHdzoI2cQM3oECDIQAA..i&w=320&h=200&hcb=2&ved=2ahUKEwi14KvNiaeKAxUjgf0HHdzoI2cQM3oECDIQAA"));
                        lista.add(new ImagineDomeniu("Oscar",imagini.get(1),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.libertatea.ro%2Flifestyle%2Fcele-mai-frumoase-pisici-din-lume-feline-iubitoare-si-jucause-3078882&psig=AOvVaw0t75uvATGWqhIN7UeZrNYU&ust=1734259336643000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOiTmuOJp4oDFQAAAAAdAAAAABAE"));
                        lista.add(new ImagineDomeniu("Norocel",imagini.get(2),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fa1.ro%2Fgalerie%2Fpoze-fotografii-2018-top-7-poze-cu-pisici-ziua-mondiala-a-fotografiei-id793369-play1321404.html&psig=AOvVaw0t75uvATGWqhIN7UeZrNYU&ust=1734259336643000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCOiTmuOJp4oDFQAAAAAdAAAAABAJ"));

                        ListView lv = findViewById(R.id.idLVImagini);
                        ImagineAdapter adapter = new ImagineAdapter(lista,getApplicationContext(),R.layout.imagine_layout);
                        lv.setAdapter(adapter);
                    }
                });
            }
        });

    }
}