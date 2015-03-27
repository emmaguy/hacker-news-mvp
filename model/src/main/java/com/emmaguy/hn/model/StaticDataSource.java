package com.emmaguy.hn.model;

import com.emmaguy.hn.common.EventBusProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * News data source based on static dummy data.
 */
public class StaticDataSource implements HackerNewsDataSource {

    @Override
    public void getLatestNewsItems() {
        List<NewsItem> newsItems = new ArrayList<>();
        for (int i=0; i<35; i++) {
            newsItems.add(new NewsItem("Static news item #" + (i + 1), "http://www.google.com"));
        }
        EventBusProvider.getNetworkBusInstance().post(newsItems);
    }
}
