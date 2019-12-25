package com.example.selamoid.Model;

public class Mesaj {

    private boolean benim;
    private String icerik;

    public Mesaj(boolean benim, String icerik) {
        this.benim = benim;
        this.icerik = icerik;
    }

    public boolean isBenim() {
        return benim;
    }

    public void setBenim(boolean benim) {
        this.benim = benim;
    }

    public String getIcerik() {
        return icerik;
    }

    public void setIcerik(String icerik) {
        this.icerik = icerik;
    }
}
