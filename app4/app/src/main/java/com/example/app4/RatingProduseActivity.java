package com.example.app4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

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

public class RatingProduseActivity extends AppCompatActivity {
  private List<String>linkuriImagini = new ArrayList<>();
  private int currentIndex = 0;
  private ProgressBar progressBar;
  private ImageView imgView;
  private Button btnLike;
  private Button btnDislike;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_rating_produse);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        imgView = findViewById(R.id.imageView);
        Button btnNext = findViewById(R.id.idBtnNext);
        progressBar = findViewById(R.id.progressBar);

        linkuriImagini.add("https://auchan.vtexassets.com/arquivos/ids/274434/5941065002873.jpg?v=638598353257730000");
        linkuriImagini.add("https://www.tuburiaparate.ro/image/cache/produse%202024/Bauturi/cafea-macinata-jacobs-kronung-500g-500x500.jpg.webp");
        linkuriImagini.add("https://auchan.vtexassets.com/arquivos/ids/160352/zahar-cristal-margaritar-1kg-8906438770718.jpg?v=637980663740930000");
        linkuriImagini.add("https://deliveryman.ro/3903-thickbox_default/iaurt-danone-natural-35-grasime-130-grame.jpg");
        linkuriImagini.add("https://cdn.freshful.ro/media/cache/sylius_shop_product_original/cb/9d/eaa1879679cda0939582b72f8367.jpg");

        progressBar.setVisibility(View.GONE);

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadImage();
            }
        });

        btnLike = findViewById(R.id.idBtnLike);
        btnLike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnLike.setBackgroundColor(getResources().getColor(R.color.color2));
                btnDislike.setBackgroundColor(getResources().getColor(R.color.white));
                btnDislike.setEnabled(false);
            }
        });

        btnDislike = findViewById(R.id.idBtnDislike);
        btnDislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnDislike.setBackgroundColor(getResources().getColor(R.color.color3));
                btnLike.setBackgroundColor(getResources().getColor(R.color.white));
                btnLike.setEnabled(false);
            }
        });
    }

    private void loadImage() {
        btnLike.setEnabled(true);
        btnDislike.setEnabled(true);
        progressBar.setVisibility(View.VISIBLE);
        btnLike.setBackgroundColor(getResources().getColor(R.color.white));
        btnDislike.setBackgroundColor(getResources().getColor(R.color.white));


        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.getMainLooper());

        executor.execute(new Runnable() {
            String link = linkuriImagini.get(currentIndex);
            Bitmap bitmap = null;
            HttpURLConnection con = null;
            @Override
            public void run() {
                    try {
                        URL url = new URL(link);
                        con = (HttpURLConnection) url.openConnection();
                        InputStream is = con.getInputStream();
                        bitmap = BitmapFactory.decodeStream(is);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                imgView.setImageBitmap(bitmap);
                                progressBar.setVisibility(View.GONE);
                            }
                        });
                    } catch (MalformedURLException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                currentIndex = (currentIndex + 1) % linkuriImagini.size();
            }
        });
    }
}