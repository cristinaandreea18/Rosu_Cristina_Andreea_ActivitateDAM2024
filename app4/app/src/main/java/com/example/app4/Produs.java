package com.example.app4;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Produs implements Parcelable {
    private String denumire;
    private String categorie;
    private String unitate;
    private float pretUnitar;
    private String dataExpirare;
    private boolean esteBio;

    public Produs(String categorie, String denumire, String unitate, float pretUnitar, boolean esteBio, String dataExpirare) {
        this.categorie = categorie;
        this.denumire = denumire;
        this.unitate = unitate;
        this.pretUnitar = pretUnitar;
        this.esteBio = esteBio;
        this.dataExpirare = dataExpirare;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getUnitate() {
        return unitate;
    }

    public void setUnitate(String unitate) {
        this.unitate = unitate;
    }

    public float getPretUnitar() {
        return pretUnitar;
    }

    public void setPretUnitar(float pretUnitar) {
        this.pretUnitar = pretUnitar;
    }

    public String getDataExpirare() {
        return dataExpirare;
    }

    public void setDataExpirare(String dataExpirare) {
        this.dataExpirare = dataExpirare;
    }

    public boolean isEsteBio() {
        return esteBio;
    }

    public void setEsteBio(boolean esteBio) {
        this.esteBio = esteBio;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Produs{");
        sb.append("denumire='").append(denumire).append('\'');
        sb.append(", categorie='").append(categorie).append('\'');
        sb.append(", unitate='").append(unitate).append('\'');
        sb.append(", pretUnitar=").append(pretUnitar);
        sb.append(", dataExpirare='").append(dataExpirare).append('\'');
        sb.append(", esteBio=").append(esteBio);
        sb.append('}');
        return sb.toString();
    }
    protected Produs(Parcel in) {
        denumire = in.readString();
        categorie = in.readString();
        unitate = in.readString();
        pretUnitar = in.readFloat();
        dataExpirare = in.readString();
        esteBio = in.readByte() != 0;
    }

    public static final Creator<Produs> CREATOR = new Creator<Produs>() {
        @Override
        public Produs createFromParcel(Parcel in) {
            return new Produs(in);
        }

        @Override
        public Produs[] newArray(int size) {
            return new Produs[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(denumire);
        parcel.writeString(categorie);
        parcel.writeString(unitate);
        parcel.writeFloat(pretUnitar);
        parcel.writeString(dataExpirare);
        parcel.writeByte((byte) (esteBio ? 1 : 0));
    }
}
