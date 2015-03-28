package com.emmaguy.hn.model;

/**
 * Created by emma on 22/03/15.
 */
public class NewsItemComparator implements java.util.Comparator<NewsItem> {
    @Override
    public int compare(NewsItem lhs, NewsItem rhs) {
        if (lhs.getScore() > rhs.getScore()) {
            return -1;
        } else if (lhs.getScore() < rhs.getScore()) {
            return 1;
        }
        if (lhs.getRootCommentIds().size() > rhs.getRootCommentIds().size()) {
            return -1;
        } else if (lhs.getRootCommentIds().size() < rhs.getRootCommentIds().size()) {
            return 1;
        }

        return 0;
    }
}
