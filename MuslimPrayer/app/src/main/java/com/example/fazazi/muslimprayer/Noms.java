package com.example.fazazi.muslimprayer;


public class Noms {
    private String name1,name2;
    private int iconID;


    public Noms(String name1, String name2, int iconID) {
        super();
        this.name1 = name1;
        this.name2 = name2;
        this.iconID = iconID;
    }

    public int getIconID() {
        return iconID;
    }

    public String getName1() {
        return name1;
    }

    public String getName2() {
        return name2;
    }
}
