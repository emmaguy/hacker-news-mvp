package com.emmaguy.hn.model.data.newsitems;

import com.emmaguy.hn.common.RxBus;
import com.emmaguy.hn.model.data.events.NewsItemEvents;

import rx.functions.Action1;

public class OnListNewsItemErrorListener implements Action1<Throwable> {
    private RxBus mNetworkBus;

    public OnListNewsItemErrorListener(RxBus networkBus) {
        mNetworkBus = networkBus;
    }

    @Override
    public void call(Throwable throwable) {
        mNetworkBus.send(new NewsItemEvents.RequestFailedEvent());
    }
}
