package com.emmaguy.hn.presenter;

import android.support.annotation.NonNull;

import com.emmaguy.hn.model.data.datasource.NewsDataSource;
import com.emmaguy.hn.model.data.events.NewsItemEvents;
import com.emmaguy.hn.view.NewsItemsView;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemsPresenter {
    private final Bus mNetworkBus;
    private final NewsDataSource mDataSource;

    private NewsItemsView mNewsItemsView;

    public NewsItemsPresenter(NewsDataSource dataSource, Bus networkBus) {
        mNetworkBus = networkBus;
        mDataSource = dataSource;
    }

    public void onStart(NewsItemsView newsItemsView) {
        setView(newsItemsView);

        mNetworkBus.register(this);

        if (mNewsItemsView.isEmptyList()) {
            mNewsItemsView.hideError();
            mNewsItemsView.showLoadingIndicator();

            mDataSource.getLatestNewsItems();
        }
    }

    private void setView(NewsItemsView newsItemsView) {
        mNewsItemsView = newsItemsView;
    }

    public void onStop() {
        mNetworkBus.unregister(this);

        setView(null);
    }

    @Subscribe
    public void onNewsItemsReceived(@NonNull NewsItemEvents.RequestSucceededEvent event) {
        mNewsItemsView.hideLoadingIndicator();
        mNewsItemsView.showNewsItems(event.getNewsItems());
    }

    @Subscribe
    public void onRetrieveNewsItemsFailed(@NonNull NewsItemEvents.RequestFailedEvent failed) {
        mNewsItemsView.hideLoadingIndicator();
        mNewsItemsView.showError();
    }
}
