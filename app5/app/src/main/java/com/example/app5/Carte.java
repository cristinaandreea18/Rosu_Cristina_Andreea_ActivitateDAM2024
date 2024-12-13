package com.example.app5;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Carti")
public class Carte implements Parcelable {
    @PrimaryKey
    @NonNull
    private String nume;
    private String autor;
    private String editura;
    private float pret;

    private float rating;

    public Carte(String nume, String autor, String editura, float pret, float rating) {
        this.nume = nume;
        this.autor = autor;
        this.editura = editura;
        this.pret = pret;
        this.rating = rating;
    }

    protected Carte(Parcel in) {
        nume = in.readString();
        autor = in.readString();
        editura = in.readString();
        pret = in.readFloat();
        rating = in.readFloat();
    }

    public static final Creator<Carte> CREATOR = new Creator<Carte>() {
        @Override
        public Carte createFromParcel(Parcel in) {
            return new Carte(in);
        }

        @Override
        public Carte[] newArray(int size) {
            return new Carte[size];
        }
    };

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditura() {
        return editura;
    }

    public void setEditura(String editura) {
        this.editura = editura;
    }

    public float getPret() {
        return pret;
    }

    public void setPret(float pret) {
        this.pret = pret;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getKey(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.nume);
        sb.append("-");
        sb.append(this.autor);
        return sb.toString();
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Carte{");
        sb.append("nume='").append(nume).append('\'');
        sb.append(", autor='").append(autor).append('\'');
        sb.append(", editura='").append(editura).append('\'');
        sb.append(", pret='").append(pret).append('\'');
        sb.append(", rating=").append(rating);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(nume);
        parcel.writeString(autor);
        parcel.writeString(editura);
        parcel.writeFloat(pret);
        parcel.writeFloat(rating);
    }
}
