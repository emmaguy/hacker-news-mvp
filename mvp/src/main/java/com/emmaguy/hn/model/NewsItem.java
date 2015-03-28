package com.emmaguy.hn.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by emma on 15/02/15.
 */
public class NewsItem {
    @SerializedName("id")
    private String mId;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("url")
    private String mUrl;

    @SerializedName("kids")
    private ArrayList<String> mRootCommentIds = new ArrayList<>();

    @SerializedName("score")
    private int mScore = 0;

    public NewsItem() {}

    public NewsItem(String title, String url) {
        super();
        mTitle = title;
        mUrl = url;
    }

    public String getId() {
        return mId;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getUrl() {
        return mUrl;
    }

    public ArrayList<String> getRootCommentIds() {
        return mRootCommentIds;
    }

    public void setRootCommentIds(ArrayList<String> ids) {
        mRootCommentIds = ids;
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int score) {
        mScore = score;
    }
}
