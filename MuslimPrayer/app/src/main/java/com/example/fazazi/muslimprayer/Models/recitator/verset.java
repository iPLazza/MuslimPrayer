package com.example.fazazi.muslimprayer.Models.recitator;


public class verset {


    private String id, surat, ayah, verse;


    public verset(String id, String surat, String ayah, String verse) {
        this.id = id;
        this.surat = surat;
        this.ayah = ayah;
        this.verse = verse;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSurat() {
        return surat;
    }

    public void setSurat(String surat) {
        this.surat = surat;
    }

    public String getAyah() {
        return ayah;
    }

    public void setAyah(String ayah) {
        this.ayah = ayah;
    }

    public String getVerse() {
        return verse;
    }

    public void setVerse(String verse) {
        this.verse = verse;
    }
}
