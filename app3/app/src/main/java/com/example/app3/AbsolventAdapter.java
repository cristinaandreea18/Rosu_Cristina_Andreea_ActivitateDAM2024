package com.example.app3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class AbsolventAdapter extends BaseAdapter {
    private List<Absolvent> absolventi = null;
    private int layoutResursa;
    private Context ctx;

    public AbsolventAdapter(List<Absolvent> absolventi, int layoutResursa, Context ctx) {
        this.absolventi = absolventi;
        this.layoutResursa = layoutResursa;
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return absolventi.size();
    }

    @Override
    public Object getItem(int i) {
        return absolventi.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf = LayoutInflater.from(ctx);
        View v = inf.inflate(layoutResursa,viewGroup,false);

        TextView tvNume = v.findViewById(R.id.tvNume);
        TextView tvSpec = v.findViewById(R.id.tvSpecializare);
        TextView tvFinant = v.findViewById(R.id.tvFinantare);
        TextView tvData = v.findViewById(R.id.tvFinantare);

        Absolvent a = (Absolvent) getItem(i);
        tvNume.setText(a.getNume());
        tvSpec.setText(a.getSpecializare());
        tvFinant.setText(a.getFormaFinantare());
        tvData.setText(a.getFormaFinantare());

        return v;
    }
}
