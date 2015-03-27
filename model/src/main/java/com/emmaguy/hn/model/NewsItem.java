package com.emmaguy.hn.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by emma on 15/02/15.
 */
public class NewsItem {
    @SerializedName("title")
    private String mTitle;

    @SerializedName("url")
    private String mUrl;

    public NewsItem() {
    }

    public NewsItem(String title, String url) {
        super();
        mTitle = title;
        mUrl = url;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }
}
