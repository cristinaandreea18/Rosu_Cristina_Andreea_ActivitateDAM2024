package com.example.app6;

import android.graphics.Bitmap;

public class ImagineDomeniu {
    private String textAfisat;
    private Bitmap imagine;
    private String link;

    public ImagineDomeniu(String textAfisat , Bitmap imagine, String link) {
        this.imagine = imagine;
        this.textAfisat = textAfisat;
        this.link = link;
    }

    public Bitmap getImagine() {
        return imagine;
    }

    public void setImagine(Bitmap imagine) {
        this.imagine = imagine;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTextAfisat() {
        return textAfisat;
    }

    public void setTextAfisat(String textAfisat) {
        this.textAfisat = textAfisat;
    }


}
