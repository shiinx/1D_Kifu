package com.Team3_6.kifu;

public class Category {
    private String Title;
    private int Picture;


    public Category() {
    }

    public Category(String title, int picture) {
        Title = title;
        Picture = picture;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public int getPicture() {
        return Picture;
    }

    public void setPicture(int picture) {
        Picture = picture;
    }
}
