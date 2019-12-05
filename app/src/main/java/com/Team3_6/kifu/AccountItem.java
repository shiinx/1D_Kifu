package com.Team3_6.kifu;

/**
 * class for cardview in account fragment
 */
public class AccountItem {


    private String url;
    private int Picture;

    public AccountItem() {
    }

    public AccountItem(String title, int picture) {
        url = title;
        Picture = picture;
    }

    public String getTitle() {
        return url;
    }

    public void setTitle(String url) {
        url = url;
    }

    public int getPicture() {
        return Picture;
    }

    public void setPicture(int picture) {
        Picture = picture;
    }
}
