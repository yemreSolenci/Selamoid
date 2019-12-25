package com.example.selamoid.Model;

public class Mesaj {

    private boolean benim;
    private String icerik;

    public Mesaj(boolean benim, String icerik) {
        this.benim = benim;
        this.icerik = icerik;
    }

    public boolean getBenim() {
        return benim;
    }

    public String getIcerik() {
        return icerik;
    }
}
