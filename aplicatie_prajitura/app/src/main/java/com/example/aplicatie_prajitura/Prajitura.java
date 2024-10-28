package com.example.aplicatie_prajitura;

import android.os.Parcel;
import android.os.Parcelable;

public class Prajitura implements Parcelable {
    private String nume;
    private double pret;
    private double cantitate;
    private String dataExpirare;
    private Boolean areGlazura;

    public Prajitura(String nume, double pret, double cantitate, String dataExpirare, Boolean areGlazura) {
        this.nume = nume;
        this.pret = pret;
        this.cantitate = cantitate;
        this.dataExpirare = dataExpirare;
        this.areGlazura = areGlazura;
    }

    //metoda de deserializare
    protected Prajitura(Parcel in) {
        nume = in.readString();
        pret = in.readDouble();
        cantitate = in.readDouble();
        dataExpirare = in.readString();
        byte tmpAreGlazura = in.readByte();
        areGlazura = tmpAreGlazura == 0 ? null : tmpAreGlazura == 1;
    }

    //serializare
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeDouble(pret);
        dest.writeDouble(cantitate);
        dest.writeString(dataExpirare);
        dest.writeByte((byte) (areGlazura == null ? 0 : areGlazura ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    //cand avem vector de obiecte serializate
    public static final Creator<Prajitura> CREATOR = new Creator<Prajitura>() {
        @Override
        public Prajitura createFromParcel(Parcel in) {
            return new Prajitura(in);
        }

        @Override
        public Prajitura[] newArray(int size) {
            return new Prajitura[size];
        }
    };

    public Boolean getAreGlazura() {
        return areGlazura;
    }

    public void setAreGlazura(Boolean areGlazura) {
        this.areGlazura = areGlazura;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getPret() {
        return pret;
    }

    public void setPret(double pret) {
        this.pret = pret;
    }

    public double getCantitate() {
        return cantitate;
    }

    public void setCantitate(double cantitate) {
        this.cantitate = cantitate;
    }

    public String getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(String dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Prajitura{");
        sb.append("nume='").append(nume).append('\'');
        sb.append(", pret=").append(pret);
        sb.append(", cantitate=").append(cantitate);
        sb.append(", dataExpirare=").append(dataExpirare);
        sb.append(", areGlazura=").append(areGlazura);
        sb.append('}');
        return sb.toString();
    }
}
