package com.example.app2;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Student implements Parcelable {
    private String nume;
    private String gen;
    private int anStudiu;
    private String curs;
    private float rating;

    public Student(String nume, String gen, int anStudiu, String curs, float rating) {
        this.nume = nume;
        this.gen = gen;
        this.anStudiu = anStudiu;
        this.curs = curs;
        this.rating = rating;
    }

    protected Student(Parcel in) {
        nume = in.readString();
        gen = in.readString();
        anStudiu = in.readInt();
        curs = in.readString();
        rating = in.readFloat();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nume);
        dest.writeString(gen);
        dest.writeInt(anStudiu);
        dest.writeString(curs);
        dest.writeFloat(rating);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Student> CREATOR = new Creator<Student>() {
        @Override
        public Student createFromParcel(Parcel in) {
            return new Student(in);
        }

        @Override
        public Student[] newArray(int size) {
            return new Student[size];
        }
    };

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getGen() {
        return gen;
    }

    public void setGen(String gen) {
        this.gen = gen;
    }

    public String getCurs() {
        return curs;
    }

    public void setCurs(String curs) {
        this.curs = curs;
    }

    public int getAnStudiu() {
        return anStudiu;
    }

    public void setAnStudiu(int anStudiu) {
        this.anStudiu = anStudiu;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Student{");
        sb.append("nume='").append(nume).append('\'');
        sb.append(", gen=").append(gen);
        sb.append(", anStudiu=").append(anStudiu);
        sb.append(", curs='").append(curs).append('\'');
        sb.append(", rating='").append(rating).append('\'');
        sb.append('}');
        return sb.toString();
    }

}
