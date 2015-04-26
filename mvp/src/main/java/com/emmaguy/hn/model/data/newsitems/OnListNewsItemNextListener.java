package com.emmaguy.hn.model.data.newsitems;

import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.data.events.NewsItemEvents;
import com.squareup.otto.Bus;

import java.util.List;

import rx.functions.Action1;

/**
 * Created by emma on 21/03/15.
 */
public class OnListNewsItemNextListener implements Action1<java.util.List<NewsItem>> {
    private Bus mNetworkBus;

    public OnListNewsItemNextListener(Bus networkBus) {
        mNetworkBus = networkBus;
    }

    @Override
    public void call(List<NewsItem> newsItems) {
        mNetworkBus.post(new NewsItemEvents.RequestSucceededEvent(newsItems));
    }
}

