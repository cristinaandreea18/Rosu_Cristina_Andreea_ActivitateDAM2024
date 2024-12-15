package com.example.app8;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Markere")
public class Marker implements Parcelable {
@PrimaryKey
@NonNull
    private int codCuloare;
    private String marca;
    private String categorie;
    private Boolean estePermanent;

    public Marker(int codCuloare, String marca, String categorie, Boolean estePermanent) {
        this.codCuloare = codCuloare;
        this.marca = marca;
        this.categorie = categorie;
        this.estePermanent = estePermanent;
    }

    protected Marker(Parcel in) {
        codCuloare = in.readInt();
        marca = in.readString();
        categorie = in.readString();
        byte tmpEstePermanent = in.readByte();
        estePermanent = tmpEstePermanent == 0 ? null : tmpEstePermanent == 1;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(codCuloare);
        dest.writeString(marca);
        dest.writeString(categorie);
        dest.writeByte((byte) (estePermanent == null ? 0 : estePermanent ? 1 : 2));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Marker> CREATOR = new Creator<Marker>() {
        @Override
        public Marker createFromParcel(Parcel in) {
            return new Marker(in);
        }

        @Override
        public Marker[] newArray(int size) {
            return new Marker[size];
        }
    };

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public int getCodCuloare() {
        return codCuloare;
    }

    public void setCodCuloare(int codCuloare) {
        this.codCuloare = codCuloare;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public Boolean getEstePermanent() {
        return estePermanent;
    }

    public void setEstePermanent(Boolean estePermanent) {
        this.estePermanent = estePermanent;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Marker{");
        sb.append("codCuloare=").append(codCuloare);
        sb.append(", marca='").append(marca).append('\'');
        sb.append(", categorie='").append(categorie).append('\'');
        sb.append(", estePermanent=").append(estePermanent);
        sb.append('}');
        return sb.toString();
    }
}
