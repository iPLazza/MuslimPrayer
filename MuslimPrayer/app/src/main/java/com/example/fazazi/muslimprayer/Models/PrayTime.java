package com.example.fazazi.muslimprayer.Models;


public class PrayTime {
    private String Fajr, Chorouk, dohr, asr, maghreb, ichae;

    public PrayTime(String fajr, String chorouk, String dohr, String asr, String maghreb, String ichae) {
        this.ichae = ichae;
        this.asr = asr;
        this.maghreb = maghreb;
        this.dohr = dohr;
        Chorouk = chorouk;
        Fajr = fajr;
    }


    public String getIchae() {
        return ichae;
    }

    public void setIchae(String ichae) {
        this.ichae = ichae;
    }

    public String getMaghreb() {
        return maghreb;
    }

    public void setMaghreb(String maghreb) {
        this.maghreb = maghreb;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getChorouk() {
        return Chorouk;
    }

    public void setChorouk(String chorouk) {
        Chorouk = chorouk;
    }

    public String getDohr() {
        return dohr;
    }

    public void setDohr(String dohr) {
        this.dohr = dohr;
    }

    public String getFajr() {
        return Fajr;
    }

    public void setFajr(String fajr) {
        Fajr = fajr;
    }
}
