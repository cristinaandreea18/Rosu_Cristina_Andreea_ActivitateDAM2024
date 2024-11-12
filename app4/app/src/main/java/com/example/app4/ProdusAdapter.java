package com.example.app4;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class ProdusAdapter extends BaseAdapter {
    private List<Produs> produse =null;
    private int resursaLayout;
    private Context ctx;

    public ProdusAdapter(int resursaLayout, List<Produs> produse, Context ctx) {
        this.resursaLayout = resursaLayout;
        this.produse = produse;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return produse.size();
    }

    @Override
    public Object getItem(int i) {
        return produse.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf = LayoutInflater.from(ctx);
        View v = inf.inflate(resursaLayout,viewGroup,false);

        TextView tvDenumire = v.findViewById(R.id.tvDenumire);
        TextView tvCategorie = v.findViewById(R.id.tvCategorie);
        TextView tvUnitate = v.findViewById(R.id.tvUnitate);
        TextView tvPret = v.findViewById(R.id.tvPret);
        TextView tvData = v.findViewById(R.id.tvDataExpirare);
        CheckBox cbEsteBio = v.findViewById(R.id.cbEsteBio);

        Produs p = (Produs) getItem(i);
        tvDenumire.setText(p.getDenumire());
        tvCategorie.setText(p.getCategorie());
        tvUnitate.setText(p.getUnitate());
        tvPret.setText(""+p.getPretUnitar());
        tvData.setText(p.getDataExpirare());
        cbEsteBio.setChecked(p.isEsteBio());

        return v;
    }
}
