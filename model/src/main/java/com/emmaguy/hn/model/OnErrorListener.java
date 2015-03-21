package com.emmaguy.hn.model;

import com.emmaguy.hn.common.Events;
import com.squareup.otto.Bus;

public class OnErrorListener implements rx.functions.Action1<Throwable> {
    private Bus mNetworkBus;

    public OnErrorListener(Bus networkBus) {
        mNetworkBus = networkBus;
    }

    @Override
    public void call(Throwable throwable) {
        mNetworkBus.post(Events.LATEST_NEWS_ITEMS_REQUEST_FAILED);
    }
}
