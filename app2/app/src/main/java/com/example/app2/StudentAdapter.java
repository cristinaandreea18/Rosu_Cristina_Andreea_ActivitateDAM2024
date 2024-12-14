package com.example.app2;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.List;
import java.util.zip.Inflater;

public class StudentAdapter extends BaseAdapter {
private List<Student>studenti = null;
private Context ctx;
private int resursaLayout;

    public StudentAdapter(Context ctx, int resursaLayout, List<Student> studenti) {
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
        this.studenti = studenti;
    }

    @Override
    public int getCount() {
        return studenti.size();
    }

    @Override
    public Object getItem(int i) {
        return studenti.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf = LayoutInflater.from(ctx);
        View v = inf.inflate(resursaLayout,viewGroup,false);

        TextView tvNume = v.findViewById(R.id.idTvNumeS);
        TextView tvGen = v.findViewById(R.id.idTvGenS);
        TextView tvAnS = v.findViewById(R.id.idTvAnS);
        TextView tvCurs = v.findViewById(R.id.idTvCursS);
        TextView rbRating = v.findViewById(R.id.idTvRating);

        Student s = (Student) getItem(i);
        tvNume.setText(s.getNume());
        tvGen.setText(s.getGen());
        tvAnS.setText("" + s.getAnStudiu());
        tvCurs.setText(s.getCurs());
        rbRating.setText("" + s.getRating());

        return v;
    }
}
