package com.example.app4;

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
        linkuriImagini.add("https://auchan.vtexassets.com/arquivos/ids/274434/5941065002873.jpg?v=638598353257730000");
        linkuriImagini.add("https://www.tuburiaparate.ro/image/cache/produse%202024/Bauturi/cafea-macinata-jacobs-kronung-500g-500x500.jpg.webp");
        linkuriImagini.add("https://auchan.vtexassets.com/arquivos/ids/160352/zahar-cristal-margaritar-1kg-8906438770718.jpg?v=637980663740930000");
        linkuriImagini.add("https://deliveryman.ro/3903-thickbox_default/iaurt-danone-natural-35-grasime-130-grame.jpg");
        linkuriImagini.add("https://static.mega-image.ro/medias/sys_master/products/hac/hde/9321452929054.jpg");

        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                for(String link:linkuriImagini){
                    HttpURLConnection con = null;
                    try {
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
                        lista.add(new ImagineDomeniu("Lapte",imagini.get(0),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.auchan.ro%2Flapte-de-consum-napolact-3-5-grasime-1-l%2Fp&psig=AOvVaw0WaepQZGE0QjL285jLHUqn&ust=1732650089220000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMjNiN6e-IkDFQAAAAAdAAAAABAE"));
                        lista.add(new ImagineDomeniu("Cafea",imagini.get(1),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.tuburiaparate.ro%2Fcafea-macinata-jacobs-kronung-500g&psig=AOvVaw2cyOOzJ5yKQzJBM01T8amB&ust=1732650431090000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCPCSvoGg-IkDFQAAAAAdAAAAABAE"));
                        lista.add(new ImagineDomeniu("Zahar",imagini.get(2),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.auchan.ro%2Fzahar-cristal-margaritar-1-kg%2Fp&psig=AOvVaw0Qz5KQWfvGt5t1pSN4u4gk&ust=1732650539767000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCNCUnbKg-IkDFQAAAAAdAAAAABAE"));
                        lista.add(new ImagineDomeniu("Iaurt",imagini.get(3),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fdeliveryman.ro%2Flapte-batut-iaurt-sana-chefir%2F2015-iaurt-danone-natural-35-grasime-130-grame.html&psig=AOvVaw2JaNgv299kfkPJtd15QO-C&ust=1732650592398000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCIDxpc2g-IkDFQAAAAAdAAAAABAE"));
                        lista.add(new ImagineDomeniu("Cascaval",imagini.get(4),"https://www.google.com/url?sa=i&url=https%3A%2F%2Fwww.mega-image.ro%2FLactate-si-oua%2FBranzeturi%2FCascaval%2FCascaval-clasic-felii-150g%2Fp%2F55966&psig=AOvVaw0zpVcPXiCF_6NY33ExchSJ&ust=1732650627780000&source=images&cd=vfe&opi=89978449&ved=0CBQQjRxqFwoTCMib49ug-IkDFQAAAAAdAAAAABAE"));

                        ListView lvImagini = findViewById(R.id.idLVImagini);
                        AdapterImagine adapter = new AdapterImagine(lista,getApplicationContext(),R.layout.imagine_layout);
                        lvImagini.setAdapter(adapter);

                        lvImagini.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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