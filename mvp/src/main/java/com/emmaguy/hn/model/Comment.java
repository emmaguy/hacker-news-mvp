package com.emmaguy.hn.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by emma on 22/03/15.
 */
public class Comment {
    @SerializedName("id")
    private String mId;

    @SerializedName("text")
    private String mText = "";

    @SerializedName("parent")
    private String mParent = "";

    @SerializedName("kids")
    private ArrayList<String> mChildCommentIds = new ArrayList<>();

    public Comment() {}

    public Comment(String text) {
        mText = text;
    }

    public String getId() {
        return mId;
    }

    public String getText() {
        return mText;
    }

    public ArrayList<String> getChildCommentIds() {
        return mChildCommentIds;
    }

    public String getParent() {
        return mParent;
    }
}
