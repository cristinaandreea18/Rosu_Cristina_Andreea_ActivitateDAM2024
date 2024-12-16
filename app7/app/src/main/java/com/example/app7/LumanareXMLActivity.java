package com.example.app7;

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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.NodeList;

public class LumanareXMLActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lumanare_xmlactivity);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ListView lv = findViewById(R.id.idLVXML);
        List<String> valuesXML = new ArrayList<>();
        Executor executor = Executors.newSingleThreadExecutor();
        Handler handler = new Handler(Looper.myLooper());

        executor.execute(new Runnable() {
            @Override
            public void run() {
                File file = new File(getApplicationContext().getFilesDir().getParent()+"/shared_prefs/LumanariFav.xml");
                DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

                try{
                    DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
                    Document document = documentBuilder.parse(file);
                    Element root = document.getDocumentElement();

                    NodeList childs = root.getElementsByTagName("string");
                    for(int i=0;i<childs.getLength();i++){
                        Element node = (Element) childs.item(i);
                        String name = node.getAttribute("name");
                        String value = node.getTextContent();
                        valuesXML.add(name+":"+value);
                    }


                } catch (ParserConfigurationException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (SAXException e) {
                    throw new RuntimeException(e);
                }

                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        ArrayAdapter<String>adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_list_item_1,valuesXML);
                        lv.setAdapter(adapter);
                    }
                });
            }
        });
    }
}