package com.example.app6;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class ImagineAdapter extends BaseAdapter {
    private List<ImagineDomeniu> imagini;
    private Context ctx;
    private int resursaLayout;

    public ImagineAdapter(List<ImagineDomeniu> imagini, Context ctx, int resursaLayout) {
        this.imagini = imagini;
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
    }


    @Override
    public int getCount() {
        return imagini.size();
    }

    @Override
    public Object getItem(int i) {
        return imagini.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf = LayoutInflater.from(ctx);
        View v = inf.inflate(resursaLayout,viewGroup,false);

        ImagineDomeniu img = (ImagineDomeniu) getItem(i);
        ImageView imageView = v.findViewById(R.id.idIVPisica);
        TextView textView = v.findViewById(R.id.idTVPisica);

        imageView.setImageBitmap(img.getImagine());
        textView.setText(img.getTextAfisat());

        return v;
    }
}
