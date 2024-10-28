package com.example.aplicatie_prajitura;

import java.io.Serializable;
import java.util.Date;

public class Prajitura implements Serializable {
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
