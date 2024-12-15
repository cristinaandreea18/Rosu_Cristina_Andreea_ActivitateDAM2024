package com.example.app8;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
private List<Bitmap> imagini = null;
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

        List<String>linkuriImagini = new ArrayList<>();
        linkuriImagini.add("https://encrypted-tbn0.gstatic.com/shopping?q=tbn:ANd9GcRxnpQLr340LCGoPT1PU9Le0IM9nQVA4T6QnpRHGiYONHl21uOy8FZFL1ybReinuMwjLhtimi5ssofFIyZ9L7xYpMGvZzm9akJmmL1ZFDOkVOnYe0AEFlfEzlcWDvdT0aJ6e4a1aomaljg&usqp=CAc");

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

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            lista = new ArrayList<>();
                            lista.add(new ImagineDomeniu("Textmarker 46-Faber Castell",imagini.get(0),"https://www.googleadservices.com/pagead/aclk?sa=L&ai=DChcSEwj61u2draqKAxWzZZEFHV3OO-MYABAmGgJscg&ae=2&aspm=1&co=1&ase=5&gclid=CjwKCAiAmfq6BhAsEiwAX1jsZyNvAVpoXSYpUQleu5wNohV34lI2p344gzGzlPXuRxjqJTXlrh-2jhoCGsoQAvD_BwE&ohost=www.google.com&cid=CAESVuD2bItgLLOmUbO32LKi_u3zVHsKE88EurPfUdslXwk7csIvu1-5udFNepnMUfEz2gDAa5Q034Y1amaNhGHgYYy9NVUuVKVR5srYZ4k6c0QTFzqVG3Zh&sig=AOD64_3Ct2Kv9WZFYgcelIiEnkw_wDcPJA&ctype=5&q=&ved=2ahUKEwj33eadraqKAxWOIBAIHTkKFfwQ9aACKAB6BAgJEEE&adurl="));

                            ListView lv = findViewById(R.id.idLVListaImagini);
                            ImagineAdapter adapter = new ImagineAdapter(lista, getApplicationContext(), R.layout.imagine_layout);
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
            }
        });
    }
}