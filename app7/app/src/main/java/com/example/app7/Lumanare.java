package com.example.app7;


import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "Lumanari")
public class Lumanare implements Parcelable {
@PrimaryKey
@NonNull
    private int cod;
    private String aroma;
    private int nrFitiluri;
    private int timpArdere;

    public Lumanare(int cod, String aroma, int nrFitiluri, int timpArdere) {
        this.cod = cod;
        this.aroma = aroma;
        this.nrFitiluri = nrFitiluri;
        this.timpArdere = timpArdere;
    }

    protected Lumanare(Parcel in) {
        cod = in.readInt();
        aroma = in.readString();
        nrFitiluri = in.readInt();
        timpArdere = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(cod);
        dest.writeString(aroma);
        dest.writeInt(nrFitiluri);
        dest.writeInt(timpArdere);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Lumanare> CREATOR = new Creator<Lumanare>() {
        @Override
        public Lumanare createFromParcel(Parcel in) {
            return new Lumanare(in);
        }

        @Override
        public Lumanare[] newArray(int size) {
            return new Lumanare[size];
        }
    };

    public String getKey(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.cod);
        sb.append("-");
        sb.append(this.aroma);
        return sb.toString();
    }
    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }
    public String getAroma() {
        return aroma;
    }

    public void setAroma(String aroma) {
        this.aroma = aroma;
    }

    public int getNrFitiluri() {
        return nrFitiluri;
    }

    public void setNrFitiluri(int nrFitiluri) {
        this.nrFitiluri = nrFitiluri;
    }

    public int getTimpArdere() {
        return timpArdere;
    }

    public void setTimpArdere(int timpArdere) {
        this.timpArdere = timpArdere;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Lumanare{");
        sb.append("cod=").append(cod);
        sb.append(", aroma='").append(aroma).append('\'');
        sb.append(", nrFitiluri=").append(nrFitiluri);
        sb.append(", timpArdere=").append(timpArdere);
        sb.append('}');
        return sb.toString();
    }
}
