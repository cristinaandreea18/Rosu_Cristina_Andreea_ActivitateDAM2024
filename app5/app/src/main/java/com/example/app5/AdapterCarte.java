package com.example.app5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class AdapterCarte extends BaseAdapter {
    private List<Carte> carti = null;
    private Context ctx;
    private int resursaLayout;

    public AdapterCarte(List<Carte> carti, Context ctx, int resursaLayout) {
        this.carti = carti;
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
    }

    @Override
    public int getCount() {
        return carti.size();
    }

    @Override
    public Object getItem(int i) {
        return carti.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf = LayoutInflater.from(ctx);
        View v = inf.inflate(resursaLayout,viewGroup,false);

        TextView nume = v.findViewById(R.id.idTVNumeCarte);
        TextView autor = v.findViewById(R.id.idTVAutorCarte);
        TextView editura = v.findViewById(R.id.idTVEdituraCarte);
        TextView pret = v.findViewById(R.id.idTVPretCarte);
        TextView rating = v.findViewById(R.id.idTVRatingCarte);

        Carte c = (Carte) getItem(i);
        nume.setText(c.getNume());
        autor.setText(c.getAutor());
        editura.setText(c.getEditura());
        pret.setText(""+c.getPret());
        rating.setText(""+c.getRating());

        return v;
    }
}
