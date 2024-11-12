package com.example.app3;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Absolvent implements Parcelable {
    private String nume;
    private String specializare;
    private String formaFinantare;
    private String dataInmatriculare;

    public Absolvent(String nume, String specializare, String formaFinantare, String dataInmatriculare) {
        this.nume = nume;
        this.specializare = specializare;
        this.formaFinantare = formaFinantare;
        this.dataInmatriculare = dataInmatriculare;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getSpecializare() {
        return specializare;
    }

    public void setSpecializare(String specializare) {
        this.specializare = specializare;
    }

    public String getFormaFinantare() {
        return formaFinantare;
    }

    public void setFormaFinantare(String formaFinantare) {
        this.formaFinantare = formaFinantare;
    }

    public String getDataInmatriculare() {
        return dataInmatriculare;
    }

    public void setDataInmatriculare(String dataInmatriculare) {
        this.dataInmatriculare = dataInmatriculare;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Absolvent{");
        sb.append("nume='").append(nume).append('\'');
        sb.append(", specializare='").append(specializare).append('\'');
        sb.append(", formaFinantare='").append(formaFinantare).append('\'');
        sb.append(", dataInmatriculare='").append(dataInmatriculare).append('\'');
        sb.append('}');
        return sb.toString();
    }

    // Constructorul care primește un Parcel
    protected Absolvent(Parcel in) {
        nume = in.readString();
        specializare = in.readString();
        formaFinantare = in.readString();
        dataInmatriculare = in.readString();
    }

    // Implementarea metodei writeToParcel
    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(nume);
        parcel.writeString(specializare);
        parcel.writeString(formaFinantare);
        parcel.writeString(dataInmatriculare);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // CREATOR-ul necesar pentru Parcelable
    public static final Parcelable.Creator<Absolvent> CREATOR = new Parcelable.Creator<Absolvent>() {
        @Override
        public Absolvent createFromParcel(Parcel in) {
            return new Absolvent(in);
        }

        @Override
        public Absolvent[] newArray(int size) {
            return new Absolvent[size];
        }
    };
}
