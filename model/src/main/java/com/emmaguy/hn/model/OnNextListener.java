package com.emmaguy.hn.model;

import com.squareup.otto.Bus;

import java.util.List;

/**
 * Created by emma on 21/03/15.
 */
public class OnNextListener implements rx.functions.Action1<java.util.List<NewsItem>> {
    private Bus mNetworkBus;

    public OnNextListener(Bus networkBus) {
        mNetworkBus = networkBus;
    }

    @Override
    public void call(List<NewsItem> stories) {
        mNetworkBus.post(stories);
    }
}

