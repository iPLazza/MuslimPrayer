package com.example.fazazi.muslimprayer.Models.recitator;

import com.orm.SugarRecord;


public class Recitator extends SugarRecord {
    private String Name;
    private Long Id;
    private String Url;
    private Integer recitationId;


    public Recitator(String name, String url, Long id, Integer recitationId) {
        Name = name;
        Url = url;
        Id = id;
        this.recitationId = recitationId;
    }


    @Override
    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public Integer getRecitationId() {
        return recitationId;
    }

    public void setRecitationId(Integer recitationId) {
        this.recitationId = recitationId;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }


}
