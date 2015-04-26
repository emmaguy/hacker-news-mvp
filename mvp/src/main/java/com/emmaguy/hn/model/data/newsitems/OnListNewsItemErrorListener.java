package com.emmaguy.hn.model.data.newsitems;

import com.emmaguy.hn.model.data.events.NewsItemEvents;
import com.squareup.otto.Bus;

import rx.functions.Action1;

public class OnListNewsItemErrorListener implements Action1<Throwable> {
    private Bus mNetworkBus;

    public OnListNewsItemErrorListener(Bus networkBus) {
        mNetworkBus = networkBus;
    }

    @Override
    public void call(Throwable throwable) {
        mNetworkBus.post(new NewsItemEvents.RequestFailedEvent());
    }
}
