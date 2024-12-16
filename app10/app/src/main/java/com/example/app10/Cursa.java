package com.example.app10;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Curse")
public class Cursa implements Parcelable {
    @PrimaryKey
    @NonNull
    private int id;
    private String destinatie;
    private int distanta;
    private Boolean esteManual;

    public Cursa(int id, String destinatie, int distanta, Boolean esteManual) {
        this.id = id;
        this.destinatie = destinatie;
        this.distanta = distanta;
        this.esteManual = esteManual;
    }

    protected Cursa(Parcel in) {
        id = in.readInt();
        destinatie = in.readString();
        distanta = in.readInt();
        byte tmpEsteManual = in.readByte();
        esteManual = tmpEsteManual == 0 ? null : tmpEsteManual == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(destinatie);
        dest.writeFloat(distanta);
        dest.writeByte((byte) (esteManual == null ? 0 : esteManual ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Cursa> CREATOR = new Creator<Cursa>() {
        @Override
        public Cursa createFromParcel(Parcel in) {
            return new Cursa(in);
        }

        @Override
        public Cursa[] newArray(int size) {
            return new Cursa[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDestinatie() {
        return destinatie;
    }

    public void setDestinatie(String destinatie) {
        this.destinatie = destinatie;
    }

    public int getDistanta() {
        return distanta;
    }

    public void setDistanta(int distanta) {
        this.distanta = distanta;
    }

    public Boolean getEsteManual() {
        return esteManual;
    }

    public void setEsteManual(Boolean esteManual) {
        this.esteManual = esteManual;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Cursa{");
        sb.append("id=").append(id);
        sb.append(", destinatie='").append(destinatie).append('\'');
        sb.append(", distanta=").append(distanta);
        sb.append(", esteManual=").append(esteManual);
        sb.append('}');
        return sb.toString();
    }
}
