package com.example.haarmonika.Objects;

public class Hairstyle {
    String name;
    int price;
    int id;
    String style;
    int duration;

    public Hairstyle(int price, int id, String style, int duration) {
        this.price = price;
        this.id = id;
        this.style = style;
        this.duration = duration;
    }

    public Hairstyle(String name) {
        this.name = name;
    }

    public String getName() {return name;}
    public void setName(String name) {this.name = name;}
    public int getPrice() {return price;}
    public void setPrice(int price) {this.price = price;}
    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
    public String getStyle() {return style;}
    public void setStyle(String style) {this.style = style;}
    public int getDuration() {return duration;}
    public void setDuration(int duration) {this.duration = duration;}
}
