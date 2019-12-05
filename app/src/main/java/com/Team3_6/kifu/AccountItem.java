package com.Team3_6.kifu;

/**class for cardview in account fragment
 *
 */
public class AccountItem {


    private String Title;
    private int Picture;

    public AccountItem() {
    }

    public AccountItem(String title, int picture) {
        Title = title;
        Picture = picture;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public void setPicture(int picture) {
        Picture = picture;
    }

    public int getPicture() {
        return Picture;
    }
}
