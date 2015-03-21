package com.emmaguy.hn.newsitemslist;

import com.emmaguy.hn.Presenter;
import com.emmaguy.hn.common.Events;
import com.emmaguy.hn.domain.NewsItemsController;
import com.emmaguy.hn.model.HackerNewsDataSource;
import com.emmaguy.hn.model.NewsItem;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

/**
 * Created by emma on 21/03/15.
 */
public class NewsItemsPresenter implements Presenter {
    private final Bus mUiBus;
    private final NewsItemsView mNewsItemsView;
    private final NewsItemsController mController;

    public NewsItemsPresenter(NewsItemsView newsItemsView, HackerNewsDataSource dataSource, Bus uiBus) {
        mUiBus = uiBus;
        mNewsItemsView = newsItemsView;
        mController = new NewsItemsController(dataSource, uiBus);
    }

    @Subscribe
    public void onNewsItemsReceived(ArrayList<NewsItem> newsItemList) {
        mNewsItemsView.hideLoadingIndicator();
        mNewsItemsView.showNewsItems(newsItemList);
    }

    @Subscribe
    public void onRetrieveNewsItemsFailed(int errorCode) {
        if (errorCode == Events.LATEST_NEWS_ITEMS_REQUEST_FAILED) {
            // TODO update UI and show error msg
        }
    }

    @Override
    public void onStart() {
        mUiBus.register(this);

        if (mNewsItemsView.listIsEmpty()) {
            mNewsItemsView.showLoadingIndicator();
            mController.execute();
        }
    }

    @Override
    public void onStop() {
        mUiBus.unregister(this);
    }
}
