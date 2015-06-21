package com.emmaguy.hn.model.data.datasource;

import com.emmaguy.hn.common.RxBus;
import com.emmaguy.hn.model.Comment;
import com.emmaguy.hn.model.NewsItem;

import java.util.ArrayList;
import java.util.List;

/**
 * News data source based on static dummy data
 */
public class StaticDataSource implements NewsDataSource {
    public static final int DEFAULT_NUMBER_NEWS_ITEMS = 35;
    public static final int DEFAULT_NUMBER_COMMENTS = 20;

    @Override
    public void getLatestNewsItems(RxBus bus) {
        List<NewsItem> newsItems = new ArrayList<>();
        for (int i = 1; i <= DEFAULT_NUMBER_NEWS_ITEMS; i++) {
            newsItems.add(new NewsItem("Static news item #" + i, "http://www.google.com"));
        }
        bus.send(newsItems);
    }

    @Override
    public void getComments(List<String> ids, RxBus bus) {
        List<Comment> comments = new ArrayList<>();

        for (int i = 1; i <= DEFAULT_NUMBER_COMMENTS; i++) {
            comments.add(new Comment("Super insightful static comment #" + i));
        }

        bus.send(comments);
    }
}
