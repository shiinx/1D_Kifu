package com.Team3_6.kifu;

import android.net.Uri;

public class Uploads {
    private String mName;
    private Uri mImageUrl;
    private String mDescriptions;
    private String mCategories;

    public Uploads() {
    }

    public Uploads(String name, Uri imageUrl) {
        this.mName = name;
        this.mImageUrl = imageUrl;
        /**this.mDescriptions = descriptions;
         this.mCategories = categories;*/
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public Uri getmImageUrl() {
        return mImageUrl;
    }

    public void setmImageUrl(Uri mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public String getmDescriptions() {
        return mDescriptions;
    }

    public void setmDescriptions(String mDescriptions) {
        this.mDescriptions = mDescriptions;
    }

    public String getmCategories() {
        return mCategories;
    }

    public void setmCategories(String mCategories) {
        this.mCategories = mCategories;
    }
}
