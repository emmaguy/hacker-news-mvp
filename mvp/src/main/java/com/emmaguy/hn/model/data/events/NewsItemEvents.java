package com.emmaguy.hn.model.data.events;

import com.emmaguy.hn.model.NewsItem;

import java.util.List;

/**
 * Created by emma on 26/04/15.
 */
public class NewsItemEvents {
    public static class RequestSucceededEvent {
        private final List<NewsItem> mNewsItems;

        public RequestSucceededEvent(List<NewsItem> newsItems) {
            mNewsItems = newsItems;
        }

        public List<NewsItem> getNewsItems() {
            return mNewsItems;
        }
    }

    public static class RequestFailedEvent {
    }
}
