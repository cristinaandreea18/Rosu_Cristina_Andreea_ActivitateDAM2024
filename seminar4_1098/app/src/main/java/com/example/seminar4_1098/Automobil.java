package com.example.seminar4_1098;

public class Automobil {
    public String marca;
    public int anFabricare;
    public String tipCombustibil;
    public int putere;
    public int vitezaMaxima;

    public Automobil(String marca, int anFabricare, String tipCombustibil, int putere, int vitezaMaxima) {
        this.marca = marca;
        this.anFabricare = anFabricare;
        this.tipCombustibil = tipCombustibil;
        this.putere = putere;
        this.vitezaMaxima = vitezaMaxima;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(" ");
        sb.append("marca='").append(marca).append('\'');
        sb.append(", anFabricare=").append(anFabricare);
        sb.append(", tipCombustibil='").append(tipCombustibil).append('\'');
        sb.append(", putere=").append(putere);
        sb.append(", vitezaMaxima=").append(vitezaMaxima);
        return sb.toString();
    }

}
