package com.Team3_6.kifu;

public class Uploads {
    private String mName;
    private String mImageUrl;
    private String mDescriptions;
    private String mCategories;

    public Uploads(){}
    public Uploads(String name, String imageUrl){
        this.mName = name;
        this.mImageUrl = imageUrl;
        /**this.mDescriptions = descriptions;
        this.mCategories = categories;*/
    }

    public String getmName() { return mName; }
    public String getmImageUrl() { return mImageUrl; }
    public String getmDescriptions() { return mDescriptions; }
    public String getmCategories() { return mCategories; }

    public void setmName(String mName) { this.mName = mName; }
    public void setmImageUrl(String mImageUrl) { this.mImageUrl = mImageUrl; }
    public void setmDescriptions(String mDescriptions) { this.mDescriptions = mDescriptions; }
    public void setmCategories(String mCategories) { this.mCategories = mCategories; }
}