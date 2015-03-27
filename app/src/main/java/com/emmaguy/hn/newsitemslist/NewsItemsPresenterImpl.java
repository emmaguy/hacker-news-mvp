package com.emmaguy.hn.newsitemslist;

import android.support.annotation.NonNull;

import com.emmaguy.hn.model.HackerNewsDataSource;
import com.emmaguy.hn.model.NewsItem;
import com.emmaguy.hn.model.NewsItemsRequestFailedEvent;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemsPresenterImpl implements NewsItemsPresenter {
    private final Bus mNetworkBus;
    private final NewsItemsView mNewsItemsView;
    private final HackerNewsDataSource mDataSource;

    public NewsItemsPresenterImpl(NewsItemsView newsItemsView, HackerNewsDataSource dataSource, Bus networkBus) {
        mNetworkBus = networkBus;
        mDataSource = dataSource;
        mNewsItemsView = newsItemsView;
    }

    @Override
    public void onStart() {
        mNetworkBus.register(this);

        if (mNewsItemsView.isEmptyList()) {
            mNewsItemsView.hideError();
            mNewsItemsView.showLoadingIndicator();

            mDataSource.getLatestNewsItems();
        }
    }

    @Override
    public void onStop() {
        mNetworkBus.unregister(this);
    }

    @Subscribe
    public void onNewsItemsReceived(@NonNull ArrayList<NewsItem> newsItemList) {
        mNewsItemsView.hideLoadingIndicator();
        mNewsItemsView.showNewsItems(newsItemList);
    }

    @Subscribe
    public void onRetrieveNewsItemsFailed(NewsItemsRequestFailedEvent failed) {
        mNewsItemsView.hideLoadingIndicator();
        mNewsItemsView.showError();
    }
}
