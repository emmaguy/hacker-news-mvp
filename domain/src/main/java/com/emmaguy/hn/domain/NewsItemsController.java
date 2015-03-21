package com.emmaguy.hn.domain;

import com.emmaguy.hn.common.EventBusProvider;
import com.emmaguy.hn.common.LatestNewsItemsErrorCode;
import com.emmaguy.hn.model.HackerNewsDataSource;
import com.emmaguy.hn.model.NewsItem;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemsController implements NewsItemsReceiver {
    private final Bus mUiBus;
    private final HackerNewsDataSource mDataSource;

    public NewsItemsController(HackerNewsDataSource dataSource, Bus uiBus) {
        mUiBus = uiBus;
        mDataSource = dataSource;
    }

    @Override
    public void getLatestNewsItems() {
        mDataSource.getLatestNewsItems();
    }

    @Override
    public void execute() {
        EventBusProvider.getNetworkBusInstance().register(this);

        getLatestNewsItems();
    }

    @Subscribe
    public void onStoryListReceived(ArrayList<NewsItem> newsItemList) {
        mUiBus.post(newsItemList);

        EventBusProvider.getNetworkBusInstance().unregister(this);
    }

    @Subscribe
    public void onRetrieveStoryListFailed(@LatestNewsItemsErrorCode int errorCode) {
        mUiBus.post(errorCode);

        EventBusProvider.getNetworkBusInstance().unregister(this);
    }
}
