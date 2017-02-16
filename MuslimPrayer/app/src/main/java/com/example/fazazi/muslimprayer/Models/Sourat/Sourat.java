package com.example.fazazi.muslimprayer.Models.Sourat;

import com.orm.dsl.Table;


@Table
public class Sourat {

    private String id;
    private String order;
    private String title;
    private Double Duration;
    private String Url;


    public Sourat(String id, String title, String order, Double duration, String url) {
        this.id = id;
        this.title = title;
        this.order = order;
        Duration = duration;
        Url = url;
    }


    public Double getDuration() {
        return Duration;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDuration(Double duration) {
        Duration = duration;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
