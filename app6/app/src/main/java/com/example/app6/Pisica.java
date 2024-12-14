package com.example.app6;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "pisici", primaryKeys = {"nume", "stapan"})
public class Pisica implements Parcelable {
//    @PrimaryKey(autoGenerate = true)
//    private int id;
    @NonNull
    private String stapan;
    @NonNull
    private String nume;
    private String rasa;

    private String gen;

    public Pisica(String stapan, String nume, String rasa, String gen) {
        this.stapan = stapan;
        this.nume = nume;
        this.rasa = rasa;
        this.gen = gen;
    }

    protected Pisica(Parcel in) {
        //id = in.readInt();
        stapan = in.readString();
        nume = in.readString();
        rasa = in.readString();
        gen = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        //dest.writeInt(id);
        dest.writeString(stapan);
        dest.writeString(nume);
        dest.writeString(rasa);
        dest.writeString(gen);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Pisica> CREATOR = new Creator<Pisica>() {
        @Override
        public Pisica createFromParcel(Parcel in) {
            return new Pisica(in);
        }

        @Override
        public Pisica[] newArray(int size) {
            return new Pisica[size];
        }
    };

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
    public String getStapan() {
        return stapan;
    }

    public void setStapan(String stapan) {
        this.stapan = stapan;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getRasa() {
        return rasa;
    }

    public void setRasa(String rasa) {
        this.rasa = rasa;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getKey(){
        StringBuilder sb = new StringBuilder();
        sb.append(this.stapan);
        sb.append("-");
        sb.append(this.nume);
        return sb.toString();
    }
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Pisica{");
        //sb.append("id=").append(id);
        sb.append(", stapan='").append(stapan).append('\'');
        sb.append(", nume='").append(nume).append('\'');
        sb.append(", rasa='").append(rasa).append('\'');
        sb.append(", gen='").append(gen).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
