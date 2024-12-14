package com.example.app7;

import android.graphics.Bitmap;

public class ImagineDomeniu {
    private String textAfisat;
    private Bitmap Imagine;
    private String link;

    public ImagineDomeniu(String textAfisat, Bitmap imagine, String link) {
        this.textAfisat = textAfisat;
        Imagine = imagine;
        this.link = link;
    }

    public String getTextAfisat() {
        return textAfisat;
    }

    public void setTextAfisat(String textAfisat) {
        this.textAfisat = textAfisat;
    }

    public Bitmap getImagine() {
        return Imagine;
    }

    public void setImagine(Bitmap imagine) {
        Imagine = imagine;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
