package com.example.app8;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ImagineAdapter extends BaseAdapter {
    private List<ImagineDomeniu>imagini= null;
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

        ImageView iv = v.findViewById(R.id.idIVMarker);
        TextView tv = v.findViewById(R.id.idTVMarker);

        iv.setImageBitmap(img.getImagine());
        tv.setText(img.getTextAfisat());

        return v;
    }
}
