package com.example.aplicatie_prajitura;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;


public class PrajituraAdapter extends BaseAdapter {

    private List<Prajitura> prajituri = null;
    private Context ctx;
    private int resursaLayout;

    public PrajituraAdapter(List<Prajitura> prajituri, Context ctx, int resursaLayout) {
        this.prajituri = prajituri;
        this.ctx = ctx;
        this.resursaLayout = resursaLayout;
    }

    @Override
    public int getCount() {
        return prajituri.size();
    }

    @Override
    public Object getItem(int i) {
        return prajituri.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    // getView() -> face maparea obiectului pe o linie din ListView.
    //Aceasta metoda va fi apelata pentru fiecare obiect din lista noastra de obiecte (getCount() ori).
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inf = LayoutInflater.from(ctx);
        View v = inf.inflate(resursaLayout,viewGroup,false);

        TextView numePrajituraTV = v.findViewById(R.id.idNumeP);
        TextView pretPrajituraTV = v.findViewById(R.id.idPretP);
        TextView cantitatePrajituraTV = v.findViewById(R.id.idCantitateP);
        TextView dataExpirareTV = v.findViewById(R.id.idDataExpirareP);
        CheckBox glazuraCB = v.findViewById(R.id.idAreGlazuraP);

        Prajitura p = (Prajitura) getItem(i);

        //toString se aplica pe clase
        //pentru valori int sau float -> folosim " " + valoarea
        numePrajituraTV.setText(p.getNume());
        pretPrajituraTV.setText("" + p.getPret());
        cantitatePrajituraTV.setText(""+p.getCantitate());
        dataExpirareTV.setText(p.getDataExpirare());
        glazuraCB.setChecked(p.getAreGlazura());

        return v;
    }
}
